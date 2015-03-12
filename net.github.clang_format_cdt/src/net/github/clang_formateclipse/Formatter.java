package net.github.clang_formateclipse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class Formatter extends CodeFormatter {

	/**
	 * Constructor of Formatter
	 */
	public Formatter() {
	}

	private String[] concat(String[] first, String[] second) {
		String[] result = new String[first.length + second.length];
		System.arraycopy(first, 0, result, 0, first.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	@Override
	public TextEdit format(int kind, String source, int offset, int length,
			int indentationLevel, String lineSeparator) {
		IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
		Runtime RT = Runtime.getRuntime();
		String err = "";
		String[] args = { prefs.getString(Preferences.CLANG_FORMAT_PATH),
				String.format("-offset=%d", offset),
				String.format("-length=%d", length),
				"-output-replacements-xml",  };
		String[] options;
		try {
			options = createOptions();
		} catch(Exception e) {
			Logger.logError(e);
			return null;
		}
		if (options == null)
			return null;
		args = concat(args, options);
		Process subProc;
		try {
			subProc = RT.exec(args);
		} catch (IOException exception) {
			Logger.logError("Could not exec command", exception);
			return null;
		}
		OutputStream outStream = subProc.getOutputStream();
		try {
			outStream.write(source.getBytes(Charset.forName("UTF-8")));
			outStream.close();
		} catch (IOException exception) {
			Logger.logError("Could not send command", exception);
			return null;
		}
		// read errors
		String line = null;
		InputStreamReader reader = new InputStreamReader(
				subProc.getErrorStream());
		BufferedReader br = new BufferedReader(reader);
		try {
			while ((line = br.readLine()) != null) {
				err += line + lineSeparator;
			}
		} catch (IOException exception) {
			Logger.logError(exception);
		}
		XMLReplacementHandler replacementHandler = new XMLReplacementHandler();
		try {
			// read the edits
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.newSAXParser().parse(subProc.getInputStream(),
					replacementHandler);
		} catch (IOException exception) {
			Logger.logError(exception);
		} catch (SAXException exception) {
			Logger.logError(exception);
		} catch (ParserConfigurationException exception) {
			Logger.logError(exception);
		}

		if (!err.isEmpty()) {
			Logger.logError(
					String.format("Error on calling %s: %s",
							Arrays.toString(args), err), new Exception());
		} else {
			int textOffset = 0;
			int textLength = source.length();
			MultiTextEdit textEdit = new MultiTextEdit(textOffset, textLength);
			TextEdit edits[] = new TextEdit[0];
			edits = replacementHandler.getEdits().toArray(edits);
			textEdit.addChildren(edits);
			return textEdit;
		}
		return null;
	}

	public String[] createOptions() throws Exception {
		String filePath = filePath();
		if (filePath == null)
			throw new Exception("Could not determine file path.");
		return new String[] { "-style=file", "-assume-filename=" + filePath };
	}
	
	private String filePath() {
		IWorkbenchPart workbenchPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();
		IFile file = (IFile) workbenchPart.getSite().getPage()
				.getActiveEditor().getEditorInput().getAdapter(IFile.class);
		if (file == null)
			return null;
		return file.getRawLocation().toOSString();
	}

	@Override
	public void setOptions(Map<String, ?> options) {}

}

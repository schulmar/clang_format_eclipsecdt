package net.github.clang_formateclipse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.SAXException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class Formatter extends CodeFormatter {
	private ClangVersionOptions versionOptions;

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
		//TODO: clang_format 3.3 does only support full text replacement 
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
							Arrays.toString(args), err), new ClangFormatError());
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
	
	private String renderStyleOption(FormatOption options[]) {
		Map<String, Object> data = new HashMap<String, Object>();
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		
		for (FormatOption option : versionOptions.getFormatOptions()) {
			if(option.hasValue(preferenceStore)) {
				data.put(option.getOptionName(),
						option.getValue(preferenceStore));
			}
		}
		
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
	    return new Yaml(dumperOptions).dump(data);
	}

	public String[] createOptions() throws Exception {
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		switch (preferenceStore.getString(Preferences.STYLE_OPTION)) {
		case Preferences.CUSTOM_STYLE:
			return new String[] { String.format("-style=%s",
					renderStyleOption(versionOptions.getFormatOptions())) };
		case Preferences.ABSOLUTE_CLANG_FORMAT_FILE_STYLE:
			String clangFormatFile = preferenceStore
					.getString(Preferences.ABSOLUTE_CLANG_FORMAT_FILE_PATH_PROPERTY);
			File f = new File(clangFormatFile);
			if (!(f.exists() && !f.isDirectory())) {
				Logger.logError(clangFormatFile
						+ " could not be found. "
						+ "clang-format will search for one in the parent directories!");
			}
			// emulate a path to a cpp file at the same level as the
			// .clang-format file to tell clang-format where to look and which
			// language to assume
			return new String[] { "-style=file",
					"-assume-filename=" + clangFormatFile + ".cpp" };
		case Preferences.RELATIVE_CLANG_FORMAT_FILE_STYLE:
			String filePath = filePath();
			if(filePath == null)
				throw new Exception("Could not determine file path.");
			return new String[]{"-style=file", "-assume-filename=" + filePath};
		default:
			return new String[] { String.format("-style=%s",
					preferenceStore.getString(Preferences.STYLE_OPTION)) };
		}
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
	public void setOptions(Map<String, ?> options) {
		String versionString = Activator.getDefault().getPreferenceStore()
				.getString(Preferences.VERSION);
		ClangVersion version;
		try {
			version = ClangVersion.fromVersionString(versionString);
		} catch (ClangVersionError e1) {
			Logger.logError("Could not determine version from preferences: "
					+ versionString, e1);
			return;
		}
		try {
			versionOptions = ClangVersionOptions.getOptionsForVersion(version);
		} catch (UnsupportedClangVersion e) {
			Logger.logError(e);
		}
	}

}

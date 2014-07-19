package net.github.clang_formateclipse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class Formatter extends CodeFormatter {
	private ClangVersionOptions versionOptions;

	/**
	 * Constructor of Formatter
	 */
	public Formatter() {
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
				"-output-replacements-xml", createOptions() };
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

	public String createOptions() {
		StringBuilder styleStringBuilder = new StringBuilder();
		IPreferenceStore preferenceStore = Activator.getDefault()
				.getPreferenceStore();
		String selectedStyle = preferenceStore.getString(Preferences.STYLE_OPTION); 
		if (selectedStyle.equals(Preferences.CUSTOM_STYLE)) {
			for (FormatOption option : versionOptions.getFormatOptions()) {
				String value = option.getValueString(preferenceStore);
				if (!value.isEmpty())
					styleStringBuilder.append(String.format("%s: %s, ",
							option.getOptionName(), value));
			}
			return String.format("-style={%s}", styleStringBuilder.toString());
		} else {
			return String.format("-style=%s",
					preferenceStore.getString(Preferences.STYLE_OPTION));
		}
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

package net.github.clang_formateclipse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.cdt.core.formatter.DefaultCodeFormatterOptions;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

public class Formatter extends CodeFormatter {
	private DefaultCodeFormatterOptions preferences;

	/**
	 * Constructor of Formatter
	 */
	public Formatter() {
	}

	@Override
	public TextEdit format(int kind, String source, int offset, int length,
			int indentationLevel, String lineSeparator) {
		IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
		Runtime RT = Runtime.getRuntime();
		String target = "";
		try {
			String[] args = {
				prefs.getString(Preferences.CLANG_FORMAT_PATH),
				String.format("-offset=%d", offset),
				String.format("-length=%d", length),
				createOptions()
			};
			Process subProc = RT.exec(args);
			InputStream inStream = subProc.getInputStream();
			OutputStream outStream = subProc.getOutputStream();
			outStream.write(source.getBytes(Charset.forName("UTF-8")));
			outStream.close();
			InputStreamReader reader = new InputStreamReader(inStream);
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			while ((line = br.readLine()) != null) {
				target += line + lineSeparator;
			}
			reader = new InputStreamReader(subProc.getErrorStream());
			br = new BufferedReader(reader);
			while ((line = br.readLine()) != null) {
				System.err.println(line);
			}
		} catch (IOException exception) {
			System.out.print(exception.getMessage());
		}
		int textOffset = 0;
		int textLength = source.length();
		MultiTextEdit textEdit = new MultiTextEdit(textOffset, textLength);
		textEdit.addChild(new ReplaceEdit(textOffset, textLength, target));
		return textEdit;
	}

	public String createOptions() {
		IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
		String options = "";
		String style = "";
		if (prefs.getString(Preferences.STYLE_CHOICE) != Preferences.STYLE_NONE)
			style += styleOption("BasedOnStyle",
					prefs.getString(Preferences.STYLE_CHOICE));
		//the clang option is relative to the indented body,
		//while the preferences is relative to the "class" indentation
		style += styleOption("AccessModifierOffset",
				preferences.indent_access_specifier_extra_spaces
				- preferences.indentation_size);
		style += booleanStyleOption(Preferences.ALIGN_ESCAPED_NEWLINES_LEFT);
		style += styleOption(
				"AlignTrailingComments",
				preferences.comment_preserve_white_space_between_code_and_line_comment);
		style += booleanStyleOption(Preferences.ALLOW_ALL_PARAMETERS_OF_DECLARATION_ON_NEXT_LINE);
		style += styleOption("AllowShortIfStatementsOnASingleLine",
				preferences.keep_simple_if_on_one_line);
		style += booleanStyleOption(Preferences.ALLOW_SHORT_LOOPS_ON_A_SINGLE_LINE);
		// TODO: style += styleOption("AlwaysBreakBeforeMultilineStrings", );
		// TODO: style += styleOption("AlwaysBreakTemplateDeclarations", );
		// TODO: style += styleOption("BinPackParameters", );
		// TODO: style += styleOption("BreakBeforeBinaryOperators", );
		// TODO: style += styleOption("BreakBeforeBraces", );
		// TODO: style += styleOption("BreakConstructorInitializersBeforeComma",
		// );
		style += styleOption("ColumnLimit", preferences.page_width);
		// TODO: style +=
		// styleOption("ConstructorInitializerAllOnOneLineOrOnePerLine", );
		// TODO:style += styleOption("ConstructorInitializerIndentWidth",);
		// TODO: style += styleOption("Cpp11BracedListStyle", );
		// TODO: style += styleOption("DerivePointerBinding", );
		// TODO: is this correct?
		style += styleOption("IndentCaseLabels",
				preferences.indent_switchstatements_compare_to_switch);
		// TODO: style += styleOption("IndentFunctionDeclarationAfterType", );
		style += styleOption("IndentWidth", preferences.indentation_size);
		style += styleOption("MaxEmptyLinesToKeep",
				preferences.number_of_empty_lines_to_preserve);
		style += styleOption(
				"NamespaceIndentation",
				preferences.indent_body_declarations_compare_to_namespace_header ? "All"
						: "None");
		// TODO: style += styleOption("PenaltyBreakComment", );
		// TODO: style += styleOption("PenaltyBreakFirstLessLess", );
		// TODO: style += styleOption("PenaltyBreakString", );
		// TODO: style += styleOption("PenaltyExcessCharacter", );
		// TODO: style += styleOption("PenaltyReturnTypeOnItsOwnLine", );
		// TODO: style += styleOption("PointerBindsToType", );
		// TODO: test all referenced and trigger error on inconsistent result
		style += styleOption("SpaceAfterControlStatementKeyword",
				preferences.insert_space_before_opening_paren_in_for);
		style += styleOption("SpaceBeforeAssignmentOperators",
				preferences.insert_space_before_assignment_operator);
		// TODO: test all referenced and trigger error on inconsistent result
		style += styleOption(
				"SpaceInEmptyParentheses",
				preferences.insert_space_between_empty_parens_in_method_invocation);
		style += styleOption("SpacesBeforeTrailingComments",
				preferences.comment_min_distance_between_code_and_line_comment);
		// TODO: test all referenced and trigger error on inconsistent result
		style += styleOption("SpacesInCStyleCastParentheses",
				preferences.insert_space_after_opening_paren_in_cast);
		// TODO: too many affected options: style +=
		// styleOption("SpacesInParentheses",);
		// TODO: style += styleOption("Standard", );
		style += styleOption("TabWidth", preferences.tab_size);
		switch (preferences.tab_char) {
		case DefaultCodeFormatterOptions.TAB:
			style += styleOption("UseTab", tabIndentValue(), true);
			break;
		case DefaultCodeFormatterOptions.MIXED:
			style += styleOption("UseTab", tabIndentValue(), true);
			break;
		case DefaultCodeFormatterOptions.SPACE:
			style += styleOption("UseTab", "Never", true);
			break;
		}
		options += String.format("-style={%s}", style);
		return options;
	}

	private String styleOption(String name, String value) {
		return styleOption(name, value, false);
	}

	private String booleanStyleOption(String prefName)
	{
		return styleOption(prefName, Activator.getDefault().getPreferenceStore().getBoolean(prefName));
	}
	
	private String styleOption(String name, String value, boolean last) {
		return String.format("%s: %s" + (last ? "" : ", "), name, value);
	}

	private String styleOption(String name, int value) {
		return styleOption(name, value, false);
	}

	private String styleOption(String name, int value, boolean last) {
		return styleOption(name, Integer.toString(value), last);
	}

	private String styleOption(String name, boolean value) {
		return styleOption(name, value, false);
	}

	private String styleOption(String name, boolean value, boolean last) {
		return styleOption(name, value ? "true" : "false", last);
	}

	String tabIndentValue() {
		return preferences.use_tabs_only_for_leading_indentations ? "ForIndendation"
				: "Always";
	}

	@Override
	public void setOptions(Map<String, ?> options) {
		// a shameless copy of the
		// org.eclipse.cdt.internal.formatter.CCodeFormatter
		if (options != null) {
			Map<String, String> formatterPrefs = new HashMap<String, String>(
					options.size());
			for (String key : options.keySet()) {
				Object value = options.get(key);
				if (value instanceof String) {
					formatterPrefs.put(key, (String) value);
				}
			}
			preferences = new DefaultCodeFormatterOptions(formatterPrefs);
		} else {
			preferences = DefaultCodeFormatterOptions.getDefaultSettings();
		}
	}

}

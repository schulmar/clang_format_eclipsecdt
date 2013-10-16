package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Setting for code formatting (engine is clang-format)");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		Composite composite = getFieldEditorParent();
		addField(new FileFieldEditor(
				Preferences.CLANG_FORMAT_PATH, "Path to clang-format",
				composite));
		addField(new ComboFieldEditor(
				Preferences.BASED_ON_STYLE,
				"style",
				new String[][] {
						{ "LLVM", Preferences.STYLE_LLVM },
						{ "Google", Preferences.STYLE_GOOGLE },
						{ "Chromium", Preferences.STYLE_CHROMIUM },
						{ "Mozilla", Preferences.STYLE_MOZILLA },
						{ "clang-format's default", Preferences.NONE }, }, 
				composite));
		addField(triStateBooleanFieldEditor(Preferences.ALLOW_ALL_PARAMETERS_OF_DECLARATION_ON_NEXT_LINE, "Allow all parameters of a declaration to be wrapped to the next line", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALIGN_ESCAPED_NEWLINES_LEFT, "Align escaped newlines", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALLOW_SHORT_LOOPS_ON_A_SINGLE_LINE, "Allow short loops on a single line", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALWAYS_BREAK_BEFORE_MULTILINE_STRINGS, "Always break on multiline strings", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALWAYS_BREAK_TEMPLATE_DECLARATIONS, "Always break template declarations", composite));
		addField(triStateBooleanFieldEditor(Preferences.BIN_PACK_PARAMETERS, "BinPackParameters", composite));
		addField(triStateBooleanFieldEditor(Preferences.BREAK_BEFORE_BINARY_OPERATORS, "Break before binary operators", composite));
		addField(triStateBooleanFieldEditor(Preferences.BREAK_BEFORE_BRACES, "Break before braces", composite));
		addField(triStateBooleanFieldEditor(Preferences.BREAK_CONSTRUCTOR_INITIALIZERS_BEFORE_COMMA, "Break constructor initializers before comma", composite));
		addField(triStateBooleanFieldEditor(Preferences.CONSTRUCTOR_INITIALIZER_ALL_ON_ONE_LINE_OR_ONE_PER_LINE, "Constructor initializer all on one line or one per line", composite));
		addField(new IntegerFieldEditor(Preferences.CONSTRUCTOR_INITIALIZER_INDENT_WIDTH, "Constructor initializer indent width", composite));
		addField(triStateBooleanFieldEditor(Preferences.CPP11_BRACED_LIST_STYLE, "C++11 braced list style", composite));
		addField(triStateBooleanFieldEditor(Preferences.DERIVE_POINTER_BINDING, "Derive pointer binding", composite));
		addField(triStateBooleanFieldEditor(Preferences.INDENT_FUNCTION_DECLARATION_AFTER_TYPE, "Indent function declaration after type", composite));
		//TODO:addField(unsignedFieldEditor(Preferences.PENALTY_BREAK_COMMENT, "Penalty \"break comment\"", composite));
		addField(triStateBooleanFieldEditor(Preferences.POINTER_BINDS_TO_TYPE, "Pointer binds to type", composite));
		addField(triStateBooleanFieldEditor(Preferences.SPACE_AFTER_CONTROL_STATEMENT_KEYWORD, "Space after control statement keyword", composite));
		addField(triStateBooleanFieldEditor(Preferences.SPACE_IN_EMPTY_PARENTHESES, "Space in empty parantheses", composite));
		addField(triStateBooleanFieldEditor(Preferences.SPACES_IN_CSTYLE_CAST_PARENTHESES, "Spaces in C-style cast parantheses", composite));
		addField(triStateBooleanFieldEditor(Preferences.SPACES_IN_PARENTHESES, "Spaces in parantheses", composite));
		addField(new ComboFieldEditor(Preferences.STANDARD, "Standard", 
				new String[][] {
					{"Autodetect", Preferences.STANDARD_AUTO},
					{"C++03", Preferences.STANDARD_CPP03},
					{"C++11", Preferences.STANDARD_CPP11},
					{"default", Preferences.NONE}
				}, 
				composite));
	}

	IntegerFieldEditor unsignedFieldEditor(String name, String label, Composite parent)
	{
		IntegerFieldEditor fi = new IntegerFieldEditor(name, label, parent);
		fi.setValidRange(0, Integer.MAX_VALUE);
		return fi;
	}
	
	ComboFieldEditor triStateBooleanFieldEditor(String name, String label, Composite parent)
	{
		return new ComboFieldEditor(name, label,
				new String[][] {
					{"default", Preferences.NONE},
					{"on", Preferences.TRUE},
					{"off", Preferences.FALSE}
				}
				, parent);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
}

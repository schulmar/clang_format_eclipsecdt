package net.github.clang_formateclipse;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
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
				Preferences.STYLE_CHOICE,
				"style",
				new String[][] {
						{ "LLVM", Preferences.STYLE_LLVM },
						{ "Google", Preferences.STYLE_GOOGLE },
						{ "Chromium", Preferences.STYLE_CHROMIUM },
						{ "Mozilla", Preferences.STYLE_MOZILLA },
						{ "clang-format's default", Preferences.STYLE_NONE }, }, 
				composite));
		addField(triStateBooleanFieldEditor(Preferences.ALLOW_ALL_PARAMETERS_OF_DECLARATION_ON_NEXT_LINE, "Allow all parameters of a declaration to be wrapped to the next line", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALIGN_ESCAPED_NEWLINES_LEFT, "Align escaped newlines", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALLOW_SHORT_LOOPS_ON_A_SINGLE_LINE, "Allow short loops on a single line", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALWAYS_BREAK_BEFORE_MULTILINE_STRINGS, "Always break on multiline strings", composite));
		addField(triStateBooleanFieldEditor(Preferences.ALWAYS_BREAK_TEMPLATE_DECLARATIONS, "Always break template declarations", composite));
		addField(triStateBooleanFieldEditor(Preferences.BIN_PACK_PARAMETERS, "BinPackParameters", composite));
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

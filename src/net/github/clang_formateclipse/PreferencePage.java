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
		// TODO: determine the version from the program path
		ClangVersion clangVersion = new ClangVersion(3, 4);
		ClangVersionOptions versionOptions = null;
		try {
			versionOptions = ClangVersionOptions.getOptionsForVersion(clangVersion);
		} catch (UnsupportedClangVersion e) {
			
		}
		for(FormatOption option: versionOptions.getFormatOptions()) {
			addField(option.getFieldEditor(composite));
		}
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

package net.github.clang_formateclipse;

import java.util.Arrays;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
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
		addField(new FileFieldEditor(Preferences.CLANG_FORMAT_PATH,
				"Path to clang-format", composite));
		// TODO: determine the version from the program path
		ClangVersion clangVersion = new ClangVersion(3, 4);
		ClangVersionOptions versionOptions = null;
		try {
			versionOptions = ClangVersionOptions
					.getOptionsForVersion(clangVersion);
		} catch (UnsupportedClangVersion e) {
			Logger.logError(e);
			return;
		}
		getPreferenceStore().setValue(Preferences.MAJOR_VERSION, clangVersion.getMajor());
		getPreferenceStore().setValue(Preferences.MINOR_VERSION, clangVersion.getMinor());
		if (clangVersion == new ClangVersion(3, 3)) {
			// version 3.3 only supports preset style
			addField(new ComboFieldEditor("style", "Style preset",
					versionOptions.getStyles(), composite));
		} else {
			// versions above 3.3 support custom styles
			String stylesWithCustom[][] = Arrays.copyOf(
					versionOptions.getStyles(),
					versionOptions.getStyles().length + 1);
			stylesWithCustom[versionOptions.getStyles().length] = new String[] {
					"Custom", "" };
			addField(new ComboFieldEditor("style", "Style preset",
					stylesWithCustom, composite));
			for (FormatOption option : versionOptions.getFormatOptions()) {
				addField(option.getFieldEditor(composite));
			}
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

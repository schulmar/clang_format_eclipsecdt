package net.github.clang_formateclipse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private List<FieldEditor> dependentFieldEditors;
	
	public PreferencePage() {
		super(GRID);
		dependentFieldEditors = new ArrayList<FieldEditor>();
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Setting for code formatting (engine is clang-format)");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		FileFieldEditor clangFormatPathEditor = new FileFieldEditor(
				Preferences.CLANG_FORMAT_PATH, "Path to clang-format",
				getFieldEditorParent());
		addField(clangFormatPathEditor);
		ClangVersion clangVersion;
		try {
			clangVersion = ClangVersion.fromExecutable(getPreferenceStore().getString(Preferences.CLANG_FORMAT_PATH));
		} catch (ClangVersionError e1) {
			Logger.logError(e1);
			clangFormatPathEditor
					.setErrorMessage("Could not determine clang-format version: "
							+ e1.getMessage());
			return;
		}
		createVersionDependandFieldEditors(clangVersion);
	}

	private void createVersionDependandFieldEditors(ClangVersion clangVersion) {
		ClangVersionOptions versionOptions = null;
		try {
			versionOptions = ClangVersionOptions
					.getOptionsForVersion(clangVersion);
		} catch (UnsupportedClangVersion e) {
			Logger.logError(e);
			return;
		}
		createOptionsForVersion(clangVersion, versionOptions);
	}
	
	public void createOptionsForVersion(ClangVersion clangVersion,
			ClangVersionOptions versionOptions) {
		getPreferenceStore().setValue(Preferences.MAJOR_VERSION,
				clangVersion.getMajor());
		getPreferenceStore().setValue(Preferences.MINOR_VERSION,
				clangVersion.getMinor());
		if (clangVersion == new ClangVersion(3, 3)) {
			// version 3.3 only supports preset style
			ComboFieldEditor styleSelector = new ComboFieldEditor("style", "Style preset",
					versionOptions.getStyles(), getFieldEditorParent());
			dependentFieldEditors.add(styleSelector);
			addField(styleSelector);
		} else {
			// versions above 3.3 support custom styles
			String stylesWithCustom[][] = Arrays.copyOf(
					versionOptions.getStyles(),
					versionOptions.getStyles().length + 1);
			stylesWithCustom[versionOptions.getStyles().length] = new String[] {
					"Custom", "" };
			ComboFieldEditor styleSelector = new ComboFieldEditor("style", "Style preset",
					stylesWithCustom, getFieldEditorParent());
			dependentFieldEditors.add(styleSelector);
			addField(styleSelector);
			for (FormatOption option : versionOptions.getFormatOptions()) {
				FieldEditor fieldEditor = option.getFieldEditor(getFieldEditorParent());
				dependentFieldEditors.add(fieldEditor);
				addField(fieldEditor);
			}
		}
	}
	
	public void clearOptionsForVersion() {
		for(FieldEditor fieldEditor : dependentFieldEditors)
			fieldEditor.dispose();
		dependentFieldEditors.clear();
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

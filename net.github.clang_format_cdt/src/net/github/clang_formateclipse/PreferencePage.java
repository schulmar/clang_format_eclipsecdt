package net.github.clang_formateclipse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private List<FieldEditor> dependentFieldEditors;
	private ComboFieldEditor clangFormatVersionEditor;
	
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
		dependentFieldEditors.clear();
		FileFieldEditor clangFormatPathEditor = new FileFieldEditor(
				Preferences.CLANG_FORMAT_PATH, "Path to clang-format",
				getFieldEditorParent());
		addField(clangFormatPathEditor);
		ClangVersion clangVersion;
		try {
			clangVersion = ClangVersion.fromVersionString(getPreferenceStore().getString(Preferences.VERSION));
			Logger.logInfo("Read clang version from preference: " + clangVersion.toString());
		} catch(Exception e) {
			Logger.logError("Could not read clang version from preferences", e);
			try {
				clangVersion = ClangVersion.fromExecutable(getPreferenceStore().getString(Preferences.CLANG_FORMAT_PATH));
				Logger.logInfo("Read clang version from executable: " + clangVersion.toString());
			} catch (Exception e1) {
				// could not read from the executable either
				Logger.logError("Could not determine clang version from executable output", e1);
				clangFormatPathEditor
						.setErrorMessage("Could not determine clang-format version: "
								+ e1.getMessage());
				// select fallback version
				clangVersion = new ClangVersion(3, 4);
				getPreferenceStore().setValue(Preferences.VERSION, clangVersion.toString());
			}
		}
		// list the known versions for selection
		clangFormatVersionEditor = new ComboFieldEditor(Preferences.VERSION,
				"LLVM version", stringArrayToOptions(new String[] {
						new ClangVersion(3, 3).toString(),
						new ClangVersion(3, 4).toString(),
						new ClangVersion(3, 5).toString(),
						new ClangVersion(3, 6).toString()}),
				getFieldEditorParent());
		addField(clangFormatVersionEditor);
		createVersionDependandFieldEditors(clangVersion);
	}
	
	private String[][] stringArrayToOptions(String array[]) {
		String result[][] = new String[array.length][2];
		for(int i = 0; i < array.length; ++i) {
			result[i][0] = array[i];
			result[i][1] = array[i];
		}
		return result;
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
		setEnabledState(getPreferenceStore()
				.getString(Preferences.STYLE_OPTION).equals(Preferences.CUSTOM_STYLE));
	}
	
	public void createOptionsForVersion(ClangVersion clangVersion,
			ClangVersionOptions versionOptions) {
		Logger.logInfo("Preparing options for version " + clangVersion.toString());
		if (clangVersion.equals(new ClangVersion(3, 3))) {
			// version 3.3 only supports preset style
			ComboFieldEditor styleSelector = new ComboFieldEditor(
					Preferences.STYLE_OPTION, "Style preset",
					versionOptions.getStyles(), getFieldEditorParent());
			//dependentFieldEditors.add(styleSelector);
			addField(styleSelector);
		} else {
			// versions above 3.3 support custom styles
			String stylesWithCustom[][] = Arrays.copyOf(
					versionOptions.getStyles(),
					versionOptions.getStyles().length + 1);
			stylesWithCustom[versionOptions.getStyles().length] = new String[] {
					Preferences.CUSTOM_STYLE, Preferences.CUSTOM_STYLE };
			ComboFieldEditor styleSelector = new ComboFieldEditor("style", "Style preset",
					stylesWithCustom, getFieldEditorParent());
			//dependentFieldEditors.add(styleSelector);
			addField(styleSelector);
			for (FormatOption option : versionOptions.getFormatOptions()) {
				FieldEditor fieldEditor = option.getFieldEditor(getFieldEditorParent());
				dependentFieldEditors.add(fieldEditor);
				addField(fieldEditor);
			}
		}
	}
	
	private void setEnabledState(boolean enabled) {
		for(FieldEditor fieldEditor : dependentFieldEditors)
			fieldEditor.setEnabled(enabled, getFieldEditorParent());
	}

	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		switch (((FieldEditor)event.getSource()).getPreferenceName()) {
		case Preferences.STYLE_OPTION:
			setEnabledState(event.getNewValue().toString() == Preferences.CUSTOM_STYLE);
			super.propertyChange(event);
			break;
		case Preferences.CLANG_FORMAT_PATH:
			super.propertyChange(event);
			try {
				ClangVersion newClangVersion = ClangVersion.fromExecutable(event.getNewValue().toString());
				Logger.logInfo("New version from executable: " + newClangVersion.toString());
				getPreferenceStore().setValue(Preferences.VERSION, newClangVersion.toString());
				clangFormatVersionEditor.load();
				setMessage(String
						.format("New clang version %s detected. Close and reopen preferences to see version specific options.",
								newClangVersion.toString()));
			}catch (Exception e) {
				Logger.logError("Could not determine version from executable", e);
			}
			break;
		case Preferences.VERSION:
			super.propertyChange(event);
			String newVersionString = event.getNewValue().toString();
			try {
				ClangVersion newClangVersion = ClangVersion.fromVersionString(newVersionString);
				Logger.logInfo("New version selected: " + newClangVersion.toString());
				setMessage("Close and reopen Preferences to see version specific options.");
			} catch(Exception e) {
				Logger.logError("Could not change version from field selection " + newVersionString, e);
			}
			break;
		default:
			super.propertyChange(event);
			break;
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

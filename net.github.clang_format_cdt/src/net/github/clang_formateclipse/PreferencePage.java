package net.github.clang_formateclipse;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private ComboFieldEditor clangFormatVersionEditor;
	
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
				clangVersion = Preferences.DEFAULT_CLANG_VERSION;
				getPreferenceStore().setValue(Preferences.VERSION, clangVersion.toString());
			}
		}
		// list the known versions for selection
		clangFormatVersionEditor = new ComboFieldEditor(Preferences.VERSION,
				"LLVM version",
				stringArrayToOptions(ClangVersionOptions.supportedVersions()),
				getFieldEditorParent());
		addField(clangFormatVersionEditor);
	}
	
	private String[][] stringArrayToOptions(String array[]) {
		String result[][] = new String[array.length][2];
		for(int i = 0; i < array.length; ++i) {
			result[i][0] = array[i];
			result[i][1] = array[i];
		}
		return result;
	}

	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		switch (((FieldEditor)event.getSource()).getPreferenceName()) {
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
			//TODO: is this still necessary? (The options are on a separate page now!)
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

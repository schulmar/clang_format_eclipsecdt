package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	RadioGroupFieldEditor styleField;
	FileFieldEditor clangFormatPathField;

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
		clangFormatPathField = new FileFieldEditor(
				Preferences.CLANG_FORMAT_PATH, "Path to clang-format",
				composite);
		addField(clangFormatPathField);
		styleField = new RadioGroupFieldEditor(
				Preferences.STYLE_CHOICE,
				" style ",
				1,
				new String[][] {
						{ "&llvm : LLVM style ", Preferences.STYLE_LLVM },
						{ "&google : Google style", Preferences.STYLE_GOOGLE },
						{ "&chromium : Chromium style",
								Preferences.STYLE_CHROMIUM },
						{ "&mozilla : Mozilla style", Preferences.STYLE_MOZILLA },
						{ "&default : Clang format's default",
								Preferences.STYLE_NONE }, }, composite);
		addField(styleField);
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

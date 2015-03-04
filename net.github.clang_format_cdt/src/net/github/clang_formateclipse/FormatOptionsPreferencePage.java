package net.github.clang_formateclipse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class FormatOptionsPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private List<FieldEditor> dependentFieldEditors;
	ClangFormatFileFieldEditor clangFormatFileFieldEditor;
	LoadStorePseudoFieldEditor loadStoreButtons;
	
	static private String[] filterExtensions = new String[]{".clang-format", "*.*"};

	public FormatOptionsPreferencePage() {
		super(GRID);
		dependentFieldEditors = new ArrayList<FieldEditor>();
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Setting for code formatting (engine is clang-format)");
	}

	@Override
	protected void createFieldEditors() {
		dependentFieldEditors.clear();
		
		ClangVersion clangVersion;
		try {
			clangVersion = ClangVersion
					.fromVersionString(getPreferenceStore().getString(
							Preferences.VERSION));
		} catch (ClangVersionError e) {
			clangVersion = Preferences.DEFAULT_CLANG_VERSION;
		}
		createVersionDependentFieldEditors(clangVersion);
	}
	
	private void createVersionDependentFieldEditors(ClangVersion clangVersion) {
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
	
	private void createFormatFileLoadStoreButtons() {
		loadStoreButtons = new LoadStorePseudoFieldEditor(getFieldEditorParent());
		addField(loadStoreButtons);
		
		loadStoreButtons.addStoreListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
				dialog.setText("Saving current setting to .clang-format");
				dialog.setFilterExtensions(filterExtensions);
				String fileName = dialog.open();
				if (fileName != null) {
					try {
						FormatOptionsStorer.storeTo(getOptions(), fileName);
					} catch (FileNotFoundException e) {
						setErrorMessage(e.getMessage());
					}
				}
			}
		});
		
		loadStoreButtons.addLoadListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
				dialog.setText("Loading options from .clang-format");
				dialog.setFilterExtensions(filterExtensions);
				String fileName = dialog.open();
				if (fileName != null) {
					try {
						setOptionsFrom(FormatOptionsLoader.loadFrom(fileName));
					} catch (FileNotFoundException e) {
						setErrorMessage(e.getMessage());
					}
				}
			}
		});
	}
	
	private Map<String, Object> getOptions() {
		Map<String, Object> result = new HashMap<String, Object>();
		for (FieldEditor fieldEditor : dependentFieldEditors) {
			String preferenceName = fieldEditor.getPreferenceName();
			if (fieldEditor instanceof BooleanFieldEditorWithDefault) {
				BooleanFieldEditorWithDefault booleanFieldEditor = (BooleanFieldEditorWithDefault) fieldEditor;
				if (!booleanFieldEditor.isDefault()) {
					result.put(preferenceName, booleanFieldEditor.getValue());
				}
			} else if (fieldEditor instanceof ComboFieldEditorWithDefault) {
				ComboFieldEditorWithDefault comboFieldEditor = (ComboFieldEditorWithDefault) fieldEditor;
				if (!comboFieldEditor.isDefault()) {
					result.put(preferenceName,
							comboFieldEditor.getStringValue());
				}
			} else if (fieldEditor instanceof IntegerFieldEditorWithDefault) {
				IntegerFieldEditorWithDefault integerFieldEditor = (IntegerFieldEditorWithDefault) fieldEditor;
				if (!integerFieldEditor.isDefault()) {
					result.put(preferenceName, integerFieldEditor.getValue());
				}
			} else if (fieldEditor instanceof StringListEditor) {
				StringListEditor stringListEditor = (StringListEditor) fieldEditor;
				if (!stringListEditor.isDefault()) {
					result.put(preferenceName, stringListEditor.getValues());
				}
			} else if (fieldEditor instanceof StringFieldEditor) {
				StringFieldEditor stringFieldEditor = (StringFieldEditor) fieldEditor;
				if (!stringFieldEditor.getStringValue().isEmpty()) {
					result.put(preferenceName,
							stringFieldEditor.getStringValue());
				}
			}
		}
		return result;
	}	
	
	private void setOptionsFrom(Map<String, Object> map) {
		for(FieldEditor fieldEditor: dependentFieldEditors) {
			String preferenceName = fieldEditor.getPreferenceName();
			if(map.containsKey(preferenceName)) {
				Object value = map.get(preferenceName);
				if(fieldEditor instanceof ComboFieldEditorWithDefault) {
					((ComboFieldEditorWithDefault) fieldEditor)
							.setStringValue(value.toString());
				} else if(fieldEditor instanceof IntegerFieldEditorWithDefault) {
					((IntegerFieldEditorWithDefault) fieldEditor)
							.setStringValue(Integer.toString((Integer)value));
				} else if(fieldEditor instanceof StringListEditor) {
					if(value instanceof ArrayList<?>) {
						ArrayList<?> list = (ArrayList<?>) value;
						((StringListEditor) fieldEditor).setValues(list
								.toArray(new String[list.size()]));
					}
				} else if (fieldEditor instanceof StringFieldEditor) {
					StringFieldEditor stringFieldEditor = (StringFieldEditor) fieldEditor;
					stringFieldEditor.setStringValue(value.toString());
				}
			}
		}
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
			ArrayList<String[]> styles = new ArrayList<String[]>(
					Arrays.asList(versionOptions.getStyles()));
			styles.add(new String[] {
					Preferences.CUSTOM_STYLE, Preferences.CUSTOM_STYLE });
			styles.add(new String[] {
					Preferences.ABSOLUTE_CLANG_FORMAT_FILE_STYLE_DESCRIPTION,
					Preferences.ABSOLUTE_CLANG_FORMAT_FILE_STYLE });
			styles.add(new String[] {
					Preferences.RELATIVE_CLANG_FORMAT_FILE_STYLE_DESCRIPTION,
					Preferences.RELATIVE_CLANG_FORMAT_FILE_STYLE });
			String[][] stylesWithAdditionalOptions = new String[styles.size()][];
			styles.toArray(stylesWithAdditionalOptions);
			ComboFieldEditor styleSelector = new ComboFieldEditor("style",
					"Style preset", stylesWithAdditionalOptions,
					getFieldEditorParent());
			addField(styleSelector);
			
			clangFormatFileFieldEditor = new ClangFormatFileFieldEditor(
					Preferences.ABSOLUTE_CLANG_FORMAT_FILE_PATH_PROPERTY,
					Preferences.ABSOLUTE_CLANG_FORMAT_FILE_PATH_LABEL,
					getFieldEditorParent());
			clangFormatFileFieldEditor.setEmptyStringAllowed(false);
			setClangFormatFileFieldEditorEnabled(getPreferenceStore()
					.getString(Preferences.STYLE_OPTION).equals(
							Preferences.ABSOLUTE_CLANG_FORMAT_FILE_STYLE));
			addField(clangFormatFileFieldEditor);
			
			createFormatFileLoadStoreButtons();
			
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
		loadStoreButtons.setEnabled(enabled, getFieldEditorParent());
	}
	
	private void setClangFormatFileFieldEditorEnabled(boolean enabled) {
		clangFormatFileFieldEditor.setEnabled(enabled, getFieldEditorParent());
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getSource() instanceof FieldEditor) {
			FieldEditor source = (FieldEditor) event.getSource();
			if (source.getPreferenceName().equals(Preferences.STYLE_OPTION)) {
				switch (event.getNewValue().toString()) {
				case Preferences.CUSTOM_STYLE:
					setEnabledState(true);
					setClangFormatFileFieldEditorEnabled(false);
					break;
				case Preferences.ABSOLUTE_CLANG_FORMAT_FILE_STYLE:
					setEnabledState(false);
					setClangFormatFileFieldEditorEnabled(true);
					break;
				case Preferences.RELATIVE_CLANG_FORMAT_FILE_STYLE:
				default:
					setEnabledState(false);
					setClangFormatFileFieldEditorEnabled(false);
					break;
				}
			}
		}
		super.propertyChange(event);
	}
	
	@Override
	public void init(IWorkbench workbench) {
	}

}

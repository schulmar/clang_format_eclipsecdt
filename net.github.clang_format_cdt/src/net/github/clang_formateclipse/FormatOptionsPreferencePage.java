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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class FormatOptionsPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private List<FieldEditor> dependentFieldEditors;
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
		
		Button storeFileButton = new Button(getFieldEditorParent(), SWT.PUSH);
		storeFileButton.setText("Store .clang-format file");
		storeFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
				dialog.setText("Saving current setting to .clang-format");
				dialog.setFilterExtensions(filterExtensions);
				String fileName = dialog.open();
				if(fileName != null) {
					try {
						FormatOptionsStorer.storeTo(getOptions(), fileName);
					} catch (FileNotFoundException e) {
						setErrorMessage(e.getMessage());
					}
				}
			}
		});
		
		Button loadFileButton = new Button(getFieldEditorParent(), SWT.PUSH);
		loadFileButton.setText("Load .clang-format file");
		loadFileButton.addSelectionListener(new SelectionAdapter() {
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
		
		createOptionsForVersion(clangVersion, versionOptions);
		setEnabledState(getPreferenceStore()
				.getString(Preferences.STYLE_OPTION).equals(Preferences.CUSTOM_STYLE));
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
		if(event.getSource() instanceof FieldEditor) {
			FieldEditor source = (FieldEditor)event.getSource(); 
			if (source.getPreferenceName().equals(Preferences.STYLE_OPTION)) {
				setEnabledState(event.getNewValue().toString()
						.equals(Preferences.CUSTOM_STYLE));
		}
		}
		super.propertyChange(event);
	}
	
	@Override
	public void init(IWorkbench workbench) {
	}

}

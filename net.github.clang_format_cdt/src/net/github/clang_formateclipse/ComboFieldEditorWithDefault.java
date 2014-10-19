package net.github.clang_formateclipse;

import java.util.Arrays;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A Combobox field editor that has a default and only values (=displayed text)
 * 
 * Most of the implementation is a copy of the original ComboFieldEditor written by
 * IBM Corporation and Remy Chi Jian Suen
 */
public class ComboFieldEditorWithDefault extends FieldEditor {

	/**
	 * The <code>Combo</code> widget.
	 */
	private Combo fCombo;
	
	/**
	 * The value (not the name) of the currently selected item in the Combo widget.
	 */
	private String fValue;
	
	/**
	 * The valid values stored in this box
	 */
	private String[] fEntries;

	/**
	 * Create the combo box field editor.
	 * 
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
	 * @param entries to select from
	 * @param parent the parent composite
	 */
	public ComboFieldEditorWithDefault(String name, String labelText, String[] entries, Composite parent) {
		init(name, labelText);
		fEntries = Arrays.copyOf(entries, entries.length + 1);
		fEntries[fEntries.length - 1] = "default";
		createControl(parent);		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
	 */
	protected void adjustForNumColumns(int numColumns) {
		if (numColumns > 1) {
			Control control = getLabelControl();
			int left = numColumns;
			if (control != null) {
				((GridData)control.getLayoutData()).horizontalSpan = 1;
				left = left - 1;
			}
			((GridData)fCombo.getLayoutData()).horizontalSpan = left;
		} else {
			Control control = getLabelControl();
			if (control != null) {
				((GridData)control.getLayoutData()).horizontalSpan = 1;
			}
			((GridData)fCombo.getLayoutData()).horizontalSpan = 1;			
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite, int)
	 */
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		int comboC = 1;
		if (numColumns > 1) {
			comboC = numColumns - 1;
		}
		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		control.setLayoutData(gd);
		control = getComboBoxControl(parent);
		gd = new GridData();
		gd.horizontalSpan = comboC;
		gd.horizontalAlignment = GridData.FILL;
		control.setLayoutData(gd);
		control.setFont(parent.getFont());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	protected void doLoad() {
		setStringValue(getPreferenceStore().getString(getPreferenceName()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	protected void doLoadDefault() {
		setStringValue(getPreferenceStore().getDefaultString(getPreferenceName()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	protected void doStore() {
		if (fValue == null) {
			getPreferenceStore().setToDefault(getPreferenceName());
			return;
		}
		getPreferenceStore().setValue(getPreferenceName(), fValue);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	public int getNumberOfControls() {
		return 2;
	}

	/*
	 * Lazily create and return the Combo control.
	 */
	private Combo getComboBoxControl(Composite parent) {
		if (fCombo == null) {
			fCombo = new Combo(parent, SWT.READ_ONLY);
			fCombo.setFont(parent.getFont());
			for (String entry: fEntries) {
				fCombo.add(entry);
			}
			
			fCombo.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					String oldValue = fValue;
					String name = fCombo.getText();
					fValue = getValueForName(name);
					setPresentsDefaultValue(false);
					fireValueChanged(VALUE, oldValue, fValue);					
				}
			});
		}
		return fCombo;
	}
	
	/*
	 * Given the name (label) of an entry, return the corresponding value.
	 */
	private String getValueForName(String name) {
		return name.equalsIgnoreCase("default") ? "" : name;
	}
	
	private String getNameForValue(String value) {
		return value.isEmpty() ? "default" : value;
	}
	
	private boolean knownValue(String value) {
		if(value.isEmpty())
			return true;
		for(String validValue: fEntries) {
			if(validValue.equals(value))
				return true;
		}
		return false;
	}
	
	private void addValidValue(String value) {
		fEntries = Arrays.copyOf(fEntries, fEntries.length + 1);
		fEntries[fEntries.length - 1] = value;
		fCombo.add(value);
	}
	
	/*
	 * Set the name in the combo widget to match the specified value.
	 */
	public void setStringValue(String value) {
		fValue = value;
		
		if(!knownValue(value)) {
			addValidValue(value);
		}
		fCombo.setText(getNameForValue(value));
	}
	
	public String getStringValue() {
		return fValue;
	}

	public boolean isDefault() {
		return fValue.isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#setEnabled(boolean,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	public void setEnabled(boolean enabled, Composite parent) {
		super.setEnabled(enabled, parent);
		getComboBoxControl(parent).setEnabled(enabled);
	}
}

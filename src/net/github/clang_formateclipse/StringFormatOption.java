package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class StringFormatOption extends FormatOption {

	public StringFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return new StringFieldEditor(getOptionName(), getOptionDescription(), parent); 
	}

	@Override
	String getValueString(IPreferenceStore preferenceStore) {
		return preferenceStore.getString(getOptionName());
	}

}

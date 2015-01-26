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
		return setTooltipAndReturn(new StringFieldEditor(getOptionName(),
				getOptionName(), parent), parent); 
	}

	@Override
	Object getValue(IPreferenceStore preferenceStore) {
		return preferenceStore.getString(getOptionName());
	}

	@Override
	boolean hasValueImpl(IPreferenceStore preferenceStore) {
		return !preferenceStore.getString(getOptionName()).isEmpty();
	}

}

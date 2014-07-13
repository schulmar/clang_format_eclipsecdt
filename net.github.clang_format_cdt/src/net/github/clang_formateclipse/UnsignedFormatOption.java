package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class UnsignedFormatOption extends FormatOption {

	public UnsignedFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		IntegerFieldEditor fieldEditor = new IntegerFieldEditor(
				getOptionName(), getOptionDescription(), parent);
		fieldEditor.setValidRange(0, Integer.MAX_VALUE);
		return fieldEditor;
	}

	@Override
	String getValueString(IPreferenceStore preferenceStore) {
		return preferenceStore.getString(getOptionName());
	}

}

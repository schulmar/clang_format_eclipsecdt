package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;

public class UnsignedFormatOption extends FormatOption {

	public UnsignedFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		IntegerFieldEditorWithDefault fieldEditor = new IntegerFieldEditorWithDefault(
				getOptionName(), getOptionName(), parent);
		fieldEditor.setValidRange(0, Integer.MAX_VALUE);
		return setTooltipAndReturn(fieldEditor, parent);
	}

	@Override
	Object getValue(IPreferenceStore preferenceStore) {
		return preferenceStore.getInt(getOptionName());
	}

	@Override
	boolean hasValueImpl(IPreferenceStore preferenceStore) {
		String value = preferenceStore.getString(getOptionName());
		return Helper.isInteger(value)
				&& preferenceStore.getInt(getOptionName()) >= 0; 
	}

}

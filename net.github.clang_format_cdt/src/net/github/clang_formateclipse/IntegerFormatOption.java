package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;

public class IntegerFormatOption extends FormatOption {

	public IntegerFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		IntegerFieldEditorWithDefault fieldEditor = new IntegerFieldEditorWithDefault(
				getOptionName(), getOptionName(), parent);
		fieldEditor.setValidRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
		return setTooltipAndReturn(fieldEditor, parent);
	}

	@Override
	String getValueString(IPreferenceStore preferenceStore) {
		return Integer.toString(preferenceStore.getInt(getOptionName()));
	}

}

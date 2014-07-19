package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class IntegerFormatOption extends FormatOption {

	public IntegerFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return setTooltipAndReturn(new IntegerFieldEditor(getOptionName(),
				getOptionName(), parent), parent);
	}

	@Override
	String getValueString(IPreferenceStore preferenceStore) {
		return Integer.toString(preferenceStore.getInt(getOptionName()));
	}

}

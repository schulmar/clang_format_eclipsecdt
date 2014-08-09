package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;

public class StringListFormatOption extends FormatOption {

	public StringListFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return setTooltipAndReturn(new StringListEditor(getOptionName(),
				getOptionName(), parent), parent);
	}

	@Override
	String getValueString(IPreferenceStore preferenceStore) {
		return preferenceStore.getString(getOptionName());
	}

}
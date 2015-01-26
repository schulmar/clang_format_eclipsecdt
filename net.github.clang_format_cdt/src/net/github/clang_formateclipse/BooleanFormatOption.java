package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;

public class BooleanFormatOption extends FormatOption {

	public BooleanFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return setTooltipAndReturn(new BooleanFieldEditorWithDefault(
				getOptionName(), getOptionName(), parent), parent);
	}

	String getStringValue(IPreferenceStore preferenceStore) {
		return preferenceStore.getString(getOptionName());
	}
	
	@Override
	Object getValue(IPreferenceStore preferenceStore) {
		return preferenceStore.getBoolean(getOptionName());
	}

	@Override
	boolean hasValueImpl(IPreferenceStore preferenceStore) {
		return getStringValue(preferenceStore).equals(Preferences.TRUE)
				|| getStringValue(preferenceStore).equals(Preferences.FALSE);
	}

}

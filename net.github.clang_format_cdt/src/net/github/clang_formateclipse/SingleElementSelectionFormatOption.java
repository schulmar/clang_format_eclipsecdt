package net.github.clang_formateclipse;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;

public class SingleElementSelectionFormatOption extends FormatOption {

	private String elements[];

	public SingleElementSelectionFormatOption(String optionName,
			String optionDescription, String elements[]) {
		super(optionName, optionDescription);
		this.elements = elements;
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return setTooltipAndReturn(new ComboFieldEditorWithDefault(
				getOptionName(), getOptionName(), elements, parent), parent);
	}

	@Override
	String getValue(IPreferenceStore preferenceStore) {
		return preferenceStore.getString(getOptionName());
	}

}

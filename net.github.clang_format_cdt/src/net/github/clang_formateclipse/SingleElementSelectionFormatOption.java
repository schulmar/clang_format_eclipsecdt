package net.github.clang_formateclipse;

import java.util.Arrays;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;

public class SingleElementSelectionFormatOption extends FormatOption {

	private String elements[][];

	public SingleElementSelectionFormatOption(String optionName,
			String optionDescription, String elements[][]) {
		super(optionName, optionDescription);
		String elementsWithDefault[][] = Arrays.copyOf(elements, elements.length + 1);
		elementsWithDefault[elements.length] = new String[]{"default", ""};
		this.elements = elementsWithDefault;
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return setTooltipAndReturn(new ComboFieldEditor(getOptionName(),
				getOptionName(), elements, parent), parent);
	}

	@Override
	String getValueString(IPreferenceStore preferenceStore) {
		return preferenceStore.getString(getOptionName());
	}

}

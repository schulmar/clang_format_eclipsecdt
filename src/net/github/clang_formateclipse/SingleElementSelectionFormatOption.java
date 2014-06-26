package net.github.clang_formateclipse;

import java.util.Arrays;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;

public class SingleElementSelectionFormatOption extends FormatOption {

	private String elements[][];

	public SingleElementSelectionFormatOption(String optionName,
			String optionDescription, String elements[][]) {
		super(optionName, optionDescription);
		String elementsWithDefault[][] = Arrays.copyOf(elements, elements.length + 1);
		elementsWithDefault[elements.length] = new String[]{"default", ""};
		this.elements = elements;
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return new ComboFieldEditor(getOptionName(), getOptionDescription(),
				elements, parent);
	}

}

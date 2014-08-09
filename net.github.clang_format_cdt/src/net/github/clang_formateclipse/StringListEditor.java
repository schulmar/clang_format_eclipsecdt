package net.github.clang_formateclipse;

import java.util.Arrays;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.widgets.Composite;

public class StringListEditor extends ListEditor {
	public StringListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}

	@Override
	protected String createList(String[] elements) {
		return Arrays.toString(elements);
	}

	@Override
	protected String getNewInputObject() {
		InputDialog inputDialog = new InputDialog(getShell(),
				"New foreach macro",
				"Enter the name of the foreach macro you want to add", "",
				new IInputValidator() {
					private String validRegex = "[_a-zA-Z][_a-zA-Z0-9]*";

					public String isValid(String userInput) {
						if (userInput.matches(validRegex)) {
							return null;
						} else {
							return "The name must be a valid C++ token (regex:"
									+ validRegex + ")";
						}
					}
				});
		return InputDialog.OK == inputDialog.open() ? inputDialog.getValue()
				: null;
	}

	@Override
	protected String[] parseString(String elementsString) {
		elementsString = elementsString.trim().replace("[", "").replace("]", "");
		if (elementsString.isEmpty())
			return new String[] {};
		String[] elements = elementsString.split(",");
		for(int i = 0; i < elements.length; ++i) {
			elements[i] = elements[i].trim();
		}
		return elements;
	}

}

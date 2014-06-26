package net.github.clang_formateclipse;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.widgets.Composite;

public class BooleanFormatOption extends FormatOption {

	public BooleanFormatOption(String optionName, String optionDescription) {
		super(optionName, optionDescription);
	}

	@Override
	FieldEditor getFieldEditor(Composite parent) {
		return new ComboFieldEditor(getOptionName(), getOptionDescription(),
				new String[][] { { "default", Preferences.NONE },
						{ "on", Preferences.TRUE },
						{ "off", Preferences.FALSE } }, parent);
	}

}

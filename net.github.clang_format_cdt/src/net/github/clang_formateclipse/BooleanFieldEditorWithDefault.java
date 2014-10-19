package net.github.clang_formateclipse;

import org.eclipse.swt.widgets.Composite;

public class BooleanFieldEditorWithDefault extends ComboFieldEditorWithDefault {

	public BooleanFieldEditorWithDefault(String name, String labelText, Composite parent) {
		super(name, labelText, new String[] {Preferences.TRUE, Preferences.FALSE }, parent);
	}

	public boolean getValue() {
		return Boolean.parseBoolean(getStringValue());
	}
	
}

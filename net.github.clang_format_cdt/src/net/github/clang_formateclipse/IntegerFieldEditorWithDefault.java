package net.github.clang_formateclipse;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class IntegerFieldEditorWithDefault extends StringFieldEditor {

	private int min;
	private int max;
	static private String defaultValue = "default";
	
	public IntegerFieldEditorWithDefault(String name, String labelText,
			Composite parent) {
		super(name, labelText, parent);
		setToolTip();
	}

	public void setValidRange(int min, int max) {
		this.min = min;
		this.max = max;
		setToolTip();
	}
	
	private void setToolTip() {
		StringBuilder builder = new StringBuilder();
		builder.append("\"" + defaultValue + "\" or integer from range [");
		builder.append(min);
		builder.append("; ");
		builder.append(max);
		builder.append("]");
		getTextControl().setToolTipText(builder.toString());
	}

	@Override
	protected boolean doCheckState() {
		String stringValue = getStringValue();
		// string must be defaultValue or
		if(stringValue.compareToIgnoreCase(defaultValue) == 0)
			return true;
		// else it must be a number between min and max
		try {
			int integerValue = Integer.parseInt(stringValue);
			if(min <= integerValue && integerValue <= max)
				return true;
			StringBuilder builder = new StringBuilder();
			builder.append("Number must be integer between ");
			builder.append(min);
			builder.append(" and ");
			builder.append(max);
			setErrorMessage(builder.toString());
		} catch (NumberFormatException e) {
			setErrorMessage("Input must be \"" + defaultValue
					+ "\" or an integer");
		}
		return false;
	}

	@Override
	protected void doLoad() {
		String stringValue = getPreferenceStore().getString(getPreferenceName());
		if(stringValue.isEmpty())
			setStringValue(defaultValue);
		else
			setStringValue(stringValue);
	}

	@Override
	protected void doStore() {
		String stringValue = getStringValue();
		getPreferenceStore().setValue(getPreferenceName(),
				stringValue.equalsIgnoreCase(defaultValue) ? "" : stringValue);
	}
	
}

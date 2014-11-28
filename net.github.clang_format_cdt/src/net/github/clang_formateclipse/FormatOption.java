package net.github.clang_formateclipse;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;

public abstract class FormatOption {
	
	private String optionName;
	private String optionDescription;
	
	public FormatOption(String optionName, String optionDescription) {
		this.optionName = optionName;
		this.optionDescription = optionDescription;
	}
	
	public String getOptionName() {
		return optionName;
	}

	public String getOptionDescription() {
		return optionDescription;
	}
	
	protected FieldEditor setTooltipAndReturn(FieldEditor fieldEditor, Composite parent) {
		fieldEditor.getLabelControl(parent).setToolTipText(
				getOptionDescription());
		return fieldEditor;
	}
	
	/**
	 * Get the field editor for this option
	 * @return
	 */
	abstract FieldEditor getFieldEditor(Composite parent);
	
	abstract Object getValue(IPreferenceStore preferenceStore);
}

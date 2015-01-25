package net.github.clang_formateclipse;

import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class ClangFormatFileFieldEditor extends FileFieldEditor {
	public ClangFormatFileFieldEditor(String name, String labelText,
			Composite parent) {
		super(name, labelText, parent);
		setFileExtensions(new String[]{".clang-format", ""});
	}
}

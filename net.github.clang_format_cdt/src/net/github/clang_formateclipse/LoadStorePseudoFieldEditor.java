package net.github.clang_formateclipse;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class LoadStorePseudoFieldEditor extends FieldEditor {

	private Button storeFileButton;
	private Button loadFileButton;
	
	public LoadStorePseudoFieldEditor(Composite parent) {
		super("", "", parent);
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {
		GridDataFactory.generate(loadFileButton, numColumns - 1, 1);
	}
	
	public void addStoreListener(SelectionListener listener) {
		storeFileButton.addSelectionListener(listener);
	}
	
	protected void addLoadListener(SelectionListener listener) {
		loadFileButton.addSelectionListener(listener);
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		storeFileButton = new Button(parent, SWT.PUSH);
		storeFileButton.setText("Store .clang-format file");
		
		loadFileButton = new Button(parent, SWT.PUSH);
		loadFileButton.setText("Load .clang-format file");
		adjustForNumColumns(numColumns);
	}

	@Override
	public void setEnabled(boolean enabled, Composite parent) {
		storeFileButton.setEnabled(enabled);
		loadFileButton.setEnabled(enabled);
	}

	@Override
	protected void doLoad() {
	}

	@Override
	protected void doLoadDefault() {
	}

	@Override
	protected void doStore() {
	}

	@Override
	public int getNumberOfControls() {
		return 2;
	}

}

package net.github.clang_formateclipse;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(Preferences.STYLE_CHOICE, Preferences.STYLE_NONE);
		store.setDefault(Preferences.CLANG_FORMAT_PATH, "/usr/bin/clang-format");
		store.setDefault(Preferences.ALLOW_ALL_PARAMETERS_OF_DECLARATION_ON_NEXT_LINE, Preferences.NONE);
		store.setDefault(Preferences.ALIGN_ESCAPED_NEWLINES_LEFT, Preferences.NONE);
		store.setDefault(Preferences.ALLOW_SHORT_LOOPS_ON_A_SINGLE_LINE, Preferences.NONE);
		store.setDefault(Preferences.ALWAYS_BREAK_BEFORE_MULTILINE_STRINGS, Preferences.NONE);
		store.setDefault(Preferences.ALWAYS_BREAK_TEMPLATE_DECLARATIONS, Preferences.NONE);
		store.setDefault(Preferences.BIN_PACK_PARAMETERS, Preferences.NONE);
	}

}

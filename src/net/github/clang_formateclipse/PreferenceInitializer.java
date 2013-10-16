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
		store.setDefault(Preferences.BASED_ON_STYLE, Preferences.NONE);
		store.setDefault(Preferences.CLANG_FORMAT_PATH, "/usr/bin/clang-format");
		store.setDefault(Preferences.ALLOW_ALL_PARAMETERS_OF_DECLARATION_ON_NEXT_LINE, Preferences.NONE);
		store.setDefault(Preferences.ALIGN_ESCAPED_NEWLINES_LEFT, Preferences.NONE);
		store.setDefault(Preferences.ALLOW_SHORT_LOOPS_ON_A_SINGLE_LINE, Preferences.NONE);
		store.setDefault(Preferences.ALWAYS_BREAK_BEFORE_MULTILINE_STRINGS, Preferences.NONE);
		store.setDefault(Preferences.ALWAYS_BREAK_TEMPLATE_DECLARATIONS, Preferences.NONE);
		store.setDefault(Preferences.BIN_PACK_PARAMETERS, Preferences.NONE);
		store.setDefault(Preferences.BREAK_BEFORE_BINARY_OPERATORS, Preferences.NONE);
		store.setDefault(Preferences.BREAK_BEFORE_BRACES, Preferences.NONE);
		store.setDefault(Preferences.BREAK_CONSTRUCTOR_INITIALIZERS_BEFORE_COMMA, Preferences.NONE);
		store.setDefault(Preferences.CONSTRUCTOR_INITIALIZER_ALL_ON_ONE_LINE_OR_ONE_PER_LINE, Preferences.NONE);
		store.setDefault(Preferences.CONSTRUCTOR_INITIALIZER_INDENT_WIDTH, Preferences.NONE);
		store.setDefault(Preferences.CPP11_BRACED_LIST_STYLE, Preferences.NONE);
		store.setDefault(Preferences.DERIVE_POINTER_BINDING, Preferences.NONE);
		store.setDefault(Preferences.INDENT_FUNCTION_DECLARATION_AFTER_TYPE, Preferences.NONE);
		//store.setDefault(Preferences.PENALTY_BREAK_COMMENT, 0);
		store.setDefault(Preferences.POINTER_BINDS_TO_TYPE, Preferences.NONE);
		store.setDefault(Preferences.SPACES_IN_CSTYLE_CAST_PARENTHESES, Preferences.NONE);
		store.setDefault(Preferences.SPACES_IN_PARENTHESES, Preferences.NONE);
		store.setDefault(Preferences.STANDARD, Preferences.NONE);
	}

}

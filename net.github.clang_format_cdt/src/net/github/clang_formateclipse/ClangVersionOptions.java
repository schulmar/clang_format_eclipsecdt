package net.github.clang_formateclipse;

public abstract class ClangVersionOptions {

	static public ClangVersionOptions getOptionsForVersion(ClangVersion version)
			throws UnsupportedClangVersion {
		// so far we only support clang 3.x versions
		if (version.getMajor() == 3) {
			if (version.getMinor() < 3)
				// version below 3.3 -> no clang format
				throw new UnsupportedClangVersion(version);
			switch (version.getMinor()) {
			case 3:
			case 4:
				return new Clang_3_4_FormatOptions();
			case 5:
				return new Clang_3_5_FormatOptions();
				// for higher options assume clang 3.5 capabilities
			default:
				return new Clang_3_5_FormatOptions();
			}
		} else {
			// version below 3.3 -> no clang format
			throw new UnsupportedClangVersion(version);
		}
	}

	abstract FormatOption[] getFormatOptions();

	abstract String[][] getStyles();
}

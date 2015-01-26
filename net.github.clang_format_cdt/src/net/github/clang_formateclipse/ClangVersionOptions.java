package net.github.clang_formateclipse;

public abstract class ClangVersionOptions {

	static public String[] supportedVersions() {
		return new String[] { new ClangVersion(3, 3).toString(),
				new ClangVersion(3, 4).toString(),
				new ClangVersion(3, 5).toString(),
				new ClangVersion(3, 6).toString(),
				new ClangVersion(3, 7).toString() };
	}
	
	static public ClangVersionOptions getOptionsForVersion(ClangVersion version)
			throws UnsupportedClangVersion {
		// so far we only support clang 3.x versions
		if (version.getMajor() == 3) {
			if (version.getMinor() < 3)
				// version below 3.3 -> no clang format
				throw new UnsupportedClangVersion(version);
			switch (version.getMinor()) {
			case 3:
				return null;
			case 4:
				return new Clang_3_4_FormatOptions();
			case 5:
				return new Clang_3_5_FormatOptions();
			case 6:
				return new Clang_3_6_FormatOptions();
			case 7:
				// for higher options assume clang 3.7 capabilities
			default:
				return new Clang_3_7_FormatOptions();
			}
		} else {
			// version below 3.3 -> no clang format
			throw new UnsupportedClangVersion(version);
		}
	}

	abstract FormatOption[] getFormatOptions();

	abstract String[][] getStyles();
}

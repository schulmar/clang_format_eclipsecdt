package net.github.clang_formateclipse;

public class Clang_3_3_FormatOptions extends ClangVersionOptions {

	public Clang_3_3_FormatOptions() {
	}

	@Override
	FormatOption[] getFormatOptions() {
		// 3.3 does not yet support custom format options, only preset styles
		return new FormatOption[] {};
	}

	@Override
	String[][] getStyles() {
		return new String[][] { { "LLVM", "LLVM" }, { "Google", "Google" },
				{ "Chromium", "Chromium" }};
	}

}

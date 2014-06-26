package net.github.clang_formateclipse;

public class UnsupportedClangVersion extends Exception {
	
	private ClangVersion version;
	
	public UnsupportedClangVersion(ClangVersion version) {
		this.version = version;
	}

	public ClangVersion getVersion() {
		return version;
	}
}

package net.github.clang_formateclipse;

public class UnsupportedClangVersion extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClangVersion version;
	
	public UnsupportedClangVersion(ClangVersion version) {
		this.version = version;
	}

	public ClangVersion getVersion() {
		return version;
	}
}

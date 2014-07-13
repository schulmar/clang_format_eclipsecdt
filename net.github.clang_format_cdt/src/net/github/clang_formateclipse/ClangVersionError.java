package net.github.clang_formateclipse;

public class ClangVersionError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClangVersionError(String message) {
		super(message);
	}

	public ClangVersionError(String message, Throwable cause) {
		super(message, cause);
	}
}

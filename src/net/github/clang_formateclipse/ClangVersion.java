package net.github.clang_formateclipse;

public class ClangVersion {

	private int major;
	private int minor;
	
	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public ClangVersion(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

}

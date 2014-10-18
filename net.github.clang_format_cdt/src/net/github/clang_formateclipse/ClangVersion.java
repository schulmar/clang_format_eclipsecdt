package net.github.clang_formateclipse;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClangVersion {
	private int major;
	private int minor;

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClangVersion) {
			ClangVersion otherVersion = (ClangVersion) obj;
			return otherVersion.getMajor() == this.major
					&& otherVersion.getMinor() == this.minor;
		}
		return super.equals(obj);
	}

	public ClangVersion(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	static public ClangVersion fromVersionString(String versionString) throws ClangVersionError {
		Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)");
		Matcher matcher = pattern.matcher(versionString);
		if(matcher.find()) {
			return new ClangVersion(Integer.parseInt(matcher.group(1)),
					Integer.parseInt(matcher.group(2)));
		}
		throw new ClangVersionError(String.format(
				"Could not find clang-format version string in \"%s\"",
				versionString));
	}
	
	public String toString() {
		return String.format("%d.%d", major, minor);
	}
	
	/**
	 * Determine the clang-format version of an executable
	 * 
	 * @return The ClangVersion of the executable
	 * @throws ClangVersionError
	 */
	static public ClangVersion fromExecutable(String pathToExecutable)
			throws ClangVersionError {
		Runtime RT = Runtime.getRuntime();
		String args[] = { pathToExecutable, "--version" };
		Process subProc = null;
		try {
			subProc = RT.exec(args);
		} catch (IOException e) {
			throw new ClangVersionError("Could not exec command", e);
		}
		// we don't have anything to say!
		try {
			subProc.getOutputStream().close();
		} catch (IOException e) {
			throw new ClangVersionError("Could not close output stream", e);
		}
		// the program ran correctly, try to parse it
		Scanner scanner = null;
		try {
			scanner = new Scanner(subProc.getInputStream());
			Pattern pattern = Pattern
					.compile("(LLVM version|clang-format version)\\s+([0-9]+.[0-9]+)");
			if (scanner.findWithinHorizon(pattern, 0) != null) {
				MatchResult match = scanner.match();
				return fromVersionString(match.group(2));
			}
			throw new ClangVersionError(
					"Could not find clang-format version string in output");
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}
}

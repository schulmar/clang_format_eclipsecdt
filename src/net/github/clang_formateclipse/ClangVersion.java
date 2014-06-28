package net.github.clang_formateclipse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.MatchResult;
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
		/*
		// we don't expect any errors but when there are some then throw them
		int exitValue = subProc.exitValue();
		if (exitValue != 0) {
			StringBuilder errorStringBuilder = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(
					subProc.getErrorStream());
			BufferedReader br = new BufferedReader(reader);
			try {
				String line;
				while ((line = br.readLine()) != null) {
					errorStringBuilder.append(line);
					errorStringBuilder.append("\n");
				}
			} catch (IOException exception) {
				Logger.logError(exception);
			}
			throw new ClangVersionError(String.format(
					"Got error exit = %i: %s", exitValue,
					errorStringBuilder.toString()));
		}
		*/
		// the program ran correctly, try to parse it
		Scanner scanner = null;
		try {
			scanner = new Scanner(subProc.getInputStream());
			Pattern pattern = Pattern
					.compile("clang-format version ([0-9]+)\\.([0-9]+)");
			if (scanner.findWithinHorizon(pattern, 0) != null) {
				MatchResult match = scanner.match();
				try {
					return new ClangVersion(Integer.getInteger(match.group(1)),
							Integer.getInteger(match.group(2)));
				} catch (NullPointerException e) {
					throw new ClangVersionError(
							"Could not find clang-format version string in output");		
				}
			}
			throw new ClangVersionError(
					"Could not find clang-format version string in output");
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}
}

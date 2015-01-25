package net.github.clang_formateclipse;

public class Preferences {
	public static final String NONE = "";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	public static final String VERSION = "ClangVersion";
	
	public static final String CLANG_FORMAT_PATH = "clangFormatPath";
	
	public static final String STYLE_OPTION = "style";
	public static final String CUSTOM_STYLE = "Custom";
	public static final String ABSOLUTE_CLANG_FORMAT_FILE_STYLE = ".clang-format-file.absolute";
	public static final String ABSOLUTE_CLANG_FORMAT_FILE_STYLE_DESCRIPTION = "Custom .clang-format file";
	// TODO: allow for .clang-format files in the project directories to be picked up
	//public static final String RELATIVE_CLANG_FORMAT_FILE_STYLE = ".clang-format-file.relative";
	//public static final String RELATIVE_CLANG_FORMAT_FILE_STYLE_DESCRIPTION = "Relative .clang-format";
	
	public static final String ABSOLUTE_CLANG_FORMAT_FILE_PATH_PROPERTY = ".clang-format-file.absolute.path"; 
	public static final String ABSOLUTE_CLANG_FORMAT_FILE_PATH_LABEL = "Path to the .clang-format file";
	
	
	public static final ClangVersion DEFAULT_CLANG_VERSION = new ClangVersion(3, 4);
}

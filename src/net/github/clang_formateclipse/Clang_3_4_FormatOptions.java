package net.github.clang_formateclipse;

public class Clang_3_4_FormatOptions extends ClangVersionOptions {

	public Clang_3_4_FormatOptions() {
	}

	@Override
	FormatOption[] getFormatOptions() {
		return new FormatOption[] {
				new SingleElementSelectionFormatOption(
						"BasedOnStyle",
						"The style used for all options not specifically set in the configuration.",
						new String[][] { { "LLVM", Preferences.STYLE_LLVM },
								{ "Google", Preferences.STYLE_GOOGLE },
								{ "Chromium", Preferences.STYLE_CHROMIUM },
								{ "Mozilla", Preferences.STYLE_MOZILLA } }),
				new IntegerFormatOption("AccessModifierOffset",
						"The extra indent or outdent of access modifiers, e.g. public:."),
				new BooleanFormatOption(
						"AlignEscapedNewlinesLeft",
						"If true, aligns escaped newlines as far left as possible. Otherwise puts them into the right-most column."),
				new BooleanFormatOption("AlignTrailingComments",
						"If true, aligns trailing comments."),
				new BooleanFormatOption(
						"AllowAllParametersOfDeclarationOnNextLine",
						"Allow putting all parameters of a function declaration onto the next line even if BinPackParameters is false."),
				new BooleanFormatOption("AllowShortIfStatementsOnASingleLine",
						"If true, if (a) return; can be put on a single line."),
				new BooleanFormatOption("AllowShortLoopsOnASingleLine",
						"If true, while (true) continue; can be put on a single line."),
				new BooleanFormatOption("AlwaysBreakBeforeMultilineStrings",
						"If true, always break before multiline string literals."),
				new BooleanFormatOption("AlwaysBreakTemplateDeclarations",
						"If true, always break after the template<...> of a template declaration."),
				new BooleanFormatOption(
						"BinPackParameters",
						"If false, a function call’s or function definition’s parameters will either all be on the same line or will have one line each."),
				new BooleanFormatOption("BreakBeforeBinaryOperators",
						"If true, binary operators will be placed after line breaks."),
				new SingleElementSelectionFormatOption(
						"BreakBeforeBraces",
						"The brace breaking style to use.",
						new String[][] {
								{
										"[Attach] Always attach braces to surrounding context.",
										"Attach" },
								{
										"[Linux] Like Attach, but break before braces on function, namespace and class definitions.",
										"Linux" },
								{
										"[Stroustrup] Like Attach, but break before function definitions.",
										"Stroustrup" },
								{ "[Allman] Always break before braces.",
										"Allman" } }),
				new BooleanFormatOption(
						"BreakConstructorInitializersBeforeComma",
						"Always break constructor initializers before commas and align the commas with the colon."),
				new UnsignedFormatOption("ColumnLimit",
						"The column limit. (0 = no limit)"),
				new BooleanFormatOption(
						"ConstructorInitializerAllOnOneLineOrOnePerLine",
						"If the constructor initializers don’t fit on a line, put each initializer on its own line."),
				new UnsignedFormatOption(
						"ConstructorInitializerIndentWidth",
						"The number of characters to use for indentation of constructor initializer lists."),
				new BooleanFormatOption("Cpp11BracedListStyle",
						"If true, format braced lists as best suited for C++11 braced lists."),
				new BooleanFormatOption("DerivePointerBinding",
						"If true, analyze the formatted file for the most common binding."),
				new BooleanFormatOption(
						"ExperimentalAutoDetectBinPacking",
						"If true, clang-format detects whether function calls and definitions are formatted with one parameter per line."),
				new BooleanFormatOption("IndentCaseLabels",
						"Indent case labels one level from the switch statement."),
				new BooleanFormatOption(
						"IndentFunctionDeclarationAfterType",
						"If true, indent when breaking function declarations which are not also definitions after the type."),
				new UnsignedFormatOption("IndentWidth",
						"The number of columns to use for indentation."),
				new UnsignedFormatOption("MaxEmptyLinesToKeep",
						"The maximum number of consecutive empty lines to keep."),
				new SingleElementSelectionFormatOption(
						"NamespaceIndentation",
						"The indentation used for namespaces.",
						new String[][] {
								{ "[None] Don’t indent in namespaces.", "None" },
								{
										"[Inner] Indent only in inner namespaces (nested in other namespaces).",
										"Inner" },
								{ "[All] Indent in all namespaces.", "All" } }),
				new BooleanFormatOption(
						"ObjCSpaceBeforeProtocolList",
						"Add a space in front of an Objective-C protocol list, i.e. use Foo <Protocol> instead of Foo<Protocol>."),
				new UnsignedFormatOption("PenaltyBreakComment",
						"The penalty for each line break introduced inside a comment."),
				new UnsignedFormatOption("PenaltyBreakFirstLessLess",
						"The penalty for breaking before the first <<."),
				new UnsignedFormatOption("PenaltyBreakString",
						"The penalty for each line break introduced inside a string literal."),
				new UnsignedFormatOption("PenaltyExcessCharacter",
						"The penalty for each character outside of the column limit."),
				new UnsignedFormatOption("PenaltyReturnTypeOnItsOwnLine",
						"Penalty for putting the return type of a function onto its own line."),
				new BooleanFormatOption("PointerBindsToType",
						"Set whether & and * bind to the type (as opposed to the variable)."),
				new BooleanFormatOption("SpaceAfterControlStatementKeyword",
						"If true, spaces will be inserted between ‘for’/’if’/’while’/... and ‘(‘."),
				new BooleanFormatOption("SpaceBeforeAssignmentOperators",
						"If false, spaces will be removed before assignment operators."),
				new BooleanFormatOption("SpaceInEmptyParentheses",
						"If false, spaces may be inserted into ‘()’."),
				new UnsignedFormatOption("SpacesBeforeTrailingComments",
						"The number of spaces to before trailing line comments."),
				new BooleanFormatOption("SpacesInCStyleCastParentheses",
						"If false, spaces may be inserted into C style casts."),
				new BooleanFormatOption("SpacesInParentheses",
						"If true, spaces will be inserted after every ‘(‘ and before every ‘)’."),
				new SingleElementSelectionFormatOption(
						"Standard",
						"Format compatible with this standard, e.g. use A<A<int> > instead of A<A<int>> for LS_Cpp03.",
						new String[][] {
								{ "[Cpp03] Use C++03-compatible syntax.",
										"Cpp03" },
								{
										"[Cpp11] Use features of C++11 (e.g. A<A<int>> instead of A<A<int> >).",
										"Cpp11" },
								{
										"[Auto] Automatic detection based on the input.",
										"Auto" }

						}),
				new UnsignedFormatOption("TabWidth",
						"The number of columns used for tab stops."),
				new SingleElementSelectionFormatOption(
						"UseTab",
						"The way to use tab characters in the resulting file.",
						new String[][] {
								{ "[Never] Never use tab.", "Never" },
								{
										"[ForIndentation] Use tabs only for indentation.",
										"ForIndentation" },
								{
										"[Always] Use tabs whenever we need to fill whitespace that spans at least from one tab stop to the next one.",
										"Always" } }) };
	}

	@Override
	String[] getStyles() {
		return new String[] { "LLVM", "Google", "Chromium", "Mozilla", "WebKit" };
	}

}

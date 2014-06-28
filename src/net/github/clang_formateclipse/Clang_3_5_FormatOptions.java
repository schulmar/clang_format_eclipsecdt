package net.github.clang_formateclipse;

public class Clang_3_5_FormatOptions extends ClangVersionOptions {

	public Clang_3_5_FormatOptions() {
	}

	@Override
	FormatOption[] getFormatOptions() {
		return new FormatOption[] {
				new SingleElementSelectionFormatOption(
						"BasedOnStyle",
						"The style used for all options not specifically set in the configuration.",
						new String[][] {
								{
										"[LLVM] A style complying with the LLVM coding standards",
										"LLVM" },
								{
										"[Google] A style complying with Google’s C++ style guide",
										"Google" },
								{
										"[Chromium] A style complying with Chromium’s style guide",
										"Chromium" },
								{
										"[Mozilla] A style complying with Mozilla’s style guide",
										"Mozilla" },
								{
										"[WebKit] A style complying with WebKit’s style guide",
										"WebKit" } }),
				new IntegerFormatOption("AccessModifierOffset",
						"The extra indent or outdent of access modifiers, e.g. public:."),
				new BooleanFormatOption(
						"IntAlignEscapedNewlinesLeft",
						"If true, aligns escaped newlines as far left as possible. Otherwise puts them into the right-most column."),
				new BooleanFormatOption("AlignTrailingComments",
						"If true, aligns trailing comments."),
				new BooleanFormatOption(
						"AllowAllParametersOfDeclarationOnNextLine",
						"Allow putting all parameters of a function declaration onto the next line even if BinPackParameters is false."),
				new BooleanFormatOption("AllowShortBlocksOnASingleLine",
						"Allows contracting simple braced statements to a single line."),
				new SingleElementSelectionFormatOption(
						"AllowShortFunctionsOnASingleLine",
						"Dependent on the value, int f() { return 0; } can be put on a single line.",
						new String[][] {
								{
										"[None] Never merge functions into a single line.",
										"None" },
								{
										"[Inline] Only merge functions defined inside a class.",
										"Inline" },

								{
										"[All] Merge all functions fitting on a single line.",
										"All" } }),

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
										"Allman" },
								{
										"[GNU] Always break before braces and add an extra level of indentation to braces of control statements, not to those of class, function or other definitions.",
										"GNU" }, }),
				new BooleanFormatOption("BreakBeforeTernaryOperators",
						"If true, ternary operators will be placed after line breaks."),
				new BooleanFormatOption(
						"BreakConstructorInitializersBeforeComma",
						"Always break constructor initializers before commas and align the commas with the colon."),
				new UnsignedFormatOption("ColumnLimit",
						"The column limit. (0 = No limit)"),
				new StringFormatOption(
						"CommentPragmas",
						"A regular expression that describes comments with special meaning, which should not be split into lines or otherwise changed."),
				new BooleanFormatOption(
						"ConstructorInitializerAllOnOneLineOrOnePerLine",
						"If the constructor initializers don’t fit on a line, put each initializer on its own line."),
				new UnsignedFormatOption(
						"ConstructorInitializerIndentWidth",
						"The number of characters to use for indentation of constructor initializer lists."),
				new UnsignedFormatOption("ContinuationIndentWidth",
						"Indent width for line continuations."),
				new BooleanFormatOption("Cpp11BracedListStyle",
						"If true, format braced lists as best suited for C++11 braced lists."),
				new BooleanFormatOption(
						"DerivePointerAlignment",
						"If true, analyze the formatted file for the most common alignment of & and *. PointerAlignment is then used only as fallback."),
				new BooleanFormatOption("DisableFormat",
						"Disables formatting at all."),
				new BooleanFormatOption(
						"ExperimentalAutoDetectBinPacking",
						"If true, clang-format detects whether function calls and definitions are formatted with one parameter per line."),
				// TODO: new StringFormatOption("ForEachMacros",
				// "A vector of macros that should be interpreted as foreach loops instead of as function calls."),
				new BooleanFormatOption("IndentCaseLabels",
						"Indent case labels one level from the switch statement."),
				new BooleanFormatOption(
						"IndentFunctionDeclarationAfterType",
						"If true, indent when breaking function declarations which are not also definitions after the type."),
				new UnsignedFormatOption("IndentWidth",
						"The number of columns to use for indentation."),
				new BooleanFormatOption("KeepEmptyLinesAtTheStartOfBlocks",
						"If true, empty lines at the start of blocks are kept."),
				new SingleElementSelectionFormatOption(
						"Language",
						"Language, this format style is targeted at.",
						new String[][] {
								{ "[None] Do not use.", "None" },
								{
										"[Cpp] Should be used for C, C++, ObjectiveC, ObjectiveC++.",
										"Cpp" },
								{
										"[JavaScript] Should be used for JavaScript.",
										"JavaScript" },
								{
										"[Proto] Should be used for Protocol Buffers (https://developers.google.com/protocol-buffers/).",
										"Proto" } }),
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
						"ObjCSpaceAfterProperty",
						"Add a space after @property in Objective-C, i.e. use \\@property (readonly) instead of \\@property(readonly)."),
				new BooleanFormatOption(
						"ObjCSpaceBeforeProtocolList",
						"Add a space in front of an Objective-C protocol list, i.e. use Foo <Protocol> instead of Foo<Protocol>."),
				new UnsignedFormatOption(
						"PenaltyBreakBeforeFirstCallParameter",
						"The penalty for breaking a function call after “call(”."),
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
				new SingleElementSelectionFormatOption(
						"PointerAlignment",
						"Pointer and reference alignment style.",
						new String[][] {
								{ "[Left] Align pointer to the left.", "Left" },
								{ "[Right] Align pointer to the right.",
										"Right" },
								{ "[Middle] Align pointer in the middle.",
										"Middle" } }),
				new BooleanFormatOption("SpaceBeforeAssignmentOperators",
						"If false, spaces will be removed before assignment operators."),
				new SingleElementSelectionFormatOption(
						"SpaceBeforeParens",
						"Defines in which cases to put a space before opening parentheses.",
						new String[][] {
								{
										"[Never] Never put a space before opening parentheses.",
										"Never" },
								{
										"[ControlStatements] Put a space before opening parentheses only after control statement keywords (for/if/while...).",
										"ControlStatement" },
								{
										"[Always] Always put a space before opening parentheses, except when it’s prohibited by the syntax rules (in function-like macro definitions) or when determined by other style rules (after unary operators, opening parentheses, etc.",
										"Always" } }),
				new BooleanFormatOption("SpaceInEmptyParentheses",
						"If true, spaces may be inserted into ‘()’."),
				new UnsignedFormatOption("SpacesBeforeTrailingComments",
						"The number of spaces before trailing line comments (// - comments)."),
				new BooleanFormatOption(
						"SpacesInAngles",
						"If true, spaces will be inserted after ‘<’ and before ‘>’ in template argument lists"),
				new BooleanFormatOption("SpacesInCStyleCastParentheses",
						"If true, spaces may be inserted into C style casts."),
				new BooleanFormatOption(
						"SpacesInContainerLiterals",
						"If true, spaces are inserted inside container literals (e.g. ObjC and Javascript array and dict literals)."),
				new BooleanFormatOption("SpacesInParentheses",
						"If true, spaces will be inserted after ‘(‘ and before ‘)’."),
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
										"Auto" } }),
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
										"Always" } })

		};
	}

	@Override
	String[][] getStyles() {
		return new String[][] { { "LLVM", "LLVM" }, { "Google", "Google" },
				{ "Chromium", "Chromium" }, { "Mozilla", "Mozilla" },
				{ "WebKit", "WebKit" } };
	}

}
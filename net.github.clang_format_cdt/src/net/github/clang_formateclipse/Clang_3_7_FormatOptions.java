package net.github.clang_formateclipse;

public class Clang_3_7_FormatOptions extends ClangVersionOptions {

	public Clang_3_7_FormatOptions() {
	}

	@Override
	FormatOption[] getFormatOptions() {
		return new FormatOption[] {
				new SingleElementSelectionFormatOption(
						"BasedOnStyle",
						"The style used for all options not specifically set in the configuration.",
						new String[] { "LLVM", "Google", "Chromium", "Mozilla",
								"WebKit" }),
				new IntegerFormatOption("AccessModifierOffset",
						"The extra indent or outdent of access modifiers, e.g. public:."),
				new BooleanFormatOption(
						"AlignAfterOpenBracket",
						"If true, horizontally aligns arguments after an open bracket.\n\n"
								+ "This applies to round brackets (parentheses), angle brackets and square brackets. "
								+ "This will result in formattings like code someLongFunction(argument1, argument2)"),
				new BooleanFormatOption(
						"AlignEscapedNewlinesLeft",
						"If true, aligns escaped newlines as far left as possible. Otherwise puts them into the right-most column."),
				new BooleanFormatOption("AlignOperands",
						"If true, horizontally align operands of binary and ternary expressions."),	
				new BooleanFormatOption("AlignTrailingComments",
						"If true, aligns trailing comments."),
				new BooleanFormatOption(
						"AllowAllParametersOfDeclarationOnNextLine",
						"Allow putting all parameters of a function declaration onto the next line even if BinPackParameters is false."),
				new BooleanFormatOption("AllowShortBlocksOnASingleLine",
						"Allows contracting simple braced statements to a single line."),
				new BooleanFormatOption("AllowShortCaseLabelsOnASingleLine",
						"If true, short case labels will be contracted to a single line."),
				new SingleElementSelectionFormatOption(
						"AllowShortFunctionsOnASingleLine",
						"Dependent on the value, int f() { return 0; } can be put on a single line.",
						new String[] { "None", "Inline", "Empty", "All" }),
				new BooleanFormatOption("AllowShortIfStatementsOnASingleLine",
						"If true, if (a) return; can be put on a single line."),
				new BooleanFormatOption("AllowShortLoopsOnASingleLine",
						"If true, while (true) continue; can be put on a single line."),
				new BooleanFormatOption("AlwaysBreakAfterDefinitionReturnType",
						"If true, always break after function definition return types."),
				new BooleanFormatOption("AlwaysBreakBeforeMultilineStrings",
						"If true, always break before multiline string literals."),
				new BooleanFormatOption("AlwaysBreakTemplateDeclarations",
						"If true, always break after the template<...> of a template declaration."),
				new BooleanFormatOption(
						"BinPackArguments",
						"If false, a function call’s arguments will either be all on the same line or will have one line each"),
				new BooleanFormatOption(
						"BinPackParameters",
						"If false, a function call’s or function definition’s parameters will either all be on the same line or will have one line each."),
				new SingleElementSelectionFormatOption(
						"BreakBeforeBinaryOperators",
						"If true, binary operators will be placed after line breaks.",
						new String[] { "None", "NonAssignment", "All" }),
				new SingleElementSelectionFormatOption("BreakBeforeBraces",
						"The brace breaking style to use.", new String[] {
								"Attach", "Linux", "Stroustrup", "Allman",
								"GNU" }),
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
				new StringListFormatOption(
						"ForEachMacros",
						"A vector of macros that should be interpreted as foreach loops instead of as function calls."),
				new BooleanFormatOption("IndentCaseLabels",
						"Indent case labels one level from the switch statement."),
				new UnsignedFormatOption("IndentWidth",
						"The number of columns to use for indentation."),
				new BooleanFormatOption("IndentWrappedFunctionNames",
						"Indent if a function definition or declaration is wrapped after the type."),
				new BooleanFormatOption("KeepEmptyLinesAtTheStartOfBlocks",
						"If true, empty lines at the start of blocks are kept."),
				// TODO: Documented but not implemented?
				/*
				 * new SingleElementSelectionFormatOption( "Language",
				 * "Language, this format style is targeted at.", new String[][]
				 * { { "[None] Do not use.", "None" }, {
				 * "[Cpp] Should be used for C, C++, ObjectiveC, ObjectiveC++.",
				 * "Cpp" }, { "[JavaScript] Should be used for JavaScript.",
				 * "JavaScript" }, {
				 * "[Proto] Should be used for Protocol Buffers (https://developers.google.com/protocol-buffers/)."
				 * , "Proto" } }),
				 */
				new UnsignedFormatOption("MaxEmptyLinesToKeep",
						"The maximum number of consecutive empty lines to keep."),
				new SingleElementSelectionFormatOption("NamespaceIndentation",
						"The indentation used for namespaces.", new String[] {
								"None", "Inner", "All" }),
				// TODO: we only use C/++ ?
				new UnsignedFormatOption("ObjCBlockIndentWidth",
						"The number of characters to use for indentation of ObjC blocks."),
				// TODO: we only use C/++ ?
				new BooleanFormatOption(
						"ObjCSpaceAfterProperty",
						"Add a space after @property in Objective-C, i.e. use \\@property (readonly) instead of \\@property(readonly)."),
				// TODO: we only use C/++ ?
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
				new SingleElementSelectionFormatOption("PointerAlignment",
						"Pointer and reference alignment style.", new String[] {
								"Left", "Right", "Middle" }),
				new BooleanFormatOption("SpaceAfterCStyleCast",
						"If true, a space may be inserted after C style casts."),
				new BooleanFormatOption("SpaceBeforeAssignmentOperators",
						"If false, spaces will be removed before assignment operators."),
				new SingleElementSelectionFormatOption(
						"SpaceBeforeParens",
						"Defines in which cases to put a space before opening parentheses.",
						new String[] { "Never", "ControlStatements", "Always" }),
				new BooleanFormatOption("SpaceInEmptyParentheses",
						"If true, spaces may be inserted into ‘()’."),
				new UnsignedFormatOption(
						"SpacesBeforeTrailingComments",
						"The number of spaces before trailing line comments (// - comments).\n\n"
								+ "This does not affect trailing block comments (/**/ - comments) "
								+ "as those commonly have different usage patterns and a number of special cases."),
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
				new BooleanFormatOption("SpacesInSquareBrackets",
						"If true, spaces will be inserted after ‘[‘ and before ‘]’."),
				new SingleElementSelectionFormatOption(
						"Standard",
						"Format compatible with this standard, e.g. use A<A<int> > instead of A<A<int>> for LS_Cpp03.",
						new String[] { "Cpp03", "Cpp11", "Auto" }),
				new UnsignedFormatOption("TabWidth",
						"The number of columns used for tab stops."),
				new SingleElementSelectionFormatOption("UseTab",
						"The way to use tab characters in the resulting file.",
						new String[] { "Never", "ForIndentation", "Always" })

		};
	}

	@Override
	String[][] getStyles() {
		return new String[][] { { "LLVM", "LLVM" }, { "Google", "Google" },
				{ "Chromium", "Chromium" }, { "Mozilla", "Mozilla" },
				{ "WebKit", "WebKit" } };
	}

}

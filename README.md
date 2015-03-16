clang_format_eclipsecdt
=======================

A CDT formatter using `clang-format`.
Requires version 3.4 or higher. (Must support the options `-style=file`, `-assume-filename` and `-output-replacements-xml`)

Installation
============

You can get the current version from the update site: 
http://schulmar.github.io/clang_format_eclipsecdt/updatesite/

Usage
=====

Once the plugin is installed you can configure the path to the `clang-format` executable under `Preferences -> Clang Format`.

After selecting `Clang Format Plugin` as the formatter under `Preferences -> C/C++ -> Code Style -> Formatter` (instead of "[built-in]"),
Eclipse CDT will use `clang-format` as its formatter when invoked with <kbd>CTRL</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd>.
(The preview may trigger errors which stem from the fact that the preview code is not a file. You can ignore them.)

`clang-format` will search upwards for a `.clang-format` or `_clang-format` file in the directories containing the currently edited file.
This means workspace format settings can be achieved by putting a `.clang-format` file into the workspace directory, and per project (override) settings by putting an additional `.clang-format` file in the project directory.

Problems
========

When the formatter does not seem to work, have a look at the `Error log`, where problems will be reported.

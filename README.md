clang_format_eclipsecdt
=======================

A CDT formatter using clang-format.
The current implementation is best suited for clang 3.4 and 3.5

Installation
============

You can get the current version from the update site: 
http://schulmar.github.io/clang_format_eclipsecdt/updatesite/

Usage
=====

Once the plugin is installed you can configure the path to the `clang-format` executable and the format options under Preferences -> Clang Format. (Make sure to save and reopen the preferences window after changing the clang-format executable so that the plugin displays the correct options for your LLVM version)

After selecting "Clang Format Plugin" as the formatter under Preferences -> C/C++ -> Code Style -> Formatter (instead of "[built-in]") Eclipse CDT will use clang-format as its formatter when invoked with <kbd>CTRL</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd>


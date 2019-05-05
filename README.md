# Manga Sorter
Simple command line program to sort manga chapters into volumes

### How to use
Run jar in folder containing chapters.
Chapters need to be folders named with the following naming scheme:
* 001 - Vol.01 Ch.001 Some random name
* 002 - Vol.01 Ch.002
* 003 - Vol.01 Ch.003.5
* 004 - Vol.02 Ch.001

This becomes:
* FolderNameContainingChapters - Volume 01
  * 001 Some page from chapter 001 - 1.jpg
  * 001 Some page from chapter 001 - 2.jpg
  * 002 Some page from chapter 002 - 1.jpg
  * ...

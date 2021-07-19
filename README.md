# CSIA-FileRenamer

This is a File Renamer written for my IB Computer Science course IA (kinda like a final project).

In which you can rename lots of files easily with a set of rules you made. 

This program is written in Java 11 (it was java 8 originally but I've migrated it to Java 11) with Java FX.



#### System Requirement

- Java 8 or 11. Any version higher than 11 should work but I've never tested it... who knows lol



#### Release?

You can find .jar files (executable) in release.



#### How to Run it?

The one with Java 11 can be run by double-click.

The one with Java 8 has to be run using the following command:

~~~~
java -jar <the jar file>
~~~~



#### How to use it?

Discover it **YOURSELF!** 

You're a smart guy and I believe you will automatically understand my software by intuition.



#### What can it do?

It can rename lots of file according to a set of rules. The rules are listed below.

(These are copied from my planning document, you can find it in documents/Crit_A_Planning.pdf)

(p.s. those rules are written quite long ago and my English wasn't good back then... so... you know XD)

Rules

- Replace some text "x" into some text "y" in the file's name
- Delete characters based on digit
- Read some tag information of the music files and add it into the file name. [Track, Album, Artist, Title]
- Insert some text into somewhere in file name.

Some other functions

- Export the naming rule set to a file, and the user can load the rules in the future.
- Multiple rules can be assigned and executed in order. For example, the user can enter a series of rules to operate the file name like “replace x with y -> delete the first 3 characters -> insert XXX” 
- User can preview those rules, but stupid may not be able to understand those rules XD.
- Some other functions are for you to discover!
- Yey

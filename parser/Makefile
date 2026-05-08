default: scanner parser

	javac *.java

scanner:
	jflex scanner.jflex
	
parser:
	java java_cup.Main parser.cup

compile:
	javac *.java
	
clean:
	rm -fr parser.java Yylex.java sym.java
	rm -vfr *.class
	rm -vfr parsing/*.class
	rm -vfr *.*~
	
init:
	mkdir source build
	
encode:
	java Encode example.txt

encode_test:
	rm -fr parser.java Yylex.java sym.java
	rm -vfr *.class
	rm -vfr *.*~
	jflex scanner.jflex
	java java_cup.Main parser.cup
	javac *.java
	java Encode example.txt
new: default run

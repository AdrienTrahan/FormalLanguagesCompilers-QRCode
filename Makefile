default: scanner parser

	javac *.java

scanner:
	jflex scanner.jflex
	
parser:
	java java_cup.Main parser.cup
	
clean:
	rm -fr parser.java Yylex.java sym.java
	rm -vfr *.class
	rm -vfr parsing/*.class
	rm -vfr *.*~
	
init:
	mkdir source build
	
run:
	java Main example.txt

test:
	rm -fr parser.java Yylex.java sym.java
	rm -vfr *.class
	rm -vfr *.*~
	jflex scanner.jflex
	java java_cup.Main parser.cup
	javac *.java
	java Main example.txt

new: default run

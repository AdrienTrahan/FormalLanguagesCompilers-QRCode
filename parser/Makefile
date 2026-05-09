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

jar:
	find . -name "*.class" > classes.txt
	echo "Main-Class: Compiler" > manifest1.txt
	echo "Main-Class: Runner" > manifest2.txt
	jar cfm Compiler.jar manifest1.txt @classes.txt
	jar cfm Runner.jar manifest2.txt @classes.txt
	rm manifest1.txt manifest2.txt classes.txt
	mv -f Compiler.jar ../pwa/static/runtime/Compiler.jar
	mv -f Runner.jar ../pwa/static/runtime/Runner.jar

build: scanner parser compile jar clean



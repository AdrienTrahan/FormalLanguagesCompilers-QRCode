JAVA_VERSION=17
JAVA_HOME ?= $(shell /usr/libexec/java_home -v 17 2>/dev/null)

JAVAC = $(JAVA_HOME)/bin/javac
JAVA = $(JAVA_HOME)/bin/java

# Verify JDK version
check-java:
	@$(JAVAC) -version 2>&1 | grep "\"\?17\." >/dev/null || \
	(echo "Error: JDK 17 is required"; exit 1)

default: check-java scanner parser compile

scanner:
	jflex scanner.jflex

parser:
	java java_cup.Main parser.cup

compile:
	$(JAVAC) --release $(JAVA_VERSION) *.java

clean:
	rm -fr parser.java Yylex.java sym.java
	rm -vfr *.class
	rm -vfr parsing/*.class
	rm -vfr *.*~

init:
	mkdir source build

encode:
	$(JAVA) Encode example.txt

encode_test: check-java
	rm -fr parser.java Yylex.java sym.java
	rm -vfr *.class
	rm -vfr *.*~
	jflex scanner.jflex
	java java_cup.Main parser.cup
	$(JAVAC) --release $(JAVA_VERSION) *.java
	$(JAVA) Encode example.txt

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

build: check-java scanner parser compile jar clean
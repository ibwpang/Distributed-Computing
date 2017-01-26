JFLAG = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	TMAN.java \
	Node.java \
	Graph.java \
	Plot.java \
	BTT.java \
	Node_BTT.java \
	Graph_BTT.java \
	Plot_BTT.java \
	CMT.java \
	Node_CMT.java \
	Graph_CMT.java \
	Plot_CMT.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

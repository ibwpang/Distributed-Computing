JFLAG = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	perfsquares.java \
	WorkerThread.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

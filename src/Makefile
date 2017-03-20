all: OBJS

FLAGS = -g
JC = javac

SRCS =\
	Model.java\
	Parser.java\
	MyModelClass.java\
	MyStateSet.java\
	TestMymodel.java\
	KripkeModel.java

OBJS: $(SRCS:.java=.class)

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

clean:
	rm -f *.class

tar:
	tar czvf java.tgz .

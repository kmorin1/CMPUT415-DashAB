all: runtime

runtime: libruntime.a

libruntime.a: libruntime.o
	ar rcs $@ $^

libruntime.o: libruntime.c
	clang -c $^ -o $@
agv: agv.o 
	gcc -Wall -g agv.o -o agv -lrt -lpthread -lm
	
agv.o: agv.c 
	gcc -Wall -g -c agv.c

run: agv
	./agv

clean:
	rm -f *.o


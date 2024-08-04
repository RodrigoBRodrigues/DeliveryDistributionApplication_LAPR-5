//Autores 1201566- Rafael Leite 1200735 - Rui Rocha

#ifndef SHARED_MEMORY_H

#define SHARED_MEMORY_H

#endif




#include <stdio.h>

#include <stdlib.h>

#include <unistd.h>

#include <fcntl.h> 

#include <sys/types.h> 

#include <sys/stat.h>

#include <sys/mman.h> 

#include <errno.h>



#define READ_AND_WRITE 1

#define ACCESS_MODE 1



/*

 *

 * Returns address for shared memory.

 * 

 * name: name of the shared memory

 * length: length of the shared memory

 * permission: permissions for the shared memory 

 * 

 * Permissions

 * 0 - READ

 * 1 - READ AND WRITE

 * 

 * Mode

 * 0 - CREATION

 * 1 - ACCESS

 *  

 */

void * get_shared_memory (char * name, int length, int* fd, int permission, int mode)

{

	void * addr;

	

	/* 

	 * 

	 * shm_open(const char *name, int oflag, mode_t mode) 

	 * creates and opens a shared memory area, 

	 * or opens an existing one.

	 * 

	 * Returns a file descriptor (the shared memory), 

	 * or -1 in case of error.

	 *

	 * name - Name to be used to identify the area, must start with '/'

	 * oflag - Creation options (O_CREAT | O_EXCL tries to create, if already exists gives error)

	 * 			and access options (O_RDWR means read and write access)

	 * mode - Defines permissions (should be 0 if area already exists)

	 * 

	 */

	*fd = shm_open(name, (mode == ACCESS_MODE ? O_RDWR : O_CREAT | O_EXCL) | (permission == READ_AND_WRITE ? O_RDWR : O_RDONLY), S_IRUSR | S_IWUSR);

	

	if(*fd == -1)

	{

		perror("shm_open falhou");

		exit(-1);

	}

	

	/*

	 * 

	 * ftruncate(int fd, off_t length)

	 * 

	 * Defines the size of the area and initializes it to zero.

	 * 

	 * Returns 0 if successful, or -1 in case of error (errno has the error)

	 * 

	 */

	ftruncate (*fd, length);

	

	if(errno == -1)

	{

		perror("truncate falhou");

		exit(-2);

	}



	

	/*

	 *

	 * void *mmap(void *addr, size_t length, int prot, int flags,int fd, off_t offset); 

	 * 

	 * Maps a shared memory object in the process address space

	 * 

	 * addr - allows to request the area to be mapped in a specific address

	 * 			NULL in the usual case to let the OS define the address

	 * 

	 * length - must be <= than length defined in truncate

	 * 

	 * prot - Protection flags. Must be consistent with shm_open

	 * 

	 * flags - always use MAP_SHARED for shared memory

	 * 

	 * fd - file descriptor returned by shm_open

	 * 

	 * offset - usually 0

	 * 

	 * Returns a pointer to the object or MAP_FAILED ((void *) -1), 

	 * with errno set with the error

	 */

	addr = mmap(NULL, length,(permission == 1 ? PROT_READ | PROT_WRITE : PROT_READ), MAP_SHARED, *fd, 0);

	

	if(errno == -1)

	{

		perror("mmap falhou");

		exit(-3);

	}

	

	return addr;

}



int close_shared_memory (void* addr, int length, int fd) 

{

	munmap(addr, length); /* disconnects */

	

	if (errno == -1) 

	{

		perror("munmap falhou");

		exit(-1);

	}

	

	/*

	 * 

	 * int close(int fd);

	 * 

	 * Closes the file descriptor. Another process can still open and use it.

	 * 

	 * Returns 0 if successful, or 

	 * -1 in case of error (errno is set with the error)

	 * 

	 * 

	 */

	close(fd); 

	

	if (errno == -1) 

	{

		perror("shm_unlink falhou");

		exit(-3);

	}

	

	return 0;

}



/*

 *

 * Closes address for shared memory.

 * 

 * name: name of the shared memory

 * length: length of the shared memory

 * addr: pointer for the shared memory address

 * mode: removing mode

 * 

 * Modes

 * 0 - CLOSE

 * 1 - REMOVE

 *  

 */

int remove_shared_memory (void* addr, char* name, int length)

{

	/* 

	 *

	 * int munmap(void *addr, size_t length);

	 * 

	 * Disconnects the shared memory area from the 

	 * process address space.

	 * 

	 * Returns 0 if successful, or -1 in case of error 

	 * (errno is set with the error)

	 * 

	 */

	munmap(addr, length); /* disconnects */

	

	if (errno == -1) 

	{

		perror("munmap falhou");

		exit(-1);

	}



	

	/*

	 *

	 * int shm_unlink(const char *name);

	 * 

	 * Removes memory area from file system. May not be immediate 

	 * if any process still has it open.

	 * 

	 * Other processes will have to create it again.

	 * 

	 * Returns 0 if successful, or -1 in case 

	 * of error (errno is set with the error) 

	 */

	shm_unlink(name); 

	

	

	if (errno == -1) 

	{

		perror("shm_unlink falhou");

		exit(-3);

	}

	

	return 0;

}

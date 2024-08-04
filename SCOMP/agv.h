#ifndef AGV_H

#define AGV_H

#endif

#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <time.h>
#include <sys/time.h>
#include <semaphore.h>
#include "shared_memory.h"
#include <pthread.h>
#include <math.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>



void* thread_battery_management(void *arg);

void* thread_route_planner(void *arg);

void* thread_simulation_engine(void *arg);

void* thread_positioning(void *arg);

void* thread_control_system(void *arg);

void* thread_sensors(void *arg);

void* thread_communications(void *arg);


// Utils Matemática

float distance(int currentX, int currentY, int endX, int endY);

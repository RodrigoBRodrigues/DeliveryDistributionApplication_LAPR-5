#include "communication.h"

int main() {
	
	int warehouse1[18][20];
	
	char * ip = "192.168.60.1";
	char * cli = "A";
	int down = 1;
     
    for(int i = 0;i < 18; i++) {
        for(int j = 0;j < 20; j++) {
			warehouse1[i][j] = 0;
		}
    }
    for (int i = 5; i < 17; i++) {
            warehouse1 [0] [i-down] = 1;
            warehouse1 [7] [i-down] = 1;
            warehouse1 [8] [i-down] = 1;
            warehouse1 [17][i-down] = 1;
            warehouse1 [9] [i-down] = 1;
            warehouse1 [10][i-down] = 1;
    }
    warehouse1 [3-down] [1-down] = 4;  warehouse1 [5-down][1-down] = 4;  warehouse1 [13-down][1-down]  =4;
    warehouse1 [15-down][1-down] = 2; warehouse1 [4-down][20-down] = 2;  warehouse1 [14-down][20-down] =2;
	//warehouse1 [6] [4] = 1;
	//warehouse1 [5] [4] = 1;
	
	for(int i = 0;i < 18; i++) {
        for(int j = 0;j < 20; j++) {
			printf("%d",warehouse1[i][j]);
		}
		printf("\n");
    }
	sendWarehouse(ip,cli,warehouse1);

	getWarehouse(ip,cli,warehouse1);
	for(int i = 0;i < 18; i++) {
        for(int j = 0;j < 20; j++) {
			printf("%d",warehouse1[i][j]);
		}
		printf("\n");
    }
	
	return 0;
}

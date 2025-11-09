#include<stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include<sys/wait.h>
#include<sys/types.h>

int main()
{
  pid_t pid1,pid2;
   int i;
  while((pid1=fork())==-1);
  if(pid1==0) //p1
    {
       for(i=0;i<5;i++)
        { printf("This is child process!\n");
           //sleep(1);
        }
     }
  else  //p
  {
        for(i=0;i<5;i++)
        { printf("This is father process!\n");
           //sleep(1);
        }
   }   
}

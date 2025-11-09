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
      for(i=0;i<100;i++) 
         {//usleep(1);
          printf("child%d\n",i);}
     }
  else  //p
  {
     while((pid2=fork())==-1);
     if(pid2==0)   //p2
       {
          for(i=0;i<100;i++) 
         	{//usleep(5);
             printf("son%d\n",i);}
    }
     else //p
       {
        for(i=0;i<100;i++) 
         {//usleep(10);
          printf("father%d\n",i);}
      }   
  }

}

#include<stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include<sys/wait.h>
#include<sys/types.h>

int main()
{
  pid_t pid1,pid2;
  while((pid1=fork())==-1);
  if(pid1==0) //p1
    {
       //usleep(1);
      putchar('c');
     }
  else  //p
  {
     while((pid2=fork())==-1);
     if(pid2==0)   //p2
       {//sleep(1);
         putchar('b');}
     else //p
       {//usleep(1);
        //wait(NULL);
        putchar('a');}   
  }

}

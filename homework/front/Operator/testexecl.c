#include<stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include<sys/wait.h>
int main()
{
  signal(SIGCHLD,SIG_IGN); //wait for all child
  int pchild;
  int i;
  int count=0;
  while((pchild=fork())==-1);
  if(pchild==0) //B
    {
       printf("子进程 pid=%d,主进程 pid=%d\n",getpid(),getppid());
       execl("./countfork","countfork",NULL);
       //_exit(0);
    }
  else  //A
 {   wait(0);printf("主进程结束!\n");   
   }
}

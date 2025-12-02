#include<stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include<string.h>
#include<sys/wait.h>
#include<sys/types.h>
/*管道的使用方法*/
/*主进程创建两个子进程,一个是发送进程,通过管道向另一个子进程发送一个字符串,
  另一个是接收进程,该子进程接收字符串后,启动一个后台加密程序对字符串进行加密*/
int main(int argc,char * argv[])
{
    signal(SIGCHLD,SIG_IGN); //wait for all child
    int n,fd[2]; //管道中的数据从fd[1]写入,从fd[0]读出  
    pid_t ps,pr;  
    char str[100]; 
    printf("主进程号:%d\n",getpid()); 
    printf("输入发送字符串:\n");
    fgets(str,100,stdin); 
    
    pipe(fd); //创建管道fd,数据只能在一个方向上流动fd[1]--->fd[0]; 
    while((ps=fork()) ==-1); //创建发送子进程
    if (ps ==0)    //发送进程  
    {   printf("\n创建发送子进程,进程号:%d\n",getpid());      
        close(fd[0]);   //close parent's read-port  
        write(fd[1],str,strlen(str));  //write to pipe  
        printf("写入明文:%s",str);
    }  
    else    //父进程  
    {   waitpid(ps,NULL,0); //等待发送子进程结束
       while((pr=fork()) ==-1); //创建接收子进程
        if(pr==0)
        {	printf("\n创建接收子进程,进程号:%d\n",getpid());
        	close(fd[1]);   //close child's write-port  
        	n = read(fd[0],str,strlen(str));  //read from pipe  
        	printf("接收明文:%s\n",str);
        	execl("./encrypt","encrypt",str,NULL); 
         }
        else  //父进程
        {wait(NULL);printf("主进程结束!\n");}
    }  
  
    return 1;  
}

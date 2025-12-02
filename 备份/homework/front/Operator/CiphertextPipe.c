#include<stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include<string.h>
#include<sys/wait.h>
#include<sys/types.h>
int main(int argc,char * argv[])
{
    signal(SIGCHLD,SIG_IGN);
    int n,fd[2];
    pid_t ps,pr;
    char str[100];
    printf("主进程号:%d\n",getpid());
    printf("输入发送字符串:\n");
    fgets(str,100,stdin);
    str[strcspn(str, "\n")] = 0;
    
    pipe(fd);
    while((ps=fork()) ==-1);
    if (ps ==0)
    {
        printf("\n创建发送子进程,进程号:%d\n",getpid());
        close(fd[0]);
        write(fd[1],str,strlen(str)+1);
        printf("写入明文:%s\n",str);
        close(fd[1]);
        _exit(0);
    }
    else
    {
        waitpid(ps,NULL,0);
        while((pr=fork()) ==-1);
        if(pr==0)
        {
            printf("\n创建接收子进程,进程号:%d\n",getpid());
            close(fd[1]);
            n = read(fd[0],str,100);
            printf("接收明文:%s\n",str);
            close(fd[0]);
            execl("./encrypt","encrypt",str,"&",NULL);
            perror("加密程序调用失败");
            _exit(1);
        }
        else
        {
            close(fd[0]);
            close(fd[1]);
            waitpid(pr,NULL,0);
            printf("主进程结束!\n");
        }
    }
    return 0;
}
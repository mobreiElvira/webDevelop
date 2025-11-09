#include<stdio.h>
#include<unistd.h>  // 包含 getpid()、getppid()

int main()
{
    // 打印 countfork 自身的进程信息
    printf("-------------------------\n");
    printf("countfork 开始运行！\n");
    printf("countfork 自身PID：%d\n", getpid());
    printf("countfork 父进程PID：%d\n", getppid());  // 父进程就是子进程1（11735）
    printf("countfork 运行结束！\n");
    printf("-------------------------\n");
    return 0;
}
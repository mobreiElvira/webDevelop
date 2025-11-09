#include<stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include<sys/wait.h>
#include<sys/types.h>

int main()
{
    signal(SIGCHLD, SIG_IGN);  // 忽略子进程终止信号（避免僵尸进程）
    pid_t pid1, pid2;
    int child_count = 0;       // 统计子进程总数

    // 第一段代码逻辑：创建子进程1（输出'c'）
    while ((pid1 = fork()) == -1);
    if (pid1 == 0)
    {
        putchar('c');  // 子进程1输出字符'c'
        printf("\n子进程1 pid=%d, 主进程 pid=%d\n", getpid(), getppid());
        // 调用外部程序countfork（若存在）
        execl("./countfork", "countfork", NULL);
        // 若execl失败，输出错误并退出
        perror("子进程1调用countfork失败");
        _exit(1);
    }
    else
    {
        child_count++;  // 统计子进程1

        // 第一段代码逻辑：创建子进程2（输出'b'）
        while ((pid2 = fork()) == -1);
        if (pid2 == 0)
        {
            putchar('b');  // 子进程2输出字符'b'
            printf("\n子进程2 pid=%d, 主进程 pid=%d\n", getpid(), getppid());
            _exit(0);  // 子进程2无需调用外部程序，直接退出
        }
        else
        {
            child_count++;  // 统计子进程2

            // 主进程逻辑：等待子进程结束，输出自身字符并显示统计结果
            wait(NULL);  // 等待子进程1
            wait(NULL);  // 等待子进程2
            putchar('a');  // 主进程输出字符'a'
            printf("\n主进程 pid=%d, 共创建 %d 个子进程\n", getpid(), child_count);
            printf("主进程结束!\n");
        }
    }

    return 0;
}
#include<stdio.h>
#include<stdlib.h>
#include <unistd.h>
#include<sys/wait.h>
#include<sys/types.h>

int main()
{
    pid_t pid1, pid2;
    pid_t parent_pid = getpid();

    while ((pid1 = fork()) == -1);
    if (pid1 == 0) {
        sleep(1);
        printf("|_c（子进程2右子树，PID：%d，父进程PID：%d）\n", getpid(), getppid());
        return 0;
    }

    while ((pid2 = fork()) == -1);
    if (pid2 == 0) {
        sleep(1);
        printf("|_b（子进程1左子树，PID：%d，父进程PID：%d）\n", getpid(), getppid());
        return 0;
    }

    printf("a（父进程根节点，PID：%d，父进程PID：%d）\n", parent_pid, getppid());
    wait(NULL);
    wait(NULL);

    return 0;
}
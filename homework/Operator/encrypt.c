#include<stdio.h>
#include<string.h>
#include<unistd.h>

// 简单加密算法：每个字符转为后一个字符（示例，可替换为其他算法）
void encrypt(char *str) {
    for (int i = 0; i < strlen(str); i++) {
        if (str[i] != '\0') {
            str[i]++;  // 例如：'a'→'b'，'1'→'2'
        }
    }
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "用法：%s <要加密的字符串>\n", argv[0]);
        return 1;
    }

    // 打印加密前后信息
    printf("\n【加密程序(后台运行)】PID: %d\n", getpid());
    printf("明文: %s\n", argv[1]);
    
    // 执行加密
    encrypt(argv[1]);
    
    // 输出密文
    printf("密文: %s\n", argv[1]);
    return 0;
}
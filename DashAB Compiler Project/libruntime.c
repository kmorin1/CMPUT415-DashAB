#include <stdint.h>
#include <math.h>
#include <stdio.h>
#include <stdbool.h>

uint32_t power_i32(uint32_t base, uint32_t exp) {
    int i, result = 1;
    for (i = 0; i < exp; i++)
        result *= base;
    return result;
}

uint32_t print_i32(uint32_t x) {
    return printf("%d", x);
}

uint32_t print_float(float x) {
    return printf("%f", x);
}

bool print_i1(bool x) {
    printf("%s", x ? "T": "F");
    return true;
}

char print_i8(char x) {
    printf("%c", x);
    return 0;
}

uint32_t input_i32(uint32_t * x) {
    scanf("%d", &x);
    return 0;
}

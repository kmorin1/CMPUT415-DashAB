#include <stdint.h>
#include <math.h>
#include <stdio.h>
#include <stdbool.h>

uint32_t FAIL;

uint32_t set_fail(uint32_t code) {
    if (code == 1) {
        FAIL = 0;
    }
    else if (code == 0) {
        FAIL = 1;
    }
    else if (code == EOF) {
        FAIL = 2;
    }
    else {
        FAIL = 0;
    }
    return 0;
}

uint32_t stream_state() {
    return FAIL;
}

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
    return printf("%g", x);
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
    int result = scanf("%d", x);
    set_fail(result);
    return 0;
}

uint32_t input_float(float * x) {
    int result = scanf("%g", x);
    set_fail(result);
    return 0;
}

uint32_t input_i1(bool * x) {
    char input;
    int result = scanf("%c", &input);
    if (result == 1) {
        if (input == 'T') {
	    *x = 1;
        }
        else if (input == 'F') {
            *x = 0;
        }
    }
    set_fail(result);
    return 0;
}

char input_i8(char * x) {
    int result = scanf("%c", x);
    set_fail(result);
    return 0;
}

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

float power_float(float base, float exp) {
    return pow(base, exp);
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

uint32_t print_i32_vector(uint32_t * x, uint32_t size) {
	uint32_t * num = x;
	for (int i = 0; i < size; i++) {
		printf("%d", *num);
		num += 1;
		if (i+1 < size) {
			printf(" ");
		}
	}
	return 0;
}

float print_float_vector(float * x, uint32_t size) {
	float * num = x;
	for (int i = 0; i < size; i++) {
		printf("%g", *num);
		num += 1;
		if (i+1 < size) {
			printf(" ");
		}
	}
	return 0;
}

bool print_i1_vector(bool * x, uint32_t size) {
	bool * val = x;
	for (int i = 0; i < size; i++) {
		printf("%s", *val ? "T": "F");
		val += 1;
		if (i+1 < size) {
			printf(" ");
		}
	}
	return 0;
}

char print_i8_vector(char * x, uint32_t size) {
	char * string = x;
	for (int i = 0; i < size; i++) {
		printf("%c", *string);
		string += 1;
		if (i+1 < size) {
			printf(" ");
		}
	}
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

uint32_t add_i32_vectors(uint32_t * x, uint32_t x_size, uint32_t * y, uint32_t y_size, uint32_t * result) {
	uint32_t * x_vector = x;
	uint32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		uint32_t x_val = *x_vector;
		uint32_t y_val = *y_vector;
		*result = x_val + y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

uint32_t sub_i32_vectors(uint32_t * x, uint32_t x_size, uint32_t * y, uint32_t y_size, uint32_t * result) {
	uint32_t * x_vector = x;
	uint32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		uint32_t x_val = *x_vector;
		uint32_t y_val = *y_vector;
		*result = x_val - y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

uint32_t mul_i32_vectors(uint32_t * x, uint32_t x_size, uint32_t * y, uint32_t y_size, uint32_t * result) {
	uint32_t * x_vector = x;
	uint32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		uint32_t x_val = *x_vector;
		uint32_t y_val = *y_vector;
		*result = x_val * y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

uint32_t div_i32_vectors(uint32_t * x, uint32_t x_size, uint32_t * y, uint32_t y_size, uint32_t * result) {
	uint32_t * x_vector = x;
	uint32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		uint32_t x_val = *x_vector;
		uint32_t y_val = *y_vector;
		*result = x_val / y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}




float add_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val + y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float sub_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val - y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float mul_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val * y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float div_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val / y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}


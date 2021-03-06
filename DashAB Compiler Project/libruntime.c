#include <stdint.h>
#include <math.h>
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

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

int32_t power_i32(int32_t base, int32_t exp) {
    int i, result = 1;
    for (i = 0; i < exp; i++)
        result *= base;
    return result;
}

float power_float(float base, float exp) {
    return pow(base, exp);
}

int32_t print_i32(int32_t x) {
    return printf("%d", x);
}

int32_t print_float(float x) {
    return printf("%g", x);
}

bool print_i1(bool x) {
    printf("%s", x ? "T": "F");
    return true;
}

char print_i8(char x) {
    if (x != 0) {
    	printf("%c", x);
    }
    return 0;
}

void check_sizes(uint32_t a, uint32_t b) {
	if (a != b) {
		printf("Error: vector sizes (%d and %d) are different.\n", a, b);
		exit(-1);
	}
}

void check_bounds(uint32_t size, uint32_t index_1) {
	if (index_1 > size || index_1 <= 0) {
		printf("Error: index [%d] is out of bounds of array with size %d.\n", index_1, size);
		exit(-1);
	}
}

void check_interval(int32_t lower, int32_t upper) {
	if (lower > upper) {
		printf("Error: interval [%d,%d] must be non-decreasing.\n", lower, upper);
		exit(-1);
	}
}

void check_not_zero(int32_t value) {
	if (value == 0) {
		printf("Error: divide by zero\n");
		exit(-1);
	}
}

void check_by(int32_t by) {
	if (by < 1) {
		printf("Error: by operator has a non-positive integer (%d).\n", by);
		exit(-1);
	}
}


int32_t print_i32_vector(int32_t * x, uint32_t size) {
	int32_t * num = x;
	for (int i = 0; i < size; i++) {
		printf("%d", *num);
		num += 1;
		if (i+1 < size) {
			printf(" ");
		}
	}
	printf("\n");
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
	printf("\n");
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
	printf("\n");
	return 0;
}

char print_i8_vector(char * x, uint32_t size) {
	char * string = x;
	for (int i = 0; i < size; i++) {
		if (*string == 0) {
			break;
		}
		printf("%c", *string);
		string += 1;
	}
	return 0;
}

int32_t print_i32_interval(int32_t lower, int32_t upper) {
	check_interval(lower, upper);

	for (int32_t i = lower; i <= upper; i++) {
		printf("%d", i);
		if (i+1 <= upper) {
			printf(" ");
		}
	}
	printf("\n");
	return 0;
}

int32_t input_i32(int32_t * x) {
    uint32_t result = scanf("%d", x);
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

int32_t add_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, int32_t * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val + y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t sub_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, int32_t * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val - y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t mul_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, int32_t * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val * y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t div_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, int32_t * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		check_not_zero(y_val);
		*result = x_val / y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t mod_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, int32_t * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val % y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t pow_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, int32_t * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = power_i32(x_val, y_val);
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t neg_i32_vectors(int32_t * x, uint32_t x_size, int32_t * result) {
	int32_t * x_vector = x;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		*result = -x_val;
		x_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t less_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val < y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t greater_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val > y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t lessequal_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val <= y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

int32_t greaterequal_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		*result = x_val >= y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

bool equal_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size) {
	check_sizes(x_size, y_size);	
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	bool equal = true;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		if (x_val != y_val) {
			equal = false;
			break;
		}
		x_vector += 1;
		y_vector += 1;		
	}
	return equal;
}

bool nequal_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size) {
	return !equal_i32_vectors(x, x_size, y, y_size);;
}






float add_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	check_sizes(x_size, y_size);	
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
	check_sizes(x_size, y_size);	
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
	check_sizes(x_size, y_size);	
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

float mod_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	check_sizes(x_size, y_size);	
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = fmod(x_val, y_val);
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float pow_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	check_sizes(x_size, y_size);	
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = power_float(x_val, y_val);
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float neg_float_vectors(float * x, uint32_t x_size, float * result) {
	float * x_vector = x;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		*result = -x_val;
		x_vector += 1;
		result += 1;		
	}
	return 0;
}

float less_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val < y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float greater_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val > y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float lessequal_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val <= y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

float greaterequal_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		*result = x_val >= y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

bool equal_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size) {
	check_sizes(x_size, y_size);	
	float * x_vector = x;
	float * y_vector = y;
	bool equal = true;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		if (x_val != y_val) {
			equal = false;
			break;
		}
		x_vector += 1;
		y_vector += 1;	
	}
	return equal;
}

bool nequal_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size) {
	return !equal_float_vectors(x, x_size, y, y_size);
}

char less_i8_vectors(char * x, uint32_t x_size, char * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	char * x_vector = x;
	char * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		char x_val = *x_vector;
		char y_val = *y_vector;
		*result = x_val < y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

char greater_i8_vectors(char * x, uint32_t x_size, char * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	char * x_vector = x;
	char * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		char x_val = *x_vector;
		char y_val = *y_vector;
		*result = x_val > y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

char lessequal_i8_vectors(char * x, uint32_t x_size, char * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	char * x_vector = x;
	char * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		char x_val = *x_vector;
		char y_val = *y_vector;
		*result = x_val <= y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

char greaterequal_i8_vectors(char * x, uint32_t x_size, char * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	char * x_vector = x;
	char * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		char x_val = *x_vector;
		char y_val = *y_vector;
		*result = x_val >= y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;		
	}
	return 0;
}

bool equal_i8_vectors(char * x, uint32_t x_size, char * y, uint32_t y_size) {
	check_sizes(x_size, y_size);	
	char * x_vector = x;
	char * y_vector = y;
	bool equal = true;
	for (int i = 0; i < x_size; i++) {
		char x_val = *x_vector;
		char y_val = *y_vector;
		if (x_val != y_val) {
			equal = false;
			break;
		}
		x_vector += 1;
		y_vector += 1;		
	}
	return equal;
}

bool nequal_i8_vectors(char * x, uint32_t x_size, char * y, uint32_t y_size) {
	return !equal_i8_vectors(x, x_size, y, y_size);
}

bool not_i1_vectors(bool * x, uint32_t x_size, bool * result) {
	bool * x_vector = x;
	for (int i = 0; i < x_size; i++) {
		bool x_val = *x_vector;
		*result = !x_val;
		x_vector += 1;
		result += 1;	
	}
	return false;
}

bool or_i1_vectors(bool * x, uint32_t x_size, bool * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	bool * x_vector = x;
	bool * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		bool x_val = *x_vector;
		bool y_val = *y_vector;
		*result = x_val | y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;	
	}
	return false;
}

bool xor_i1_vectors(bool * x, uint32_t x_size, bool * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	bool * x_vector = x;
	bool * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		bool x_val = *x_vector;
		bool y_val = *y_vector;
		*result = x_val ^ y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;	
	}
	return false;
}

bool and_i1_vectors(bool * x, uint32_t x_size, bool * y, uint32_t y_size, bool * result) {
	check_sizes(x_size, y_size);	
	bool * x_vector = x;
	bool * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		bool x_val = *x_vector;
		bool y_val = *y_vector;
		*result = x_val & y_val;
		x_vector += 1;
		y_vector += 1;
		result += 1;	
	}
	return false;
}

bool equal_i1_vectors(bool * x, uint32_t x_size, bool * y, uint32_t y_size) {
	check_sizes(x_size, y_size);	
	bool * x_vector = x;
	bool * y_vector = y;
	bool equal = true;
	for (int i = 0; i < x_size; i++) {
		bool x_val = *x_vector;
		bool y_val = *y_vector;
		if (x_val != y_val) {
			equal = false;
			break;
		}
		x_vector += 1;
		y_vector += 1;	
	}
	return equal;
}

bool nequal_i1_vectors(bool * x, uint32_t x_size, bool * y, uint32_t y_size) {
	return !equal_i1_vectors(x, x_size, y, y_size);
}

int32_t dot_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size) {
	check_sizes(x_size, y_size);
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	int32_t sum = 0;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		int32_t y_val = *y_vector;
		sum += x_val * y_val;
		x_vector += 1;
		y_vector += 1;
	}
	return sum;
}

float dot_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size) {
	check_sizes(x_size, y_size);
	float * x_vector = x;
	float * y_vector = y;
	float sum = 0;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		float y_val = *y_vector;
		sum += x_val * y_val;
		x_vector += 1;
		y_vector += 1;	
	}
	return sum;
}

int32_t cat_i32_vectors(int32_t * x, uint32_t x_size, int32_t * y, uint32_t y_size, int32_t * result) {
	int32_t * x_vector = x;
	int32_t * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		int32_t x_val = *x_vector;
		*result = x_val;
		x_vector += 1;
		result += 1;
	}

	for (int i = 0; i < y_size; i++) {
		int32_t y_val = *y_vector;
		*result = y_val;
		y_vector += 1;
		result += 1;
	}
	return 0;
}

float cat_float_vectors(float * x, uint32_t x_size, float * y, uint32_t y_size, float * result) {
	float * x_vector = x;
	float * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		float x_val = *x_vector;
		*result = x_val;
		x_vector += 1;
		result += 1;
	}

	for (int i = 0; i < y_size; i++) {
		float y_val = *y_vector;
		*result = y_val;
		y_vector += 1;
		result += 1;
	}
	return 0;
}

char cat_i8_vectors(char * x, uint32_t x_size, char * y, uint32_t y_size, char * result) {
	char * x_vector = x;
	char * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		char x_val = *x_vector;
		if (x_val == 0) {
			break;
		}
		*result = x_val;
		x_vector += 1;
		result += 1;
	}

	for (int i = 0; i < y_size; i++) {
		char y_val = *y_vector;
		*result = y_val;
		y_vector += 1;
		result += 1;
	}
	return 0;
}

bool cat_i1_vectors(bool * x, uint32_t x_size, bool * y, uint32_t y_size, bool * result) {
	bool * x_vector = x;
	bool * y_vector = y;
	for (int i = 0; i < x_size; i++) {
		bool x_val = *x_vector;
		*result = x_val;
		x_vector += 1;
		result += 1;
	}

	for (int i = 0; i < y_size; i++) {
		bool y_val = *y_vector;
		*result = y_val;
		y_vector += 1;
		result += 1;
	}
	return 0;
}

int32_t index_int_i32_vectors(int32_t * x, uint32_t x_size, uint32_t index_1) {
	check_bounds(x_size, index_1);	
	uint32_t index_0 = index_1 - 1;
	int32_t * x_vector = x;
	int32_t val = 0;
	x_vector += index_0;
	val = *x_vector;
	return val;	
}

int32_t index_vector_i32_vectors(uint32_t * x, uint32_t vector_size, uint32_t * y, uint32_t num_indices, uint32_t * result) {
	uint32_t * vector = x;
	uint32_t * indices = y;

	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
		indices += 1;
	}

	return 0;
}

int32_t index_interval_i32_vectors(uint32_t * x, uint32_t vector_size, int32_t lower, int32_t upper, uint32_t * result) {
	uint32_t * vector = x;

	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
	}

	return 0;
}

int32_t index_int_i32_intervals(int32_t lower, uint32_t upper, uint32_t index_1) {
	check_interval(lower, upper);
	check_bounds(upper - lower + 1, index_1);
	uint32_t index_0 = index_1 - 1;
	int32_t val = lower + index_0;
	return val;	
}

int32_t index_vector_i32_intervals(int32_t lower, uint32_t upper, uint32_t * y, uint32_t num_indices, uint32_t * result) {
	check_interval(lower, upper);
	uint32_t * indices = y;

	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(upper - lower + 1, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = lower+index_0;
		result += 1;
		indices += 1;
	}

	return 0;
}

int32_t index_interval_i32_intervals(int32_t lower1, uint32_t upper1, int32_t lower2, int32_t upper2, uint32_t * result) {
	check_interval(lower1, upper1);
	check_interval(lower2, upper2);

	for (int i = lower2; i <= upper2; i++) {
		uint32_t index_1 = i;
		check_bounds(upper1 - lower1 + 1, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = lower1+index_0;
		result += 1;
	}

	return 0;
}

float index_int_float_vectors(float * x, uint32_t x_size, uint32_t index_1) {
	check_bounds(x_size, index_1);	
	uint32_t index_0 = index_1 - 1;
	float * x_vector = x;
	float val = 0;
	x_vector += index_0;
	val = *x_vector;
	return val;	
}

float index_vector_float_vectors(float * x, uint32_t vector_size, uint32_t * y, uint32_t num_indices, float * result) {
	float * vector = x;
	uint32_t * indices = y;

	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
		indices += 1;
	}

	return 0;
}

float index_interval_float_vectors(float * x, uint32_t vector_size, int32_t lower, int32_t upper, float * result) {
	float * vector = x;

	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
	}

	return 0;
}

char index_int_i8_vectors(char * x, uint32_t x_size, uint32_t index_1) {
	check_bounds(x_size, index_1);	
	uint32_t index_0 = index_1 - 1;
	char * x_vector = x;
	char val = 0;
	x_vector += index_0;
	val = *x_vector;
	return val;	
}

char index_vector_i8_vectors(char * x, uint32_t vector_size, uint32_t * y, uint32_t num_indices, char * result) {
	char * vector = x;
	uint32_t * indices = y;

	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
		indices += 1;
	}

	return 0;
}

char index_interval_i8_vectors(char * x, uint32_t vector_size, int32_t lower, int32_t upper, char * result) {
	char * vector = x;

	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
	}

	return 0;
}

bool index_int_i1_vectors(bool * x, uint32_t x_size, uint32_t index_1) {
	check_bounds(x_size, index_1);	
	uint32_t index_0 = index_1 - 1;
	bool * x_vector = x;
	bool val = false;
	x_vector += index_0;
	val = *x_vector;
	return val;	
}

bool index_vector_i1_vectors(bool * x, uint32_t vector_size, uint32_t * y, uint32_t num_indices, bool * result) {
	bool * vector = x;
	uint32_t * indices = y;

	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
		indices += 1;
	}

	return 0;
}

bool index_interval_i1_vectors(bool * x, uint32_t vector_size, int32_t lower, int32_t upper, bool * result) {
	bool * vector = x;

	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(vector_size, index_1);
		uint32_t index_0 = index_1 - 1;
		*result = *(vector+index_0);
		result += 1;
	}

	return 0;
}

int32_t add_i32_intervals(int32_t lower1, int32_t upper1, int32_t lower2, int32_t upper2, int32_t * lower_result, int32_t * upper_result) {
	*lower_result = lower1 + lower2;
	*upper_result = upper1 + upper2;
	check_interval(*lower_result, *upper_result);
	return 0;
}

int32_t sub_i32_intervals(int32_t lower1, int32_t upper1, int32_t lower2, int32_t upper2, int32_t * lower_result, int32_t * upper_result) {
	*lower_result = lower1 - upper2;
	*upper_result = upper1 - lower2;
	check_interval(*lower_result, *upper_result);
	return 0;
}

int32_t mul_i32_intervals(int32_t lower1, int32_t upper1, int32_t lower2, int32_t upper2, int32_t * lower_result, int32_t * upper_result) {
	int32_t ac = lower1 * lower2;
	int32_t ad = lower1 * upper2;
	int32_t bc = upper1 * lower2;
	int32_t bd = upper1 * upper2;	
	
	int32_t min = ac;
	if (ad < min) {
		min = ad;
	}

	if (bc < min) {
		min = bc;
	}

	if (bd < min) {
		min = bd;
	}

	int32_t max = ac;
	if (ad > max) {
		max = ad;
	}

	if (bc > max) {
		max = bc;
	}

	if (bd > max) {
		max = bd;
	}


	*lower_result = min;
	*upper_result = max;
	check_interval(*lower_result, *upper_result);
	return 0;
}

int32_t div_i32_intervals(int32_t lower1, int32_t upper1, int32_t lower2, int32_t upper2, int32_t * lower_result, int32_t * upper_result) {
	check_not_zero(lower2);
	check_not_zero(upper2);	
	int32_t ac = lower1 / lower2;
	int32_t ad = lower1 / upper2;
	int32_t bc = upper1 / lower2;
	int32_t bd = upper1 / upper2;

	int32_t min = ac;
	if (ad < min) {
		min = ad;
	}

	if (bc < min) {
		min = bc;
	}

	if (bd < min) {
		min = bd;
	}

	int32_t max = ac;
	if (ad > max) {
		max = ad;
	}

	if (bc > max) {
		max = bc;
	}

	if (bd > max) {
		max = bd;
	}

	*lower_result = min;
	*upper_result = max;
	check_interval(*lower_result, *upper_result);
	return 0;
}

int32_t neg_i32_intervals(int32_t lower, int32_t upper, int32_t * lower_result, int32_t * upper_result) {
	*lower_result = -upper;
	*upper_result = -lower;
	check_interval(*lower_result, *upper_result);
	return 0;
}

bool equal_i32_intervals(int32_t lower1, int32_t upper1, int32_t lower2, int32_t upper2) {
	return lower1 == lower2 && upper1 == upper2;
}

bool nequal_i32_intervals(int32_t lower1, int32_t upper1, int32_t lower2, int32_t upper2) {
	return !equal_i32_intervals(lower1, upper1, lower2, upper2);
}

uint32_t * malloc_i32_vector(uint32_t size) {
	uint32_t * vector = calloc(size, sizeof(uint32_t));
	return vector;
}

float * malloc_float_vector(uint32_t size) {
	float * vector = calloc(size, sizeof(float));
	return vector;
}

char * malloc_i8_vector(uint32_t size) {
	char * vector = calloc(size, sizeof(char));
	return vector;
}

bool * malloc_i1_vector(uint32_t size) {
	bool * vector = calloc(size, sizeof(bool));
	return vector;
}


uint32_t get_by_size(uint32_t size, uint32_t by) {
	uint32_t result = (uint32_t)ceil((float)size / (float)by);
	return result;
}

uint32_t by_i32_vector(uint32_t * x, uint32_t size, int32_t by, uint32_t * result) {
	check_by(by);
	uint32_t * vector = x;	
	for (int i = 0; i < size; i+=by) {
		*result = *vector;
		result += 1;
		vector += by;
	}

	return 0;
}

float by_float_vector(float * x, uint32_t size, int32_t by, float * result) {
	check_by(by);
	float * vector = x;	
	for (int i = 0; i < size; i+=by) {
		*result = *vector;
		result += 1;
		vector += by;
	}

	return 0;
}

char by_i8_vector(char * x, uint32_t size, int32_t by, char * result) {
	check_by(by);
	char * vector = x;	
	for (int i = 0; i < size; i+=by) {
		*result = *vector;
		result += 1;
		vector += by;
	}

	return 0;
}

bool by_i1_vector(bool * x, uint32_t size, int32_t by, bool * result) {
	check_by(by);
	bool * vector = x;
	for (int i = 0; i < size; i+=by) {
		*result = *vector;
		result += 1;
		vector += by;
	}

	return false;
}

uint32_t by_i32_interval(int32_t lower, int32_t upper, int32_t by, uint32_t * result) {
	check_by(by);
	for (int i = lower; i <= upper; i+=by) {
		*result = i;
		result += 1;
	}
	return 0;
}

uint32_t set_int_index_i32_vector(uint32_t * x, uint32_t x_size, uint32_t index_1, int32_t value) {
	check_bounds(x_size, index_1);
	uint32_t index_0 = index_1 - 1;
	*(x + index_0) = value;
	return 0;
}

uint32_t set_vector_index_i32_vector(uint32_t * x, uint32_t x_size, uint32_t * indices, uint32_t num_indices, uint32_t * values, uint32_t values_size) {
	check_sizes(num_indices, values_size);
	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		int32_t value = *values;
		*(x + index_0) = value;
		indices += 1;
		values += 1;
	}

	return 0;
}

uint32_t set_interval_index_i32_vector(uint32_t * x, uint32_t x_size, uint32_t lower, uint32_t upper, uint32_t * values, uint32_t values_size) {
	check_interval(lower, upper);
	check_sizes(upper - lower + 1, values_size);
	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		int32_t value = *values;
		*(x + index_0) = value;
		values += 1;
	}

	return 0;
}

float set_int_index_float_vector(float * x, uint32_t x_size, uint32_t index_1, float value) {
	check_bounds(x_size, index_1);
	uint32_t index_0 = index_1 - 1;
	*(x + index_0) = value;
	return 0;
}

float set_vector_index_float_vector(float * x, uint32_t x_size, uint32_t * indices, uint32_t num_indices, float * values, uint32_t values_size) {
	check_sizes(num_indices, values_size);
	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		float value = *values;
		*(x + index_0) = value;
		indices += 1;
		values += 1;
	}

	return 0;
}

float set_interval_index_float_vector(float * x, uint32_t x_size, uint32_t lower, uint32_t upper, float * values, uint32_t values_size) {
	check_interval(lower, upper);
	check_sizes(upper - lower + 1, values_size);
	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		float value = *values;
		*(x + index_0) = value;
		values += 1;
	}

	return 0;
}

char set_int_index_i8_vector(char * x, uint32_t x_size, uint32_t index_1, char value) {
	check_bounds(x_size, index_1);
	uint32_t index_0 = index_1 - 1;
	*(x + index_0) = value;
	return 0;
}

char set_vector_index_i8_vector(char * x, uint32_t x_size, uint32_t * indices, uint32_t num_indices, char * values, uint32_t values_size) {
	check_sizes(num_indices, values_size);
	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		char value = *values;
		*(x + index_0) = value;
		indices += 1;
		values += 1;
	}

	return 0;
}

char set_interval_index_i8_vector(char * x, uint32_t x_size, uint32_t lower, uint32_t upper, char * values, uint32_t values_size) {
	check_interval(lower, upper);
	check_sizes(upper - lower + 1, values_size);
	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		char value = *values;
		*(x + index_0) = value;
		values += 1;
	}

	return 0;
}

bool set_int_index_i1_vector(bool * x, uint32_t x_size, uint32_t index_1, bool value) {
	check_bounds(x_size, index_1);
	uint32_t index_0 = index_1 - 1;
	*(x + index_0) = value;
	return 0;
}

bool set_vector_index_i1_vector(bool * x, uint32_t x_size, uint32_t * indices, uint32_t num_indices, bool * values, uint32_t values_size) {
	check_sizes(num_indices, values_size);
	for (int i = 0; i < num_indices; i++) {
		uint32_t index_1 = *indices;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		bool value = *values;
		*(x + index_0) = value;
		indices += 1;
		values += 1;
	}

	return 0;
}

bool set_interval_index_i1_vector(bool * x, uint32_t x_size, uint32_t lower, uint32_t upper, bool * values, uint32_t values_size) {
	check_interval(lower, upper);
	check_sizes(upper - lower + 1, values_size);
	for (int i = lower; i <= upper; i++) {
		uint32_t index_1 = i;
		check_bounds(x_size, index_1);
		uint32_t index_0 = index_1 - 1;
		bool value = *values;
		*(x + index_0) = value;
		values += 1;
	}

	return 0;
}

uint32_t reverse_i32_vector(uint32_t * x, uint32_t x_size, uint32_t * result) {
	for (int i = 0; i < x_size; i++) {
		*(result + x_size - 1 - i) = *(x + i);
	}

	return 0;
}

float reverse_float_vector(float * x, uint32_t x_size, float * result) {
	for (int i = 0; i < x_size; i++) {
		*(result + x_size - 1 - i) = *(x + i);
	}

	return 0;
}

char reverse_i8_vector(char * x, uint32_t x_size, char * result) {
	for (int i = 0; i < x_size; i++) {
		*(result + x_size - 1 - i) = *(x + i);
	}

	return 0;
}

bool reverse_i1_vector(bool * x, uint32_t x_size, bool * result) {
	for (int i = 0; i < x_size; i++) {
		*(result + x_size - 1 - i) = *(x + i);
	}

	return 0;
}


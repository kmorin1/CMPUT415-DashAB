#include <stdint.h>
#include <math.h>
#include <stdio.h>

uint32_t exponentiation_integer(uint32_t b, uint32_t p) {
	uint32_t result = pow(b, p);
	return result;
}

float exponentiation_float(float b, float p) {
	float result = pow(b, p);
	return result;
}

void output(uint32_t x) {
	printf("%d", x);
}
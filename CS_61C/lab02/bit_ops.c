#include <stdio.h>
#include "bit_ops.h"

/* Returns the Nth bit of X. Assumes 0 <= N <= 31. */
unsigned get_bit(unsigned x, unsigned n) {
    return (x << 31 - n) >> 31;
}

/* Set the nth bit of the value of x to v. Assumes 0 <= N <= 31, and V is 0 or 1 */
void set_bit(unsigned *x, unsigned n, unsigned v) {
    int gb_res = get_bit(*x, n);
    gb_res = gb_res << n;
    *x = (*x ^ gb_res) + (v << n);
}

/* Flips the Nth bit in X. Assumes 0 <= N <= 31.*/
void flip_bit(unsigned *x, unsigned n) {
    /* YOUR CODE HERE */
    unsigned toSet = get_bit(~get_bit(*x, n), 0);
    set_bit(x, n, toSet);
}


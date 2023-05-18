#include "transpose.h"

/* The naive transpose function as a reference. */
void transpose_naive(int n, int blocksize, int *dst, int *src) {
    for (int x = 0; x < n; x++) {
        for (int y = 0; y < n; y++) {
            dst[y + x * n] = src[x + y * n];
        }
    }
}

/* Implement cache blocking below. You should NOT assume that n is a
 * multiple of the block size. */
void transpose_blocking(int n, int blocksize, int *dst, int *src) {
    for (int r = 0 ; r < n ; r += blocksize) {
        for (int c = 0 ; c < n ; c += blocksize) {
            for (int x = r ; x < r+blocksize ; x += 1) {
                for (int y = c ; y < c+blocksize ; y += 1) {
                    if (x < n && y < n) {
                        dst[y+x*n] = src[x+y*n];
                    }
                }
            }
        }
    }
}

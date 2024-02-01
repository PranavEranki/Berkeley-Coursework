addi t0 x0 0
addi t1 x0 1
addi t2 x0 1
addi s1 x0 -1
beqtest:
    beq t1 t2 bge_equal_test
    addi a0 x0 1
bge_equal_test:
    bge t1 t2 bge_greater_test
    addi a0 x0 1
bge_greater_test:
    bge t1 t0 bge_unsign_test
    addi a0 x0 1
bge_unsign_test:
    bgeu s1 t1 blt_test
    addi a0 x0 1
blt_test:
    blt t1 t0 blt_uns_test
    addi a0 x0 1
blt_uns_test:
    bltu s1 t1 bne_test
    addi a0 x0 1
bne_test:
    bne t0 t1 ext
    addi a0 x0 1
ext:
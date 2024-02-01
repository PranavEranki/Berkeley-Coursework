.globl classify

.text
# =====================================
# COMMAND LINE ARGUMENTS
# =====================================
# Args:
#   a0 (int)        argc
#   a1 (char**)     argv
#   a1[1] (char*)   pointer to the filepath string of m0
#   a1[2] (char*)   pointer to the filepath string of m1
#   a1[3] (char*)   pointer to the filepath string of input matrix
#   a1[4] (char*)   pointer to the filepath string of output file
#   a2 (int)        silent mode, if this is 1, you should not print
#                   anything. Otherwise, you should print the
#                   classification and a newline.
# Returns:
#   a0 (int)        Classification
# Exceptions:
#   - If there are an incorrect number of command line args,
#     this function terminates the program with exit code 31
#   - If fails, this function terminates the program with exit code 26
#
# Usage:
#   main.s <M0_PATH> <M1_PATH> <INPUT_PATH> <OUTPUT_PATH>
classify:
    addi t0 x0 5
    bne a0 t0 error31
    ebreak
    addi sp sp -48
    sw s0 0(sp)
    sw s1 4(sp)
    sw s2 8(sp)
    sw s3 12(sp) #store address of row, col of m0
    sw s4 16(sp) #store address of m0
    sw s5 20(sp) #store address of row, col of m1
    sw s6 24(sp) #store address of m1
    sw s7 28(sp) #store address of row, col of input
    sw s8 32(sp) #store address of input
    sw s9 36(sp) #store address of h
    sw s10 40(sp) # store address of o
    sw ra 44(sp)
    
    mv s0 a0 
    mv s1 a1
    mv s2 a2
    
    # Read pretrained m0
    li a0 8
    jal ra malloc
    beq a0 x0 error26
    mv s3 a0
    
    lw a0 4(s1)
    add a1 x0 s3
    addi a2 s3 4
    
    jal ra read_matrix
    
    mv s4 a0
    
    # Read pretrained m1
    li a0 8
    jal ra malloc
    beq a0 x0 error26
    mv s5 a0
    
    lw a0 8(s1)
    add a1 x0 s5
    addi a2 s5 4
    
    jal ra read_matrix
    
    mv s6 a0

    # Read input matrix
    li a0 8
    jal ra malloc
    beq a0 x0 error26
    mv s7 a0
    
    lw a0 12(s1)
    add a1 x0 s7
    addi a2 s7 4
    
    jal ra read_matrix
    
    mv s8 a0
    
    # Compute h = matmul(m0, input)
    lw t1 0(s3)
    lw t2 4(s7)
    mul t1 t1 t2
    mv a0 t1
    slli a0 a0 2
    jal ra malloc
    beq a0 x0 error26
    mv s9 a0
    
    mv a0 s4
    lw a1 0(s3)
    lw a2 4(s3)
    mv a3 s8
    lw a4 0(s7)
    lw a5 4(s7)
    mv a6 s9
    
    jal ra matmul
    # Compute h = relu(h)
    mv a0 s9
    lw t0 0(s3)
    lw t1 4(s7)
    mul t0 t0 t1
    mv a1 t0
    jal ra relu
    
    # Compute o = matmul(m1, h)
    lw t1 0(s5)
    lw t2 4(s7)
    mul t1 t1 t2
    mv a0 t1
    slli a0 a0 2
    jal ra malloc
    beq a0 x0 error26
    mv s10 a0
    
    mv a0 s6
    lw a1 0(s5)
    lw a2 4(s5)
    mv a3 s9
    lw a4 0(s3)
    lw a5 4(s7)
    mv a6 s10
    
    jal ra matmul

    # Write output matrix o
    lw a0 16(s1)
    mv a1 s10
    lw a2 0(s5)
    lw a3 4(s7)
    
    jal ra write_matrix
    # Compute and return argmax(o)
    mv a0 s10
    lw t0 0(s5)
    lw t1 4(s7)
    mul a1 t0 t1
    
    jal ra argmax
    mv s0 a0
    # If enabled, print argmax(o) and newline
    li t0 0
    ebreak
    beq s2 t0 print
    
    j return
return:
    #free
    
    mv a0 s3
    jal ra free
    mv a0 s4
    jal ra free
    mv a0 s5
    jal ra free
    mv a0 s6
    jal ra free
    mv a0 s7
    jal ra free
    mv a0 s8
    jal ra free
    mv a0 s9
    jal ra free
    mv a0 s10
    jal ra free
    
    mv a0 s0
    
    lw s0 0(sp)
    lw s1 4(sp)
    lw s2 8(sp)
    lw s3 12(sp) #store address of row, col of m0
    lw s4 16(sp) #store address of m0
    lw s5 20(sp) #store address of row, col of m1
    lw s6 24(sp) #store address of m1
    lw s7 28(sp) #store address of row, col of input
    lw s8 32(sp) #store address of input
    lw s9 36(sp) #store address of h
    lw s10 40(sp) # store address of o
    lw ra 44(sp)
    addi sp sp 48
    jr ra
error31:
    li a0 31
    j exit
error26:
    li a0 26
    j quit
print:
    
    mv a0 s0
    jal ra print_int
    li a0 '\n'
    jal ra print_char
    j return
quit:
    lw s0 0(sp)
    lw s1 4(sp)
    lw s2 8(sp)
    lw s3 12(sp) #store address of row, col of m0
    lw s4 16(sp) #store address of m0
    lw s5 20(sp) #store address of row, col of m1
    lw s6 24(sp) #store address of m1
    lw s7 28(sp) #store address of row, col of input
    lw s8 32(sp) #store address of input
    lw s9 36(sp) #store address of h
    lw s10 40(sp) # store address of o
    lw ra 44(sp)
    addi sp sp 48
    j exit

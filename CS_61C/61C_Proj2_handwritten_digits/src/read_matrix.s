.globl read_matrix

.text
# ==============================================================================
# FUNCTION: Allocates memory and reads in a binary file as a matrix of integers
#
# FILE FORMAT:
#   The first 8 bytes are two 4 byte ints representing the # of rows and columns
#   in the matrix. Every 4 bytes afterwards is an element of the matrix in
#   row-major order.
# Arguments:
#   a0 (char*) is the pointer to string representing the filename
#   a1 (int*)  is a pointer to an integer, we will set it to the number of rows
#   a2 (int*)  is a pointer to an integer, we will set it to the number of columns
# Returns:
#   a0 (int*)  is the pointer to the matrix in memory
# Exceptions:
#   - If malloc returns an error,
#     this function terminates the program with error code 26
#   - If you receive an fopen error or eof,
#     this function terminates the program with error code 27
#   - If you receive an fclose error or eof,
#     this function terminates the program with error code 28
#   - If you receive an fread error or eof,
#     this function terminates the program with error code 29
# ==============================================================================
read_matrix:

    # Prologue
    #s0: store the address of the
    #s1: store the num rows address
    #s2: store the num cols address
    #s3: store the file discriptor
    #s4: address of return matrix
    #s5: size(in bytes) of the return matrix
    addi sp sp -28
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    sw s3 16(sp)
    sw s4 20(sp)
    sw s5 24(sp)
    
    add s0 x0 a0
    add s1 x0 a1
    add s2 x0 a2
    
    #sep up a1 for f open, a0 is the file path now
    addi a1 x0 0
    jal ra fopen
    li t0 -1
    beq a0 t0 err27
    
    #save file descriptor from a0 to s3
    add s3 x0 a0
    #use sp to store num rows and num cols
    addi sp sp -8
    #set up address of sp to a1
    add a1 x0 sp
    #set up a2 to 8 (read 8 bytes of the file and store on stack)
    addi a2 x0 8
    
    jal ra fread
    #check read fail
    li t0 8
    bne a0 t0 err29 
    
    #load data from sp and store to s1 s2
    lw t0 0(sp)
    sw t0 0(s1)
    lw t1 4(sp)
    sw t1 0(s2)
    #get dimension of matrix
    addi sp sp 8
    mul t0 t0 t1
    slli t0 t0 2
    mv s5 t0
    
    #malloc for the matrix
    mv a0 t0
    jal ra malloc
    #check malloc fail
    beq a0 x0 err26
    
    
    #store malloc address on s4
    mv s4 a0
    #store malloc address on a0
    mv a1 a0
    #get the file discriptor
    mv a0 s3
    mv a2 s5
    
    jal ra fread
    #check fread fail
    bne a0 s5 err29
    mv a0 s3
    jal ra fclose
    #check close fail
    li t0 -1
    beq a0 t0 err28
    
    mv a0 s4

    # Epilogue
    lw ra 0(sp)
    lw s0 4(sp)
    lw s1 8(sp)
    lw s2 12(sp)
    lw s3 16(sp)
    lw s4 20(sp)
    lw s5 24(sp)
    addi sp sp 28
    jr ra
err26: 
    li a0 26
    j quit
err27: 
    li a0 27
    j quit
err28: 
    li a0 28
    j quit
err29: 
    li a0 29
    j quit
quit:
    lw ra 0(sp)
    lw s0 4(sp)
    lw s1 8(sp)
    lw s2 12(sp)
    lw s3 16(sp)
    lw s4 20(sp)
    lw s5 24(sp)
    addi sp sp 28
    j exit
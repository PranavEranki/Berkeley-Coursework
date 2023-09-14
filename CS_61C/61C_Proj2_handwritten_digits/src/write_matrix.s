.globl write_matrix

.text
# ==============================================================================
# FUNCTION: Writes a matrix of integers into a binary file
# FILE FORMAT:
#   The first 8 bytes of the file will be two 4 byte ints representing the
#   numbers of rows and columns respectively. Every 4 bytes thereafter is an
#   element of the matrix in row-major order.
# Arguments:
#   a0 (char*) is the pointer to string representing the filename
#   a1 (int*)  is the pointer to the start of the matrix in memory
#   a2 (int)   is the number of rows in the matrix
#   a3 (int)   is the number of columns in the matrix
# Returns:
#   None
# Exceptions:
#   - If you receive an fopen error or eof,
#     this function terminates the program with error code 27
#   - If you receive an fclose error or eof,
#     this function terminates the program with error code 28
#   - If you receive an fwrite error or eof,
#     this function terminates the program with error code 30
# ==============================================================================
write_matrix:

    # Prologue
   
    addi sp sp -24
    sw ra 0(sp)
    sw s0 4(sp)
    sw s1 8(sp)
    sw s2 12(sp)
    sw s3 16(sp)
    sw s4 20(sp)
    
    #s0: store the address of the file
    #s1: store the address of the matrix in memory
    #s2: num rows
    #s3: num cols
    #s4: store the file descriptor
    add s0 x0 a0
    add s1 x0 a1
    mv s2 a2
    mv s3 a3
    
    
    
    #sep up a1 for f open, a0 is the file path now
    addi a1 x0 1
    jal ra fopen
    li t0 -1
    beq a0 t0 err27
    #store file discriptor on s4
    mv s4 a0
    
    addi sp sp -4
    #write num of rows
    mv a0 s4
    sw s2 0(sp)
    li a2 1
    li a3 4
    mv a1 sp
    jal ra fwrite
    li t0 1
    bne a0 t0 err30
    
    #write num of cols
    mv a0 s4
    sw s3 0(sp)
    li a2 1
    li a3 4
    mv a1 sp
    jal ra fwrite
    li t0 1
    bne a0 t0 err30
    
    addi sp sp 4
    
    #write the matrix
    mv a0 s4
    mul t0 s2 s3
    mv a2 t0
    li a3 4
    mv a1 s1
    
    jal ra fwrite
    mul t0 s2 s3
    bne a0 t0 err30
    
    mv a0 s4
    jal ra fclose
    #check close fail
    li t0 -1
    beq a0 t0 err28
    
    # Epilogue
    lw ra 0(sp)
    lw s0 4(sp)
    lw s1 8(sp)
    lw s2 12(sp)
    lw s3 16(sp)
    lw s4 20(sp)
    addi sp sp 24
    jr ra
err27:
    li a0 27
    j quit
err28:
    li a0 28
    j quit
err30:
    li a0 30
    j quit
quit:
    lw ra 0(sp)
    lw s0 4(sp)
    lw s1 8(sp)
    lw s2 12(sp)
    lw s3 16(sp)
    lw s4 20(sp)
    addi sp sp 24
    j exit
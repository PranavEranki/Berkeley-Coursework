#.import ../src/dot.s
#.import ../src/utils.s
.globl matmul

.text
# =======================================================
# FUNCTION: Matrix Multiplication of 2 integer matrices
#   d = matmul(m0, m1)
# Arguments:
#   a0 (int*)  is the pointer to the start of m0
#   a1 (int)   is the # of rows (height) of m0
#   a2 (int)   is the # of columns (width) of m0
#   a3 (int*)  is the pointer to the start of m1
#   a4 (int)   is the # of rows (height) of m1
#   a5 (int)   is the # of columns (width) of m1
#   a6 (int*)  is the pointer to the the start of d
# Returns:
#   None (void), sets d = matmul(m0, m1)
# Exceptions:
#   Make sure to check in top to bottom order!
#   - If the dimensions of m0 do not make sense,
#     this function terminates the program with exit code 38
#   - If the dimensions of m1 do not make sense,
#     this function terminates the program with exit code 38
#   - If the dimensions of m0 and m1 don't match,
#     this function terminates the program with exit code 38
# =======================================================
matmul:
#     jal ra randomizeCalleeSavedRegs
    addi t0 x0 1

    blt a1 t0 termi
    blt a2 t0 termi
    blt a4 t0 termi
    blt a5 t0 termi
    bne a2 a4 termi
    # Prologue
    addi sp sp -52
    #store all of s0-10 and ra to the stack
    sw s0 0(sp)
    sw s1 4(sp)
    sw s2 8(sp)
    sw s3 12(sp)
    sw s4 16(sp)
    sw s5 20(sp)
    sw s6 24(sp)
    sw s7 28(sp) #result
    sw s8 32(sp)
    sw s9 36(sp)
    sw s10 40(sp)
    sw s11 44(sp)
    sw ra 48(sp)
    #store all of a to s
    mv s0 a0
    mv s1 a1
    mv s2 a2
    mv s3 a3
    mv s4 a4
    mv s5 a5
    mv s6 a6 #s6 now point to return array
    mv s7 a6 #save the original pointer
    addi s8 x0 0 #r = 0
    addi s9 x0 0 #c = 0
    mv s10 a3 # origin: store adress of m1
    mv s11 a0 # origin: store adress of m0
    j row_count

    

row_count:
    
    bge s8 s1 loop_end
    j col_count
col_count:
    bge s9 s5 r_plus_1_and_c_0
    j loop_continue
r_plus_1_and_c_0:
    li s9 0
    addi s8 s8 1
    #move ptr1 to next row
    addi t0 x0 4
    mul t0 t0 s2
    add s0 s0 t0
    #move ptr2 to origin
    mv s3 s10
    j row_count

loop_continue:
    mv a0 s0 
    mv a1 s3
    mv a2 s2    #num of eles is col 0
    addi a3 x0 1 #stride m0 always 1
    mv a4 s5 #stride m2 is col2
    
    jal ra dot
    sw a0 0(s6)
    
    addi s6 s6 4 # move s6
    addi s3 s3 4 # move ptr m1 to the next ele
    addi s9 s9 1 # j++
    j col_count
    
    


loop_end:
    mv a0 s11
    mv a1 s1
    mv a2 s2
    mv a3 s3
    mv a4 s4
    mv a5 s5
    mv a6 s7 #store address of ret array in a6
    
    
    # Epilogue
    lw s0 0(sp)
    lw s1 4(sp)
    lw s2 8(sp)
    lw s3 12(sp)
    lw s4 16(sp)
    lw s5 20(sp)
    lw s6 24(sp)
    lw s7 28(sp)
    lw s8 32(sp)
    lw s9 36(sp)
    lw s10 40(sp)
    lw s11 44(sp)
    lw ra 48(sp)   
    addi sp sp 52
    
#     jal ra checkCalleeSavedRegs
    jr ra

termi:
    addi a0 x0 38
#     jal ra checkCalleeSavedRegs
    j exit

    

.globl dot

.text
# =======================================================
# FUNCTION: Dot product of 2 int arrays
# Arguments:
#   a0 (int*) is the pointer to the start of arr0
#   a1 (int*) is the pointer to the start of arr1
#   a2 (int)  is the number of elements to use
#   a3 (int)  is the stride of arr0
#   a4 (int)  is the stride of arr1
# Returns:
#   a0 (int)  is the dot product of arr0 and arr1
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
#   - If the stride of either array is less than 1,
#     this function terminates the program with error code 37
# =======================================================
dot:

    # Prologue
    
    li t0 1
    blt a2 t0 termi36
    blt a3 t0 termi37
    blt a4 t0 termi37
    addi sp sp -4
    sw ra 0(sp)
    mv t0 a0
    mv t1 a1
    lw t2 0(t0)
    lw t3 0(t1)
    mul t4 t2 t3 #multiply 2 first element and store at t4 (result)
    li t5 1 # i = 1
    li t6 4 #size = 4
    j loop_start
    
termi36:
    addi a0 x0 36
    j exit
    
termi37:
    addi a0 x0 37
    j exit

loop_start:
    bge t5 a2 loop_end
    j loop_continue

loop_continue:
    mul t2 a3 t6 #step_size 1 = grid1 * size
    mul t3 a4 t6 #step_size 2 = grid2 * size
    #move pointer to step_size
    add t0 t0 t2 
    add t1 t1 t3
    #load 2 elements from 2 pointers
    lw t2 0(t0)
    lw t3 0(t1)
    #multiply 2 element
    mul t2 t2 t3 
    #add product to result
    add t4 t4 t2
    addi t5 t5 1
    j loop_start
    
loop_end:
    mv a0 t4

    # Epilogue
    lw ra 0(sp)
    addi sp sp 4

    jr ra

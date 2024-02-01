.globl argmax

.text
# =================================================================
# FUNCTION: Given a int array, return the index of the largest
#   element. If there are multiple, return the one
#   with the smallest index.
# Arguments:
#   a0 (int*) is the pointer to the start of the array
#   a1 (int)  is the # of elements in the array
# Returns:
#   a0 (int)  is the first index of the largest element
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
# =================================================================
argmax:
    # Prologue
    addi t0 x0 1
    blt a1 t0 termi
    mv t0 a0 #copy address a0
    addi t1 x0 1 # i = 1
    lw t2 0(t0)  #max = arr[0]
    mv t3 x0 #index_of_max = 0
    addi t0 t0 4
    j loop_start

termi:
    addi a0 x0 36
    j exit
    
loop_start:
    beq t1 a1 loop_end
    j loop_continue
loop_continue:
    lw t4 0(t0) #temp = *t0
    beq t4 t2 equal_less_case
    bge t4 t2 greater_case
    blt t4 t2 equal_less_case
    
greater_case:
    lw t2 0(t0)
    mv t3 t1
    addi t1 t1 1
    addi t0 t0 4
    j loop_start
    
equal_less_case:
    addi t1 t1 1
    addi t0 t0 4
    j loop_start

    


loop_end:
    mv a0 t3
    # Epilogue
    
    jr ra

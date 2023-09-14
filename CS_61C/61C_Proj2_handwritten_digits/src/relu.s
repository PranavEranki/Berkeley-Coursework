.globl relu

.text
# ==============================================================================
# FUNCTION: Performs an inplace element-wise ReLU on an array of ints
# Arguments:
#   a0 (int*) is the pointer to the array
#   a1 (int)  is the # of elements in the array
# Returns:
#   None
# Exceptions:
#   - If the length of the array is less than 1,
#     this function terminates the program with error code 36
# ==============================================================================
relu:
    # Prologue
    addi t0 x0 1
    blt a1 t0 termi
    mv t0 a0 #copy addres a0
    add t1 x0 x0 # i = 0
    
    j loop_start

    
loop_start:
    bge t1 a1 loop_end #i >= a1 :loop end
    j loop_continue
loop_continue:
    
    lw t3 0(t0)
    addi t0 t0 4
    addi t1 t1 1
    bge t3 x0 loop_start
    addi t0 t0 -4
    mv t3 x0
    sw t3 0(t0) 
    addi t0 t0 4
    j loop_start
    
termi:
    addi a0 x0 36
    
    j exit
    
loop_end:
    nop

    # Epilogue


    jr ra

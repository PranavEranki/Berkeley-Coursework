addi t0 x0 1
addi t1 t0 2
andi t2 t1 1
ori s0 t2 15
xori s1 s0 15
slli a0 s1 3
srli ra a0 3
srai sp ra 2
slti t0 sp 4
# Coursework I enjoyed while at UC Berkeley

## Focus: Systems, Security, Full-stack, Algorithms

<b> Each of the below links to a folder with materials pertaining to each class, complete with a README, homework/discussions/projects where applicable, and key takeaways from each class. </b>

I graduated after 2 hectic years full of late nights grinding out projects, early mornings preparing for midterms, and weekends spent making memories. I learned a lot about engineering, developed my technical skillset/portfolio, and most importantly, grew tenfold as a person and young adult. I tried to actively learn from my regrets, journal and introspect, and stay hungry for improvement. 

Although this won't be a huge blurb about everything I learned about myself as a person, and will mainly focus on my classes, _I gaurantee that college will teach you just as much, if not more, about your personality, deficiencies, strengths, traumas, and potential._

I hope this repository will serve useful for you. Best of luck in everything you do, and may you change the world for the better.
Best,
Pranav

</b>


## [CS 61A](CS_61A)
- Intro to computer programming at a college level
- Concepts:
    - OOP, higher order functons / envs, lambda, functional abstraction
    - Recursion, trees and tree recursion, sequences (strs, lists, buffers), containers (dicts, etc)
    - Iterators / generators
    - Scheme (functional programming language, like Lisp)
- [CHEATSHEET](61a-final-study-guide.pdf)

## [CS 47B](CS_47B)
- Bridge course used to fufill my 61B requirement;
    - Focus on 
        - Intro to DS&A (linked lists, b-trees, BSTs, heaps, PQs, graphs, MSTs, sorting ) 
        - Advanced OOP concepts (polymorphism, asymptotics, ADTs, etc)
    - <b> Replicated the WWII [ENIGMA machine](CS_47B/proj1), which can be passed different configurations and messages to encode/decode </b>
    - <b> Created a basic version of Git, [GITLET](CS_47B/proj3), capable of efficient adds/commits/rm/checkouts/logs/status/branches/merges/resets</b>

## CS 70
- This class was very theoretically challenging and helped  develop mathematical maturity useful for EE / upper level CS classes.
- <b> Basics: </b> 
    - prop logic, proofs & weak/strong inducton, set theory, modular arithmetic
    - countability and computability, graph theory, polynomials
    - probability theory & conditional probability, random variables, variance/covariance, distributions (geometric/poisson/continuous), concentration inequalities
- <b> Higher level concepts: </b> 
    - RSA, error correcting codes, markov chains, coupon collector problem
- [Cheatsheet](CS70_Cheatsheet.pdf) 

## [CS 188](CS_188)
- This class introduces you to the basics of artificial intelligence. Across the key projects and homeworks, you learn many key concepts. The class uses a growingly complex implementation of Pacman to show you these concepts.
- <b> Concepts </b>: 
    - rational agents, state spaces & search trees
    - uninformed/informed search (DFS/BFS/UCS, A*, heuristics, local search)
        - [Project 1: Search](CS_188/search/)
    - games (trees, minimax & pruning, expectimax, monte carlo)
        - [Project 2: Multiagent Search](CS_188/multiagent/)
    - logic (prop logic, planning, inference, theorem proving, satisfiability, FOL)
        - [Project 3: Logic](CS_188/logic/)
    - probability (bayes nets, markov chains, hidden markov models + forward/viterbi, dyamic nets, decision networks, value of perfect information)
        - [Project 4: Tracking](CS_188/tracking)
    - Non-deterministic search (Markov decision processes, bellman, value/policy iteration, q-values)
    - ML (naive bayes, regression, perceptrons, reinforcement learning, Q-learning, exploration functions)
        - [Project 5: ML](CS_188/machinelearning)
        - [Project 6: RL](CS_188/reinforcement/)

## CS 170
- This class builds on the mathematical maturity of CS 70 to introduce advanced efficient algorithm, intractable problems, and the general nature of P vs NP.
- Refer to [DPV for more](https://people.eecs.berkeley.edu/~vazirani/algorithms/chap2.pdf), and change the chapter (i.e. /chap3.pdf)
- Concepts: 
    - Big O, recurrence relations, master theorem
    - Fast multiplication (arithmetic, matrix, & polynomial / FFT)
    - Complex #s, roots of unity, fourier transform
    - Median finding (quicksort)
    - Graph theory & DAGs
        - DFS using stack, BFS using queue, topological sort, post/pre-order traversals
        - SCCs, MST (Kruskal's), min cut max flow, huffman encoding, set cover
        - Paths, single source shortest paths, Djikstras,
    - Dynamic programming
    - Linear programming, Simplex
    - Network flow, bipartite matching, duality
    - Zero-sum games, multiplicative updates
    - P vs NP, NP hard, NP complete, reductions, approximations


## CS 61C
- This class teaches C, assembly level programming (Risc-V), code translation, computer organization, caches, performance measurement, parallelism, CPU design, warehouse-scale computing, and related topics.
- CONCEPTS + links if found:
    - [Number representation notes](https://inst.eecs.berkeley.edu/~cs61c/resources/Anusha_Number_Rep_Notes.pdf)
    - [Detailed C Notes](https://inst.eecs.berkeley.edu/~cs61c/resources/HarveyNotesC1-3.pdf)
    - [C memory management](https://inst.eecs.berkeley.edu/~cs61c/sp21/resources-pdfs/pnh.stg.mgmt.pdf)
    - [RISC-V conventions](https://inst.eecs.berkeley.edu/~cs61c/resources/RISCV_Calling_Convention.pdf)
    - RISC-V Instruction formats and 5 Stage Pipeline
    - Compiler, Assembler, Linker, Loaded
    - [Synchronous digital systems](https://inst.eecs.berkeley.edu/~cs61c/resources/sds.pdf) and [FSMs](https://inst.eecs.berkeley.edu/~cs61c/resources/state.pdf)
    - [Combinational logic blocks](https://inst.eecs.berkeley.edu/~cs61c/resources/blocks.pdf), clocks, pipelining / parallelism
    - Caches (direct mapped, multilevel) and virtual memory
    - Dependability, ECC, RAID, MapReduce/Spark, warehouse computing
- [Labs folder](CS_61C) contains all the labs that have been done covering all these subjects
- 61C had three projects:
    - 1: Writing snek in C
    - 2: Using RISC-v assembly to classify handwritten digits 
    - 3: Building a CPU using Risc-V 5 stage pipeline to run every supported RISC-V operation, designing own ALU, RegFile, ImmGen, and implementing pipelining.

## CS 161
- Easily one of my favorite classes at Berkeley, this class teaches memory safety, crytography, web security, and network security fundamentals for aspiring engineers. They maintain a beautiful [textbook](https://textbook.cs161.org/) which you should check out.
- Projects:
    - Proj 1: Exploiting a range of memory security vulnerabilities
    - [Proj 2](CS_161/sp23-proj2-tahir-pranav-main/): Designed a secure file sharing system with user auth, save/load/overwrite/append/share/revoke access for all user files across sessions. Refer to [design doc](CS_161/sp23-proj2-tahir-pranav-main/Informal_Design_Doc.pdf) for more details.
    - [Proj 3]: web safety attacks, including sql injection, session token and cookie manipulation, and stored / reflected XSS attack.

## CS 186[CS_186]
- This class covers database systems and their associated concepts, including SQL, disks and files, B+ trees, buffer management, relational algebra, hashing/sorting, iterators and joins. query optimiation, transactions and concurrency, design, recovery, parallel query processing, distributed transactions, NoSQL, and MapReduce/Spark. Their [notes](https://cs186berkeley.net/notes/) are also well maintained, refer to those.
- For our projects in this class, we:
    - wrote complex, nested SQL queries covering wide functionality
    - designed and tested B+ tree indices
    - wrote database iterators and implemented all efficient join algorithms, concurrently optimizing queries and conducting plan space search similar to System R
    - Implemented a concurrency locking system with queueing, adding multigranularity locking constraints and 2 phase locking, 
    - Wrote write-ahead logging and support for savepoints, rollbacks, and ACID compliant restart recovery
    - Wrote efficient, complex, pipelined NoSQL code for MongoDB.

## Eleng 122
- This mathematically rigorous communications class taught me the design, implementation, and management of communication networks. The [textbook is downloaded and linked here](communications.pdf), and chiefly covers:
    - A overview of how today’s Internet works and it's architecture
    - Key ideas behind Ethernet, WiFi networks, routing, internetworking, and TCP. 
    - Briefly covers probability, calculus, mathematical models, and markov chain concepts for background knowledge. 
    - LTE wireless networks, QoS, network protocols
    - Physical layer technologies

## [Comp Programming Decal](comp_decal)
- This decal run by Jelani Nelson and the Comp Programming Club @ Berkeley aims to introduce the basics of comp programming. I have attached <b>all lectures except the intro in the above folder </b>. These lectures were supplemented with Codeforces problems, some of which are mentioned by name in the lectures. 

## Cogsci 131
- This class had a very interesting, informative lecture on computational models of cognition, ...
    - covering the logic behind neural networks
    - providing insight how humans solve challenging computational problems
    - exploring three ways in which researchers have attempted to formalize cognition -- symbolic approaches, neural networks, and probability and statistics -- considering the strengths and weaknesses of each.
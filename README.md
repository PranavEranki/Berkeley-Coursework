# Coursework I enjoyed while at UC Berkeley

## Focus: Systems, Security, Full-stack, Algorithms, SWE

<b> Each of the below links to a folder with materials pertaining to each class, complete with a README, homework/discussions/projects where applicable, and key takeaways from each class. </b>

I hope this repository will serve useful for you. Best of luck in everything you do, and may you change the world for the better.

</b>

*As an aside, and an obvious note, please DO NOT COPY any of the code or logic for projects, homeworks, designs, etc within this repository. Thank you. *


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
    - 1: [Writing snek in C](Cs_61C/61C_Proj1_Snek/)
    - 2: [Using RISC-v assembly to classify handwritten digits](CS_61C/61C_Proj2_handwritten_digits/)
    - 3: [Building a CPU using Risc-V 5 stage pipeline to run every supported RISC-V operation, designing own ALU, RegFile, ImmGen, and implementing pipelining.](CS_61C/61C_Proj3_CPU/)

## CS 161
- Easily one of my favorite classes at Berkeley, this class teaches memory safety, crytography, web security, and network security fundamentals for aspiring engineers. They maintain a beautiful [textbook](https://textbook.cs161.org/) which you should check out.
- Projects:
    - Proj 1: Exploiting a range of memory security vulnerabilities, see [our code here](CS_161/Proj1/) which has the different "flags" that we completed.
    - [Proj 2](CS_161/sp23-proj2-tahir-pranav-main/): Designed a secure file sharing system with user auth, save/load/overwrite/append/share/revoke access for all user files across sessions. Refer to [design doc](CS_161/sp23-proj2/Informal_Design_Doc.pdf) for more details, or see [our code here](S_161/sp23-proj2).
    - [Proj 3]: web safety attacks, including sql injection, session token and cookie manipulation, stored / reflected XSS attack, and sql vulnerability / hash decoders. Deliverables completed elsewhere, cannot share this.

## [CS 186](CS_186)
- This class covers database systems and their associated concepts, including SQL, disks and files, B+ trees, buffer management, relational algebra, hashing/sorting, iterators and joins. query optimiation, transactions and concurrency, design, recovery, parallel query processing, distributed transactions, NoSQL, and MapReduce/Spark. Their [notes](https://cs186berkeley.net/notes/) are also well maintained, refer to those.
- For our projects in this class, we:
    - wrote complex, nested SQL queries covering wide functionality
    - designed and tested B+ tree indices
    - wrote database iterators and implemented all efficient join algorithms, concurrently optimizing queries and conducting plan space search similar to System R
    - Implemented a concurrency locking system with queueing, adding multigranularity locking constraints and 2 phase locking, 
    - Wrote write-ahead logging and support for savepoints, rollbacks, and ACID compliant restart recovery
    - Wrote efficient, complex, pipelined NoSQL code for MongoDB.
- You can see my specific code for Projects 1-5 (the first five bullet points above) in [THIS FOLDER](CS_186).

## Eleng 122
- This mathematically rigorous communications class taught me the design, implementation, and management of communication networks. The [textbook is downloaded and linked here](communications.pdf), and chiefly covers:
    - A overview of how today’s Internet works and it's architecture
    - Key ideas behind Ethernet, WiFi networks, routing, internetworking, and TCP. 
    - Briefly covers probability, calculus, mathematical models, and markov chain concepts for background knowledge. 
    - LTE wireless networks, QoS, network protocols
    - Physical layer technologies
- For our final project in this class, my team and I developed a [Starlink LEO constellation network simulation](https://github.com/sidharthrajaram/starsim).

## CS 169A
- This class focused on an introduction to SWE on large software systems from an Agile methodologies standpoint.
    - Agile vs Plan on Doc methodologies, behavior and test-driven-development
    - Restful SaaS apps and microservices
    - Ruby programs, Rails applications
    - Unit & module tests, understanding design patterns, identifying security/performance problems
- The textbook pdf can be found [for free here](http://www.saasbook.info/).

## [Comp Programming Decal](comp_decal)
- This decal run by Jelani Nelson and the Comp Programming Club @ Berkeley aims to introduce the basics of comp programming. I have attached <b>all lectures except the intro in the above folder </b>. These lectures were supplemented with Codeforces problems, some of which are mentioned by name in the lectures.

## Cogsci 131
- This class had a very interesting, informative lecture on computational models of cognition, but most of the material from this class was simple data analysis type stuff, building neural networks, etc.
    - covering the logic behind neural networks
    - providing insight how humans solve challenging computational problems
    - exploring three ways in which researchers have attempted to formalize cognition -- symbolic approaches, neural networks, and probability and statistics -- considering the strengths and weaknesses of each.

## CS 162 Lectures
- I watched this [free lecture series on operating systems and systems programming](https://www.youtube.com/playlist?list=PLF2K2xZjNEf97A_uBCwEl61sdxWVP7VWC) on and off just to get a better understanding. A lot of the material overlaps with CS 186 and Eleng 122, which is why I found it very helpful 

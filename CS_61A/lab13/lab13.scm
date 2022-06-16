(define (split-at lst n)
(define (helper left right number)
    (if (= number n) (list left right)
    (helper (cons left (car right)) (cdr right) (+ number 1)))
)
(helper nil lst 0)
)

(define (compose-all funcs)
(lambda (x)
    (if (null? funcs) x
    ((compose-all (cdr funcs)) ((car funcs) x) )))
)

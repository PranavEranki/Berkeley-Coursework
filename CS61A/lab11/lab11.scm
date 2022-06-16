(define-macro (def func args body)
  `(define ,func (lambda ,args ,body)))

(define (tail-replicate x n)
(define (helper lst n) (if (= n 0) lst (helper (cons x lst) (- n 1))))
(helper () n))

(define (exp b n) 
(define (helper product n)
(if (= n 0) product (helper (* product b) (- n 1))))
(helper 1 n))

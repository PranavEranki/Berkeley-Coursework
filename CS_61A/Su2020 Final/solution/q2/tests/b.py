test = {'name': 'b',
 'points': 0.1,
 'suites': [{'cases': [{'code': 'scm> (define zeros (cons-stream 0 zeros))\n'
                                'zeros\n'
                                '\n'
                                'scm> (define ones (cons-stream 1 ones))\n'
                                'ones\n'
                                '\n'
                                'scm> (take 10 (studio-switch zeros ones))\n'
                                '(0 0 0 0 0 0 0 0 0 0)\n'
                                '\n'
                                'scm> (take 10 (studio-switch ones zeros))\n'
                                '(1 1 1 1 1 1 1 1 1 1)\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'},
            {'cases': [{'code': 'scm> (define twos (cons-stream 2 twos))\n'
                                'twos\n'
                                '\n'
                                'scm> (define threes (cons-stream 3 threes))\n'
                                'threes\n'
                                '\n'
                                'scm> (take 10 (studio-switch twos threes))\n'
                                '(2 2 2 2 2 2 2 2 2 2)\n'
                                '\n'
                                'scm> (take 10 (studio-switch threes twos))\n'
                                '(3 3 3 3 3 3 3 3 3 3)\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'},
            {'cases': [{'code': 'scm> (define a (cons-stream 0 (cons-stream '
                                "'guitar (cons-stream 1 (cons-stream 'guitar "
                                '(cons-stream 2 a))))))\n'
                                'a\n'
                                '\n'
                                "scm> (define b (cons-stream 'x (cons-stream "
                                "'guitar (cons-stream 'y (cons-stream 'guitar "
                                '(cons-stream 2 a))))))\n'
                                'b\n'
                                '\n'
                                'scm> (take 10 a)\n'
                                '(0 guitar 1 guitar 2 0 guitar 1 guitar 2)\n'
                                '\n'
                                'scm> (take 10 b)\n'
                                '(x guitar y guitar 2 0 guitar 1 guitar 2)\n'
                                '\n'
                                'scm> (take 10 (studio-switch a b))\n'
                                '(0 x 1 y 2 0 2 0 1 1)\n'
                                '\n'
                                'scm> (take 10 (studio-switch b b))\n'
                                '(x x y y 2 0 2 0 1 1)\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'},
            {'cases': [{'code': 'scm> (define a (cons-stream 2 (cons-stream '
                                "'guitar (cons-stream 3 (cons-stream 'guitar "
                                '(cons-stream 4 a))))))\n'
                                'a\n'
                                '\n'
                                "scm> (define b (cons-stream 'x (cons-stream "
                                "'y (cons-stream 'guitar (cons-stream 'guitar "
                                '(cons-stream 7 a))))))\n'
                                'b\n'
                                '\n'
                                'scm> (take 10 a)\n'
                                '(2 guitar 3 guitar 4 2 guitar 3 guitar 4)\n'
                                '\n'
                                'scm> (take 10 b)\n'
                                '(x y guitar guitar 7 2 guitar 3 guitar 4)\n'
                                '\n'
                                'scm> (take 10 (studio-switch a b))\n'
                                '(2 x y 3 4 2 7 2 3 3)\n'
                                '\n'
                                'scm> (take 10 (studio-switch b b))\n'
                                '(x y x y 7 2 7 2 3 3)\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'}]}
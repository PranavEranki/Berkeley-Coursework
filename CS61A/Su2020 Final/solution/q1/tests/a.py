test = {'name': 'a',
 'points': 0.1,
 'suites': [{'cases': [{'code': "scm> (cabinet-filter '(1 2 3) (lambda (x) (= "
                                'x 2)))\n'
                                '(1 3)\n'
                                '\n'
                                "scm> (cabinet-filter '(1 2 3) (lambda (x) "
                                '(list? x)))\n'
                                '(1 2 3)\n'
                                '\n'
                                "scm> (cabinet-filter '(1 (2 3)) (lambda (x) "
                                '(list? x)))\n'
                                '(1)\n'
                                '\n'
                                "scm> (cabinet-filter '((1) (2 3)) (lambda (x) "
                                '(list? x)))\n'
                                '()\n'
                                '\n'
                                "scm> (cabinet-filter '((1) (2 3)) (lambda (x) "
                                '(and (list? x) (equal? (car x) 2))))\n'
                                '((1))\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'},
            {'cases': [{'code': "scm> (cabinet-filter '(5 6 7) (lambda (x) (= "
                                'x 5)))\n'
                                '(6 7)\n'
                                '\n'
                                "scm> (cabinet-filter '(8 12 14) (lambda (x) "
                                '(list? x)))\n'
                                '(8 12 14)\n'
                                '\n'
                                "scm> (cabinet-filter '((2 3) 1) (lambda (x) "
                                '(list? x)))\n'
                                '(1)\n'
                                '\n'
                                "scm> (cabinet-filter '((9 3) (8)) (lambda (x) "
                                '(list? x)))\n'
                                '()\n'
                                '\n'
                                "scm> (cabinet-filter '((1) (6 3)) (lambda (x) "
                                '(and (list? x) (equal? (car x) 6))))\n'
                                '((1))\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'}]}
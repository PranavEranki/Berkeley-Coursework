test = {'name': 'a',
 'points': 0.1,
 'suites': [{'cases': [{'code': 'scm> (ladder-locator 10)\n'
                                '(1 0)\n'
                                '\n'
                                'scm> (ladder-locator 10001)\n'
                                '(1 0 0 0 1)\n'
                                '\n'
                                'scm> (ladder-locator 8080)\n'
                                '((8) 0 (8) 0)\n'
                                '\n'
                                'scm> (ladder-locator 123456780)\n'
                                '(1 2 3 4 5 6 7 (8) 0)\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'},
            {'cases': [{'code': 'scm> (ladder-locator 42)\n'
                                '(4 2)\n'
                                '\n'
                                'scm> (ladder-locator 31201)\n'
                                '(3 1 2 0 1)\n'
                                '\n'
                                'scm> (ladder-locator 7881)\n'
                                '(7 (8) (8) 1)\n'
                                '\n'
                                'scm> (ladder-locator 211823018)\n'
                                '(2 1 1 (8) 2 3 0 1 (8))\n'
                                '\n'
                                'scm> (length (ladder-locator (expt 10 100)))\n'
                                '101\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'}]}
test = {'name': 'q2',
 'points': 1,
 'suites': [{'cases': [{'code': 'scm> (ladder-locator 12)\n'
                                '(1 2)\n'
                                '\n'
                                'scm> (ladder-locator 1881)\n'
                                '(1 (8) (8) 1)\n'
                                '\n'
                                'scm> (ladder-locator 0) ; no digits\n'
                                '()\n'
                                '\n'
                                'scm> (ladder-locator 88888888)\n'
                                '((8) (8) (8) (8) (8) (8) (8) (8))\n'
                                '\n'
                                'scm> (ladder-locator 1128651)\n'
                                '(1 1 2 (8) 6 5 1)\n'
                                '\n'
                                "scm> (define just-guitar (cons-stream 'guitar "
                                'just-guitar))\n'
                                'just-guitar\n'
                                '\n'
                                'scm> (define two (cons-stream 1 (cons-stream '
                                "'guitar two)))\n"
                                'two\n'
                                '\n'
                                "scm> (define three (cons-stream 'x "
                                "(cons-stream 'y (cons-stream 'guitar "
                                'three))))\n'
                                'three\n'
                                '\n'
                                'scm> (take 10 two)\n'
                                '(1 guitar 1 guitar 1 guitar 1 guitar 1 '
                                'guitar)\n'
                                '\n'
                                'scm> (take 10 three)\n'
                                '(x y guitar x y guitar x y guitar x)\n'
                                '\n'
                                'scm> (take 10 (studio-switch two three))\n'
                                '(1 x y 1 x y 1 x y 1)\n'
                                '\n'
                                'scm> (take 10 (studio-switch two '
                                'just-guitar))\n'
                                '(1 1 1 1 1 1 1 1 1 1)\n'
                                '\n'
                                'scm> (take 10 (studio-switch three three))\n'
                                '(x y x y x y x y x y)\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'}]}
test = {'name': 'q1',
 'points': 1,
 'suites': [{'cases': [{'code': "scm> (cabinet-filter '(1 (2) (2 3)) (lambda "
                                '(x) (and (number? x) (even? x))))\n'
                                '(1 () (3))\n'
                                '\n'
                                "scm> (cabinet-filter '(1 (2) (2 3) 4 5 6) "
                                'list?)\n'
                                '(1 4 5 6)\n'
                                '\n'
                                "scm> (cabinet-filter '(1 2 3 4) list?)\n"
                                '(1 2 3 4)\n'
                                '\n'
                                "scm> (cabinet-filter '(i accidentally broke "
                                "my computer) (lambda (x) (equal? x 'broke)))\n"
                                '(i accidentally my computer)\n'
                                '\n'
                                "scm> (cabinet-filter '(a (a) ((a)) (((a)))) "
                                "(lambda (x) (equal? x 'a)))\n"
                                '(() (()) ((())))\n'
                                '\n'
                                'scm> (remove-comments ((comment-starts-here '
                                'hi) + 2 3))\n'
                                '5\n'
                                '\n'
                                "scm> (remove-comments '(this is "
                                '(comment-starts-here even-when-quoted) a '
                                'list))\n'
                                '(this is a list)\n'
                                '\n'
                                "scm> (remove-comments '((this is not a "
                                'comment comment-starts-here)))\n'
                                '((this is not a comment '
                                'comment-starts-here))\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'},
            {'cases': [{'code': 'scm> (remove-comments (if #f 1 '
                                '(comment-starts-here this is a comment) 2))\n'
                                '2\n'
                                '\n'
                                "scm> (remove-comments '((comment-starts-here "
                                'this is a comment)))\n'
                                '()\n'
                                '\n'
                                "scm> (remove-comments '((this is not a "
                                'comment comment-starts-here)))\n'
                                '((this is not a comment '
                                'comment-starts-here))\n'
                                '\n'
                                'scm> (remove-comments (comment-starts-here '
                                'cannot delete the entire thing so this is an '
                                'error))\n'
                                'SchemeError\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'},
            {'cases': [{'code': 'scm> (remove-comments (if #f 1 '
                                '(comment-starts-here this is a comment) 8))\n'
                                '8\n'
                                '\n'
                                "scm> (remove-comments '(5 "
                                '(comment-starts-here this is a cool '
                                'comment)))\n'
                                '(5)\n'
                                '\n'
                                "scm> (remove-comments '((this is not a "
                                'comment-starts-here cool comment)))\n'
                                '((this is not a comment-starts-here cool '
                                'comment))\n'
                                '\n'
                                'scm> (remove-comments (comment-starts-here '
                                'cannot delete the entire thing so this is an '
                                'error))\n'
                                'SchemeError\n'
                                '\n'
                                "scm> (remove-comments (lambda (x) ('(5) "
                                '(print 2 (comment-starts-here ok)) '
                                '(comment-starts-here okpy))))\n'
                                '(lambda (x) ((quote (5)) (print 2)))\n'}],
             'scored': True,
             'setup': 'scm> (load-all ".")',
             'type': 'scheme'}]}
test = {'name': 'q4',
 'points': 1,
 'suites': [{'cases': [{'code': '>>> salmon1 = Link(0, Link(1, Link(2, '
                                'Link(3,  Link(5, Link(9))))))\n'
                                '\n'
                                '>>> salmon2 = Link(1, Link(4))\n'
                                '\n'
                                '>>> symdiff([salmon1, salmon2])\n'
                                'Link(0, Link(2, Link(3, Link(4, Link(5, '
                                'Link(9))))))\n'
                                '\n'
                                '>>> salmon1 # unchanged\n'
                                'Link(0, Link(1, Link(2, Link(3, Link(5, '
                                'Link(9))))))\n'
                                '\n'
                                '>>> salmon2 # unchanged\n'
                                'Link(1, Link(4))\n'
                                '\n'
                                '>>> salmon1 = Link(0, Link(1, Link(2, '
                                'Link(3,  Link(5, Link(9))))))\n'
                                '\n'
                                '>>> salmon2 = Link(1, Link(4))\n'
                                '\n'
                                '>>> symdiff([salmon1, salmon1, salmon2])\n'
                                'Link(1, Link(4))\n'
                                '\n'
                                '>>> salmon3 = Link(0, Link(1, Link(2, '
                                'Link(3,  Link(5, Link(9))))))\n'
                                '\n'
                                '>>> salmon4 = Link(1, Link(2, Link(3,  '
                                'Link(5, Link(9)))))\n'
                                '\n'
                                '>>> symdiff([salmon3, salmon4])\n'
                                'Link(0)\n'
                                '\n'
                                '>>> symdiff([salmon1, salmon2, salmon3, '
                                'salmon4])\n'
                                'Link(2, Link(3, Link(4, Link(5, Link(9)))))\n'
                                '\n'
                                '>>> s = Link(1, Link(2, Link(3, Link(4))))\n'
                                '\n'
                                '>>> len(s)\n'
                                '4\n'
                                '\n'
                                '>>> s[2]\n'
                                '3\n'
                                '\n'
                                '>>> s\n'
                                'Link(1, Link(2, Link(3, Link(4))))\n'}],
             'scored': True,
             'setup': '>>> from q4 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> symdiff([Link(1), Link(2), Link(3), '
                                'Link(4), Link(5)])\n'
                                'Link(1, Link(2, Link(3, Link(4, Link(5)))))\n'
                                '\n'
                                '>>> symdiff([Link(1, Link(2, Link(3, Link(4, '
                                'Link(5)))))])\n'
                                'Link(1, Link(2, Link(3, Link(4, Link(5)))))\n'
                                '\n'
                                '>>> symdiff([Link(1), Link.empty, Link(-2, '
                                'Link(3))])\n'
                                'Link(-2, Link(1, Link(3)))\n'
                                '\n'
                                '>>> symdiff([Link(1), Link(1)]) is '
                                'Link.empty\n'
                                'True\n'
                                '\n'
                                '>>> symdiff([Link(2), Link(2, Link(3)), '
                                'Link(3)]) is Link.empty\n'
                                'True\n'}],
             'scored': True,
             'setup': '>>> from q4 import *',
             'type': 'doctest'}]}
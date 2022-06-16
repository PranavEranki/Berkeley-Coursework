test = {'name': 'a',
 'points': 0.1,
 'suites': [{'cases': [{'code': '>>> t3 = Tree(10, [Tree(20), Tree(30, '
                                '[Tree(50)]), Tree(40, [Tree(70)])])\n'
                                '\n'
                                '>>> same_length(t3, 4)\n'
                                '\n'
                                '>>> print(t3)\n'
                                '10\n'
                                '    20\n'
                                '        hello\n'
                                '            hello\n'
                                '                hello\n'
                                '    30\n'
                                '        50\n'
                                '            hello\n'
                                '                hello\n'
                                '    40\n'
                                '        70\n'
                                '            hello\n'
                                '                hello\n'}],
             'scored': True,
             'setup': '>>> from q3 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> t4 = Tree(900, [Tree(800, [Tree(700, '
                                '[Tree(600, [Tree(500, [Tree(400)])])])]), '
                                'Tree(300, [Tree(200, [Tree(100)])])])\n'
                                '\n'
                                '>>> same_length(t4, 4)\n'
                                '\n'
                                '>>> print(t4)\n'
                                '900\n'
                                '    800\n'
                                '        700\n'
                                '            600\n'
                                '                500\n'
                                '    300\n'
                                '        200\n'
                                '            100\n'
                                '                hello\n'}],
             'scored': True,
             'setup': '>>> from q3 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> t_nothing = Tree(1, [Tree(2), Tree(3), '
                                'Tree(4), Tree(5)])\n'
                                '\n'
                                '>>> same_length(t_nothing, 1)\n'
                                '\n'
                                '>>> print(t_nothing)\n'
                                '1\n'
                                '    2\n'
                                '    3\n'
                                '    4\n'
                                '    5\n'}],
             'scored': True,
             'setup': '>>> from q3 import *',
             'type': 'doctest'}]}
test = {'name': 'b',
 'points': 0.1,
 'suites': [{'cases': [{'code': '>>> t3 = Tree(10, [Tree(20, [Tree("hello", '
                                '[Tree("hello", [Tree("hello")])])]), Tree(30, '
                                '[Tree(50, [Tree("hello", '
                                '[Tree("hello")])])]), Tree(40, [Tree(70, '
                                '[Tree("hello", [Tree("hello")])])])])\n'
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
                                '                hello\n'
                                '\n'
                                '>>> shortest_no_hello(t3)\n'
                                '[10, 20]\n'}],
             'scored': True,
             'setup': '>>> from q3 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> t4 = Tree(900, [Tree(800, [Tree(700, '
                                '[Tree(600, [Tree(500)])])]), Tree(300, '
                                '[Tree(200, [Tree(100, [Tree("hello")])])])])\n'
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
                                '                hello\n'
                                '\n'
                                '>>> shortest_no_hello(t4)\n'
                                '[900, 300, 200, 100]\n'}],
             'scored': True,
             'setup': '>>> from q3 import *',
             'type': 'doctest'}]}
test = {'name': 'q6',
 'points': 1,
 'suites': [{'cases': [{'code': '>>> compress([1,1,1], [3])\n'
                                'True\n'
                                '\n'
                                '>>> compress([1,1,1], [2])\n'
                                'False\n'
                                '\n'
                                '>>> compress([1,1,1], [1])\n'
                                'True\n'
                                '\n'
                                '>>> compress([1,2,3], [1,5])\n'
                                'True\n'
                                '\n'
                                '>>> compress([1,2,3], [2])\n'
                                'True\n'
                                '\n'
                                '>>> compress([], [1,2,3])\n'
                                'False\n'
                                '\n'
                                '>>> compress([1,2,3], [])\n'
                                'False\n'
                                '\n'
                                '>>> compress([], [])\n'
                                'True\n'
                                '\n'
                                '>>> compress([1,4,2,8,3,9,4], [31])\n'
                                'True\n'
                                '\n'
                                '>>> compress([1,4,2,8,3,9,4], [3,5,5])\n'
                                'True\n'}],
             'scored': True,
             'setup': '>>> from q6 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> compress([1, 2, 3, 4, 5, 9], [0])\n'
                                'True\n'
                                '\n'
                                '>>> compress([5], [0])\n'
                                'False\n'
                                '\n'
                                '>>> compress([0], [0])\n'
                                'True\n'
                                '\n'
                                '>>> compress([1, 2, 3, 4, 5], [1, 2, 3, 4, '
                                '5])\n'
                                'True\n'}],
             'scored': True,
             'setup': '>>> from q6 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> compress([2, 3, 4, 3, 4, 7], [1])\n'
                                'True\n'
                                '\n'
                                '>>> compress([5, 6, 7, 8], [0, 2, 3])\n'
                                'False\n'
                                '\n'
                                '>>> compress([5], [5])\n'
                                'True\n'
                                '\n'
                                '>>> compress([2, 3], [2, 3])\n'
                                'True\n'}],
             'scored': True,
             'setup': '>>> from q6 import *',
             'type': 'doctest'}]}
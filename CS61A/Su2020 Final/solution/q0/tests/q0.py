test = {'name': 'q0',
 'points': 1,
 'suites': [{'cases': [{'code': ">>> assert mc_result_compvsinterp in ['True, "
                                'there is no way to convert an interpreter to '
                                "a compiler', 'False, you can turn an "
                                'interpreter into a compiler via a Futamura '
                                "projection', 'False, compilers and "
                                "interpreters are the same'], 'Selected an "
                                "invalid option'\n"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> mc_result_compvsinterp\n'
                                "'False, you can turn an interpreter into a "
                                "compiler via a Futamura projection'"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'},
            {'cases': [{'code': ">>> assert mc_result_pyfeatures in ['Memory "
                                "Management', 'Functions', 'Coroutines', 'Lazy "
                                "Evaluation'], 'Selected an invalid "
                                "option'\n"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'},
            {'cases': [{'code': ">>> mc_result_pyfeatures\n'Lazy Evaluation'"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> assert mc_result_regression in '
                                "['Hyperplexed', 'Parallel', 'Orthogonal', "
                                "'Triangular'], 'Selected an invalid "
                                "option'\n"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'},
            {'cases': [{'code': ">>> mc_result_regression\n'Orthogonal'"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> assert mc_result_machine_learning in '
                                "['Determine f(x)', 'Determine f', 'Determine "
                                "x'], 'Selected an invalid option'\n"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'},
            {'cases': [{'code': '>>> mc_result_machine_learning\n'
                                "'Determine f'"}],
             'scored': True,
             'setup': '>>> from q0 import *',
             'type': 'doctest'}]}
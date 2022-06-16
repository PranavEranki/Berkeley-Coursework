test = {'name': 'b',
 'points': 0.1,
 'suites': [{'cases': [{'code': 'sqlite> SELECT * FROM partb;\n'
                                '***|2\n'
                                '*****|3\n'}],
             'scored': True,
             'setup': 'sqlite> .read q7.sql',
             'type': 'sqlite'},
            {'cases': [{'code': 'sqlite> SELECT * FROM partb;\n'
                                'MANUALMANUALMANUALMANUALMANUALMANUAL\n'}],
             'scored': True,
             'setup': 'sqlite> .read q7.sql',
             'type': 'sqlite'}]}
def falling(n, k):
    """Compute the falling factorial of n to depth k.

    >>> falling(6, 3)  # 6 * 5 * 4
    120
    >>> falling(4, 3)  # 4 * 3 * 2
    24
    >>> falling(4, 1)  # 4
    4
    >>> falling(4, 0)
    1
    """
    "*** YOUR CODE HERE ***"
    depth = 0
    total = 1
    while (depth < k):
        total *= (n-depth)
        depth += 1
    return total



def sum_digits(y):
    """Sum all the digits of y.

    >>> sum_digits(10) # 1 + 0 = 1
    1
    >>> sum_digits(4224) # 4 + 2 + 2 + 4 = 12
    12
    >>> sum_digits(1234567890)
    45
    >>> a = sum_digits(123) # make sure that you are using return rather than print
    >>> a
    6
    """
    "*** YOUR CODE HERE ***"
    copy = y
    total = 0
    while (copy > 0):
        total += copy%10
        # print("DEBUG: total is being incremented by" + str(copy%10))
        copy = copy//10
        # print("DEBUG: copy" + str(copy))
    return total

def double_eights(n):
    """Return true if n has two eights in a row.
    >>> double_eights(8)
    False
    >>> double_eights(88)
    True
    >>> double_eights(2882)
    True
    >>> double_eights(880088)
    True
    >>> double_eights(12345)
    False
    >>> double_eights(80808080)
    False
    """
    "*** YOUR CODE HERE ***"
    has_eight_prev = False
    while (n > 0):
        # Three cases
        # eight prev and eight now - return true
        # no eight previously, but eight now - make eight prev true
        # no eight - make sure eight_prev is false
        # floor divide by 10 every time
        if (n%10 == 8 and has_eight_prev):
            return True
        elif (n%10 == 8 and not has_eight_prev):
            has_eight_prev = True
        elif (n%10 != 8):
            has_eight_prev = False
        n = n//10
    
    # If all else fails and no ret true up to now, return False
    return False

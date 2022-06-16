package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Pranav Eranki
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
        _derangement = true;
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        cycle = cycle.trim();
        _cycles += "(" + cycle + ")";
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return alphabet().size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        int modded = wrap(p);
        char c = _alphabet.toChar(modded);
        for (int i = 0; i < _cycles.length(); i++) {
            if (_cycles.charAt(i) == c) {
                if (_cycles.charAt(i + 1) == ')') {
                    for (int j = i - 1; j >= 0; j--) {
                        if (_cycles.charAt(j) == '(') {
                            return _alphabet.toInt(_cycles.charAt(j + 1));
                        }
                    }
                } else {
                    return _alphabet.toInt(_cycles.charAt(i + 1));
                }
            }
        }
        return p;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        int modded = wrap(c);
        char p = _alphabet.toChar(modded);
        for (int i = 0; i < _cycles.length(); i++) {
            if (_cycles.charAt(i) == p) {
                if (_cycles.charAt(i - 1) == '(') {
                    for (int j = i + 1; j < _cycles.length(); j++) {
                        if (_cycles.charAt(j) == ')') {
                            return _alphabet.toInt(_cycles.charAt(j - 1));
                        }
                    }
                } else {
                    return _alphabet.toInt(_cycles.charAt(i - 1));
                }
            }
        }
        return c;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        int inp = _alphabet.toInt(p);
        return _alphabet.toChar(permute(inp));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        return _alphabet.toChar(invert(_alphabet.toInt(c)));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        return _derangement;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** This permutation's cycle. */
    private String _cycles;

    /** Tells us whether or not this permutation has
     * values that map to themselves. */
    private boolean _derangement;

}

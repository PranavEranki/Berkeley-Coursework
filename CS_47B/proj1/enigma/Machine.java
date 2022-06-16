package enigma;

import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Pranav Eranki
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _pawls = pawls;
        _numRotors = numRotors;
        _rotorSet = new Rotor[allRotors.size()];
        int i = 0;
        for (Rotor add : allRotors) {
            _rotorSet[i] = add;
            i++;
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Return Rotor #K, where Rotor #0 is the reflector, and Rotor
     *  #(numRotors()-1) is the fast Rotor.  Modifying this Rotor has
     *  undefined results. */
    Rotor getRotor(int k) {
        return ourRotors[k];
    }

    Alphabet alphabet() {
        return _alphabet;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        ourRotors = new Rotor[rotors.length];
        for (int i = 0; i < rotors.length; i++) {
            for (int r = 0; r < _rotorSet.length; r++) {
                String rotorToInsertIfMatches =
                        _rotorSet[r].toString().substring(6);
                String rotorNameWeWant = rotors[i];
                if (rotorToInsertIfMatches.equals(rotorNameWeWant)) {
                    ourRotors[i] = _rotorSet[r];
                }
                if (r == _rotorSet.length - 1 && ourRotors[i] == null) {
                    throw new EnigmaException("Rotor provided at " + i
                            + " with name " + rotorNameWeWant
                            + " is not valid in our rotor set.");
                }
            }
        }
        if (rotors.length != ourRotors.length) {
            throw new EnigmaException("Could not correctly read and "
                    + "interpret some given rotor names in insertRotors.");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Not enough or too "
                    + "many settings provided.");
        }
        for (int i = 1; i < numRotors(); i++) {
            char current = setting.charAt(i - 1);
            if (_alphabet.contains(current)) {
                ourRotors[i].set(current);
            } else {
                throw new EnigmaException("Illegal character "
                        + "provided in initial settings");
            }
        }
    }

    /** Return the current plugboard's permutation. */
    Permutation plugboard() {
        return _plugboard;
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        advanceRotors();
        if (Main.verbose()) {
            System.err.printf("[");
            for (int r = 1; r < numRotors(); r += 1) {
                System.err.printf("%c",
                        alphabet().toChar(getRotor(r).setting()));
            }
            System.err.printf("] %c -> ", alphabet().toChar(c));
        }
        c = plugboard().permute(c);
        if (Main.verbose()) {
            System.err.printf("%c -> ", alphabet().toChar(c));
        }
        c = applyRotors(c);
        int d = plugboard().permute(c);
        if (Main.verbose()) {
            System.err.printf("%c%n", alphabet().toChar(c));
        }
        return d;
    }

    /** Advance all rotors to their next position. */
    private void advanceRotors() {
        boolean[] advanced = new boolean[ourRotors.length];
        advanced[ourRotors.length - 1] = true;
        for (int i = 1; i < ourRotors.length - 1; i++) {
            if (ourRotors[i].rotates() && i != ourRotors.length - 1
                    && ourRotors[i + 1].atNotch()) {
                advanced[i] = true;
            }
        }

        for (int i = 0; i < advanced.length; i++) {
            if (advanced[i]) {
                ourRotors[i].advance();
                if (i != advanced.length - 1) {
                    ourRotors[i + 1].advance();
                    i++;
                }
            }
        }

    }

    /** Return the result of applying the rotors to the character C (as an
     *  index in the range 0..alphabet size - 1). */
    private int applyRotors(int c) {
        int output = c;
        int startingIndex = ourRotors.length - 1;
        for (int i = startingIndex; i > 0; i--) {
            Rotor currentRotor = ourRotors[i];
            output = currentRotor.convertForward(output);
        }

        Reflector reflector = (Reflector) ourRotors[0];
        output = reflector.convertForward(output);
        for (int i = 1; i <= startingIndex; i++) {
            Rotor currentRotor = ourRotors[i];
            output = currentRotor.convertBackward(output);
        }

        return output;

    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        char[] results = msg.toUpperCase().replaceAll(" ", "").toCharArray();
        for (int i = 0; i < results.length; i++) {
            if (_alphabet.contains(results[i])) {
                int currAs = alphabet().toInt(results[i]);
                int convertedCurr = convert(currAs);
                char converted = alphabet().toChar(convertedCurr);
                results[i] = converted;
            }
        }
        String toRet = String.valueOf(results);
        return toRet;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Numer of pawls. */
    private int _pawls;

    /** Number of rotors. */
    private int _numRotors;

    /** The rotors we have available for the machine. */
    private Rotor[] _rotorSet;

    /** The rotors our machine uses. */
    private Rotor[] ourRotors;

    /** Our permutation plugboard. */
    private Permutation _plugboard;
}

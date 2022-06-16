package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a reflector in the enigma.
 *  @author Pranav Eranki
 */
class Reflector extends FixedRotor {

    /** A non-moving rotor named NAME whose permutation at the 0 setting
     * is PERM. There is a set of reflectors that we can choose from.
     * These are to the left of all the rotors. Thus,
     * they must be in the 0th position. Their setting is set
     * to reflecting*/
    Reflector(String name, Permutation perm) {
        super(name, perm);
        set(0);
    }

    @Override
    void set(int posn) {
        if (posn != 0) {
            throw error("reflector has only one position");
        }
    }

    /** Reflector! */
    @Override
    boolean reflecting() {
        return true;
    }

    /** This is overriden as a reminder that this
     * must be in the 0th position! */
    @Override
    int setting() {
        return 0;
    }
}

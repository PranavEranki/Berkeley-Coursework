package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.Scanner;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

import org.checkerframework.checker.units.qual.A;
import ucb.util.CommandArgs;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Pranav Eranki
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {

        try {
            CommandArgs options =
                new CommandArgs("--verbose --=(.*){1,3}", args);
            if (!options.ok()) {
                throw error("Usage: java enigma.Main [--verbose] "
                            + "[INPUT [OUTPUT]]");
            }

            _verbose = options.contains("--verbose");
            new Main(options.get("--")).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);

    }

    /** Open the necessary files for non-option arguments ARGS (see comment
      *  on main). */
    Main(List<String> args) {
        _config = getInput(args.get(0));

        if (args.size() > 1) {
            _input = getInput(args.get(1));
        } else {
            _input = new Scanner(System.in);
        }

        if (args.size() > 2) {
            _output = getOutput(args.get(2));
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output.*/
    private void process() {
        Machine ourMachine = readConfig();
        while (_input.hasNext()) {
            inputProcessing(ourMachine, "", false);
        }
    }
    /** Does our input processing, takes in an optional settings file.
     * @param ourMachine the machine we get from read config()
     * @param settings the optional settings string to use if we're
     *                 not reading one in
     * @param use whether or not to use the settings param as our settings.*/
    private void inputProcessing(Machine ourMachine, String settings,
                                 boolean use) {
        String settingLine;
        if (use) {
            settingLine = settings;
        } else {
            settingLine = _input.nextLine();
        }
        if (!settingLine.contains("*")) {
            throw new EnigmaException("Incorrect settings format");
        } else {
            setUp(ourMachine, settingLine);
        }
        String inp = _input.nextLine().toUpperCase();
        while (inp.isEmpty()) {
            inp = _input.nextLine().toUpperCase();
        }
        String newInput = inp.trim();
        boolean dead = false;
        try {
            while (!newInput.contains("*")) {
                String newInputNoSpaces = newInput.replace(" ", "");
                if (newInputNoSpaces.length() == 0) {
                    _output.println();
                } else {
                    String converted = ourMachine.convert(newInputNoSpaces);
                    printMessageLine(converted);
                }
                if (_input.hasNext()) {
                    newInput = _input.nextLine();
                } else {
                    dead = true;
                    break;
                }
            }
            if (!dead) {
                inputProcessing(ourMachine, newInput, true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alpha = _config.next();
            boolean a = alpha.contains(")");
            boolean b = alpha.contains("(");
            boolean c = alpha.contains("*");
            if (a || b || c) {
                throw new EnigmaException(
                        "Configuration format is wrong, invalid alphabet");
            } else {
                _alphabet = new Alphabet(alpha);
            }
            if (!_config.hasNextInt()) {
                throw new EnigmaException(
                        "Configuration format is wrong, need # of rotors.");
            }
            int rotors = _config.nextInt();
            if (!_config.hasNextInt()) {
                throw new EnigmaException(
                        "Configuration format is wrong, need # of pawls.");
            }
            int pawls = _config.nextInt();
            ArrayList<Rotor> allTheRotors = new ArrayList<>();
            String clearingLine = _config.nextLine();
            String currRotorLine = "";
            String prevLine = _config.nextLine().trim();
            boolean moveToNext = true;
            while (_config.hasNext()) {
                currRotorLine = _config.nextLine();
                currRotorLine = currRotorLine.trim();
                if (currRotorLine.charAt(0) == '(') {
                    if (prevLine.equals("")) {
                        throw error("invalid rotor line in config file");
                    }
                    prevLine = prevLine + " " + currRotorLine;
                    allTheRotors.add(readRotor(prevLine));
                } else {
                    allTheRotors.add(readRotor(prevLine));
                    prevLine = currRotorLine;
                }
            }
            return new Machine(_alphabet, rotors, pawls, allTheRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config.
     * @param currLine takes in the current config line from which
     *                 to read the rotor.*/
    private Rotor readRotor(String currLine) {
        try {
            Scanner temp = new Scanner(currLine);
            String rotorName = temp.next();
            String rotorNotches = temp.next();
            ArrayList<String> cycles = new ArrayList<>();
            String restOfRotorLine = temp.nextLine();
            Scanner rotorLineScanner = new Scanner(restOfRotorLine);
            while (rotorLineScanner.hasNext()) {
                String currCycle = rotorLineScanner.next();
                if (currCycle.charAt(0) != '('
                        || currCycle.charAt(currCycle.length() - 1) != ')') {
                    throw new EnigmaException("Cycles not "
                            + "correctly represented");
                }
                cycles.add(currCycle);
            }
            Permutation ourPerm = new Permutation(
                    String.join(" ", cycles), _alphabet);
            if (rotorNotches.charAt(0) == 'M') {
                return new MovingRotor(rotorName, ourPerm,
                        rotorNotches.substring(1));
            } else if (rotorNotches.charAt(0) == 'N') {
                return new FixedRotor(rotorName, ourPerm);
            }  else if (rotorNotches.charAt(0) == 'R') {
                return new Reflector(rotorName, ourPerm);
            } else {
                throw new EnigmaException("Notches inputted for rotor "
                        + rotorName + " do not properly tell us it's type.");
            }
        } catch (NoSuchElementException excp) {
            throw error(excp.getMessage() + " "
                    + excp.getStackTrace().toString()
                    + " bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        String[] ourSettings = settings.split(" ");
        String[] ourRotors = new String[M.numRotors()];
        if (ourSettings.length <= M.numRotors() - 2) {
            throw new EnigmaException("Not enough rotor configs "
                    + "or general arguments in provided settings.");
        }
        for (int i = 1; i < M.numRotors() + 1; i++) {
            ourRotors[i - 1] = ourSettings[i];
        }

        for (int i = 0; i < ourRotors.length; i++) {
            for (int j = 0; j < ourRotors.length; j++) {
                if (i != j && ourRotors[i].equals(ourRotors[j])) {
                    throw new EnigmaException("Repeating rotors "
                            + "is not allowed");
                }
            }
        }
        M.insertRotors(ourRotors);
        if (!M.getRotor(0).reflecting()) {
            throw new EnigmaException("Edge rotor in pos 1 must be reflector");
        }
        M.setRotors(ourSettings[M.numRotors() + 1]);
        String plugBoardSteckering = "";
        for (int i = ourRotors.length + 2; i < ourSettings.length; i++) {
            plugBoardSteckering += ourSettings[i] + " ";
        }
        M.setPlugboard(new Permutation(plugBoardSteckering, _alphabet));
    }

    /** Return true iff verbose option specified. */
    static boolean verbose() {
        return _verbose;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        while (true) {
            int msgLength = msg.length();
            if (msgLength <= 5) {
                _output.println(msg);
                break;
            } else {
                _output.print(msg.substring(0, 5));
                msg = msg.substring(5);
                _output.print(" ");
            }
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private final Scanner _input;

    /** Source of machine configuration. */
    private final Scanner _config;

    /** File for encoded/decoded messages. */
    private final PrintStream _output;

    /** True if --verbose specified. */
    private static boolean _verbose;
}

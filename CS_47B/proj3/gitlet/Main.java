package gitlet;

/** Driver class for Gitlet, the t<3ny stupid version-control system.
 *  @author Pranav Eranki
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */


    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        runIt(args);
    }

    public static void validateNumArgs(String cmd, String[] args, int n) {
        if (args.length != n) {
            throw new RuntimeException(
                    String.format("Invalid number of arguments for: %s.", cmd));
        }
    }

    public static void init(String... args) {
        validateNumArgs("init", args, 1);
        Commands.init();
    }

    public static void add(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("add", args, 2);
            Commands.add(args[1]);
        }
    }

    public static void commit(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            Commands.commit(args);
        }
    }

    public static void rm(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("rm", args, 2);
            Commands.rm(args[1]);
        }
    }

    public static void log(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("log", args, 1);
            Commands.log();
        }
    }

    public static void globalLog(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("global-log", args, 1);
            Commands.globalLog();
        }
    }

    public static void find(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("find", args, 2);
            Commands.findCommits(args[1]);
        }
    }

    public static void status(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("status", args, 1);
            Commands.status();
        }
    }

    public static void checkout(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            Commands.checkout(args);
        }
    }

    public static void branch(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("branch", args, 2);
            Commands.branch(args);
        }
    }

    public static void rmBranch(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("rm-branch", args, 2);
            Commands.removeBranch(args);
        }
    }

    public static void reset(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("reset", args, 2);
            Commands.reset(args);
        }
    }

    public static void merge(String... args) {
        if (!Commands.GITLET_FOLDER.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
        } else {
            validateNumArgs("merge", args, 2);
            Commands.merge(args);
        }
    }

    public static void runIt(String... args) {
        switch (args[0]) {
        case "init":
            init(args);
            break;
        case "add":
            add(args);
            break;
        case "commit":
            commit(args);
            break;
        case "rm":
            rm(args);
            break;
        case "log":
            log(args);
            break;
        case "global-log":
            globalLog(args);
            break;
        case "find":
            find(args);
            break;
        case "status":
            status(args);
            break;
        case "checkout":
            checkout(args);
            break;
        case "branch":
            branch(args);
            break;
        case "rm-branch":
            rmBranch(args);
            break;
        case "reset":
            reset(args);
            break;
        case "merge":
            merge(args);
            break;
        default:
            System.out.println("No command with that name exists.");
            System.exit(0);
        }
        return;
    }
}

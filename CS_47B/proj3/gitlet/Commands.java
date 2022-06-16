package gitlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Commands {
    /** Main metadata folder. */
    static final File CWD = new File(".");
    /** The folder containing all our gitlet serialization and etc. */
    static final File GITLET_FOLDER = Utils.join(CWD, ".gitlet");
    /** The folder containing serialized commits. */
    static final File COMMITS_FOLDER = Utils.join(GITLET_FOLDER, "commits");
    /** The folder containing serialized tree structures. */
    static final File TREES_FOLDER = Utils.join(GITLET_FOLDER, "trees");
    /** The folder containing serialized hashmap indices for staging
     * and removal. */
    static final File INDEX_FOLDER = Utils.join(GITLET_FOLDER, "index");
    /** The index for staging. */
    static final File STAGING_INDEX = Utils.join(INDEX_FOLDER, "staging");
    /** The index for removal. */
    static final File REMOVED_INDEX = Utils.join(INDEX_FOLDER, "removed");
    /** The folder containing refs to HEAD, master, etc - all branches. */
    static final File REFS_FOLDER = Utils.join(GITLET_FOLDER, "refs");
    /** HEAD points to the branch that is currently active. */
    static final File HEAD = Utils.join(REFS_FOLDER, "HEAD");
    /** This sentinel points to the hash of the initial commit. */
    static final String INITIAL_COMMIT_HASH_SENTINEL =
            "2d8aed4cccdcc37ac58a271eb3474ab33724fee4";
    /** The length of completed hashes. */
    static final int MAX_HASH_LENGTH = 40;

    /** The init command for git. */
    public static void init() {
        if (GITLET_FOLDER.exists()) {
            System.out.println("A Gitlet version-control "
                    + "system already exists in the current directory.");
            System.exit(0);
        } else {
            GITLET_FOLDER.mkdir();
            COMMITS_FOLDER.mkdir();
            TREES_FOLDER.mkdir();
            INDEX_FOLDER.mkdir();
            REFS_FOLDER.mkdir();

            Commit init = new Commit();
            String initHash = init.getHash();
            File toSave = Utils.join(COMMITS_FOLDER, initHash);
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream(toSave));
                out.writeObject(init);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File m = Utils.join(REFS_FOLDER, "master");
            Utils.writeContents(m, init.getHash());
            setHeadToBranch("master");
            try {
                out = new ObjectOutputStream(new
                        FileOutputStream(STAGING_INDEX));
                out.writeObject(new HashMap<String, FileBlob>());
                out.close();
                out = new ObjectOutputStream(new
                        FileOutputStream(REMOVED_INDEX));
                out.writeObject(new HashMap<String, FileBlob>());
                out.close();
            } catch (IOException excp) {
                System.out.println(excp.getMessage());
            }
        }
    }

    public static void setHeadToBranch(String branchName) {
        File m = Utils.join(HEAD);
        Utils.writeContents(m, branchName);
    }

    public static String getHeadBranchName() {
        return Utils.readContentsAsString(HEAD);
    }

    public static String getHashOfTheActiveCommit() {
        File active = Utils.join(REFS_FOLDER, getHeadBranchName());
        String headCommitHash = Utils.readContentsAsString(active);
        return headCommitHash;
    }

    public static Commit getHeadCommitItself() {
        return Utils.readObject(Utils.join(
                COMMITS_FOLDER, getHashOfTheActiveCommit()),
                Commit.class);
    }

    public static void moveTheActiveBranchUp(String newCommitHash) {
        File active = Utils.join(REFS_FOLDER, getHeadBranchName());
        Utils.writeContents(active, newCommitHash);
    }

    public static void writeNewBranch(String commitHash, String branchName) {
        File br = Utils.join(REFS_FOLDER, branchName);
        Utils.writeContents(br, commitHash);
    }

    public static HashMap<String, FileBlob> getStagedIndex() {
        return Utils.readObject(STAGING_INDEX, HashMap.class);
    }

    public static HashMap<String, FileBlob>  getRemovalIndex() {
        return Utils.readObject(REMOVED_INDEX, HashMap.class);
    }

    public static void writeStaged(HashMap ourMap) {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream(STAGING_INDEX));
            out.writeObject(ourMap);
            out.close();
        } catch (IOException excp) {
            System.out.println(excp.getMessage());
        }
    }

    public static void writeRemoved(HashMap ourMap) {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream(REMOVED_INDEX));
            out.writeObject(ourMap);
            out.close();
        } catch (IOException excp) {
            System.out.println(excp.getMessage());
        }
    }

    public static void add(String file) {
        File theFile = Utils.join(CWD, file);
        if (theFile.exists()) {
            String contents = Utils.readContentsAsString(theFile);
            Commit workingCommit = getHeadCommitItself();

            boolean headCommitContainsExactSameContents = false;
            if (workingCommit.getIndexFile().containsKey(file)) {
                String cont = workingCommit.getIndexFile().
                        get(file).getFileContentsAtTime();
                if (cont.equals(contents)) {
                    headCommitContainsExactSameContents = true;
                    if (getStagedIndex().containsKey(file)) {
                        HashMap staged = getStagedIndex();
                        staged.remove(file);
                        writeStaged(staged);
                    }
                    if (getRemovalIndex().containsKey(file)) {
                        HashMap removal = getRemovalIndex();
                        removal.remove(file);
                        writeRemoved(removal);
                    }
                }
            }

            HashMap<String, FileBlob> staged = getStagedIndex();
            HashMap<String, FileBlob> removed = getRemovalIndex();
            if (!headCommitContainsExactSameContents) {
                if (removed.containsKey(file)) {
                    removed.remove(file);
                    writeRemoved(removed);
                } else {
                    FileBlob toStage = new FileBlob(theFile);
                    staged.put(file, toStage);
                    writeStaged(staged);
                }
            }
        } else {
            System.out.println("File does not exist.");
            System.exit(0);
        }
    }

    public static void commit(String... arg) {
        if (arg.length != 2 || arg[1].equals("")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        if (getStagedIndex().keySet().size() == 0
                && getRemovalIndex().keySet().size() == 0) {
            System.out.println("No changes added to the commit.");
        }
        Commit oldHead = getHeadCommitItself();
        oldHead.makeDiff();
        oldHead.changeMetadata(arg[1], new String[]{oldHead.getHash()});
        File toSave = Utils.join(COMMITS_FOLDER, oldHead.getHash());
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(toSave));
            out.writeObject(oldHead);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeStaged(new HashMap());
        writeRemoved(new HashMap());
        moveTheActiveBranchUp(oldHead.getHash());
    }

    public static void rm(String fileName) {
        File toRm = Utils.join(fileName);
        Commit head = getHeadCommitItself();
        boolean staged = false;
        boolean trackedByHead = false;
        if (getStagedIndex().containsKey(fileName)) {
            staged = true;
        }
        if (!head.getIndexFile().isEmpty()
                && head.getIndexFile().containsKey(fileName)) {
            trackedByHead = true;
        }

        if (staged) {
            HashMap<String, FileBlob> oldStaged = getStagedIndex();
            oldStaged.remove(fileName);
            writeStaged(oldStaged);
        }
        if (trackedByHead) {
            HashMap<String, FileBlob> oldRemoval = getRemovalIndex();
            oldRemoval.put(fileName, new FileBlob(toRm));
            writeRemoved(oldRemoval);
            if (toRm.exists()) {
                toRm.delete();
            }
        }
        if (!staged && !trackedByHead) {
            System.out.println("No reason to remove the file.");
        }
    }

    public static void log() {
        String headCommitHash = getHashOfTheActiveCommit();
        String toPrint = "";
        Commit head = getHeadCommitItself();
        while (!head.isInitialCommit()) {
            toPrint += "=== \n" + "commit " + headCommitHash + "\n";
            if (head.isMergeCommit()) {
                toPrint += "Merge: " + head.getHashOfParent1().substring(0, 7)
                        + " " + head.getHashOfParent2().substring(0, 7) + "\n";
            }
            toPrint += "Date: " + head.getCommitTime() + "\n"
                    + head.getLogMessage() + "\n\n";
            headCommitHash = head.getHashOfParent1();
            head = Utils.readObject(Utils.join(COMMITS_FOLDER,
                    headCommitHash), Commit.class);
        }
        if (head.isInitialCommit()) {
            toPrint += "=== \n" + "commit " + headCommitHash + "\n";
            toPrint += "Date: " + head.getCommitTime() + "\n"
                    + head.getLogMessage();
            System.out.println(toPrint);
        } else {
            System.out.println("THIS ISNT SUPPOSED TO HAPPEN (?)");
        }
    }

    public static void globalLog() {
        String toPrint = "";
        for (String commitHash : Utils.plainFilenamesIn(COMMITS_FOLDER)) {
            Commit commit = Utils.readObject(
                    Utils.join(COMMITS_FOLDER, commitHash), Commit.class);
            toPrint += "=== \n" + "commit " + commitHash + "\n";
            if (commit.isMergeCommit()) {
                toPrint += "Merge: " + commit.getHashOfParent1().substring(0, 7)
                        + " " + commit.getHashOfParent2().substring(0, 7)
                        + "\n";
            }
            toPrint += "Date: " + commit.getCommitTime() + "\n"
                    + commit.getLogMessage() + "\n\n";
        }
        if (toPrint.length() > 0) {
            toPrint = toPrint.substring(0, toPrint.length() - 2);
        }
        System.out.println(toPrint);
    }

    public static void findCommits(String msg) {
        String toPrint = "";
        for (String commitHash : Utils.plainFilenamesIn(COMMITS_FOLDER)) {
            Commit commit = Utils.readObject(
                    Utils.join(COMMITS_FOLDER, commitHash), Commit.class);
            if (commit.getLogMessage().equals(msg)) {
                toPrint += commitHash + "\n";
            }
        }
        if (toPrint.equals("")) {
            System.out.println("Found no commit with that message.");
        } else {
            System.out.println(toPrint);
        }
    }

    public static String sortArrayListAndConcat(
            boolean branches, ArrayList<String> toSort) {
        String sout = "";
        Collections.sort(toSort);
        for (int i = 0; i < toSort.size(); i++) {
            sout += "\n";
            if (branches) {
                if (getHeadBranchName().equals(toSort.get(i))) {
                    sout += "*";
                }
            }
            sout += toSort.get(i);
        }
        return sout;
    }

    public static void status() {
        String sout = "";

        sout += "=== Branches ===";
        ArrayList<String> toSort = new ArrayList<>();
        for (String branchName : Utils.plainFilenamesIn(REFS_FOLDER)) {
            if (branchName.equals("HEAD")) {
                continue;
            } else {
                toSort.add(branchName);
            }
        }
        sout += sortArrayListAndConcat(true, toSort);

        sout += "\n\n" + "=== Staged Files ===";
        toSort = new ArrayList<>();
        for (Object stagedFile : getStagedIndex().keySet()) {
            toSort.add((String) stagedFile);
        }
        sout += sortArrayListAndConcat(false, toSort);

        sout += "\n\n" + "=== Removed Files ===";
        toSort = new ArrayList<>();
        for (Object removedFile : getRemovalIndex().keySet()) {
            toSort.add((String) removedFile);
        }
        sout += sortArrayListAndConcat(false, toSort);
        sout += "\n\n" + "=== Modifications Not Staged For Commit ===";
        sout += "\n\n" + "=== Untracked Files ===\n";
        System.out.println(sout);
    }

    public static ArrayList<String> getUntrackedFiles() {
        ArrayList<String> fileNames = new ArrayList<>();
        for (String fileName: Utils.plainFilenamesIn(CWD)) {
            if (getHeadCommitItself().getIndexFile().
                            containsKey(fileName)
                    || getStagedIndex().containsKey(fileName)
                    || getRemovalIndex().containsKey(fileName)
                    || fileName.equals(".gitignore")
                    || fileName.equals("Makefile")
                    || fileName.equals("proj3.iml")
            ) {
                continue;
            } else {
                fileNames.add(fileName);
            }
        }
        return fileNames;
    }

    public static void checkoutBranch(String branchName) {
        File br = Utils.join(REFS_FOLDER, branchName);
        if (!br.exists() || branchName.equals("HEAD")) {
            System.out.println("No such branch exists.");
        } else {
            if (branchName.equals((getHeadBranchName()))) {
                System.out.println("No need to "
                        + "checkout the current branch.");
            } else {
                String commitHash = Utils.readContentsAsString(
                        Utils.join(REFS_FOLDER, branchName));
                Commit respectiveCommit = Utils.readObject(
                        Utils.join(COMMITS_FOLDER, commitHash),
                        Commit.class);
                HashMap respectiveIndexFile =
                        respectiveCommit.getIndexFile();
                ArrayList<String> untrackedFiles = getUntrackedFiles();
                for (Object file : respectiveIndexFile.keySet()) {
                    String fileName = (String) file;
                    if (untrackedFiles.contains(fileName)) {
                        System.out.println("There is an untracked "
                                + "file in the way; delete it, or "
                                + "add and commit it first.");
                        System.exit(0);
                    }
                }

                for (Object file : respectiveIndexFile.keySet()) {
                    String fileName = (String) file;
                    FileBlob blob = (FileBlob) respectiveIndexFile
                            .get(fileName);
                    String contents = blob.getFileContentsAtTime();
                    File dir = Utils.join(CWD, fileName);
                    if (dir.exists()) {
                        Utils.writeContents(dir, contents);
                    } else {
                        try {
                            dir.createNewFile();
                            Utils.writeContents(dir, contents);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Commit oldHead = getHeadCommitItself();
                for (Object file : oldHead.getIndexFile().keySet()) {
                    if (!respectiveIndexFile.containsKey(file)) {
                        FileBlob toRm = oldHead.getIndexFile().get(file);
                        File toRmm = toRm.getFileAsFile();
                        if (toRmm.exists()) {
                            toRmm.delete();
                        }
                    }
                }
                writeStaged(new HashMap());
                writeRemoved(new HashMap());
                setHeadToBranch(branchName);
            }
        }
    }

    public static void checkoutFile(String fileName) {
        Commit head = getHeadCommitItself();
        if (head.getIndexFile().containsKey(fileName)) {
            String contentsOfHeadVersion = head.getIndexFile()
                    .get(fileName).getFileContentsAtTime();
            Utils.writeContents(Utils.join(fileName),
                    contentsOfHeadVersion);
        } else {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
    }

    public static String findFullCommitHash(String abbreviatedHash) {
        List<String> allCommits  = Utils.plainFilenamesIn(COMMITS_FOLDER);
        if (abbreviatedHash.length() != MAX_HASH_LENGTH) {
            for (String commit : allCommits) {
                if (commit.substring(0, abbreviatedHash.length())
                        .equals(abbreviatedHash)) {
                    return commit;
                }
            }
        }
        return abbreviatedHash;
    }

    public static void checkoutFileFromCommit(String fileName,
                                              String commitHash) {
        Commit commitInQuestion;
        File loc = Utils.join(COMMITS_FOLDER, findFullCommitHash(commitHash));

        try {
            commitInQuestion = Utils.readObject(loc, Commit.class);
            if (commitInQuestion.getIndexFile().containsKey(fileName)) {
                String contentsOfHeadVersion = commitInQuestion
                        .getIndexFile().get(fileName)
                        .getFileContentsAtTime();
                Utils.writeContents(Utils.join(fileName),
                        contentsOfHeadVersion);
            } else {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            }
        } catch (IllegalArgumentException io) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
    }


    public static void checkout(String... args) {
        if (args[1].equals("--") && args.length == 3) {
            checkoutFile(args[2]);
        } else if (args.length == 2) {
            String branchName = args[1];
            checkoutBranch(branchName);
        } else if (args.length == 4 && args[2].equals("--")) {
            String fileName = args[3];
            String commitHash = args[1];
            checkoutFileFromCommit(fileName, commitHash);
        } else {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }

    public static void branch(String... args) {
        String branchName = args[1];
        String currentHeadCommit = getHashOfTheActiveCommit();
        if (Utils.join(REFS_FOLDER, branchName).exists()) {
            System.out.println("A branch with that name already exists.");
        } else {
            writeNewBranch(currentHeadCommit, branchName);
        }
    }

    public static void removeBranch(String[] args) {
        String branchToRm = args[1];
        File toDel = Utils.join(REFS_FOLDER, branchToRm);
        if (toDel.exists() && !branchToRm.equals("HEAD")) {
            if (branchToRm.equals(getHeadBranchName())) {
                System.out.println("Cannot remove the current branch.");
            } else {
                try {
                    toDel.delete();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println("A branch with that name does not exist.");
        }
    }

    public static void reset(String[] args) {
        String commitWeAreLookingFor = args[1];
        File lookFor = Utils.join(COMMITS_FOLDER,
                findFullCommitHash(commitWeAreLookingFor));
        if (lookFor.exists()) {
            Commit currentCommit = getHeadCommitItself();
            HashMap indexForHead = currentCommit.getIndexFile();
            ArrayList<String> untrackedFiles = getUntrackedFiles();
            Commit checkingOutFrom = Utils.readObject(lookFor, Commit.class);
            HashMap indexForOurCommit = checkingOutFrom.getIndexFile();

            for (String file : untrackedFiles) {
                if (indexForOurCommit.containsKey(file)) {
                    System.out.println("There is an untracked "
                            + "file in the way; delete it, or "
                            + "add and commit it first.");
                    System.exit(0);
                }
            }

            for (Object file : indexForOurCommit.keySet()) {
                String fileName = (String) file;
                FileBlob blob = (FileBlob) indexForOurCommit.get(fileName);
                String contents = blob.getFileContentsAtTime();
                File dir = Utils.join(CWD, fileName);
                if (dir.exists()) {
                    Utils.writeContents(dir, contents);
                } else {
                    try {
                        dir.createNewFile();
                        Utils.writeContents(dir, contents);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Object file : indexForHead.keySet()) {
                if (!indexForOurCommit.containsKey(file)) {
                    FileBlob toRm = currentCommit.getIndexFile().get(file);
                    File toRmm = toRm.getFileAsFile();
                    if (toRmm.exists()) {
                        toRmm.delete();
                    }
                }
            }
            Utils.writeContents(Utils.join(REFS_FOLDER, getHeadBranchName()),
                    checkingOutFrom.getHash());
            writeStaged(new HashMap());
            writeRemoved(new HashMap());
        } else {
            System.out.println("No commit with that id exists.");
        }
    }

    public static ArrayList<String> pastCommits(String h) {
        Commit c = Utils.readObject(Utils.join(COMMITS_FOLDER, h),
                Commit.class);
        ArrayList<String> past = new ArrayList<String>();
        while (!c.isInitialCommit()) {
            String parentHash = c.getHashOfParent1();
            past.add(parentHash);
            c = Utils.readObject(Utils.join(COMMITS_FOLDER,
                    parentHash), Commit.class);
        }
        return past;
    }

    public static String getSplitPoint(ArrayList<String> otherHead) {
        ArrayList<String> q = new ArrayList<String>();
        q.add(getHashOfTheActiveCommit());
        while (!q.isEmpty()) {
            String currCommit = q.remove(0);
            Commit c = Utils.readObject(Utils.join(COMMITS_FOLDER,
                    currCommit), Commit.class);
            String parent1 = c.getHashOfParent1();
            String parent2 = c.getHashOfParent1();
            if (!parent1.equals("-1")) {
                q.add(parent1);
                if (otherHead.contains(parent1)) {
                    return parent1;
                }
            }
            if (!parent2.equals("-1")) {
                q.add(parent2);
                if (otherHead.contains(parent2)) {
                    return parent2;
                }
            }
        }
        return INITIAL_COMMIT_HASH_SENTINEL;
    }

    public static void edgeCases(String branchOther) {
        File branchOtherFile = Utils.join(REFS_FOLDER, branchOther);
        ArrayList<String> untrackedFiles = getUntrackedFiles();
        if (!branchOtherFile.exists()) {
            System.out.println("A branch with that name does not exist");
            System.exit(0);
        } else if (branchOther.equals(getHeadBranchName())) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        } else if (!getStagedIndex().isEmpty()
                || !getRemovalIndex().isEmpty()) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        } else if (!untrackedFiles.isEmpty()) {
            System.out.println("There is an untracked file in the way;"
                    + "delete it, or add and commit it first.");
            System.exit(0);
        }
    }

    public static ArrayList<String> merge1(
            String trackedFile, Commit otherCommit,
            Commit splitCommit, String currContents,
            ArrayList<String> mergeConflicts) {
        String otherBranchContents = otherCommit.
                getIndexFile().get(trackedFile).
                getFileContentsAtTime();
        if (splitCommit.getIndexFile().
                containsKey(trackedFile)) {
            String splitVersionContents = splitCommit.
                    getIndexFile().get(trackedFile).
                    getFileContentsAtTime();
            if (!currContents.equals(otherBranchContents)) {
                if (splitVersionContents.
                        equals(currContents)) {
                    FileBlob other = otherCommit.getIndexFile()
                            .get(trackedFile);
                    Boolean a = getStagedIndex().containsKey(trackedFile);
                    Boolean b = other.getFileContentsAtTime().equals((
                            (FileBlob) getStagedIndex().get(trackedFile))
                            .getFileContentsAtTime());
                    if (!(a && b)) {
                        HashMap<String, FileBlob> x =
                                getStagedIndex();
                        x.put(trackedFile, otherCommit.
                                getIndexFile().
                                get(trackedFile));
                        writeStaged(x);
                    }
                    File f = Utils.join(trackedFile);
                    Utils.writeContents(f, otherBranchContents);
                } else {
                    mergeConflicts.add(trackedFile);
                }
            }
        } else {
            if (!currContents.equals(otherBranchContents)) {
                mergeConflicts.add(trackedFile);
            }
        }
        return mergeConflicts;
    }

    public static ArrayList<String> merge2(
            Commit splitCommit, String trackedFile,
            ArrayList<String> mergeConflicts,
            String currContents) {
        if (splitCommit.getIndexFile().containsKey(trackedFile)) {
            String splitVersionContents = splitCommit.
                    getIndexFile().get(trackedFile).
                    getFileContentsAtTime();
            if (splitVersionContents.equals(currContents)) {
                try {
                    Utils.join(trackedFile).delete();
                    HashMap staged = getStagedIndex();
                    if (staged.containsKey(trackedFile)) {
                        staged.remove(trackedFile);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                mergeConflicts.add(trackedFile);
            }
        }
        return mergeConflicts;
    }

    public static ArrayList<String> mergeBrainded(
            Commit otherCommit, Commit headCommit, Commit splitCommit,
            ArrayList<String> mergeConflicts
    ) {
        for (Object f : otherCommit.getIndexFile().keySet()) {
            if (!headCommit.getIndexFile().containsKey(f)) {
                String fileName = (String) f;
                String otherV = otherCommit.getIndexFile().
                        get(fileName).getFileContentsAtTime();
                if (splitCommit.getIndexFile().containsKey(fileName)) {
                    if (!splitCommit.getIndexFile().get(fileName).
                            getFileContentsAtTime().equals(otherV)) {
                        mergeConflicts.add(fileName);
                    } else {
                        Utils.join(fileName).delete();
                        HashMap staged = getStagedIndex();
                        if (staged.containsKey(fileName)) {
                            staged.remove(fileName);
                        }
                    }
                } else {
                    FileBlob other = otherCommit.getIndexFile()
                            .get(fileName);
                    if (!(getStagedIndex().containsKey(
                            fileName) && other.
                            getFileContentsAtTime().
                            equals(((FileBlob) getStagedIndex()
                                    .get(fileName)).
                                    getFileContentsAtTime()))) {
                        HashMap<String, FileBlob> x = getStagedIndex();
                        x.put(fileName, otherCommit.
                                getIndexFile().
                                get(fileName));
                        writeStaged(x);
                    }
                    File ff = Utils.join(fileName);
                    Utils.writeContents(ff, otherCommit.
                            getIndexFile().get(fileName).
                            getFileContentsAtTime());
                }
            }
        }
        return mergeConflicts;
    }

    public static ArrayList<String> mergeKillMe(
            Commit headCommit, Commit otherCommit, Commit splitCommit,
            String otherHash) {
        ArrayList<String> mergeConflicts = new ArrayList<String>();
        for (String trackedFile: headCommit.getIndexFile().keySet()) {
            String currContents = headCommit.getIndexFile().
                    get(trackedFile).getFileContentsAtTime();
            if (otherCommit.getIndexFile().containsKey(trackedFile)) {
                mergeConflicts = merge1(trackedFile, otherCommit,
                        splitCommit, currContents, mergeConflicts);
            } else {
                mergeConflicts = merge2(splitCommit, trackedFile,
                        mergeConflicts, currContents);
            }
        }

        return mergeBrainded(otherCommit, headCommit,
                splitCommit, mergeConflicts);
    }

    public static void hahaKillMeMerge(
            ArrayList<String> mergeConflicts, Commit headCommit,
            Commit otherCommit
    ) {
        String toPrint = "Encountered a merge conflict.\n";
        for (String conflict : mergeConflicts) {
            String h = "", m = "", c = "";
            FileBlob fileHH = headCommit.getIndexFile()
                    .get(conflict);
            String originalFileHHContents = fileHH
                    .getFileContentsAtTime();
            if (headCommit.getIndexFile().containsKey(conflict)) {
                h = originalFileHHContents;
            } else {
                fileHH.setContents(c);
            }
            if (otherCommit.getIndexFile().containsKey(conflict)) {
                m = otherCommit.getIndexFile().get(conflict)
                        .getFileContentsAtTime();
            }
            c += "<<<<<<< HEAD\n" + h + "=======\n";
            if (m.length() != 0) {
                c += m + "\n>>>>>>>\n";
            } else {
                c += ">>>>>>>\n";
            }
            fileHH.setContents(c);
            Utils.writeContents(Utils.join(conflict), c);

            FileBlob param1 = headCommit.getIndexFile().
                    get(conflict);
            File param2 = Utils.join(CWD, conflict);

            if (!(getStagedIndex().containsKey(conflict)
                    && param1.getFileContentsAtTime().equals(
                            getStagedIndex().get(conflict)))) {
                HashMap<String, FileBlob> oldStaged =
                        getStagedIndex();
                FileBlob xx = new FileBlob(param2);
                xx.setContents(param1.getFileContentsAtTime());
                oldStaged.put(conflict, xx);
                writeStaged(oldStaged);
            }
        }
        System.out.println(toPrint);
    }

    public static void lastMergeBit(Commit headCommit, Commit splitCommit,
                                    HashMap headOldIndex, Commit otherCommit,
                                    String branchOther, String otherHash) {
        HashMap stage = getStagedIndex();
        for (Object e : stage.keySet()) {
            String kk = (String) e;
            headCommit.put(kk, (FileBlob) stage.get(kk));
        }

        for (String file : headCommit.getIndexFile().keySet()) {
            if (splitCommit.getIndexFile().containsKey(file)
                    && headOldIndex.containsKey(file) && !otherCommit.
                    getIndexFile().containsKey(file)) {
                if (splitCommit.getIndexFile().get(file).
                        getFileContentsAtTime().equals(headOldIndex.get(
                                file))) {
                    System.out.println("dude wtf?");
                    headCommit.remove(file);
                }
            }
        }

        headCommit.convertToMergeCommit("Merged " + branchOther
                + " into " + getHeadBranchName() + ".", otherHash);

        stage.clear();
        writeStaged(stage);
        Utils.writeObject(Utils.join(COMMITS_FOLDER,
                headCommit.getHash()), headCommit);
        moveTheActiveBranchUp(headCommit.getHash());
    }

    public static void merge(String... args) {
        String branchOther = args[1];
        File branchOtherFile = Utils.join(REFS_FOLDER, branchOther);
        ArrayList<String> untrackedFiles = getUntrackedFiles();
        edgeCases(branchOther);
        String otherHash = Utils.readContentsAsString(branchOtherFile);
        ArrayList<String> parentsOther = pastCommits(otherHash);
        ArrayList<String> parentsHead = pastCommits
                (getHashOfTheActiveCommit());
        if (parentsHead.contains(parentsOther)) {
            System.out.println("Given branch is an ancestor of the "
                    + "current branch.");
        } else if (parentsOther.contains(getHashOfTheActiveCommit())) {
            checkoutBranch(args[1]);
            System.out.println("Current branch fast-forwarded.");
        } else {
            String splitPointHash = getSplitPoint(parentsOther);
            Commit splitCommit = Utils.readObject(Utils.join(COMMITS_FOLDER,
                    splitPointHash), Commit.class);
            Commit headCommit = getHeadCommitItself();
            Commit otherCommit = Utils.readObject(Utils.join(COMMITS_FOLDER,
                    otherHash), Commit.class);

            ArrayList<String> mergeConflicts = mergeKillMe(headCommit,
                    otherCommit, splitCommit, otherHash);

            HashMap headOldIndex = headCommit.getIndexFile();

            if (!mergeConflicts.isEmpty()) {
                hahaKillMeMerge(mergeConflicts, headCommit, otherCommit);
            }
            lastMergeBit(headCommit, splitCommit, headOldIndex,
                    otherCommit, branchOther, otherHash);
        }
    }
}

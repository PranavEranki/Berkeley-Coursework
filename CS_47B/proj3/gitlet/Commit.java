package gitlet;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Commit implements Serializable {
    /** Our commit's log message. */
    private String logMessage;
    /** The time our commit was made. */
    private LocalDateTime commitTime;
    /** Is this the commit made with init, or no? */
    private boolean isInitial;
    /** The hash of the commit's parent commit. */
    private String hashOfParent1;
    /** The hash of the commit's second parent, only if it is a merge commit. */
    private String hashOfParent2;
    /** Is this a merge commit? */
    private boolean isMerge;
    /** This commit's hash. */
    private String ourHash;
    /** An index mapping of file names in this commit to FileBlobs. */
    private HashMap<String, FileBlob> indexFile;
    /** Cuz 1970 is a magic number lol. */
    private final int seventyy = 1954 + 16;

    public Commit() {
        logMessage = "initial commit";
        commitTime = LocalDateTime.of(seventyy, 1, 1, 0, 0, 0);
        commitTime = commitTime.minusHours(8);
        isInitial = true;
        isMerge = false;
        String h = logMessage + "\n" + commitTime.toString();
        ourHash = Utils.sha1(h);
        hashOfParent1 = "-1";
        indexFile = new HashMap<String, FileBlob>();
    }

    public String getHashOfParent1() {
        return hashOfParent1;
    }

    public String getHashOfParent2() {
        if (isMergeCommit()) {
            return hashOfParent2;
        } else {
            return "-1";
        }
    }

    public HashMap<String, FileBlob> getIndexFile() {
        return indexFile;
    }

    public void put(String file, FileBlob V) {
        indexFile.put(file, V);
    }

    public void remove(String file) {
        indexFile.remove(file);
    }

    public String getHash() {
        return ourHash;
    }

    public String getLogMessage() {
        return logMessage;
    }
    public String getCommitTime() {
        DateTimeFormatter formatter = DateTimeFormatter.
                ofPattern("E MMM d HH:mm:ss y '-0800'");
        return formatter.format(commitTime);
    }

    public boolean isInitialCommit() {
        return isInitial;
    }

    public boolean isMergeCommit() {
        return isMerge;
    }

    public void makeDiff() {
        HashMap stagingFolder = Commands.getStagedIndex();
        HashMap removedFolder = Commands.getRemovalIndex();
        for (Object file : stagingFolder.keySet()) {
            FileBlob x = (FileBlob) stagingFolder.get(file);
            indexFile.put((String) file, x);
        }
        for (Object file : removedFolder.keySet()) {
            indexFile.remove((String) file);
        }
    }

    public void changeMetadata(String message, String[] parent) {
        logMessage = message;
        commitTime = LocalDateTime.now();
        commitTime = commitTime.minusHours(8);
        isInitial = false;
        if (parent.length == 1) {
            hashOfParent1 = parent[0];
            isMerge = false;
            String mappings = "";
            for (String key : indexFile.keySet()) {
                mappings += key + ":" + indexFile.get(key) + "\n";
            }
            ourHash = Utils.sha1(logMessage, commitTime.toString(),
                    mappings, hashOfParent1);
        } else if (parent.length == 2) {
            hashOfParent1 = parent[0];
            hashOfParent2 = parent[1];
            isMerge = true;
            String mappings = "";
            for (String key : indexFile.keySet()) {
                mappings += key + ":" + indexFile.get(key) + "\n";
            }
            ourHash = Utils.sha1(logMessage, commitTime.toString(),
                    mappings, hashOfParent1, hashOfParent2);
        } else {
            System.out.println("ERROR ; TOO MANY OR TOO FEW PARENTS");
        }
    }

    public void convertToMergeCommit(String commitMessage, String parent2) {
        logMessage = commitMessage;
        commitTime = LocalDateTime.now();
        commitTime = commitTime.minusHours(8);
        isInitial = false;
        isMerge = true;
        this.hashOfParent1 = Commands.getHashOfTheActiveCommit();
        this.hashOfParent2 = parent2;
        String mappings = "";
        for (String key : indexFile.keySet()) {
            mappings += key + ":" + indexFile.get(key) + "\n";
        }
        ourHash = Utils.sha1(logMessage, commitTime.toString(), mappings,
                hashOfParent1, hashOfParent2);
    }
}

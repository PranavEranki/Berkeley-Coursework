package gitlet;

import java.io.File;
import java.io.Serializable;

public class FileBlob implements Serializable {

    /** This FileBlob's hash. */
    private String hash;
    /** This FileBlob's associated file. */
    private File file;
    /** The contents of the inputted file at time of creation. */
    private String fileContentsAtTime;

    public FileBlob(File fileI) {

        String contents;
        if (fileI.exists()) {
            contents = Utils.readContentsAsString(fileI);
        } else {
            contents = "__FILE DOES NOT EXIST IN CWD__";
        }
        this.hash = Utils.sha1(fileI.getAbsolutePath(), contents);
        this.file = fileI;
        this.fileContentsAtTime = contents;
    }

    public String getHash() {
        return hash;
    }

    public File getFileAsFile() {
        return file;
    }

    public String getFileContentsAtTime() {
        return fileContentsAtTime;
    }

    public void setContents(String s) {
        fileContentsAtTime = s;
    }

}

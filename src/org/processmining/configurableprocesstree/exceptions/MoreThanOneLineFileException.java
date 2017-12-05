package org.processmining.configurableprocesstree.exceptions;

public class MoreThanOneLineFileException extends Exception {
    private String filename;

    public MoreThanOneLineFileException(String filename) {
        super("File '" + filename + "' contains more than 1 line");
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}

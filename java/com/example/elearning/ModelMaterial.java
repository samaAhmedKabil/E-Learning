package com.example.elearning;

public class ModelMaterial {
    private String fileName ;
    private String fileLink ;
    public ModelMaterial(){}

    public ModelMaterial(String fileName, String fileLink) {
        this.fileName = fileName;
        this.fileLink = fileLink;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}

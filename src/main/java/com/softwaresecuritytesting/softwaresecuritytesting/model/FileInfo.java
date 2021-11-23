package com.softwaresecuritytesting.softwaresecuritytesting.model;

import java.net.URLConnection;

public class FileInfo {
    private String name;
    private String url;
    private String type;

    public FileInfo(String name, String url) {
        this.name = name;
        this.url = url;
        this.type = URLConnection.guessContentTypeFromName(name);
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() { return type;}

    public void setType(String type) { this.type = type;}
}


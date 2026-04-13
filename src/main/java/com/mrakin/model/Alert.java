package com.mrakin.model;

import java.util.List;

public class Alert {
    private String id;
    private List<Content> contents;
    private String date;
    private String inputType;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<Content> getContents() { return contents; }
    public void setContents(List<Content> contents) { this.contents = contents; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getInputType() { return inputType; }
    public void setInputType(String inputType) { this.inputType = inputType; }
}

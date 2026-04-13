package com.mrakin.model;

public class QueryTerm {
    private int id;
    private String text;
    private String language;
    private boolean keepOrder;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public boolean isKeepOrder() { return keepOrder; }
    public void setKeepOrder(boolean keepOrder) { this.keepOrder = keepOrder; }
}

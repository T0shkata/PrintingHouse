package com.example.printinghouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Printer {

    private int id;
    private String type;
    private int pagesPrinted;

    public Printer() {}

    @JsonProperty("Id")
    public int getId() {
        return id;
    }

    @JsonProperty("Id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("PagesPrinted")
    public int getPagesPrinted() {
        return pagesPrinted;
    }

    @JsonProperty("PagesPrinted")
    public void setPagesPrinted(int pagesPrinted) {
        this.pagesPrinted = pagesPrinted;
    }
}

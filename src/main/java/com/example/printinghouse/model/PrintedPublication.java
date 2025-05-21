package com.example.printinghouse.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintedPublication {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Copies")
    private int copies;

    public PrintedPublication() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCopies() { return copies; }
    public void setCopies(int copies) { this.copies = copies; }
}

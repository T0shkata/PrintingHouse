package com.example.printinghouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Publication {

    private int amount;
    private String publication;
    private int pageCount;
    private String pageFormat;
    private String paperType;
    private String printer;

    public Publication() {}

    @JsonProperty("Amount")
    public int getAmount() {
        return amount;
    }

    @JsonProperty("Amount")
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @JsonProperty("Publication")
    public String getPublication() {
        return publication;
    }

    @JsonProperty("Publication")
    public void setPublication(String publication) {
        this.publication = publication;
    }

    @JsonProperty("PageCount")
    public int getPageCount() {
        return pageCount;
    }

    @JsonProperty("PageCount")
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @JsonProperty("PageFormat")
    public String getPageFormat() {
        return pageFormat;
    }

    @JsonProperty("PageFormat")
    public void setPageFormat(String pageFormat) {
        this.pageFormat = pageFormat;
    }

    @JsonProperty("PaperType")
    public String getPaperType() {
        return paperType;
    }

    @JsonProperty("PaperType")
    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    @JsonProperty("Printer")
    public String getPrinter() {
        return printer;
    }

    @JsonProperty("Printer")
    public void setPrinter(String printer) {
        this.printer = printer;
    }
}

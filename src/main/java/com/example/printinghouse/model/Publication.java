package com.example.printinghouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Publication {

    @JsonProperty("ID")
    private int ID;

    @JsonProperty("Publication")
    private String Publication;

    @JsonProperty("PageCount")
    private int PageCount;

    @JsonProperty("PageFormat")
    private String PageFormat;

    @JsonProperty("PaperType")
    private String PaperType;

    public Publication() {}

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getPublication() { return Publication; }
    public void setPublication(String Publication) { this.Publication = Publication; }

    public int getPageCount() { return PageCount; }
    public void setPageCount(int PageCount) { this.PageCount = PageCount; }

    public String getPageFormat() { return PageFormat; }
    public void setPageFormat(String PageFormat) { this.PageFormat = PageFormat; }

    public String getPaperType() { return PaperType; }
    public void setPaperType(String PaperType) { this.PaperType = PaperType; }
}

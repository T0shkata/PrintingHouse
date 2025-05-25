package com.example.printinghouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Printer
{

    @JsonProperty("Id")
    private int id;

    @JsonProperty("Colour")
    private boolean colour;

    @JsonProperty("PagesPrinted")
    private int pagesPrinted;

    @JsonProperty("PagesPerMinute")
    private int pagesPerMinute;

    @JsonProperty("PaperCapacity")
    private int paperCapacity;

    @JsonProperty("PapersCount")
    private int papersCount;

    @JsonProperty("PrintedPublications")
    private List<PrintedPublication> printedPublications;

    public List<PrintedPublication> getPrintedPublications() {
        return printedPublications;
    }

    public void setPrintedPublications(List<PrintedPublication> printedPublications) {
        this.printedPublications = printedPublications;
    }


    public Printer() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean getType() { return colour; }
    public void setType(String type) { this.colour = colour; }

    public int getPagesPrinted() { return pagesPrinted; }
    public void setPagesPrinted(int pagesPrinted) { this.pagesPrinted = pagesPrinted; }

    public int getPagesPerMinute() { return pagesPerMinute; }
    public void setPagesPerMinute(int pagesPerMinute) { this.pagesPerMinute = pagesPerMinute; }

    public int getPaperCapacity() { return paperCapacity; }
    public void setPaperCapacity(int paperCapacity) { this.paperCapacity = paperCapacity; }

    public int getPapersCount() { return papersCount; }
    public void setPapersCount(int papersCount) { this.papersCount = papersCount; }
}

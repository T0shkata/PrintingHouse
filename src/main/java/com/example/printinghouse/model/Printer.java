package com.example.printinghouse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Printer {

    @JsonProperty("Id")
    private int Id;

    @JsonProperty("Type")
    private String Type;

    @JsonProperty("PagesPrinted")
    private int PagesPrinted;

    public Printer() {}

    public int getId() { return Id; }
    public void setId(int Id) { this.Id = Id; }

    public String getType() { return Type; }
    public void setType(String Type) { this.Type = Type; }

    public int getPagesPrinted() { return PagesPrinted; }
    public void setPagesPrinted(int PagesPrinted) { this.PagesPrinted = PagesPrinted; }
}

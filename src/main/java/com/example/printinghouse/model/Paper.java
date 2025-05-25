package com.example.printinghouse.model;

import java.util.Map;

public class Paper {
    private Map<String, Integer> normal;
    private Map<String, Integer> glossy;
    private Map<String, Integer> newspaper;

    public Map<String, Integer> getNormal() {
        return normal;
    }

    public void setNormal(Map<String, Integer> normal) {
        this.normal = normal;
    }

    public Map<String, Integer> getGlossy() {
        return glossy;
    }

    public void setGlossy(Map<String, Integer> glossy) {
        this.glossy = glossy;
    }

    public Map<String, Integer> getNewspaper() {
        return newspaper;
    }

    public void setNewspaper(Map<String, Integer> newspaper) {
        this.newspaper = newspaper;
    }
}

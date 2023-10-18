package com.infy.mapper.domain;

import java.util.HashMap;

public class AttributeExtractionInformation {
    private String[] jsonHierarchy = new String[0];
    private HashMap<String,Boolean> jsonAttributes;

    // Getter for jsonHierarchy
    public String[] getJsonHierarchy() {
        return jsonHierarchy;
    }

    // Setter for jsonHierarchy
    public void setJsonHierarchy(String[] jsonHierarchy) {
        this.jsonHierarchy = jsonHierarchy;
    }

    // Getter for jsonAttributes
    public HashMap<String,Boolean> getJsonAttributes() {
        return jsonAttributes;
    }

    // Setter for jsonAttributes
    public void setJsonAttributes(HashMap<String,Boolean> jsonAttributes) {
        this.jsonAttributes = jsonAttributes;
    }

}

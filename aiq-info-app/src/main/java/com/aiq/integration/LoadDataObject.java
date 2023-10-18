package com.aiq.integration;

public class LoadDataObject {
    private String dataPointId;
    private String name;
    private String value;

       // Getter methods
       public String getDataPointId() {
        return dataPointId;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    // Setter methods
    public void setDataPointId(String dataPointId) {
        this.dataPointId = dataPointId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

package com.aiq.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class LoanDataPointParser {
    public static JsonObject[] loanDataPointParser(String jsonString) {

        // Parse JSON to a JsonArray
        JsonArray jsonObjectArray = JsonParser.parseString(jsonString).getAsJsonArray();//.getAsJsonObject();
        System.out.println("jsonObjectArray " + jsonObjectArray);
        //Extract and print the required attributes
        int elementSize = jsonObjectArray.size();
        JsonObject[] dataObjects = new JsonObject[jsonObjectArray.size()];
        for (int i=0 ; i < elementSize ;i ++){
            JsonObject jsonObject = jsonObjectArray.get(i).getAsJsonObject();
            JsonObject loanDataPoint = jsonObject.getAsJsonObject("loanDataPoint");
            JsonObject loanDataPointDetails = loanDataPoint.getAsJsonObject("loanDataPointDetails");
            String dataPointId = loanDataPointDetails.get("dataPointId").getAsString();
            String name = loanDataPointDetails.get("name").getAsString();
            String value = loanDataPointDetails.get("value").getAsString();
            JsonObject dataObject = new JsonObject();
            dataObject.add("dataPointId",new JsonPrimitive(dataPointId));
            dataObject.add("name",new JsonPrimitive(name));
            dataObject.add("value",new JsonPrimitive(value));
            System.out.println("DataPoint ID: " + dataPointId);
            System.out.println("Name: " + name);
            System.out.println("Value: " + value);
            System.out.println("----------------------");
            dataObjects[i] = dataObject;
        }
        return dataObjects;
    }
}

package com.aiq.integration;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MetaDataParser {
    public static Map<String,JsonObject> findBlockByDataPointIdAndName(String jsonString) {
         // Parse JSON to a JsonArray
         JsonArray jsonObjectArray = JsonParser.parseString(jsonString).getAsJsonArray();//.getAsJsonObject();
         System.out.println("jsonObjectArray " + jsonObjectArray);

        Map<String,JsonObject> dataPointIdMap = new HashMap<String,JsonObject>();
        for (JsonElement jsonElement : jsonObjectArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String dataPointId = jsonObject.get("dataPointId").getAsString();
            // String name = jsonObject.get("field").getAsString();

            dataPointIdMap.put(dataPointId, jsonObject);
        }

        return dataPointIdMap;
    }
}
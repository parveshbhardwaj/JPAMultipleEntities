package com.infy.mapper.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class JsonExtractor {
    protected String jsonString;
    protected AttributeExtractionInformation attributesToExtract;

    public JsonExtractor(String jsonString,AttributeExtractionInformation attributesToExtract){
        this.jsonString = jsonString;
        this.attributesToExtract = attributesToExtract;
    }

    public Map<String,Map<String,String>> extractJsonAttributes() {
        // Parse JSON String to a JsonArray
        JsonArray jsonObjectArray = JsonParser.parseString(jsonString).getAsJsonArray();
        String[] rootElements = attributesToExtract.getJsonHierarchy();
        HashMap<String,Boolean> jsonAttributes =  attributesToExtract.getJsonAttributes();
        Map<String,Map<String,String>> jsonAttributesMap = new HashMap<String,Map<String,String>>();
        List<Map<String,String>>  attributesForUpdate =  new ArrayList<Map<String,String>>();
        
        // iterate over json attributes and create map  with unique attributes as key
        for (JsonElement jsonElement : jsonObjectArray){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for(String elements : rootElements) {
                jsonObject = jsonObject.getAsJsonObject(elements);
            }
            Map<String,String> attributesMap = new HashMap<String,String>();
            String uniqueAttributeKey = "";
            for(String attribute :jsonAttributes.keySet()){
                boolean isUniqueAttr = jsonAttributes.get(attribute);
                JsonElement attributeElement = jsonObject.get(attribute);
                if(attributeElement == null){
                    continue;
                }
                String attributeValue = attributeElement.getAsString();
                if(isUniqueAttr){
                    uniqueAttributeKey += "_"+ attributeValue;
                }
                attributesMap.put(attribute, attributeValue);
            }
            if(isUpdateRequired(attributesMap)){
                attributesForUpdate.add(attributesMap);
            }
            jsonAttributesMap.put(uniqueAttributeKey, attributesMap);
        }
        // if there is nothing to update return the exisitng map
        if(attributesForUpdate.size() ==  0) {
            return jsonAttributesMap;
        }
        // add updated attributed to map
        attributesForUpdate = updateAttributes(attributesForUpdate);
        for(Map<String,String> attributesMap : attributesForUpdate){
            String uniqueAttributeKey = "";
            for(String attribute :attributesMap.keySet()){  
                boolean isUniqueAttr = jsonAttributes.get(attribute);
                if(!isUniqueAttr){
                   continue;
                }
                String attributeValue = attributesMap.get(attribute);
                uniqueAttributeKey += "_"+ attributeValue;
            }
            jsonAttributesMap.put(uniqueAttributeKey, attributesMap);
        }
        return jsonAttributesMap;
    }
    // Hook provided for checking if any updated required specific to any attribute
    // return true if any update is required else return false
    protected abstract boolean isUpdateRequired(Map<String,String> attibutesMap);
    // Hook provided to update the attributes 
    protected abstract List<Map<String,String>> updateAttributes(List<Map<String,String>> attibutesListForUpdate);
}

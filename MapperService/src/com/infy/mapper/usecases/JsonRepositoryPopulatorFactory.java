// This is the use case class responsible for creating json repository populator for saving the
// data in the db
package com.infy.mapper.usecases;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.infy.mapper.domain.JsonExtractor;

public class JsonRepositoryPopulatorFactory {


    
    public JsonRepositoryPopulator getPopulator(JsonExtractor sourceExtractor, JsonExtractor metadataExtractor){
        return new JsonRepositoryPopulator(sourceExtractor,metadataExtractor);
    }

    public String GenerateRepositoryPopulator(){
        // call source json extractor
        Map<String, Map<String, String>> sourceData = sourceDataExtractor.extractJsonAttributes();
        // call metadata json extractor
        Map<String, Map<String, String>> metaData = metadataExtractor.extractJsonAttributes();
        // call repo populator creator
        return generateRepositoryJson(sourceData,metaData);
    }
    
    private String generateRepositoryJson(Map<String, Map<String, String>> sourceData,Map<String, Map<String, String>> metadataInfo){
        Map<String,JsonObject> classJsonDataMap = new HashMap<String,JsonObject>();
        
        Set<String> sourceKeys = sourceData.keySet();
        for(String sourceKey : sourceKeys){
            Map<String, String> metadata = metadataInfo.get(sourceKey);
            if(metadata == null){
                System.out.println("metadata info  not found for "+sourceKey);
                continue;
            }

           String className = metadata.get("_class");;

           JsonObject dbDataObject = classJsonDataMap.get(className);
           if(dbDataObject == null){
                dbDataObject = new JsonObject();
                dbDataObject.add("_class",new JsonPrimitive(className));
           }
           Map<String,String> sourceAttributes = sourceData.get(sourceKey);
           String dataType = metadata.get("dataType");
           JsonPrimitive jsonPrimitiveObj = null;
           String nameValue = sourceAttributes.get("value");
           if(dataType != null) {
                jsonPrimitiveObj = convertToJsonDataType(nameValue,dataType);
           }
           if(jsonPrimitiveObj == null) {
                jsonPrimitiveObj = new JsonPrimitive(nameValue);
           }
           dbDataObject.add(sourceAttributes.get("name"),jsonPrimitiveObj);
           classJsonDataMap.put(className,dbDataObject);
        }
        String repoJsonString = "";
        for (Map.Entry<String, JsonObject> entry : classJsonDataMap.entrySet()) {
            JsonObject jsonObject = entry.getValue();
            String jsonStr = new Gson().toJson(jsonObject);
            repoJsonString += jsonStr;
        } 
        return repoJsonString;
    }

    private JsonPrimitive convertToJsonDataType(String value, String dataType) {
        switch(dataType.toLowerCase()) {
        case "string" :
            return new JsonPrimitive(value);
        case "integer" :
            return  new JsonPrimitive(Integer.parseInt(value));
        case "float" :
            return  new JsonPrimitive(Float.parseFloat(value));
        }
        return null;
    } 
}

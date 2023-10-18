package com.aiq.integration;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


public class DBJsonCreator {
 public static Map<String,JsonObject> createDBJson(Map<String,JsonObject> metaDataMap, LoadDataObject[] aiqData) {

        Map<String,JsonObject> classJsonDataMap = new HashMap<String,JsonObject>();
        

        for(LoadDataObject dataObject : aiqData){
            JsonObject jsonObject = metaDataMap.get(dataObject.getDataPointId());
            if(jsonObject == null){
                continue;
            }
           String className = jsonObject.get("_class").getAsString();

           JsonObject dbDataObject = classJsonDataMap.get(className);
           if(dbDataObject == null){
                dbDataObject = new JsonObject();
                dbDataObject.add("_class",new JsonPrimitive(className));
           }
            
           dbDataObject.add(dataObject.getName(),new JsonPrimitive(dataObject.getValue()));
           classJsonDataMap.put(className,dbDataObject);
        }



        return classJsonDataMap;
    }
}

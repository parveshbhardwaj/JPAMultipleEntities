package com.aiq.integration;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class AIQ {

        public static void main(String[] args) {
        String jsonString = "[\n" +
        "    {\n" +
        "        \"loanDataPoint\": {\n" +
        "            \"loanDataPointDetails\": {\n" +
        "                \"id\": \"SOMEUNIQUEID\",\n" +
        "                \"dataPointId\": \"DATAPOINTID_UNIQUE_FOR_DOC\",\n" +
        "                \"name\": \"ATTRIBUTE_NAME\",\n" +
        "                \"value\": \"ACTUAL VALUE\",\n" +
        "                \"originalValue\": \"\",\n" +
        "                \"folderId\": \"unique_folder_id\",\n" +
        "                \"sourceId\": \"unique_source_id\",\n" +
        "                \"dateModified\": \"date\",\n" +
        "                \"collaboratorId\": \"UniqueId\",\n" +
        "                \"container\": \"containerid\",\n" +
        "                \"piiReferenceId\": \"ForPIIdata\",\n" +
        "                \"verified\": \"true\"\n" +
        "            },\n" +
        "            \"pageReferenceId\": \"somerefid\",\n" +
        "            \"pageId\": \"somepgno\"\n" +
        "        }\n" +
        "    },\n" +
        "    {\n" +
        "        \"loanDataPoint\": {\n" +
        "            \"loanDataPointDetails\": {\n" +
        "                \"id\": \"SOMEUNIQUEID\",\n" +
        "                \"dataPointId\": \"DATAPOINTID_UNIQUE_FOR_DOC1\",\n" +
        "                \"name\": \"ATTRIBUTE_NAME1\",\n" +
        "                \"value\": \"ACTUAL VALUE1\",\n" +
        "                \"originalValue\": \"\",\n" +
        "                \"folderId\": \"unique_folder_id\",\n" +
        "                \"sourceId\": \"unique_source_id\",\n" +
        "                \"dateModified\": \"date\",\n" +
        "                \"collaboratorId\": \"UniqueId\",\n" +
        "                \"container\": \"containerid\",\n" +
        "                \"piiReferenceId\": \"ForPIIdata\",\n" +
        "                \"verified\": \"true\"\n" +
        "            },\n" +
        "            \"pageReferenceId\": \"somerefid\",\n" +
        "            \"pageId\": \"somepgno\"\n" +
        "        }\n" +
        "    },\n" +
        "    {\n" +
        "        \"loanDataPoint\": {\n" +
        "            \"loanDataPointDetails\": {\n" +
        "                \"id\": \"SOMEUNIQUEID\",\n" +
        "                \"dataPointId\": \"DATAPOINTID_UNIQUE_FOR_DOC2\",\n" +
        "                \"name\": \"ATTRIBUTE_NAME1\",\n" +
        "                \"value\": \"ACTUAL VALUE2\",\n" +
        "                \"originalValue\": \"\",\n" +
        "                \"folderId\": \"unique_folder_id\",\n" +
        "                \"sourceId\": \"unique_source_id\",\n" +
        "                \"dateModified\": \"date\",\n" +
        "                \"collaboratorId\": \"UniqueId\",\n" +
        "                \"container\": \"containerid\",\n" +
        "                \"piiReferenceId\": \"ForPIIdata\",\n" +
        "                \"verified\": \"true\"\n" +
        "            },\n" +
        "            \"pageReferenceId\": \"somerefid\",\n" +
        "            \"pageId\": \"somepgno\"\n" +
        "        }\n" +
        "    }\n" +
        "]";
    

        JsonObject[] parsedValues = LoanDataPointParser.loanDataPointParser(jsonString);

        String jsonMetaDataString = "[\n" +
        "    {\n" +
        "        \"dataPointId\":\"DATAPOINTID_UNIQUE_FOR_DOC\",\n" +
        "        \"_class\":\"com.infy.test.class\",\n" +
        "        \"field\":\"ATTRIBUTE_NAME\",\n" +
        "        \"dataType\":\"varchar\",\n" +
        "        \"isRestricted\":\"True/False\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"dataPointId\":\"DATAPOINTID_UNIQUE_FOR_DOC1\",\n" +
        "        \"_class\":\"com.infy.test.class\",\n" +
        "        \"field\":\"ATTRIBUTE_NAME1\",\n" +
        "        \"dataType\":\"varchar\",\n" +
        "        \"isRestricted\":\"True/False\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"dataPointId\":\"DATAPOINTID_UNIQUE_FOR_DOC2\",\n" +
        "        \"_class\":\"com.infy.test.class1\",\n" +
        "        \"field\":\"ATTRIBUTE_NAME2\",\n" +
        "        \"dataType\":\"varchar\",\n" +
        "        \"isRestricted\":\"True/False\"\n" +
        "    }\n" +
        "]";

        Map<String,JsonObject> dataPointIdBlock = MetaDataParser.findBlockByDataPointIdAndName(jsonMetaDataString);
        Map<String,JsonObject> dbJsonObjects = DBJsonCreator.createDBJson(dataPointIdBlock,parsedValues);
        String dbJsonString = "";
        for (Map.Entry<String, JsonObject> entry : dbJsonObjects.entrySet()) {
            JsonObject jsonObject = entry.getValue();
            String jsonStr = new Gson().toJson(jsonObject);
            dbJsonString += jsonStr;
        } 
        System.out.println(dbJsonString);
    }

   static class MetaDataParser {
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
static class DBJsonCreator {
 public static Map<String,JsonObject> createDBJson(Map<String,JsonObject> metaDataMap, JsonObject[] aiqData) {

        Map<String,JsonObject> classJsonDataMap = new HashMap<String,JsonObject>();
        

        for(JsonObject dataObject : aiqData){
            JsonObject jsonObject = metaDataMap.get(dataObject.get("dataPointId").getAsString());
            if(jsonObject == null){
                continue;
            }
           String className = jsonObject.get("_class").getAsString();

           JsonObject dbDataObject = classJsonDataMap.get(className);
           if(dbDataObject == null){
                dbDataObject = new JsonObject();
                dbDataObject.add("_class",new JsonPrimitive(className));
           }
            
           dbDataObject.add(dataObject.get("name").getAsString(),new JsonPrimitive(dataObject.get("value").getAsString()));
           classJsonDataMap.put(className,dbDataObject);
        }



        return classJsonDataMap;
    }
}
static class LoanDataPointParser {
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
 static class LoadDataObject {
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
}
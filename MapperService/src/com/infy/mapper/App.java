package com.infy.mapper;

import java.util.HashMap;

import com.infy.mapper.domain.AttributeExtractionInformation;
import com.infy.mapper.domain.JsonExtractor;
import com.infy.mapper.extractor.DefaultJsonExtractor;
import com.infy.mapper.usecases.JsonRepositoryPopulator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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
        "    }\n" +
        "]";
    

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

       AttributeExtractionInformation sourceAttribute =  new AttributeExtractionInformation();
       String[] heirarcyElement = new String[2];
       heirarcyElement[0] = "loanDataPoint";
       heirarcyElement[1] = "loanDataPointDetails";
       sourceAttribute.setJsonHierarchy(heirarcyElement);
       HashMap<String,Boolean> jsonAttributes =  new HashMap<String,Boolean>();
       jsonAttributes.put("dataPointId",true);
       jsonAttributes.put("name",false);
       jsonAttributes.put("value",false);
       sourceAttribute.setJsonAttributes(jsonAttributes);

       AttributeExtractionInformation metadataAttribute =  new AttributeExtractionInformation();
       jsonAttributes =   new HashMap<String,Boolean>();
       jsonAttributes.put("dataPointId",true);
       jsonAttributes.put("_class",false);
       jsonAttributes.put("dataType",false);
       metadataAttribute.setJsonAttributes(jsonAttributes);

       JsonExtractor sourceJsonExtractor =  new DefaultJsonExtractor(jsonString,sourceAttribute);
       JsonExtractor metadataJsonExtractor =  new DefaultJsonExtractor(jsonMetaDataString,metadataAttribute);

       JsonRepositoryPopulator jsonRepoPopulator = new JsonRepositoryPopulator(sourceJsonExtractor,metadataJsonExtractor);
       String dbJson = jsonRepoPopulator.GenerateRepositoryPopulator();

       System.out.println(" db json creator "+dbJson);
     
    }
}

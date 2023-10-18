package com.infy.mapper.extractor;

import java.util.List;
import java.util.Map;

import com.infy.mapper.domain.AttributeExtractionInformation;
import com.infy.mapper.domain.JsonExtractor;

public class DefaultJsonExtractor extends JsonExtractor{

    public DefaultJsonExtractor(String jsonString,AttributeExtractionInformation attributesToExtract) {
        super(jsonString,attributesToExtract);
    }

    @Override
    protected List<Map<String, String>> updateAttributes(List<Map<String, String>> attibutesMap) {
        return attibutesMap;
    }

    @Override
    protected boolean isUpdateRequired(Map<String, String> attibutesMap) {
       return false;
    } 
}

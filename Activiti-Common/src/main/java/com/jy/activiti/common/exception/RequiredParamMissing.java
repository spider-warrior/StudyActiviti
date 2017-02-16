package com.jy.activiti.common.exception;

import java.util.ArrayList;
import java.util.List;

public class RequiredParamMissing extends RuntimeException{

    private final List<String> fieldNameList;

    public void addMissingField(String fieldName) {
        fieldNameList.add(fieldName);
    }
    public void addMissingFields(List<String> fieldNameList) {
        if (fieldNameList != null) {
            this.fieldNameList.addAll(fieldNameList);
        }
    }

    public List<String> getFieldNameList() {
        return fieldNameList;
    }

    public RequiredParamMissing(List<String> fieldNameList) {
        if (fieldNameList == null) {
            this.fieldNameList = new ArrayList<>();
        }
        else {
            this.fieldNameList = fieldNameList;
        }
    }

    @Override
    public String toString() {
        return "RequiredParamMissing{" +
                "fieldNameList=" + fieldNameList +
                "} " + super.toString();
    }
}

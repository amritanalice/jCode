package com.ibm.mr.inventory.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.ibm.mr.inventory.views.JsonViews;

public class JsonContainer {
    
    @JsonView(JsonViews.Public.class)
    List<?> data;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
    

}

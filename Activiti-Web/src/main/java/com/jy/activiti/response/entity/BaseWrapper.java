package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseWrapper {

    @JsonProperty("id")
    private String id;

    public String getId() {
        return id;
    }

    public BaseWrapper setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "BaseWrapper{" +
                "id='" + id + '\'' +
                '}';
    }

    public static class Config {
        private boolean needId;

        public boolean isNeedId() {
            return needId;
        }

        public Config setNeedId(boolean needId) {
            this.needId = needId;
            return this;
        }
    }
}

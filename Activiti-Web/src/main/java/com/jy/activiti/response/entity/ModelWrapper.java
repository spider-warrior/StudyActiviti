package com.jy.activiti.response.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelWrapper extends BaseWrapper {

    /**
     * 名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * key
     */
    @JsonProperty("key")
    private String key;

    /**
     * 范畴
     */
    @JsonProperty("category")
    private String category;

    /**
     * 创建时间
     */
    @JsonProperty("createTime")
    private String createTime;

    /**
     * 版本
     */
    @JsonProperty("version")
    private String version;

    /**
     * metaInfo
     */
    @JsonProperty("metaInfo")
    private String metaInfo;

    /**
     * 部署ID
     */
    @JsonProperty("deploymentId")
    private String deploymentId;

    public String getName() {
        return name;
    }

    public ModelWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public String getKey() {
        return key;
    }

    public ModelWrapper setKey(String key) {
        this.key = key;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ModelWrapper setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public ModelWrapper setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ModelWrapper setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public ModelWrapper setMetaInfo(String metaInfo) {
        this.metaInfo = metaInfo;
        return this;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public ModelWrapper setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
        return this;
    }
    
    public static class ModelWrapperConfig extends BaseWrapper.Config {

        private boolean needName;
        private boolean needKey;
        private boolean needCategory;
        private boolean needCreateTime;
        private boolean needVersion;
        private boolean needMetaInfo;
        private boolean needDeploymentId;

        public boolean isNeedName() {
            return needName;
        }

        public ModelWrapperConfig setNeedName(boolean needName) {
            this.needName = needName;
            return this;
        }

        public boolean isNeedKey() {
            return needKey;
        }

        public ModelWrapperConfig setNeedKey(boolean needKey) {
            this.needKey = needKey;
            return this;
        }

        public boolean isNeedCategory() {
            return needCategory;
        }

        public ModelWrapperConfig setNeedCategory(boolean needCategory) {
            this.needCategory = needCategory;
            return this;
        }

        public boolean isNeedCreateTime() {
            return needCreateTime;
        }

        public ModelWrapperConfig setNeedCreateTime(boolean needCreateTime) {
            this.needCreateTime = needCreateTime;
            return this;
        }

        public boolean isNeedVersion() {
            return needVersion;
        }

        public ModelWrapperConfig setNeedVersion(boolean needVersion) {
            this.needVersion = needVersion;
            return this;
        }

        public boolean isNeedMetaInfo() {
            return needMetaInfo;
        }

        public ModelWrapperConfig setNeedMetaInfo(boolean needMetaInfo) {
            this.needMetaInfo = needMetaInfo;
            return this;
        }

        public boolean isNeedDeploymentId() {
            return needDeploymentId;
        }

        public ModelWrapperConfig setNeedDeploymentId(boolean needDeploymentId) {
            this.needDeploymentId = needDeploymentId;
            return this;
        }
    }
}

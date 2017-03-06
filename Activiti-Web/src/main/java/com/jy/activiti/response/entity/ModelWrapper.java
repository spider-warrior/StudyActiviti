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
}

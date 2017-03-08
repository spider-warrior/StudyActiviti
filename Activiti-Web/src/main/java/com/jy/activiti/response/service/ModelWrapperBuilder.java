package com.jy.activiti.response.service;

import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.response.entity.ModelWrapper;
import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelWrapperBuilder {

    private static final ModelWrapper empty = new ModelWrapper();

    public ModelWrapper buildModelWrapper(Model model, ModelWrapper.ModelWrapperConfig config) {
        if (model == null) {
            return empty;
        }
        ModelWrapper modelWrapper = new ModelWrapper();
        if (config.isNeedId()) {
            modelWrapper.setId(model.getId());
        }
        if (config.isNeedName()) {
            modelWrapper.setName(model.getName());
        }
        if (config.isNeedCategory()) {
            modelWrapper.setCategory(model.getCategory());
        }
        if (config.isNeedCreateTime()) {
            modelWrapper.setCreateTime(TimeUtil.formatYYYYMMMDDHHMMSS(model.getCreateTime()));
        }
        if (config.isNeedDeploymentId()) {
            modelWrapper.setDeploymentId(model.getDeploymentId());
        }
        if (config.isNeedKey()) {
            modelWrapper.setKey(model.getKey());
        }
        if (config.isNeedVersion()) {
            modelWrapper.setVersion(model.getVersion() == null ? "" : model.getVersion().toString());
        }
        if (config.isNeedMetaInfo()) {
            modelWrapper.setMetaInfo(model.getMetaInfo());
        }
        return modelWrapper;
    }
}

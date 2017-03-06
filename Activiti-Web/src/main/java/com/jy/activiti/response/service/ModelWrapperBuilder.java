package com.jy.activiti.response.service;

import com.jy.activiti.common.util.TimeUtil;
import com.jy.activiti.response.entity.ModelWrapper;
import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelWrapperBuilder {

    private static final ModelWrapper empty = new ModelWrapper();

    public ModelWrapper buildModelWrapper(Model model) {
        if (model == null) {
            return empty;
        }
        ModelWrapper modelWrapper = new ModelWrapper();
        modelWrapper.setId(model.getId());
        modelWrapper.setName(model.getName());
        modelWrapper.setCategory(model.getCategory());
        modelWrapper.setCreateTime(TimeUtil.formatYYYYMMMDDHHMMSS(model.getCreateTime()));
        modelWrapper.setDeploymentId(model.getDeploymentId());
        modelWrapper.setKey(model.getKey());
        modelWrapper.setVersion(model.getVersion() == null ? "" : model.getVersion().toString());
        modelWrapper.setMetaInfo(model.getMetaInfo());
        return modelWrapper;
    }
}

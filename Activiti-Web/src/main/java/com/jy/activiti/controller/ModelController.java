package com.jy.activiti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jy.activiti.common.enums.ResourcesType;
import com.jy.activiti.response.entity.ModelWrapper;
import com.jy.activiti.response.service.ModelWrapperBuilder;
import com.jy.activiti.service.exception.ExceptionCode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/model")
@RestController
public class ModelController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ModelWrapperBuilder modelWrapperBuilder;
    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getModels(@RequestBody(required = false) Map<String, String> allRequestParams) {
        ModelQuery modelQuery = this.repositoryService.createModelQuery();
        if(allRequestParams.containsKey("id")) {
            modelQuery.modelId((String)allRequestParams.get("id"));
        }

        if(allRequestParams.containsKey("category")) {
            modelQuery.modelCategory((String)allRequestParams.get("category"));
        }

        if(allRequestParams.containsKey("categoryLike")) {
            modelQuery.modelCategoryLike((String)allRequestParams.get("categoryLike"));
        }

        if(allRequestParams.containsKey("categoryNotEquals")) {
            modelQuery.modelCategoryNotEquals((String)allRequestParams.get("categoryNotEquals"));
        }

        if(allRequestParams.containsKey("name")) {
            modelQuery.modelName((String)allRequestParams.get("name"));
        }

        if(allRequestParams.containsKey("nameLike")) {
            modelQuery.modelNameLike((String)allRequestParams.get("nameLike"));
        }

        if(allRequestParams.containsKey("key")) {
            modelQuery.modelKey((String)allRequestParams.get("key"));
        }

        if(allRequestParams.containsKey("version")) {
            modelQuery.modelVersion(Integer.valueOf((String)allRequestParams.get("version")));
        }

        boolean withoutTenantId;
        if(allRequestParams.containsKey("latestVersion")) {
            withoutTenantId = Boolean.valueOf((String)allRequestParams.get("latestVersion")).booleanValue();
            if(withoutTenantId) {
                modelQuery.latestVersion();
            }
        }

        if(allRequestParams.containsKey("deploymentId")) {
            modelQuery.deploymentId((String)allRequestParams.get("deploymentId"));
        }

        if(allRequestParams.containsKey("deployed")) {
            withoutTenantId = Boolean.valueOf((String)allRequestParams.get("deployed")).booleanValue();
            if(withoutTenantId) {
                modelQuery.deployed();
            } else {
                modelQuery.notDeployed();
            }
        }

        if(allRequestParams.containsKey("tenantId")) {
            modelQuery.modelTenantId((String)allRequestParams.get("tenantId"));
        }

        if(allRequestParams.containsKey("tenantIdLike")) {
            modelQuery.modelTenantIdLike((String)allRequestParams.get("tenantIdLike"));
        }

        if(allRequestParams.containsKey("withoutTenantId")) {
            withoutTenantId = Boolean.valueOf((String)allRequestParams.get("withoutTenantId")).booleanValue();
            if(withoutTenantId) {
                modelQuery.modelWithoutTenantId();
            }
        }
        List<Model> modelList = modelQuery.list();
        if (modelList.size() == 0) {
            return success(modelList);
        }
        List<ModelWrapper> modelWrapperList = new ArrayList<>(modelList.size());
        modelList.forEach(model -> modelWrapperList.add(modelWrapperBuilder.buildModelWrapper(model)));
        Map<String, Object> result = new HashMap<>();
        result.put("models", modelWrapperList);
        return success(result);
    }


    @RequestMapping(value = "/save/quick", method = RequestMethod.POST)
    public Object quickCreateModel(@RequestBody Map<String, String> param) throws UnsupportedEncodingException {

        String modelName = param.get("name");
        String description = param.get("description");

        //editorNode
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        //stencilSetNode
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.set("stencilset", stencilSetNode);
        //modelObjectNode
        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);

        Model modelData = repositoryService.newModel();
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(modelName);

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

        return success();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Object createModel(@RequestBody Map<String, String> param) throws UnsupportedEncodingException {

        String name = param.get("name");
        String categoty = param.get("categoty");
        String deploymentId = param.get("deploymentId");
        String key = param.get("key");
        String metaInfo = param.get("metaInfo");
        String tenantId = param.get("tenantId");
        String versionStr = param.get("version");

        Integer version = null;
        try {
            version = Integer.parseInt(versionStr);
        } catch (Exception e) {
            return fail(ExceptionCode.PARAM_INVALID.getValue());
        }
        Model model = this.repositoryService.newModel();
        model.setCategory(categoty);
        model.setDeploymentId(deploymentId);
        model.setKey(key);
        model.setMetaInfo(metaInfo);
        model.setName(name);
        model.setTenantId(tenantId);
        model.setVersion(version);

        this.repositoryService.saveModel(model);

        return success();
    }


    @RequestMapping(value="/{modelId}", method = RequestMethod.DELETE)
    public Object deleteModel(@PathVariable String modelId) {
        repositoryService.deleteModel(modelId);
        return success();
    }

    @RequestMapping(value="/deploy/{modelId}", method = RequestMethod.GET)
    public Object deployModel(@PathVariable String modelId) throws IOException {

        Model model = repositoryService.createModelQuery().modelId(modelId).singleResult();
        if (model == null) {
            return fail(ExceptionCode.SOURCE_NOT_FOUND_EXCEPTION.getValue(), ResourcesType.PROCESSDEFINITION_MODEL.getValue());
        }
        byte[] editorSource = repositoryService.getModelEditorSource(modelId);
        if (editorSource == null) {
            return fail(ExceptionCode.SOURCE_NOT_FOUND_EXCEPTION.getValue(), ResourcesType.PROCESSDEFINITION_DOC.getValue());
        }
        JsonNode modelNode = objectMapper.readTree(editorSource);

        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);

        String processName = model.getName() + ".bpmn20.xml";
        repositoryService.createDeployment()
                .name(model.getName())
                .addString(processName, new String(bpmnBytes))
                .deploy();
        return success();
    }

    @RequestMapping(value="/export/{modelId}", method = RequestMethod.GET)
    public Object exportModel(@PathVariable String modelId, HttpServletResponse response) throws IOException {
        byte[] editorSource = repositoryService.getModelEditorSource(modelId);
        if (editorSource == null) {
            return fail(ExceptionCode.SOURCE_NOT_FOUND_EXCEPTION.getValue(), ResourcesType.PROCESSDEFINITION_DOC.getValue());
        }
        JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelId));
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);

        response.setContentType("application/octet-stream");
        response.addHeader("content-Disposition", "attachment;fileName="+ URLEncoder.encode(filename, "utf-8"));
        return bpmnBytes;
    }

    @RequestMapping(value="/image/{modelId}", method = RequestMethod.GET)
    public Object modelImage(@PathVariable String modelId, HttpServletResponse response) throws IOException {
        final byte[] editorSourceExtra = repositoryService.getModelEditorSourceExtra(modelId);
        if (editorSourceExtra == null) {
            return fail(ExceptionCode.SOURCE_NOT_FOUND_EXCEPTION.getValue(), ResourcesType.PROCESSDEFINITION_IMG.getValue());
        }
        response.setContentType("image/png");
        response.addHeader("content-Disposition", "attachment;fileName="+ URLEncoder.encode("流程图.png", "utf-8"));
        return editorSourceExtra;
    }

    public ModelController() {

    }
}

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
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cmd.SetProcessDefinitionVersionCmd;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.activiti.editor.constants.ModelDataJsonConstants.*;

@RequestMapping("/model")
@RestController
public class ModelController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private ModelWrapperBuilder modelWrapperBuilder;
    @Autowired
    private CommandExecutor commandExecutor;
    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getModels(@RequestBody(required = false) Map<String, String> allRequestParams) {
        ModelQuery modelQuery = this.repositoryService.createModelQuery();
        if (allRequestParams.containsKey("id")) {
            modelQuery.modelId((String) allRequestParams.get("id"));
        }

        if (allRequestParams.containsKey("category")) {
            modelQuery.modelCategory((String) allRequestParams.get("category"));
        }

        if (allRequestParams.containsKey("categoryLike")) {
            modelQuery.modelCategoryLike((String) allRequestParams.get("categoryLike"));
        }

        if (allRequestParams.containsKey("categoryNotEquals")) {
            modelQuery.modelCategoryNotEquals((String) allRequestParams.get("categoryNotEquals"));
        }

        if (allRequestParams.containsKey("name")) {
            modelQuery.modelName((String) allRequestParams.get("name"));
        }

        if (allRequestParams.containsKey("nameLike")) {
            modelQuery.modelNameLike((String) allRequestParams.get("nameLike"));
        }

        if (allRequestParams.containsKey("key")) {
            modelQuery.modelKey((String) allRequestParams.get("key"));
        }

        if (allRequestParams.containsKey("version")) {
            modelQuery.modelVersion(Integer.valueOf((String) allRequestParams.get("version")));
        }

        boolean withoutTenantId;
        if (allRequestParams.containsKey("latestVersion")) {
            withoutTenantId = Boolean.valueOf((String) allRequestParams.get("latestVersion")).booleanValue();
            if (withoutTenantId) {
                modelQuery.latestVersion();
            }
        }

        if (allRequestParams.containsKey("deploymentId")) {
            modelQuery.deploymentId((String) allRequestParams.get("deploymentId"));
        }

        if (allRequestParams.containsKey("deployed")) {
            withoutTenantId = Boolean.valueOf((String) allRequestParams.get("deployed")).booleanValue();
            if (withoutTenantId) {
                modelQuery.deployed();
            } else {
                modelQuery.notDeployed();
            }
        }

        if (allRequestParams.containsKey("tenantId")) {
            modelQuery.modelTenantId((String) allRequestParams.get("tenantId"));
        }

        if (allRequestParams.containsKey("tenantIdLike")) {
            modelQuery.modelTenantIdLike((String) allRequestParams.get("tenantIdLike"));
        }

        if (allRequestParams.containsKey("withoutTenantId")) {
            withoutTenantId = Boolean.valueOf((String) allRequestParams.get("withoutTenantId")).booleanValue();
            if (withoutTenantId) {
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
        modelObjectNode.put(MODEL_NAME, modelName);
        modelObjectNode.put(MODEL_REVISION, 1);
        modelObjectNode.put(MODEL_DESCRIPTION, description);

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
        Model model = repositoryService.newModel();
        model.setCategory(categoty);
        model.setDeploymentId(deploymentId);
        model.setKey(key);
        model.setMetaInfo(metaInfo);
        model.setName(name);
        model.setTenantId(tenantId);
        model.setVersion(version);

        repositoryService.saveModel(model);

        return success();
    }


    @RequestMapping(value = "/{modelId}", method = RequestMethod.DELETE)
    public Object deleteModel(@PathVariable String modelId) {
        repositoryService.deleteModel(modelId);
        return success();
    }

    @RequestMapping(value = "/deploy/{modelId}", method = RequestMethod.GET)
    public Object deployModel(@PathVariable String modelId) throws IOException {

        Model model = repositoryService.createModelQuery().modelId(modelId).singleResult();
        if (model == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION_MODEL.getValue());
        }
        byte[] editorSource = repositoryService.getModelEditorSource(modelId);
        if (editorSource == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION_DOC.getValue());
        }
        JsonNode modelNode = objectMapper.readTree(editorSource);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);

        String processName = model.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .enableDuplicateFiltering()
                .name(model.getName())
                .addBpmnModel(processName, bpmnModel)
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        //曾经部署过,更新旧流程实例到新流程
        if (model.getDeploymentId() != null) {
            List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().deploymentId(model.getDeploymentId()).list();
            for (ProcessInstance processInstance: processInstanceList) {
                commandExecutor.execute(new SetProcessDefinitionVersionCmd(processInstance.getProcessInstanceId(), processDefinition.getVersion()));
            }
        }
        model = repositoryService.createModelQuery().modelId(modelId).singleResult();
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
        return success();
    }

    @RequestMapping(value = "/export/{modelId}", method = RequestMethod.GET)
    public Object exportModel(@PathVariable String modelId, HttpServletResponse response) throws IOException {
        byte[] editorSource = repositoryService.getModelEditorSource(modelId);
        if (editorSource == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION_DOC.getValue());
        }
        JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelId));
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);

        response.setContentType("application/octet-stream");
        response.addHeader("content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
        return bpmnBytes;
    }

    @RequestMapping(value = "/image/{modelId}", method = RequestMethod.GET)
    public Object modelImage(@PathVariable String modelId, HttpServletResponse response) throws IOException {
        final byte[] editorSourceExtra = repositoryService.getModelEditorSourceExtra(modelId);
        if (editorSourceExtra == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION_IMG.getValue());
        }
        response.setContentType("image/png");
        response.addHeader("content-Disposition", "attachment;fileName=" + URLEncoder.encode("流程图.png", "utf-8"));
        return editorSourceExtra;
    }

    @RequestMapping(value = "/import/{processDefinitionId}", method = RequestMethod.GET)
    public Object importModelByProcessDefinition(@PathVariable("processDefinitionId") String processDefinitionId) throws IOException, XMLStreamException {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        if (pd == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION_DOC.getValue());
        }
        InputStream xmlsourceInputStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getResourceName());
        if (xmlsourceInputStream == null) {
            return failSourceNotFound(ResourcesType.PROCESSDEFINITION_DOC.getValue());
        }
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(xmlsourceInputStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        ObjectNode objectNode = jsonConverter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(MODEL_NAME, pd.getName());
        modelObjectNode.put(MODEL_REVISION, 1);
        modelObjectNode.put(MODEL_DESCRIPTION, pd.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(pd.getName());
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), objectNode.toString().getBytes("utf-8"));
        return success();
    }

    public ModelController() {

    }
}

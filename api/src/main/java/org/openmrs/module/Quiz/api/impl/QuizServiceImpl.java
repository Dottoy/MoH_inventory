/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.Quiz.api.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.Quiz.api.QuizService;
import org.openmrs.module.Quiz.api.db.QuizDAO;
import org.openmrs.module.Quiz.model.AttributeNames;
import org.openmrs.module.Quiz.model.MohDeviceDetails;
import org.openmrs.module.Quiz.util.DateUtil;

import java.util.List;

/**
 * Implementation of {@link QuizService}.
 */


public class QuizServiceImpl extends BaseOpenmrsService implements QuizService {

    private QuizDAO quizDAO;

    public QuizDAO getQuizDAO() {
        return quizDAO;
    }

    public void setQuizDAO(QuizDAO quizDAO) {
        this.quizDAO = quizDAO;
    }



    @Override
    public String addDeviceTypeObject(String deviceTypeBody) {
        JSONObject itemObject = new JSONObject(deviceTypeBody);
        return quizDAO.addDeviceType(itemObject.getString("type_name"));
    }

    @Override
    public String updateDeviceTypeObject(String deviceTypeBody) {
        JSONObject itemObject = new JSONObject(deviceTypeBody);
        if (itemObject.has("type_name") && itemObject.has("type_id")){
            String status = quizDAO.updateDeviceTypeObject(itemObject.getString("type_name"),itemObject.getString("type_id"));
            JSONObject statusObject = new JSONObject();
            String message = "Something went wrong, fail to complete your request, Refresh your browser and try again, if you continue to experience this kind of error contact support team!";
            if (status.equalsIgnoreCase("success")){
                message = "Request completed successfully, Device Type details changed!";
                statusObject.put("message",message);
                return statusObject.toString();
            } else {
                statusObject.put("message",message);
                return statusObject.toString();
            }
        }else{
            JSONObject statusObject = new JSONObject();
            statusObject.put("message","All fields are required!");
            return statusObject.toString();
        }
    }


    @Override
    public String addItemObject(String ItemPayload){
        JSONObject itemObject = new JSONObject(ItemPayload);
        if (itemObject.has("item_name") && itemObject.has("description")){
            return quizDAO.addItem(itemObject.getString("item_name"), itemObject.getString("description"));
        }else{
            JSONObject statusObject = new JSONObject();
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Incorrect Object Provided");
            return statusObject.toString();
        }

    }

    @Override
    public String addDevice(String DevicePayload) {
        JSONObject itemObject = new JSONObject(DevicePayload);
        if (itemObject.has("device_type_id") && itemObject.has("device_name")){
            return quizDAO.addDevice(itemObject.getInt("device_type_id"), itemObject.getString("device_name"));
        }else{
            JSONObject statusObject = new JSONObject();
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public String updateDevice(String detailPayload) {
        JSONObject itemObject = new JSONObject(detailPayload);
        if (itemObject.has("uuid") && itemObject.has("device_type_id") && itemObject.has("device_name")){
            return quizDAO.updateDevice(itemObject.getString("uuid"), itemObject.getInt("device_type_id"), itemObject.getString("device_name"));
        }else{
            JSONObject statusObject = new JSONObject();
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public List deviceList() {
        return quizDAO.deviceList();
    }

    @Override
    public String addAttributeNames(String names) {
        JSONObject attributeName = new JSONObject(names);
        if (attributeName.has("name"))
        {
            return quizDAO.addAttributeNames(attributeName.getString("name"), attributeName.getString("description"),attributeName.getString("format"));
        }
        else
        {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public String updateAttributeName(String name){
        JSONObject attributeName = new JSONObject(name);
        JSONObject statusObject = new JSONObject();
        if (attributeName.has("name") && attributeName.has("description") && attributeName.has("uuid")){
            String res = quizDAO.updateAttributeName(attributeName.getString("name"), attributeName.getString("description"),attributeName.getString("format"),attributeName.getString("uuid"));
            if (res.equalsIgnoreCase("success"))
            {
                statusObject.put("status","success");
                statusObject.put("statusCode",200);
                statusObject.put("message","Attribute name updated successfully");
                return statusObject.toString();
            }
            else {
                statusObject.put("status","failed");
                statusObject.put("statusCode",500);
                statusObject.put("message","Failed to update attribute name information");
                return statusObject.toString();
            }
        }

        statusObject.put("status","failed");
        statusObject.put("statusCode",1000);
        statusObject.put("message","Important parameters are missing!");
        return statusObject.toString();
    }

    @Override
    public List getAttributeName() {
        return quizDAO.getAttributeName();
    }

    @Override
    public String setDeviceAttribute(String detailPayload) {
        JSONObject attributeObject = new JSONObject(detailPayload);
        if (attributeObject.has("attributes")){
            JSONArray attributesArray = attributeObject.getJSONArray("attributes");
            if (attributesArray.length() > 0){
                String status = "";
                for (Object a : attributesArray){
                    JSONObject attributesObject = (JSONObject)a;

                    if (attributesObject.has("deviceUuid") && attributesObject.has("attributeUuid")){
                        status = "attributeUuid";
                        MohDeviceDetails deviceId =  quizDAO.setDeviceId(attributesObject.getString("deviceUuid"));
                        AttributeNames attributeId = quizDAO.setDeviceAttribute(attributesObject.getString("attributeUuid"));

                        if (deviceId != null && attributeId != null){
                            status = quizDAO.setDeviceAttribute(deviceId.getDeviceId(), attributeId.getAttributeId());
                        }
                    }
                }
//                    return  status;
                JSONObject statusObject = new JSONObject();
                if (status.equalsIgnoreCase("success")){
                    statusObject.put("status","success");
                    statusObject.put("message","attribute set successfully");
                    statusObject.put("statusCode",200);
                }else if(status.equalsIgnoreCase("exist")){
                    statusObject.put("status","failed");
                    statusObject.put("message","Record Exist");
                    statusObject.put("statusCode",401);
                }else {
                    statusObject.put("status","failed");
                    statusObject.put("message","failed to set attribute");
                    statusObject.put("statusCode",500);
                }
                return statusObject.toString();

            }
        }
        return null;
    }

    @Override
    public String addDeviceStatus(String status) {
        JSONObject deviceStatus = new JSONObject(status);
        if (deviceStatus.has("status_name"))
        {
            return quizDAO.addDeviceStatus(deviceStatus.getString("status_name"));
        }
        else
        {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public String updateDeviceStatus(String status) {
        JSONObject deviceStatus = new JSONObject(status);
        JSONObject statusObject = new JSONObject();
        if (deviceStatus.has("status_name") && deviceStatus.has("uuid")){
            String res = quizDAO.updateDeviceStatus(deviceStatus.getString("status_name"),deviceStatus.getString("uuid"));
            if (res.equalsIgnoreCase("success"))
            {
                statusObject.put("status","success");
                statusObject.put("statusCode",200);
                statusObject.put("message","Device status updated successfully");
                return statusObject.toString();
            }
            else {
                statusObject.put("status","failed");
                statusObject.put("statusCode",500);
                statusObject.put("message","Failed to update device status");
                return statusObject.toString();
            }
        }

        statusObject.put("status","failed");
        statusObject.put("statusCode",1000);
        statusObject.put("message","Important parameters are missing!");
        return statusObject.toString();
    }

    @Override
    public List getDeviceStatus() {
        return quizDAO.getDeviceStatus();
    }

    public List deviceTypeList() {
        return quizDAO.getDeviceTypeList();
    }

    @Override
    public String addDeviceInventoryAnswer(String answers) {
        JSONObject inventoryAnswers = new JSONObject(answers);
        if(inventoryAnswers.has("attribute_name_id") && inventoryAnswers.has("attribute_value"))
        {
            return quizDAO.addDeviceInventoryAnswer(inventoryAnswers.getInt("attribute_name_id"),inventoryAnswers.getString("attribute_value"));
        }
        else
        {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public List getDeviceInventoryAnswers() {
        return quizDAO.getDeviceInventoryAnswers();
    }

}
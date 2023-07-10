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

import org.json.JSONObject;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.Quiz.api.QuizService;
import org.openmrs.module.Quiz.api.db.QuizDAO;
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
        return null;
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
    public List getAttributeName(String name) {
        return quizDAO.getAttributeName(name);
    }
}
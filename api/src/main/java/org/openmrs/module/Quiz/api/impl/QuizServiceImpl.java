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
}
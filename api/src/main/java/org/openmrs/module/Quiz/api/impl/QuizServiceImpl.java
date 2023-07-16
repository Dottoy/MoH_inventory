/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.Quiz.api.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.Quiz.api.QuizService;
import org.openmrs.module.Quiz.api.db.QuizDAO;
import org.openmrs.module.Quiz.model.AttributeNames;
import org.openmrs.module.Quiz.model.MohDeviceDetails;
import org.openmrs.module.Quiz.model.MohDeviceInventory;
import org.openmrs.module.Quiz.model.MohDeviceStatus;
import org.openmrs.module.Quiz.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;

import java.util.List;
import java.util.UUID;
import com.google.gson.Gson;
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

    public String addDeviceMovementObject(String deviceMovementBody){
        JSONObject deviceObject = new JSONObject(deviceMovementBody);
        if (deviceObject.has("dev_uuid") && deviceObject.has("receiver_uuid") && deviceObject.has("sender_uuid") && deviceObject.has("location_uuid")){
            if (deviceObject.getString("dev_uuid").equalsIgnoreCase("") || deviceObject.getString("receiver_uuid").equalsIgnoreCase("") || deviceObject.getString("sender_uuid").equalsIgnoreCase("") || deviceObject.getString("location_uuid").equalsIgnoreCase("")){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message","Review you data, some details are missing to process this request!");
                return jsonObject.toString();
            }else{
                return quizDAO.addDeviceMovementObject(deviceObject.getString("dev_uuid"),deviceObject.getString("receiver_uuid"),deviceObject.getString("sender_uuid"),deviceObject.getString("location_uuid"));
            }
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message","All fields are required");
            return jsonObject.toString();
        }
    }

    @Override
    public String updateDeviceTypeObject(String deviceTypeBody) {
        JSONObject itemObject = new JSONObject(deviceTypeBody);
        if (itemObject.has("type_name") && itemObject.has("type_id")) {
            String status = quizDAO.updateDeviceTypeObject(itemObject.getString("type_name"), itemObject.getString("type_id"));
            JSONObject statusObject = new JSONObject();
            String message = "Something went wrong, fail to complete your request, Refresh your browser and try again, if you continue to experience this kind of error contact support team!";
            if (status.equalsIgnoreCase("success")) {
                message = "Request completed successfully, Device Type details changed!";
                statusObject.put("message", message);
                return statusObject.toString();
            } else {
                statusObject.put("message", message);
                return statusObject.toString();
            }
        } else {
            JSONObject statusObject = new JSONObject();
            statusObject.put("message", "All fields are required!");
            return statusObject.toString();
        }
    }

    @Override
    public String verifyUserNidNumber(String nidaNumber) {
        JSONObject nidaObject = new JSONObject(nidaNumber);
        if (!nidaObject.getString("nida_number").equalsIgnoreCase("")){
            String myNida = nidaObject.getString("nida_number");
            try{
                // Create URL object with the target URL
                URL url = new URL("https://ors.brela.go.tz/um/load/load_nida/"+myNida);

                // Create HttpURLConnection object
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set request method (GET, POST, PUT, DELETE, etc.)
                connection.setRequestMethod("POST");

                // Optional: Set request headers
                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Authorization", "Bearer <your_token>");

                // Request body
                String requestBody = "{\"key\":\"value\"}";

                // Set the Content-Length header
                connection.setRequestProperty("Content-Length", String.valueOf(requestBody.length()));

                // Enable output and write the request body
                connection.setDoOutput(true);
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(requestBody);
                outputStream.flush();
                outputStream.close();

                // Get response code
                int responseCode = connection.getResponseCode();
//                System.out.println("Response Code: " + responseCode);

                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Close the connection
                connection.disconnect();
                String requestResponse = response.toString();

                // Parse the JSON string into a JsonObject
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(requestResponse, JsonObject.class);


                // Access the nested data
                JsonObject resultObject = jsonObject.getAsJsonObject("obj").getAsJsonObject("result");
                if (jsonObject.has("obj") && jsonObject.get("obj").getAsJsonObject().has("result")){
                    String nin          = resultObject.get("NIN").getAsString();
                    String firstName    = resultObject.get("FIRSTNAME").getAsString();
                    String lastName     = resultObject.get("SURNAME").getAsString();
                    String dateOfBirth  = resultObject.get("DATEOFBIRTH").getAsString();
                    String sex          = resultObject.get("SEX").getAsString();
                    String nationality  = resultObject.get("NATIONALITY").getAsString();

                    return quizDAO.saveUserNiNDetails(nin,firstName,lastName,dateOfBirth,sex,nationality);

                }else{
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("message","Supplied NiN is not found, Review your details and try again!");
                    return jsonObject1.toString();
                }

            }catch (IOException e) {
                return e.getMessage();
            }
        }else{
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("message","Please supply your nida number to verify your details!");
            return jsonObject1.toString();
        }
    }


    @Override
    public String addItemObject(String ItemPayload) {
        JSONObject itemObject = new JSONObject(ItemPayload);
        if (itemObject.has("item_name") && itemObject.has("description")) {
            return quizDAO.addItem(itemObject.getString("item_name"), itemObject.getString("description"));
        } else {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public String addDevice(String DevicePayload) {
        JSONObject itemObject = new JSONObject(DevicePayload);
        if (itemObject.has("device_type_id") && itemObject.has("device_name")) {
            return quizDAO.addDevice(itemObject.getInt("device_type_id"), itemObject.getString("device_name"));
        } else {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public String updateDevice(String detailPayload) {
        JSONObject itemObject = new JSONObject(detailPayload);
        if (itemObject.has("uuid") && itemObject.has("device_type_id") && itemObject.has("device_name")) {
            return quizDAO.updateDevice(itemObject.getString("uuid"), itemObject.getInt("device_type_id"), itemObject.getString("device_name"));
        } else {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Incorrect Object Provided");
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
        if (attributeName.has("name")) {
            return quizDAO.addAttributeNames(attributeName.getString("name"), attributeName.getString("description"), attributeName.getString("format"));
        } else {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public String updateAttributeName(String name) {
        JSONObject attributeName = new JSONObject(name);
        JSONObject statusObject = new JSONObject();
        if (attributeName.has("name") && attributeName.has("description") && attributeName.has("uuid")) {
            String res = quizDAO.updateAttributeName(attributeName.getString("name"), attributeName.getString("description"), attributeName.getString("format"), attributeName.getString("uuid"));
            if (res.equalsIgnoreCase("success")) {
                statusObject.put("status", "success");
                statusObject.put("statusCode", 200);
                statusObject.put("message", "Attribute name updated successfully");
                return statusObject.toString();
            } else {
                statusObject.put("status", "failed");
                statusObject.put("statusCode", 500);
                statusObject.put("message", "Failed to update attribute name information");
                return statusObject.toString();
            }
        }

        statusObject.put("status", "failed");
        statusObject.put("statusCode", 1000);
        statusObject.put("message", "Important parameters are missing!");
        return statusObject.toString();
    }

    @Override
    public List getAttributeName() {
        return quizDAO.getAttributeName();
    }

    @Override
    public String setDeviceAttribute(String detailPayload) {
        JSONObject attributeObject = new JSONObject(detailPayload);
        if (attributeObject.has("attributes")) {
            JSONArray attributesArray = attributeObject.getJSONArray("attributes");
            if (attributesArray.length() > 0) {
                String status = "";
                for (Object a : attributesArray) {
                    JSONObject attributesObject = (JSONObject) a;

                    if (attributesObject.has("deviceUuid") && attributesObject.has("attributeUuid")) {
                        status = "attributeUuid";
                        MohDeviceDetails deviceId = quizDAO.setDeviceId(attributesObject.getString("deviceUuid"));
                        AttributeNames attributeId = quizDAO.setDeviceAttribute(attributesObject.getString("attributeUuid"));

                        if (deviceId != null && attributeId != null) {
                            status = quizDAO.setDeviceAttribute(deviceId.getDeviceId(), attributeId.getAttributeId());
                        }
                    }
                }
//                    return  status;
                JSONObject statusObject = new JSONObject();
                if (status.equalsIgnoreCase("success")) {
                    statusObject.put("status", "success");
                    statusObject.put("message", "attribute set successfully");
                    statusObject.put("statusCode", 200);
                } else if (status.equalsIgnoreCase("exist")) {
                    statusObject.put("status", "failed");
                    statusObject.put("message", "Record Exist");
                    statusObject.put("statusCode", 401);
                } else {
                    statusObject.put("status", "failed");
                    statusObject.put("message", "failed to set attribute");
                    statusObject.put("statusCode", 500);
                }
                return statusObject.toString();

            }
        }
        return null;
    }

    @Override
    public String addDeviceStatus(String status) {
        JSONObject deviceStatus = new JSONObject(status);
        if (deviceStatus.has("status_name")) {
            return quizDAO.addDeviceStatus(deviceStatus.getString("status_name"));
        } else {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public String updateDeviceStatus(String status) {
        JSONObject deviceStatus = new JSONObject(status);
        JSONObject statusObject = new JSONObject();
        if (deviceStatus.has("status_name") && deviceStatus.has("uuid")) {
            String res = quizDAO.updateDeviceStatus(deviceStatus.getString("status_name"), deviceStatus.getString("uuid"));
            if (res.equalsIgnoreCase("success")) {
                statusObject.put("status", "success");
                statusObject.put("statusCode", 200);
                statusObject.put("message", "Device status updated successfully");
                return statusObject.toString();
            } else {
                statusObject.put("status", "failed");
                statusObject.put("statusCode", 500);
                statusObject.put("message", "Failed to update device status");
                return statusObject.toString();
            }
        }

        statusObject.put("status", "failed");
        statusObject.put("statusCode", 1000);
        statusObject.put("message", "Important parameters are missing!");
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
        if (inventoryAnswers.has("attribute_name_id") && inventoryAnswers.has("attribute_value")) {
            return quizDAO.addDeviceInventoryAnswer(inventoryAnswers.getInt("attribute_name_id"), inventoryAnswers.getString("attribute_value"));
        } else {
            JSONObject statusObject = new JSONObject();
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Incorrect Object Provided");
            return statusObject.toString();
        }
    }

    @Override
    public List getDeviceInventoryAnswers() {
        return quizDAO.getDeviceInventoryAnswers();
    }

    @Override
    public String addInventory(String payload) {
        JSONObject inventory = new JSONObject(payload);
        JSONObject statusObject = new JSONObject();
        String message = validateInventoryPayload(payload);
        String messageStatus = "Failed";
        int code=500;
       if(message.equals("correct")){
            JSONObject DeviceInventory = inventory.getJSONObject("device_inventory");
            MohDeviceDetails deviceDetails = quizDAO.setDeviceId(DeviceInventory.getString("deviceUuid"));

            MohDeviceStatus deviceStatus = quizDAO.setDeviceStatusId(DeviceInventory.getString("deviceStatusUuid"));
            Integer device_id = deviceDetails.getDeviceId();
            Integer device_status_id = deviceStatus.getStatus_id();
            Integer current_location = Context.getLocationService().getDefaultLocation().getLocationId();
            Integer created_by = Context.getAuthenticatedUser().getUserId();
            String uuid_value = UUID.randomUUID().toString();
            String response = quizDAO.addInventory(device_id,device_status_id, current_location, created_by, uuid_value);
            if(response.equals("success")) {
                MohDeviceInventory inventoryDetails = quizDAO.setInventoryDetail(uuid_value);
                Integer inventory_id = inventoryDetails.getInventory_id();
                JSONArray attributesAnswerArray = inventory.getJSONArray("attribute_answer");
                for (Object a : attributesAnswerArray) {
                    JSONObject answersArray = (JSONObject) a;
                    AttributeNames attributeNames = quizDAO.setAttributeNames(answersArray.getString("attributeUuid"));
                    Integer attribute_name_id = attributeNames.getAttributeId();
                    String result = quizDAO.addAttributeValue(inventory_id, attribute_name_id, answersArray.getString("attributeValue"));
                }
            }
           message = "Success";
           messageStatus="Success";
           code = 200;
        }
        statusObject.put("status", messageStatus);
        statusObject.put("statusCode", code);
        statusObject.put("message",message);
        return statusObject.toString();
    }

    public String validateInventoryPayload(String PayLoad) {
        JSONObject inventory = new JSONObject(PayLoad);
        String message = "correct";
        if (!inventory.has("device_inventory") || !inventory.has("attribute_answer")) {
            message = "Incorrect Object Provided";
        } else {
            JSONArray attributesAnswerArray = inventory.getJSONArray("attribute_answer");
            JSONObject device_inventory = inventory.getJSONObject("device_inventory");
            if(!device_inventory.has("deviceUuid")
                    || !device_inventory.has("deviceStatusUuid")){
                message = "device_uuid, deviceStatusUUid must be provided";
            }

            if (attributesAnswerArray.length() > 0) {
                for (Object a : attributesAnswerArray) {
                    JSONObject answersArray = (JSONObject) a;
                    if (!answersArray.has("attributeUuid")) {
                        message = "Invalid Attribute UUID Key Provided, ";
                    }
                    if (!answersArray.has("attributeValue")) {
                        message = "Invalid Attribute Value key";
                    }

                }
            }else{
                message = "No Attribute Value Provided";
            }
        }
        return message;
    }

    @Override
    public List listInventory() {
        return quizDAO.getInventoryList();
    }

    @Override
    public List listInventoryAttributeAnswers(String inventoryUuid) {
        MohDeviceInventory inventoryDetails = quizDAO.setInventoryDetail(inventoryUuid);
        Integer inventory_id = inventoryDetails.getInventory_id();
        return quizDAO.getListInventoryAttributeAnswers(inventory_id);
    }

}
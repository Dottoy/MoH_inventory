/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.Quiz.api.db;
import org.hibernate.Query;
import org.openmrs.module.Quiz.api.QuizService;
import org.openmrs.module.Quiz.model.*;

import java.util.List;


/**
 *  Database methods for {@link QuizService}.
 */
public interface QuizDAO {




    //moh functions start here
     String addDeviceType(String type_name);

     String addDeviceMovementObject(String dev_uuid,String receiver_uuid,String sender_uuid,String location_uuid);

     String updateDeviceTypeObject(String typeName, String type_id);

    String saveUserNiNDetails(String nin,String firstName,String lastName,String dateOfBirth,String sex, String nationality);
     //moh functions end here

     String addItem(String ItemName, String Description);
     String addDevice(Integer device_type_id, String device_name);


     String updateDevice(String uuid, int deviceTypeId, String deviceName);

     List deviceList();
    String addAttributeNames(String name, String description, String format);

    String updateAttributeName(String name, String description, String format, String uuid);

    List getAttributeName();


    MohDeviceDetails setDeviceId(String deviceUuid);

    AttributeNames setDeviceAttribute(String attributeUuid);

    String setDeviceAttribute(int deviceId, int attributeId);


    String addDeviceStatus(String status_name);

    String updateDeviceStatus(String status_name, String uuid);

    List getDeviceStatus();

    List getDeviceTypeList();

    String addDeviceInventoryAnswer(int attribute_name_id, String attribute_value);

    List getDeviceInventoryAnswers();


    MohDeviceStatus setDeviceStatusId(String deviceStatusUuid);

    String addInventory(Integer device_id, Integer device_status_id, Integer current_location, Integer created_by, String uuid_value);

    MohDeviceInventory setInventoryDetail(String uuid);

    String addAttributeValue(Integer inventory_id, int attribute_name_id, String attributeValue);

    AttributeNames setAttributeNames(String attributeUuid);

    List getInventoryList();

    List getListInventoryAttributeAnswers(Integer inventory_id);

    String updateInventory(int device_id, int device_status_id, String uuid);

    String inventorAnswer(int attribute_name_id, String attribute_value, String uuid);

    String addMaintenance(String reported_issue, Integer inventory_id);

    String updateDeviceMaintenance(String reported_issue, Integer inventory_id, String uuid);

    String attendDeviceMaintenance(String fault_found, String action_taken, String uuid);

    List listDeviceMaintenance(String uuid, String type);

    List getMonthlyOpd(Integer report_id) ;

    DhisMohElement setMohElement(Integer element_id);
    DhisMohReportHeader setReportHeader(Integer report_id);

    String getCounter(String period, String generatedSql);
}
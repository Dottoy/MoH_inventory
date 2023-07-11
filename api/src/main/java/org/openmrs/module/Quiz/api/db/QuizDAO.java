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
import org.openmrs.module.Quiz.api.QuizService;

import java.util.List;


/**
 *  Database methods for {@link QuizService}.
 */
public interface QuizDAO {

     //moh functions start here
     String addDeviceType(String type_name);

     String updateDeviceTypeObject(String typeName, String type_id);


     //moh functions end here

     String addItem(String ItemName, String Description);
     String addDevice(Integer device_type_id, String device_name);


     String updateDevice(String uuid, int deviceTypeId, String deviceName);

     List deviceList();
    String addAttributeNames(String name, String description, String format);

    String updateAttributeName(String name, String description, String format, String uuid);

    List getAttributeName();


    String addDeviceStatus(String status_name);

    String updateDeviceStatus(String status_name, String uuid);

    List getDeviceStatus();


    String setDeviceAttribute(String deviceUuid, String attributeUuid);
}
/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.Quiz.api;

import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service exposes module's core functionality.
 */

@Transactional
public interface QuizService extends OpenmrsService {

    //function for moh test goes here
    String addDeviceTypeObject(String deviceTypeBody);

    String updateDeviceTypeObject(String deviceTypeBody);

    //function for moh test end here

    String addItemObject(String ItemPayload);

    String addAttributeNames(String names);

    String updateAttributeName(String name);

    List getAttributeName(String name);
}

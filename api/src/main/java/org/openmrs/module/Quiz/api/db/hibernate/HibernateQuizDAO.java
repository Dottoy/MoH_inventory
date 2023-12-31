/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.Quiz.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.Quiz.api.db.QuizDAO;
import org.openmrs.module.Quiz.model.*;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of  {@link org.openmrs.module.Quiz.api.db.QuizDAO}.
 */

public class HibernateQuizDAO implements QuizDAO {

    protected final Log log = LogFactory.getLog(this.getClass());

    private DbSessionFactory sessionFactory;

    /**
     * @param sessionFactory sessionFactory to be set
     */
    public void setSessionFactory(DbSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return DbSessionFactory instance
     */
    public DbSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * DAO specified methods
     *
     * @Author: Eric Mwailunga
     * October,2019
     */

    //...................Query building with results transformation...........................//
    private Query createSQLQueryAndTransform(String hql, Class transformationBean) {
        DbSession session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(transformationBean));
    }

    //...................Query building with no transformation.........................//
    private org.hibernate.SQLQuery createSQLQuery(String hql) {
        DbSession session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(hql);
    }


    //moh test query start here
    public String addDeviceType(String type_name) {
        //check if device type is already registered
        JSONObject statusObject = new JSONObject();
        String message = "failed";
        String hql = "insert into moh_device_type (device_type_name, created_by, created_at, uuid) " +
                " values ('" + type_name + "'," + Context.getAuthenticatedUser().getUserId() + ", now(),uuid())";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
            message = "Request completed successfully, Device type registered";
            statusObject.put("message", message);
        }
        statusObject.put("message", message);
        return statusObject.toString();
    }

    @Override
    public String addDeviceMovementObject(String dev_uuid, String receiver_uuid, String sender_uuid, String location_uuid) {
        String query1 = "select * from moh_device where uuid = '" + dev_uuid + "' LIMIT 1 ";
        User username = Context.getUserService().getUserByUuid(receiver_uuid);
        Integer id = username.getId();

        JSONObject jsonObject = new JSONObject();
        return jsonObject.put("useID", id).toString();

    }


    public String updateDeviceTypeObject(String typeName, String type_id) {

        String hql = "update moh_device_type set device_type_name='" + typeName + "' WHERE uuid='" + type_id + "' LIMIT 1";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
            return "success";
        } else {
            return "error";
        }

    }

    @Override
    public String saveUserNiNDetails(String nin, String firstName, String lastName, String dateOfBirth, String sex, String nationality) {
        //this line is for creating connection

        //check if nin number already registered
        String query = "select * from nida_table where nin_no ='" + nin + "' ";
        DbSession session = sessionFactory.getCurrentSession();
        Query query2 = session.createSQLQuery(query);
        List info = query2.list();
        if (info.size() == 0) {

            //new nin number register it!
            String hql = "insert into nida_table (nin_no,firstname,middlename,lastname,gender,dob,nationality,created_at,uuid)" +
                    " VALUES ('" + nin + "','" + firstName + "','" + firstName + "','" + lastName + "','" + sex + "','" + dateOfBirth + "','" + nationality + "', now(), uuid() )";
            int rowsAffected = createSQLQuery(hql).executeUpdate();

            if (rowsAffected >= 1) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Your request completed successfully, Detail saved.");
                return jsonObject.toString();
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "Something went wrong, fail to process your request!");
                return jsonObject.toString();
            }
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Your nida information already saved! Thank you.");
            return jsonObject.toString();
        }
    }


    //moh test query end here


    public String addItem(String ItemName, String Description) {
        String result = "failed";
        String hql = "insert into test_item (item_name, description, created_by, date_created, uuid) " +
                " values ('" + ItemName + "','" + Description + "','" + Context.getAuthenticatedUser().getUserId() + "', now(), uuid())";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
            result = "success";
        }
        return result;
    }


    public String addDevice(Integer device_type_id, String device_name) {
        JSONObject statusObject = new JSONObject();
        String hql = "insert into moh_device (device_type_id, device_name, created_by, created_at, uuid) " +
                " values (" + device_type_id + ",'" + device_name + "','" + Context.getAuthenticatedUser().getUserId() + "', current_date(), uuid())";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
            statusObject.put("status", "success");
            statusObject.put("statusCode", 200);
            statusObject.put("message", "Success");
        } else {
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Problem on insert Data");
        }
        return statusObject.toString();
    }

    @Override
    public String updateDevice(String uuid, int deviceTypeId, String deviceName) {
        JSONObject statusObject = new JSONObject();
        String hql = "UPDATE moh_device SET " +
                "device_type_id=" + deviceTypeId + ",  device_name='" + deviceName + "' WHERE uuid='" + uuid + "'";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
            statusObject.put("status", "success");
            statusObject.put("statusCode", 200);
            statusObject.put("message", "Update Success");
        } else {
            statusObject.put("status", "failed");
            statusObject.put("statusCode", 500);
            statusObject.put("message", "Problem on insert Data");
        }
        return statusObject.toString();
    }

    @Override
    public List deviceList() {
        String hql = "SELECT moh_device_type.device_type_id AS deviceTypeId, moh_device.device_id AS deviceId, " +
                "moh_device_type.device_type_name AS deviceTypeName, " +
                "device_name AS deviceName, " +
                "moh_device.created_by AS createdBy, " +
                "moh_device.created_at AS createdAt, " +
                "moh_device.uuid AS uuid FROM moh_device, moh_device_type " +
                "WHERE moh_device.device_type_id=moh_device_type.device_type_id";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(MohDeviceDetails.class));
        List infor = query.list();
        if (infor != null) {
            if (infor.size() > 0) {
                return infor;
            }
        }
        return null;
    }

    @Override
    public String addAttributeNames(String name, String description, String format) {
        JSONObject res = new JSONObject();
        String answ = "insert into moh_additional_attributes_names (name, description,format, created_by, created_at, uuid) " +
                " values ('" + name + "','" + description + "','" + format + "','" + Context.getAuthenticatedUser().getUserId() + "', current_date(), uuid())";
        int rowsAffected = createSQLQuery(answ).executeUpdate();
        if (rowsAffected >= 1) {
            res.put("status", "success");
            res.put("statusCode", 200);
            res.put("message", "Success");
        } else {
            res.put("status", "failed");
            res.put("statusCode", 500);
            res.put("message", "Problem on insert Data");
        }
        return res.toString();
    }

    @Override
    public String updateAttributeName(String name, String description, String format, String uuid) {
        //JSONObject res = new JSONObject();
        String res;
        String ans = "update moh_additional_attributes_names set name = '" + name + "', description = '" + description + "',format='" + format + "' where uuid = '" + uuid + "'";
        int rowsAffected = createSQLQuery(ans).executeUpdate();
        if (rowsAffected >= 1) {
            res = "success";
        } else {
            res = "failed";
        }
        return res;
    }

    @Override
    public List getAttributeName() {
        String hql;
        hql = "select name as 'Name', description as 'Description',  created_by as Creator, created_at as 'CreateDate',format as 'Format', uuid as 'uuid'  " +
                "from moh_additional_attributes_names";

        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AttributeNames.class));
        List infor = query.list();
        if (infor != null) {
            if (infor.size() > 0) {
                return infor;
            }
        }
        return null;
    }

    @Override
    public MohDeviceDetails setDeviceId(String deviceUuid) {
        String hql_device = "select device_id as 'deviceId' from moh_device where uuid='" + deviceUuid + "'";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(MohDeviceDetails.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (MohDeviceDetails) iterator.next();
            }
        }
        return null;

    }

    @Override
    public AttributeNames setDeviceAttribute(String attributeUuid) {
        String hql_device = "select attribute_id as 'attributeId' from moh_additional_attributes_names where uuid='" + attributeUuid + "'";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(AttributeNames.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (AttributeNames) iterator.next();
            }
        }
        return null;

    }

    //for create moh_device_status
    @Override
    public String addDeviceStatus(String status_name) {
        JSONObject res = new JSONObject();
        String answ = "insert into moh_device_status (status_name, created_by, created_at, uuid) " +
                " values ('" + status_name + "','" + Context.getAuthenticatedUser().getUserId() + "', current_date(), uuid())";
        int rowsAffected = createSQLQuery(answ).executeUpdate();
        if (rowsAffected >= 1) {
            res.put("status", "success");
            res.put("statusCode", 200);
            res.put("message", "Success");
        } else {
            res.put("status", "failed");
            res.put("statusCode", 500);
            res.put("message", "Problem on insert Data");
        }
        return res.toString();
    }

    @Override
    public String updateDeviceStatus(String status_name, String uuid) {
        String res;
        String ans = "update moh_device_status set status_name = '" + status_name + "' where uuid = '" + uuid + "'";
        int rowsAffected = createSQLQuery(ans).executeUpdate();
        if (rowsAffected >= 1) {
            res = "success";
        } else {
            res = "failed";
        }
        return res;
    }

    @Override
    public List getDeviceStatus() {
        String hql;
        hql = "select status_name as 'Name', created_by as Creator, created_at as 'CreateDate', uuid as 'uuid'  " +
                "from moh_device_status";

        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AttributeNames.class));
        List infor = query.list();
        if (infor != null) {
            if (infor.size() > 0) {
                return infor;
            }
        }
        return null;
    }

    @Override
    public String setDeviceAttribute(int deviceId, int attributeId) {
        String hql_device = "select attribute_id as 'attributeId' from moh_device_attribute where attribute_id='" + attributeId + "'";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(AttributeNames.class));
        List results = query.list();
        if (results.size() > 0) {
            return "exist";
        } else {
            String answ = "insert into moh_device_attribute (device_id, attribute_id) " +
                    " values (" + deviceId + "," + attributeId + ")";
            int rowsAffected = createSQLQuery(answ).executeUpdate();
            if (rowsAffected >= 1) {
                return "success";
            }
            return "failed";
        }
    }


    public List getDeviceTypeList() {
        String hql = "select * from moh_device_type order by device_type_name asc";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(MohDeviceType.class));
        List info = query.list();
        if (info != null) {
            if (info.size() > 0) {
                return info;
            }
        }
        return null;
    }

    //for moh_device_inventory_attribute_answer table
    @Override
    public String addDeviceInventoryAnswer(int attribute_name_id, String attribute_value) {
        JSONObject res = new JSONObject();
        String answ = "insert into moh_device_inventory_attribute_answer (attribute_name_id, attribute_value, created_by, created_at, uuid) " +
                " values (" + attribute_name_id + ",'" + attribute_value + "'," + Context.getAuthenticatedUser().getUserId() + ", current_date(), uuid())";
        int rowsAffected = createSQLQuery(answ).executeUpdate();
        if (rowsAffected >= 1) {
            res.put("status", "success");
            res.put("statusCode", 200);
            res.put("message", "Success added data");
        } else {
            res.put("status", "failed");
            res.put("statusCode", 500);
            res.put("message", "Problem on insert Data");
        }
        return res.toString();
    }

    @Override
    public List getDeviceInventoryAnswers() {
        String hql;
        hql = "select attribute_name_id as 'AttributeName',attribute_value as 'AttributeValue', created_by as Creator, created_at as 'CreateDate', uuid as 'uuid'  " +
                "from moh_device_inventory_attribute_answer";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(DeviceAnswers.class));
        List infor = query.list();
        if (infor != null) {
            if (infor.size() > 0) {
                return infor;
            }
        }
        return null;
    }

    @Override
    public MohDeviceStatus setDeviceStatusId(String deviceStatusUuid) {
        String hql_device = "select * from moh_device_status where uuid='" + deviceStatusUuid + "'";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(MohDeviceStatus.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (MohDeviceStatus) iterator.next();
            }
        }
        return null;
    }

    @Override
    public MohDeviceInventory setInventoryDetail(String uuid) {
        String hql_device = "select * from moh_device_inventory where uuid='" + uuid + "'";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(MohDeviceInventory.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (MohDeviceInventory) iterator.next();
            }
        }
        return null;
    }

    @Override
    public AttributeNames setAttributeNames(String attributeUuid) {
        String hql_device = "select attribute_id AS attributeId from moh_additional_attributes_names where uuid='" + attributeUuid + "'";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(AttributeNames.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (AttributeNames) iterator.next();
            }
        }
        return null;
    }


    @Override
    public String addAttributeValue(Integer inventory_id, int attribute_name_id, String attributeValue) {
        JSONObject res = new JSONObject();
        String query_string = "insert into moh_device_inventory_attribute_answer (inventory_id, attribute_name_id, attribute_value, created_by, created_at, uuid) " +
                " values (" + inventory_id + "," + attribute_name_id + ",'" + attributeValue + "', " + Context.getAuthenticatedUser().getUserId() + ", current_date(), uuid())";
        int rowsAffected = createSQLQuery(query_string).executeUpdate();
        if (rowsAffected > 0) {
            return "success";
        }
        return "fail";
    }

    @Override
    public String addInventory(Integer device_id, Integer device_status_id, Integer current_location, Integer created_by, String uuid_value) {
        JSONObject res = new JSONObject();
        String query_string = "insert into moh_device_inventory (device_id,device_status_id,current_location, created_by, created_at, uuid) " +
                " values (" + device_id + "," + device_status_id + ", " + current_location + "," + Context.getAuthenticatedUser().getUserId() + ", current_date(), '" + uuid_value + "')";
        int rowsAffected = createSQLQuery(query_string).executeUpdate();
        if (rowsAffected > 0) {
            return "success";
        }
        return "fail";
    }

    @Override
    public List getInventoryList() {
        String hql;
        hql = "select moh_device_type.device_type_name AS device_category_name,  " +
                " moh_device.device_name AS device_name, moh_device_status.status_name AS device_status, " +
                " CONCAT(IFNULL(person_name.given_name, ' '), ' ', IFNULL(person_name.middle_name, ' '), ' ', IFNULL(person_name.family_name, ' ')) AS created_by, " +
                " moh_device_inventory.uuid AS uuid, moh_device_inventory.created_at AS  created_at, location.name AS location_name " +
                " FROM moh_device_type, moh_device, moh_device_inventory, location, moh_device_status, users, person_name WHERE " +
                " moh_device.device_type_id=moh_device_type.device_type_id " +
                " AND moh_device.device_id=moh_device_inventory.device_id " +
                " AND location.location_id=moh_device_inventory.current_location " +
                " AND moh_device_inventory.device_status_id=moh_device_status.status_id " +
                " AND moh_device_inventory.created_by=users.user_id AND users.person_id=person_name.person_id";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(MohDeviceInventoryDetails.class));
        List infor = query.list();
        if (infor != null) {
            if (infor.size() > 0) {
                return infor;
            }
        }
        return null;
    }

    @Override
    public List getListInventoryAttributeAnswers(Integer inventory_id) {
        String hql;
        hql = "SELECT moh_additional_attributes_names.name AS attributeName, " +
                "moh_device_inventory_attribute_answer.attribute_value AS attributeValue, " +
                "moh_device_inventory_attribute_answer.uuid AS uuid, moh_device_inventory_attribute_answer.created_at AS createDate, " +
                "CONCAT(IFNULL(person_name.given_name, ' '), ' ', IFNULL(person_name.middle_name, ' '), ' ', IFNULL(person_name.family_name, ' ')) AS creator " +
                "FROM  moh_device_inventory_attribute_answer, moh_additional_attributes_names, users, person_name WHERE " +
                "moh_device_inventory_attribute_answer.created_by=users.user_id AND users.person_id=person_name.person_id AND " +
                "moh_device_inventory_attribute_answer.attribute_name_id=moh_additional_attributes_names.attribute_id AND " +
                "moh_device_inventory_attribute_answer.inventory_id=" + inventory_id;
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(DeviceAnswers.class));
        List infor = query.list();
        if (infor != null) {
            if (infor.size() > 0) {
                return infor;
            }
        }
        return null;
    }

    @Override
    public String updateInventory(int device_id, int device_status_id, String uuid) {
        String res;
        String ans = "update moh_device_inventory set device_id = " + device_id + ",device_status_id = " + device_status_id + " where uuid = '" + uuid + "'";
        int rowsAffected = createSQLQuery(ans).executeUpdate();
        if (rowsAffected >= 1) {
            res = "success";
        } else {
            res = "failed";
        }
        return res;
    }

    @Override
    public String inventorAnswer(int attribute_name_id, String attribute_value, String uuid) {
        String res;
        String answer = "update moh_device_inventory_attribute_answer set attribute_name_id = " + attribute_name_id + ",attribute_value = '" + attribute_value + "' where uuid = '" + uuid + "'";
        int rowsAffected = createSQLQuery(answer).executeUpdate();
        if (rowsAffected >= 1) {
            res = "success";
        } else {
            res = "failed";
        }
        return res;
    }

    @Override
    public List getMonthlyOpd(Integer report_id) {
        String hql = "SELECT dhis_moh_report_element_combo.element_id AS elementId, " +
                "dhis_moh_report_element_combo.report_header_id AS headerId, categoryOptionComboUUID AS categoryOptionCombo " +
                "FROM dhis_moh_report_element_combo, dhis_moh_report_header WHERE " +
                "dhis_moh_report_element_combo.report_header_id=dhis_moh_report_header.id AND report_id='" + report_id + "'";
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(OpdReport.class));
        List infor = query.list();
        if (infor != null) {
            if (infor.size() > 0) {
                return infor;
            }
        }
        return null;
    }

    @Override
    public DhisMohElement setMohElement(Integer element_id) {
        String hql_device = "SELECT * FROM dhis_moh_element where element_id=" + element_id;
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(DhisMohElement.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (DhisMohElement) iterator.next();
            }
        }
        return null;
    }
    @Override
    public String addMaintenance(String reported_issue, Integer inventory_id) {
        JSONObject res = new JSONObject();
        String query_string = "insert into moh_device_maintenance (inventory_id,reported_issue,reported_by, date_reported,created_at,uuid) " +
                " values (" + inventory_id + ",'"+ reported_issue +"', " + Context.getAuthenticatedUser().getUserId() + ", current_date(),current_date(), uuid())";
        int rowsAffected = createSQLQuery(query_string).executeUpdate();
        if (rowsAffected > 0) {
            return "success";
        }
        return "fail";
    }

    @Override
    public String updateDeviceMaintenance(String reported_issue, Integer inventory_id, String uuid) {
        String res;
        String ans = "update moh_device_maintenance set inventory_id = "+inventory_id+",reported_issue = '"+ reported_issue +"' where uuid = '"+uuid+"'";
        int rowsAffected = createSQLQuery(ans).executeUpdate();
        if (rowsAffected >= 1)
        {
            res= "success";
        }
        else
        {
            res="failed";
        }
        return res;
    }

    @Override
    public String attendDeviceMaintenance(String fault_found, String action_taken, String uuid) {
        String res;
        String ans = "update moh_device_maintenance set fault_found = '"+ fault_found +"',action_taken ='"+ action_taken +"', service_by=" + Context.getAuthenticatedUser().getUserId() + ", service_date= current_date() where uuid = '"+uuid+"'";
        int rowsAffected = createSQLQuery(ans).executeUpdate();
        if (rowsAffected >= 1)
        {
            res= "success";
        }
        else
        {
            res="failed";
        }
        return res;
    }

    @Override
    public List listDeviceMaintenance(String uuid, String type) {
        String hql;
        if (type.equalsIgnoreCase("all")){
            hql ="select moh_device_maintenance.reported_issue AS reported_issue, "+
                    " moh_device_maintenance.fault_found AS fault_found, "+
                    " moh_device_maintenance.action_taken AS action_taken," +
                    " moh_additional_attributes_names.name AS attribute_name,"+
                    " moh_device_maintenance.uuid AS uuid, " +
                    " moh_device_maintenance.reported_by AS reported_by, " +
                    " CONCAT(IFNULL(person_name.given_name, ' '), ' ', IFNULL(person_name.middle_name, ' '), ' ', IFNULL(person_name.family_name, ' ')) AS creator " +
                    " FROM moh_device_maintenance, users, person_name, moh_device_inventory, moh_device_inventory_attribute_answer, moh_additional_attributes_names  WHERE " +
                    " moh_device_inventory.inventory_id=moh_device_maintenance.inventory_id " +
                    " AND moh_device_maintenance.reported_by=users.user_id " +
                    " AND moh_device_inventory_attribute_answer.inventory_id=moh_device_inventory.inventory_id " +
                    " AND moh_device_inventory_attribute_answer.attribute_name_id=moh_additional_attributes_names.attribute_id " +
                    " AND users.person_id=person_name.person_id";
        }
        else {
            hql="select moh_device_maintenance.reported_issue AS reported_issue,"+
                    " moh_device_maintenance.fault_found AS fault_found, "+
                    " moh_device_maintenance.action_taken AS action_taken," +
                    " moh_additional_attributes_names.name AS attribute_name,"+
                    " moh_device_maintenance.uuid AS uuid, " +
                    " moh_device_maintenance.reported_by AS reported_by, " +
                    " CONCAT(IFNULL(person_name.given_name, ' '), ' ', IFNULL(person_name.middle_name, ' '), ' ', IFNULL(person_name.family_name, ' ')) AS creator " +
                    " FROM moh_device_maintenance, users, person_name, moh_device_inventory, moh_device_inventory_attribute_answer, moh_additional_attributes_names  WHERE " +
                    " moh_device_inventory.inventory_id=moh_device_maintenance.inventory_id " +
                    " AND moh_device_maintenance.reported_by=users.user_id "+
                    " AND moh_device_inventory_attribute_answer.inventory_id=moh_device_inventory.inventory_id " +
                    " AND moh_device_inventory_attribute_answer.attribute_name_id=moh_additional_attributes_names.attribute_id " +
                    " AND users.person_id=person_name.person_id  AND moh_device_maintenance.uuid = '"+uuid+"' LIMIT 1";
        }
        DbSession session=sessionFactory.getCurrentSession();
        Query query=session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(deviceMaintenance.class));
        List infor=query.list();
        if(infor!=null)
        {
            if(infor.size()>0)
            {
                return infor;
            }
        }
        return null;
    }


    public DhisMohReportHeader setReportHeader(Integer header_id) {
        String hql_device = "SELECT * FROM dhis_moh_report_header where id=" + header_id;
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(DhisMohReportHeader.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (DhisMohReportHeader) iterator.next();
            }
        }
        return null;
    }

    @Override
    public BigInteger getCounter(String period, String generatedSql) {
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(generatedSql);
        query.setParameter("tarehe", period);
        query.setParameter("tarehemwezi", period.substring(0,7));
        return (BigInteger) query.uniqueResult();
    }

    @Override
    public DhisMohReports setDhisReports(Integer reportId) {
        String hql_device = "SELECT * FROM dhis_moh_reports where report_id=" + reportId;
        DbSession session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(hql_device).setResultTransformer(Transformers.aliasToBean(DhisMohReports.class));
        List results = query.list();
        if (results != null) {
            if (results.size() > 0) {
                Iterator iterator = results.iterator();
                return (DhisMohReports) iterator.next();
            }
        }
        return null;
    }
}
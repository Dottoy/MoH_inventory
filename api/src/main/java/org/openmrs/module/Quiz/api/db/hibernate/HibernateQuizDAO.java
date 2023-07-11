/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.Quiz.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.Quiz.api.db.QuizDAO;
import org.openmrs.module.Quiz.model.AttributeNames;
import org.openmrs.module.Quiz.model.MohDeviceDetails;
import org.openmrs.module.Quiz.model.MohDeviceType;
import org.openmrs.module.Quiz.model.PersonalDetails;


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
    public String addDeviceType(String type_name){
        //check if device type is already registered
        JSONObject statusObject = new JSONObject();
        String message = "failed";
        String hql = "insert into moh_device_type (device_type_name, created_by, created_at, uuid) " +
                " values ('" + type_name + "'," + Context.getAuthenticatedUser().getUserId() + ", now(),uuid())";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
            message = "Request completed successfully, Device type registered";
            statusObject.put("message",message);
        }
        statusObject.put("message",message);
        return statusObject.toString();
    }



    public String updateDeviceTypeObject(String typeName, String type_id) {

        String hql = "update moh_device_type set device_type_name='"+typeName+"' WHERE uuid='"+type_id+"' LIMIT 1";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
          return "success";
        }else{
           return "error";
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
            statusObject.put("status","success");
            statusObject.put("statusCode",200);
            statusObject.put("message","Success");
        }else{
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Problem on insert Data");
        }
        return statusObject.toString();
    }

    @Override
    public String updateDevice(String uuid, int deviceTypeId, String deviceName) {
        JSONObject statusObject = new JSONObject();
        String hql = "UPDATE moh_device SET " +
                "device_type_id=" + deviceTypeId + ",  device_name='"+ deviceName +"' WHERE uuid='"+ uuid +"'";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
        if (rowsAffected >= 1) {
            statusObject.put("status","success");
            statusObject.put("statusCode",200);
            statusObject.put("message","Update Success");
        }else{
            statusObject.put("status","failed");
            statusObject.put("statusCode",500);
            statusObject.put("message","Problem on insert Data");
        }
        return statusObject.toString();
    }

    @Override
    public List deviceList() {
        String hql ="SELECT moh_device_type.device_type_name AS deviceTypeName, " +
                "device_name AS deviceName, " +
                "moh_device.created_by AS createdBy, " +
                "moh_device.created_at AS createdAt, " +
                "moh_device.uuid AS uuid FROM moh_device, moh_device_type " +
                "WHERE moh_device.device_type_id=moh_device_type.device_type_id" ;
        DbSession session=sessionFactory.getCurrentSession();
        Query query=session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(MohDeviceDetails.class));
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

    @Override
    public String addAttributeNames(String name, String description, String format) {
        JSONObject res = new JSONObject();
        String answ = "insert into moh_additional_attributes_names (name, description,format, created_by, created_at, uuid) " +
                " values ('" + name + "','" + description + "','"+ format +"','" + Context.getAuthenticatedUser().getUserId() + "', current_date(), uuid())";
        int rowsAffected = createSQLQuery(answ).executeUpdate();
        if (rowsAffected >= 1) {
            res.put("status","success");
            res.put("statusCode",200);
            res.put("message","Success");
        }else{
            res.put("status","failed");
            res.put("statusCode",500);
            res.put("message","Problem on insert Data");
        }
        return res.toString();
    }

    @Override
    public String updateAttributeName(String name, String description, String format, String uuid) {
        //JSONObject res = new JSONObject();
        String res;
        String ans = "update moh_additional_attributes_names set name = '"+name+"', description = '"+description+"',format='"+format+"' where uuid = '"+uuid+"'";
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
    public List getAttributeName() {
        String hql;
            hql ="select name as 'Name', description as 'Description',  created_by as Creator, created_at as 'CreateDate',format as 'Format', uuid as 'uuid'  " +
                    "from moh_additional_attributes_names";

        DbSession session=sessionFactory.getCurrentSession();
        Query query=session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AttributeNames.class));
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

    @Override
    public String setDeviceAttribute(String deviceUuid, String attributeUuid) {
        String hql_device = "select device_id as deviceId from moh_device where uuid='" + deviceUuid + "' LIMIT 1";
        String hql_attribute = "select attribute_id as attributeId from moh_additional_attributes_names where uuid='" + attributeUuid + "' LIMIT 1";

        DbSession dbSession = getSessionFactory().getCurrentSession();
        List results_device = dbSession.createSQLQuery(hql_device).addScalar("deviceId", IntegerType.INSTANCE).setResultTransformer(Transformers.aliasToBean(MohDeviceDetails.class)).list();
//        List results_attribute = dbSession.createSQLQuery(hql_device).addScalar("attributeId", IntegerType.INSTANCE).setResultTransformer(Transformers.aliasToBean(MohDeviceDetails.class)).list();
//        log.info(results_device);
//        log.info(results_attribute);

//
//        if (results.size() > 0) {
//            Iterator iterator = results.iterator();
//            ReceiptAuthorizationRaw receiptAuthorizationRaw = (ReceiptAuthorizationRaw) iterator.next();
//            if (receiptAuthorizationRaw != null)
//                if (receiptAuthorizationRaw.getPrintNo() > 0)
//                    return (receiptAuthorizationRaw.getPrintNo()) + 1;
//        }
//        return 1;


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
            res.put("status","success");
            res.put("statusCode",200);
            res.put("message","Success");
        }else{
            res.put("status","failed");
            res.put("statusCode",500);
            res.put("message","Problem on insert Data");
        }
        return res.toString();
    }

    @Override
    public String updateDeviceStatus(String status_name, String uuid) {
        String res;
        String ans = "update moh_device_status set status_name = '"+status_name+"' where uuid = '"+uuid+"'";
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
    public List getDeviceStatus() {
        String hql;
        hql ="select status_name as 'Name', created_by as Creator, created_at as 'CreateDate', uuid as 'uuid'  " +
                "from moh_device_status";

        DbSession session=sessionFactory.getCurrentSession();
        Query query=session.createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(AttributeNames.class));
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

}
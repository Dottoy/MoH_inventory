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
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.Quiz.api.db.QuizDAO;
import org.openmrs.module.Quiz.model.PersonalDetails;

import java.time.LocalDateTime;
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
        JSONObject res = new JSONObject();
        String hql = "update moh_additional_attributes_names set name = '"+name+"', description = '"+description+"' where uuid = '"+uuid+"'";
        int rowsAffected = createSQLQuery(hql).executeUpdate();
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

}
package org.openmrs.module.Quiz.api.db;

import org.hibernate.FlushMode;

public class DbSessionUtil {
    private static DbSessionDAO dbSessionDAO;

    public static DbSessionDAO getDbSessionDAO() {
        return dbSessionDAO;
    }

    public void setDbSessionDAO(DbSessionDAO dbSessionDAO) {
        DbSessionUtil.dbSessionDAO = dbSessionDAO;
    }

    public static void setDAO(DbSessionDAO dbSessionDAO) {
        DbSessionUtil.dbSessionDAO =dbSessionDAO;
    }

    public static FlushMode getCurrentFlushMode(){
        return dbSessionDAO.getCurrentFlushMode();
    }

    public static void setManualFlushMode(){
        dbSessionDAO.setManualFlushMode();
    }

    public static void setFlushMode(FlushMode flushMode){
        dbSessionDAO.setFlushMode(flushMode);
    }
}

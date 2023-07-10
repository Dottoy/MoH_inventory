package org.openmrs.module.Quiz.api.db.hibernate;

import org.hibernate.FlushMode;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.Quiz.api.db.DbSessionDAO;

public class DbSessionDAOImpl implements DbSessionDAO {

    private DbSessionFactory sessionFactory;

    @Override
    public FlushMode getCurrentFlushMode() {
        return sessionFactory.getCurrentSession().getFlushMode();
    }

    @Override
    public void setManualFlushMode() {
        sessionFactory.getCurrentSession().setFlushMode(FlushMode.MANUAL);
    }

    @Override
    public void setFlushMode(FlushMode flushMode) {
        sessionFactory.getCurrentSession().setFlushMode(flushMode);
    }

    public void setSessionFactory(DbSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}

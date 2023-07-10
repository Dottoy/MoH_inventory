package org.openmrs.module.Quiz.api.db;

import org.hibernate.FlushMode;

public interface DbSessionDAO {
    FlushMode getCurrentFlushMode();
    void setManualFlushMode();
    void setFlushMode(FlushMode flushMode);
}

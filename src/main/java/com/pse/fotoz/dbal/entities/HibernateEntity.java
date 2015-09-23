package com.pse.fotoz.dbal.entities;

import com.pse.fotoz.dbal.HibernateException;
import com.pse.fotoz.dbal.HibernateSession;
import org.hibernate.Session;

/**
 *
 * @author Robert
 */
public interface HibernateEntity {
    
    public default void persist() throws HibernateException {
        final Session session = HibernateSession.getInstance().newSession();
        
        session.beginTransaction();
        session.save(this);
        session.getTransaction().commit();
        session.close();
    }
    
    public default void delete() throws HibernateException {
        final Session session = HibernateSession.getInstance().newSession();
        
        session.beginTransaction();
        session.delete(this);
        session.getTransaction().commit();
        session.close();
    }
}

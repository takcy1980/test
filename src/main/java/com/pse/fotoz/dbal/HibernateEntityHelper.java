package com.pse.fotoz.dbal;

import com.pse.fotoz.dbal.entities.HibernateEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Robert
 */
public class HibernateEntityHelper {
    
    public static <T extends HibernateEntity> List<T> all(Class<T> c) {        
        try {
            Session session = HibernateSession.getInstance().newSession();
            return session.createCriteria(c).list();
        } catch (HibernateException ex) {
            Logger.getLogger(HibernateEntityHelper.class.getName()).
                    log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
        
    }
    
    public static <T extends HibernateEntity> Optional<T> byId(Class<T> c, 
            int id) {
        try {
            Session session = HibernateSession.getInstance().newSession();
            return Optional.of(session.get(c, id));
        } catch (HibernateException ex) {
            Logger.getLogger(HibernateEntityHelper.class.getName()).
                    log(Level.SEVERE, null, ex);
            return Optional.empty();
        }
    }
    
    public static <T extends HibernateEntity> List<T> find(Class<T> c, 
            String fieldName, Object fieldValue) {
        try {
            Criteria criteria = HibernateSession.getInstance().newSession().
                    createCriteria(c);
            return criteria.add(Restrictions.eq(fieldName, fieldValue)).list();            
        } catch (HibernateException ex) {
            Logger.getLogger(HibernateEntityHelper.class.getName()).
                    log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }
}

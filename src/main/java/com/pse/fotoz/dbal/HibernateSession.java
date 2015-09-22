package com.pse.fotoz.dbal;

import java.io.File;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


/**
 *
 * @author Robert
 */
public class HibernateSession {
    private final static File CONFIG_FILE = 
            new File("src/main/resources/hibernate/hibernate.cfg.xml");
    
    private static Optional<HibernateSession> instance = Optional.empty();
    
    private final StandardServiceRegistry registry;
    private final SessionFactory sessionFactory;
    
    private HibernateSession() throws HibernateException {
        this.registry = new StandardServiceRegistryBuilder().
			configure(CONFIG_FILE).
			build();
        
        try {
            this.sessionFactory = new MetadataSources(registry).
                buildMetadata().
                buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            
            throw e;
        }
    }
    
    public static HibernateSession getInstance() throws HibernateException {
        if (!instance.isPresent()) {
            instance = Optional.of(new HibernateSession());
        }
        
        return instance.get();
    }
    
    public Session newSession() {
        return sessionFactory.openSession();
    }
}

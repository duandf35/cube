package cube.services;

import cube.models.Score;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author wenyu
 * @since 12/23/15
 */
public class HibernateSessionFactory {
    private static final Logger LOG = LogManager.getLogger(HibernateSessionFactory.class);
    private static final SessionFactory FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            LOG.info("Initializing Hibernate session factory.");

            // Create SessionFactory from 'hibernate.cfg.xml' by default
            final StandardServiceRegistry reg = new StandardServiceRegistryBuilder().configure().build();
            return new MetadataSources(reg).buildMetadata().buildSessionFactory();
        } catch (Throwable t) {
            LOG.error("Error happened during Hibernate session factory initializing.");
            throw new ExceptionInInitializerError(t);
        }
    }

    public static SessionFactory getSessionFactory() {
        return FACTORY;
    }

    public static void shutdown() {
        FACTORY.close();
    }
}

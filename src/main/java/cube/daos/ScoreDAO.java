package cube.daos;

import com.google.common.base.Preconditions;
import cube.models.Score;
import cube.services.HibernateSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author wenyu
 * @since 12/23/15
 */
public final class ScoreDAO implements IScoreDAO {
    private static final Logger LOG = LogManager.getLogger(ScoreDAO.class);
    private static final SessionFactory FACTORY = HibernateSessionFactory.getSessionFactory();

    public void save(final Score score) {
        Preconditions.checkNotNull(score, "Score must not be null.");

        LOG.info("Saving {} to database.", score.toString());

        Session session = FACTORY.openSession();

        session.beginTransaction();
        session.save(score);
        session.getTransaction().commit();

        session.close();
    }

    public void getAll() {

    }
}

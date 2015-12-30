package cube.daos;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import cube.models.Score;
import cube.services.HibernateSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

import java.util.List;

/**
 * @author wenyu
 * @since 12/23/15
 */
public final class ScoreDAO implements IScoreDAO {
    private static final Logger LOG = LogManager.getLogger(ScoreDAO.class);
    private static final SessionFactory FACTORY = HibernateSessionFactory.getSessionFactory();

    @Override
    public void save(final Score score) {
        Preconditions.checkNotNull(score, "Score must not be null.");

        LOG.info("Saving {} to database.", score.toString());

        Session session = FACTORY.openSession();

        session.beginTransaction();
        session.save(score);
        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<Score> getAll() {
        Session session = FACTORY.openSession();

        try {
            session.beginTransaction();

            List<Score> scores = (List<Score>) session.createQuery("FROM Score").list();

            session.getTransaction().commit();
            session.close();

            return scores;
        } catch (ClassCastException e) {
            LOG.error("Error happened during casting query results.");
            return ImmutableList.of();
        }
    }

    @Override
    public Score getBest() {
        Session session = FACTORY.openSession();

        try {
            session.beginTransaction();

            DetachedCriteria maxValue = DetachedCriteria.forClass(Score.class)
                                                        .setProjection(Projections.max("value"));

            List<Score> bestScores = session.createCriteria(Score.class)
                                            .add(Property.forName("value").eq(maxValue))
                                            .list();

            session.getTransaction().commit();
            session.close();

            return bestScores.isEmpty() ? null : bestScores.get(0);
        } catch (ClassCastException e) {
            LOG.error("Error happened during casting query results.");
            return null;
        }
    }
}

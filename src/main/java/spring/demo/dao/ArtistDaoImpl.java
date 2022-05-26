package spring.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.demo.entity.Album;
import spring.demo.entity.Artist;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
public class ArtistDaoImpl implements ArtistDao{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private EntityManager entityManager;
    @Override
    @Transactional
    public void addArtist(Artist artist) {
        Session currentSession = entityManager.unwrap(Session.class);

        currentSession.saveOrUpdate(artist);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query =
                currentSession.createQuery("delete from Album where artist_id=:artistId");
        query.setParameter("artistId", id);
        query.executeUpdate();
        Query theQuery =
                currentSession.createQuery("delete from Artist where id=:artistId");
        theQuery.setParameter("artistId", id);

        theQuery.executeUpdate();
    }

    @Override
    public Artist getArtist(int id) {
        Session currentSession = sessionFactory.openSession();
        Artist theArtist;
        try {
            theArtist = currentSession.get(Artist.class, id);
        }finally{
            if (currentSession!=null) currentSession.close();
        }

        return theArtist;
    }



    @Override
    public List<Artist> findAll() {
        Session currentSession = sessionFactory.openSession();
        List<Artist> artists;
        try {
            Query<Artist> theQuery =
                    currentSession.createQuery("from Artist", Artist.class);
            artists = theQuery.getResultList();
        }finally{
            if (currentSession!=null) currentSession.close();
        }

        return artists;
    }

    @Override
    public List<Artist> searchBy(String theName) {
        List<Artist> results = null;

        if (theName != null && (theName.trim().length() > 0)) {
            Session currentSession = entityManager.unwrap(Session.class);
            theName=theName.stripTrailing();

            Query<Artist> query =currentSession.createQuery("from Artist where UPPER(artist) LIKE UPPER(:ArtistName)", Artist.class);
            query.setParameter("ArtistName", "%" + theName + "%");
            query.list();
            results = query.getResultList();
        }
        else {
            results = findAll();
        }

        return results;
    }
}

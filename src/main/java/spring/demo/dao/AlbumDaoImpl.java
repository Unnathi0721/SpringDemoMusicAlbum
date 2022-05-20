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

public class AlbumDaoImpl implements AlbumDao{
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private EntityManager entityManager;
    @Override
   @Transactional
    public void addAlbum(Album album) {
//      Session currentSession = sessionFactory.openSession();
        Session currentSession = entityManager.unwrap(Session.class);
        System.out.println(album.toString());
        //Artist theArtist=new Artist();
        currentSession.saveOrUpdate(album);
    }

    @Override
    @Transactional
    public void delete(int id) {
//        Session currentSession = sessionFactory.openSession();
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery =
                currentSession.createQuery("delete from Album where id=:AlbumId");
        theQuery.setParameter("AlbumId", id);

        theQuery.executeUpdate();

    }

    @Override
    @Transactional
    public Album getAlbum(int id) {
        Session currentSession = sessionFactory.openSession();
        Album theAlbum;
        try {
            //currentSession = sessionFactory.openSession();
            theAlbum = currentSession.get(Album.class, id);
        } finally {
            if (currentSession!=null) currentSession.close();
        }

//        Album theAlbum = currentSession.get(Album.class, id);

        return theAlbum;

    }

    @Override
    public Artist getArtist(int id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Album theAlbum = currentSession.get(Album.class, id);
        Artist theArtist=theAlbum.getArtist();
        return theArtist;
    }

    @Override
    @Transactional
    public List<Album> findAll() {
        Session currentSession = sessionFactory.openSession();
        List<Album> albums;
        try {
            Query<Album> theQuery =
                    currentSession.createQuery("from Album", Album.class);
            albums = theQuery.getResultList();
        }finally{
            if (currentSession!=null) currentSession.close();
        }
        return albums;
    }

    @Override
    @Transactional
    public List<Album> searchBy(String theName) {
        List<Album> results = null;

        if (theName != null && (theName.trim().length() > 0)) {
            Session currentSession = entityManager.unwrap(Session.class);
            theName=theName.stripTrailing();
            System.out.println(theName);
            Query<Album> query =currentSession.createQuery("from Album where UPPER(title) LIKE UPPER(:AlbumName)", Album.class);
            query.setParameter("AlbumName", "%" + theName + "%");
            query.list();
            results = query.getResultList();
            System.out.println(results);
//            Query<Album> theQuery =
//                    currentSession.createQuery("from Album where title=:AlbumName", Album.class);
//            theQuery.setParameter("AlbumName", theName);
////            theQuery.executeUpdate();
//            theQuery.list();
//            results = theQuery.getResultList();
        }
        else {
            results = findAll();
        }
        System.out.println(results);
        return results;
    }
}

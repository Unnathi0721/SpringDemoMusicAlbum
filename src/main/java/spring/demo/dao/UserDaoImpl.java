package spring.demo.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.demo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
//    @Qualifier("userTransactionManager")
    private EntityManager entityManager;

    @Override
//    @Transactional
//    @Transactional(value = "transactionManager",noRollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public User findByUserName(String theUserName) {
        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // now retrieve/read from database using username
        Query<User> theQuery = currentSession.createQuery("from User where username=:uName", User.class);
        theQuery.setParameter("uName", theUserName);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    @Transactional(noRollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW, rollbackFor = {Throwable.class})
    public void save(User theUser) {
        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // create the user ... finally LOL
//        currentSession.flush();
        currentSession.saveOrUpdate(theUser);
//        currentSession.flush();
    }

}
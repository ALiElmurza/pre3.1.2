package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.models.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class DaoImp {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getListUsers() {
        Query query = entityManager.createQuery("from User ");
        return query.getResultList();
    }

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void delete(Long id) {
        entityManager.remove(getUserByID(id));
    }

    public User getUserByID(Long id) {
        return entityManager.find(User.class, id);
    }


}

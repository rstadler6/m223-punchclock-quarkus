package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    private EntityManager entityManager;

    public UserService() {
    }

    @Transactional 
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User updateUser(User user) {
        entityManager.merge(user);
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        var query = entityManager.createQuery("FROM User");
        return query.getResultList();
    }

    public User find(String username) {
        return entityManager.find(User.class, username);
    }
}

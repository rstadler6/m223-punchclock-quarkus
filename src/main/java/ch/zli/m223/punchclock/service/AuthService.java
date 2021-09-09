package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.User;
import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class AuthService {
    @Inject
    private EntityManager entityManager;

    public String generateToken(User user) {
        var groups = "User";
        if (user.isAdmin())
            groups += ", Admin";

        return Jwt.issuer("http://localhost:8080")
                        .upn(user.getUsername())
                        .groups(groups)
                        .sign();
    }

    @Transactional
    public void createUser(User user) {
        if (entityManager.contains(user))
            throw new BadRequestException();
        entityManager.persist(user);
    }

    public boolean userExists(String username) {
        return entityManager.find(User.class, username) != null;
    }

    public boolean validateCredentials(User loginUser) {
        var user = entityManager.find(User.class, loginUser.getUsername());

        return user != null && user.getPassword().equals(loginUser.getPassword());
    }
}

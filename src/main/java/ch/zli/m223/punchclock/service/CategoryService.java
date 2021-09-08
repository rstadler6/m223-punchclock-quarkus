package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.List;

@ApplicationScoped
public class CategoryService {
    @Inject
    private EntityManager entityManager;

    public CategoryService() {
    }

    @Transactional 
    public Category createCategory(Category category) {
        if (entityManager.contains(category))
            throw new BadRequestException("Category already exists");

        entityManager.persist(category);
        return category;
    }

    @Transactional
    public boolean tryDeleteCategory(String name) {
        var category = find(name);
        System.out.println(category.getName());
        if (category == null || category.getName().isBlank())
            return false;

        entityManager.remove(category);
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        var query = entityManager.createQuery("FROM Category");
        return query.getResultList();
    }

    public Category find(String name) {
        return entityManager.find(Category.class, name);
    }   // returns null??
}

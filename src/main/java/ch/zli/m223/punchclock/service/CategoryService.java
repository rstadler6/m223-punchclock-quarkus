package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CategoryService {
    @Inject
    private EntityManager entityManager;

    public CategoryService() {
    }

    @Transactional 
    public Category createCategory(Category category) {
        entityManager.persist(category);
        return category;
    }

    @Transactional
    public Category updateCategory(Category category) {
        entityManager.merge(category);
        return category;
    }

    @Transactional
    public void deleteCategory(Category category) {
        entityManager.remove(category);
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAll() {
        var query = entityManager.createQuery("FROM Category");
        return query.getResultList();
    }

    public Category find(Long id) {
        return entityManager.find(Category.class, id);
    }

    public Category find(String name) {
        return entityManager.find(Category.class, name);
    }
}

package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Entry;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    public EntryService() {
    }

    @Transactional 
    public Entry createEntry(Entry entry) {
        entityManager.persist(entry);
        return entry;
    }

    @Transactional
    public Entry updateEntry(Entry entry) {
        entityManager.merge(entry);
        return entry;
    }

    @Transactional
    public boolean tryDeleteEntry(Long id) {
        var entity = find(id);
        if (entity == null)
            return false;

        entityManager.remove(entity);
        return true;
    }

    @SuppressWarnings("unchecked")
    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry");
        return query.getResultList();
    }

    public Entry find(Long id) {
        return entityManager.find(Entry.class, id);
    }
}

package ch.zli.m223.punchclock.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Comment;
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
    public void deleteEntry(Entry entry) {
        entityManager.remove(entry);
    }

    @SuppressWarnings("unchecked")
    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry");
        return query.getResultList();
    }

    public Entry find(Long id) {
        return entityManager.find(Entry.class, id);
    }

    public void comment(Entry entry, Comment comment) {
        var comments = entry.getComments();
        comments.add(comment);
        entry.setComments(comments);

        entityManager.merge(entry);
    }
}

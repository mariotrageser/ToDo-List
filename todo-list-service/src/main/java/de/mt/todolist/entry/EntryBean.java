package de.mt.todolist.entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class EntryBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ToDoEntry> getEntries() {
        try {
            return entityManager.createNamedQuery("ToDoEntry.all", ToDoEntry.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

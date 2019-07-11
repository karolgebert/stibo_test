package pl.karolgebert.stibo.dao;

public class RepositoryIdGenerator {
    private long id;

    synchronized long generateId() {
        return ++id;
    }
}

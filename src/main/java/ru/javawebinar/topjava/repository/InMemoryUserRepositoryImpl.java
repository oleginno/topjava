package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();

    {
        save(new User(1, "Admin", "admin@admin.net", "1",
                1700, true, new HashSet<>()));
        save(new User(2, "Regular", "user@regular.net", "2",
                1800, true, new HashSet<>()));
        save(new User(3, "Guest", "user@guest.net", "3",
                1900, true, new HashSet<>()));
    }

    @Override
    public boolean delete(int id) {
        repository.remove(id);
        log.info("deleting user: {} ...", id);
        return true;
    }

    @Override
    public User save(User user) {
        repository.put(user.getId(), user);
        log.info("saving user: {}, {} ...", user.getId(), user.getName());
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get user: {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("get all users");
        return new ArrayList<>(repository.values());
    }

    @Override
    public User getByEmail(String email) {
        log.info("get user by email: {}", email);
        return getAll().stream().
                filter(user -> email.equals(user.getEmail())).
                findFirst().
                orElse(null);
    }
}

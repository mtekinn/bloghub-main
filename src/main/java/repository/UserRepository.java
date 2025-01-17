package repository;

import model.User;
import model.enums.StatusType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private List<User> userList = new ArrayList<>();
    private static UserRepository instance;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void save(User user) {
        userList.add(user);
    }

    public Optional<User> findByEmail(String email) {
        return userList.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public List<User> findAll() {
        return userList.stream()
                .filter(user -> user.getStatusType().equals(StatusType.APPROVED))
                .toList();
    }
}

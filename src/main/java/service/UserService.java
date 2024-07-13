package service;

import model.User;
import model.enums.StatusType;
import repository.UserRepository;
import utils.PasswordUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String email, String password) {
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(email, hashedPassword);
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void changeStatus(String email, StatusType statusType) {
        User user = getUserByEmail(email);
        user.setStatusType(statusType);
    }

    public void changeStatus(List<String> emails, StatusType statusType) {
        emails.forEach(email -> changeStatus(email, statusType));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Map<String, User> getAllUsersMap() {
        return userRepository.findAll().stream()
                .collect(Collectors.toMap(User::getEmail, user -> user));
    }
}

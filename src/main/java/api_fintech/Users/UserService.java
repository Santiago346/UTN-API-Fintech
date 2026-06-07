package api_fintech.Users;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User AddUser(User user) {
        return userRepository.save(user);
    }
}

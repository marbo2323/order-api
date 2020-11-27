package com.company.user;

import com.company.order.Order;
import com.company.role.UserRole;
import com.company.role.UserRoleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<Order> getUserOrders(Long userId){
        User user = getUserById(userId);
        return user.getOrders();
    }

    public User addUser(String rawBody){
        JSONObject jsonObject = new JSONObject(rawBody);
        User user = new User(jsonObject.getString("username"),
                jsonObject.getString("password"),
                jsonObject.getString("firstName"),
                jsonObject.getString("lastName"),
                jsonObject.getString("email"));
        userRepository.save(user);
        UserRole userRole = new UserRole(User.ROLE_USER);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
        Long userId = user.getId();
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}

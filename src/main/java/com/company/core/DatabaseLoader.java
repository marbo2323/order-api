package com.company.core;

import com.company.product.Product;
import com.company.product.ProductRepository;
import com.company.role.UserRole;
import com.company.role.UserRoleRepository;
import com.company.user.User;
import com.company.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository, ProductRepository productRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User[] users = new User[]{
                new User("admin", "admin123!", "builtin", "admin", "admin@company.com")
        };

        Product[] products = new Product[]{
                new Product("Mini", "Mini TV package"),
                new Product("Standard", "Standard TV package"),
                new Product("Premium", "Premium TV package")
        };

        for (User user : users) {
            addUser(user);
        }

        for (Product product : products) {
            addProduct(product);
        }
    }

    private void addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            userRepository.save(user);
            if (user.getUsername().equals("admin")) {
                addUserRole(user, User.ROLE_ADMIN);
            } else {
                addUserRole(user, User.ROLE_USER);
            }
        }
    }

    private void addProduct(Product product) {
        if (productRepository.findByName(product.getName()) == null) {
            productRepository.save(product);
        }
    }

    private void addUserRole(User user, String roleName) {
        UserRole userRole = new UserRole(roleName);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }

}

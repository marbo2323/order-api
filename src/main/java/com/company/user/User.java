package com.company.user;

import com.company.core.BaseEntity;
import com.company.order.Order;
import com.company.role.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
public class User extends BaseEntity {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    @NotNull
    private String username;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email
    private String email;

    @JsonIgnore
    @NotNull
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;


    public User(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setPassword(password);
    }

    protected User() {
        super();
    }


    public List<Order> getOrders() {
        return orders;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    @JsonIgnore
    public String[] getRoleNames(){
        String[] result = new String[userRoles.size()];
        int i = 0;
        for (UserRole role: userRoles){
            result[i] = role.getRoleName();
            i++;
        }
        return result;
    }

    public void addUserRole(UserRole userRole) {
        userRole.setUser(this);
        userRoles.add(userRole);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

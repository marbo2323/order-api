package com.company.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DetailsService implements UserDetailsService {
    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username + " was not found");
        }
        String[] userRoles = user.getRoleNames();
        System.out.printf("Roles for user %s are: %s%n", username, Arrays.toString(userRoles));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(userRoles)
        );
    }
}

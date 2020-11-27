package com.company.core;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new User("basic_user", "password",
                AuthorityUtils.createAuthorityList("ROLE_USER"));

        User adminUser = new User("admin_user", "password",
                AuthorityUtils.createAuthorityList("ROLE_USER"));


        return new InMemoryUserDetailsManager(Arrays.asList(
                basicUser, adminUser
        ));
    }
}

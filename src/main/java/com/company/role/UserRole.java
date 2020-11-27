package com.company.role;

import com.company.core.BaseEntity;
import com.company.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user_role", schema = "public")
public class UserRole extends BaseEntity {
    @NotNull
    @Column(name="role_name")
    private String roleName;

    @JsonIgnore
    @ManyToOne
    private User user;

    protected UserRole() {
        super();
    }

    public UserRole(@NotNull String roleName) {
        this.roleName = roleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}

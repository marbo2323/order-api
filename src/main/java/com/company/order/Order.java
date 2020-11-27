package com.company.order;

import com.company.core.BaseEntity;
import com.company.product.Product;
import com.company.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="user_order", schema="public")
public class Order extends BaseEntity {

    public static final String STATUS_NEW="NEW";
    public static final String STATUS_COMPLETED="COMPLETED";

    @NotNull
    @Column(name="status", nullable = false)
    private String status = STATUS_NEW;

    @Column(name = "created_timestamp", nullable = false, updatable = false, insertable = false)
    private Date created = new Date();

    @JsonIgnore
    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    protected Order() {
        super();
    }

    public Order(@NotNull String status, Date created) {
        this.status = status;
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

package com.company.product;

import com.company.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product", schema = "public")
public class Product extends BaseEntity {
    @NotNull
    @Size(min=2, max = 16)
    @Column(name = "name")
    private String name;
    @Size(max = 500)
    @Column(name = "description")
    private String description;

    protected Product() {
        super();
    }

    public Product(@NotNull String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

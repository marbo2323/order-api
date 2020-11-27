package com.company.core;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "serial")
    private Long id;

    protected BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }
}

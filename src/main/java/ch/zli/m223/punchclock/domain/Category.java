package ch.zli.m223.punchclock.domain;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
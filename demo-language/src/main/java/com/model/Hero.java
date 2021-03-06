package com.model;

import com.annotations.hibernate_annotation.MyColumn;
import com.annotations.hibernate_annotation.MyEntity;
import com.annotations.hibernate_annotation.MyGeneratedValue;
import com.annotations.hibernate_annotation.MyId;
import com.annotations.hibernate_annotation.MyTable;

@MyEntity
@MyTable(name = "hero_table")
public class Hero {
    private int id;
    private String name;
    private int damage;
    private int armor;

    @MyId
    @MyGeneratedValue(strategy = "identity")
    @MyColumn("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @MyColumn("name_table")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MyColumn("damage_table")
    public int getDamage() {
        return damage;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }

    @MyColumn("armor_table")
    public int getArmor() {
        return armor;
    }


    public void setArmor(int armor) {
        this.armor = armor;
    }
}

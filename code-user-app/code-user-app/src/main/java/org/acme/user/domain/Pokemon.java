package org.acme.user.domain;

import java.util.List;

public class Pokemon {
    private int id;
    private String name;
    private String type;
    private int level;
    private List<String> abilities;

    public Pokemon() {}

    public Pokemon(int id, String name, String type, int level, List<String> abilities) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.abilities = abilities;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                ", abilities=" + abilities +
                '}';
    }
} 
package com.roguelike;

import com.roguelike.equipment.ElementType;

public abstract class Entity {

    private double health;
    private double damage;
    private final ElementType element;
    private int level;

    private boolean dead = false;
    private boolean stunned = false;

    public Entity(double health, double damage, ElementType element) {
        this.health = health;
        this.element = element;
        this.damage = damage;
    }

    public void attack(Entity entity) {
        if (dead || stunned) {
            return;
        }
        entity.takeDamage(damage);
    }

    public void takeDamage(double damageTaken) {
        health -= damageTaken;
        if (health <= 0) {
            dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public void setIsDead(boolean isDead) {
        dead = isDead;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public ElementType getElement() {
        return element;
    }

    public void setStunned() {
        stunned = true;
    }

    public void setUnStunned() {
        stunned = false;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

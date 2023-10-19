package com.rougelike.enemies;

import com.rougelike.Player;
import com.rougelike.equipment.ElementType;

public abstract class Entity {

    private double health;
    private double damage;
    private final ElementType element;
    private boolean dead = false;
    private boolean stunned = false;


    public Entity(double health, double damage, ElementType element){
        this.health = health;
        this.element = element;
        this.damage = damage;
    }

    public void attack (Player player){
        if(dead || stunned){
            return;
        }
        player.takeDamage(damage);
    }

    public void takeDamage(double damageTaken){
        health -= damageTaken;
        if(health <= 0){
            dead = true;
        }
    }

    public boolean isDead(){
        return dead;
    }

    public double getHealth(){
        return health;
    }

    public double getDamage(){
        return damage;
    }
    public ElementType getElement(){
        return element;
    }

    public void getStunned(){
        stunned = true;
    }

    public void getUnStunned(){
        stunned = false;
    }

    public boolean isStunned(){
        return stunned;
    }

    public void setHealth(double health){
        this.health = health;
    }

    public void setDamage(double damage){
        this.damage = damage;
    }

}

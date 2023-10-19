package com.rougelike.enemies;

import com.rougelike.Player;
import com.rougelike.equipment.ElementType;
import com.rougelike.magic.MagicElementType;

public abstract class Entity {

    private double health;
    private double damage;
    private ElementType element;
    private boolean isDead = false;


    public Entity(double health, double damage, ElementType element){
        this.health = health;
        this.element = element;
        this.damage = damage;
    }

    public void attack (Player player){
        if(isDead){
            return;
        }
        player.takeDamage(damage);
    }

    public void takeDamage(double damageTaken){
        health -= damageTaken;
        if(health <= 0){
            isDead = true;
        }
    }

    public boolean isDead(){
        return isDead;
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


}

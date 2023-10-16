package com.rougelike.magic;

public class Heal extends MagicInvoker{

    public Heal() {
        super("Heal");
    }
  
    public String getName() {
        return super.name;
    }

    void heal() {
        System.out.println("Heal");
    }
    
}

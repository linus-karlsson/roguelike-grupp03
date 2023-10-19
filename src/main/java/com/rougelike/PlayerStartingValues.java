package com.rougelike;

import com.rougelike.races.Race;
import com.rougelike.roles.Role;

public class PlayerStartingValues {

    private String name;
    private Race race;
    private Role role;
    private Point position;


    public PlayerStartingValues(String name, Race race, Role role, Point position) {
        this.name = name;
        this.race = race;
        this.role = role;
        this.position = position;

    }

  public String getName(){
        return name;
  }

  public Race getRace(){
        return race;
  }

  public Role getRole(){
        return role;
  }

  public Point getPosition(){
        return position;
  }
}

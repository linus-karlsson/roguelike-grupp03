package com.rougelike.roles;

import static org.junit.jupiter.api.Assertions.*;

import com.rougelike.races.Elf;
import org.junit.jupiter.api.Test;


public class RoleTest {

    @Test
    public void TestRoleInstanceCreation(){
        Knight knight = new Knight();
        assertEquals(1.5, knight.getStrengthMultiplier());
    }


}

package com.roguelike.roles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void testRoleInstanceCreation() {
        Knight knight = new Knight();
        assertEquals(1.5, knight.getStrengthMultiplier());
    }

}

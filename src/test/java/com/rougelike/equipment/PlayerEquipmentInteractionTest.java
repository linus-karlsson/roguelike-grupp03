package com.rougelike.equipment;

import org.junit.jupiter.api.*;

import com.rougelike.Player;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerEquipmentInteractionTest {

    @Test
    public void canAddWeaponToInventory() {
        Stick stick = new Stick();
        Player player = new Player("Sven");

        player.addWeaponToInventory(stick);

        assertTrue(player.getWeaponInventory().contains(stick));
    }

    // @Test
    // public void onlyWeaponsCanBeAddedToWeaponInventory() {
    // HeavyArmor heavyArmor = new HeavyArmor();
    // Player player = new Player("Sven");

    // player.addWeaponToInventory(heavyArmor);

    // assertFalse(player.getWeaponInventory().contains(heavyArmor));

    // }

    @Test
    public void canNotHaveMoreThanFiveWeaponsInWeaponInventory() {
        Stick stick = new Stick();
        Player player = new Player("Sven");

        for (int i = 0; i < 6; i++) {
            player.addWeaponToInventory(stick);
        }

        assertTrue(player.getWeaponInventory().size() < 6);
    }

    @Test
    public void canRemoveWeaponFromWeaponInventory() {
        Stick stick = new Stick();
        Player player = new Player("Sven");

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(stick);
        player.removeWeaponFromInventory(stick);

        assertTrue(player.getWeaponInventory().size() == 1 && player.getWeaponInventory().contains(stick));
    }

    @Test
    public void ifAtMaxCapacity_RemoveEarliestAddedWeapon() {

        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        Wand wand = new Wand();
        Sword sword = new Sword();
        Player player = new Player("Sven");

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertFalse(player.getWeaponInventory().contains(stick));
    }

    @Test
    public void removingWeaponDueToCapacityReturnsWeaponsCost() {

        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        Wand wand = new Wand();
        Sword sword = new Sword();
        Player player = new Player("Sven");

        int actual = player.getWallet() + stick.getPrice();

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertEquals(actual, player.getWallet());
    }
}

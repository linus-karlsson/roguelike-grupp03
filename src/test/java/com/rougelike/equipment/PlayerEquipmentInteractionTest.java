package com.rougelike.equipment;

import org.junit.jupiter.api.*;

import com.rougelike.*;
import com.rougelike.races.Dwarf;
import com.rougelike.roles.Knight;
import com.rougelike.roles.Mage;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerEquipmentInteractionTest {

    @Test
    public void canAddWeaponToInventory() {
        Stick stick = new Stick();
        Player player = new Player("Sven", new Point());

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
        Player player = new Player("Sven", new Point());

        for (int i = 0; i < 6; i++) {
            player.addWeaponToInventory(stick);
        }

        assertTrue(player.getWeaponInventory().size() < 6);
    }

    @Test
    public void canRemoveWeaponFromWeaponInventory() {
        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        Player player = new Player("Sven", new Point());

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.removeWeaponFromInventory(stick);

        assertTrue(player.getWeaponInventory().size() == 1 && player.getWeaponInventory().contains(dagger));
    }

    @Test
    public void correctValueStartingMoney() {
        Player player = new Player("Sven", new Point());

        int actual = 100;

        assertEquals(actual, player.getWallet());
    }

    @Test
    public void inventoryAtCapacityMinusOne_AddsWeapon() {
        // Måste skrivas om ifall max inventory capacity ändras

        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Player player = new Player("Sven", new Point());

        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);

        Sword sword = new Sword();
        player.addWeaponToInventory(sword);

        assertTrue(player.getWeaponInventory().contains(sword)
                && player.getWeaponInventory().size() == player.getMaxInventoryCapacity());

    }

    @Test
    public void ifAtMaxCapacity_RemoveEarliestAddedWeapon() {

        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Point());

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
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Point());

        int actual = player.getWallet() + stick.getPrice();

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertEquals(actual, player.getWallet());
    }

    @Test
    public void weaponInventoryContainsNoDuplicates() {

        Stick stick = new Stick();
        Player player = new Player("Sven", new Point());

        int actual = 1;

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(stick);

        assertEquals(actual, player.getWeaponInventory().size());
    }

    // Fungerar inte längre då metoden kräver en klass.
    // @Test
    // public void canEquipInOffhand() {
    // Dagger dagger = new Dagger();
    // Player player = new Player("Sven");

    // player.addWeaponToInventory(dagger);
    // player.equipOffhand(dagger);
    // assertTrue(player.getEquippedOffhand() == dagger);
    // }

    @Test
    public void doesNotEquipOffhandIfNotInWeaponInventory() {
        Dagger dagger = new Dagger();
        Player player = new Player("Sven", new Point());

        player.equipOffhand(dagger);
        assertFalse(player.getEquippedOffhand() == dagger);
    }

    @Test
    public void doesNotEquipOffhandIfNotInArmorInventory() {
        Shield shield = new Shield();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point());

        Equipment actual = null;
        player.equipOffhand(shield);
        assertEquals(actual, player.getEquippedOffhand());
    }

    @Test
    public void doesNotEquipOffhandIfWrongRole() {
        Dagger dagger = new Dagger();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point());

        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);
        assertFalse(player.getEquippedOffhand() == dagger);
    }

    @Test
    public void equipsOffhandIfCorrectRoleAndInInventory() {
        Shield shield = new Shield();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point());

        Equipment actual = shield;
        player.addArmorToInventory(shield);
        player.equipOffhand(shield);

        assertEquals(actual, player.getEquippedOffhand());
    }

    @Test
    public void equipsWeaponIfCorrectRoleAndInInventory() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        Weapon actual = fireSword;
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(actual, player.getEquippedWeapon());
    }

    @Test
    public void doesNotEquipWeaponIfWrongRole() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point());
        Weapon actual = player.getEquippedWeapon();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(actual, player.getEquippedWeapon());
    }

    @Test
    public void equippingWeaponIncreasesPlayerStats() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        FireSword fireSword = new FireSword();
        double nothingEquippedStats = player.getStrength();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        double weaponEquippedStats = player.getStrength();
        assertTrue(weaponEquippedStats > nothingEquippedStats);
    }

    // Skapa fler tester för att kolla resten av kombinationerna.
    // Skapa tester för att sätta på sig vapen.
}

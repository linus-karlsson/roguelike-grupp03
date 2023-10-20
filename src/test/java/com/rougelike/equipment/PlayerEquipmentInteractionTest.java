package com.rougelike.equipment;

import org.junit.jupiter.api.*;

import com.rougelike.*;
import com.rougelike.races.Dwarf;
import com.rougelike.races.Elf;
import com.rougelike.races.Human;
import com.rougelike.roles.Knight;
import com.rougelike.roles.Mage;
import com.rougelike.roles.Thief;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerEquipmentInteractionTest {

    @Test
    void canAddWeaponToInventory() {
        Stick stick = new Stick();
        Player player = new Player("Sven", new Point());

        player.addWeaponToInventory(stick);

        assertTrue(player.getWeaponInventory().contains(stick));
    }

    @Test
    void canAddArmorToInventory() {
        LightArmor lightArmor = new LightArmor();
        Player player = new Player("Sven", new Point());
        player.addArmorToInventory(lightArmor);
        assertTrue(player.getArmorInventory().contains(lightArmor));
    }

    // @Test
    // void onlyWeaponsCanBeAddedToWeaponInventory() {
    // HeavyArmor heavyArmor = new HeavyArmor();
    // Player player = new Player("Sven");

    // player.addWeaponToInventory(heavyArmor);

    // assertFalse(player.getWeaponInventory().contains(heavyArmor));

    // }

    @Test
    void canNotHaveMoreThanFiveWeaponsInWeaponInventory() {
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

        assertTrue(player.getWeaponInventory().size() < 6);
    }

    @Test
    void canNotHaveMoreThanFiveArmorsInArmorInventory() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Point());
        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);
        assertTrue(player.getArmorInventory().size() < 6);
    }

    @Test
    void canRemoveWeaponFromWeaponInventory() {
        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        Player player = new Player("Sven", new Point());

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.removeWeaponFromInventory(stick);

        assertTrue(player.getWeaponInventory().size() == 1 && player.getWeaponInventory().contains(dagger));
    }

    @Test
    void correctValueStartingMoney() {
        Player player = new Player("Sven", new Point());

        int expected = 100;

        assertEquals(expected, player.getWallet());
    }

    @Test
    void inventoryAtCapacityMinusOne_AddsWeapon() {
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
    void inventoryAtCapacityMinusOne_AddsArmor() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();

        Player player = new Player("Sven", new Point());
        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);

        assertTrue(player.getArmorInventory().contains(heavyArmor)
                && player.getArmorInventory().size() == player.getMaxInventoryCapacity());

    }

    @Test
    void ifAtMaxCapacity_RemoveEarliestAddedWeapon() {

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
    void ifAtMaxCapacity_RemoveEarliestAddedArmor() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Point());

        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertFalse(player.getArmorInventory().contains(tome));
    }

    @Test
    void removingWeaponDueToCapacityReturnsWeaponsCost() {

        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Point());

        int expected = player.getWallet() + stick.getPrice();

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertEquals(expected, player.getWallet());
    }

    @Test
    void removingArmorDueToCapacityReturnsArmorCost() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Point());

        int expected = player.getWallet() + tome.getPrice();

        player.addArmorToInventory(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertEquals(expected, player.getWallet());
    }

    @Test
    void weaponInventoryContainsNoDuplicates() {

        Stick stick = new Stick();
        Player player = new Player("Sven", new Point());

        int expected = 1;

        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(stick);

        assertEquals(expected, player.getWeaponInventory().size());
    }

    @Test
    void cantRemoveEquippedArmor() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        player.removeArmorFromInventory(heavyArmor);
        assertTrue(player.getArmorInventory().contains(heavyArmor));
    }

    @Test
    void cantRemoveEquippedWeapon() {
        Stick stick = new Stick();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);
        player.removeWeaponFromInventory(stick);
        assertTrue(player.getWeaponInventory().contains(stick));
    }

    @Test
    void addingNullToWeaponInventoryThrowsException() {
        Player player = new Player("Sven", new Point());
        assertThrows(IllegalArgumentException.class, () -> {
            player.addWeaponToInventory(null);
        });
    }

    @Test
    void addingNullToArmorInventoryThrowsException() {
        Player player = new Player("Sven", new Point());
        assertThrows(IllegalArgumentException.class, () -> {
            player.addArmorToInventory(null);
        });
    }

    @Test
    void removingNullFromWeaponInventoryThrowsException() {
        Player player = new Player("Sven", new Point());
        assertThrows(IllegalArgumentException.class, () -> {
            player.removeWeaponFromInventory(null);
        });
    }

    @Test
    void removingNullFromArmorInventoryThrowsException() {
        Player player = new Player("Sven", new Point());
        assertThrows(IllegalArgumentException.class, () -> {
            player.removeArmorFromInventory(null);
        });
    }

    @Test
    void armorInventoryContainsNoDuplicates() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Point());
        int expected = 1;
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(heavyArmor);

        assertEquals(expected, player.getArmorInventory().size());
    }

    @Test
    void cantRemoveEquippedWeaponWhenRemovingDueToCapacityAndRemovesNextIndexedWeaponInstead() {
        Stick stick = new Stick();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());

        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);

        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertTrue(player.getEquippedWeapon() == stick && player.getWeaponInventory().contains(stick)
                && !(player.getWeaponInventory().contains(dagger)));
    }

    @Test
    void removesThirdItemWhenTheFirstAndSecondWeaponAreUneligibleForRemoval() {
        WaterDagger waterDagger = new WaterDagger();
        Dagger dagger = new Dagger();
        FireSword fireSword = new FireSword();
        Torch torch = new Torch();
        AirWand wand = new AirWand();
        Sword sword = new Sword();
        Player player = new Player("Sven", new Dwarf(), new Thief(), new Point());

        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);

        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertTrue(player.getEquippedWeapon() == waterDagger && player.getEquippedOffhand() == dagger
                && player.getWeaponInventory().contains(waterDagger) && player.getWeaponInventory().contains(dagger)
                && !(player.getWeaponInventory().contains(fireSword)) && player.getWeaponInventory().contains(sword));
    }

    @Test
    void cantRemoveEquippedArmorWhenRemovingDueToCapacityAndRemovesNextIndexedArmorInstead() {
        Tome tome = new Tome();
        Shield shield = new Shield();
        LightArmor lightArmor = new LightArmor();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertTrue(player.getEquippedOffhand() == tome && player.getArmorInventory().contains(tome)
                && !(player.getArmorInventory().contains(shield)));
    }

    @Test
    void removesThirdItemWhenTheFirstAndSecondArmorAreUneligibleForRemoval() {
        Tome tome = new Tome();
        LightArmor lightArmor = new LightArmor();
        Shield shield = new Shield();
        MediumArmor mediumArmor = new MediumArmor();
        HeavyArmor heavyArmor = new HeavyArmor();
        SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertTrue(player.getEquippedArmor() == lightArmor && player.getEquippedOffhand() == tome
                && player.getArmorInventory().contains(lightArmor) && player.getArmorInventory().contains(tome)
                && !(player.getArmorInventory().contains(shield))
                && player.getArmorInventory().contains(superiorHeavyArmor));
    }

    // Fungerar inte längre då metoden kräver en klass.
    // @Test
    // void canEquipInOffhand() {
    // Dagger dagger = new Dagger();
    // Player player = new Player("Sven");

    // player.addWeaponToInventory(dagger);
    // player.equipOffhand(dagger);
    // assertTrue(player.getEquippedOffhand() == dagger);
    // }

    @Test
    void doesNotEquipOffhandIfNotInWeaponInventory() {
        Dagger dagger = new Dagger();
        Player player = new Player("Sven", new Point());

        player.equipOffhand(dagger);
        assertFalse(player.getEquippedOffhand() == dagger);
    }

    @Test
    void doesNotEquipOffhandIfNotInArmorInventory() {
        Shield shield = new Shield();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point());

        Equipment expected = null;
        player.equipOffhand(shield);
        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfWrongRole() {
        Dagger dagger = new Dagger();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point());

        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);
        assertFalse(player.getEquippedOffhand() == dagger);
    }

    @Test
    void equipsOffhandIfCorrectRoleAndInInventory() {
        Shield shield = new Shield();
        Dwarf dwarf = new Dwarf();
        Knight knight = new Knight();
        Player player = new Player("Sven", dwarf, knight, new Point());

        Equipment expected = shield;
        player.addArmorToInventory(shield);
        player.equipOffhand(shield);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void equipsSwordIfCorrectRoleAndInInventory() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        Weapon expected = fireSword;
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void equipsWandIfCorrectRoleAndInInventory() {
        SuperiorAirWand superiorAirWand = new SuperiorAirWand();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point());
        Weapon expected = superiorAirWand;
        player.addWeaponToInventory(superiorAirWand);
        player.equipWeapon(superiorAirWand);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void equipsDaggerIfCorrectRoleAndInInvetory() {
        WaterDagger waterDagger = new WaterDagger();
        Player player = new Player("Sven", new Elf(), new Thief(), new Point());
        Weapon expected = waterDagger;
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfWrongRole() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point());
        Weapon expected = player.getEquippedWeapon();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfNotInWeaponInventory() {
        FireSword fireSword = new FireSword();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        player.equipWeapon(fireSword);
        assertFalse(player.getEquippedWeapon() == fireSword);
    }

    @Test
    void equippingWeaponIncreasesPlayerStats() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        FireSword fireSword = new FireSword();
        double nothingEquippedStats = player.getStrength();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        double weaponEquippedStats = player.getStrength();
        assertTrue(weaponEquippedStats > nothingEquippedStats);
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Dwarf_Knight() {
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point());
        FireSword fireSword = new FireSword();
        double expected = (player.getStrength() + (fireSword.getStrength() * knight.getStrengthMultiplier()));
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getStrength());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Human_Mage() {
        Mage mage = new Mage();
        Player player = new Player("Bengt", new Human(), mage, new Point());
        SuperiorAirWand superiorAirWand = new SuperiorAirWand();
        double expected = (player.getIntelligence()
                + (superiorAirWand.getIntelligence() * mage.getIntelligenceMultiplier()));
        player.addWeaponToInventory(superiorAirWand);
        player.equipWeapon(superiorAirWand);
        assertEquals(expected, player.getIntelligence());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Elf_Thief() {
        Thief thief = new Thief();
        Player player = new Player("Tor", new Elf(), thief, new Point());
        WaterDagger waterDagger = new WaterDagger();
        double expected = (player.getDexterity() + (waterDagger.getDexterity() * thief.getDexterityMultiplier()));
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        assertEquals(expected, player.getDexterity());
    }

    @Test
    void canUnequipWeapon() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        Torch torch = new Torch();
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        player.unequipWeapon();
        assertTrue(player.getEquippedWeapon() != torch);
    }

    @Test
    void unequippingWeaponReducesStats() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        Torch torch = new Torch();
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        double statsBeforeUnequipping = player.getStrength();
        player.unequipWeapon();
        assertTrue(player.getStrength() < statsBeforeUnequipping);
    }

    @Test
    void unequippingWeaponReducesStatsByCorrectAmount() {
        Knight knight = new Knight();
        Dwarf dwarf = new Dwarf();
        Player player = new Player("Sven", dwarf, knight, new Point());
        Torch torch = new Torch();
        double expected = dwarf.getStartingStrength() * knight.getStrengthMultiplier();
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        player.unequipWeapon();
        assertEquals(expected, player.getStrength());
    }

    @Test
    void canNotEquipArmorIfNotInInventory() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        player.equipArmor(heavyArmor);
        assertFalse(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Knight() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        assertTrue(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Mage() {
        LightArmor lightArmor = new LightArmor();
        Player player = new Player("Sven", new Elf(), new Mage(), new Point());
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        assertTrue(player.getEquippedArmor() == lightArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Thief() {
        MediumArmor mediumArmor = new MediumArmor();
        Player player = new Player("Sven", new Human(), new Thief(), new Point());
        player.addArmorToInventory(mediumArmor);
        player.equipArmor(mediumArmor);
        assertTrue(player.getEquippedArmor() == mediumArmor);
    }

    @Test
    void canNotEquipArmorIfWrongRole() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Player player = new Player("Sven", new Dwarf(), new Mage(), new Point());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        assertFalse(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canUnequipArmor() {
        Player player = new Player("Sven", new Dwarf(), new Knight(), new Point());
        HeavyArmor heavyArmor = new HeavyArmor();
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        player.unequipArmor();
        assertTrue(player.getEquippedArmor() == null);
    }

    @Test
    void equippingArmorIncreasesStatsByCorrectAmount() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point());
        double[] expected = { player.getStrength() + (heavyArmor.getStrength() * knight.getStrengthMultiplier()),
                player.getDexterity() + (heavyArmor.getDexterity() * knight.getDexterityMultiplier()),
                player.getIntelligence() + (heavyArmor.getIntelligence() * knight.getIntelligenceMultiplier()),
                player.getHealth() + heavyArmor.getHealth(), player.getMana() + heavyArmor.getMana(),
                player.getArmorValue() + heavyArmor.getArmorValue() };
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        double[] equippedStats = { player.getStrength(), player.getDexterity(), player.getIntelligence(),
                player.getHealth(), player.getMana(), player.getArmorValue() };
        assertArrayEquals(expected, equippedStats);
    }

    @Test
    void unequippingArmorDecreasesStatsByCorrectAmount() {
        HeavyArmor heavyArmor = new HeavyArmor();
        Knight knight = new Knight();
        Player player = new Player("Sven", new Dwarf(), knight, new Point());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        double[] expected = { player.getStrength() - (heavyArmor.getStrength() * knight.getStrengthMultiplier()),
                player.getDexterity() - (heavyArmor.getDexterity() * knight.getDexterityMultiplier()),
                player.getIntelligence() - (heavyArmor.getIntelligence() * knight.getIntelligenceMultiplier()),
                player.getHealth() - heavyArmor.getHealth(), player.getMana() - heavyArmor.getMana(),
                player.getArmorValue() - heavyArmor.getArmorValue() };
        player.unequipArmor();
        double[] actual = { player.getStrength(), player.getDexterity(), player.getIntelligence(),
                player.getHealth(), player.getMana(), player.getArmorValue() };
        assertArrayEquals(expected, actual);
    }

}

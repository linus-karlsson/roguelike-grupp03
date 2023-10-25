package com.roguelike.equipment;

import org.junit.jupiter.api.*;

import com.roguelike.*;
import com.roguelike.races.Dwarf;
import com.roguelike.races.Elf;
import com.roguelike.races.Human;
import com.roguelike.roles.Knight;
import com.roguelike.roles.Mage;
import com.roguelike.roles.Thief;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class PlayerEquipmentInteractionTest {
    private Stick stick = new Stick();
    private Dagger dagger = new Dagger();
    private FireSword fireSword = new FireSword();
    private Torch torch = new Torch();
    private AirWand wand = new AirWand();
    private Sword sword = new Sword();
    private LightArmor lightArmor = new LightArmor();
    private Tome tome = new Tome();
    private Shield shield = new Shield();
    private MediumArmor mediumArmor = new MediumArmor();
    private HeavyArmor heavyArmor = new HeavyArmor();
    private SuperiorHeavyArmor superiorHeavyArmor = new SuperiorHeavyArmor();
    private WaterDagger waterDagger = new WaterDagger();
    private SuperiorAirWand superiorAirWand = new SuperiorAirWand();
    private Player player = new Player("Sven", new Point2D());

    @Test
    void canAddWeaponToInventory() {
        player.addWeaponToInventory(stick);
        Weapon expected = stick;
        assertEquals(player.getWeaponInventory().get(0), expected);
    }

    @Test
    void canAddArmorToInventory() {
        player.addArmorToInventory(lightArmor);
        Armor expected = lightArmor;
        assertEquals(player.getArmorInventory().get(0), expected);
    }

    @Test
    void inventoryCapacityIsFive() {
        int expected = 5;
        assertEquals(expected, player.getMaxInventoryCapacity());
    }

    @Test
    void canNotHaveMoreThanFiveWeaponsInWeaponInventory() {
        int expected = 5;
        player.addWeaponToInventory(stick);
        addFiveWeapons();
        assertEquals(expected, player.getWeaponInventory().size());
    }

    private void addFiveWeapons() {
        player.addWeaponToInventory(dagger);
        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);
    }

    @Test
    void canNotHaveMoreThanFiveArmorsInArmorInventory() {
        int expected = 5;
        player.addArmorToInventory(tome);
        addFiveArmors();
        assertEquals(expected, player.getArmorInventory().size());
    }

    private void addFiveArmors() {
        player.addArmorToInventory(shield);
        player.addArmorToInventory(lightArmor);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);
    }

    @Test
    void canRemoveWeaponFromWeaponInventory() {
        player.addWeaponToInventory(stick);
        player.removeWeaponFromInventory(stick);
        int expected = 0;

        assertEquals(expected, player.getWeaponInventory().size());
    }

    @Test
    void removesCorrectWeaponFromWeaponInventory() {
        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(dagger);
        player.removeWeaponFromInventory(stick);

        assertTrue(player.getWeaponInventory().contains(dagger));
    }

    @Test
    void removingArmorFromArmorInventoryReturnsCost() {
        int expected = 160;
        player.addArmorToInventory(heavyArmor);
        player.removeArmorFromInventory(heavyArmor);

        assertEquals(expected, player.getWallet());
    }

    @Test
    void correctValueStartingMoney() {
        int expected = 100;

        assertEquals(expected, player.getWallet());
    }

    @Test
    void inventoryAtCapacityMinusOne_AddsWeapon() {
        addFiveWeapons();
        assertTrue(player.getWeaponInventory().contains(sword));

    }

    @Test
    void inventoryAtCapacityMinusOne_AddsArmor() {
        addFiveArmors();
        assertTrue(player.getArmorInventory().contains(superiorHeavyArmor));

    }

    @Test
    void ifAtMaxCapacity_RemoveEarliestAddedWeapon() {
        player.addWeaponToInventory(stick);
        addFiveWeapons();
        assertFalse(player.getWeaponInventory().contains(stick));
    }

    @Test
    void ifAtMaxCapacity_RemoveEarliestAddedArmor() {
        player.addArmorToInventory(tome);
        addFiveArmors();
        assertFalse(player.getArmorInventory().contains(tome));
    }

    @Test
    void removingWeaponDueToCapacityReturnsWeaponsCost() {
        int expected = player.getWallet() + stick.getPrice();
        player.addWeaponToInventory(stick);
        addFiveWeapons();
        assertEquals(expected, player.getWallet());
    }

    @Test
    void removingArmorDueToCapacityReturnsArmorCost() {
        int expected = player.getWallet() + tome.getPrice();
        player.addArmorToInventory(tome);
        addFiveArmors();
        assertEquals(expected, player.getWallet());
    }

    @Test
    void weaponInventoryContainsNoDuplicates() {
        int expected = 1;
        player.addWeaponToInventory(stick);
        player.addWeaponToInventory(stick);

        assertEquals(expected, player.getWeaponInventory().size());
    }

    @Test
    void cantRemoveEquippedArmor() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        player.removeArmorFromInventory(heavyArmor);

        assertTrue(player.getArmorInventory().contains(heavyArmor));
    }

    @Test
    void cantRemoveEquippedArmor_InOffhand() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addArmorToInventory(shield);
        player.equipOffhand(shield);
        player.removeArmorFromInventory(shield);

        assertTrue(player.getArmorInventory().contains(shield));
    }

    @Test
    void cantRemoveEquippedWeapon() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);
        player.removeWeaponFromInventory(stick);

        assertTrue(player.getWeaponInventory().contains(stick));
    }

    @Test
    void cantRemoveEquippedOffhand_Dagger() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);
        player.removeWeaponFromInventory(dagger);

        assertTrue(player.getWeaponInventory().contains(dagger));
    }

    @Test
    void addingNullToWeaponInventoryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.addWeaponToInventory(null);
        });
    }

    @Test
    void addingNullToArmorInventoryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.addArmorToInventory(null);
        });
    }

    @Test
    void removingNullFromWeaponInventoryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.removeWeaponFromInventory(null);
        });
    }

    @Test
    void removingNullFromArmorInventoryThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            player.removeArmorFromInventory(null);
        });
    }

    @Test
    void armorInventoryContainsNoDuplicates() {
        int expected = 1;
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(heavyArmor);

        assertEquals(expected, player.getArmorInventory().size());
    }

    @Test
    void cantRemoveEquippedWeaponWhenRemovingDueToCapacityAndRemovesNextIndexedWeaponInstead() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());

        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);
        addFiveWeapons();

        assertTrue(player.getEquippedWeapon() == stick);
        assertTrue(player.getWeaponInventory().contains(stick));
        assertFalse(player.getWeaponInventory().contains(dagger));
    }

    @Test
    void removesThirdItemWhenTheFirstAndSecondWeaponAreUneligibleForRemoval() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());

        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);

        player.addWeaponToInventory(fireSword);
        player.addWeaponToInventory(torch);
        player.addWeaponToInventory(wand);
        player.addWeaponToInventory(sword);

        assertTrue(player.getEquippedWeapon() == waterDagger);
        assertTrue(player.getEquippedOffhand() == dagger);
        assertTrue(player.getWeaponInventory().contains(waterDagger));
        assertTrue(player.getWeaponInventory().contains(dagger));
        assertFalse(player.getWeaponInventory().contains(fireSword));
        assertTrue(player.getWeaponInventory().contains(sword));
    }

    @Test
    void cantRemoveEquippedArmorWhenRemovingDueToCapacityAndRemovesNextIndexedArmorInstead() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        addFiveArmors();

        assertTrue(player.getEquippedOffhand() == tome);
        assertTrue(player.getArmorInventory().contains(tome));
        assertFalse(player.getArmorInventory().contains(shield));
    }

    @Test
    void removesThirdItemWhenTheFirstAndSecondArmorAreUneligibleForRemoval() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertTrue(player.getEquippedArmor() == lightArmor);
        assertTrue(player.getEquippedOffhand() == tome);
        assertTrue(player.getArmorInventory().contains(lightArmor));
        assertTrue(player.getArmorInventory().contains(tome));
        assertFalse(player.getArmorInventory().contains(shield));
        assertTrue(player.getArmorInventory().contains(superiorHeavyArmor));
    }

    // Berör beslutstabellen
    // Kolumn 1
    @Test
    void doesNotEquipOffhandIfNotInWeaponInventory() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());
        player.equipOffhand(dagger);

        Equipment expected = null;
        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfNotInArmorInventory() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());

        player.equipOffhand(shield);
        Equipment expected = null;
        assertEquals(expected, player.getEquippedOffhand());
    }

    // Knight

    @Test
    void equipsOffhandIfCorrectRoleAndInInventory_Knight() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());

        Equipment expected = shield;
        player.addArmorToInventory(shield);
        player.equipOffhand(shield);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfWrongOffhandType_Knight_Armor() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        assertFalse(player.getEquippedOffhand() == tome);
    }

    @Test
    void doesNotEquipOffhandIfWrongOffhandType_Knight_Weapon() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());

        Equipment unexpected = dagger;
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);
        assertNotEquals(unexpected, player.getEquippedOffhand());
    }

    // Thief

    @Test
    void equipsOffhandIfCorrectRoleAndInInventory_Thief() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());

        Equipment expected = dagger;
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfWrongOffhandType_Thief_Armor() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());

        Equipment unexpected = tome;
        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        assertNotEquals(unexpected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfWrongOffhandType_Thief_Weapon() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());

        Equipment unexpected = sword;
        player.addWeaponToInventory(sword);
        player.equipOffhand(sword);
        assertNotEquals(unexpected, player.getEquippedOffhand());
    }

    // Mage

    @Test
    void equipsOffhandIfCorrectRoleAndInInventory_Mage() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        Equipment expected = tome;
        player.addArmorToInventory(tome);
        player.equipOffhand(tome);

        assertEquals(expected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfWrongOffhandType_Mage_Armor() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        Equipment unexpected = shield;
        player.addArmorToInventory(shield);
        player.equipOffhand(shield);

        assertNotEquals(unexpected, player.getEquippedOffhand());
    }

    @Test
    void doesNotEquipOffhandIfWrongOffhandType_Mage_Weapon() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        Equipment unexpected = dagger;
        player.addWeaponToInventory(dagger);
        player.equipOffhand(dagger);

        assertNotEquals(unexpected, player.getEquippedOffhand());
    }

    // -------------

    @Test
    void equipsSwordIfCorrectRoleAndInInventory() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        Weapon expected = fireSword;
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void equipsWandIfCorrectRoleAndInInventory() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());
        Weapon expected = superiorAirWand;
        player.addWeaponToInventory(superiorAirWand);
        player.equipWeapon(superiorAirWand);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void equipsDaggerIfCorrectRoleAndInInvetory() {
        player = new Player("Sven", new Elf(), new Thief(), new Point2D());
        Weapon expected = waterDagger;
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfWrongRole_Knight() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        Weapon expected = player.getEquippedWeapon();
        player.addWeaponToInventory(dagger);
        player.equipWeapon(dagger);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfWrongRole_Mage() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());
        Weapon expected = player.getEquippedWeapon();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfWrongRole_Thief() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());
        Weapon expected = player.getEquippedWeapon();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getEquippedWeapon());
    }

    @Test
    void doesNotEquipWeaponIfNotInWeaponInventory() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.equipWeapon(fireSword);
        assertFalse(player.getEquippedWeapon() == fireSword);
    }

    @Test
    void equippingWeaponIncreasesPlayerStats() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        double nothingEquippedStats = player.getStrength();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        double weaponEquippedStats = player.getStrength();
        assertTrue(weaponEquippedStats > nothingEquippedStats);
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Dwarf_Knight_Sword() {
        Knight knight = new Knight();
        player = new Player("Sven", new Dwarf(), knight, new Point2D());
        double expected = (player.getStrength() + (fireSword.getStrength() * knight.getStrengthMultiplier()));
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        assertEquals(expected, player.getStrength());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Dwarf_Knight_Club() {
        Knight knight = new Knight();
        player = new Player("Sven", new Dwarf(), knight, new Point2D());
        double expected = (player.getStrength() + (stick.getStrength() * knight.getStrengthMultiplier()));
        player.addWeaponToInventory(stick);
        player.equipWeapon(stick);
        assertEquals(expected, player.getStrength());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Human_Mage() {
        Mage mage = new Mage();
        player = new Player("Bengt", new Human(), mage, new Point2D());
        double expected = (player.getIntelligence()
                + (superiorAirWand.getIntelligence() * mage.getIntelligenceMultiplier()));
        player.addWeaponToInventory(superiorAirWand);
        player.equipWeapon(superiorAirWand);
        assertEquals(expected, player.getIntelligence());
    }

    @Test
    void equippingWeaponIncreasesStatsByCorrectAmount_Elf_Thief() {
        Thief thief = new Thief();
        player = new Player("Tor", new Elf(), thief, new Point2D());
        double expected = (player.getDexterity() + (waterDagger.getDexterity() * thief.getDexterityMultiplier()));
        player.addWeaponToInventory(waterDagger);
        player.equipWeapon(waterDagger);
        assertEquals(expected, player.getDexterity());
    }

    @Test
    void canUnequipWeapon() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        player.unequipWeapon();
        assertTrue(player.getEquippedWeapon() != torch);
    }

    @Test
    void unequippingWeaponReducesStats() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
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
        player = new Player("Sven", dwarf, knight, new Point2D());
        double expected = dwarf.getStartingStrength() * knight.getStrengthMultiplier();
        player.addWeaponToInventory(torch);
        player.equipWeapon(torch);
        player.unequipWeapon();
        assertEquals(expected, player.getStrength());
    }

    @Test
    void canNotEquipArmorIfNotInInventory() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.equipArmor(heavyArmor);
        assertFalse(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Knight() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        assertTrue(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Mage() {
        player = new Player("Sven", new Elf(), new Mage(), new Point2D());
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        assertTrue(player.getEquippedArmor() == lightArmor);
    }

    @Test
    void canEquipArmorIfCorrectRole_Thief() {
        player = new Player("Sven", new Human(), new Thief(), new Point2D());
        player.addArmorToInventory(mediumArmor);
        player.equipArmor(mediumArmor);
        assertTrue(player.getEquippedArmor() == mediumArmor);
    }

    @Test
    void canNotEquipArmorIfWrongRole_Mage() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        assertFalse(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canNotEquipArmorIfWrongRole_Thief() {
        player = new Player("Sven", new Dwarf(), new Thief(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        assertFalse(player.getEquippedArmor() == heavyArmor);
    }

    @Test
    void canNotEquipArmorIfWrongRole_Knight() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        assertFalse(player.getEquippedArmor() == lightArmor);
    }

    @Test
    void canUnequipArmor() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        player.unequipArmor();
        assertTrue(player.getEquippedArmor() == null);
    }

    @Test
    void equippingArmorIncreasesStatsByCorrectAmount() {
        Knight knight = new Knight();
        player = new Player("Sven", new Dwarf(), knight, new Point2D());
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
        Knight knight = new Knight();
        player = new Player("Sven", new Dwarf(), knight, new Point2D());
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

    // Matcher som undersöker om värden i två olika arrayer är lika.
    // Utöver förbättrad läsbarhet så kan man se alla värden i arrayen samtidigt
    // ifall den misslyckas
    // vilket gör att man kan se ifall det var flera värden som inte stämde överens
    // under samma testkörning
    @Test
    void equippingArmorIncreasesStatsByCorrectAmount_Matcher() {
        Knight knight = new Knight();
        player = new Player("Sven", new Dwarf(), knight, new Point2D());
        double[] expected = { player.getStrength() + (heavyArmor.getStrength() * knight.getStrengthMultiplier()),
                player.getDexterity() + (heavyArmor.getDexterity() * knight.getDexterityMultiplier()),
                player.getIntelligence() + (heavyArmor.getIntelligence() * knight.getIntelligenceMultiplier()),
                player.getHealth() + heavyArmor.getHealth(), player.getMana() + heavyArmor.getMana(),
                player.getArmorValue() + heavyArmor.getArmorValue() };
        player.addArmorToInventory(heavyArmor);
        player.equipArmor(heavyArmor);
        double[] equippedStats = { player.getStrength(), player.getDexterity(), player.getIntelligence(),
                player.getHealth(), player.getMana(), player.getArmorValue() };
        assertThat(equippedStats, is(equalTo(expected)));
    }

    @Test
    void unequippingArmorDecreasesStatsByCorrectAmount_Matcher() {
        Knight knight = new Knight();
        player = new Player("Sven", new Dwarf(), knight, new Point2D());
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
        assertThat(actual, is(equalTo(expected)));
    }

    // Ger ett tydligare felmeddelande.
    // Expected: a collection with size[x]
    // but: collection size was [y]
    // istället för att bara ge siffror
    @Test
    void canNotHaveMoreThanFiveWeaponsInWeaponInventory_Matcher() {
        player.addWeaponToInventory(stick);
        addFiveWeapons();
        int expected = 5;
        assertThat(player.getWeaponInventory(), hasSize(expected));
    }

    // Kan undersöka om en lista innehåller alla element oavsett ordning.
    @Test
    void removesThirdItemWhenTheFirstAndSecondArmorAreUneligibleForRemoval_Matcher() {
        player = new Player("Sven", new Dwarf(), new Mage(), new Point2D());

        player.addArmorToInventory(tome);
        player.equipOffhand(tome);
        player.addArmorToInventory(lightArmor);
        player.equipArmor(lightArmor);
        player.addArmorToInventory(shield);
        player.addArmorToInventory(mediumArmor);
        player.addArmorToInventory(heavyArmor);
        player.addArmorToInventory(superiorHeavyArmor);

        assertThat(player.getArmorInventory(),
                containsInAnyOrder(tome, lightArmor, superiorHeavyArmor, heavyArmor, mediumArmor));
    }

    // Något bättre läsbarhet än assertTrue (x>y)
    @Test
    void equippingWeaponIncreasesPlayerStats_Matcher() {
        player = new Player("Sven", new Dwarf(), new Knight(), new Point2D());
        double nothingEquippedStats = player.getStrength();
        player.addWeaponToInventory(fireSword);
        player.equipWeapon(fireSword);
        double weaponEquippedStats = player.getStrength();
        assertThat(weaponEquippedStats, greaterThan(nothingEquippedStats));
    }

}

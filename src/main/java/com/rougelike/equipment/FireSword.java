package com.rougelike.equipment;

public class FireSword extends Weapon {

    private static final String FIRESWORD_NAME = "Fire Sword";
    private static final int FIRESWORD_DAMAGE = 4;
    private static final int FIRESWORD_ELEMENTAL_DAMAGE = 10;
    private static final int FIRESWORD_PRICE = 300;
    private static final EquipmentType TYPE = EquipmentType.SWORD;

    public FireSword() {
        super(FIRESWORD_NAME, FIRESWORD_DAMAGE, FIRESWORD_ELEMENTAL_DAMAGE,
                FIRESWORD_PRICE, TYPE);
    }

}

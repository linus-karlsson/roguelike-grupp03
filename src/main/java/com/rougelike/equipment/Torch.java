package com.rougelike.equipment;

public class Torch extends Weapon {

    private static final String TORCH_NAME = "Torch";
    private static final int TORCH_DAMAGE = 2;
    private static final int TORCH_ELEMENTAL_DAMAGE = 3;
    private static final int TORCH_PRICE = 50;
    private static final EquipmentType TYPE = EquipmentType.CLUB;

    public Torch() {
        super(TORCH_NAME, TORCH_DAMAGE, TORCH_ELEMENTAL_DAMAGE, TORCH_PRICE, TYPE);
    }
}

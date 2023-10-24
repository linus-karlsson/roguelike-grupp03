package com.rougelike.equipment;

public enum ElementType {
    NONE,
    EARTH,
    WATER,
    FIRE,
    AIR;

    public static boolean elementIsEffective(ElementType a, ElementType b) {
        if (a == ElementType.WATER && b == ElementType.FIRE) {
            return true;
        }
        if (a == ElementType.EARTH && b == ElementType.FIRE) {
            return true;
        }
        if (a == ElementType.AIR && b == ElementType.EARTH) {
            return true;
        }
        return false;
    }
}

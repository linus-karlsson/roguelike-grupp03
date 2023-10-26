package com.roguelike;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.roguelike.enemies.Bandit;
import com.roguelike.enemies.Troll;
import com.roguelike.equipment.ElementType;

public class EntityTest {

    @Test
    void testConstructorSetElementType() {
        ElementType elementType = ElementType.NONE;
        Entity entity = new Bandit();
        assertThat(entity.getElement(), is(equalTo(elementType)));
    }

    @Test
    void testIsStunned() {
        Entity entity = new Bandit();
        entity.setUnStunned();
        assertFalse(entity.isStunned());
    }

    @Test
    void testAttackWhenStunned() {
        Entity entity = new Bandit();
        Entity other = new Troll();
        double healthBefore = other.getHealth();
        entity.setStunned();
        entity.attack(entity);
        double healthAfter = other.getHealth();
        assertThat(healthAfter, is(equalTo(healthBefore)));
    }
}

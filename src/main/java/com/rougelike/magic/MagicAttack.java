package com.rougelike.magic;

import com.rougelike.Player;

public class MagicAttack  extends MagicInvoker{
        public MagicAttack() {
            super("Attack");
        }
      
        public String getName() {
            return super.name;
        }

        @Override
        public void throwMagic(Magic magic, Player player) {
            System.out.println("Magic Attack");
        }
}

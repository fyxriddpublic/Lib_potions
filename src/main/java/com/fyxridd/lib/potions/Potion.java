package com.fyxridd.lib.potions;

import java.util.List;

public class Potion {
    int mode;
    boolean all;
    List<PotionItem> potions;

    Potion(int mode, boolean all, List<PotionItem> potions) {
        this.mode = mode;
        this.all = all;
        this.potions = potions;
    }
}

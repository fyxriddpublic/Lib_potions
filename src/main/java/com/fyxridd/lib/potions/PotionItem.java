package com.fyxridd.lib.potions;

import com.fyxridd.lib.core.api.CoreApi;
import org.bukkit.potion.PotionEffectType;

public class PotionItem {
    PotionEffectType potionEffectType;
    Time time;
    Level level;

    PotionItem(PotionEffectType potionEffectType, Time time, Level level) {
        this.potionEffectType = potionEffectType;
        this.time = time;
        this.level = level;
    }

    /**
     * 从字符串中读取PotionItem
     * @param s 定义字符串
     * @return 异常返回null
     */
    public static PotionItem load(String s) {
        PotionEffectType potionEffectType = CoreApi.getPotionEffectType(s.split(" ")[0]);
        if (potionEffectType == null) return null;
        Time time = Time.load(s.split(" ")[1]);
        if (time == null) return null;
        Level level = Level.load(s.split(" ")[2]);
        if (level == null) return null;
        return new PotionItem(potionEffectType, time, level);
    }
}

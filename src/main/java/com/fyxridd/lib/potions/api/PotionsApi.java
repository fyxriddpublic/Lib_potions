package com.fyxridd.lib.potions.api;

import com.fyxridd.lib.potions.PotionsMain;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class PotionsApi {
    /**
     * 重新读取药效配置
     * 会读取'插件名/potions.yml'文件
     */
    public static void reloadPotions(String plugin) {
        PotionsMain.reloadPotions(plugin);
    }

    /**
     * 添加药效
     * @param plugin 插件名,可为null(null时返回false)
     * @param type 药效类型,可为null(null时返回false)
     * @param le 增加药效的生物,可为null(null时返回false)
     * @return 添加成功的药效列表,不为null可为空列表
     */
    public static List<PotionEffect> addPotions(String plugin, String type, LivingEntity le) {
        return PotionsMain.addPotions(plugin, type, le);
    }
}

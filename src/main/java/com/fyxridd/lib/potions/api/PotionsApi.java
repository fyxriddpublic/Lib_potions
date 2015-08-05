package com.fyxridd.lib.potions.api;

import com.fyxridd.lib.potions.PotionsMain;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.util.List;

public class PotionsApi {
    /**
     * @param file yml文件,可为null(null时无效果)
     * @see #reloadPotions(String, org.bukkit.configuration.file.YamlConfiguration)
     */
    public static void reloadPotions(String plugin, File file) {
        PotionsMain.reloadPotions(plugin, file);
    }

    /**
     * 从配置文件中重新读取potions配置
     * @param plugin 注册的插件名,可为null(null时无效果)
     * @param config 配置文件,可为null(null时无效果)
     */
    public static void reloadPotions(String plugin, YamlConfiguration config) {
        PotionsMain.reloadPotions(plugin, config);
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

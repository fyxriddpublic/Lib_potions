package com.fyxridd.lib.potions;

import com.fyxridd.lib.core.api.ConfigApi;
import com.fyxridd.lib.core.api.CoreApi;
import com.fyxridd.lib.core.api.event.ReloadConfigEvent;
import com.fyxridd.lib.potions.api.PotionsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.util.*;

public class PotionsMain implements Listener{
    private static String savePath;

    //插件名 类型名 类型信息
    private static  HashMap<String, HashMap<String, Potion>> potionsHash = new HashMap<String, HashMap<String, Potion>>();

    public PotionsMain() {
        savePath = PotionsPlugin.dataPath+File.separator+"potions.yml";
        //初始化配置
        initConfig();
		//读取配置文件
		loadConfig();
		//注册事件
		Bukkit.getPluginManager().registerEvents(this, PotionsPlugin.instance);
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onReloadConfig(ReloadConfigEvent e) {
		if (e.getPlugin().equals(PotionsPlugin.pn)) {
            loadConfig();
        }
	}

    /**
     * @see com.fyxridd.lib.potions.api.PotionsApi#reloadPotions(String, File)
     */
    public static void reloadPotions(String plugin, File file) {
        if (plugin == null || file == null) return;
        reloadPotions(plugin, CoreApi.loadConfigByUTF8(file));
    }

    /**
     * @see com.fyxridd.lib.potions.api.PotionsApi#reloadPotions(String, org.bukkit.configuration.file.YamlConfiguration)
     */
    public static void reloadPotions(String plugin, YamlConfiguration config) {
        if (plugin == null || config == null) return;

        potionsHash.put(plugin, new HashMap<String, Potion>());

        Map<String, Object> map = config.getValues(false);
        if (map == null) return;
        for (String key:map.keySet()) loadPotion(plugin, config, key);
    }

    /**
     * @see com.fyxridd.lib.potions.api.PotionsApi#addPotions(String, String, org.bukkit.entity.LivingEntity)
     */
    public static boolean addPotions(String plugin, String type, LivingEntity le) {
        if (plugin == null || type == null || le == null) return false;

        boolean result = false;
        //不存在此注册的药效类型
        if (!potionsHash.containsKey(plugin) || !potionsHash.get(plugin).containsKey(type)) return result;
        //检测添加
        Potion potion = potionsHash.get(plugin).get(type);
        if (potion.potions == null) return result;
        for (PotionItem potionItem:potion.potions) {
            //数据
            int time = potionItem.time.getRandomTime();
            if (time == 0) continue;
            int level = potionItem.level.getRandomLevel();
            if (level == -1) continue;

            int preLevel = -1;
            int preTime = 0;
            for (PotionEffect pe:le.getActivePotionEffects()) {
                if (pe.getType().equals(potionItem.potionEffectType)) {
                    preLevel = pe.getAmplifier();
                    preTime = pe.getDuration();
                }
            }

            //检测是否添加药效
            switch (potion.mode) {
                case 1:
                    if (preLevel > -1 && preTime > 0) {
                        if (preLevel > level || preTime >= time) continue;
                    }
                    break;
                case 3:
                    if (preLevel > -1 && preTime > 0) continue;
            }

            //添加药效
            PotionEffect pe = new PotionEffect(potionItem.potionEffectType, time, level, false);
            le.addPotionEffect(pe, true);
            result = true;

            //是否检测下个药效
            if (!potion.all) break;
        }
        return result;
    }

    /**
     * 从指定的配置文件中读取指定类型的potions配置
     * @param plugin 注册的插件名,不为null
     * @param config 配置文件,不为null
     * @param type 指定的类型,不为null
     */
    private static void loadPotion(String plugin, FileConfiguration config, String type) {
        //读取
        //mode
        int mode = config.getInt(type+".mode");
        if (mode < 1) {
            mode = 1;
            ConfigApi.log(PotionsPlugin.pn, "load potion effect type '" + type + "''s mode error");
        }
        if (mode > 3) {
            mode = 3;
            ConfigApi.log(PotionsPlugin.pn, "load potion effect type '"+type+"''s mode error");
        }
        //all
        boolean all = config.getBoolean(type+".all");
        //potions
        List<PotionItem> potions = new ArrayList<PotionItem>();
        if (config.contains(type+".potions")) {
            for (String s : config.getStringList(type + ".potions")) {
                PotionItem potionItem = PotionItem.load(s);
                if (potionItem == null) {
                    ConfigApi.log(PotionsPlugin.pn, "load potion effect type '" + type + "''s potions error");
                    continue;
                }
                potions.add(potionItem);
            }
        }
        //添加
        Potion potion = new Potion(mode, all, potions);
        potionsHash.get(plugin).put(type, potion);
    }

    private void initConfig() {
        ConfigApi.register(PotionsPlugin.file, PotionsPlugin.dataPath, PotionsPlugin.pn, null);
        ConfigApi.loadConfig(PotionsPlugin.pn);
    }

	private static void loadConfig() {
        YamlConfiguration saveConfig = CoreApi.loadConfigByUTF8(new File(savePath));
        if (saveConfig == null) {
            ConfigApi.log(PotionsPlugin.pn, "potions.yml is error");
            return;
        }
        reloadPotions(PotionsPlugin.pn, saveConfig);
	}
}

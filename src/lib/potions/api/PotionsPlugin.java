package lib.potions.api;

import lib.core.api.CoreApi;
import lib.core.api.FormatApi;
import lib.potions.PotionsMain;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PotionsPlugin extends JavaPlugin{
    public static PotionsPlugin instance;
    public static String pn;
    public static File file;
    public static String dataPath;
    public static String ver;

    @Override
    public void onEnable() {
        instance = this;
        pn = getName();
        file = getFile();
        dataPath = file.getParentFile().getAbsolutePath()+ File.separator+pn;
        ver = CoreApi.getPluginVersion(file);

        new PotionsMain();

        //成功启动
        CoreApi.sendConsoleMessage(FormatApi.get(pn, 25, pn, ver).getText());
    }

    @Override
    public void onDisable() {
        //显示插件成功停止信息
        CoreApi.sendConsoleMessage(FormatApi.get(pn, 30, pn, ver).getText());
    }
}

package com.ankoki.olysene;

import com.ankoki.olysene.listeners.ClickListener;
import com.ankoki.olysene.test.TestCMD;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Olysene extends JavaPlugin {

    private static String nmsPackage;
    private Logger logger;
    private PluginDescriptionFile descriptionFile;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        this.logger = this.getLogger();
        this.descriptionFile = this.getDescription();
        this.getServer().getPluginManager().registerEvents(new ClickListener(), this);
        setNmsPackage();
        regTestCmds();
        logger.info(String.format("%s %s has been enabled in %.2f seconds (%sms)",
                descriptionFile.getName(),
                descriptionFile.getVersion(),
                (float) System.currentTimeMillis() - start,
                System.currentTimeMillis() - start));
    }

    private void setNmsPackage() {
        String packageName = this.getServer().getClass().getPackage().getName();
        nmsPackage = packageName.substring(packageName.lastIndexOf('.') + 1);
    }

    public static String getNmsPackage() {
        return nmsPackage;
    }

    private void regTestCmds() {
        this.getServer().getPluginCommand("olysene").setExecutor(new TestCMD());
    }
}

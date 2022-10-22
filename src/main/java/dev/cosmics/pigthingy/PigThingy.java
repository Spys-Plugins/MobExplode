package dev.cosmics.pigthingy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PigThingy extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("mobexplode").setExecutor(new MobCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new MobEvents(), this);
        System.out.println("PigThingy has been enabled!, made by super_cool_spy");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

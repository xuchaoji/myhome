package com.xuchaoji.myhome;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.xuchaoji.myhome.commands.Commands;

public class MyHome extends JavaPlugin {
	private FileConfiguration cfg= getConfig();
	@Override
	public void onEnable() {
		cfg.addDefault("setlable", "set");
		cfg.addDefault("golable", "go");
		cfg.addDefault("deletelable", "delete");
		cfg.addDefault("homeLimit", 3);
		cfg.options().copyDefaults(true);
		saveConfig();
		this.getCommand("myhome").setExecutor(new Commands());
		System.out.println(ChatColor.GREEN+"[MyHome] 加载成功~");
	}
	
	@Override 
	public void onDisable() {
		System.out.println(ChatColor.RED+"[MyHome] 已停止~");
	}
}

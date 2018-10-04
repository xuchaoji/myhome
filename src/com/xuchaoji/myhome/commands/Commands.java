package com.xuchaoji.myhome.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.xuchaoji.myhome.MyHome;

public class Commands implements CommandExecutor {
	private Plugin plugin=MyHome.getPlugin(MyHome.class);
	public String setlable = plugin.getConfig().getString("setlable");
	public String golable = plugin.getConfig().getString("golable");
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player=(Player)sender;
			if(args.length==0) {
				player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"请添加详细参数:\n"
						+ ChatColor.RED+"myhome set home 设置名为home的家。\n"
						+ ChatColor.RED+"myhome go home 传送到名为home的家。");
			}else if (args[0].equalsIgnoreCase(setlable)) {
				//设置家
				if(args.length==2) {
					Location location = player.getLocation();
					plugin.getConfig().set(player.getName()+"."+args[1]+".Yaw", location.getYaw());
					plugin.getConfig().set(player.getName()+"."+args[1]+".Pitch", location.getPitch());
					plugin.getConfig().set(player.getName()+"."+args[1]+".X", location.getX());
					plugin.getConfig().set(player.getName()+"."+args[1]+".Y", location.getY());
					plugin.getConfig().set(player.getName()+"."+args[1]+".Z", location.getZ());
					plugin.saveConfig();
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.GREEN+"已设置家："+ChatColor.DARK_AQUA+args[1]);
					return true;
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"设置出错：请添加合法的家名称。");
				}
			}else if(args[0].equalsIgnoreCase(golable)) {
				if(args.length==2) {
					if(plugin.getConfig().contains(player.getName()+"."+args[1])) {
						double x=plugin.getConfig().getDouble(player.getName()+"."+args[1]+".X");
						double y=plugin.getConfig().getDouble(player.getName()+"."+args[1]+".Y");
						double z=plugin.getConfig().getDouble(player.getName()+"."+args[1]+".Z");
						float yaw=(float)plugin.getConfig().getDouble(player.getName()+"."+args[1]+".Yaw");
						float pitch=(float)plugin.getConfig().getDouble(player.getName()+"."+args[1]+".Pitch");
						Location location=new Location(player.getWorld(), x, y, z, yaw, pitch);
						player.teleport(location);
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.GREEN+"已传送到家："+ChatColor.DARK_AQUA+args[1]);
						return true;
					}
					
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"传送出错：请添加合法的家名称。");
				}
			}else{
				player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"指令格式不对");
			}
		}else {
			//only player can set or go home
			sender.sendMessage(ChatColor.RED+"[MyHome]only player can use this command!");
		}
		return true;
	}

}

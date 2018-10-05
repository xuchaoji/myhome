package com.xuchaoji.myhome.commands;

import java.util.Iterator;
import java.util.Set;

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
	public String deletelable = plugin.getConfig().getString("deletelable");
	public String listlable = plugin.getConfig().getString("listlable");
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(sender instanceof Player) {
			Player player=(Player)sender;
			if(args.length==0) {
				player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"请添加详细参数:\n"
						+ ChatColor.RED+"myhome set home 设置名为home的家。\n"
						+ ChatColor.RED+"myhome go home 传送到名为home的家。\n"
						+ ChatColor.RED+"myhome list 查看家列表。\n"
						+ ChatColor.RED+ "myhome delete home 删除名为home的家。\n"
						+ ChatColor.DARK_RED+ "注意:请确保设置家和回家在同一个世界，否则会传送到不同世界对应位置，可能出现的后果自负。");
			}else if (args[0].equalsIgnoreCase(setlable)) {
				//set home
				int homeAmount;
				if(!plugin.getConfig().contains(player.getName())) {
					homeAmount=0;
				}else {
					homeAmount = plugin.getConfig().getConfigurationSection(player.getName()).getKeys(false).size();
				}
				int homeLimit = plugin.getConfig().getInt("homeLimit");
				if(homeAmount<homeLimit) {
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
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"设置出错：家数量已超限，最多设置："+ChatColor.AQUA+homeLimit+ChatColor.RED+"个家。");
				}
			}else if(args[0].equalsIgnoreCase(golable)) {
				//go home
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
					}else {
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"你没有设置家："+ChatColor.DARK_AQUA+args[1]);
					}
					
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"传送出错：请添加合法的家名称。");
				}
			}else if (args[0].equalsIgnoreCase(deletelable)) {
				//delete home
				if(args.length==2) {
					if(plugin.getConfig().contains(player.getName()+"."+args[1])) {
						plugin.getConfig().set(player.getName()+"."+args[1], null);
						plugin.saveConfig();
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.GREEN+"已删除家："+ChatColor.AQUA+args[1]);
					}else {
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"删除出错：你没有名为"+ChatColor.AQUA+args[1]+ChatColor.RED+"的家。");
					}
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"删除出错：请添加合法的家名称。");
				}
			}else if (args[0].equalsIgnoreCase(listlable)) {
				//homelist
				if(!plugin.getConfig().contains(player.getName())) {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"你还没设置过家。");
				}else {
					Set<String> homeSet = plugin.getConfig().getConfigurationSection(player.getName()).getKeys(false);
					Iterator<String> iterator=homeSet.iterator();
					player.sendMessage(ChatColor.GOLD+"[myhome]"+ChatColor.GREEN+"你已设置的家有：");
					while (iterator.hasNext()) {
						player.sendMessage(ChatColor.AQUA + iterator.next());
					}
				}
			}else{
				//other arguments except set go delete is invalid
				player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"指令格式不对");
			}
		}else {
			//only player can set or go home
			sender.sendMessage(ChatColor.RED+"[MyHome]only player can use this command!");
		}
		return true;
	}

}

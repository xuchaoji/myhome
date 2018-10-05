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
				player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"�������ϸ����:\n"
						+ ChatColor.RED+"myhome set home ������Ϊhome�ļҡ�\n"
						+ ChatColor.RED+"myhome go home ���͵���Ϊhome�ļҡ�\n"
						+ ChatColor.RED+"myhome list �鿴���б�\n"
						+ ChatColor.RED+ "myhome delete home ɾ����Ϊhome�ļҡ�\n"
						+ ChatColor.DARK_RED+ "ע��:��ȷ�����üҺͻؼ���ͬһ�����磬����ᴫ�͵���ͬ�����Ӧλ�ã����ܳ��ֵĺ���Ը���");
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
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.GREEN+"�����üң�"+ChatColor.DARK_AQUA+args[1]);
						return true;
					}else {
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"���ó�������ӺϷ��ļ����ơ�");
					}
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"���ó����������ѳ��ޣ�������ã�"+ChatColor.AQUA+homeLimit+ChatColor.RED+"���ҡ�");
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
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.GREEN+"�Ѵ��͵��ң�"+ChatColor.DARK_AQUA+args[1]);
						return true;
					}else {
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"��û�����üң�"+ChatColor.DARK_AQUA+args[1]);
					}
					
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"���ͳ�������ӺϷ��ļ����ơ�");
				}
			}else if (args[0].equalsIgnoreCase(deletelable)) {
				//delete home
				if(args.length==2) {
					if(plugin.getConfig().contains(player.getName()+"."+args[1])) {
						plugin.getConfig().set(player.getName()+"."+args[1], null);
						plugin.saveConfig();
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.GREEN+"��ɾ���ң�"+ChatColor.AQUA+args[1]);
					}else {
						player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"ɾ��������û����Ϊ"+ChatColor.AQUA+args[1]+ChatColor.RED+"�ļҡ�");
					}
				}else {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"ɾ����������ӺϷ��ļ����ơ�");
				}
			}else if (args[0].equalsIgnoreCase(listlable)) {
				//homelist
				if(!plugin.getConfig().contains(player.getName())) {
					player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"�㻹û���ù��ҡ�");
				}else {
					Set<String> homeSet = plugin.getConfig().getConfigurationSection(player.getName()).getKeys(false);
					Iterator<String> iterator=homeSet.iterator();
					player.sendMessage(ChatColor.GOLD+"[myhome]"+ChatColor.GREEN+"�������õļ��У�");
					while (iterator.hasNext()) {
						player.sendMessage(ChatColor.AQUA + iterator.next());
					}
				}
			}else{
				//other arguments except set go delete is invalid
				player.sendMessage(ChatColor.GOLD+"[MyHome]"+ChatColor.RED+"ָ���ʽ����");
			}
		}else {
			//only player can set or go home
			sender.sendMessage(ChatColor.RED+"[MyHome]only player can use this command!");
		}
		return true;
	}

}

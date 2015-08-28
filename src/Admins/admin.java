package Admins;

import me.sidhant.kitpvp.Config;
import me.sidhant.kitpvp.Titles;
import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;


public class admin {
	String name;
	Player p;
	public static void sendMessage(Player p, String UUID) {
		Titles.sendTitle(p, 20, 20*2, 20, ChatColor.YELLOW+"You are now a "+UUID, ChatColor.DARK_AQUA+"Congradulations!");
		Bukkit.getServer().broadcastMessage(ChatColor.BLUE+p.getName()+": just purchased "+UUID+"!");
	}
	public static void add(String name, String UUID) {
		Player p = Bukkit.getServer().getPlayer(name);
		if(p != null) {
			sendMessage(p, UUID);
			if(Util.hasPlayerinLobby(p)) {
				Config.addAdmin(name, UUID);
				Util.clearInventory(p);
				Util.setInventory(p);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
			}
		}
		else {
			Config.addAdmin(name, UUID);
		}
	}
}

package Kits;


import me.sidhant.kitpvp.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BasicPvp extends Kit implements Listener{
	public static int UUID = 0;
	public BasicPvp()	{
		price = 0;
		id = UUID;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
	}
	@Override
	public void addPlayer(Player p) {
		setMessage("You are now playing the basic pvp kit", ChatColor.GREEN, p);
		setHelmet(new ItemStack(Material.IRON_HELMET), p);
		setChestplate(new ItemStack(Material.IRON_CHESTPLATE), p);
		setLeggings(new ItemStack(Material.IRON_LEGGINGS), p);
		setBoots(new ItemStack(Material.IRON_BOOTS), p);
		setWeapon(new ItemStack(Material.DIAMOND_SWORD), p);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(14);
	}
	
	@EventHandler
	public void death(PlayerDeathEvent e) {
		Player p =  e.getEntity().getKiller();
		if(p == null) return;
		if(Util.getKit(p) instanceof BasicPvp) {
			Double newhealth = p.getHealth()+10;
			if(p.getMaxHealth() < newhealth) {
				p.setHealth(p.getMaxHealth());
			}
			else {
				p.setHealth(newhealth);
			}
			p.sendMessage(ChatColor.GREEN+"You gain health for getting a kill");
			
		}
	}
}

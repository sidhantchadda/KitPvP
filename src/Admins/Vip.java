package Admins;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;



public class Vip extends admin {
	
	public static String UUID = "VIP";
	public Vip() {

	}
	public static void addPlayer(String name) {
		add(name, UUID);
	}
	public static void addToInventory(Player p) {
		PlayerInventory inv = p.getInventory();
		inv.setHelmet(new ItemStack(Material.GOLD_HELMET));
		inv.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		inv.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
		inv.setBoots(new ItemStack(Material.GOLD_BOOTS));
	}
}

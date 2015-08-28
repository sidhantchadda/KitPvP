package Admins;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Hero extends admin {
	public static String UUID = "Hero";
	public static void addPlayer(String name) {
		add(name, UUID);
	}
	public static void addToInventory(Player p) {
		PlayerInventory inv = p.getInventory();
		inv.setHelmet(new ItemStack(Material.IRON_HELMET));
		inv.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		inv.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		inv.setBoots(new ItemStack(Material.IRON_BOOTS));
	}
}

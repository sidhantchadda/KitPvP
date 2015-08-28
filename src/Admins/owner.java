package Admins;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class owner extends admin {
	public static String UUID = "Owner";
	public static void addPlayer(String name) {
		add(name, UUID);
	}
	public static void addToInventory(Player p) {
		PlayerInventory inv = p.getInventory();
		inv.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		inv.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		inv.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		inv.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
	}
}

package Kits;

import me.sidhant.kitpvp.Util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Flash extends Kit {
	public static int UUID = 11;
	public Flash() {
		id = UUID;
		price = 25;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
	}
	@Override
	public void addPlayer(Player p) {
		setMessage("You are playing Flash you are fast but very weak", ChatColor.RED, p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 20));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1));
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta helmetMeta = setArmourColor(helmet.getItemMeta(), Color.RED);
		helmet.setItemMeta(helmetMeta);
		setHelmet(helmet, p);
		p.setMaxHealth(10);
		setWeapon(new ItemStack(Material.STONE_SWORD), p);
	}

}

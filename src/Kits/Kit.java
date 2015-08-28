package Kits;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

public class Kit {
	 public int price;
	 public int id;
	
	public void setMessage(String message, ChatColor color, Player p) {
		p.sendMessage(color+message);
	}
	public void setHelmet(ItemStack i, Player p) {
		p.getInventory().setHelmet(i);;
	}
	
	public void setChestplate(ItemStack i, Player p) {
		p.getInventory().setChestplate(i);
	}
	
	public void setLeggings(ItemStack i, Player p)	{
		p.getInventory().setLeggings(i);
	}
	
	public void setBoots(ItemStack i, Player p) {
		p.getInventory().setBoots(i);
	}
	
	public void setWeapon(ItemStack i, Player p) {
		ItemMeta stickMeta = i.getItemMeta();
		stickMeta.spigot().setUnbreakable(true);
		i.setItemMeta(stickMeta);
		p.getInventory().setItem(0, i);
	}
	
	public void setEffects(PotionEffect effect, Player p)	{
		p.addPotionEffect(effect, true);	
	}
	public void fillSoup(Player p) {
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(14);
	}
	public void addPlayer(Player p) {
		
	}
	public ItemMeta setArmourColor(ItemMeta meta, Color color) {
		if(meta instanceof LeatherArmorMeta) {
			LeatherArmorMeta leather = (LeatherArmorMeta) meta;
			leather.setColor(color);
			return leather;
		} 
		else {
			return meta;
		}
	}
}

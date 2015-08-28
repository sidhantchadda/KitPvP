package Kits;

import java.util.Random;

import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Gambler extends Kit implements Listener {
	public static int UUID = 13;
	Plugin plugin;
	public Gambler(Plugin plugin) {
		id = UUID;
		price = 15;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		setHelmet(new ItemStack(randomHelmet()), p);
		setChestplate(new ItemStack(randomChestplate()), p);
		setLeggings(new ItemStack(randomLeggings()), p);
		setBoots(new ItemStack(randomBoots()), p);
		ItemStack sword = new ItemStack(randomSword());
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.MAGIC+"!123! "+ChatColor.DARK_AQUA+"Crouch to gamble!"+ChatColor.WHITE+ChatColor.MAGIC+" !123!");
		
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		setHealth(p);
		p.setLevel(1);
		fillSoup(p);
	}
	public static void gamble(Player p) {
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setHelmet(new ItemStack(randomHelmet()));
		inv.setChestplate(new ItemStack(randomChestplate()));
		inv.setLeggings(new ItemStack(randomLeggings()));
		inv.setBoots(new ItemStack(randomBoots()));
		ItemStack sword = new ItemStack(randomSword());
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.MAGIC+"!123! "+ChatColor.DARK_AQUA+"Crouch to gamble!"+ChatColor.WHITE+ChatColor.MAGIC+" !123!");
		sword.setItemMeta(swordMeta);
		p.getInventory().setItem(0, sword);
		setHealth(p);
	}
	public static Material randomHelmet() {
		Random r = new Random();

		// Get the type
		int rt = r.nextInt(5) + 1;
		Material mat = Material.AIR;
		if (rt == 1)
			mat = Material.LEATHER_HELMET;
		if (rt == 2)
			mat = Material.CHAINMAIL_HELMET;
		if (rt == 3)
			mat = Material.GOLD_HELMET;
		if (rt == 4)
			mat = Material.IRON_HELMET;
		if (rt == 5)
			mat = Material.DIAMOND_HELMET;
		return mat;
	}
	public static Material randomChestplate() {
		Random r = new Random();

		// Get the type
		int rt = r.nextInt(5) + 1;
		Material mat = Material.AIR;
		if (rt == 1)
			mat = Material.LEATHER_CHESTPLATE;
		if (rt == 2)
			mat = Material.CHAINMAIL_CHESTPLATE;
		if (rt == 3)
			mat = Material.GOLD_CHESTPLATE;
		if (rt == 4)
			mat = Material.IRON_CHESTPLATE;
		if (rt == 5)
			mat = Material.DIAMOND_CHESTPLATE;
		return mat;
	}
	public static Material randomLeggings() {
		Random r = new Random();

		// Get the type
		int rt = r.nextInt(5) + 1;
		Material mat = Material.AIR;
		if (rt == 1)
			mat = Material.LEATHER_LEGGINGS;
		if (rt == 2)
			mat = Material.CHAINMAIL_LEGGINGS;
		if (rt == 3)
			mat = Material.GOLD_LEGGINGS;
		if (rt == 4)
			mat = Material.IRON_LEGGINGS;
		if (rt == 5)
			mat = Material.DIAMOND_LEGGINGS;
		return mat;
	}
	public static Material randomBoots() {
		Random r = new Random();

		// Get the type
		int rt = r.nextInt(5) + 1;
		Material mat = Material.AIR;
		if (rt == 1)
			mat = Material.LEATHER_BOOTS;
		if (rt == 2)
			mat = Material.CHAINMAIL_BOOTS;
		if (rt == 3)
			mat = Material.GOLD_BOOTS;
		if (rt == 4)
			mat = Material.IRON_BOOTS;
		if (rt == 5)
			mat = Material.DIAMOND_BOOTS;
		return mat;
	}
	public static Material randomSword() {
		Random r = new Random();

		// Get the type
		int rt = r.nextInt(5) + 1;
		Material mat = Material.AIR;
		if (rt == 1)
			mat = Material.WOOD_SWORD;
		if (rt == 2)
			mat = Material.STONE_SWORD;
		if (rt == 3)
			mat = Material.GOLD_SWORD;
		if (rt == 4)
			mat = Material.IRON_SWORD;
		if (rt == 5)
			mat = Material.DIAMOND_SWORD;
		return mat;
	}
	public static void setHealth(Player p) {
		Random r = new Random();
		int rt = r.nextInt(40) + 1;
		p.setHealth(p.getMaxHealth());
		p.setMaxHealth(rt);
		p.setHealth(p.getMaxHealth());
	}
	@EventHandler
	public void crouch(PlayerToggleSneakEvent e) {
		if(e.isSneaking()) {
			if(Util.getKit(e.getPlayer()) instanceof Gambler) {
				final Player p = e.getPlayer();
				if(p.getLevel() >= 1) {
					Gambler.gamble(p);
					p.setLevel(0);
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
					p.sendMessage(ChatColor.GREEN+"Gamble!");
					
					BukkitScheduler scheduler = Bukkit.getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							if(Util.getKit(p) instanceof Gambler) {
								p.setLevel(1);
							}
						}
						
					}, 20*15);
				}
				else {
					p.sendMessage(ChatColor.GRAY+"Gamble has a 15 second cooldown!");
				}
			}
		}
	}
}

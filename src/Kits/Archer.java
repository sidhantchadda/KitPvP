package Kits;

import java.util.Random;

import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Archer extends Kit implements Listener{
	public static int UUID = 1;
	Plugin plugin;
	public Archer(Plugin plugin) {
		id = UUID;
		price = 10;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		setMessage("You are playing the Archer kit", ChatColor.GREEN, p);
		p.setLevel(1);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		helmet.setItemMeta(setArmourColor(helmet.getItemMeta(), Color.FUCHSIA));
		setHelmet(helmet, p);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.SILVER));
		setChestplate(chestplate, p);
		
		ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Leggings.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.SILVER));
		setLeggings(Leggings, p);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.setItemMeta(setArmourColor(boots.getItemMeta(), Color.FUCHSIA));
		setBoots(boots, p);
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		bowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
		bowMeta.addEnchant(Enchantment.DURABILITY, 5, true);
		bowMeta.setDisplayName("Gods Bow");
		bow.setItemMeta(bowMeta);
		setWeapon(bow, p);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.getInventory().addItem(new ItemStack(Material.ARROW));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
	}
	
	@EventHandler
	public void shoot(EntityShootBowEvent e) {
		if(e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if(Util.getKit(p) instanceof Archer) {
				if(p.getLevel() > 0) {
					Random r = new Random();
					double rangeMax = .3;
					double rangeMin = -.3;
					for(int a = 0; a<5; a++) {	
						Arrow ar = p.launchProjectile(Arrow.class);	
						Vector vec =e.getProjectile().getVelocity();
						ar.setVelocity(vec.add(new Vector(rangeMin + (rangeMax - rangeMin) * r.nextDouble(), rangeMin + (rangeMax - rangeMin) * r.nextDouble(), rangeMin + (rangeMax - rangeMin) * r.nextDouble())));
						
					}
					p.setLevel(0);
					BukkitScheduler scheduler = Bukkit.getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							p.setLevel(1);
							p.sendMessage(ChatColor.BLUE+"Volley ready");
						}
						
					}, 20L*7);
					
				}
			}
		}	
	}

}

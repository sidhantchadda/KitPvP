package Kits;

import java.util.Arrays;

import me.sidhant.kitpvp.Util;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Vampire extends Kit implements Listener{
	public static int UUID = 12;
	Plugin plugin;
	public Vampire(Plugin plugin) {
		id = UUID;
		price = 100;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		helmet.setItemMeta(setArmourColor(helmet.getItemMeta(), Color.RED));
		setHelmet(helmet, p);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.BLACK));
		setChestplate(chestplate, p);
		
		ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Leggings.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.BLACK));
		setLeggings(Leggings, p);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.setItemMeta(setArmourColor(boots.getItemMeta(), Color.RED));
		setBoots(boots, p);
		
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.DARK_RED+"Blood Thief");
		swordMeta.setLore(Arrays.asList("Double jump to bat fly!", "Attack enenmies to steal their life!"));
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		
		p.setAllowFlight(true);
		p.setFlySpeed(0.1f);
		p.setLevel(1);
	}
	
	@EventHandler
	public void lifesteal(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			if(e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				if(Util.getKit(p) instanceof Vampire) {
					double health = p.getHealth()+(e.getDamage()/3);
					if(health > p.getMaxHealth()) {
						p.setHealth(p.getMaxHealth());
					}
					else {
						p.setHealth(health);
					}
				}
			}
		}
	}
	@EventHandler
	public void fall(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Util.getKit(p) instanceof Vampire) {
				if(e.getCause() == DamageCause.FALL) e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void fly(PlayerToggleFlightEvent e) {
		final Player p = e.getPlayer();
		if(Util.getKit(p) instanceof Vampire) {
			if(p.getLevel() >= 1) {
				p.setFlying(true);
				p.sendMessage(ChatColor.GREEN+"Bat fly!");
				Location loc = p.getLocation();
				World world = p.getWorld();
				p.playSound(loc, Sound.BAT_DEATH, 1F, 1F);
				p.setLevel(0);
				for(int x = 0; x<10; x++) {
					Bat bat = (Bat) world.spawnEntity(loc, EntityType.BAT);
					bat.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 0), true);
				}
				BukkitScheduler scheduler = Bukkit.getScheduler();
				scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						if(Util.getKit(p) instanceof Vampire) {
							p.setFlying(false);
							p.setAllowFlight(false);
							p.sendMessage(ChatColor.GRAY+"Flyer cooldown 20 seconds");
							p.setLevel(0);
						}
					}
					
				}, 3* 20L);
				scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						if(Util.getKit(p) instanceof Vampire) {
							p.setLevel(1);
							p.setFlySpeed(0.1f);
							p.setAllowFlight(true);
						}
					}
					
				}, 20* 20L);
			}
		}
	}


}

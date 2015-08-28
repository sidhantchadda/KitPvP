package Kits;

import java.util.HashMap;

import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Ghost extends Kit implements Listener{
	public static int UUID = 5;
	private Plugin plugin;
	private HashMap<String, ItemStack[]> armourSave;
	public Ghost(Plugin plugin) {
		id = UUID;
		price = 35;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
		armourSave = new HashMap<String, ItemStack[]>();
	}
	@Override
	public void addPlayer(Player p) {
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setLevel(1);
		p.setAllowFlight(true);
		p.sendMessage(ChatColor.GREEN+"You are playing Ghost use crouch and double jump to activate you abitlies");
		setHelmet(new ItemStack(Material.LEATHER_HELMET), p);
		setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE), p);
		setLeggings(new ItemStack(Material.LEATHER_LEGGINGS), p);
		setBoots(new ItemStack(Material.LEATHER_BOOTS), p);
		setWeapon(new ItemStack(Material.IRON_SWORD), p);
	}
	
	@EventHandler
	public void invis(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if(Util.getKit(p) instanceof Ghost) {
			if(e.isSneaking()) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
				armourSave.put(p.getName(), p.getInventory().getArmorContents());
	            p.getInventory().setArmorContents(null);
				p.sendMessage(ChatColor.GRAY+"You are now Invisible");
			}
			else {
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
				p.getInventory().setArmorContents(armourSave.get(p.getName()));
				p.sendMessage(ChatColor.GRAY+"You are no longer Invisible");
			}
		}
	}
	
	@EventHandler
	public void fly(PlayerToggleFlightEvent event) {
		final Player p = event.getPlayer();
		if(Util.getKit(p) instanceof Ghost) {
			if(p.getLevel() >= 1) {
				p.setFlying(true);
				p.sendMessage(ChatColor.GREEN+"You are now flying");
				BukkitScheduler scheduler = Bukkit.getScheduler();
				scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						if(Util.getKit(p) instanceof Ghost) {
							p.setFlying(false);
							p.setAllowFlight(false);
							p.sendMessage(ChatColor.GRAY+"Flyer cooldown 20 seconds");
							p.setLevel(0);
						}
					}
					
				}, 1* 20L);
				scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						if(Util.getKit(p) instanceof Ghost) {
							p.setLevel(1);
							p.setAllowFlight(true);
						}
					}
					
				}, 20* 20L);
			}
		}
	}

}

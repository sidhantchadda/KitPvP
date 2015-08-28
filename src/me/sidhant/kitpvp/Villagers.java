package me.sidhant.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Villagers implements Listener{
	public static String buy = ChatColor.BLUE+"Black Smith!";
	public static String babybuy = ChatColor.AQUA+"Child!";
	public static String tip = ChatColor.LIGHT_PURPLE+"Tips";
	public Villagers() {
		World w = Bukkit.getServer().getWorld(Main.worldName);
		addVillager(new Location(w, 1, 2, -6), buy, Profession.BLACKSMITH, true);
		addVillager(new Location(w, 0, 2, -6), babybuy, Profession.BLACKSMITH, false);
		//addVillager(new Location(w, 4., 65, 21), tip, Profession.LIBRARIAN, true);
	}
	
	private void addVillager(Location loc, String text, Profession profession, boolean adult) {
		Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
		villager.setRemoveWhenFarAway(false);
		villager.setProfession(profession);
		villager.setAgeLock(true);
		villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 20), true);
		if(text != null) {
			villager.setCustomName(text);
			villager.setCustomNameVisible(true);
		}
		if(adult) {
			villager.setAdult();
		}
		else {
			villager.setBaby();
		}
	}
	
	@EventHandler
	public void damage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Villager) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void interact(PlayerInteractEntityEvent e) {

		if (e.getRightClicked() instanceof Villager) {
			e.setCancelled(true);
			Villager v = (Villager) e.getRightClicked();
			if (v.getCustomName() == buy) {
				e.getPlayer().chat("/buy");
				v.playEffect(EntityEffect.VILLAGER_HAPPY);
			}
			if (v.getCustomName() == babybuy) {
				e.getPlayer().sendMessage(
						ChatColor.AQUA + "Child:" + ChatColor.WHITE
								+ " Please support my family!");
			}
		}

	}

}

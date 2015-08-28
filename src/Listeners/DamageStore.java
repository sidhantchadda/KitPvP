package Listeners;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import me.sidhant.kitpvp.Config;
import me.sidhant.kitpvp.Titles;
import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class DamageStore implements Listener { 
	Plugin plugin;
	HashMap<UUID, HashMap<UUID, Double>> damaged;
	public final int StoreTime = 10;
	//stores the damage in the map for 10 seconds
	public DamageStore(Plugin plugin) {
		damaged = new HashMap<UUID, HashMap<UUID, Double>>();
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID defenderid = p.getUniqueId();
		if(Util.hasPlayerinLobby(p)) return;
		if(damaged.containsKey(defenderid)) {
			//did not log off safely
			Config.addDeaths(p.getName(), 1);
			giveCredit(p);
		}
	}
	private void giveCredit(Player defender) {
		UUID defenderid = defender.getUniqueId();
		HashMap<UUID, Double> inner = damaged.get(defenderid);
		if(inner == null) return;
		Double total = 0.0;
		for (Double value : inner.values()) {
		    // ...
			total = total+ value;
		}
		Double killerdamage = 0.0;
		OfflinePlayer killer = null;
		for (Entry<UUID, Double> entry : inner.entrySet()) {
		    UUID key = entry.getKey();
		    Double value = entry.getValue();
		    Player attacker = Bukkit.getPlayer(key);
		    if(attacker == null) return;
		    if(attacker.isOnline()) {
		    	if(value > killerdamage) {
		    		if(attacker.getUniqueId() != defenderid) {
		    			killerdamage = value;
			    		killer = attacker;
		    		}
		    	}
		    	assist(value, total, attacker, defender);
		    }
		    // ...
		}
		if(killer != null) {
			if(killer.isOnline()) {
				Titles.sendTitle((Player) killer, 20, 20*2, 20, ChatColor.GREEN+"You killed "+defender.getName(), ChatColor.AQUA+"Giving you 5 tokens");
				Config.addKills(killer.getName(), 1);
				Config.addTokens(killer.getName(), 5);
			}
			Titles.sendTitle(defender, 20, 20*2, 20, ChatColor.RED+"You were killed by " +killer.getName(), ChatColor.GRAY+"Try another kit");
			Config.addDeaths(defender.getName(), 1);
			
		}
		damaged.remove(defenderid);
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void death(PlayerDeathEvent e) {
		giveCredit(e.getEntity());
	}
	
	@EventHandler
	public void EntityDamge(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof LivingEntity) {
				damage((LivingEntity)e.getDamager(), (Player)e.getEntity(), e.getDamage());
			}
			else if(e.getDamager() instanceof Projectile) {
				Projectile p = (Projectile) e.getDamager();
				if(p.getShooter() instanceof LivingEntity) {
					LivingEntity shooter = (LivingEntity) p.getShooter();
					damage(shooter, (Player)e.getEntity(), e.getDamage());
				}
			}
		}
	}
	
	private void damage(LivingEntity attacker, final Player defender, final double damage) {
		final UUID attackerid = attacker.getUniqueId();
		final UUID defenderid = defender.getUniqueId();
		HashMap<UUID, Double> map = damaged.get(defenderid);
		if(map != null) {
			if(map.containsKey(attackerid)) {
				map.put(attackerid, map.get(attackerid)+damage);
			}
			else {
				map.put(attackerid, damage);
			}
		}
		else {
			map = new HashMap<UUID, Double>();
			map.put(attackerid, damage);
		}
		//inner map is definitely created
		damaged.put(defenderid, map);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				HashMap<UUID, Double> map = damaged.get(defenderid);
				if(map == null) return;
				Double olddamage = map.get(attackerid);
				if(olddamage == null) return;
				Double removedDamage = olddamage - damage;
				if(removedDamage <= 0) {
					map.remove(attackerid);
					if(map.isEmpty()) {
						defender.sendMessage(ChatColor.WHITE+"["+ChatColor.RED+"COMBAT LOG" +ChatColor.WHITE+"]"+ChatColor.GRAY+" You can now log off safely!");
					}
				}
				else {
					map.put(attackerid, removedDamage);
				}
				damaged.put(defenderid, map);
			}
			
		}, 20L*StoreTime);
	}
	//This code runs everytime a player gets an assist
	private void assist(double value, double total, Player attacker, Player defender) {
		if(attacker.isOnline()) {
			Titles.sendTitle(attacker, 20, 20*2, 20, ChatColor.WHITE+"You got an Assist", ChatColor.AQUA+"Giving you 1 token");
			Config.addTokens(attacker.getName(), 1);
	    	attacker.sendMessage(ChatColor.GREEN+"Responsible for "+ChatColor.GOLD+value/total*100+"% "+ChatColor.GREEN+"of the damage to "+defender.getName());
		}
    	
	}
	//This code runs whenever you get a kill
	private void kill(double value, double total, Player killer, Player defender) {
		if(killer.isOnline()) {
			Titles.sendTitle((Player) killer, 20, 20*2, 20, ChatColor.GREEN+"You killed "+defender.getName(), ChatColor.AQUA+"Giving you 5 tokens");
			Config.addKills(killer.getName(), 1);
			Config.addTokens(killer.getName(), 5);
		}
	}
//	private void killed(Player killed, Player killer) {
//		Titles.sendTitle(defender, 20, 20*2, 20, ChatColor.RED+"You were killed by " +killer.getName(), ChatColor.GRAY+"Try another kit");
//		Config.addDeaths(defender.getName(), 1);
//	}

}

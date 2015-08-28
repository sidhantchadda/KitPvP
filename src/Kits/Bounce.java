package Kits;

import java.util.Arrays;
import java.util.List;

import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Bounce extends Kit implements Listener {
	
	public static int UUID = 16;
	Plugin plugin;
	public Bounce(Plugin plugin) {
		id = UUID;
		this.plugin = plugin;
		price = 1000;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		helmet.setItemMeta(setArmourColor(helmet.getItemMeta(), Color.LIME));
		setHelmet(helmet, p);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.ORANGE));
		setChestplate(chestplate, p);
		
		ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Leggings.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.ORANGE));
		setLeggings(Leggings, p);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.setItemMeta(setArmourColor(boots.getItemMeta(), Color.LIME));
		setBoots(boots, p);
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Bouncing Spear!");
		swordMeta.setLore(Arrays.asList(ChatColor.RED+"Press shift to begin bouncing", ChatColor.RED+"The more enemies you damage the higher you jump", ChatColor.RED+"The higher you jump the more damage you do!"));
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		p.setLevel(1);
		fillSoup(p);
	}
	@EventHandler
	public void hitGround(EntityDamageEvent e)
	{
		if(e.getCause() == DamageCause.FALL) {
			Entity en = e.getEntity();
			if(en instanceof Player) {
				Player p = (Player) en;
				if(Util.getKit(p) instanceof Bounce) {
					List<Entity> entities = p.getNearbyEntities(2, 2, 2);
					for(Entity entity: entities) {
						if(entity instanceof LivingEntity) {
							LivingEntity alive = (LivingEntity) entity;
							alive.damage(e.getDamage()/3, p);
							alive.sendMessage(ChatColor.GREEN+p.getName()+ChatColor.WHITE+" Has jumped on you!");
						}
						else {
							entities.remove(entity);
						}
					}
					double health = p.getHealth()+entities.size();
					if(health > p.getMaxHealth()) {
						p.setHealth(p.getMaxHealth());
					}
					else {
						p.setHealth(health);
					}
					Vector v  = p.getVelocity();
					p.setVelocity(p.getVelocity().add(new Vector(v.getX()*10, 1.5+entities.size(), v.getZ()*10)));
					e.setCancelled(true);
					ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.EXPLOSION_NORMAL, .2, 20, .2);
					effect.sendToLocation(p.getLocation());
				}
			}
		}
	}
	@EventHandler
	public void toggle(PlayerToggleSneakEvent e) {
		if(e.isSneaking()) {
			final Player p = e.getPlayer();
			if(Util.getKit(p) instanceof Bounce) {
				if(p.getLevel() >= 1) {
					if(p.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
						p.setVelocity(new Vector(0, 2, 0));
						p.sendMessage(ChatColor.GREEN+"Bounce!");
						p.setLevel(0);
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								if(Util.getKit(p) instanceof Bounce) {
									p.setLevel(1);
									p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
								}
							}
							
							}, 20L*8);

					}
					else {
						p.sendMessage(ChatColor.GRAY+"You must be on the ground in order to bounce");
					}
				}
				else {
					p.sendMessage(ChatColor.GRAY+"Bounce is on cooldown!");
				}
			}
		}
	}
}

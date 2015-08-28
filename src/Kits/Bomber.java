package Kits;

import java.util.Arrays;

import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Bomber extends Kit implements Listener {
	public static int UUID = 8;
	Plugin plugin;
	public Bomber(Plugin plugin) {
		id = UUID;
		price = 100;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		setHelmet(new ItemStack(Material.CHAINMAIL_HELMET), p);
		ItemStack armour = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemMeta meta = setArmourColor(armour.getItemMeta(), Color.RED);
		armour.setItemMeta(meta);
		setChestplate(armour, p);
		setLeggings(new ItemStack(Material.IRON_LEGGINGS), p);
		setBoots(new ItemStack(Material.DIAMOND_BOOTS), p);
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setLore(Arrays.asList("Crouch to throw bomb!", "Bomb heals if no one else is around!"));
		swordMeta.setDisplayName(ChatColor.RED+"Allahu Akbar");
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		fillSoup(p);
		p.setLevel(1);
	}
	
	@EventHandler
	public void crouch(PlayerToggleSneakEvent e) {
		final Player p = e.getPlayer();
		if(Util.getKit(p) instanceof Bomber) {
			if(e.isSneaking()) {
				if(p.getLevel() >= 1) {
					if(p.getLocation().getBlock().getType() == Material.AIR || p.getLocation().getBlock().getType() == Material.LONG_GRASS) {
						p.setLevel(0);
						TNTPrimed tnt = (TNTPrimed) p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
						tnt.setIsIncendiary(false);
						tnt.setFuseTicks(20*2);
						tnt.setVelocity(p.getVelocity().multiply(2.5));
						tnt.setCustomName(p.getName());
						
						BukkitScheduler scheduler = Bukkit.getScheduler();
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								if(Util.getKit(p) instanceof Bomber) {
									p.setLevel(1);
								}
							}
							
						}, 20*5);
					}
				}
				else {
					p.sendMessage(ChatColor.GRAY+"Tnt has a 5 second cooldown!");
				}
			}
		}
	}
	@EventHandler
	public void damaged(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			if(e.getDamager() instanceof TNTPrimed) {
				if(e.getEntity() instanceof LivingEntity) {
					if(e.getEntity() instanceof Player) {
						Player p = (Player) e.getEntity();
						if(Util.getKit(p) instanceof Bomber) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*3, 1), true);
							p.sendMessage(ChatColor.GREEN+"You have been healed by a bomb");
							e.setCancelled(true);
						}
						else {
							TNTPrimed tnt = (TNTPrimed) e.getDamager();
							String name = tnt.getCustomName();
							Player bomber = Bukkit.getServer().getPlayer(name);
							if(bomber != null) {
								p.sendMessage(ChatColor.RED+"You have been bombed by "+name);
								p.damage(10, bomber);
								e.setCancelled(true);
							}
						}
					}
					else {
						//if they are a mob
						e.setDamage(5);
						e.setCancelled(true);
					}
				}
			}
		}
	}

}

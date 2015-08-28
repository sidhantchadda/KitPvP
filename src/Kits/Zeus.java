package Kits;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Zeus extends Kit implements Listener {
	public static int UUID = 17;
	Plugin plugin;
	private String bolt = ChatColor.DARK_GRAY + "Lightning Bolt!";
	private String storm = ChatColor.DARK_GRAY+"Lightning Storm!";
	private String heal = ChatColor.DARK_GRAY+"God's Heal!";
	private int stormradius = 10;
	public Zeus(Plugin plugin) {
		id = UUID;
		this.plugin = plugin;
		price = 1500;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}

	@Override
	public void addPlayer(Player p) {
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		helmet.setItemMeta(setArmourColor(helmet.getItemMeta(), Color.TEAL));
		setHelmet(helmet, p);

		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.setItemMeta(setArmourColor(chestplate.getItemMeta(),
				Color.SILVER));
		setChestplate(chestplate, p);

		ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Leggings.setItemMeta(setArmourColor(chestplate.getItemMeta(),
				Color.SILVER));
		setLeggings(Leggings, p);

		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.setItemMeta(setArmourColor(boots.getItemMeta(), Color.TEAL));
		setBoots(boots, p);

		ItemStack weapon = new ItemStack(Material.STICK);
		ItemMeta weaponMeta = weapon.getItemMeta();
		weaponMeta.setDisplayName(bolt);
		weaponMeta.setLore(Arrays.asList(ChatColor.BLUE
				+ "Click to strike lightning!"));
		weaponMeta.addEnchant(Enchantment.LURE, 1, true);
		weapon.setItemMeta(weaponMeta);
		setWeapon(weapon, p);
		
		ItemStack StormStick = new ItemStack(Material.STICK);
		ItemMeta stormMeta = StormStick.getItemMeta();
		stormMeta.setDisplayName(storm);
		stormMeta.addEnchant(Enchantment.LURE, 1, true);
		StormStick.setItemMeta(stormMeta);
		p.getInventory().addItem(StormStick);
		
		ItemStack healStick = new ItemStack(Material.STICK);
		ItemMeta healMeta = StormStick.getItemMeta();
		healMeta.setDisplayName(heal);
		healMeta.addEnchant(Enchantment.LURE, 1, true);
		healStick.setItemMeta(healMeta);
		p.getInventory().addItem(healStick);
		fillSoup(p);

		
	}

	@EventHandler
	public void strike(PlayerInteractEvent e) {
		if (e.getAction() != Action.PHYSICAL) {
			final Player p = e.getPlayer();
			if (Util.getKit(p) instanceof Zeus) {
				final ItemStack item = p.getItemInHand();
				if (item.getType() == Material.STICK) {
					final ItemMeta stickmeta = item.getItemMeta();
					//code for bolt
					if (stickmeta.getDisplayName() == bolt) {
						if (stickmeta.hasEnchants()) {
							@SuppressWarnings("deprecation")
							Block b = p.getTargetBlock(null, 50);
							Location loc = b.getLocation();
							if (b.getType() != Material.AIR) {
								loc = b.getLocation().add(0, 1, 0);
								spawnLightning(p, loc);
								stickmeta.removeEnchant(Enchantment.LURE);
								item.setItemMeta(stickmeta);
								Bukkit.getScheduler().scheduleSyncDelayedTask(
										plugin, new Runnable() {

											public void run() {
												// TODO Auto-generated method
												// stub
												if(Util.getKit(p) instanceof Zeus) {
													PlayerInventory inv = p.getInventory();
													inv.remove(item);
													stickmeta.addEnchant(Enchantment.LURE, 1, true);
													item.setItemMeta(stickmeta);
													inv.addItem(item);
												}
											}

										}, 20L * 7);
							} else {
								p.sendMessage(ChatColor.GRAY
										+ "This is not a valid location!");
							}
						} else {
							p.sendMessage(ChatColor.GRAY+"Bolt is on cooldown");
						}
					}
					//code for storm
					if(stickmeta.getDisplayName() == storm) {
						if(stickmeta.hasEnchants()) {
							stickmeta.removeEnchant(Enchantment.LURE);
							item.setItemMeta(stickmeta);
							Bukkit.getScheduler().scheduleSyncDelayedTask(
									plugin, new Runnable() {

										public void run() {
											// TODO Auto-generated method
											// stub
											if(Util.getKit(p) instanceof Zeus) {
												PlayerInventory inv = p.getInventory();
												inv.remove(item);
												stickmeta.addEnchant(Enchantment.LURE, 1, true);
												item.setItemMeta(stickmeta);
												inv.addItem(item);
											}
										}

									}, 20L * 25);
							 new BukkitRunnable() {
						            int x = 0;
						            Random ran = new Random();
						            public void run() {
						            	x++;
						            	if(x >= 16){
						                    this.cancel();
						                } else {
						                	if(p != null) {
						                		int ranX = ran.nextInt((stormradius - -stormradius) + 1) - stormradius;
						                		int ranZ = ran.nextInt((stormradius - -stormradius) + 1) - stormradius;
						                		spawnLightning(p, p.getLocation().add(ranX, 0, ranZ));
						                	}
						                }
						            }
						        }.runTaskTimer(plugin, 0, 15);
						}
						else {
							p.sendMessage(ChatColor.GRAY+"Lighning Storm is on cooldown!");
						}
					}
					//code for heal
					if(stickmeta.getDisplayName() == heal) {
						if(stickmeta.hasEnchants()) {
							Location loc = p.getLocation();
							spawnLightning(p, loc);
							stickmeta.removeEnchant(Enchantment.LURE);
							item.setItemMeta(stickmeta);
							double health = p.getHealth() + 10;
							if (health > p.getMaxHealth()) {
								p.setHealth(p.getMaxHealth());
							} else {
								p.setHealth(health);
							}
							ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.HEART, 0.02, 1, 0);
							effect.sendToLocation(p.getLocation());
							Bukkit.getScheduler().scheduleSyncDelayedTask(
									plugin, new Runnable() {

										public void run() {
											// TODO Auto-generated method
											// stub
											if(Util.getKit(p) instanceof Zeus) {
												PlayerInventory inv = p.getInventory();
												inv.remove(item);
												stickmeta.addEnchant(Enchantment.LURE, 1, true);
												item.setItemMeta(stickmeta);
												inv.addItem(item);
											}
										}

									}, 20L * 30);
						}
					}
				}
			}
		}
	}
	public void spawnLightning(Player source, Location loc) {
		LightningStrike strike = source.getWorld()
				.strikeLightning(loc);
		strike.setCustomName(source.getUniqueId() + "");
	}
 	//extinguisher
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
	    if (event.getCause() == IgniteCause.LIGHTNING) {
	        event.setCancelled(true);
	    }
	}

	@EventHandler
	public void boltDamage(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		if(damager instanceof LightningStrike) {
			LightningStrike bolt = (LightningStrike) damager;
			UUID id = java.util.UUID.fromString(bolt.getCustomName());
			if(id != null) {
				Player p = Bukkit.getPlayer(id);
				if(p != null) {
					Entity victim = e.getEntity();
					if(victim instanceof LivingEntity) {
						if(p.getUniqueId() == victim.getUniqueId()) {
							e.setCancelled(true);
							return;
						}
						LivingEntity livingVictim = (LivingEntity) victim;
						livingVictim.damage(3, p);
					}
				}
			}
			
		}
	}

}

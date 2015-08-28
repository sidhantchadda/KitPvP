package Kits;

import me.sidhant.kitpvp.Main;
import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;


public class Frost extends Kit implements Listener{
	public static int UUID = 4;
	public static int HAIL_RADIUS = 6;
	Plugin plugin;
	public Frost(Plugin plugin) {
		id = UUID;
		price = 100;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		setMessage("You are now playing the Frost Kit, use snowballs to freeze enemies", ChatColor.GREEN, p);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		helmet.setItemMeta(setArmourColor(helmet.getItemMeta(), Color.WHITE));
		setHelmet(helmet, p);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.WHITE));
		setChestplate(chestplate, p);
		
		ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Leggings.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.WHITE));
		setLeggings(Leggings, p);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.setItemMeta(setArmourColor(boots.getItemMeta(), Color.WHITE));
		setBoots(boots, p);
		setWeapon(new ItemStack(Material.IRON_SWORD), p);
		p.setHealth(20);
		p.setFoodLevel(20);
		p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 10));
		p.setLevel(1);
	}
	
	@EventHandler
	public void snowball(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball) e.getDamager();
			if(snowball.getShooter() instanceof Player) {
				Player p = (Player) snowball.getShooter();
				if(Util.getKit(p) instanceof Frost) {
					if(e.getEntity() instanceof LivingEntity) {
						LivingEntity en = (LivingEntity) e.getEntity();
						if(en.getUniqueId() == p.getUniqueId()) {
							e.setCancelled(true);
							return;
						}
						en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 15));
						en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*3, 3));
						en.damage(4, p);
						en.sendMessage(ChatColor.RED+"You have been frosted by "+p.getName());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void Kill(PlayerDeathEvent e) {
		Player killer = e.getEntity().getKiller();
		if(killer == null) return;
		if (Util.getKit(killer) instanceof Frost) {
			killer.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 10));
		}
	}
	@EventHandler
	public void hail(PlayerToggleSneakEvent e) {
		if(e.isSneaking()) {
			final Player p = e.getPlayer();
			if(Util.getKit(p) instanceof Frost) {
				if(p.getLevel() >= 1) {
					p.sendMessage(ChatColor.DARK_AQUA+"Hail!");
					p.setLevel(0);
					Location loc = p.getLocation();
					p.playSound(loc, Sound.STEP_SNOW, 1F, 1F);
					ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.SNOWBALL, 0.02, 20, .2);
					effect.sendToLocation(loc);

					final Location air = new Location(loc.getWorld(), loc.getX(), loc.getY()+10, loc.getZ());
					new BukkitRunnable() {
			            int i = 0;
			            public void run() {
			            	i++;
			            	if(i >= 5){
			                    this.cancel();
			                } else {
			                	if(p != null) {
			                		for(int x = -HAIL_RADIUS; x<HAIL_RADIUS; x++) {
			                			for(int z = -HAIL_RADIUS; z<HAIL_RADIUS; z++) {
			                				Snowball snowball = Bukkit.getServer().getWorld(Main.worldid).spawn(new Location(air.getWorld(), air.getX()+x, air.getY(), air.getZ()+z), Snowball.class);
			                				snowball.setShooter(p);
			                			}
			                		}
			                	}
			                }
			            }
			        }.runTaskTimer(plugin, 0, 10);
			        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							if(p.isOnline()) {
								if(Util.getKit(p) instanceof Frost) {
									p.setLevel(1);
								}
							}
						}
			        	
			        },20L*10);
				}
				else {
					p.sendMessage(ChatColor.GRAY+"Hail is on Cooldown");
				}
			}
		}
	}
	
	@EventHandler
	public void hit(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Snowball) {
			Snowball snow = (Snowball) e.getEntity();
			if(snow.getShooter() instanceof Player) {
				Player p = (Player) snow.getShooter();
				if(Util.getKit(p) instanceof Frost) {
					final Block b = snow.getLocation().getBlock();
					if(b.getRelative(BlockFace.DOWN).getType() == Material.AIR) return;
					if(b.getType() == Material.STATIONARY_WATER) {
						b.setType(Material.ICE, false);
						BukkitScheduler scheduler = Bukkit.getScheduler();
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								if(b.getType() == Material.ICE) {
									b.setType(Material.STATIONARY_WATER, false);
								}
							}
							
						}, 20L*2);
					}
					if(b.getType() == Material.AIR) {
						b.setType(Material.SNOW, false);
						BukkitScheduler scheduler = Bukkit.getScheduler();
						scheduler.scheduleSyncDelayedTask(plugin,
								new Runnable() {

									public void run() {
										// TODO Auto-generated method stub
										b.setType(Material.AIR, false);
									}

								}, 20L * 2);
					}
				}
			}
		}
	}	
}

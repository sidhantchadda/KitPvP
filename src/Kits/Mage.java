package Kits;

import java.util.Arrays;

import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;
import me.sidhant.kitpvp.Vector3D;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Mage extends Kit implements Listener {
	public static String fireball = "Fireball";
	public static String ignite = "Ignite";
	public static String freeze = "Frost";
	public static String heal = "Heal";
	public static int UUID = 2;
	Plugin plugin;
	public Mage(Plugin plugin) {
		id = UUID;
		price = 50;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}

	@Override
	public void addPlayer(Player p) {
		// fillSoup(p);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		helmet.setItemMeta(setArmourColor(helmet.getItemMeta(), Color.FUCHSIA));
		setHelmet(helmet, p);
		
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		chestplate.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.TEAL));
		setChestplate(chestplate, p);
		
		ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		Leggings.setItemMeta(setArmourColor(chestplate.getItemMeta(), Color.TEAL));
		setLeggings(Leggings, p);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		boots.setItemMeta(setArmourColor(boots.getItemMeta(), Color.FUCHSIA));
		setBoots(boots, p);
		ItemStack stick = new ItemStack(Material.STICK);
		ItemMeta stickm = stick.getItemMeta();
		// shooter
		stickm.setDisplayName(fireball);
		stickm.setLore(Arrays.asList("A wand that shoots fireballs"));
		stickm.addEnchant(Enchantment.LURE, 1, true);
		stick.setItemMeta(stickm);
		setWeapon(stick, p);

		// ignite on
		stickm.setDisplayName(ignite);
		stickm.setLore(Arrays
				.asList("Point at a player and right click to set enemies on fire"));
		stickm.addEnchant(Enchantment.LURE, 1, true);
		stick.setItemMeta(stickm);
		p.getInventory().addItem(stick);

		// freeze
		stickm.setDisplayName(freeze);
		stickm.setLore(Arrays
				.asList("Point at a player and right clock to freeze enemies"));
		stickm.addEnchant(Enchantment.LURE, 1, true);
		stick.setItemMeta(stickm);
		p.getInventory().addItem(stick);

		// heal
		stickm.setDisplayName(heal);
		stickm.setLore(Arrays.asList("A wand that heals your life"));
		stickm.addEnchant(Enchantment.LURE, 1, true);
		stick.setItemMeta(stickm);
		p.getInventory().addItem(stick);

		fillSoup(p);
	}
	
	@EventHandler
	public void click(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (Util.getKit(p) instanceof Mage) {
			final ItemStack stickS = p.getItemInHand();
			final ItemMeta stickm = stickS.getItemMeta();
			if(stickS.getType() != Material.STICK) return;
			if(!stickm.hasDisplayName()) return;
			if (stickm.getDisplayName() == Mage.fireball) {
				if (stickm.hasEnchants()) {
					Fireball fb =p.launchProjectile(Fireball.class);
					fb.setVelocity(fb.getDirection().multiply(15));
					fb.setYield(2);
					stickm.removeEnchant(Enchantment.LURE);
					stickS.setItemMeta(stickm);

					BukkitScheduler scheduler = Bukkit.getServer()
							.getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							if(Util.getKit(p) instanceof Mage) {
								PlayerInventory inv = p.getInventory();
								inv.remove(stickS);
								stickm.addEnchant(Enchantment.LURE, 1, true);
								stickS.setItemMeta(stickm);
								inv.addItem(stickS);
							}
						}

					}, 20 * 4L);
				} else {
					p.sendMessage(ChatColor.GRAY + Mage.fireball
							+ ": has a four second cooldown");
				}
			}
			// code for ignite
			if (stickm.getDisplayName() == Mage.ignite) {
				if (stickm.hasEnchants()) {
					Entity looking = getEntityInSight(p);
					
					if(looking == null) {
						p.sendMessage(ChatColor.GRAY+"Miss! aim for the head");
						return;
					}
					looking.setFireTicks(20 * 10);
					if(looking instanceof Player) {
						looking = (Player) looking;
						((Player) looking).playSound(p.getLocation(), Sound.BLAZE_BREATH, 1F, 1F);
					}
					stickm.removeEnchant(Enchantment.LURE);
					stickS.setItemMeta(stickm);
					
					BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							if(Util.getKit(p) instanceof Mage) {
								PlayerInventory inv = p.getInventory();
								inv.remove(stickS);
								stickm.addEnchant(Enchantment.LURE, 1, true);
								stickS.setItemMeta(stickm);
								inv.addItem(stickS);
							}
						}
					}, 20 * 10L);
				} else {
					p.sendMessage(ChatColor.GRAY + Mage.ignite
							+ ": has a 10 second cooldown");
				}
			}
			//code for freeze
			if(stickm.getDisplayName() == Mage.freeze) {
				if(stickm.hasEnchants()) {
					Entity looking = getEntityInSight(p);
					if(looking == null) {
						p.sendMessage(ChatColor.GRAY+"Miss!");
						return;
					}
					if(looking instanceof LivingEntity) {
						looking = (LivingEntity) looking;
						((LivingEntity) looking).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5, 1));
						looking.sendMessage(ChatColor.RED+"You have been slowed by "+p.getName());
						p.sendMessage(ChatColor.AQUA+"You slowed "+looking.getName());
						
						stickm.removeEnchant(Enchantment.LURE);
						stickS.setItemMeta(stickm);
						
						BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								if(Util.getKit(p) instanceof Mage) {
									PlayerInventory inv = p.getInventory();
									inv.remove(stickS);
									stickm.addEnchant(Enchantment.LURE, 1, true);
									stickS.setItemMeta(stickm);
									inv.addItem(stickS);
								}
							}
							
						}, 20 *10L);
					}
					
				}
				else {
					p.sendMessage(ChatColor.GRAY+Mage.freeze+": has a 10 second cooldown");
				}
			}
			
			//code for heal
			if(stickm.getDisplayName() == Mage.heal) {
				if(stickm.hasEnchants()) {
					double health = p.getHealth()+10;
					if(health > p.getMaxHealth()) {
						p.setHealth(p.getMaxHealth());
					}
					else {
						p.setHealth(health);
					}
					ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.HEART, 0.02, 1, 0);
					effect.sendToLocation(p.getLocation());
					stickm.removeEnchant(Enchantment.LURE);
					stickS.setItemMeta(stickm);
					
					BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							if(Util.getKit(p) instanceof Mage) {
								PlayerInventory inv = p.getInventory();
								inv.remove(stickS);
								stickm.addEnchant(Enchantment.LURE, 1, true);
								stickS.setItemMeta(stickm);
								inv.addItem(stickS);
							}
						}
						
					}, 35*20L);
				}
				else {
					p.sendMessage(ChatColor.GRAY+Mage.heal+": has a 35 second cooldown");
				}
			}
		}
	}
	@EventHandler
	public void fireballDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			LivingEntity en = (LivingEntity) e.getEntity();
			if(e.getDamager() instanceof Fireball) {
				Fireball fb= (Fireball) e.getDamager();
				Player shooter = (Player) fb.getShooter();
				if(Util.getKit(shooter) instanceof Mage) {
					en.damage(4, shooter);
				}
			}
		}
	}
	

	private Entity getEntityInSight(Player player){
        Location playerLoc = player.getLocation();
        Vector3D playerDirection = new Vector3D(playerLoc.getDirection());
        Vector3D start = new Vector3D(playerLoc);
        int scanDistance = 20;
        Vector3D end = start.add(playerDirection.multiply(scanDistance));
        Entity inSight = null;
        
 
        for(Entity nearbyEntity : player.getNearbyEntities(scanDistance, scanDistance, scanDistance)){
            Vector3D nearbyLoc = new Vector3D(nearbyEntity.getLocation());
 
            //Bounding box
            Vector3D min = nearbyLoc.subtract(0.5D, 0, 0.5D);
            Vector3D max = nearbyLoc.add(0.5D, 1.67D, 0.5D);
 
            if(hasIntersection(start, end, min, max)){
                if(inSight == null || inSight.getLocation().distanceSquared(playerLoc) > nearbyEntity.getLocation().distanceSquared(playerLoc)){
                    inSight = nearbyEntity;
                }
            }
        }
 
        return inSight;
    }
 
    private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
        final double epsilon = 0.0001f;
 
        Vector3D d = p2.subtract(p1).multiply(0.5);
        Vector3D e = max.subtract(min).multiply(0.5);
        Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
        Vector3D ad = d.abs();
 
        if (Math.abs(c.x) > e.x + ad.x)
            return false;
        if (Math.abs(c.y) > e.y + ad.y)
            return false;
        if (Math.abs(c.z) > e.z + ad.z)
            return false;
 
        if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
            return false;
        if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
            return false;
        if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
            return false;
 
        return true;
    }
}

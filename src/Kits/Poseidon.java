package Kits;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Poseidon extends Kit implements Listener{
	public static int UUID = 9;
	Plugin plugin;
	HashMap<UUID, Block> map;
	public Poseidon(Plugin plugin) {
		id = UUID;
		price = 75;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
		map = new HashMap<UUID, Block>();
	}
	@Override
	public void addPlayer(Player p) {
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta helmetMeta = setArmourColor(helmet.getItemMeta(), Color.BLUE);
		helmetMeta.addEnchant(Enchantment.OXYGEN, 3, true);
		helmet.setItemMeta(helmetMeta);
		setHelmet(helmet, p);
		setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE), p);
		setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS), p);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta bootsMeta = setArmourColor(boots.getItemMeta(), Color.BLUE);
		//bootsMeta.addEnchant(Enchantment.DEPTH_STRIDER, 3, true);
		// can't add depth strider because No cheat plus blocks it atm.
		bootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 3, true);
		boots.setItemMeta(bootsMeta);
		setBoots(boots, p);
		ItemStack sword = new ItemStack(Material.GOLD_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.BLUE+ "Trident");
		swordMeta.setLore(Arrays.asList("Crouch in water to activate!"));
		swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		swordMeta.addEnchant(Enchantment.DURABILITY, 5, true);
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		p.setLevel(1);
		fillSoup(p);
	}
	
	@EventHandler
	public void crouch(PlayerToggleSneakEvent e) {
		if(e.isSneaking()) {
			final Player p = e.getPlayer();
			if(Util.getKit(p) instanceof Poseidon) {
				if(p.getLevel() >= 1) {
					if(p.getLocation().getBlock().getType() == Material.WATER || p.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
						p.setLevel(0);
						Vector v= p.getLocation().getDirection();
						p.setVelocity(new Vector(0, v.getY()+4, 0));
						p.setAllowFlight(true);
						p.setFlying(true);
						p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*5, 0));
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*5, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 0));
						p.playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 1F, 1F);
						p.sendMessage(ChatColor.GREEN+"Poseidon pounce!");
						BukkitScheduler scheduler = Bukkit.getScheduler();
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								if(Util.getKit(p) instanceof Poseidon) {
									p.setFlying(false);
									p.setAllowFlight(false);
								}
							}
							
						}, 20*3L);
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								if(Util.getKit(p) instanceof Poseidon) {
									p.setLevel(1);
								}
							}
						}, 20*10);
						/*p.setLevel(0);
						WaterSprite(p);
						BukkitScheduler  scheduler = Bukkit.getScheduler();
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								if(Util.getKit(p) instanceof Poseidon) {
									p.setLevel(1);
								}
							}
						}, 20* 7);*/
					}
				}
				else {
					p.sendMessage(ChatColor.GRAY+"Poseidon pounce is recharging please wait 10 seconds.");
				}
			}
		}
	}
	/*private void WaterSprite(Player p) {
		// TODO Auto-generated method stub
		p.setVelocity(new Vector(p.getLocation().getDirection().multiply(2.7)
				.getX(),
				p.getLocation().getDirection().multiply(1.5).getY() + .7, p
						.getLocation().getDirection().multiply(2.7).getZ()));
		p.playSound(p.getLocation(), Sound.ZOMBIE_REMEDY, 1F, 1F);
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*5, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*5, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*5, 0));
		p.sendMessage(ChatColor.BLUE + "Poseidon pounce!");
	}*/
	@EventHandler
	public void move(PlayerMoveEvent e) {
		if(Util.getKit(e.getPlayer()) instanceof Poseidon) {
			if(e.getPlayer().getAllowFlight() == true) {
				final Player p = e.getPlayer();
				final Block b = p.getLocation().getBlock();
				if(b.getType() == Material.AIR) {
					b.setType(Material.STATIONARY_WATER, false);
					ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.WATER_BUBBLE, 0.02, 10, .5);
					ParticleEffect effect2 = new ParticleEffect(ParticleEffect.ParticleType.WATER_DROP, 0.02, 10, .5);
					ParticleEffect effect3 = new ParticleEffect(ParticleEffect.ParticleType.DRIP_WATER, 0.02, 10, .5);
					ParticleEffect effect4 = new ParticleEffect(ParticleEffect.ParticleType.WATER_WAKE, 0.02, 10, .5);


					effect.sendToLocation(p.getLocation());
					effect2.sendToLocation(p.getLocation());
					effect3.sendToLocation(p.getLocation());
					effect4.sendToLocation(p.getLocation());
					BukkitScheduler scheduler = Bukkit.getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							if(b.getType() == Material.STATIONARY_WATER) {
								b.setType(Material.AIR, false);
							}
						}
					}, 20L*2);
				}
			}
		}
	}
	@EventHandler
	public void fall(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Util.getKit(p) instanceof Poseidon) {
				if(e.getCause() == DamageCause.FALL) e.setCancelled(true);
			}
		}
	}

}

package Kits;

import java.util.Arrays;
import java.util.List;

import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Elemental extends Kit implements Listener{
	public static int UUID = 3;
	private Plugin plugin;
	public Elemental(Plugin plugin) {
		id = UUID;
		price = 100;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		fillSoup(p);
		p.setLevel(1);
		setMessage("You are playing elemental", ChatColor.GREEN, p);
		setHelmet(new ItemStack(Material.CHAINMAIL_HELMET), p);
		setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE), p);
		setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS), p);
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
		boots.setItemMeta(bootsMeta);
		setBoots(boots, p);
		ItemStack sword = new ItemStack(Material.GOLD_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName("Elemental Weapon");
		swordMeta.addEnchant(Enchantment.DURABILITY, 5, true);
		swordMeta.setLore(Arrays.asList("Crouch to activate elemental powers!"));
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
	}
	
	@EventHandler
	public void shift(PlayerToggleSneakEvent e) {
		final Player p = e.getPlayer();
		if (!(Util.getKit(p) instanceof Elemental))
			return;
		if (e.isSneaking()) {
			if (p.getLevel() < 1) {
				p.sendMessage(ChatColor.GRAY
						+ "Sprits are recharging powers please wait 4 seconds");
				return;
			}
			Block b = p.getLocation().getBlock();
			if (b.getType().equals(Material.WATER)
					|| b.getType().equals(Material.STATIONARY_WATER)) {
				WaterSprite(p);
			} else if (b.getType().equals(Material.LAVA)
					|| b.getType().equals(Material.STATIONARY_LAVA)) {
				LavaSprite(p);
			} else if (b.getRelative(BlockFace.DOWN).getType()
					.equals(Material.AIR)
					|| b.getRelative(BlockFace.DOWN).getType()
							.equals(Material.LONG_GRASS)) {
				Speed(p);
			} else if (b.getRelative(BlockFace.DOWN).getType()
					.equals(Material.LEAVES)
					|| b.getRelative(BlockFace.DOWN).getType()
							.equals(Material.LEAVES_2)) {
				Heal(p);
			} else {
				smash(p);
			}
			p.setLevel(0);
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					p.setLevel(1);
				}

			}, 4 * 20L);

		}
	}

	private void Speed(Player p) {
		// TODO Auto-generated method stub
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 6, 1),
				true);
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*6, 1));
		p.playSound(p.getLocation(), Sound.HORSE_GALLOP, 1F, 1F);
		p.sendMessage(ChatColor.GREEN + "Speed Spirit!");

	}

	private void smash(Player p) {
		// TODO Auto-generated method stub
		List<Entity> entities = p.getNearbyEntities(4, 3, 4);
		for (Entity en : entities) {
			if (en instanceof LivingEntity) {
				LivingEntity ent = (LivingEntity) en;
				ent.damage(7, p);
			}
		}
		p.playSound(p.getLocation(), Sound.ZOMBIE_WOODBREAK, 1F, 1F);
		p.sendMessage(ChatColor.GREEN + "Earth Pound!");
		ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.EXPLOSION_NORMAL, .2, 20, .2);
		effect.sendToLocation(p.getLocation());
	}

	private void Heal(Player p) {
		// TODO Auto-generated method stub
		double health = p.getHealth() + 8;
		if (health > p.getMaxHealth()) {
			p.setHealth(p.getMaxHealth());
		} else {
			p.setHealth(health);
		}
		p.playSound(p.getLocation(), Sound.HORSE_SOFT, 2F, 1F);
		p.sendMessage(ChatColor.GREEN + "Tree of Life!");
		ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.HEART, 0.02, 1, 0);
		effect.sendToLocation(p.getLocation());
	}

	private void LavaSprite(Player p) {
		// TODO Auto-generated method stub
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
				20 * 30, 0, true));
		p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 1F, 1F);
		p.sendMessage(ChatColor.GREEN + "Fire Sprite");
	}
	
	private void WaterSprite(Player p) {
		// TODO Auto-generated method stub
		p.setVelocity(new Vector(p.getLocation().getDirection().multiply(2.7)
				.getX(),
				p.getLocation().getDirection().multiply(1.5).getY() + .7, p
						.getLocation().getDirection().multiply(2.7).getZ()));
		p.playSound(p.getLocation(), Sound.SPLASH, 1F, 1F);
		p.sendMessage(ChatColor.GREEN + "Water Sprite!");
	}

	@EventHandler
	public void lavaDamage(EntityDamageEvent e) {
		Entity en = e.getEntity();
		if (!(en instanceof Player))
			return;
		Player p = (Player) en;
		if (Util.getKit(p) instanceof Elemental) {
			if (e.getCause().equals(DamageCause.LAVA)) {
				e.setCancelled(true);
				p.setFireTicks(0);
			}
			if (e.getCause().equals(DamageCause.FIRE_TICK)) {
				e.setCancelled(true);
				p.setFireTicks(0);
			}
			if (e.getCause().equals(DamageCause.FIRE)) {
				e.setCancelled(true);
				p.setFireTicks(0);
			}

		}
	}

}

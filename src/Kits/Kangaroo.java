package Kits;

import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;


public class Kangaroo extends Kit implements Listener{
	public static int UUID = 6;
	Plugin plugin;
	public Kangaroo(Plugin plugin) {
		id = UUID;
		price = 75;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.setLevel(1);
		setMessage("You are now playing Kangaroo double jump to jump high!", ChatColor.GREEN, p);
		setHelmet(new ItemStack(Material.IRON_HELMET), p);
		setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE), p);
		setLeggings(new ItemStack(Material.IRON_LEGGINGS), p);
		ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 20, true);
		boots.setItemMeta(bootsMeta);
		setBoots(boots, p);
		setWeapon(new ItemStack(Material.IRON_SWORD), p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
		p.setAllowFlight(true);
	}
	
	@EventHandler
	public void doublejump(PlayerToggleFlightEvent e) {
		final Player p = e.getPlayer();
		if(Util.getKit(p) instanceof Kangaroo) {
			if(p.getLevel()>= 1) {
				p.setLevel(0);
				p.setFlying(false);
				p.setAllowFlight(false);
				e.setCancelled(true);
				p.setVelocity(new Vector(p.getLocation().getDirection().multiply(1)
						.getX(),
						p.getLocation().getDirection().multiply(1.5).getY() + 2, p
								.getLocation().getDirection().multiply(1).getZ()));
				p.setFallDistance(0);
				BukkitScheduler scheduler = Bukkit.getScheduler();
				scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

					public void run() {
						// TODO Auto-generated method stub
						p.setLevel(1);
						p.setAllowFlight(true);
						p.sendMessage(ChatColor.GRAY+"High Jump cooldown: 7 seconds");
					}
					
				}, 7 *20L);
			}
		}
	}
	@EventHandler
	public void fall(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Util.getKit(p) instanceof Kangaroo) {
				if(e.getCause() == DamageCause.FALL) e.setCancelled(true);
			}
		}
	}

}

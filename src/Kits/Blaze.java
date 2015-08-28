package Kits;

import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Blaze extends Kit implements Listener {
	public static int UUID = 7;
	Plugin plugin;
	public Blaze(Plugin plugin) {
		this.plugin = plugin;
		id = UUID;
		price = 50;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
	}
	@Override
	public void addPlayer(Player p) {
		p.setLevel(1);
		setMessage("You are now playing the Blaze Kit heat is your friend", ChatColor.GREEN, p);
		setHelmet(new ItemStack(Material.GOLD_HELMET), p);
		setChestplate(new ItemStack(Material.GOLD_CHESTPLATE), p);
		setLeggings(new ItemStack(Material.GOLD_LEGGINGS), p);
		setBoots(new ItemStack(Material.GOLD_BOOTS), p);
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();	
		bowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
		bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		bow.setItemMeta(bowMeta);
		setWeapon(bow, p);
		p.getInventory().addItem(new ItemStack(Material.ARROW));
		fillSoup(p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0));
		p.setLevel(1);
	}
	
	@EventHandler
	public void Move(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if(Util.getKit(p) instanceof Blaze) {
			Material mat = p.getLocation().getBlock().getType();
			if(mat.equals(Material.STATIONARY_LAVA) || mat.equals(Material.LAVA)) {
				if(p.getLevel() >= 1) {
					p.setLevel(0);
					double newHealth = p.getHealth() + 1;
					if(newHealth > p.getMaxHealth()) {
						p.setHealth(p.getMaxHealth());
					}
					else {
						p.setHealth(newHealth);
					}
					BukkitScheduler scheduler = Bukkit.getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable()  {

						public void run() {
							// TODO Auto-generated method stub
							p.setLevel(1);
						}
						
					}, 20L *1);
				}
			}
		}
	}
	@EventHandler
	public void lavaDamage(EntityDamageEvent e) {
		Entity en = e.getEntity();
		if (!(en instanceof Player))
			return;
		Player p = (Player) en;
		if (Util.getKit(p) instanceof Blaze) {
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

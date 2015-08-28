package Kits;

import java.util.Arrays;

import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Blink extends Kit implements Listener{
	public static int UUID = 15;
	Plugin plugin;
	public Blink(Plugin plugin) {
		id = UUID;
		price = 50;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
	}
	@Override
	public void addPlayer(Player p) {
		ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
		//helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		setHelmet(helmet, p);
		
		ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		//chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		setChestplate(chestplate, p);
		
		ItemStack Leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
		//Leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		setLeggings(Leggings, p);
		
		ItemStack Boots = new ItemStack(Material.CHAINMAIL_BOOTS);
		//Boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		//Boots.addEnchantment(Enchantment.PROTECTION_FALL, 1);
		setBoots(Boots, p);
		
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.AQUA+"Blink Strike!");
		swordMeta.setLore(Arrays.asList("Press Shift to activate"));
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		p.setLevel(1);
	}
	
	@EventHandler
	public void Blinkme(PlayerToggleSneakEvent e) {
		if(e.isSneaking()) {
			final Player p = e.getPlayer();
			if(Util.getKit(p) instanceof Blink) {
				if(p.getLevel() >= 1) {
					@SuppressWarnings("deprecation")
					Block b = p.getTargetBlock(null, 50);
					if(b != null) {
						Location loc = b.getLocation();
						loc.setY(loc.getY()+1);
						Location playerloc = p.getLocation();
						ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.PORTAL, 0.02, 20, .2);
						effect.sendToLocation(loc);
						effect.sendToLocation(playerloc);
						loc.setYaw(playerloc.getYaw());
						p.teleport(loc);
						p.setLevel(0);
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20*3, 1));
						BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								p.setLevel(1);
							}
							
						}, 20L*5);
					}
					else {
						p.sendMessage(ChatColor.GRAY+"Can't teleport here!");
					}
				}
				else {
					p.sendMessage(ChatColor.GRAY+"BlinkStrike has a cooldown of 5 seconds!");
				}
			}
		}
	}
}

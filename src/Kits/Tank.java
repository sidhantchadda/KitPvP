package Kits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.sidhant.kitpvp.ParticleEffect;
import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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

public class Tank extends Kit implements Listener {
	public static int UUID = 14;
	Plugin plugin;
	Map<UUID, List<BlockState>> blockstates;
	public Tank(Plugin plugin) {
		id = UUID;
		price = 100;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
		this.plugin = plugin;
		blockstates = new HashMap<UUID, List<BlockState>>();
	}
	@Override
	public void addPlayer(Player p) {
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		//helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		setHelmet(helmet, p);
		
		ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
		//chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		setChestplate(chestplate, p);
		
		ItemStack Leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
		//Leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		setLeggings(Leggings, p);
		
		ItemStack Boots = new ItemStack(Material.DIAMOND_BOOTS);
		//Boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		//Boots.addEnchantment(Enchantment.PROTECTION_FALL, 1);
		setBoots(Boots, p);
		
		ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.BLUE+ "Defeneders Sword");
		swordMeta.setLore(Arrays.asList("Press Crouch to charge!"));	
		//swordMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 5));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2));
		p.setHealth(20);
		p.setLevel(1);
		fillSoup(p);
		
	}
	
	@EventHandler
	public void crouch(PlayerToggleSneakEvent e) {
		if (Util.getKit(e.getPlayer()) instanceof Tank) {
			if (e.isSneaking()) {
				final Player p = e.getPlayer();
				if(p.getLevel() >= 1) {
//					p.setVelocity(new Vector(p.getLocation().getDirection().multiply(9)
//							.getX(),
//							p.getLocation().getDirection().multiply(1).getY() + .75, p
//									.getLocation().getDirection().multiply(9).getZ()));
					
					generateWall(p, Material.MOSSY_COBBLESTONE);
					ParticleEffect effect = new ParticleEffect(ParticleEffect.ParticleType.SMOKE_LARGE, 0.02, 20, 1);
					effect.sendToLocation(p.getLocation());
					p.setLevel(0);
					p.playSound(p.getLocation(), Sound.ZOMBIE_METAL, 1F, 1F);
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 30, 1));
					p.sendMessage(ChatColor.GREEN+"Sheild!");
					BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							if(Util.getKit(p) instanceof Tank) {
								p.setLevel(1);
							}
							
						}
						
					}, 20*7);
				}
				else {
					p.sendMessage(ChatColor.GRAY+"Shield has a 7 second cooldown");
				}
			}
		}
	}
	public void generateWall(final Player player, Material mat) {
		UUID id = player.getUniqueId();
		List<BlockState> list= blockstates.get(id);
		if(list == null) {
			list = new ArrayList<BlockState>();
		}
		for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(+ 2, + i, 0).getBlock();
            if(block.getType() == Material.AIR) {
                list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            //Location location = new Location(player.getWorld(), player.getLocation().getBlockX() + 2, player.getLocation().getBlockY() + i, player.getLocation().getBlockZ() + 1);
            Block block = player.getLocation().add(+ 2, + i, + 1).getBlock();
            if(block.getType() == Material.AIR) {
                list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(+ 2, i, - 1).getBlock();
            if(block.getType() == Material.AIR) {
                list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(- 2, + i, 0).getBlock();
            if(block.getType() == Material.AIR) {
                list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(- 2, + i, + 1).getBlock();
            if(block.getType() == Material.AIR) {
                list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(- 2, + i, - 1).getBlock();
            if(block.getType() == Material.AIR) {
                list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(0, + i, + 2).getBlock();
            if(block.getType() == Material.AIR) {
            	list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(+ 1, + i, + 2).getBlock();
            if(block.getType() == Material.AIR) {
            	list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(- 1, + i, + 2).getBlock();
            if(block.getType() == Material.AIR) {
            	list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(0, + i, - 2).getBlock();
            if(block.getType() == Material.AIR) {
            	list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(+ 1, + i, - 2).getBlock();
            if(block.getType() == Material.AIR) {
            	list.add(block.getState());
                block.setType(mat, false);
            }
        }
     
        for (int i = 0; i < 3; i++) {
            Block block = player.getLocation().add(- 1, i, - 2).getBlock();
            if(block.getType() == Material.AIR) {
            	list.add(block.getState());
                block.setType(mat, false);
            }
        }
        blockstates.put(id, list);
        BukkitScheduler scheuduler = Bukkit.getServer().getScheduler();
        scheuduler.scheduleSyncDelayedTask(plugin, new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				List<BlockState> list = blockstates.get(player.getUniqueId());
				for (int i = 0; i < list.size(); i++) {
                    BlockState state = list.get(i);
                    state.getBlock().setType(Material.AIR, false);
                }
				list.clear();
				list = null;
				blockstates.put(player.getUniqueId(), list);
			}
        	
        }, 20*3L);
        
	}

}

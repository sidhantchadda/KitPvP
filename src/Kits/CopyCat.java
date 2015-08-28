package Kits;

import java.util.Arrays;

import me.sidhant.kitpvp.Util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CopyCat extends Kit implements Listener{
	public static int UUID = 10;
	public CopyCat() {
		id = UUID;
		price = 25;
		Util.addPrices(id, price);
		Util.addSlot(this, id);
	}
	@Override
	public void addPlayer(Player p) {
		setHelmet(new ItemStack(Material.IRON_HELMET), p);
		setChestplate(new ItemStack(Material.IRON_CHESTPLATE), p);
		setLeggings(new ItemStack(Material.IRON_LEGGINGS), p);
		setBoots(new ItemStack(Material.IRON_BOOTS), p);
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.setDisplayName(ChatColor.YELLOW+"Copy Cat");
		swordMeta.setLore(Arrays.asList("Get a kill on an enemy to take their kit"));
		sword.setItemMeta(swordMeta);
		setWeapon(sword, p);
		fillSoup(p);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void die(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player killer = p.getKiller();
		if (killer != null) {
			if (Util.getKit(killer) instanceof CopyCat) {
				if (p.getUniqueId() == killer.getUniqueId())
					return;
				killer.playSound(p.getLocation(), Sound.DONKEY_DEATH, 1F, 1F);
				killer.sendMessage(ChatColor.GREEN+"Copy Cat!");
				Util.clearInventory(p);
				Util.addKitSelected(killer, Util.getKitSelected(p));
				Util.getKit(killer).addPlayer(killer);
				Util.JoinLobby(killer);
			}
		}
	}
}

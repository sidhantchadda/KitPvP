package me.sidhant.kitpvp;


import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventorySelector implements Listener {

	public InventorySelector() {

	}

	@EventHandler
	public void select(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if (he instanceof Player) {
			Player p = (Player) he;
			if (Util.hasPlayerinLobby(p)) {
				Integer slot = e.getSlot();
				if (slot == null)
					return;
				if(e.getCurrentItem() == null) return;
				if(e.getClickedInventory() == null) return;
				ItemStack item = e.getInventory().getItem(slot);
				if (item == null)
					return;
				if (item.getType() == Material.AIR
						|| item.getType() == Material.EMERALD)
					return;
				if(item.getType() == Material.EMERALD_BLOCK) {
					Integer id = Integer.parseInt(item.getItemMeta().getLore().get(1));
					if(id == null) return;
					if(Config.getTokens(p.getName()) >= Util.getPriceById(id)) {
						Config.buyKit(p.getName(), Util.getPriceById(id), id);
						p.sendMessage(ChatColor.GREEN+"You bought a kit!");
						Util.addKitSelected(p, id);
					}
					else {
						p.sendMessage(ChatColor.RED+"You have insufficent funds");
					}
					p.closeInventory();
					p.openInventory(Util.getInventory(p));
					e.setCancelled(true);
					return;
				}
				if(item.getType() == Material.REDSTONE_BLOCK) {
					p.sendMessage("You didnt buy that kit");
					p.closeInventory();
					p.openInventory(Util.getInventory(p));
				}
				else if (Config.hasKit(p.getName(), slot)) {
					Util.addKitSelected(p, slot);
					p.sendMessage(ChatColor.GREEN
							+ "You have chosen your kit, go outside of spawn to start!");
					p.closeInventory();
				}
				else {
					p.sendMessage("You dont own that kit");
					Inventory inv = Bukkit.createInventory(null, 9);
					ItemStack yes = new ItemStack(Material.EMERALD_BLOCK);
					ItemMeta yesMeta = yes.getItemMeta();
					yesMeta.setDisplayName("Buy Kit!");
					yesMeta.setLore(Arrays.asList("Costs: "+Util.getPriceById(e.getSlot()),e.getSlot()+""));
					yes.setItemMeta(yesMeta);
					
					ItemStack no = new ItemStack(Material.REDSTONE_BLOCK);
					ItemMeta noMeta = no.getItemMeta();
					noMeta.setDisplayName("No buy later!");
					no.setItemMeta(noMeta);
					inv.addItem(yes);
					inv.setItem(8, no);
					p.openInventory(inv);

				}
				e.setCancelled(true);
			}
		}
	}

	/*
	 * private void click(Player p) { Util.clearInventory(p);
	 * Util.removePlayerfromLobby(p); }
	 */
}

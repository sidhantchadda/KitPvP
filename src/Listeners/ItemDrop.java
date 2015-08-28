package Listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDrop implements Listener{
	//prevents players from dropping items
	@EventHandler
	public void dropped(PlayerDropItemEvent e)	{
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		e.getItemDrop().remove();
		e.setCancelled(true);
	}
	@EventHandler
	public void InventoryChange(InventoryClickEvent event)	{
		if(event.getSlotType() == SlotType.ARMOR)	{
			event.setCancelled(true);
		}
	}
}

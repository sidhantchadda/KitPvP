package Listeners;

import me.sidhant.kitpvp.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestClick implements Listener {
	@EventHandler
	public void click(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			if(Util.hasPlayerinLobby(p)) {
				if(p.getInventory().getHeldItemSlot() == 0) {
					p.openInventory(Util.getInventory(p));
				}
			}
		}
	}

}

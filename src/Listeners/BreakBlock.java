package Listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlock implements Listener {
	@EventHandler
	public void BlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p == null) return;
		if(p.getGameMode().equals(GameMode.CREATIVE)) return;
		e.setCancelled(true);
	}
}

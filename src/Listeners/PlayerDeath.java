package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener{
	@EventHandler
	public void death(PlayerDeathEvent e) {
		e.setDeathMessage("");
		e.getDrops().clear();
	}
}

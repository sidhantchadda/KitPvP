package Listeners;

import me.sidhant.kitpvp.Util;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void respawn(PlayerRespawnEvent e) {
		Util.JoinLobby(e.getPlayer());
	}
}

package Listeners;

import me.sidhant.kitpvp.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageInSpawn implements Listener {
	@EventHandler
	public void Damaged(EntityDamageEvent  e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Util.hasPlayerinLobby(p)) {
				e.setCancelled(true);
			}
		}
	}

}

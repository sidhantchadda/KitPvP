package Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {
	@EventHandler
	public void fireballExplode(EntityExplodeEvent event) {
		Entity ent = event.getEntity();
		if (ent instanceof Fireball) {
			((Fireball) ent).setIsIncendiary(false);
			event.setCancelled(true);
		}
		if (ent.getType() == EntityType.PRIMED_TNT) {
			event.setCancelled(true);
		}
	}
}

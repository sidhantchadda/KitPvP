package Listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileRemove implements Listener {
	@EventHandler
	public void hit(ProjectileHitEvent e) {
		Projectile projectile = e.getEntity();
		if(projectile instanceof Arrow) {
			projectile.remove();
		}
	}

}

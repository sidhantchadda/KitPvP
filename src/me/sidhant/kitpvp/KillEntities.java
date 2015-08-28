package me.sidhant.kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class KillEntities {
	public KillEntities() {
		for(Entity e : Bukkit.getServer().getWorld(Main.worldName).getEntities()){
			e.remove();
		}
	}
}

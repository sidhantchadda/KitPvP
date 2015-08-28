package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodDrop implements Listener{
	@EventHandler
	public void fixhunger(FoodLevelChangeEvent e)	{
		e.setCancelled(true);
	}
}

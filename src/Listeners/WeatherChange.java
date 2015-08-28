package Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {
	@EventHandler
	public void Weather(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

}

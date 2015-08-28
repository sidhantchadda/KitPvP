package me.sidhant.kitpvp;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Admins.AdminCommand;
import Kits.Archer;
import Kits.BasicPvp;
import Kits.Blaze;
import Kits.Blink;
import Kits.Bomber;
import Kits.Bounce;
import Kits.CopyCat;
import Kits.Elemental;
import Kits.Flash;
import Kits.Frost;
import Kits.Gambler;
import Kits.Ghost;
import Kits.Kangaroo;
import Kits.Mage;
import Kits.Poseidon;
import Kits.Tank;
import Kits.Vampire;
import Kits.Zeus;
import Listeners.BreakBlock;
import Listeners.ChestClick;
import Listeners.DamageInSpawn;
import Listeners.DamageStore;
import Listeners.EntityExplode;
import Listeners.FoodDrop;
import Listeners.InventorySelector;
import Listeners.ItemDrop;
import Listeners.PlayerDeath;
import Listeners.PlayerLogin;
import Listeners.PlayerRespawn;
import Listeners.ProjectileRemove;
import Listeners.SpawnLeave;
import Listeners.WeatherChange;

public class Main extends JavaPlugin {
	public static String worldName = "world";
	public static UUID worldid;
	@Override
	public void onEnable()	{
		getServer().getLogger().info("Kitpvp has run!");
		worldid = Bukkit.getWorld("world").getUID();
		new KillEntities();
		PluginManager pm = getServer().getPluginManager();
		addKits(pm);
		pm.registerEvents(new PlayerLogin(this), this);
		pm.registerEvents(new ItemDrop(), this);
		pm.registerEvents(new FoodDrop(), this);
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerRespawn(), this);
		pm.registerEvents(new EntityExplode(), this);
		pm.registerEvents(new WeatherChange(), this);
		pm.registerEvents(new BreakBlock(), this);
		pm.registerEvents(new SpawnLeave(), this);
		pm.registerEvents(new DamageInSpawn(), this);
		pm.registerEvents(new ChestClick(), this);
		pm.registerEvents(new Villagers(), this);
		pm.registerEvents(new DamageStore(this), this);
		pm.registerEvents(new InventorySelector(), this);
		pm.registerEvents(new ProjectileRemove(), this);
		new Config(this);
		this.getCommand("tokens").setExecutor(new BuyCraftCommands());
		this.getCommand("admin").setExecutor(new AdminCommand());
	}
	
	@Override
	public void onDisable()	{
		saveConfig();
	}
	
	public void addKits(PluginManager pm) {
		
		pm.registerEvents(new Archer(this), this);
		pm.registerEvents(new BasicPvp(), this);
		pm.registerEvents(new Blaze(this), this);
		pm.registerEvents(new Elemental(this), this);
		pm.registerEvents(new Kangaroo(this), this);
		pm.registerEvents(new Frost(this), this);
		pm.registerEvents(new Ghost(this), this);
		pm.registerEvents(new Gambler(this), this);
		pm.registerEvents(new Mage(this), this);
		pm.registerEvents(new Bomber(this), this);
		pm.registerEvents(new Poseidon(this), this);
		pm.registerEvents(new CopyCat(), this);
		new Flash();
		pm.registerEvents(new Vampire(this), this);
		pm.registerEvents(new Tank(this), this);
		pm.registerEvents(new Blink(this), this);
		pm.registerEvents(new Bounce(this), this);
		pm.registerEvents(new Zeus(this), this);
	}
	

}

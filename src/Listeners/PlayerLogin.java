package Listeners;

import java.util.Random;

import me.sidhant.kitpvp.Config;
import me.sidhant.kitpvp.Titles;
import me.sidhant.kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import Admins.Hero;
import Admins.Legend;
import Admins.owner;

public class PlayerLogin implements Listener {
	Plugin plugin;
	public PlayerLogin(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		p.teleport(p.getWorld().getSpawnLocation());
		Config.ReloadScoreBoard(p);
		Util.JoinLobby(p);
		Titles.sendTitle(p, 20, 20 * 4, 20, ChatColor.YELLOW
				+ "Welcome to Kit Pvp!", ChatColor.AQUA
				+ "Right click your chest to get started");
		String admin = Config.getAdmin(p.getName());
		if (admin.equalsIgnoreCase(Hero.UUID)|| admin.equalsIgnoreCase(Legend.UUID) || admin.equalsIgnoreCase(owner.UUID)) {
			e.setJoinMessage(null);
			Bukkit.getServer().broadcastMessage(
					ChatColor.MAGIC + "123" + ChatColor.GREEN + " " + admin
							+ " " + p.getName() + ChatColor.DARK_AQUA
							+ " has joined the game! " + ChatColor.WHITE
							+ ChatColor.MAGIC + "123");
			
			 new BukkitRunnable() {
		            int x = 0;
		            public void run() {
		            	x++;
		            	if(x >= 5){
		                    this.cancel();
		                } else {
		                	if(p != null) {
		                		shootFirework(p);
		                	}
		                }
		            }
		        }.runTaskTimer(plugin, 0, 10);
			
			
		}

	}
	private void shootFirework(Player p) {
		Firework fw = (Firework) p.getWorld().spawn(p.getEyeLocation(),
				Firework.class);
		FireworkMeta fwm = fw.getFireworkMeta();

		// Our random generator
		Random r = new Random();

		// Get the type
		int rt = r.nextInt(4) + 1;
		Type type = Type.BALL;
		if (rt == 1)
			type = Type.BALL;
		if (rt == 2)
			type = Type.BALL_LARGE;
		if (rt == 3)
			type = Type.BURST;
		if (rt == 4)
			type = Type.CREEPER;
		if (rt == 5)
			type = Type.STAR;

		// Get our random colours
		int r1i = r.nextInt(17) + 1;
		int r2i = r.nextInt(17) + 1;
		Color c1 = getColor(r1i);
		Color c2 = getColor(r2i);

		// Create our effect with this
		FireworkEffect effect = FireworkEffect.builder()
				.flicker(r.nextBoolean()).withColor(c1).withFade(c2)
				.with(type).trail(r.nextBoolean()).build();

		// Then apply the effect to the meta
		fwm.addEffect(effect);

		// Generate some random power and set it
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);

		// Then apply this to our rocket
		fw.setFireworkMeta(fwm);

		// use meta to customize the firework or add parameters to the
		// method
		// fw.setVelocity(p.getLocation().getDirection().multiply(speed);
		// speed is how fast the firework flies
	}

	private Color getColor(int i) {
		Color c = null;
		if (i == 1) {
			c = Color.AQUA;
		}
		if (i == 2) {
			c = Color.BLACK;
		}
		if (i == 3) {
			c = Color.BLUE;
		}
		if (i == 4) {
			c = Color.FUCHSIA;
		}
		if (i == 5) {
			c = Color.GRAY;
		}
		if (i == 6) {
			c = Color.GREEN;
		}
		if (i == 7) {
			c = Color.LIME;
		}
		if (i == 8) {
			c = Color.MAROON;
		}
		if (i == 9) {
			c = Color.NAVY;
		}
		if (i == 10) {
			c = Color.OLIVE;
		}
		if (i == 11) {
			c = Color.ORANGE;
		}
		if (i == 12) {
			c = Color.PURPLE;
		}
		if (i == 13) {
			c = Color.RED;
		}
		if (i == 14) {
			c = Color.SILVER;
		}
		if (i == 15) {
			c = Color.TEAL;
		}
		if (i == 16) {
			c = Color.WHITE;
		}
		if (i == 17) {
			c = Color.YELLOW;
		}

		return c;
	}
}
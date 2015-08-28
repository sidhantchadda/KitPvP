package me.sidhant.kitpvp;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Config {
	Plugin plugin;
	public static FileConfiguration config;
	
	public Config(Plugin plugin) {
		config = plugin.getConfig();
		Bukkit.getLogger().info("test");
	}
	public static void addTokens(OfflinePlayer offlinePlayer, int tokens) {
		int currentTokens = getTokens(offlinePlayer);
		config.set("Tokens "+offlinePlayer.getName(), currentTokens+tokens);
		if(offlinePlayer instanceof Player) {
			ReloadScoreBoard((Player) offlinePlayer);
		}
	}
	public static void addTokens(String name, int tokens) {
		int currentTokens = getTokens(name);
		config.set("Tokens "+name, currentTokens+tokens);
		Player p = Bukkit.getServer().getPlayer(name);
		if(p != null) {
			ReloadScoreBoard(p);
		}
	}
	public static void addKills(String name, int amount) {
		amount = config.getInt("Kills "+name)+amount;
		config.set("Kills "+name, amount);
		Player p = Bukkit.getServer().getPlayer(name);
		if(p != null) {
			ReloadScoreBoard(p);
		}
	}
	public static int getKills(String name) {
		Integer kills = config.getInt("Kills "+name);
		if(kills == null) {
			kills = 0;
		}
		return kills;
	}
	private static int getDeaths(String name) {
		Integer deaths = config.getInt("Deaths "+name);
		if(deaths == null) {
			deaths = 0;
		}
		return deaths;
	}
	public static void addDeaths(String name, int amount) {
		amount = config.getInt("Deaths "+name)+amount;
		config.set("Deaths "+name, amount);
		Player p = Bukkit.getServer().getPlayer(name);
		if(p != null) {
			ReloadScoreBoard(p);
		}
	}
	public static void removeTokens(String name, int tokens) {
		int currentTokens = getTokens(name);
		config.set("Tokens "+name, currentTokens - tokens);
		Player p = Bukkit.getServer().getPlayer(name);
		if(p != null) {
			ReloadScoreBoard(p);
		}
	}
	
	public static int getTokens(OfflinePlayer offlinePlayer) {
		Integer tokens = config.getInt("Tokens "+offlinePlayer.getName());
		if(tokens == null) {
			tokens = 0;
		}
		return tokens;
	}
	public static int getTokens(String name) {
		Integer tokens = config.getInt("Tokens "+name);
		if(tokens == null) {
			tokens = 0;
		}
		return tokens;
	}

	public static boolean hasKit(String name, Integer id) {
		if(config.get("Kit "+name+" "+id) != null) return true;
		return false;
	}
	public static void buyKit(String name, Integer tokens, Integer id) {
		config.set("Kit "+name+" "+id, true);
		removeTokens(name, tokens);
	}
	public static void addAdmin(String name, String admin) {
		config.set("Admin "+name, admin);
	}
	public static String getAdmin(String name) {
		String admin = config.getString("Admin "+name);
		if(admin != null) {
			return admin;
		}
		return "Peasant";
	}
 	public static void ReloadScoreBoard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		 
		Objective objective = board.registerNewObjective("test", "dummy");
		 
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Stats");
		Score score = objective.getScore(ChatColor.GREEN+"Tokens");
		score.setScore(Config.getTokens(p));
		
		Score score2 = objective.getScore(ChatColor.GREEN+"Kills");
		score2.setScore(Config.getKills(p.getName()));
		p.setScoreboard(board);
		
		Score score3 = objective.getScore(ChatColor.GREEN+"Deaths");
		score3.setScore(Config.getDeaths(p.getName()));
		p.setScoreboard(board);
	}
}

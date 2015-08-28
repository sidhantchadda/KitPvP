package MYSQL_DATABASE;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

import MYSQL_DATABASE.code.husky.mysql.MySQL;

public class InitializeDB {
	Connection c;
	public InitializeDB(Plugin plugin) {
		MySQL MySQL = new MySQL(plugin, "host.name", "port", "database", "user", "pass");
		try {
			c = MySQL.openConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

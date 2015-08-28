package Admins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class AdminCommand implements CommandExecutor {
	public AdminCommand() {
		new Vip();
		new Hero();
		new Legend();
		new Peasant();
		new owner();
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof ConsoleCommandSender) {
			String admin = args[1];
			String name = args[0];
			if(admin.equalsIgnoreCase(Vip.UUID)) Vip.addPlayer(name);
			if(admin.equalsIgnoreCase(Hero.UUID)) Hero.addPlayer(name);
			if(admin.equalsIgnoreCase(Legend.UUID)) Legend.addPlayer(name);	
			if(admin.equalsIgnoreCase(Peasant.UUID)) Peasant.addPlayer(name);
			if(admin.equalsIgnoreCase(owner.UUID)) owner.addPlayer(name);
				
		}
		else if(sender.isOp()) {
			String admin = args[1];
			String name = args[0];
			if(admin.equalsIgnoreCase(Vip.UUID)) Vip.addPlayer(name);
			if(admin.equalsIgnoreCase(Hero.UUID)) Hero.addPlayer(name);
			if(admin.equalsIgnoreCase(Legend.UUID)) Legend.addPlayer(name);	
			if(admin.equalsIgnoreCase(Peasant.UUID)) Peasant.addPlayer(name);
			if(admin.equalsIgnoreCase(owner.UUID)) owner.addPlayer(name);
		}
		return false;
	}
}

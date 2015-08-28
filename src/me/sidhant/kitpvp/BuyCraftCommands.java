package me.sidhant.kitpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class BuyCraftCommands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			if(args.length == 2) {
				Config.addTokens(args[0], Integer.parseInt(args[1]));
			}
		}
		return false;
	}
}

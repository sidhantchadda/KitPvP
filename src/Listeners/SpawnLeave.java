package Listeners;

import me.sidhant.kitpvp.Config;
import me.sidhant.kitpvp.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

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
import Kits.Kit;
import Kits.Mage;
import Kits.Poseidon;
import Kits.Tank;
import Kits.Vampire;
import Kits.Zeus;

public class SpawnLeave implements Listener {
	@EventHandler
	public void Leave(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Util.hasPlayerinLobby(p)) {
			Location loc = p.getLocation();
			if(!((loc.getZ() > -19 && loc.getZ() < 18) && (loc.getX() > -18 && loc.getX() < 19))) {
				Integer kit = Util.getKitSelected(p);
				p.closeInventory();
				if(kit == null) {
					click(p);
					Kit k = Util.getKitByid(0);
					k.addPlayer(p);
					Util.addPlayerToGame(p, k);
				}
				else if(!(Config.hasKit(p.getName(), kit))) {
					click(p);
					Kit k = Util.getKitByid(0);
					BasicPvp basic = (BasicPvp) k;
					basic.addPlayer(p);
					Util.addPlayerToGame(p, basic);
				}
				else if (Util.getKitByid(kit) != null) {
					Kit k = Util.getKitByid(kit);
					if(k instanceof Archer) {
						click(p);
						Archer arch = (Archer) k;
						arch.addPlayer(p);
						Util.addPlayerToGame(p, arch);
					}
					if(k instanceof BasicPvp) {
						click(p);
						BasicPvp basic = (BasicPvp) k;
						basic.addPlayer(p);
						Util.addPlayerToGame(p, basic);
					}
					if(k instanceof Blaze) {
						click(p);
						Blaze blaze = (Blaze) k;
						blaze.addPlayer(p);
						Util.addPlayerToGame(p, blaze);
					}
					if(k instanceof Elemental) {
						click(p);
						Elemental element = (Elemental) k;
						element.addPlayer(p);
						Util.addPlayerToGame(p, element);
					}
					if(k instanceof Frost) {
						click(p);
						Frost frost = (Frost) k;
						frost.addPlayer(p);
						Util.addPlayerToGame(p, frost);
					}
					if(k instanceof Ghost) {
						click(p);
						Ghost ghost = (Ghost) k;
						ghost.addPlayer(p);
						Util.addPlayerToGame(p, ghost);
					}
					if(k instanceof Kangaroo) {
						click(p);
						Kangaroo kang = (Kangaroo) k;
						kang.addPlayer(p);
						Util.addPlayerToGame(p, kang);
					}
					if(k instanceof Mage) {
						click(p);
						Mage mage = (Mage) k;
						mage.addPlayer(p);
						Util.addPlayerToGame(p, mage);
					}
					if(k instanceof Bomber) {
						click(p);
						Bomber b = (Bomber) k;
						b.addPlayer(p);
						Util.addPlayerToGame(p, b);
					}
					if(k instanceof Poseidon) {
						click(p);
						Poseidon poseidon = (Poseidon) k;
						poseidon.addPlayer(p);
						Util.addPlayerToGame(p, poseidon);
					}
					if(k instanceof CopyCat) {
						click(p);
						CopyCat copycat = (CopyCat) k;
						copycat.addPlayer(p);
						Util.addPlayerToGame(p, copycat);
					}
					if(k instanceof Flash) {
						click(p);
						Flash flash = (Flash) k;
						flash.addPlayer(p);
						Util.addPlayerToGame(p, flash);
					}
					if(k instanceof Vampire) {
						click(p);
						Vampire vampire = (Vampire) k;
						vampire.addPlayer(p);
						Util.addPlayerToGame(p, vampire);
					}
					if(k instanceof Gambler) {
						click(p);
						Gambler gambler = (Gambler) k;
						gambler.addPlayer(p);
						Util.addPlayerToGame(p, gambler);
					}
					if(k instanceof Tank) {
						click(p);
						Tank tank = (Tank) k;
						tank.addPlayer(p);
						Util.addPlayerToGame(p, tank);
					}
					if(k instanceof Blink) {
						click(p);
						Blink blink = (Blink) k;
						blink.addPlayer(p);
						Util.addPlayerToGame(p, blink);
					}
					if(k instanceof Bounce) {
						click(p);
						Bounce bounce = (Bounce) k;
						bounce.addPlayer(p);
						Util.addPlayerToGame(p, bounce);
					}
					if(k instanceof Zeus) {
						click(p);
						Zeus z = (Zeus) k;
						z.addPlayer(p);
						Util.addPlayerToGame(p, z);
					}
				}	
				else {
					click(p);
					Kit k = Util.getKitByid(0);
					BasicPvp basic = (BasicPvp) k;
					basic.addPlayer(p);
					Util.addPlayerToGame(p, basic);
				}
			}
		}
	}

	private void click(Player p) {
		Util.clearInventory(p);
		Util.removePlayerfromLobby(p);
	}

}

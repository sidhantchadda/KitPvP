package me.sidhant.kitpvp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import Admins.Hero;
import Admins.Legend;
import Admins.Peasant;
import Admins.Vip;
import Admins.owner;
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

public class Util {
	public static ArrayList<String> inLobby = new ArrayList<String>();
	public static HashMap<String, Kit> kits = new HashMap<String, Kit>();
	public static HashMap<String, Integer> kitSelected = new HashMap<String, Integer>();
	public static HashMap<Integer, Kit> ids = new HashMap<Integer, Kit>();  
	public static HashMap<Integer, Integer> prices = new HashMap<Integer, Integer>();
	
	public static void clearInventory(Player p)	{
		PlayerInventory inv = p.getInventory();
		inv.setHelmet(new ItemStack(Material.AIR));
		inv.setChestplate(new ItemStack(Material.AIR));
		inv.setLeggings(new ItemStack(Material.AIR));
		inv.setBoots(new ItemStack(Material.AIR));
		inv.clear();
		for (PotionEffect ef : p.getActivePotionEffects()) {
			p.removePotionEffect(ef.getType());
		}
		if(p.getGameMode() != GameMode.CREATIVE) {
			p.setAllowFlight(false);
			p.setFlying(false);
		}
		p.setMaxHealth(20);
		//remove arrows from player
		((CraftPlayer) p).getHandle().getDataWatcher().watch(9, (byte)0);
		p.setFireTicks(0);
	}
	public static void setInventory(Player p)	{
		PlayerInventory inv =p.getInventory();
		ItemStack chest = new ItemStack(Material.CHEST);
		ItemMeta chestMeta = chest.getItemMeta();
		chestMeta.setDisplayName(ChatColor.GREEN+"Choose your kit!");
		chest.setItemMeta(chestMeta);
        inv.addItem(chest);
        String admin = Config.getAdmin(p.getName());
        if(admin.equalsIgnoreCase(Peasant.UUID)) {
        	p.setDisplayName(ChatColor.GRAY+"Peasant "+p.getName()+ChatColor.WHITE);
        }
        else if(admin.equalsIgnoreCase(Vip.UUID)) {
        	Vip.addToInventory(p);
        	p.setDisplayName(ChatColor.DARK_PURPLE+"VIP "+p.getName()+ChatColor.WHITE);
        }
        else if(admin.equalsIgnoreCase(Hero.UUID)) {
        	Hero.addToInventory(p);
        	p.setDisplayName(ChatColor.GOLD+"HERO "+p.getName()+ChatColor.WHITE);
        }
        else if(admin.equalsIgnoreCase(Legend.UUID)) {
        	Legend.addToInventory(p);
        	p.setDisplayName(ChatColor.RED+"LEGEND "+p.getName()+ChatColor.WHITE);
        }
        else if(admin.equalsIgnoreCase(owner.UUID)) {
        	p.setDisplayName(ChatColor.AQUA.toString()+ChatColor.BOLD+"OWNER "+ChatColor.GOLD+p.getName()+ChatColor.WHITE);
        }
	}
	public static Inventory getInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27);
		
		addKit(new ItemStack(Material.DIAMOND_SWORD), "Basic Pvp", "Basic Pvp Kit, but no regen", BasicPvp.UUID, inv, Config.hasKit(p.getName(), BasicPvp.UUID));
        addKit(new ItemStack(Material.ENCHANTED_BOOK), "Mage", "Magical Kit", Mage.UUID, inv, Config.hasKit(p.getName(), Mage.UUID));
        addKit(new ItemStack(Material.NETHER_STAR), "Elemental", "Elemental kit crouch to use abilties", Elemental.UUID, inv, Config.hasKit(p.getName(), Elemental.UUID));
        addKit(new ItemStack(Material.BOW), "Archer", "Archer Kit", Archer.UUID, inv, Config.hasKit(p.getName(), Archer.UUID));
        addKit(new ItemStack(Material.FEATHER), "Ghost", "Boo!", Ghost.UUID, inv, Config.hasKit(p.getName(), Ghost.UUID));
        addKit(new ItemStack(Material.RABBIT_FOOT), "Kangaroo", "Jump really high!", Kangaroo.UUID, inv, Config.hasKit(p.getName(), Kangaroo.UUID));
        addKit(new ItemStack(Material.BLAZE_POWDER), "Blaze", "Heat is your friend!", Blaze.UUID, inv, Config.hasKit(p.getName(), Blaze.UUID));
        addKit(new ItemStack(Material.SNOW_BALL), "Frost", "Freeze your enemies!", Frost.UUID, inv, Config.hasKit(p.getName(), Frost.UUID));
        addKit(new ItemStack(Material.TNT), "Bomber", "Crouch to place tnt", Bomber.UUID, inv, Config.hasKit(p.getName(), Bomber.UUID));
        addKit(new ItemStack(Material.WATER_BUCKET), "Poseidon", "Pounce at people from water", Poseidon.UUID, inv, Config.hasKit(p.getName(), Poseidon.UUID));
        addKit(new ItemStack(Material.HAY_BLOCK), "Copy Cat", "Copy your enemies kits when you kill them", CopyCat.UUID, inv, Config.hasKit(p.getName(), CopyCat.UUID));
        addKit(new ItemStack(Material.SUGAR), "Flash", "Speedy but weak", Flash.UUID, inv, Config.hasKit(p.getName(), Flash.UUID));
        addKit(new ItemStack(Material.FLINT), "Vampire", "Take your opponents health", Vampire.UUID, inv, Config.hasKit(p.getName(), Vampire.UUID));
        addKit(new ItemStack(Material.WOOD_BUTTON), "Gambler", "Gamble for items!", Gambler.UUID, inv, Config.hasKit(p.getName(), Gambler.UUID));
        addKit(new ItemStack(Material.DIAMOND_CHESTPLATE), "Tank", "Armour up!", Tank.UUID, inv, Config.hasKit(p.getName(), Tank.UUID));
        addKit(new ItemStack(Material.MAP), "Blink", "Teleportation", Blink.UUID, inv, Config.hasKit(p.getName(), Blink.UUID));
        addKit(new ItemStack(Material.SLIME_BALL), "Bounce", "Pound your enemies!", Bounce.UUID, inv, Config.hasKit(p.getName(), Bounce.UUID));
        addKit(new ItemStack(Material.BLAZE_ROD), "Zeus", "Shoot Lightning at your enemies", Zeus.UUID, inv, Config.hasKit(p.getName(), Bounce.UUID));
        
        return inv;
	}
	
	private static void addKit(ItemStack item, String name, String description, int inventoryID, Inventory inv, boolean haskit) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.GREEN+name);
		if(haskit = true) {
			itemMeta.setLore(Arrays.asList(description));
		}
		else {
			Integer price = getPriceById(inventoryID);
			if(price != null) {
				itemMeta.setLore(Arrays.asList(description, "Price: "+price));
			}
		}
		item.setItemMeta(itemMeta);
		inv.setItem(inventoryID, item);
	}
	public static void addPlayerToLobby(Player p)	{
		inLobby.add(p.getName());
	}
	public static void removePlayerfromLobby(Player p)	{
		inLobby.remove(p.getName());
	}
	
	public static boolean hasPlayerinLobby(Player p)	{
		if(inLobby.contains(p.getName()))	{
			return true;
		}
		else	{
			return false;
		}
	}
	public static void addPlayerToGame(Player p, Kit k) {
		String name = p.getName();
		if(kits.containsKey(name)) {
			kits.remove(name);
		}
		kits.put(name, k);
	}
	public static Kit getKit(Player p) {
		return kits.get(p.getName());
	}
	public static Integer getKitSelected(Player p) {
		return kitSelected.get(p.getName());
	}
	public static void addKitSelected(Player p, Integer k) {
		String name = p.getName();
		if(kitSelected.containsKey(name)) {
			kitSelected.remove(name);
		}
		kitSelected.put(name, k);
	}
	public static boolean hasKitSelected(Player p) {
		if(kitSelected.get(p.getName()) == null) {
			return false;
		}
		return true;
	}
	
	public static void JoinLobby (Player p) {
		kits.remove(p.getName());
		p.setGameMode(GameMode.ADVENTURE);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		clearInventory(p);
		setInventory(p);
		addPlayerToLobby(p);
	}
	public static void addSlot(Kit kit, Integer slot) {
		ids.put(slot, kit);
	}
	public static Kit getKitByid(Integer id) {
		return ids.get(id);
	}
	public static Integer getPriceById(Integer id) {
		return prices.get(id);
	}
	public static void addPrices(Integer id, Integer price) {
		prices.put(id, price);
	}
}

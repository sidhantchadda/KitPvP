package Admins;

public class Peasant extends admin {
	public static String UUID = "Peasant";
	public static void addPlayer(String name) {
		add(name, UUID);
	}

}

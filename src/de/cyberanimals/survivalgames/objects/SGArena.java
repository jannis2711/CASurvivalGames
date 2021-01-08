package de.cyberanimals.survivalgames.objects;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SGArena {
	
	
	private String configname;
	
	private Location lobbyloc = null;
	private ArrayList<Location> spawnlist;
	
	private String maptitle;
	private String mapcreator;
	
	private boolean configured;
	
	
	public SGArena(String configname) {
		this.configname = configname;
		
		System.out.println("Start intializing of MAPCONFIG");
		File cfgfile = new File("plugins/SurvivalGames/arenas/", configname+".yml");
		FileConfiguration mapcfg = YamlConfiguration.loadConfiguration(cfgfile);
		if(!mapcfg.getBoolean("conf.configured")) {
			configured = false;
			return;
		} else {
			configured = true;
		}
		System.out.println("MAPCONFIG initialized.");
		
		System.out.println("Start loading Lobby Coords.");
		lobbyloc = new Location(Bukkit.getWorld(mapcfg.getString("coords.lobby.world")),mapcfg.getInt("coords.lobby.x"),mapcfg.getInt("coords.lobby.y"),mapcfg.getInt("coords.lobby.z"),(float) mapcfg.getDouble("coords.lobby.yaw"),(float) mapcfg.getDouble("coords.lobby.pitch"));
		System.out.println("Lobby Coords loaded. Builded Lobby Location.");
		
		int spawns = mapcfg.getInt("conf.spawns");
		spawnlist = new ArrayList<Location>();
		System.out.println("Start getting Spawns from MAPCONFIG");
		for(int i = 1; i < spawns; i++) {
			Location spawnpoint = new Location(Bukkit.getWorld(mapcfg.getString("spawn."+i+".world")),mapcfg.getInt("spawn."+i+".x"),mapcfg.getInt("spawn."+i+".y"),mapcfg.getInt("spawn."+i+".z"),(float)mapcfg.getDouble("spawn."+i+".yaw"),(float)mapcfg.getDouble("spawn."+i+".pitch"));
			spawnlist.add(spawnpoint);
		}
		System.out.println("Spawns successfully stored in Spawnlist.");
		
		System.out.println("Map title is loading from MAPCONFIG...");
		this.maptitle = mapcfg.getString("conf.maptitle");
		this.mapcreator = mapcfg.getString("conf.mapcreator");
		System.out.println("Map title loaded.");

	}
	
	public boolean isReady() {
		return configured;
	}
	
	public Location getLobbyLocation() {
		return lobbyloc;
	}
	
	public ArrayList<Location> getSpawnlist() {
		return spawnlist;
	}
	
	public String getMapTitle() {
		return maptitle;
	}
	
	public String getMapCreator() {
		return mapcreator;
	}
	
	
	
	
}

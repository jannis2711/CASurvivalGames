package de.cyberanimals.survivalgames;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.cyberanimals.survivalgames.commands.sgcmd_setup;
import de.cyberanimals.survivalgames.listener.sglis_build;
import de.cyberanimals.survivalgames.listener.sglis_dropitemlistener;
import de.cyberanimals.survivalgames.listener.sglis_entitydamagebyentitylistener;
import de.cyberanimals.survivalgames.listener.sglis_entitydamagelistener;
import de.cyberanimals.survivalgames.listener.sglis_foodlevel;
import de.cyberanimals.survivalgames.listener.sglis_joinloginlistener;
import de.cyberanimals.survivalgames.listener.sglis_movelistener;
import de.cyberanimals.survivalgames.listener.sglis_pickupitemlistener;
import de.cyberanimals.survivalgames.listener.sglis_playerdeathlistener;
import de.cyberanimals.survivalgames.objects.SGArena;

public class sgmain extends JavaPlugin{
	
	public String pfx = "§2SurvivalGames §f|| §6";
	public String epfx = "§2SurvivalGames §f|| §c";
	public String cpfx = "[CASurvivalGames] ";
	public String msgperm = epfx+"Fehler. Du hast keine Berechtigung für diesen Befehl.";
	public String msgplayer = epfx+"Fehler. Du musst ein Spieler sein.";
	
	
	public int friedenszeit;
	public boolean frieden;
	public boolean noMove;
	public boolean nojoin;
	public boolean gamerunning;
	public boolean pvp;
	public boolean chestopen;
	
	public int minplayers;
	public int maxplayers;
	
	public boolean betrieb;
	
	public String currentArena;
	public File carenaFile;
	public FileConfiguration mapcfg;
	public SGArena gamearena;
	
	public HashMap<Location, Inventory> sgchest = new HashMap<Location, Inventory>();
	public sgfunctions functions = new sgfunctions(this);
	
	public ArrayList<String> dead = new ArrayList<String>();
	public ArrayList<String> alive = new ArrayList<String>();
	
	
	
	public void onEnable() {
		System.out.println("[CyberAnimals] SurvivalGames is loading...");
		currentArena = "";
		getConfig().addDefault("config.betrieb", "false");
		getConfig().addDefault("config.minplayers", 2);
		getConfig().addDefault("config.maxplayers", 24);
		getConfig().options().copyDefaults(true);
		this.saveConfig();
		betrieb = getConfig().getBoolean("config.betrieb");
		this.loadSG();
		System.out.println("[CyberAnimals] SurvivalGames is loaded successfully.");
	}
	
	public void onDisable() {
		System.out.println("[CyberAnimals] SurvivalGames is shutting down...");
		
		System.out.println("[CyberAnimals] SurvivalGames is disabled now.");
	}
	
	public void initializeVariables() {
			this.friedenszeit = 60;
			this.nojoin = false;
			this.frieden = true;
			this.noMove = false;
			this.gamerunning = false;
			this.pvp = false;
			this.chestopen = false;
			this.minplayers = getConfig().getInt("config.minplayers");
			this.maxplayers = getConfig().getInt("config.maxplayers");
			
	}
	
	public void loadSG() {
			if(!betrieb) {
				//KEIN BETRIEB
				System.out.println("!!!!Kein Betrieb!!!! Nur Setup!");
				this.getCommand("setup").setExecutor(new sgcmd_setup(this));
			}
			
			if(betrieb) {
			PluginManager pm = Bukkit.getPluginManager();
			pm.registerEvents(new chestmanager(this), this);
			pm.registerEvents(new sglis_build(this), this);
			pm.registerEvents(new sglis_dropitemlistener(this), this);
			pm.registerEvents(new sglis_entitydamagebyentitylistener(this), this);
			pm.registerEvents(new sglis_entitydamagelistener(this), this);
			pm.registerEvents(new sglis_foodlevel(this), this);
			pm.registerEvents(new sglis_joinloginlistener(this), this);
			pm.registerEvents(new sglis_movelistener(this), this);
			pm.registerEvents(new sglis_pickupitemlistener(this), this);
			pm.registerEvents(new sglis_playerdeathlistener(this), this);
			
			
			this.getCommand("setup").setExecutor(new sgcmd_setup(this));
			this.gamearena = this.functions.chooseArena();
			
			}
			
			initializeVariables();
			
			
			 Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
			 

	}

}

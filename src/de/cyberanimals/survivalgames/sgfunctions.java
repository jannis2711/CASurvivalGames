package de.cyberanimals.survivalgames;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.cyberanimals.survivalgames.objects.SGArena;

public class sgfunctions {
	
	private sgmain plugin;
	public sgfunctions(sgmain plugin) {
		this.plugin = plugin;
	}
	
	private int gamecountdown_schedule;
	private int friedencountdown_schedule;
	
	
	
	public boolean createNewArena(String name) {
		
		File newArenaFile = new File("plugins/SurvivalGames/arenas/"+name+".yml");
		FileConfiguration arenacfg = YamlConfiguration.loadConfiguration(newArenaFile);
		
		if(newArenaFile.exists()) {
			return false;
		}
		
		arenacfg.set("conf.configured", "false");
		try {
			arenacfg.save(newArenaFile);
			return true;
		} catch (IOException e) {
			System.out.println("Can't create Arena!");
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	public boolean selectArena(String name) {
		this.plugin.carenaFile =  new File("plugins/SurvivalGames/arenas/"+name+".yml");
		if(this.plugin.carenaFile.exists()) {
			this.plugin.currentArena = name;
			this.plugin.mapcfg = YamlConfiguration.loadConfiguration(this.plugin.carenaFile);
			System.out.println("SELECTED ARENA: "+name);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setMapTitle(String maptitle) {
		
		File mapcfgfile = new File("plugins/SurvivalGames/arenas/"+this.plugin.currentArena+".yml");
		FileConfiguration mapcfg = YamlConfiguration.loadConfiguration(mapcfgfile);
		
		mapcfg.set("conf.maptitle", maptitle);
		
		
		try {
			mapcfg.save(mapcfgfile);
			return true;
		} catch (IOException e) {
			System.out.println("Can't set Mapinfo");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setMapCreator(String mapcreator) {
		
		File mapcfgfile = new File("plugins/SurvivalGames/arenas/"+this.plugin.currentArena+".yml");
		FileConfiguration mapcfg = YamlConfiguration.loadConfiguration(mapcfgfile);
		
		mapcfg.set("conf.mapcreator", mapcreator);
		
		
		try {
			mapcfg.save(mapcfgfile);
			return true;
		} catch (IOException e) {
			System.out.println("Can't set Mapinfo");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean finishArena() {
		FileConfiguration mapcfg = this.plugin.mapcfg;
		
		boolean lobbyloc = mapcfg.contains("coords.lobby");
		int spawns = mapcfg.getInt("conf.spawns");
		boolean maptitle = mapcfg.contains("conf.maptitle");
		boolean mapcreator = mapcfg.contains("conf.mapcreator");
		
		if(lobbyloc && maptitle && mapcreator && spawns>0) {
			mapcfg.set("conf.configured", "true");
			return true;
		}
		
		return false;
	}
	
	
	//GGGGGGGGGGGGGAAAAAAAAAAAAAMMMMMMMMMMMEEEEEEEEEEEEE STUFF
	
	
	
	public void startGameCountdown() {
		this.gamecountdown_schedule = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new game_countdown(plugin), 0L, 1*20L);
		
	}
	
	public void stopGameCountdown() {
		Bukkit.getScheduler().cancelTask(this.gamecountdown_schedule);
	}
	
	public SGArena chooseArena() {
		//durch zufall mach mal
		this.plugin.currentArena = "SG4";
		return new SGArena("SG4");
	}
	
	public void mapTeleport() {
		List<Location> spawns = this.plugin.gamearena.getSpawnlist();
		int spawnindex = 0;
		for(Player all:Bukkit.getOnlinePlayers()) {
			all.teleport(spawns.get(spawnindex));
			this.plugin.alive.add(all.getName());
			spawnindex++;
		}
	}
	
	public void startFriedenszeit() {
		this.friedencountdown_schedule = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new frieden_schedule(plugin), 0L, 1*20L);
	}
	
	public void stopFriedenszeit() {
		Bukkit.getScheduler().cancelTask(this.friedencountdown_schedule);
	}
	
	public void startGame() {
		this.plugin.functions.startFriedenszeit();
		this.plugin.chestopen = true;
		this.plugin.gamerunning = true;
		this.plugin.noMove = false;
		
	}
	
	public void checkPlayers() {
		if(Bukkit.getOnlinePlayers().size() >= this.plugin.minplayers) {
			this.startGameCountdown();
		}
	}
	
	public void conOther(Player a) {
		 ByteArrayOutputStream b = new ByteArrayOutputStream();
		 DataOutputStream out = new DataOutputStream(b);
		  
		 try {
		 out.writeUTF("Connect");
		 out.writeUTF("lobby1");
		 } catch (IOException ex) {
		  
		 }
		 a.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
	}
	
	public void checkIfEnd() {
		if(this.plugin.alive.size() == 1) {
			for(String pname : this.plugin.alive) {
				Player p = Bukkit.getPlayer(pname);
				Bukkit.broadcastMessage(this.plugin.pfx+pname+" hat die SurvivalGames gewonnen!!!111!1!!!1111!!!");
				
				Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, new Runnable() {
					int count = 10;
					@Override
					public void run() {
						
						Bukkit.broadcastMessage(plugin.pfx+"Der Server wird in " + count + " Sekunden neugestartet");
						if(count == 0) {
							Bukkit.getScheduler().cancelAllTasks();
							for(Player a : Bukkit.getOnlinePlayers()) {
								conOther(a);
							}
							Bukkit.reload();
						}
						
						count--;
						
						
					}
					
					
				}, 0L, 1*20L);
			}
		}
	}
	

}

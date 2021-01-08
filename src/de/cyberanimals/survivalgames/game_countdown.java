package de.cyberanimals.survivalgames;

import org.bukkit.Bukkit;

public class game_countdown implements Runnable{

	
	private sgmain plugin;
	public game_countdown(sgmain plugin) {
		this.plugin = plugin;
	}

	int countdown = 40;
	
	@Override
	public void run() {
			if(!(Bukkit.getOnlinePlayers().size() >= this.plugin.minplayers)) {
				this.plugin.functions.stopGameCountdown();
			}
			
			//Gamecountdown
			if(countdown == 40) {
				Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis zum Spielstart!");
			}
			
			if(countdown == 30) {
				Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis zum Spielstart!");
			}
			
			if(countdown == 20) {
				Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis zum Spielstart!");
			}
			
			if(countdown == 13) {
				this.plugin.nojoin = true;
			}
			
			if(countdown == 10) {
				//Mapteleport
				this.plugin.functions.mapTeleport();
				this.plugin.noMove = true;
				Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis zum Spielstart!");
			}
			
			if(countdown <= 5 && countdown > 0) {
				Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis zum Spielstart!");
			}
			
			if(countdown == 0) {
				Bukkit.broadcastMessage(plugin.pfx+"Das Spiel beginnt!");
				this.plugin.functions.stopGameCountdown();
				this.plugin.functions.startGame();
				
				
				//Friedenszeit LOS!
			}
			
			countdown--;
			
	}
	
	

}

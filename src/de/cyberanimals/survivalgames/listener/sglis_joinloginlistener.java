package de.cyberanimals.survivalgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_joinloginlistener implements Listener {

	private sgmain plugin;
	public sglis_joinloginlistener(sgmain plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(this.plugin.nojoin) {
			e.disallow(Result.KICK_OTHER, "Das Spiel läuft bereits!");
		}
		
		if(Bukkit.getOnlinePlayers().size() >= this.plugin.maxplayers) {
			e.disallow(Result.KICK_FULL, "Der Server ist voll.");
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(!this.plugin.gamerunning) {
			e.setJoinMessage("§e"+e.getPlayer().getName()+"§6 hat das Spiel betreten.");
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.updateInventory();
			p.teleport(this.plugin.gamearena.getLobbyLocation());
			this.plugin.functions.checkPlayers();
		} else {
			p.setGameMode(GameMode.SPECTATOR);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.updateInventory();
			//Inventar für Spec festlegen?
		}
	}
	
	
	
}

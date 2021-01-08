package de.cyberanimals.survivalgames.listener;


import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_playerdeathlistener implements Listener{
	
	
	private sgmain plugin;
	public sglis_playerdeathlistener(sgmain plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player dead = e.getEntity();
		
		
		if(dead.getKiller() != null) {
			Player killer = dead.getKiller();
			e.setDeathMessage(this.plugin.pfx+killer.getName()+" hat " + dead.getName()+ " umgebracht!");
			if(killer.getHealth() <= 18) {
			killer.setHealth(killer.getHealth()+2);
			}
		}
		
		
		dead.setHealth(20);
		dead.setFoodLevel(20);
		dead.getInventory().clear();
		dead.getInventory().setArmorContents(null);
		dead.updateInventory();
		
		Vector v = dead.getVelocity();
		v.setY(2);
		dead.setVelocity(v);
		dead.setGameMode(GameMode.SPECTATOR);
		this.plugin.alive.remove(dead.getName());
		this.plugin.dead.add(dead.getName());
		dead.sendMessage(this.plugin.pfx+"Du bist nun ein Spectator.");
		
		
		this.plugin.functions.checkIfEnd();
	}

}

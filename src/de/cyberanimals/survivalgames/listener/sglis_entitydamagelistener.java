package de.cyberanimals.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_entitydamagelistener implements Listener{
	
	
	private sgmain plugin;
	public sglis_entitydamagelistener(sgmain plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!this.plugin.gamerunning) e.setCancelled(true);
	}
	
	

}

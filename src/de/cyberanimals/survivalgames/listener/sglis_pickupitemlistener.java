package de.cyberanimals.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_pickupitemlistener implements Listener {

	private sgmain plugin;
	public sglis_pickupitemlistener(sgmain plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void DROP(PlayerDropItemEvent e) {
		if(this.plugin.gamerunning) {
			if(this.plugin.alive.contains(e.getPlayer().getName())) {
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
	}
		
	
	
}

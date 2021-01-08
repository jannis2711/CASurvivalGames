package de.cyberanimals.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_movelistener implements Listener{
	
	private sgmain plugin;
	public sglis_movelistener(sgmain plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(plugin.noMove) {
			
			if(e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) {
				return;
			}
			e.setTo(e.getFrom());
		}
	}

}

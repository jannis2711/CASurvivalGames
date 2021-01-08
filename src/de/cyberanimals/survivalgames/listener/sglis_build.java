package de.cyberanimals.survivalgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_build implements Listener{
	
	
	private sgmain plugin;
	public sglis_build(sgmain plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(plugin.betrieb) e.setCancelled(true);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(plugin.betrieb) e.setCancelled(true);
	}
	

}

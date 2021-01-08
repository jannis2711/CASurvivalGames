package de.cyberanimals.survivalgames.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_foodlevel implements Listener{
	
	
	private sgmain plugin;
	public sglis_foodlevel(sgmain plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if(!this.plugin.gamerunning) {
			e.setCancelled(true);
		}
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(this.plugin.dead.contains(p.getName())) e.setCancelled(true);
		}
		
	}

}

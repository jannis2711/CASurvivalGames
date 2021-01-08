package de.cyberanimals.survivalgames.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.cyberanimals.survivalgames.sgmain;

public class sglis_entitydamagebyentitylistener implements Listener{
	
	
	private sgmain plugin;
	public sglis_entitydamagebyentitylistener(sgmain plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}	
	
	@EventHandler
	public void onTakeDamage(EntityDamageByEntityEvent e) {
		if(!this.plugin.gamerunning) e.setCancelled(true);
		
		if(!(this.plugin.frieden) && (this.plugin.pvp)) {
			if(e.getDamager() instanceof Player) {
				Player damager = (Player) e.getDamager();
				if(plugin.alive.contains(damager.getName())) {
					e.setCancelled(false);
				} else {
					e.setCancelled(true);
				}
				
			}
		} else {
			e.setCancelled(true);
		}
		
	}

}

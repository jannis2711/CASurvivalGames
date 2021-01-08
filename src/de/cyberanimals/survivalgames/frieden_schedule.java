package de.cyberanimals.survivalgames;

import org.bukkit.Bukkit;

public class frieden_schedule implements Runnable{

	private sgmain plugin;
	public frieden_schedule(sgmain plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}

	int countdown =60;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if(countdown == 60) {
			Bukkit.broadcastMessage(plugin.pfx+"Noch 1 Minute bis PVP aktiviert wird!");
		}
		
		if(countdown == 30) {
			Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis die Schutzzeit zuende ist.");
		}
		
		if(countdown == 10) {
			Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis die Schutzzeit zuende ist.");
		}
		
		
		if(countdown <= 5 && countdown > 0) {
			Bukkit.broadcastMessage(plugin.pfx+"Noch "+countdown+" Sekunden bis die Schutzzeit zuende ist.");
		}
		
		if(countdown == 0) {
			Bukkit.broadcastMessage(plugin.pfx+"Die Schutzzeit ist beendet.");
			this.plugin.pvp = true;
			this.plugin.frieden = false;
			
		}
		
		countdown--;
	}

}

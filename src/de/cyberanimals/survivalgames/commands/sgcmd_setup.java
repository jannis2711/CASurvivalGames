package de.cyberanimals.survivalgames.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.cyberanimals.survivalgames.sgmain;

public class sgcmd_setup implements CommandExecutor{

	private sgmain plugin;
	public sgcmd_setup(sgmain plugin) {
		this.plugin = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(this.plugin.msgplayer);
		}
		
		if(this.plugin.betrieb) {
			sender.sendMessage("Der Produktive Betrieb des Servers ist aktiv. Kein Setup möglich!");
			return true;
		}
		
		Player p = (Player)sender;
		
		if(!p.hasPermission("cyberanimals.sg.setup")) {
			p.sendMessage(this.plugin.msgperm);
		}
		
		
		if(args.length == 0) {
			//syntax
			//setup createarena <name>
			//setup selectarena <name>
			//setup addspawn
			//setup setlobbyspawn
			//setup setmaptitle <title>
			//setup setmapcreator <creator>
			//setup finish
			
			p.sendMessage(this.plugin.pfx+"Syntax: /setup Einrichten des Spiels");
			p.sendMessage(this.plugin.pfx+"/setup createarena <name> Eine Arena(Map) erstellen");
			p.sendMessage(this.plugin.pfx+"/setup selectarena <name> Eine erstellte Arena zum konfigurieren auswählen.");
			p.sendMessage(this.plugin.pfx+"/setup addspawn Einen Spawnpunkt zur Konfiguration hinzufügen");
			p.sendMessage(this.plugin.pfx+"/setup setlobbyspawn Lobby Spawnpunkt einer Arena setzen");
			p.sendMessage(this.plugin.pfx+"/setup setmaptitle <title> Den Titel einer Map setzen.");
			p.sendMessage(this.plugin.pfx+"/setup setmapcreator <creator> Den Ersteller einer Map setzen.");
			p.sendMessage(this.plugin.pfx+"/setup finish Eine Arena fertigstellen und zum Spielen freigeben.");
			
			return true;
		}
			
		
		if(args.length == 1) {
			if((args[0].equalsIgnoreCase("addspawn") || args[0].equalsIgnoreCase("setlobbyspawn") || args[0].equalsIgnoreCase("finish")) && this.plugin.currentArena == "") {
				//Arena wurde nicht ausgewählt.
				p.sendMessage(this.plugin.epfx+"Du hast keine Arena ausgewählt! §e(/setup selectarena <Name>)");
				p.sendMessage(this.plugin.epfx+"Oder: Der Produktivbetrieb des Plugins ist aktiv.");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("finish")) {
				if(this.plugin.functions.finishArena()) {
					p.sendMessage(this.plugin.pfx+"Die Arena ist vollständig konfiguriert und jetzt nutzbar wenn du den Server in den Produktivbetrieb schaltest.");
					return true;
				} else {
					p.sendMessage(this.plugin.epfx+"Die Arena ist noch nicht vollständig konfiguriert.");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("addspawn")) {
				//Spawn hinzufügen
				
				FileConfiguration mapcfg = this.plugin.mapcfg;
				
				int spawnnumber = this.plugin.mapcfg.getInt("conf.spawns");
				if(spawnnumber == 24) {
					p.sendMessage(this.plugin.pfx+"Das Plugin ist auf 24 Spieler ausgelegt. Mehr Spawns sind derzeit nur möglich indem du einen Admin kontaktierst");
					p.sendMessage(this.plugin.pfx+"dev-antraege@cyberanimals.de");
					return true;
				}
				int newspawn = spawnnumber+1;
				
				Location loc = p.getLocation();
				mapcfg.set("spawn."+newspawn+".x", loc.getBlockX());
				mapcfg.set("spawn."+newspawn+".y", loc.getBlockY());
				mapcfg.set("spawn."+newspawn+".z", loc.getBlockZ());
				mapcfg.set("spawn."+newspawn+".yaw", loc.getYaw());
				mapcfg.set("spawn."+newspawn+".pitch", loc.getPitch());
				mapcfg.set("spawn."+newspawn+".world", loc.getWorld().getName());
				mapcfg.set("conf.spawns",newspawn);
				try {
					mapcfg.save(this.plugin.carenaFile);
					p.sendMessage(this.plugin.pfx+"Spawnpunkt #"+newspawn+" wurde gesetzt & gespeichert.");
					return true;
				} catch (IOException e) {
					p.sendMessage(this.plugin.epfx+"Es ist ein Fehler aufgetreten. Der Spawnpunkt konnte nicht gespeichert werden.");
					// TODO Auto-generated catch block
					e.printStackTrace();
					return true;
				}
				
			}
			if(args[0].equalsIgnoreCase("setlobbyspawn")) {
				//Lobbyspawn setzen
				
				FileConfiguration mapcfg = this.plugin.mapcfg;
				
				mapcfg.set("coords.lobby.x", p.getLocation().getBlockX());
				mapcfg.set("coords.lobby.y", p.getLocation().getBlockY());
				mapcfg.set("coords.lobby.z", p.getLocation().getBlockZ());
				mapcfg.set("coords.lobby.yaw", p.getLocation().getYaw());
				mapcfg.set("coords.lobby.pitch", p.getLocation().getPitch());
				mapcfg.set("coords.lobby.world",p.getLocation().getWorld().getName());
				
				try {
					mapcfg.save(this.plugin.carenaFile);
					p.sendMessage(plugin.pfx+"Der Lobbyspawnpunkt wurde erfolgreich gesetzt.");
					return true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					p.sendMessage(plugin.epfx+"Der Lobbyspawnpunkt konnte nicht gesetzt werden.");
					return true;
				}
				
			}
		}
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("createarena")) {
				String arenaname = args[1];
				boolean status = this.plugin.functions.createNewArena(arenaname);
				if(status) {
					p.sendMessage(this.plugin.pfx+"Die Arena wurde erfolgreich angelegt. §c("+arenaname+")");
					return true;
				} else {
					p.sendMessage(this.plugin.pfx+"Die Arena existiert bereits! oder ein anderer Fehler ist aufgetreten.");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("selectarena")) {
				String arenaname = args[1];
				if(this.plugin.functions.selectArena(arenaname)) {
					p.sendMessage(this.plugin.pfx+"Die Arena wurde erfolgreich ausgewählt!");
					return true;
				} else {
					p.sendMessage(this.plugin.epfx+"Ein Fehler ist aufgetreten. Es kann sein dass die Datei nicht existiert.");
					return true;
				}
			}
			
			if(args[0].equalsIgnoreCase("setmaptitle")) {
				if(this.plugin.currentArena == "") {
					p.sendMessage(this.plugin.epfx+"Du hast keine Arena ausgewählt! §e(/setup selectarena <Name>)");
					p.sendMessage(this.plugin.epfx+"Oder: Der Produktivbetrieb des Plugins ist aktiv.");
					return true;
				}
				String maptitle = args[1];
				if(this.plugin.functions.setMapTitle(maptitle)) {
					p.sendMessage(plugin.pfx+"Der Titel wurde erfolgreich gesetzt.");
				} else {
					p.sendMessage(plugin.pfx+"Der Titel konnte nicht gesetzt werden.");
				}
			}
			
			if(args[0].equalsIgnoreCase("setmapcreator")) {
				if(this.plugin.currentArena == "") {
					p.sendMessage(this.plugin.epfx+"Du hast keine Arena ausgewählt! §e(/setup selectarena <Name>)");
					p.sendMessage(this.plugin.epfx+"Oder: Der Produktivbetrieb des Plugins ist aktiv.");
					return true;
				}
				String mapcreator = args[1];
				if(this.plugin.functions.setMapCreator(mapcreator)) {
					p.sendMessage(plugin.pfx+"Der Map-Ersteller(Creator) wurde erfolgreich gesetzt.");
				} else {
					p.sendMessage(plugin.pfx+"Der Map-Ersteller(Creator) konnte nicht gesetzt werden.");
				}
			}
		}
		
		if(args.length > 2) {
			if(args[0].equalsIgnoreCase("setmaptitle")) {
				if(this.plugin.currentArena == "") {
					p.sendMessage(this.plugin.epfx+"Du hast keine Arena ausgewählt! §e(/setup selectarena <Name>)");
					p.sendMessage(this.plugin.epfx+"Oder: Der Produktivbetrieb des Plugins ist aktiv.");
					return true;
				}
				String maptitle = "";
			    for (int i = 1; i < args.length; i++) {
				      maptitle = maptitle + " " + args[i];
				    }
				if(this.plugin.functions.setMapTitle(maptitle)) {
					p.sendMessage(plugin.pfx+"Der Titel wurde erfolgreich gesetzt.");
				} else {
					p.sendMessage(plugin.pfx+"Der Titel konnte nicht gesetzt werden.");
				}
			}
			
			if(args[0].equalsIgnoreCase("setmapcreator")) {
				if(this.plugin.currentArena == "") {
					p.sendMessage(this.plugin.epfx+"Du hast keine Arena ausgewählt! §e(/setup selectarena <Name>)");
					p.sendMessage(this.plugin.epfx+"Oder: Der Produktivbetrieb des Plugins ist aktiv.");
					return true;
				}
			    String mapcreator = "";
			    for (int i = 1; i < args.length; i++) {
			      mapcreator = mapcreator + " " + args[i];
			    }
				
				
				if(this.plugin.functions.setMapCreator(mapcreator)) {
					p.sendMessage(plugin.pfx+"Der Map-Ersteller(Creator) wurde erfolgreich gesetzt.");
				} else {
					p.sendMessage(plugin.pfx+"Der Map-Ersteller(Creator) konnte nicht gesetzt werden.");
				}
			}
		}
		
		return true;
	}
	
	

}

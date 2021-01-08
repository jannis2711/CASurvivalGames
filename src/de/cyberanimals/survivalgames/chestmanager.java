package de.cyberanimals.survivalgames;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class chestmanager implements Listener{
	
	private sgmain plugin;
	public chestmanager(sgmain plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.SEA_LANTERN)) {
			if(!plugin.chestopen) {
				p.sendMessage(plugin.pfx+"Das Spiel ist noch nicht gestartet. Kisten können noch nicht geöffnet werden!");
				return;
			}
			Location chestloc = e.getClickedBlock().getLocation();
			if(this.plugin.sgchest.containsKey(chestloc)) {
				p.openInventory((Inventory)this.plugin.sgchest.get(chestloc));
				
			} else {
			Inventory chestinv = Bukkit.createInventory(null, 9*3,"Kiste");
			plugin.sgchest.put(chestloc, chestinv);
			
			
			List<ItemStack> items = new ArrayList<ItemStack>();
			items.add(new ItemStack(Material.STRING));
			items.add(new ItemStack(Material.IRON_INGOT));
			items.add(new ItemStack(Material.RAW_FISH));
			items.add(new ItemStack(Material.COOKED_FISH));
			items.add(new ItemStack(Material.DIAMOND));
			items.add(new ItemStack(Material.LEATHER_BOOTS));
			items.add(new ItemStack(Material.LEATHER_CHESTPLATE));
			items.add(new ItemStack(Material.LEATHER_HELMET));
			items.add(new ItemStack(Material.LEATHER_LEGGINGS));
			items.add(new ItemStack(Material.WOOD_SWORD));
			items.add(new ItemStack(Material.WOOD_SWORD));
			items.add(new ItemStack(Material.WOOD_SWORD));
			items.add(new ItemStack(Material.STONE_SWORD));
			items.add(new ItemStack(Material.STONE_SWORD));
			items.add(new ItemStack(Material.STICK,2));
			items.add(new ItemStack(Material.GRILLED_PORK));
			items.add(new ItemStack(Material.PORK));
			items.add(new ItemStack(Material.PORK,3));
			items.add(new ItemStack(Material.BREAD));
			items.add(new ItemStack(Material.BREAD,3));
			items.add(new ItemStack(Material.ARROW,5));
			items.add(new ItemStack(Material.FLINT));
			items.add(new ItemStack(Material.FEATHER));
			items.add(new ItemStack(Material.SNOW_BALL,16));
			items.add(new ItemStack(Material.EGG,16));
			items.add(new ItemStack(Material.CHAINMAIL_BOOTS));
			items.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			items.add(new ItemStack(Material.CHAINMAIL_HELMET));
			items.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			//items.add(new ItemStack(Material.TNT,2));
			items.add(new ItemStack(Material.APPLE,3));
			items.add(new ItemStack(Material.COOKIE,3));
			items.add(new ItemStack(Material.COOKIE));
			//items.add(new ItemStack(Material.MILK_BUCKET));

			Random rnd0 = new Random();
			int maximum = rnd0.nextInt(6);
			
			if(maximum == 0) maximum = 2;
			
			Random rnd1 = new Random();
			Random rnd2 = new Random();
			
			
			
			while(maximum != 0) {
				
				int slot = rnd1.nextInt(27);
				int itemindex = rnd2.nextInt(items.size());
				
				if(chestinv.getItem(slot) == null) {
					chestinv.setItem(slot, items.get(itemindex));
					maximum--;
				}
			}
			
			
			
			
			
			
			p.openInventory(chestinv);
			p.updateInventory();
			return;
			}
		}
		
		
	
	}

}

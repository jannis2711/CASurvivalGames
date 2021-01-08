package de.cyberanimals.survivalgames;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class WorldManager {
	
	public static World loadWorld(String world)
	{
		if(!isLoaded(world))
		{
			return Bukkit.getServer().createWorld(new WorldCreator(world));
		}
		else
		{
			return Bukkit.getWorld(world);
		}
	}
	
	public static boolean isLoaded(String world)
	{
		for (World w : Bukkit.getServer().getWorlds())
		{
			if (w.getName().equals(world)) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean unloadWorld(String world)
	{
		if(isLoaded(world))
		{
			World w = Bukkit.getWorld(world);
			for (Player p : w.getPlayers()) 
			{
				p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
			}
			
			for(Chunk c : w.getLoadedChunks())
			{
				c.unload();
			}

			boolean unload = Bukkit.unloadWorld(w, true);
			return unload;
		}
		
		return false;
	}
	
	private static void deleteDir(File dir) throws IOException
	{
		FileUtils.deleteDirectory(dir);
	}
	
	private static void copyDir(File src, File dir) throws IOException
	{
		FileUtils.copyDirectory(src, dir);
	}
	
	private static void moveDir(File src, File dir) throws IOException
	{
		FileUtils.moveDirectory(src, dir);
	}
	


}

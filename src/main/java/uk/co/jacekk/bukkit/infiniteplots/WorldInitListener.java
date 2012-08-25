package uk.co.jacekk.bukkit.infiniteplots;

import java.lang.reflect.Field;

import net.minecraft.server.WorldServer;
import net.minecraft.server.WorldType;

import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import uk.co.jacekk.bukkit.baseplugin.BaseListener;

public class WorldInitListener extends BaseListener<InfinitePlots> {
	
	public WorldInitListener(InfinitePlots plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onWorldInit(WorldInitEvent event){
		World world = event.getWorld();
		
		if (world.getGenerator() instanceof PlotsGenerator){
			WorldServer worldServer = ((CraftWorld) world).getHandle();
			
			try{
				Class<?> worldData = worldServer.worldData.getClass();
				
				Field type = worldData.getDeclaredField("type");
				type.setAccessible(true);
				
				type.set(worldServer.worldData, WorldType.FLAT);
				
				plugin.log.info("Changed the world type of '" + world.getName() + "' to flat (this makes the void blue down to y = 0).");
			}catch (Exception e){
				plugin.log.info("Could not change the world type of '" + world.getName() + "'.");
				e.printStackTrace();
			}
		}
	}
	
}

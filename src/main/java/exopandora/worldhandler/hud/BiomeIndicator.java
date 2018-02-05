package exopandora.worldhandler.hud;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class BiomeIndicator
{
	private static final Set<String> BIOMES;
	private static String CURRENT_BIOME;
	private static int TICKS;
	
	static
	{
		Set<String> pool = new HashSet<String>();
		
		for(ResourceLocation location : Biome.REGISTRY.getKeys())
		{
			pool.add(filterName(Biome.REGISTRY.getObject(location).getBiomeName()));
		}
		
		BIOMES = new HashSet<String>(pool);
		
		for(String biome : pool)
		{
			for(String index : pool)
			{
				if(index.matches(biome + "([A-Za-z ])+") || index.matches("([A-Za-z ])+ " + biome))
				{
					BIOMES.remove(index);
				}
			}
		}
		
		BIOMES.remove("River");
		BIOMES.remove("Beach");
	}
	
	public static void tick()
	{
        int posX = MathHelper.floor(Minecraft.getMinecraft().player.posX);
        int posY = MathHelper.floor(Minecraft.getMinecraft().player.posY);
        int posZ = MathHelper.floor(Minecraft.getMinecraft().player.posZ);
        
        BlockPos pos = new BlockPos(posX, posY, posZ);
        
        if(Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.isBlockLoaded(pos))
        {
            Chunk chunk = Minecraft.getMinecraft().world.getChunkFromBlockCoords(pos);
        	String biome = getBaseBiome(filterName(chunk.getBiome(pos, Minecraft.getMinecraft().world.getBiomeProvider()).getBiomeName()));
        	
        	if(TICKS == 0 && biome != null)
        	{
        		if(CURRENT_BIOME == null || !CURRENT_BIOME.equals(biome))
    			{
            		Minecraft.getMinecraft().ingameGUI.displayTitle(biome, null, 20, 60, 20);
            		TICKS = 100;
            		CURRENT_BIOME = biome;
    			}
        	}
        	else if(TICKS > 0)
        	{
        		TICKS--;
        	}
        }
	}
	
	private static String filterName(String biome)
	{
		return biome.replaceAll("([a-z])([A-Z])", "$1 $2").replaceAll("[^A-Za-z ]", "").replaceAll("( [A-Z])$", "");
	}
	
	@Nullable
	private static String getBaseBiome(String input)
	{
		for(String biome : BIOMES)
		{
			if(input.matches("([A-Za-z ])*" + biome + "([A-Za-z ])*"))
			{
				return biome;
			}
		}
		
		return null;
	}
}

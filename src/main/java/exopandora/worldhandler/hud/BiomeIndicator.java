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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BiomeIndicator
{
	private final Set<String> biomes = new HashSet<String>();
	private String currentBiome;
	private int ticksRemaining;
	
	public BiomeIndicator()
	{
		this.init();
	}
	
	private void init()
	{
		Set<String> biomes = new HashSet<String>();
		
		for(ResourceLocation location : Biome.REGISTRY.getKeys())
		{
			biomes.add(this.filterName(Biome.REGISTRY.getObject(location).getBiomeName()));
		}
		
		this.biomes.addAll(biomes);
		
		for(String biome : biomes)
		{
			for(String index : biomes)
			{
				if(index.matches(biome + "([A-Za-z ])+") || index.matches("([A-Za-z ])+ " + biome))
				{
					this.biomes.remove(index);
				}
			}
		}
		
		this.biomes.remove("River");
		this.biomes.remove("Beach");
	}
	
	public void tick()
	{
		int posX = MathHelper.floor(Minecraft.getMinecraft().player.posX);
		int posY = MathHelper.floor(Minecraft.getMinecraft().player.posY);
		int posZ = MathHelper.floor(Minecraft.getMinecraft().player.posZ);
		
		BlockPos pos = new BlockPos(posX, posY, posZ);
		
		if(Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().world.isBlockLoaded(pos))
		{
			Chunk chunk = Minecraft.getMinecraft().world.getChunkFromBlockCoords(pos);
			String biome = this.getBaseBiome(this.filterName(chunk.getBiome(pos, Minecraft.getMinecraft().world.getBiomeProvider()).getBiomeName()));
			
			if(this.ticksRemaining == 0 && biome != null)
			{
				if(this.currentBiome == null || !this.currentBiome.equals(biome))
				{
					Minecraft.getMinecraft().ingameGUI.displayTitle(biome, null, 20, 60, 20);
					this.ticksRemaining = 100;
					this.currentBiome = biome;
				}
			}
			else if(this.ticksRemaining > 0)
			{
				this.ticksRemaining--;
			}
		}
	}
	
	private String filterName(String biome)
	{
		return biome.replaceAll("([a-z])([A-Z])", "$1 $2").replaceAll("[^A-Za-z ]", "").replaceAll("( [A-Z])$", "");
	}
	
	@Nullable
	private String getBaseBiome(String input)
	{
		for(String biome : this.biomes)
		{
			if(input.matches("([A-Za-z ])*" + biome + "([A-Za-z ])*"))
			{
				return biome;
			}
		}
		
		return null;
	}
}

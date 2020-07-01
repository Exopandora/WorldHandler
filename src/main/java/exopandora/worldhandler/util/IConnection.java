package exopandora.worldhandler.util;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IConnection
{
	@OnlyIn(Dist.CLIENT)
	public static class IntegratedConnection implements IConnection
	{
		private final String folder;
		private final WorldSettings worldSettings;
		private final DimensionGeneratorSettings dimensionGeneratorSettings;
		
		public IntegratedConnection(String folderName, WorldSettings worldSettings, DimensionGeneratorSettings dimensionGeneratorSettings)
		{
			this.folder = folderName;
			this.worldSettings = worldSettings;
			this.dimensionGeneratorSettings = dimensionGeneratorSettings;
		}
		
		public String getFolder()
		{
			return this.folder;
		}
		
		public WorldSettings getWorldSettings()
		{
			return this.worldSettings;
		}
		
		public DimensionGeneratorSettings getDimensionGeneratorSettings()
		{
			return this.dimensionGeneratorSettings;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class DedicatedConnection implements IConnection
	{
		private final ServerData data;
		
		public DedicatedConnection(ServerData data)
		{
			this.data = data;
		}
		
		public ServerData getData()
		{
			return this.data;
		}
	}
}
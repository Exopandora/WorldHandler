package exopandora.worldhandler.util;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;

public interface IConnection
{
	public static class IntegratedConnection implements IConnection
	{
		private final String folder;
		private final LevelSettings levelSettings;
		private final WorldGenSettings worldGenSettings;
		
		public IntegratedConnection(String folderName, LevelSettings levelSettings, WorldGenSettings worldGenSettings)
		{
			this.folder = folderName;
			this.levelSettings = levelSettings;
			this.worldGenSettings = worldGenSettings;
		}
		
		public String getFolder()
		{
			return this.folder;
		}
		
		public LevelSettings getWorldSettings()
		{
			return this.levelSettings;
		}
		
		public WorldGenSettings getWorldGenSettings()
		{
			return this.worldGenSettings;
		}
	}
	
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
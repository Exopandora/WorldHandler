package exopandora.worldhandler.util;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class Connection
{
	private final Type type;
	
	public Connection(Type type)
	{
		this.type = type;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum Type
	{
		INTEGRATED,
		DEDICATED;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class IntegratedConnection extends Connection
	{
		private final String worldName;
		private final String folderName;
		
		public IntegratedConnection(Type type, String worldName, String folderName)
		{
			super(type);
			this.worldName = worldName;
			this.folderName = folderName;
		}
		
		public String getWorldName()
		{
			return this.worldName;
		}
		
		public String getFolderName()
		{
			return this.folderName;
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class DedicatedConnection extends Connection
	{
		private final ServerData data;
		
		public DedicatedConnection(Type type, ServerData data)
		{
			super(type);
			this.data = data;
		}
		
		public ServerData getData()
		{
			return this.data;
		}
	}
}
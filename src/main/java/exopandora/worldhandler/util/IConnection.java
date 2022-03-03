package exopandora.worldhandler.util;

import net.minecraft.client.multiplayer.ServerData;

public interface IConnection
{
	public static class IntegratedConnection implements IConnection
	{
		private final String folder;
		
		public IntegratedConnection(String folderName)
		{
			this.folder = folderName;
		}
		
		public String getFolder()
		{
			return this.folder;
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
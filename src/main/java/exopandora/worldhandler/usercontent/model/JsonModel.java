package exopandora.worldhandler.usercontent.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JsonModel
{
	@SerializedName("commands")
	private List<JsonCommand> commands = null;
	
	public JsonModel(List<JsonCommand> commands)
	{
		this.commands = commands;
	}
	
	public List<JsonCommand> getCommands()
	{
		return this.commands;
	}
	
	public void setCommands(List<JsonCommand> commands)
	{
		this.commands = commands;
	}
}

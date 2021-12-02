package exopandora.worldhandler.usercontent.model;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class JsonUsercontent
{
	@SerializedName("commands")
	private Map<String, JsonCommand> commands = null;
	
	@SerializedName("gui")
	private JsonGui gui;
	
	public JsonUsercontent(Map<String, JsonCommand> commands, JsonGui gui)
	{
		this.commands = commands;
		this.gui = gui;
	}
	
	public Map<String, JsonCommand> getCommands()
	{
		return this.commands;
	}
	
	public void setCommands(Map<String, JsonCommand> commands)
	{
		this.commands = commands;
	}
	
	public JsonGui getGui()
	{
		return this.gui;
	}
	
	public void setGui(JsonGui gui)
	{
		this.gui = gui;
	}
	
	public void validate() throws IllegalStateException
	{
		if(this.gui == null)
		{
			throw new IllegalStateException("gui is null");
		}
		
		this.gui.validate();
	}
}

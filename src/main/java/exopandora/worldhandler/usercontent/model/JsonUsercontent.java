package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

public class JsonUsercontent
{
	@SerializedName("model")
	private JsonModel model;
	
	@SerializedName("gui")
	private JsonGui gui;
	
	public JsonUsercontent(JsonModel model, JsonGui gui)
	{
		this.model = model;
		this.gui = gui;
	}
	
	public JsonModel getModel()
	{
		return this.model;
	}
	
	public void setModel(JsonModel model)
	{
		this.model = model;
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

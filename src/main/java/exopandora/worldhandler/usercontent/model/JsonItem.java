package exopandora.worldhandler.usercontent.model;

import com.google.gson.annotations.SerializedName;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JsonItem
{
	@SerializedName("id")
	private String id;
	
	@SerializedName("translation")
	private String translation;
	
	public JsonItem(String id, String translation)
	{
		this.id = id;
		this.translation = translation;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getTranslation()
	{
		return this.translation;
	}
	
	public void setTranslation(String translation)
	{
		this.translation = translation;
	}
}

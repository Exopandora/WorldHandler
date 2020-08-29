package exopandora.worldhandler.usercontent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.impl.BuilderUsercontent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UsercontentAPI
{
	private final Map<String, String> values = new HashMap<String, String>();
	private final List<BuilderUsercontent> builders;
	
	public UsercontentAPI(List<BuilderUsercontent> builders)
	{
		this.builders = builders;
	}
	
	@Nullable
	public String getValue(String id)
	{
		return this.values.get(id);
	}
	
	public void updateValue(String id, String value)
	{
		this.values.put(id, value);
	}
	
	public void addChatMessage(Object object)
	{
		if(object != null)
		{
			Minecraft.getInstance().ingameGUI.func_238450_a_(ChatType.CHAT, new StringTextComponent(object.toString()), Util.DUMMY_UUID);
		}
	}
	
	public void setCommandArgument(int command, int index, String object)
	{
		if(command < this.builders.size() && command >= 0)
		{
			this.builders.get(command).set(index, object);
		}
	}
	
	@Nullable
	public String getCommandArgument(int command, int index)
	{
		if(command < this.builders.size() && command >= 0)
		{
			return this.builders.get(command).get(index);
		}
		
		return null;
	}
}

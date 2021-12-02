package exopandora.worldhandler.usercontent;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.argument.IDeserializableArgument;
import exopandora.worldhandler.builder.impl.UsercontentCommandBuilder;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;

public class UsercontentAPI
{
	private final Map<String, String> values = new HashMap<String, String>();
	private final Map<String, UsercontentCommandBuilder> builders;
	
	public UsercontentAPI(Map<String, UsercontentCommandBuilder> builders)
	{
		this.builders = builders;
	}
	
	@Nullable
	public String getValue(String id)
	{
		return this.values.get(id);
	}
	
	public void setValue(String id, String value)
	{
		this.values.put(id, value);
	}
	
	public void addChatMessage(Object object)
	{
		Minecraft.getInstance().gui.handleChat(ChatType.CHAT, new TextComponent(object != null ? object.toString() : "null"), Util.NIL_UUID);
	}
	
	public void setArgument(String command, String argument, String value)
	{
		UsercontentCommandBuilder builder = this.builders.get(command);
		
		if(builder != null)
		{
			builder.setArgument(argument, value);
		}
	}
	
	@Nullable
	private IDeserializableArgument getArgumentInternal(String command, String argument)
	{
		UsercontentCommandBuilder builder = this.builders.get(command);
		
		if(builder != null)
		{
			return builder.getArgument(argument);
		}
		
		return null;
	}
	
	@Nullable
	public String getArgument(String command, String argument)
	{
		IDeserializableArgument result = this.getArgumentInternal(command, argument);
		
		if(result != null)
		{
			return result.serialize();
		}
		
		return null;
	}
}

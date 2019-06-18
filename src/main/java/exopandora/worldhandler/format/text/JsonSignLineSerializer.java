package exopandora.worldhandler.format.text;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import exopandora.worldhandler.format.EnumColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JsonSignLineSerializer implements JsonSerializer<JsonSignLine>
{
	@Override
	public JsonElement serialize(JsonSignLine src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject object = (JsonObject) new Gson().toJsonTree(src);
		
		if(src.getClickEvent() == null)
		{
			object.remove("clickEvent");
		}
		
		if(!src.isBold())
		{
			object.remove("bold");
		}
		
		if(!src.isStriked())
		{
			object.remove("strikethrough");
		}
		
		if(!src.isUnderlined())
		{
			object.remove("underlined");
		}
		
		if(!src.isItalic())
		{
			object.remove("italic");
		}
		
		if(!src.isObfuscated())
		{
			object.remove("obfuscated");
		}
		
		if(src.getColor() != null)
		{
			if(src.getColor().equals(EnumColor.DEFAULT.getName()))
			{
				object.remove("color");
			}
		}
		
        return object;
	}
}

package exopandora.worldhandler.builder.types;

import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum Type
{
	SHORT(Short::valueOf),
	BYTE(Byte::valueOf),
	INT(Integer::valueOf),
	FLOAT(Float::valueOf),
	DOUBLE(Double::valueOf),
	LONG(Long::valueOf),
	BOOLEAN(Boolean::valueOf),
	STRING(String::valueOf),
	RESOURCE_LOCATION(Type::parseResourceLocation),
	NBT(Type::parseNBTTagCompound),
	COORDINATE(Coordinate::valueOf),
	TARGET_SELECTOR(TargetSelector::valueOf),
	LEVEL(Level::valueOf);
	
	private final Function<String, Object> parser;
	
	private Type(Function<String, Object> parser)
	{
		this.parser = parser;
	}
	
	@Nullable
	public <T> T parse(String object)
	{
		return (T) this.parser.apply(object);
	}
	
	@Nullable
	public static ResourceLocation parseResourceLocation(String value)
	{
		return value != null ? new ResourceLocation(value) : null;
	}
	
	@Nullable
	public static NBTTagCompound parseNBTTagCompound(String value)
	{
		if(value != null)
		{
			try
	        {
				return JsonToNBT.getTagFromJson(value);
	        }
	        catch(NBTException nbtexception)
	        {
	    		return null;
	        }
		}
		
		return null;
	}
}

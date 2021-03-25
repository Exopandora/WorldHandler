package exopandora.worldhandler.builder.types;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum ArgumentType
{
	SHORT(Short::valueOf),
	BYTE(Byte::valueOf),
	INT(Integer::valueOf),
	FLOAT(Float::valueOf),
	DOUBLE(Double::valueOf),
	LONG(Long::valueOf),
	BOOLEAN(Boolean::valueOf),
	STRING(String::valueOf),
	GREEDY_STRING(GreedyString::valueOf),
	RESOURCE_LOCATION(ArgumentType::parseResourceLocation),
	ITEM_RESOURCE_LOCATION(ItemResourceLocation::valueOf),
	BLOCK_RESOURCE_LOCATION(BlockResourceLocation::valueOf),
	NBT(ArgumentType::parseCompoundNBT),
	COORDINATE_INT(CoordinateInt::valueOf),
	COORDINATE_DOUBLE(CoordinateDouble::valueOf),
	TARGET_SELECTOR(TargetSelector::valueOf),
	PLAYER(String::valueOf);
	
	private final Function<String, Object> parser;
	
	private ArgumentType(Function<String, Object> parser)
	{
		this.parser = parser;
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
	public <T> T parse(String object)
	{
		return (T) this.parser.apply(object);
	}
	
	public <T> T parseOfDefault(String object, T def)
	{
		try
		{
			return this.<T>parse(object);
		}
		catch(Exception e)
		{
			return def;
		}
	}
	
	@Nullable
	public static ResourceLocation parseResourceLocation(String value)
	{
		return value != null && !value.isEmpty() ? new ResourceLocation(value) : null;
	}
	
	@Nullable
	public static CompoundNBT parseCompoundNBT(String value)
	{
		if(value != null)
		{
			try
	        {
				return JsonToNBT.parseTag(value);
	        }
	        catch(CommandSyntaxException e)
	        {
	    		return null;
	        }
		}
		
		return null;
	}
}

package exopandora.worldhandler.builder.types;

import java.util.function.Function;

import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
	GREEDY_STRING(GreedyString::valueOf),
	RESOURCE_LOCATION(Type::parseResourceLocation),
	ITEM_RESOURCE_LOCATION(ItemResourceLocation::valueOf),
	BLOCK_RESOURCE_LOCATION(BlockResourceLocation::valueOf),
	NBT(Type::parseCompoundNBT),
	COORDINATE_INT(CoordinateInt::valueOf),
	COORDINATE_DOUBLE(CoordinateDouble::valueOf),
	TARGET_SELECTOR(TargetSelector::valueOf);
	
	private final Function<String, Object> parser;
	
	private Type(Function<String, Object> parser)
	{
		this.parser = parser;
	}
	
	@Nullable
	@SuppressWarnings("unchecked")
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
	public static CompoundNBT parseCompoundNBT(String value)
	{
		if(value != null)
		{
			try
	        {
				return JsonToNBT.getTagFromJson(value);
	        }
	        catch(CommandSyntaxException e)
	        {
	    		return null;
	        }
		}
		
		return null;
	}
}

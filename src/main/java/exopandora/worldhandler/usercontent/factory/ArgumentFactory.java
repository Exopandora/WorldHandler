package exopandora.worldhandler.usercontent.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.IDeserializableArgument;
import exopandora.worldhandler.usercontent.model.ArgumentType;
import exopandora.worldhandler.usercontent.model.JsonArgument;

public class ArgumentFactory
{
	private static final Map<ArgumentType, Supplier<IDeserializableArgument>> FACTORY = new HashMap<ArgumentType, Supplier<IDeserializableArgument>>();
	
	static
	{
		FACTORY.put(ArgumentType.SHORT, Arguments::shortArg);
		FACTORY.put(ArgumentType.BYTE, Arguments::byteArg);
		FACTORY.put(ArgumentType.INT, Arguments::intArg);
		FACTORY.put(ArgumentType.FLOAT, Arguments::floatArg);
		FACTORY.put(ArgumentType.DOUBLE, Arguments::doubleArg);
		FACTORY.put(ArgumentType.LONG, Arguments::longArg);
		FACTORY.put(ArgumentType.BOOLEAN, Arguments::boolArg);
		FACTORY.put(ArgumentType.WORD, Arguments::word);
		FACTORY.put(ArgumentType.STRING, Arguments::string);
		FACTORY.put(ArgumentType.GREEDY_STRING, Arguments::greedyString);
		FACTORY.put(ArgumentType.RESOURCE_LOCATION, Arguments::resourceLocation);
		FACTORY.put(ArgumentType.ITEM, Arguments::item);
		FACTORY.put(ArgumentType.BLOCKSTATE, Arguments::blockState);
		FACTORY.put(ArgumentType.BLOCKPREDICATE, Arguments::blockPredicate);
		FACTORY.put(ArgumentType.NBT, Arguments::tag);
		FACTORY.put(ArgumentType.COORDINATE_INT, Arguments::intCoordinate);
		FACTORY.put(ArgumentType.COORDINATE_DOUBLE, Arguments::doubleCoordinate);
		FACTORY.put(ArgumentType.PLAYER, Arguments::word);
		FACTORY.put(ArgumentType.RANGE_INT, Arguments::intRange);
		FACTORY.put(ArgumentType.RANGE_DOUBLE, Arguments::doubleRange);
		FACTORY.put(ArgumentType.ANGLE, Arguments::angle);
		FACTORY.put(ArgumentType.ENCHANTMENT, Arguments::enchantment);
		FACTORY.put(ArgumentType.ENTITY, Arguments::entitySummon);
		FACTORY.put(ArgumentType.GAMEMODE, Arguments::gamemode);
		FACTORY.put(ArgumentType.TIME, Arguments::time);
		FACTORY.put(ArgumentType.EFFECT, Arguments::effect);
		FACTORY.put(ArgumentType.AXIS, Arguments::axis);
		FACTORY.put(ArgumentType.ANCHOR, Arguments::anchor);
		FACTORY.put(ArgumentType.DIFFICULTY, Arguments::difficulty);
		FACTORY.put(ArgumentType.RENDER_TYPE, Arguments::renderType);
		FACTORY.put(ArgumentType.OPERATION, Arguments::operation);
		FACTORY.put(ArgumentType.TEXT_COMPONENT, Arguments::textComponent);
		FACTORY.put(ArgumentType.RELATION, Arguments::relation);
		FACTORY.put(ArgumentType.TYPE, Arguments::type);
		FACTORY.put(ArgumentType.LINKAGE, Arguments::linkage);
		FACTORY.put(ArgumentType.NBT_PATH, Arguments::nbtPath);
		FACTORY.put(ArgumentType.ITEM_PREDICATE, Arguments::itemPredicate);
		FACTORY.put(ArgumentType.CRITERIA, Arguments::criteria);
	}
	
	@Nullable
	public static IDeserializableArgument createArgument(JsonArgument json)
	{
		Supplier<IDeserializableArgument> supplier = FACTORY.get(json.getType());
		
		if(supplier == null)
		{
			return null;
		}
		
		return supplier.get();
	}
}

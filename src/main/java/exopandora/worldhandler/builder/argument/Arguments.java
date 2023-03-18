package exopandora.worldhandler.builder.argument;

import java.util.function.Function;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.builder.argument.PrimitiveArgument.Linkage;
import exopandora.worldhandler.builder.argument.PrimitiveArgument.Operation;
import exopandora.worldhandler.builder.argument.PrimitiveArgument.Relation;
import exopandora.worldhandler.builder.argument.PrimitiveArgument.Type;
import exopandora.worldhandler.util.EnumHelper;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;

public class Arguments
{
	public static PrimitiveArgument<Short> shortArg()
	{
		return PrimitiveArgument.builder(string ->
		{
			try
			{
				return Short.parseShort(string);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}).build();
	}
	
	public static PrimitiveArgument<Byte> byteArg()
	{
		return PrimitiveArgument.builder(string ->
		{
			try
			{
				return Byte.parseByte(string);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}).build();
	}
	
	public static PrimitiveArgument<Integer> intArg()
	{
		return PrimitiveArgument.builder(string ->
		{
			try
			{
				return Integer.parseInt(string);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}).build();
	}
	
	public static PrimitiveArgument<Float> floatArg()
	{
		return PrimitiveArgument.builder(string ->
		{
			try
			{
				return Float.parseFloat(string);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}).build();
	}
	
	public static PrimitiveArgument<Double> doubleArg()
	{
		return PrimitiveArgument.builder(string ->
		{
			try
			{
				return Double.parseDouble(string);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}).build();
	}
	
	public static PrimitiveArgument<Long> longArg()
	{
		return PrimitiveArgument.builder(string ->
		{
			try
			{
				return Long.parseLong(string);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}).build();
	}
	
	public static PrimitiveArgument<Boolean> boolArg()
	{
		return PrimitiveArgument.builder(string ->
		{
			try
			{
				return Boolean.parseBoolean(string);
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}).build();
	}
	
	public static PrimitiveArgument<String> string()
	{
		return PrimitiveArgument.builder(Function.identity())
				.serializer(string -> string.isBlank() ? null : StringArgumentType.escapeIfRequired(string))
				.build();
	}
	
	public static PrimitiveArgument<String> greedyString()
	{
		return PrimitiveArgument.builder(string -> string == null || string.isBlank() ? null : string)
				.serializer(string -> string.isBlank() ? null : string)
				.defaultOverride(string -> string == null || string.isBlank())
				.build();
	}
	
	public static PrimitiveArgument<String> word()
	{
		return PrimitiveArgument.builder(string -> string == null || string.isBlank() ? null : string)
				.serializer(string -> string.isBlank() ? null : string.replaceAll("[^0-9A-Za-z_\\-.+]", "_"))
				.defaultOverride(string -> string == null || string.isBlank())
				.build();
	}
	
	public static PrimitiveArgument<ResourceLocation> resourceLocation()
	{
		return PrimitiveArgument.builder(string -> string.isEmpty() ? null : new ResourceLocation(string)).build();
	}
	
	public static ItemArgument item()
	{
		return new ItemArgument();
	}
	
	public static BlockStateArgument blockState()
	{
		return new BlockStateArgument();
	}
	
	public static BlockPredicateArgument blockPredicate()
	{
		return new BlockPredicateArgument();
	}
	
	public static TagArgument tag()
	{
		return new TagArgument();
	}
	
	public static PrimitiveArgument<Coordinate<Integer>> intCoordinate()
	{
		return PrimitiveArgument.<Coordinate<Integer>>builder(Coordinate.Ints::parse).build();
	}
	
	public static PrimitiveArgument<Coordinate<Double>> doubleCoordinate()
	{
		return PrimitiveArgument.<Coordinate<Double>>builder(Coordinate.Doubles::parse).build();
	}
	
	public static RangeArgument<Integer> intRange()
	{
		return new RangeArgument<Integer>(string ->
		{
			try
			{
				return MinMaxBounds.Ints.fromReader(new StringReader(string));
			}
			catch(CommandSyntaxException e)
			{
				return null;
			}
		});
	}
	
	public static RangeArgument<Double> doubleRange()
	{
		return new RangeArgument<Double>(string ->
		{
			try
			{
				return MinMaxBounds.Doubles.fromReader(new StringReader(string));
			}
			catch(CommandSyntaxException e)
			{
				return null;
			}
		});
	}
	
	public static AngleArgument angle()
	{
		return new AngleArgument();
	}
	
	public static EnchantmentArgument enchantment()
	{
		return new EnchantmentArgument();
	}
	
	public static EntitySummonArgument entitySummon()
	{
		return new EntitySummonArgument();
	}
	
	public static PrimitiveArgument<Gamemode> gamemode()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Gamemode.values(), Gamemode::toString)).build();
	}
	
	public static TimeArgument time()
	{
		return new TimeArgument();
	}
	
	public static EffectArgument effect()
	{
		return new EffectArgument();
	}
	
	public static PrimitiveArgument<Axis> axis()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Axis.values(), Axis::getName))
				.serializer(Axis::getName)
				.build();
	}
	
	public static PrimitiveArgument<Anchor> anchor()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Anchor.values(), anchor -> anchor.name))
				.serializer(anchor -> anchor.name)
				.build();
	}
	
	public static PrimitiveArgument<Difficulty> difficulty()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Difficulty.values(), Difficulty::getKey))
				.serializer(Difficulty::getKey)
				.build();
	}
	
	public static PrimitiveArgument<RenderType> renderType()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, RenderType.values(), RenderType::getId))
				.serializer(RenderType::getId)
				.build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Operation> operation()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Operation.values(), Operation::toString)).build();
	}
	
	public static PrimitiveArgument<Component> textComponent()
	{
		return PrimitiveArgument.<Component>builder(string ->
		{
			try
			{
				return Component.Serializer.fromJson(string);
			}
			catch(Exception e)
			{
				return Component.literal(string);
			}
		}).serializer(Component.Serializer::toJson).build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Relation> relation()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Relation.values(), Relation::toString)).build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Type> type()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Type.values(), Type::toString)).build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Linkage> linkage()
	{
		return PrimitiveArgument.builder(string -> EnumHelper.find(string, Linkage.values(), Linkage::toString)).build();
	}
	
	public static NbtPathArgument nbtPath()
	{
		return new NbtPathArgument();
	}
	
	public static ItemPredicateArgument itemPredicate()
	{
		return new ItemPredicateArgument();
	}
	
	public static PrimitiveArgument<String> criteria()
	{
		return PrimitiveArgument.builder(string -> string == null || string.isBlank() ? null : string)
				.serializer(string -> string.isBlank() ? null : string.replaceAll(" ", "_"))
				.defaultOverride(string -> string == null || string.isBlank())
				.build();
	}
	
	public static TargetArgument target()
	{
		return new TargetArgument();
	}
	
	public static BlockPosArgument blockPos()
	{
		return new BlockPosArgument();
	}
	
	public static DimensionArgument dimension()
	{
		return new DimensionArgument();
	}
}

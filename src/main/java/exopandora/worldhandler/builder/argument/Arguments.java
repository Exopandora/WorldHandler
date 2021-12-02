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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.scores.criteria.ObjectiveCriteria.RenderType;

public class Arguments
{
	public static PrimitiveArgument<Short> shortArg()
	{
		return new PrimitiveArgument.Builder<Short>(string ->
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
		return new PrimitiveArgument.Builder<Byte>(string ->
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
		return new PrimitiveArgument.Builder<Integer>(string ->
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
		return new PrimitiveArgument.Builder<Float>(string ->
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
		return new PrimitiveArgument.Builder<Double>(string ->
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
		return new PrimitiveArgument.Builder<Long>(string ->
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
		return new PrimitiveArgument.Builder<Boolean>(string ->
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
		return new PrimitiveArgument.Builder<String>(Function.identity())
				.serializer(string -> string.isBlank() ? null : StringArgumentType.escapeIfRequired(string))
				.build();
	}
	
	public static PrimitiveArgument<String> greedyString()
	{
		return new PrimitiveArgument.Builder<String>(string -> string == null || string.isBlank() ? null : string)
				.serializer(string -> string.isBlank() ? null : string)
				.defaultOverride(string -> string == null || string.isBlank())
				.build();
	}
	
	public static PrimitiveArgument<String> word()
	{
		return new PrimitiveArgument.Builder<String>(string -> string == null || string.isBlank() ? null : string)
				.serializer(string -> string.isBlank() ? null : string.replaceAll("[^0-9A-Za-z_\\-.+]", "_"))
				.defaultOverride(string -> string == null || string.isBlank())
				.build();
	}
	
	public static PrimitiveArgument<ResourceLocation> resourceLocation()
	{
		return new PrimitiveArgument.Builder<ResourceLocation>(string -> string.isEmpty() ? null : new ResourceLocation(string)).build();
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
		return new PrimitiveArgument.Builder<Coordinate<Integer>>(Coordinate.Ints::parse).build();
	}
	
	public static PrimitiveArgument<Coordinate<Double>> doubleCoordinate()
	{
		return new PrimitiveArgument.Builder<Coordinate<Double>>(Coordinate.Doubles::parse).build();
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
		return new PrimitiveArgument.Builder<Gamemode>(string -> EnumHelper.find(string, Gamemode.values(), Gamemode::toString)).build();
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
		return new PrimitiveArgument.Builder<Axis>(string -> EnumHelper.find(string, Axis.values(), Axis::getName))
				.serializer(Axis::getName)
				.build();
	}
	
	public static PrimitiveArgument<Anchor> anchor()
	{
		return new PrimitiveArgument.Builder<Anchor>(string -> EnumHelper.find(string, Anchor.values(), anchor -> anchor.name))
				.serializer(anchor -> anchor.name)
				.build();
	}
	
	public static PrimitiveArgument<Difficulty> difficulty()
	{
		return new PrimitiveArgument.Builder<Difficulty>(string -> EnumHelper.find(string, Difficulty.values(), Difficulty::getKey))
				.serializer(Difficulty::getKey)
				.build();
	}
	
	public static PrimitiveArgument<RenderType> renderType()
	{
		return new PrimitiveArgument.Builder<RenderType>(string -> EnumHelper.find(string, RenderType.values(), RenderType::getId))
				.serializer(RenderType::getId)
				.build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Operation> operation()
	{
		return new PrimitiveArgument.Builder<Operation>(string -> EnumHelper.find(string, Operation.values(), Operation::toString)).build();
	}
	
	public static PrimitiveArgument<Component> textComponent()
	{
		return new PrimitiveArgument.Builder<Component>(string ->
		{
			try
			{
				return Component.Serializer.fromJson(string);
			}
			catch(Exception e)
			{
				return new TextComponent(string);
			}
		}).serializer(Component.Serializer::toJson).build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Relation> relation()
	{
		return new PrimitiveArgument.Builder<Relation>(string -> EnumHelper.find(string, Relation.values(), Relation::toString)).build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Type> type()
	{
		return new PrimitiveArgument.Builder<Type>(string -> EnumHelper.find(string, Type.values(), Type::toString)).build();
	}
	
	public static PrimitiveArgument<PrimitiveArgument.Linkage> linkage()
	{
		return new PrimitiveArgument.Builder<Linkage>(string -> EnumHelper.find(string, Linkage.values(), Linkage::toString)).build();
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
		return new PrimitiveArgument.Builder<String>(string -> string == null || string.isBlank() ? null : string)
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
}

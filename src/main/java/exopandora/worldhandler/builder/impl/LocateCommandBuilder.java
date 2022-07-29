package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandNode;
import exopandora.worldhandler.builder.CommandNodeLiteral;
import exopandora.worldhandler.builder.argument.Arguments;
import exopandora.worldhandler.builder.argument.PrimitiveArgument;
import net.minecraft.resources.ResourceLocation;

public class LocateCommandBuilder extends CommandBuilder
{
	private final PrimitiveArgument<ResourceLocation> biome = Arguments.resourceLocation();
	private final PrimitiveArgument<ResourceLocation> structure = Arguments.resourceLocation();
	private final PrimitiveArgument<ResourceLocation> poi = Arguments.resourceLocation();
	
	private final CommandNodeLiteral root = CommandNode.literal("locate")
			.then(CommandNode.literal("biome")
					.then(CommandNode.argument("biome", this.biome)
							.label(Label.BIOME)))
			.then(CommandNode.literal("structure")
					.then(CommandNode.argument("structure", this.structure)
							.label(Label.STRUCTURE)))
			.then(CommandNode.literal("poi")
					.then(CommandNode.argument("poi", this.poi)
							.label(Label.POI)));
	
	public PrimitiveArgument<ResourceLocation> biome()
	{
		return this.biome;
	}
	
	public PrimitiveArgument<ResourceLocation> structure()
	{
		return this.structure;
	}
	
	public PrimitiveArgument<ResourceLocation> poi()
	{
		return this.poi;
	}
	
	@Override
	protected CommandNodeLiteral root()
	{
		return this.root;
	}
	
	public static enum Label
	{
		BIOME,
		STRUCTURE,
		POI;
	}
}

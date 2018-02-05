package exopandora.worldhandler.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import exopandora.worldhandler.builder.impl.BuilderClone;
import exopandora.worldhandler.builder.impl.BuilderClone.EnumMask;
import exopandora.worldhandler.builder.impl.BuilderFill;
import exopandora.worldhandler.builder.impl.BuilderWH;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.helper.EnumHelper;
import exopandora.worldhandler.helper.ResourceHelper;
import exopandora.worldhandler.main.WorldHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CommandWH extends CommandBase
{
	@Override
	public String getName()
	{
		return "wh";
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if(args.length > 0)
		{
			if(args[0].equals("pos1"))
			{
				BlockHelper.setPos1(BlockHelper.getFocusedBlockPos());
				sender.sendMessage(new TextComponentString("Set first position to " + BlockHelper.getPos1().getX() + ", " + BlockHelper.getPos1().getY() + ", " + BlockHelper.getPos1().getZ() + " (" + Block.REGISTRY.getNameForObject(BlockHelper.getFocusedBlock()) + ")"));
			}
			else if(args[0].equals("pos2"))
			{
				BlockHelper.setPos2(BlockHelper.getFocusedBlockPos());
				sender.sendMessage(new TextComponentString("Set second position to " + BlockHelper.getPos2().getX() + ", " + BlockHelper.getPos2().getY() + ", " + BlockHelper.getPos2().getZ() + " (" + Block.REGISTRY.getNameForObject(BlockHelper.getFocusedBlock()) + ")"));
			}
			else if(args[0].equals("fill"))
			{
				final String usage = "/wh fill <block> [meta]";
				
				if(args.length > 1)
				{
					ResourceLocation id = new ResourceLocation(args[1]);
					int meta = 0;
					
					if(args.length > 2)
					{
						try
						{
							meta = Integer.parseInt(args[2]);
						}
						catch(Exception e)
						{
							throw new WrongUsageException(usage);
						}
					}
					
					if(!ResourceHelper.isRegisteredBlock(id.toString()))
					{
						throw new NumberInvalidException(usage);
					}
					
					BuilderFill builder = new BuilderFill();
					builder.setBlock1(id);
					builder.setMeta1(meta);
					
					WorldHandler.sendCommand(builder);
				}
				else
				{
					throw new WrongUsageException(usage);
				}
			}
			else if(args[0].equals("replace"))
			{
				final String usage = "/wh replace <block1> <meta1> <block2> [meta2]";
				
				if(args.length > 1)
				{
					ResourceLocation id1 = new ResourceLocation(args[1]);
					
					if(args.length > 2)
					{
						ResourceLocation id2 = new ResourceLocation(args[3]);
						int meta1 = 0;
						int meta2 = 0;
						
						try
						{
							meta1 = Integer.parseInt(args[2]);
						}
						catch(Exception e)
						{
							throw new WrongUsageException(usage);
						}
						
						if(args.length > 4)
						{
							try
							{
								meta2 = Integer.parseInt(args[4]);
							}
							catch(Exception e)
							{
								throw new WrongUsageException(usage);
							}
						}
						
						if(!ResourceHelper.isRegisteredBlock(id1.toString()) || !ResourceHelper.isRegisteredBlock(id2.toString()))
						{
							throw new WrongUsageException(usage);
						}
						
						BuilderFill builder = new BuilderFill();
						builder.setPosition1(BlockHelper.getPos1());
						builder.setPosition2(BlockHelper.getPos2());
						builder.setBlock1(id1);
						builder.setMeta1(meta1);
						builder.setBlock2(id2);
						builder.setMeta2(meta2);
						
						Minecraft.getMinecraft().player.sendChatMessage(builder.getBuilderForReplace().toActualCommand());
					}
					else
					{
						throw new WrongUsageException(usage);
					}
				}
				else
				{
					throw new WrongUsageException(usage);
				}
			}
			else if(args[0].equals("clone"))
			{
				final String usage = "/wh clone [" + String.join("|", Arrays.stream(EnumMask.MASKED.values()).map(EnumMask::toString).collect(Collectors.toList())) + "]";
				EnumMask mask = EnumMask.MASKED;
				
				if(args.length > 1)
				{
					mask = EnumHelper.valueOf(EnumMask.class, args[1]);
				}
				
				if(mask == null)
				{
					throw new WrongUsageException(usage);
				}
				
				BuilderClone builder = new BuilderClone();
				builder.setPosition1(BlockHelper.getPos1());
				builder.setPosition2(BlockHelper.getPos2());
				builder.setMask(mask);
				
				Minecraft.getMinecraft().player.sendChatMessage(builder.toActualCommand());
			}
			else
			{
				throw new WrongUsageException(new BuilderWH().toCommand());
			}
		}
		else
		{
			throw new WrongUsageException(new BuilderWH().toCommand());
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
	{
		if(args.length == 1)
		{
			return this.getListOfStringsMatchingLastWord(args, new String[]{"pos1", "pos2", "fill", "replace", "clone"});
		}
		else if(args.length == 2)
		{
			if(args[0].equals("fill") || args[0].equals("replace"))
			{
				return this.getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys());
			}
			else if(args[0].equals("clone"))
			{
				return this.getListOfStringsMatchingLastWord(args, new String[]{"replace", "masked", "filtered"});
			}
		}
		else if(args.length == 3)
		{
			if(args[0].equals("fill") || args[0].equals("replace"))
			{
				return this.getListOfStringsMatchingLastWord(args, new String[] {"0"});
			}
		}
		else if(args.length == 4)
		{
			if(args[0].equals("replace"))
			{
				return this.getListOfStringsMatchingLastWord(args, Block.REGISTRY.getKeys());
			}
		}
		
		return Collections.<String>emptyList();
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return new BuilderWH().toCommand();
	}
}
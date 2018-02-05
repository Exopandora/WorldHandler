package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderDoubleBlockPos;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.helper.BlockHelper;
import exopandora.worldhandler.helper.EnumHelper;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderFill extends BuilderDoubleBlockPos
{
	public BuilderFill(ResourceLocation block1, EnumBlockHandling handling, ResourceLocation block2)
	{
		this.setPosition1(BlockHelper.getPos1());
		this.setPosition2(BlockHelper.getPos2());
		this.setBlock1(block1);
		this.setMeta1(0);
		this.setBlockHandling(handling);
		this.setBlock2(block2);
	}
	
	public BuilderFill()
	{
		this.init();
	}
	
	private void init()
	{
		this.setMeta1(0);
	}
	
	@Deprecated
	public void setMeta1(int meta)
	{
		this.setNode(7, meta);
	}
	
	@Deprecated
	public void setMeta2(int meta)
	{
		this.setNode(10, meta);
	}
	
	public void setBlock1(String block)
	{
		this.setBlock1(ResourceHelper.stringToResourceLocationNullable(block, ResourceHelper::isRegisteredBlock));
	}
	
	public void setBlock1(ResourceLocation location)
	{
		this.setNode(6, location);
	}
	
	public ResourceLocation getBlock1()
	{
		return this.getNodeAsResourceLocation(6);
	}
	
	public String getBlock1String()
	{
		ResourceLocation location = this.getBlock1();
		
		if(location != null)
		{
			return location.toString();
		}
		
		return null;
	}
	
	public void setBlockHandling(EnumBlockHandling blockHandling)
	{
		this.setNode(8, blockHandling != null ? blockHandling.toString() : null);
	}
	
	public void setBlock2(String block)
	{
		this.setBlock2(ResourceHelper.stringToResourceLocationNullable(block, ResourceHelper::isRegisteredBlock));
	}
	
	public void setBlock2(ResourceLocation location)
	{
		this.setNode(9, location);
	}
	
	public String getBlock2String()
	{
		ResourceLocation location = this.getBlock2();
		
		if(location != null)
		{
			return location.toString();
		}
		
		return null;
	}
	
	public ResourceLocation getBlock2()
	{
		return this.getNodeAsResourceLocation(9);
	}
	
	@Nullable
	public EnumBlockHandling getBlockHandling()
	{
		return EnumHelper.valueOf(EnumBlockHandling.class, this.getNodeAsString(8));
	}
	
	public BuilderFill getBuilderForFill()
	{
		return new BuilderFill(this.getBlock1(), null, null);
	}
	
	public BuilderFill getBuilderForReplace()
	{
		return new BuilderFill(this.getBlock2(), EnumBlockHandling.REPLACE, this.getBlock1());
	}
	
	@Override
	public String getCommandName()
	{
		return "fill";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("x1", Type.COORDINATE);
		syntax.addRequired("y1", Type.COORDINATE);
		syntax.addRequired("z1", Type.COORDINATE);
		syntax.addRequired("x2", Type.COORDINATE);
		syntax.addRequired("y2", Type.COORDINATE);
		syntax.addRequired("z2", Type.COORDINATE);
		syntax.addRequired("block", Type.RESOURCE_LOCATION);
		syntax.addOptional("data_value", Type.INT);
		syntax.addOptional("old_block_handling", Type.STRING);
		syntax.addOptional("block_2|nbt", Type.RESOURCE_LOCATION, "block_2|nbt");
		syntax.addOptional("data_value", Type.INT, "data_value");
		
		return syntax;
	}
	
	public static enum EnumBlockHandling
	{
		REPLACE,
		DESTROY,
		KEEP,
		HOLLOW,
		OUTLINE;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}

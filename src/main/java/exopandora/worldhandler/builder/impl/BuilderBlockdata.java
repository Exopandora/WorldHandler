package exopandora.worldhandler.builder.impl;

import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.impl.abstr.BuilderBlockPos;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderBlockdata extends BuilderBlockPos
{
	@Override
	public void setNBT(NBTTagCompound nbt)
	{
		this.setNode(3, nbt);
	}
	
	@Override
	public String getCommandName()
	{
		return "blockdata";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("x", Type.COORDINATE);
		syntax.addRequired("y", Type.COORDINATE);
		syntax.addRequired("z", Type.COORDINATE);
		syntax.addRequired("nbt", Type.NBT);
		
		return syntax;
	}
}

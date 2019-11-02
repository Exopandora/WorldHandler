package exopandora.worldhandler.builder.component.impl;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.util.MutableStringTextComponent;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentDisplay implements IBuilderComponent 
{
	private MutableStringTextComponent name = new MutableStringTextComponent();
	private String[] lore = new String[2];
	
	@Override
	public INBT serialize()
	{
		CompoundNBT display = new CompoundNBT();
		
		if(this.name.getText() != null && !this.name.getText().isEmpty())
		{
			display.putString("Name", ITextComponent.Serializer.toJson(new StringTextComponent(this.name.toString())));
		}
		
		ListNBT lore = new ListNBT();
		
		for(int x = 0; x < this.lore.length; x++)
		{
			if(this.lore[x] != null && !this.lore[x].isEmpty())
			{
				lore.add(new StringNBT(this.lore[x]));
			}
		}
		
		if(!lore.isEmpty())
		{
			display.put("Lore", lore);
		}
		
		if(!display.isEmpty())
		{
			return display;
		}
		
		return null;
	}
	
	public void setName(MutableStringTextComponent name)
	{
		this.name = name;
	}
	
	public MutableStringTextComponent getName()
	{
		return this.name;
	}
	
	public void setLore(String[] lore)
	{
		this.lore = lore;
	}
	
	public String[] getLore()
	{
		return this.lore;
	}
	
	public void setLore1(String lore)
	{
		this.lore[0] = lore;
	}
	
	public String getLore1()
	{
		return this.lore[0];
	}
	
	public void setLore2(String lore)
	{
		this.lore[1] = lore;
	}
	
	public String getLore2()
	{
		return this.lore[1];
	}

	@Override
	public String getTag()
	{
		return "display";
	}
}

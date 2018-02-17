package exopandora.worldhandler.builder.impl;

import java.util.Set;

import exopandora.worldhandler.builder.component.impl.ComponentAttributeItem;
import exopandora.worldhandler.builder.component.impl.ComponentDisplay;
import exopandora.worldhandler.builder.component.impl.ComponentEnchantment;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes.Applyable;
import exopandora.worldhandler.format.text.ColoredString;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderCustomItem extends BuilderGive
{
	private final ComponentAttributeItem attribute;
	private final ComponentDisplay display;
	private final ComponentEnchantment enchantment;
	
	public BuilderCustomItem()
	{
		this(null, null);
	}
	
	public BuilderCustomItem(String username, ResourceLocation item)
	{
		this.setPlayer(username);
		this.setItem(item);
		this.setAmount(1);
		this.setMetadata(0);
		this.attribute = this.registerNBTComponent(new ComponentAttributeItem(attribute -> attribute.getApplyable().equals(Applyable.BOTH) || attribute.getApplyable().equals(Applyable.PLAYER)));
		this.display = this.registerNBTComponent(new ComponentDisplay());
		this.enchantment = this.registerNBTComponent(new ComponentEnchantment());
	}
	
	public void setEnchantment(Enchantment enchantment, short level)
	{
		this.enchantment.setLevel(enchantment, level);
	}
	
	public short getEnchantmentLevel(Enchantment enchantment)
	{
		return this.enchantment.getLevel(enchantment);
	}
	
	public Set<Enchantment> getEnchantments()
	{
		return this.enchantment.getEnchantments();
	}
	
	public void setAttribute(EnumAttributes attribute, double ammount)
	{
		this.attribute.set(attribute, ammount);
	}
	
	public void removeAttribute(EnumAttributes attribute)
	{
		this.attribute.remove(attribute);
	}
	
	public double getAttributeAmmount(EnumAttributes attribute)
	{
		return this.attribute.getAmmount(attribute);
	}
	
	public Set<EnumAttributes> getAttributes()
	{
		return this.attribute.getAttributes();
	}
	
	public void setName(ColoredString name)
	{
		this.display.setName(name);
	}
	
	public ColoredString getName()
	{
		return this.display.getName();
	}
	
	public void setLore1(String lore)
	{
		this.display.setLore1(lore);
	}
	
	public String getLore1()
	{
		return this.display.getLore1();
	}
	
	public void setLore2(String lore)
	{
		this.display.setLore2(lore);
	}
	
	public String getLore2()
	{
		return this.display.getLore2();
	}
}

package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentArgument implements IDeserializableArgument
{
	private Enchantment enchantment;
	
	protected EnchantmentArgument()
	{
		super();
	}
	
	public void set(@Nullable Enchantment enchantment)
	{
		this.enchantment = enchantment;
	}
	
	public void set(@Nullable ResourceLocation enchantment)
	{
		if(enchantment != null)
		{
			this.set(ForgeRegistries.ENCHANTMENTS.getValue(enchantment));
		}
		else
		{
			this.enchantment = null;
		}
	}
	
	@Nullable
	public Enchantment getEnchantment()
	{
		return this.enchantment;
	}
	
	@Override
	public void deserialize(@Nullable String string)
	{
		this.set(ResourceHelper.stringToResourceLocation(string));
	}
	
	@Override
	@Nullable
	public String serialize()
	{
		if(this.enchantment == null)
		{
			return null;
		}
		
		return ForgeRegistries.ENCHANTMENTS.getKey(this.enchantment).toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return false;
	}
}

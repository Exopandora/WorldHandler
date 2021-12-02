package exopandora.worldhandler.builder.argument;

import javax.annotation.Nullable;

import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectArgument implements IDeserializableArgument
{
	private MobEffect effect;
	
	protected EffectArgument()
	{
		super();
	}
	
	public void set(@Nullable MobEffect effect)
	{
		this.effect = effect;
	}
	
	public void set(@Nullable ResourceLocation effect)
	{
		if(effect != null)
		{
			this.set(ForgeRegistries.MOB_EFFECTS.getValue(effect));
		}
		else
		{
			this.effect = null;
		}
	}
	
	@Nullable
	public MobEffect getEffect()
	{
		return this.effect;
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
		if(this.effect == null)
		{
			return null;
		}
		
		return this.effect.getRegistryName().toString();
	}
	
	@Override
	public boolean isDefault()
	{
		return this.effect == null;
	}
}

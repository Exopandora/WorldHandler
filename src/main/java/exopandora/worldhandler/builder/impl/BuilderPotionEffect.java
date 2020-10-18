package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.component.impl.EffectNBT;
import exopandora.worldhandler.builder.types.ArgumentType;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class BuilderPotionEffect extends CommandBuilder
{
	private int seconds;
	private int minutes;
	private int hours;
	
	public BuilderPotionEffect()
	{
		this(null, null, null);
	}
	
	public BuilderPotionEffect(EnumMode mode, String player, ResourceLocation effect)
	{
		this(mode, player, effect, 0, (byte) 0, false);
	}
	
	public BuilderPotionEffect(EnumMode mode, String player, ResourceLocation effect, int duration, byte amplifier, boolean hideParticles)
	{
		this.setMode(mode);
		this.setPlayer(player);
		this.setEffect(effect);
		this.setDuration(duration);
		this.setAmplifier(amplifier);
		this.setHideParticles(hideParticles);
	}
	
	public void setMode(EnumMode mode)
	{
		if(mode != null)
		{
			this.setNode(0, mode.toString());
		}
	}
	
	public void setPlayer(String player)
	{
		this.setNode(1, player);
	}
	
	@Nullable
	public String getPlayer()
	{
		return this.getNodeAsString(1);
	}
	
	public void setEffect(Effect effect)
	{
		this.setEffect(effect.getRegistryName());
	}
	
	public void setEffect(ResourceLocation effect)
	{
		this.setNode(2, effect);
	}
	
	@Nullable
	public Effect getEffectAsPotion()
	{
		ResourceLocation location = this.getNodeAsResourceLocation(2);
		
		if(location != null)
		{
			return ForgeRegistries.POTIONS.getValue(location);
		}
		
		return null;
	}
	
	@Nullable
	public ResourceLocation getEffect()
	{
		return this.getNodeAsResourceLocation(2);
	}
	
	public void setDuration(int duration)
	{
		this.setNode(3, Math.min(duration, 1000000));
	}
	
	public int getDuration()
	{
		return this.getNodeAsInt(3);
	}
	
	public void setAmplifier(byte amplifier)
	{
		this.setNode(4, (byte) (amplifier - 1));
	}
	
	public int getAmplifier()
	{
		return this.getNodeAsByte(4);
	}
	
	public void setHideParticles(boolean hideParticles)
	{
		this.setNode(5, hideParticles);
	}
	
	public boolean getHideParticles()
	{
		return this.getNodeAsBoolean(5);
	}
	
	public int getSeconds()
	{
		return this.seconds;
	}
	
	public void setSeconds(int seconds)
	{
		this.seconds = seconds;
		this.setDuration(EffectNBT.toSeconds(this.seconds, this.minutes, this.hours));
	}
	
	public int getMinutes()
	{
		return this.minutes;
	}
	
	public void setMinutes(int minutes)
	{
		this.minutes = minutes;
		this.setDuration(EffectNBT.toSeconds(this.seconds, this.minutes, this.hours));
	}
	
	public int getHours()
	{
		return this.hours;
	}
	
	public void setHours(int hours)
	{
		this.hours = hours;
		this.setDuration(EffectNBT.toSeconds(this.seconds, this.minutes, this.hours));
	}
	
	public BuilderGeneric getGiveCommand()
	{
		return new BuilderGeneric(this.getCommandName(), EnumMode.GIVE.toString(), this.getPlayer(), this.getEffect().toString(), String.valueOf(this.getDuration()), String.valueOf(this.getAmplifier()), String.valueOf(this.getHideParticles()));
	}
	
	public BuilderGeneric getRemoveCommand()
	{
		return new BuilderGeneric(this.getCommandName(), EnumMode.CLEAR.toString(), this.getPlayer(), this.getEffect().toString());
	}
	
	public BuilderGeneric getClearCommand()
	{
		return new BuilderGeneric(this.getCommandName(), EnumMode.CLEAR.toString(), this.getPlayer());
	}
	
	@Override
	public String getCommandName()
	{
		return "effect";
	}
	
	@Override
	public final CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("give|clear", ArgumentType.STRING);
		syntax.addRequired("player", ArgumentType.STRING);
		syntax.addRequired("effect", ArgumentType.RESOURCE_LOCATION);
		syntax.addOptional("seconds", ArgumentType.INT, 0);
		syntax.addOptional("amplifier", ArgumentType.BYTE, (byte) -1);
		syntax.addOptional("hideParticles", ArgumentType.BOOLEAN, false);
		
		return syntax;
	}
	
	@OnlyIn(Dist.CLIENT)
	public static enum EnumMode
	{
		GIVE,
		CLEAR;
		
		@Override
		public String toString()
		{
			return this.name().toLowerCase();
		}
	}
}

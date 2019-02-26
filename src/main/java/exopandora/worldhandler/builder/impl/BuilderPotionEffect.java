package exopandora.worldhandler.builder.impl;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilder;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.component.abstr.PotionMetadata;
import exopandora.worldhandler.builder.types.Type;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BuilderPotionEffect extends CommandBuilder
{
	private int seconds;
	private int minutes;
	private int hours;
	
	public BuilderPotionEffect()
	{
		this(null, null);
	}
	
	public BuilderPotionEffect(String player, ResourceLocation effect)
	{
		this(player, effect, 0, (byte) 0, false);
	}
	
	public BuilderPotionEffect(String player, ResourceLocation effect, int duration, byte amplifier, boolean hideParticles)
	{
		this.setPlayer(player);
		this.setEffect(effect);
		this.setDuration(duration);
		this.setAmplifier(amplifier);
		this.setHideParticles(hideParticles);
	}
	
	public void setPlayer(String player)
	{
		this.setNode(0, player);
	}
	
	public String getPlayer()
	{
		return this.getNodeAsString(0);
	}
	
	public void setEffect(ResourceLocation effect)
	{
		this.setNode(1, effect);
	}
	
	@Nullable
	public Potion getEffectAsPotion()
	{
		ResourceLocation location = this.getNodeAsResourceLocation(1);
		
		if(location != null)
		{
			return Potion.getPotionFromResourceLocation(location.toString());
		}
		
		return null;
	}
	
	public ResourceLocation getEffect()
	{
		return this.getNodeAsResourceLocation(1);
	}
	
	public void setDuration(int duration)
	{
		this.setNode(2, Math.min(duration, 1000000));
	}
	
	public int getDuration()
	{
		return this.getNodeAsInt(2);
	}
	
	public void setAmplifier(byte amplifier)
	{
		this.setNode(3, (byte) (amplifier - 1));
	}
	
	public int getAmplifier()
	{
		return this.getNodeAsByte(3);
	}
	
	public void setHideParticles(boolean hideParticles)
	{
		this.setNode(4, hideParticles);
	}
	
	public boolean getHideParticles()
	{
		return this.getNodeAsBoolean(4);
	}
	
	public int getSeconds()
	{
		return this.seconds;
	}
	
	public void setSeconds(int seconds)
	{
		this.seconds = seconds;
		this.setDuration(PotionMetadata.getDurationSeconds(this.seconds, this.minutes, this.hours));
	}
	
	public int getMinutes()
	{
		return this.minutes;
	}
	
	public void setMinutes(int minutes)
	{
		this.minutes = minutes;
		this.setDuration(PotionMetadata.getDurationSeconds(this.seconds, this.minutes, this.hours));
	}
	
	public int getHours()
	{
		return this.hours;
	}
	
	public void setHours(int hours)
	{
		this.hours = hours;
		this.setDuration(PotionMetadata.getDurationSeconds(this.seconds, this.minutes, this.hours));
	}
	
	public BuilderGeneric getRemoveCommand()
	{
		return new BuilderGeneric(this.getCommandName(), this.getPlayer(), this.getEffect().toString(), "0");
	}
	
	public BuilderGeneric getClearCommand()
	{
		return new BuilderGeneric(this.getCommandName(), this.getPlayer(), "clear");
	}
	
	@Override
	public String getCommandName()
	{
		return "effect";
	}
	
	@Override
	public final Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("player", Type.STRING);
		syntax.addRequired("effect", Type.RESOURCE_LOCATION);
		syntax.addOptional("seconds", Type.INT, 0);
		syntax.addOptional("amplifier", Type.BYTE, (byte) -1);
		syntax.addOptional("hideParticles", Type.BOOLEAN, false);
		
		return syntax;
	}
}

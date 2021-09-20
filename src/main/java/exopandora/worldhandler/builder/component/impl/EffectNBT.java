package exopandora.worldhandler.builder.component.impl;

import exopandora.worldhandler.builder.INBTWritable;
import net.minecraft.nbt.CompoundTag;

public class EffectNBT implements INBTWritable
{
	private byte amplifier;
	private int seconds;
	private int minutes;
	private int hours;
	private boolean showParticles;
	private boolean ambient;
	
	public EffectNBT()
	{
		this((byte) 0, 0, 0, 0, true, false);
	}
	
	public EffectNBT(byte amplifier, int seconds, int minutes, int hours, boolean showParticles, boolean ambient)
	{
		this.amplifier = amplifier;
		this.seconds = seconds;
		this.minutes = minutes;
		this.hours = hours;
		this.showParticles = showParticles;
		this.ambient = ambient;
	}
	
	public byte getAmplifier()
	{
		return this.amplifier;
	}
	
	public void setAmplifier(byte amplifier)
	{
		this.amplifier = amplifier;
	}
	
	public int getSeconds()
	{
		return this.seconds;
	}
	
	public void setSeconds(int seconds)
	{
		this.seconds = seconds;
	}
	
	public int getMinutes()
	{
		return this.minutes;
	}
	
	public void setMinutes(int minutes)
	{
		this.minutes = minutes;
	}
	
	public int getHours()
	{
		return this.hours;
	}
	
	public void setHours(int hours)
	{
		this.hours = hours;
	}
	
	public boolean getShowParticles()
	{
		return this.showParticles;
	}
	
	public void setShowParticles(boolean showParticles)
	{
		this.showParticles = showParticles;
	}
	
	public boolean getAmbient()
	{
		return this.ambient;
	}
	
	public void setAmbient(boolean ambient)
	{
		this.ambient = ambient;
	}
	
	public int toTicks()
	{
		return EffectNBT.toTicks(this.seconds, this.minutes, this.hours);
	}
	
	public int toSeconds()
	{
		return EffectNBT.toSeconds(this.seconds, this.minutes, this.hours);
	}
	
	@Override
	public CompoundTag serialize()
	{
		CompoundTag compound = new CompoundTag();
		int ticks = this.toTicks();
		
		compound.putByte("Amplifier", (byte) (this.amplifier - 1));
		compound.putInt("Duration", ticks > 0 ? ticks : 1000000);
		compound.putBoolean("Ambient", this.ambient);
		compound.putBoolean("ShowParticles", this.showParticles);
		
		return compound;
	}
	
	public static int toTicks(int seconds, int minutes, int hours)
	{
		return seconds * 20 + minutes * 1200 + hours * 72000;
	}
	
	public static int toSeconds(int seconds, int minutes, int hours)
	{
		return seconds + minutes * 60 + hours * 3600;
	}
	
	public EffectNBT withAmplifier(byte amplifier)
	{
		this.amplifier = amplifier;
		return this;
	}
	
	public EffectNBT withShowParticles(boolean showParticles)
	{
		this.showParticles = showParticles;
		return this;
	}
	
	public EffectNBT withSeconds(int seconds)
	{
		this.seconds = seconds;
		return this;
	}
	
	public EffectNBT withMinutes(int minutes)
	{
		this.minutes = minutes;
		return this;
	}
	
	public EffectNBT withHours(int hours)
	{
		this.hours = hours;
		return this;
	}
	
	public EffectNBT withAmbient(boolean ambient)
	{
		this.ambient = ambient;
		return this;
	}
	
	@Override
	public String toString()
	{
		return "EffectNBT [amplifier=" + amplifier + ", seconds=" + seconds + ", minutes=" + minutes + ", hours=" + hours + ", showParticles=" + showParticles + ", ambient=" + ambient + "]";
	}
}


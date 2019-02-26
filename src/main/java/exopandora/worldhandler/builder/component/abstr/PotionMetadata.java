package exopandora.worldhandler.builder.component.abstr;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PotionMetadata
{
	private byte amplifier;
	private int seconds;
	private int minutes;
	private int hours;
	private boolean showParticles;
	private boolean ambient;
	
	public PotionMetadata()
	{
		this((byte) 0, 0, 0, 0, true, false);
	}
	
	public PotionMetadata(byte amplifier, int seconds, int minutes, int hours, boolean showParticles, boolean ambient)
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
	
	public int getDurationTicks()
	{
		return PotionMetadata.getDurationTicks(this.seconds, this.minutes, this.hours);
	}
	
	public int getDurationSeconds()
	{
		return PotionMetadata.getDurationSeconds(this.seconds, this.minutes, this.hours);
	}
	
	public static int getDurationTicks(int seconds, int minutes, int hours)
	{
		return seconds * 20 + minutes * 1200 + hours * 72000;
	}
	
	public static int getDurationSeconds(int seconds, int minutes, int hours)
	{
		return seconds + minutes * 60 + hours * 3600;
	}
	
	public PotionMetadata withAmplifier(byte amplifier)
	{
		this.amplifier = amplifier;
		return this;
	}
	
	public PotionMetadata withShowParticles(boolean showParticles)
	{
		this.showParticles = showParticles;
		return this;
	}
	
	public PotionMetadata withSeconds(int seconds)
	{
		this.seconds = seconds;
		return this;
	}
	
	public PotionMetadata withMinutes(int minutes)
	{
		this.minutes = minutes;
		return this;
	}
	
	public PotionMetadata withHours(int hours)
	{
		this.hours = hours;
		return this;
	}
	
	public PotionMetadata withAmbient(boolean ambient)
	{
		this.ambient = ambient;
		return this;
	}
}


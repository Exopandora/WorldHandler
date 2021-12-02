package exopandora.worldhandler.builder.argument.tag;

public class EffectInstance
{
	private byte amplifier;
	private int seconds;
	private int minutes;
	private int hours;
	private boolean showParticles;
	private boolean ambient;
	
	public EffectInstance()
	{
		this((byte) 0, 0, 0, 0, true, false);
	}
	
	public EffectInstance(byte amplifier, int seconds, int minutes, int hours, boolean showParticles, boolean ambient)
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
	
	public boolean doShowParticles()
	{
		return this.showParticles;
	}
	
	public void setShowParticles(boolean showParticles)
	{
		this.showParticles = showParticles;
	}
	
	public boolean isAmbient()
	{
		return this.ambient;
	}
	
	public void setAmbient(boolean ambient)
	{
		this.ambient = ambient;
	}
	
	public int toTicks()
	{
		return EffectInstance.toTicks(this.seconds, this.minutes, this.hours);
	}
	
	public int toSeconds()
	{
		return EffectInstance.toSeconds(this.seconds, this.minutes, this.hours);
	}
	
	public static int toTicks(int seconds, int minutes, int hours)
	{
		return seconds * 20 + minutes * 1200 + hours * 72000;
	}
	
	public static int toSeconds(int seconds, int minutes, int hours)
	{
		return seconds + minutes * 60 + hours * 3600;
	}
}
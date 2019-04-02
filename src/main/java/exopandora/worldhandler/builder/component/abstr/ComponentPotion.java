package exopandora.worldhandler.builder.component.abstr;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ComponentPotion implements IBuilderComponent 
{
	protected final Map<Potion, PotionMetadata> potions = new HashMap<Potion, PotionMetadata>();
	
	@Override
	@Nullable
	public INBTBase serialize()
	{
		NBTTagList list = new NBTTagList();
		
		for(Entry<Potion, PotionMetadata> entry : this.potions.entrySet())
		{
			PotionMetadata potion = entry.getValue();
			
			if(potion.getAmplifier() > 0)
			{
				NBTTagCompound compound = new NBTTagCompound();
				
				compound.setByte("Id", (byte) Potion.getIdFromPotion(entry.getKey()));
				compound.setByte("Amplifier", (byte) (potion.getAmplifier() - 1));
				compound.setInt("Duration", Math.min(potion.toTicks(), 1000000));
				compound.setBoolean("Ambient", potion.getAmbient());
				compound.setBoolean("ShowParticles", potion.getShowParticles());
				
				list.add(compound);
			}
		}
		
		if(list.isEmpty())
		{
			return null;
		}
		
		return list;
	}
	
	public void setAmplifier(Potion potion, byte amplifier)
	{
		this.getMetadata(potion).setAmplifier(amplifier);
	}
	
	public byte getAmplifier(Potion potion)
	{
		return this.getMetadata(potion).getAmplifier();
	}
	
	public void setSeconds(Potion potion, int seconds)
	{
		this.getMetadata(potion).setSeconds(seconds);
	}
	
	public int getSeconds(Potion potion)
	{
		return this.getMetadata(potion).getSeconds();
	}
	
	public void setMinutes(Potion potion, int minutes)
	{
		this.getMetadata(potion).setMinutes(minutes);
	}
	
	public int getMinutes(Potion potion)
	{
		return this.getMetadata(potion).getMinutes();
	}
	
	public void setHours(Potion potion, int hours)
	{
		this.getMetadata(potion).setHours(hours);
	}
	
	public int getHours(Potion potion)
	{
		return this.getMetadata(potion).getHours();
	}
	
	public void setShowParticles(Potion potion, boolean showParticles)
	{
		this.getMetadata(potion).setShowParticles(showParticles);
	}
	
	public boolean getShowParticles(Potion potion)
	{
		return this.getMetadata(potion).getShowParticles();
	}
	
	public void setAmbient(Potion potion, boolean ambient)
	{
		this.getMetadata(potion).setAmbient(ambient);
	}
	
	public boolean getAmbient(Potion potion)
	{
		return this.getMetadata(potion).getAmbient();
	}
	
	private PotionMetadata getMetadata(Potion potion)
	{
		return this.potions.get(this.validate(potion));
	}
	
	private Potion validate(Potion potion)
	{
		if(!this.potions.containsKey(potion))
		{
			this.potions.put(potion, new PotionMetadata());
		}
		
		return potion;
	}
	
	public Set<Potion> getPotions()
	{
		return this.potions.keySet();
	}
	
	public void remove(Potion potion)
	{
		this.potions.remove(potion);
	}
}

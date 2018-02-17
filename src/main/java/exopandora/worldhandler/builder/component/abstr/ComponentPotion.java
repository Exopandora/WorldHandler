package exopandora.worldhandler.builder.component.abstr;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ComponentPotion implements IBuilderComponent 
{
	protected final Map<Potion, PotionMetadata> potions = Potion.REGISTRY.getKeys().stream().collect(Collectors.toMap(Potion.REGISTRY::getObject, key -> new PotionMetadata()));
	
	@Override
	@Nullable
	public NBTBase serialize()
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
				compound.setInteger("Duration", potion.getDuration() > 0 ? Math.min(potion.getDuration(), 1000000) : 1000000);
				compound.setBoolean("Ambient", potion.getAmbient());
				compound.setBoolean("ShowParticles", potion.getShowParticles());
				
				list.appendTag(compound);
			}
		}
		
		if(!list.hasNoTags())
		{
			return list;
		}
		
		return null;
	}
	
	public void set(Potion potion, PotionMetadata metadata)
	{
		this.potions.put(potion, metadata);
	}
	
	public void set(Potion potion, byte amplifier, int seconds, int minutes, int hours, boolean showParticles, boolean ambient)
	{
		this.set(potion, new PotionMetadata(amplifier, seconds, minutes, hours, showParticles, ambient));
	}
	
	public void setAmplifier(Potion potion, byte amplifier)
	{
		if(this.potions.containsKey(potion))
		{
			this.potions.get(potion).setAmplifier(amplifier);
		}
	}
	
	public byte getAmplifier(Potion potion)
	{
		if(this.potions.containsKey(potion))
		{
			return this.potions.get(potion).getAmplifier();
		}
		
		return 0;
	}
	
	public void setSeconds(Potion potion, int seconds)
	{
		if(this.potions.containsKey(potion))
		{
			this.potions.get(potion).setSeconds(seconds);;
		}
	}
	
	public int getSeconds(Potion potion)
	{
		if(this.potions.containsKey(potion))
		{
			return this.potions.get(potion).getSeconds();
		}
		
		return 0;
	}
	
	public void setMinutes(Potion potion, int minutes)
	{
		if(this.potions.containsKey(potion))
		{
			this.potions.get(potion).setSeconds(minutes);;
		}
	}
	
	public int getMinutes(Potion potion)
	{
		if(this.potions.containsKey(potion))
		{
			return this.potions.get(potion).getMinutes();
		}
		
		return 0;
	}
	
	public void setHours(Potion potion, int hours)
	{
		if(this.potions.containsKey(potion))
		{
			this.potions.get(potion).setSeconds(hours);;
		}
	}
	
	public int getHours(Potion potion)
	{
		if(this.potions.containsKey(potion))
		{
			return this.potions.get(potion).getHours();
		}
		
		return 0;
	}
	
	public void setShowParticles(Potion potion, boolean showParticles)
	{
		if(this.potions.containsKey(potion))
		{
			this.potions.get(potion).setShowParticles(showParticles);
		}
	}
	
	public boolean getShowParticles(Potion potion)
	{
		if(this.potions.containsKey(potion))
		{
			return this.potions.get(potion).getShowParticles();
		}
		
		return true;
	}
	
	public void setAmbient(Potion potion, boolean ambient)
	{
		if(this.potions.containsKey(potion))
		{
			this.potions.get(potion).setAmbient(ambient);
		}
	}
	
	public boolean getAmbient(Potion potion)
	{
		if(this.potions.containsKey(potion))
		{
			return this.potions.get(potion).getAmbient();
		}
		
		return false;
	}
	
	public Set<Potion> getPotions()
	{
		return this.potions.keySet();
	}
	
	@Nullable
	public PotionMetadata get(Potion potion)
	{
		return this.potions.get(potion);
	}
	
	public void remove(Potion potion)
	{
		this.potions.remove(potion);
	}
}

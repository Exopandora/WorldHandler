package exopandora.worldhandler.builder.component.impl;

import java.util.Random;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.helper.ResourceHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ComponentSummon implements IBuilderComponent
{
	private final Random random = new Random();
	
	private String tag;
	private String name;
	private ResourceLocation entity;
	private boolean hasPassenger; 
	
	public void setEntity(ResourceLocation entityName)
	{
		this.entity = entityName;
	}
	
	public ResourceLocation getEntity()
	{
		return this.entity;
	}
	
	public void setHasPassenger(boolean hasPassenger)
	{
		this.hasPassenger = hasPassenger;
	}
	
	public boolean hasPassenger()
	{
		return this.hasPassenger;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public INBTBase serialize()
	{
		if(this.name != null)
		{
			if(this.name.equalsIgnoreCase("Cat"))
			{
				this.tag = "CatType";
				return new NBTTagInt(this.random.nextInt(3) + 1);
			}
			else if(this.name.equalsIgnoreCase("Farmer") || this.name.equalsIgnoreCase("Fisherman") || this.name.equalsIgnoreCase("Shepherd") || this.name.equalsIgnoreCase("Fletcher"))
			{
				this.tag = "Profession";
				return new NBTTagInt(0);
			}
			else if(this.name.equalsIgnoreCase("Librarian") || this.name.equalsIgnoreCase("Carthographer"))
			{
				this.tag = "Profession";
				return new NBTTagInt(1);
			}
			else if(this.name.equalsIgnoreCase("Cleric") || this.name.equalsIgnoreCase("Priest"))
			{
				this.tag = "Profession";
				return new NBTTagInt(2);
			}
			else if(this.name.equalsIgnoreCase("Armorer") || this.name.equalsIgnoreCase("Blacksmith") || this.name.equalsIgnoreCase("WeaponSmith") || this.name.equalsIgnoreCase("ToolSmith"))
			{
				this.tag = "Profession";
				return new NBTTagInt(3);
			}
			else if(this.name.equalsIgnoreCase("Butcher") || this.name.equalsIgnoreCase("Leatherworker"))
			{
				this.tag = "Profession";
				return new NBTTagInt(4);
			}
			else if(this.name.equalsIgnoreCase("Nitwit"))
			{
				this.tag = "Profession";
				return new NBTTagInt(5);
			}
			
			if(this.entity != null)
			{
				if(this.entity.equals(EntityType.ZOMBIE.getRegistryName()))
				{
					if(StringUtils.containsIgnoreCase(this.name, "Baby"))
					{
						this.tag = "IsBaby";
						return new NBTTagByte((byte) 1);
					}
				}
				else if(this.entity.equals(EntityType.CHICKEN.getRegistryName()))
				{
					if(StringUtils.containsIgnoreCase(this.name, "Jockey") && !this.hasPassenger)
					{
						NBTTagCompound passenger = new NBTTagCompound();
						NBTTagList list = new NBTTagList();
						
						passenger.setString("id", EntityType.ZOMBIE.getRegistryName().toString());
						passenger.setBoolean("IsBaby", true);
						list.add(passenger);
						
						this.tag = "Passengers";
						return list;
					}
				}
				else if(this.entity.equals(EntityType.SPIDER.getRegistryName()))
				{
					if(StringUtils.containsIgnoreCase(this.name, "Jockey") && !this.hasPassenger)
					{
						NBTTagCompound passenger = new NBTTagCompound();
						NBTTagList list = new NBTTagList();
						
						passenger.setString("id", EntityType.SKELETON.getRegistryName().toString());
						list.add(passenger);
						
						this.tag = "Passengers";
						return list;
					}
				}
			}
		}
		
		return null;
	}
	
	@Override
	public String getTag()
	{
		return this.tag;
	}
	
	@Nullable
	public static ResourceLocation resolve(String entityName)
	{
		String name = ResourceHelper.stripToResourceLocation(entityName);
		
		for(EntityType<?> type : ForgeRegistries.ENTITIES.getValues())
		{
			if(type.isSummonable() && entityName.equalsIgnoreCase(I18n.format(type.getTranslationKey())))
			{
				return type.getRegistryName();
			}
		}
		
		String entity = name.replaceAll("_", "");
		
		if(entity.equalsIgnoreCase("RedCow"))
		{
			return EntityType.MOOSHROOM.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("ChickenJockey"))
		{
			return EntityType.CHICKEN.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Pigman") || entity.equalsIgnoreCase("ZombiePig") || entity.equalsIgnoreCase("ZombiePigman"))
		{
			return EntityType.ZOMBIE_PIGMAN.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Wither"))
		{
			return EntityType.WITHER.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Dog"))
		{
			return EntityType.WOLF.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Dragon"))
		{
			return EntityType.ENDER_DRAGON.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("minecraft:SnowGolem"))
		{
			return EntityType.SNOW_GOLEM.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Horse") || entity.equalsIgnoreCase("ZombieHorse") || entity.equalsIgnoreCase("SkeletonHorse"))
		{
			return EntityType.HORSE.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("LavaCube")|| entity.equalsIgnoreCase("MagmaSlime") || entity.equalsIgnoreCase("MagmaCube"))
		{
			return EntityType.MAGMA_CUBE.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("SpiderJockey"))
		{
			return EntityType.SPIDER.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("IronGolem"))
		{
			return EntityType.IRON_GOLEM.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Ozelot") || entity.equals("Ocelot") || entity.equalsIgnoreCase("Cat") || entity.equalsIgnoreCase("Kitty") || entity.equalsIgnoreCase("Kitten"))
		{
			return EntityType.OCELOT.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("TESTIFICATE") || entity.equalsIgnoreCase("Blacksmith") || entity.equalsIgnoreCase("Farmer") || entity.equalsIgnoreCase("Fisherman") || entity.equalsIgnoreCase("Shepherd") || entity.equalsIgnoreCase("Fletcher") || entity.equalsIgnoreCase("Librarian") || entity.equalsIgnoreCase("Cleric") || entity.equalsIgnoreCase("Priest") || entity.equalsIgnoreCase("Armorer") || entity.equalsIgnoreCase("WeaponSmith") || entity.equalsIgnoreCase("ToolSmith") || entity.equalsIgnoreCase("Butcher") || entity.equalsIgnoreCase("Leatherworker") || entity.equalsIgnoreCase("Carthographer") || entity.equalsIgnoreCase("Nitwit"))
		{
			return EntityType.VILLAGER.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Octopus") || entity.equalsIgnoreCase("Kraken"))
		{
			return EntityType.SQUID.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Exwife"))
		{
			return EntityType.GHAST.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("TNTMinecart"))
		{
			return EntityType.TNT_MINECART.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Minecart"))
		{
			return EntityType.MINECART.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("HopperMinecart"))
		{
			return EntityType.HOPPER_MINECART.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("ChestMinecart"))
		{
			return EntityType.CHEST_MINECART.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("SpawnerMinecart"))
		{
			return EntityType.SPAWNER_MINECART.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("FurnaceMinecart"))
		{
			return EntityType.FURNACE_MINECART.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("CommandBlockMinecart") || entity.equalsIgnoreCase("MinecartCommand") || entity.equalsIgnoreCase("CommandMinecart"))
		{
			return EntityType.COMMAND_BLOCK_MINECART.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Wizard"))
		{
			return EntityType.EVOKER.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("Johnny"))
		{
			return EntityType.VINDICATOR.getRegistryName();
		}
		else if(entity.equalsIgnoreCase("BabyZombie"))
		{
			return EntityType.ZOMBIE.getRegistryName();
		}
		
		if(entity == null || entity.isEmpty())
		{
			return null;
		}
		
		return ResourceHelper.stringToResourceLocation(name);
	}
}

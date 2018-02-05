package exopandora.worldhandler.builder.component.impl;

import java.util.Random;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.helper.EntityHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	public NBTBase serialize()
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
				if(this.entity.equals(EntityHelper.getResourceLocation(EntityZombie.class)))
				{
					if(StringUtils.containsIgnoreCase(this.name, "Baby"))
					{
						this.tag = "IsBaby";
						return new NBTTagByte((byte) 1);
					}
				}
				else if(this.entity.equals(EntityHelper.getResourceLocation(EntityChicken.class)))
				{
					if(StringUtils.containsIgnoreCase(this.name, "Jockey") && !this.hasPassenger)
					{
						NBTTagCompound passenger = new NBTTagCompound();
						NBTTagList list = new NBTTagList();
						
						passenger.setString("id", EntityHelper.getResourceLocation(EntityZombie.class).toString());
						passenger.setBoolean("IsBaby", true);
						list.appendTag(passenger);
						
						this.tag = "Passengers";
						return list;
					}
				}
				else if(this.entity.equals(EntityHelper.getResourceLocation(EntitySpider.class)))
				{
					if(StringUtils.containsIgnoreCase(this.name, "Jockey") && !this.hasPassenger)
					{
						NBTTagCompound passenger = new NBTTagCompound();
						NBTTagList list = new NBTTagList();
						
						passenger.setString("id", EntityHelper.getResourceLocation(EntitySkeleton.class).toString());
						list.appendTag(passenger);
						
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
		String entity = entityName.replaceAll("_| ", "");
		
		for(ResourceLocation location : EntityList.ENTITY_EGGS.keySet())
		{
			if(entityName.equalsIgnoreCase(I18n.format("entity." + EntityHelper.getEntityName(location) + ".name")))
			{
				entity = location.getResourcePath();
				break;
			}
		}
		
		if(entity.equalsIgnoreCase("RedCow"))
		{
			return EntityHelper.getResourceLocation(EntityMooshroom.class);
		}
		else if(entity.equalsIgnoreCase("ChickenJockey"))
		{
			return EntityHelper.getResourceLocation(EntityChicken.class);
		}
		else if(entity.equalsIgnoreCase("Pigman") || entity.equalsIgnoreCase("ZombiePig") || entity.equalsIgnoreCase("ZombiePigman"))
		{
			return EntityHelper.getResourceLocation(EntityPigZombie.class);
		}
		else if(entity.equalsIgnoreCase("Wither"))
		{
			return EntityHelper.getResourceLocation(EntityWither.class);
		}
		else if(entity.equalsIgnoreCase("Dog"))
		{
			return EntityHelper.getResourceLocation(EntityWolf.class);
		}
		else if(entity.equalsIgnoreCase("Dragon"))
		{
			return EntityHelper.getResourceLocation(EntityDragon.class);
		}
		else if(entity.equalsIgnoreCase("minecraft:SnowGolem"))
		{
			return EntityHelper.getResourceLocation(EntitySnowman.class);
		}
		else if(entity.equalsIgnoreCase("Horse") || entity.equalsIgnoreCase("ZombieHorse") || entity.equalsIgnoreCase("SkeletonHorse"))
		{
			return EntityHelper.getResourceLocation(EntityHorse.class);
		}
		else if(entity.equalsIgnoreCase("LavaCube")|| entity.equalsIgnoreCase("MagmaSlime") || entity.equalsIgnoreCase("MagmaCube"))
		{
			return EntityHelper.getResourceLocation(EntityMagmaCube.class);
		}
		else if(entity.equalsIgnoreCase("SpiderJockey"))
		{
			return EntityHelper.getResourceLocation(EntitySpider.class);
		}
		else if(entity.equalsIgnoreCase("IronGolem"))
		{
			return EntityHelper.getResourceLocation(EntityIronGolem.class);
		}
		else if(entity.equalsIgnoreCase("Ozelot") || entity.equals("Ocelot") || entity.equalsIgnoreCase("Cat") || entity.equalsIgnoreCase("Kitty") || entity.equalsIgnoreCase("Kitten"))
		{
			return EntityHelper.getResourceLocation(EntityOcelot.class);
		}
		else if(entity.equalsIgnoreCase("TESTIFICATE") || entity.equalsIgnoreCase("Blacksmith") || entity.equalsIgnoreCase("Farmer") || entity.equalsIgnoreCase("Fisherman") || entity.equalsIgnoreCase("Shepherd") || entity.equalsIgnoreCase("Fletcher") || entity.equalsIgnoreCase("Librarian") || entity.equalsIgnoreCase("Cleric") || entity.equalsIgnoreCase("Priest") || entity.equalsIgnoreCase("Armorer") || entity.equalsIgnoreCase("WeaponSmith") || entity.equalsIgnoreCase("ToolSmith") || entity.equalsIgnoreCase("Butcher") || entity.equalsIgnoreCase("Leatherworker") || entity.equalsIgnoreCase("Carthographer") || entity.equalsIgnoreCase("Nitwit"))
		{
			return EntityHelper.getResourceLocation(EntityVillager.class);
		}
		else if(entity.equalsIgnoreCase("Octopus") || entity.equalsIgnoreCase("Kraken"))
		{
			return EntityHelper.getResourceLocation(EntitySquid.class);
		}
		else if(entity.equalsIgnoreCase("Exwife"))
		{
			return EntityHelper.getResourceLocation(EntityGhast.class);
		}
		else if(entity.equalsIgnoreCase("TNTMinecart"))
		{
			return EntityHelper.getResourceLocation(EntityMinecartTNT.class);
		}
		else if(entity.equalsIgnoreCase("Minecart"))
		{
			return EntityHelper.getResourceLocation(EntityMinecart.class);
		}
		else if(entity.equalsIgnoreCase("HopperMinecart"))
		{
			return EntityHelper.getResourceLocation(EntityMinecartHopper.class);
		}
		else if(entity.equalsIgnoreCase("ChestMinecart"))
		{
			return EntityHelper.getResourceLocation(EntityMinecartChest.class);
		}
		else if(entity.equalsIgnoreCase("SpawnerMinecart"))
		{
			return EntityHelper.getResourceLocation(EntityMinecartMobSpawner.class);
		}
		else if(entity.equalsIgnoreCase("FurnaceMinecart"))
		{
			return EntityHelper.getResourceLocation(EntityMinecartFurnace.class);
		}
		else if(entity.equalsIgnoreCase("CommandBlockMinecart") || entity.equalsIgnoreCase("MinecartCommand") || entity.equalsIgnoreCase("CommandMinecart"))
		{
			return EntityHelper.getResourceLocation(EntityMinecartCommandBlock.class);
		}
		else if(entity.equalsIgnoreCase("Wizard"))
		{
			return EntityHelper.getResourceLocation(EntityEvoker.class);
		}
		else if(entity.equalsIgnoreCase("Johnny"))
		{
			return EntityHelper.getResourceLocation(EntityVindicator.class);
		}
		else if(entity.equalsIgnoreCase("BabyZombie"))
		{
			return EntityHelper.getResourceLocation(EntityZombie.class);
		}
		
		if(entity == null || entity.isEmpty())
		{
			return null;
		}
		
		return new ResourceLocation(entity);
	}
}

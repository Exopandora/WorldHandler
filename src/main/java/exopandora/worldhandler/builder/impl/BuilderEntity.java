package exopandora.worldhandler.builder.impl;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.component.impl.EntityNBT;
import exopandora.worldhandler.util.MutableTextComponent;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class BuilderEntity extends CommandBuilderNBT
{
	private final EntityNBT nbt = new EntityNBT();
	
	public abstract void setEntity(ResourceLocation entity);
	
	public abstract ResourceLocation getEntity();
	
	public void setName(String name)
	{
		this.setEntity(BuilderEntity.parseEntityName(name));
		this.updateCustomComponent(name);
	}
	
	public void setNameAndId(String name)
	{
		this.setName(name);
		this.nbt.setId(this.getEntity());
	}
	
	public void setId(ResourceLocation resource)
	{
		this.nbt.setId(resource);
	}
	
	public ResourceLocation getId()
	{
		return this.nbt.getId();
	}
	
	public void setAttribute(Attribute attribute, double ammount)
	{
		this.nbt.setAttribute(attribute, ammount);
	}
	
	public void removeAttribute(Attribute attribute)
	{
		this.nbt.removeAttribute(attribute);
	}
	
	public double getAttributeAmmount(Attribute attribute)
	{
		return this.nbt.getAttributeAmmount(attribute);
	}
	
	public Set<Attribute> getAttributes()
	{
		return this.nbt.getAttributes();
	}
	
	public void setCustomName(String name)
	{
		this.nbt.setCustomName(name);
	}
	
	@Nullable
	public MutableTextComponent getCustomName()
	{
		return this.nbt.getCustomName();
	}
	
	public void setPassenger(int index, String name)
	{
		this.nbt.setPassenger(index, BuilderEntity.parseEntityName(name));
	}
	
	public void setPassenger(int index, EntityNBT entity)
	{
		this.nbt.setPassenger(index, entity);
	}
	
	public void setPassenger(int index, ResourceLocation id)
	{
		this.setPassenger(index, new EntityNBT(id));
	}
	
	public void addPassenger(EntityNBT entity)
	{
		this.nbt.addPassenger(entity);
	}
	
	public void addPassenger(int index, EntityNBT entity)
	{
		this.nbt.addPassenger(index, entity);
	}
	
	public void removePassenger(int index)
	{
		this.nbt.removePassenger(index);
	}
	
	public int getPassengerCount()
	{
		return this.nbt.getPassengerCount();
	}
	
	public List<EntityNBT> getPassengers()
	{
		return this.nbt.getPassengers();
	}
	
	@Nullable
	public EntityNBT getPassenger(int index)
	{
		return this.nbt.getPassenger(index);
	}
	
	public boolean hasPassengers()
	{
		return this.nbt.hasPassengers();
	}

	public void setArmorItem(int index, Block block)
	{
		this.nbt.setArmorItem(index, block);
	}
	
	public void setArmorItem(int index, Item item)
	{
		this.nbt.setArmorItem(index, item);
	}
	
	public void setArmorItem(int index, ResourceLocation location)
	{
		this.nbt.setArmorItem(index, location);
	}
	
	public void setArmorItems(ResourceLocation[] armor)
	{
		this.nbt.setArmorItems(armor);
	}
	
	public ResourceLocation getArmorItem(int slot)
	{
		return this.nbt.getArmorItem(slot);
	}
	
	public void setHandItem(int index, Block block)
	{
		this.nbt.setHandItem(index, block);
	}
	
	public void setHandItem(int index, Item item)
	{
		this.nbt.setHandItem(index, item);
	}
	
	public void setHandItem(int index, ResourceLocation location)
	{
		this.nbt.setHandItem(index, location);
	}
	
	public ResourceLocation getHandItem(int slot)
	{
		return this.nbt.getHandItem(slot);
	}
	
	public double[] getMotion()
	{
		return this.nbt.getMotion();
	}
	
	public void setMotion(double x, double y, double z)
	{
		this.nbt.setMotion(x, y, z);
	}
	
	public double getMotionX()
	{
		return this.nbt.getMotionX();
	}
	
	public double getMotionY()
	{
		return this.nbt.getMotionY();
	}
	
	public double getMotionZ()
	{
		return this.nbt.getMotionZ();
	}
	
	public void setMotionX(double x)
	{
		this.nbt.setMotionX(x);
	}
	
	public void setMotionY(double y)
	{
		this.nbt.setMotionY(y);
	}
	
	public void setMotionZ(double z)
	{
		this.nbt.setMotionZ(z);
	}
	
	public void setAmplifier(MobEffect potion, byte amplifier)
	{
		this.nbt.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(MobEffect potion, int seconds)
	{
		this.nbt.setSeconds(potion, seconds);
	}
	
	public void setMinutes(MobEffect potion, int minutes)
	{
		this.nbt.setMinutes(potion, minutes);
	}
	
	public void setHours(MobEffect potion, int hours)
	{
		this.nbt.setHours(potion, hours);
	}
	
	public void setShowParticles(MobEffect potion, boolean showParticles)
	{
		this.nbt.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(MobEffect potion, boolean ambient)
	{
		this.nbt.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(MobEffect potion)
	{
		return this.nbt.getAmplifier(potion);
	}

	public int getSeconds(MobEffect potion)
	{
		return this.nbt.getSeconds(potion);
	}
	
	public int getMinutes(MobEffect potion)
	{
		return this.nbt.getMinutes(potion);
	}
	
	public int getHours(MobEffect potion)
	{
		return this.nbt.getHours(potion);
	}
	
	public boolean getShowParticles(MobEffect potion)
	{
		return this.nbt.getShowParticles(potion);
	}
	
	public boolean getAmbient(MobEffect potion)
	{
		return this.nbt.getAmbient(potion);
	}
	
	public Set<MobEffect> getMobEffects()
	{
		return this.nbt.getEffects();
	}
	
	public void setBlockState(BlockState blockState)
	{
		this.nbt.setBlockState(blockState);
	}
	
	public BlockState getBlockState()
	{
		return this.nbt.getBlockState();
	}
	
	public void setTime(int time)
	{
		this.nbt.setTime(time);
	}
	
	public int getTime()
	{
		return this.nbt.getTime();
	}
	
	public void setCommand(String command)
	{
		this.nbt.setCommand(command);
	}
	
	public String getCommand()
	{
		return this.nbt.getCommand();
	}
	
	public void setEntityNBT(String nbt)
	{
		this.nbt.setNBT(nbt);
	}
	
	public void setEntityNBT(CompoundTag nbt)
	{
		this.nbt.setNBT(nbt);
	}
	
	public CompoundTag getEntityNBT()
	{
		return this.nbt.getNBT();
	}
	
	@Override
	protected CompoundTag buildNBT()
	{
		return this.nbt.serialize();
	}
	
	private void updateCustomComponent(String name)
	{
		ResourceLocation entity = this.getEntity();
		
		if(name != null && entity != null)
		{
			if(entity.equals(EntityType.CAT.getRegistryName()))
			{
				this.nbt.setCustomComponent("CatType", IntTag.valueOf(new Random().nextInt(11)));
			}
			else if(entity.equals(EntityType.VILLAGER.getRegistryName()))
			{
				for(VillagerProfession profession : ForgeRegistries.PROFESSIONS)
				{
					if(StringUtils.equalsIgnoreCase(name, profession.toString()))
					{
						CompoundTag villagerData = new CompoundTag();
						villagerData.putString("profession", profession.getRegistryName().toString());
						
						this.nbt.setCustomComponent("VillagerData", villagerData);
						break;
					}
				}
			}
			else if(entity.equals(EntityType.ZOMBIE.getRegistryName()))
			{
				if(StringUtils.containsIgnoreCase(name, "Baby"))
				{
					this.nbt.setCustomComponent("IsBaby", ByteTag.valueOf((byte) 1));
				}
			}
			else if(entity.equals(EntityType.CHICKEN.getRegistryName()))
			{
				if(StringUtils.containsIgnoreCase(name, "Jockey") && !this.nbt.hasPassengers())
				{
					ListTag list = new ListTag();
					EntityNBT zombie = new EntityNBT(EntityType.ZOMBIE.getRegistryName());
					
					zombie.setIsBaby(true);
					list.add(zombie.serialize());
					this.nbt.setCustomComponent("Passengers", list);
				}
			}
			else if(entity.equals(EntityType.SPIDER.getRegistryName()))
			{
				if(StringUtils.containsIgnoreCase(name, "Jockey") && !this.nbt.hasPassengers())
				{
					ListTag list = new ListTag();
					EntityNBT skeleton = new EntityNBT(EntityType.SKELETON.getRegistryName());
					
					skeleton.setHandItem(0, Items.BOW);
					list.add(skeleton.serialize());
					
					this.nbt.setCustomComponent("Passengers", list);
				}
			}
			else
			{
				this.nbt.resetCustomComponent();
			}
		}
		else
		{
			this.nbt.resetCustomComponent();
		}
	}
	
	@Nullable
	public static ResourceLocation parseEntityName(String entityName)
	{
		String name = ResourceHelper.stripToResourceLocation(entityName);
		
		if(name == null || name.isEmpty())
		{
			return null;
		}
		
		for(EntityType<?> type : ForgeRegistries.ENTITIES.getValues())
		{
			if(type.canSummon() && entityName.equalsIgnoreCase(I18n.get(type.getDescriptionId())))
			{
				return type.getRegistryName();
			}
		}
		
		String entity = name.replaceAll("_", "");
		
		if("RedCow".equalsIgnoreCase(entity))
		{
			return EntityType.MOOSHROOM.getRegistryName();
		}
		else if("ChickenJockey".equalsIgnoreCase(entity))
		{
			return EntityType.CHICKEN.getRegistryName();
		}
		else if("Pigman".equalsIgnoreCase(entity) || "ZombiePig".equalsIgnoreCase(entity) || "ZombiePigman".equalsIgnoreCase(entity))
		{
			return EntityType.PIGLIN.getRegistryName();
		}
		else if("Dog".equalsIgnoreCase(entity))
		{
			return EntityType.WOLF.getRegistryName();
		}
		else if("Dragon".equalsIgnoreCase(entity))
		{
			return EntityType.ENDER_DRAGON.getRegistryName();
		}
		else if("SnowMan".equalsIgnoreCase(entity))
		{
			return EntityType.SNOW_GOLEM.getRegistryName();
		}
		else if("LavaCube".equalsIgnoreCase(entity)|| "MagmaSlime".equalsIgnoreCase(entity) || "LavaSlime".equalsIgnoreCase(entity))
		{
			return EntityType.MAGMA_CUBE.getRegistryName();
		}
		else if("SpiderJockey".equalsIgnoreCase(entity))
		{
			return EntityType.SPIDER.getRegistryName();
		}
		else if("VillagerGolem".equalsIgnoreCase(entity))
		{
			return EntityType.IRON_GOLEM.getRegistryName();
		}
		else if("Ozelot".equalsIgnoreCase(entity))
		{
			return EntityType.OCELOT.getRegistryName();
		}
		else if("Kitty".equalsIgnoreCase(entity) || "Kitten".equalsIgnoreCase(entity))
		{
			return EntityType.CAT.getRegistryName();
		}
		else if("TESTIFICATE".equalsIgnoreCase(entity) || ForgeRegistries.PROFESSIONS.getKeys().stream().anyMatch(profession -> profession.getPath().equalsIgnoreCase(entity)))
		{
			return EntityType.VILLAGER.getRegistryName();
		}
		else if("Octopus".equalsIgnoreCase(entity) || "Kraken".equalsIgnoreCase(entity))
		{
			return EntityType.SQUID.getRegistryName();
		}
		else if("Exwife".equalsIgnoreCase(entity))
		{
			return EntityType.GHAST.getRegistryName();
		}
		else if("CommandMinecart".equalsIgnoreCase(entity))
		{
			return EntityType.COMMAND_BLOCK_MINECART.getRegistryName();
		}
		else if("Wizard".equalsIgnoreCase(entity))
		{
			return EntityType.EVOKER.getRegistryName();
		}
		else if("Johnny".equalsIgnoreCase(entity))
		{
			return EntityType.VINDICATOR.getRegistryName();
		}
		else if("BabyZombie".equalsIgnoreCase(entity))
		{
			return EntityType.ZOMBIE.getRegistryName();
		}
		
		return ResourceHelper.stringToResourceLocation(name);
	}
}

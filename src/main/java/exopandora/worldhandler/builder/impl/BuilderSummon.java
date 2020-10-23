package exopandora.worldhandler.builder.impl;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.component.impl.EntityNBT;
import exopandora.worldhandler.builder.types.ArgumentType;
import exopandora.worldhandler.builder.types.Coordinate.EnumType;
import exopandora.worldhandler.builder.types.CoordinateDouble;
import exopandora.worldhandler.util.MutableStringTextComponent;
import exopandora.worldhandler.util.ResourceHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class BuilderSummon extends CommandBuilderNBT
{
	private final EntityNBT nbt = new EntityNBT();
	
	public BuilderSummon()
	{
		this.setX(new CoordinateDouble(0.0, EnumType.LOCAL));
		this.setY(new CoordinateDouble(0.0, EnumType.LOCAL));
		this.setZ(new CoordinateDouble(2.0, EnumType.LOCAL));
	}
	
	public void setName(String name)
	{
		this.setEntity(BuilderSummon.parseEntityName(name));
		this.updateCustomComponent(name);
	}
	
	public void setNameAndId(String name)
	{
		this.setName(name);
		this.nbt.setId(this.getEntity());
	}
	
	public void setEntity(ResourceLocation entity)
	{
		this.setNode(0, entity);
	}
	
	public ResourceLocation getEntity()
	{
		return this.getNodeAsResourceLocation(0);
	}
	
	public void setX(CoordinateDouble x)
	{
		this.setNode(1, x);
	}
	
	public CoordinateDouble getX()
	{
		return this.getNodeAsCoordinateDouble(1);
	}
	
	public void setY(CoordinateDouble y)
	{
		this.setNode(2, y);
	}
	
	public CoordinateDouble getY()
	{
		return this.getNodeAsCoordinateDouble(2);
	}
	
	public void setZ(CoordinateDouble z)
	{
		this.setNode(3, z);
	}
	
	public CoordinateDouble getZ()
	{
		return this.getNodeAsCoordinateDouble(3);
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
	public MutableStringTextComponent getCustomName()
	{
		return this.nbt.getCustomName();
	}
	
	public void setPassenger(int index, String name)
	{
		this.nbt.setPassenger(index, BuilderSummon.parseEntityName(name));
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
	
	public void setAmplifier(Effect potion, byte amplifier)
	{
		this.nbt.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(Effect potion, int seconds)
	{
		this.nbt.setSeconds(potion, seconds);
	}
	
	public void setMinutes(Effect potion, int minutes)
	{
		this.nbt.setMinutes(potion, minutes);
	}
	
	public void setHours(Effect potion, int hours)
	{
		this.nbt.setHours(potion, hours);
	}
	
	public void setShowParticles(Effect potion, boolean showParticles)
	{
		this.nbt.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(Effect potion, boolean ambient)
	{
		this.nbt.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(Effect potion)
	{
		return this.nbt.getAmplifier(potion);
	}

	public int getSeconds(Effect potion)
	{
		return this.nbt.getSeconds(potion);
	}
	
	public int getMinutes(Effect potion)
	{
		return this.nbt.getMinutes(potion);
	}
	
	public int getHours(Effect potion)
	{
		return this.nbt.getHours(potion);
	}
	
	public boolean getShowParticles(Effect potion)
	{
		return this.nbt.getShowParticles(potion);
	}
	
	public boolean getAmbient(Effect potion)
	{
		return this.nbt.getAmbient(potion);
	}
	
	public Set<Effect> getEffects()
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
	
	@Override
	protected CompoundNBT buildNBT()
	{
		return this.nbt.serialize();
	}
	
	@Override
	public void setNBT(CompoundNBT nbt)
	{
		this.setNode(4, nbt);
	}
	
	@Override
	public String getCommandName()
	{
		return "summon";
	}
	
	@Override
	public CommandSyntax getSyntax()
	{
		CommandSyntax syntax = new CommandSyntax();
		
		syntax.addRequired("entity_name", ArgumentType.RESOURCE_LOCATION);
		syntax.addOptional("x", ArgumentType.COORDINATE_DOUBLE);
		syntax.addOptional("y", ArgumentType.COORDINATE_DOUBLE);
		syntax.addOptional("z", ArgumentType.COORDINATE_DOUBLE);
		syntax.addOptional("nbt", ArgumentType.NBT);
		
		return syntax;
	}
	
	private void updateCustomComponent(String name)
	{
		ResourceLocation entity = this.getEntity();
		
		if(name != null && entity != null)
		{
			if(entity.equals(EntityType.CAT.getRegistryName()))
			{
				this.nbt.setCustomComponent("CatType", IntNBT.valueOf(new Random().nextInt(11)));
			}
			else if(entity.equals(EntityType.VILLAGER.getRegistryName()))
			{
				for(VillagerProfession profession : ForgeRegistries.PROFESSIONS)
				{
					if(StringUtils.equalsIgnoreCase(name, profession.toString()))
					{
						CompoundNBT villagerData = new CompoundNBT();
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
					this.nbt.setCustomComponent("IsBaby", ByteNBT.valueOf((byte) 1));
				}
			}
			else if(entity.equals(EntityType.CHICKEN.getRegistryName()))
			{
				if(StringUtils.containsIgnoreCase(name, "Jockey") && !this.nbt.hasPassengers())
				{
					ListNBT list = new ListNBT();
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
					ListNBT list = new ListNBT();
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
			if(type.isSummonable() && entityName.equalsIgnoreCase(I18n.format(type.getTranslationKey())))
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
		else if("ZombieHorse".equalsIgnoreCase(entity) || "SkeletonHorse".equalsIgnoreCase(entity))
		{
			return EntityType.HORSE.getRegistryName();
		}
		else if("LavaCube".equalsIgnoreCase(entity)|| "MagmaSlime".equalsIgnoreCase(entity) || "LavaSlime".equalsIgnoreCase(entity))
		{
			return EntityType.MAGMA_CUBE.getRegistryName();
		}
		else if("SpiderJockey".equalsIgnoreCase(entity))
		{
			return EntityType.SPIDER.getRegistryName();
		}
		else if("IronGolem".equalsIgnoreCase(entity))
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
		else if("TESTIFICATE".equalsIgnoreCase(entity) || "Blacksmith".equalsIgnoreCase(entity) || "Farmer".equalsIgnoreCase(entity) || "Fisherman".equalsIgnoreCase(entity) || "Shepherd".equalsIgnoreCase(entity) || "Fletcher".equalsIgnoreCase(entity) || "Librarian".equalsIgnoreCase(entity) || "Cleric".equalsIgnoreCase(entity) || "Priest".equalsIgnoreCase(entity) || "Armorer".equalsIgnoreCase(entity) || "WeaponSmith".equalsIgnoreCase(entity) || "ToolSmith".equalsIgnoreCase(entity) || "Butcher".equalsIgnoreCase(entity) || "Leatherworker".equalsIgnoreCase(entity) || "Carthographer".equalsIgnoreCase(entity) || "Nitwit".equalsIgnoreCase(entity))
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

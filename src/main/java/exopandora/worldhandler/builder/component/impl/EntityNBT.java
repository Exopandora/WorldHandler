package exopandora.worldhandler.builder.component.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import exopandora.worldhandler.builder.component.IBuilderComponent;
import exopandora.worldhandler.util.MutableTextComponent;
import exopandora.worldhandler.util.NBTHelper;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EntityNBT implements IBuilderComponent
{
	private ResourceLocation id;
	private String command;
	private Integer time;
	private double[] motion = {0.0, 0.0, 0.0};
	private boolean isBaby;
	private BlockState blockState;
	private ComponentCustom entity = new ComponentCustom();
	private ComponentAttributeMob attribute = new ComponentAttributeMob();
	private MutableTextComponent customName = new MutableTextComponent();
	private List<EntityNBT> passengers = new ArrayList<EntityNBT>();
	private ResourceLocation[] armorItems = {Items.AIR.getRegistryName(), Items.AIR.getRegistryName(), Items.AIR.getRegistryName(), Items.AIR.getRegistryName()};
	private ResourceLocation[] handItems = {Items.AIR.getRegistryName(), Items.AIR.getRegistryName()};
	private ComponentPotionMob potion = new ComponentPotionMob();
	private CompoundTag nbt;
	
	public EntityNBT()
	{
		super();
	}
	
	public EntityNBT(ResourceLocation id)
	{
		this.id = id;
	}
	
	public void setId(ResourceLocation id)
	{
		this.id = id;
	}
	
	public ResourceLocation getId()
	{
		return this.id;
	}
	
	public void setAttribute(Attribute attribute, double ammount)
	{
		this.attribute.set(attribute, ammount);
	}
	
	public void removeAttribute(Attribute attribute)
	{
		this.attribute.remove(attribute);
	}
	
	public double getAttributeAmmount(Attribute attribute)
	{
		return this.attribute.getAmmount(attribute);
	}
	
	public Set<Attribute> getAttributes()
	{
		return this.attribute.getAttributes();
	}
	
	public void setCustomName(String name)
	{
		this.customName.setText(name);
	}
	
	@Nullable
	public MutableTextComponent getCustomName()
	{
		return this.customName;
	}
	
	public void setPassenger(int index, EntityNBT entity)
	{
		if(index < 0 || index >= this.passengers.size())
		{
			this.passengers.add(entity);
		}
		else
		{
			this.passengers.set(index, entity);
		}
	}
	
	public void setPassenger(int index, ResourceLocation id)
	{
		this.setPassenger(index, new EntityNBT(id));
	}
	
	public void addPassenger(EntityNBT entity)
	{
		this.passengers.add(entity);
	}
	
	public void addPassenger(int index, EntityNBT entity)
	{
		this.passengers.add(index, entity);
	}
	
	public void removePassenger(int index)
	{
		this.passengers.remove(index);
	}
	
	public int getPassengerCount()
	{
		return this.passengers.size();
	}
	
	public List<EntityNBT> getPassengers()
	{
		return this.passengers;
	}
	
	@Nullable
	public EntityNBT getPassenger(int index)
	{
		if(index >= 0 && index <= this.passengers.size())
		{
			return this.passengers.get(index);
		}
		
		return null;
	}
	
	public boolean hasPassengers()
	{
		for(EntityNBT entity : this.passengers)
		{
			if(entity.serialize() != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void setArmorItem(int index, Block block)
	{
		this.setArmorItem(index, block.getRegistryName());
	}
	
	public void setArmorItem(int index, Item item)
	{
		this.setArmorItem(index, item.getRegistryName());
	}
	
	public void setArmorItem(int index, ResourceLocation location)
	{
		if(EntityNBT.isArrayIndexValid(this.armorItems, index) && location != null)
		{
			this.armorItems[index] = location;
		}
	}
	
	public void setArmorItems(ResourceLocation[] armor)
	{
		this.armorItems = armor;
	}
	
	@Nonnull
	public ResourceLocation getArmorItem(int slot)
	{
		if(EntityNBT.isArrayIndexValid(this.armorItems, slot))
		{
			return this.armorItems[slot];
		}
		
		return Blocks.AIR.getRegistryName();
	}
	
	public void setHandItem(int index, Block block)
	{
		this.setHandItem(index, block.getRegistryName());
	}
	
	public void setHandItem(int index, Item item)
	{
		this.setHandItem(index, item.getRegistryName());
	}
	
	public void setHandItem(int index, ResourceLocation location)
	{
		if(EntityNBT.isArrayIndexValid(this.handItems, index) && location != null)
		{
			this.handItems[index] = location;
		}
	}
	
	@Nonnull
	public ResourceLocation getHandItem(int slot)
	{
		if(EntityNBT.isArrayIndexValid(this.handItems, slot))
		{
			return this.handItems[slot];
		}
		
		return Blocks.AIR.getRegistryName();
	}
	
	public double[] getMotion()
	{
		return this.motion;
	}
	
	public void setMotion(double x, double y, double z)
	{
		this.setMotionX(x);
		this.setMotionY(y);
		this.setMotionZ(z);
	}
	
	public double getMotionX()
	{
		return this.motion[0];
	}
	
	public double getMotionY()
	{
		return this.motion[1];
	}
	
	public double getMotionZ()
	{
		return this.motion[2];
	}
	
	public void setMotionX(double x)
	{
		this.motion[0] = x;
	}
	
	public void setMotionY(double y)
	{
		this.motion[1] = y;
	}
	
	public void setMotionZ(double z)
	{
		this.motion[2] = z;
	}
	
	public void setAmplifier(MobEffect potion, byte amplifier)
	{
		this.potion.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(MobEffect potion, int seconds)
	{
		this.potion.setSeconds(potion, seconds);
	}
	
	public void setMinutes(MobEffect potion, int minutes)
	{
		this.potion.setMinutes(potion, minutes);
	}
	
	public void setHours(MobEffect potion, int hours)
	{
		this.potion.setHours(potion, hours);
	}
	
	public void setShowParticles(MobEffect potion, boolean showParticles)
	{
		this.potion.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(MobEffect potion, boolean ambient)
	{
		this.potion.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(MobEffect potion)
	{
		return this.potion.getAmplifier(potion);
	}
	
	public int getSeconds(MobEffect potion)
	{
		return this.potion.getSeconds(potion);
	}
	
	public int getMinutes(MobEffect potion)
	{
		return this.potion.getMinutes(potion);
	}
	
	public int getHours(MobEffect potion)
	{
		return this.potion.getHours(potion);
	}
	
	public boolean getShowParticles(MobEffect potion)
	{
		return this.potion.getShowParticles(potion);
	}
	
	public boolean getAmbient(MobEffect potion)
	{
		return this.potion.getAmbient(potion);
	}
	
	public Set<MobEffect> getEffects()
	{
		return this.potion.getMobEffects();
	}
	
	public void setBlockState(BlockState blockState)
	{
		this.blockState = blockState;
	}
	
	public BlockState getBlockState()
	{
		return this.blockState;
	}
	
	public void setTime(int time)
	{
		this.time = time;
	}
	
	public int getTime()
	{
		return this.time;
	}
	
	public void setCustomComponent(String tag, Tag nbt)
	{
		this.entity.set(tag, nbt);
	}
	
	public void resetCustomComponent()
	{
		this.entity.set(null, null);
	}
	
	public void setIsBaby(boolean baby)
	{
		this.isBaby = baby;
	}
	
	public boolean isBaby()
	{
		return this.isBaby;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
	public String getCommand()
	{
		return this.command;
	}
	
	public void setNBT(CompoundTag nbt)
	{
		this.nbt = nbt;
	}
	
	public CompoundTag getNBT()
	{
		return this.nbt;
	}
	
	public void setNBT(String nbt)
	{
		try
		{
			this.nbt = TagParser.parseTag("{" + nbt + "}");
		}
		catch(CommandSyntaxException e)
		{
			this.nbt = null;
		}
	}
	
	@Override
	public CompoundTag serialize()
	{
		CompoundTag nbt = new CompoundTag();
		
		if(this.time != null)
		{
			NBTHelper.append(nbt, "Time", IntTag.valueOf(this.time));
		}
		
		if(this.command != null)
		{
			NBTHelper.append(nbt, "Command", StringTag.valueOf(this.command));
		}
		
		if(this.isBaby)
		{
			NBTHelper.append(nbt, "IsBaby", ByteTag.valueOf(true));
		}
		
		NBTHelper.append(nbt, "id", NBTHelper.serialize(this.id));
		NBTHelper.append(nbt, "Motion", NBTHelper.serialize(this.motion));
		NBTHelper.append(nbt, "Passengers", NBTHelper.serialize(this.passengers));
		NBTHelper.append(nbt, "ArmorItems", NBTHelper.serialize(this.armorItems));
		NBTHelper.append(nbt, "HandItems", NBTHelper.serialize(this.handItems));
		NBTHelper.append(nbt, "BlockState", NBTHelper.serialize(this.blockState));
		
		NBTHelper.append(nbt, "CustomName", this.customName);
		
		NBTHelper.append(nbt, this.entity);
		NBTHelper.append(nbt, this.potion);
		NBTHelper.append(nbt, this.attribute);
		
		if(this.nbt != null)
		{
			nbt.merge(this.nbt);
		}
		
		if(nbt.isEmpty())
		{
			return null;
		}
		
		return nbt;
	}
	
	@Override
	public String getTag()
	{
		return null;
	}
	
	private static boolean isArrayIndexValid(Object[] array, int index)
	{
		if(array != null && (Array.getLength(array) == 0 || array.length <= index))
		{
            return false;
		}
		
        return index >= 0;
	}
}

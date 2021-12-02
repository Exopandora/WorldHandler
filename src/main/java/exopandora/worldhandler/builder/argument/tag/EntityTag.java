package exopandora.worldhandler.builder.argument.tag;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

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
import net.minecraft.world.level.block.state.BlockState;

public class EntityTag implements ITagProvider
{
	private ResourceLocation id;
	private String command;
	private Integer time;
	private double[] motion = {0.0, 0.0, 0.0};
	private boolean isBaby;
	private BlockState blockState;
	private AttributesTag attribute = new AttributesTag();
	private MutableTextComponent customName = new MutableTextComponent();
	private List<EntityTag> passengers = new ArrayList<EntityTag>();
	private Item[] armorItems = {Items.AIR, Items.AIR, Items.AIR, Items.AIR};
	private Item[] handItems = {Items.AIR, Items.AIR};
	private ActiveEffectsTag potion = new ActiveEffectsTag();
	private CompoundTag nbt;
	
	public EntityTag()
	{
		super();
	}
	
	public EntityTag(ResourceLocation id)
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
	
	public double getAttribute(Attribute attribute)
	{
		return this.attribute.get(attribute);
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
	
	public void setPassenger(int index, EntityTag entity)
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
		this.setPassenger(index, new EntityTag(id));
	}
	
	public void addPassenger(EntityTag entity)
	{
		this.passengers.add(entity);
	}
	
	public void addPassenger(int index, EntityTag entity)
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
	
	public List<EntityTag> getPassengers()
	{
		return this.passengers;
	}
	
	@Nullable
	public EntityTag getPassenger(int index)
	{
		if(index >= 0 && index <= this.passengers.size())
		{
			return this.passengers.get(index);
		}
		
		return null;
	}
	
	public boolean hasPassengers()
	{
		for(EntityTag entity : this.passengers)
		{
			if(entity.value() != null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void setArmorItem(int index, Item location)
	{
		if(EntityTag.isArrayIndexValid(this.armorItems, index) && location != null)
		{
			this.armorItems[index] = location;
		}
	}
	
	public void setArmorItems(Item[] armor)
	{
		this.armorItems = armor;
	}
	
	@Nonnull
	public Item getArmorItem(int slot)
	{
		if(EntityTag.isArrayIndexValid(this.armorItems, slot))
		{
			return this.armorItems[slot];
		}
		
		return Items.AIR;
	}
	
	public void setHandItem(int index, Item location)
	{
		if(EntityTag.isArrayIndexValid(this.handItems, index) && location != null)
		{
			this.handItems[index] = location;
		}
	}
	
	@Nonnull
	public Item getHandItem(int slot)
	{
		if(EntityTag.isArrayIndexValid(this.handItems, slot))
		{
			return this.handItems[slot];
		}
		
		return Items.AIR;
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
	
	public void setAmplifier(MobEffect effect, byte amplifier)
	{
		this.potion.getOrCreate(effect).setAmplifier(amplifier);
	}
	
	public void setSeconds(MobEffect effect, int seconds)
	{
		this.potion.getOrCreate(effect).setSeconds(seconds);
	}
	
	public void setMinutes(MobEffect effect, int minutes)
	{
		this.potion.getOrCreate(effect).setMinutes(minutes);
	}
	
	public void setHours(MobEffect effect, int hours)
	{
		this.potion.getOrCreate(effect).setHours(hours);
	}
	
	public void setShowParticles(MobEffect effect, boolean showParticles)
	{
		this.potion.getOrCreate(effect).setShowParticles(showParticles);
	}
	
	public void setAmbient(MobEffect effect, boolean ambient)
	{
		this.potion.getOrCreate(effect).setAmbient(ambient);
	}
	
	public byte getAmplifier(MobEffect effect)
	{
		return this.potion.getOrCreate(effect).getAmplifier();
	}
	
	public int getSeconds(MobEffect effect)
	{
		return this.potion.getOrCreate(effect).getSeconds();
	}
	
	public int getMinutes(MobEffect effect)
	{
		return this.potion.getOrCreate(effect).getMinutes();
	}
	
	public int getHours(MobEffect effect)
	{
		return this.potion.getOrCreate(effect).getHours();
	}
	
	public boolean doShowParticles(MobEffect effect)
	{
		return this.potion.getOrCreate(effect).doShowParticles();
	}
	
	public boolean isAmbient(MobEffect effect)
	{
		return this.potion.getOrCreate(effect).isAmbient();
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
	public String key()
	{
		return null;
	}
	
	@Override
	public Tag value()
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
		
		NBTHelper.append(nbt, "CustomName", this.customName.serialize());
		
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
	
	private static boolean isArrayIndexValid(Object[] array, int index)
	{
		if(array != null && (Array.getLength(array) == 0 || array.length <= index))
		{
            return false;
		}
		
        return index >= 0;
	}
}

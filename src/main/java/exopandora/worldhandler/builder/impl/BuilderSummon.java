package exopandora.worldhandler.builder.impl;

import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.CommandSyntax;
import exopandora.worldhandler.builder.component.impl.ComponentAttributeMob;
import exopandora.worldhandler.builder.component.impl.ComponentPotionMob;
import exopandora.worldhandler.builder.component.impl.ComponentSummon;
import exopandora.worldhandler.builder.component.impl.ComponentTag;
import exopandora.worldhandler.builder.impl.EnumAttributes.Applyable;
import exopandora.worldhandler.builder.types.Coordinate.CoordinateType;
import exopandora.worldhandler.util.MutableStringTextComponent;
import exopandora.worldhandler.builder.types.CoordinateDouble;
import exopandora.worldhandler.builder.types.ArgumentType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderSummon extends CommandBuilderNBT
{
	private final ComponentAttributeMob attribute;
	private final ComponentTag<MutableStringTextComponent> customName;
	private final ComponentTag<ListNBT> passengers;
	private final ComponentTag<ListNBT> armorItems;
	private final ComponentTag<ListNBT> handItems;
	private final ComponentPotionMob potion;
	private final ComponentSummon summon;
	private final ResourceLocation[] armorItemsArray = {Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName()};
	private final ResourceLocation[] handItemsArray = {Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName()};
	
	public BuilderSummon()
	{
		this.attribute = this.registerNBTComponent(new ComponentAttributeMob(attribute -> attribute.getApplyable().equals(Applyable.BOTH) || attribute.getApplyable().equals(Applyable.MOB)));
		this.customName = this.registerNBTComponent(new ComponentTag<MutableStringTextComponent>("CustomName", new MutableStringTextComponent(), this::textComponentSerializer));
		this.passengers = this.registerNBTComponent(new ComponentTag<ListNBT>("Passengers"));
		this.armorItems = this.registerNBTComponent(new ComponentTag<ListNBT>("ArmorItems", this::itemListSerializer));
		this.handItems = this.registerNBTComponent(new ComponentTag<ListNBT>("HandItems", this::itemListSerializer));
		this.summon = this.registerNBTComponent(new ComponentSummon(), "summon");
		this.potion = this.registerNBTComponent(new ComponentPotionMob());
		this.setX(new CoordinateDouble(0.0, CoordinateType.LOCAL));
		this.setY(new CoordinateDouble(0.0, CoordinateType.LOCAL));
		this.setZ(new CoordinateDouble(2.0, CoordinateType.LOCAL));
	}
	
	public void setEntity(String entityName)
	{
		ResourceLocation location = ComponentSummon.resolve(entityName);
		
		this.summon.setName(entityName);
		this.summon.setEntity(location);
		
		this.setNode(0, location);
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
	
	public void setAttribute(EnumAttributes attribute, double ammount)
	{
		this.attribute.set(attribute, ammount);
	}
	
	public void removeAttribute(EnumAttributes attribute)
	{
		this.attribute.remove(attribute);
	}
	
	public double getAttributeAmmount(EnumAttributes attribute)
	{
		return this.attribute.getAmmount(attribute);
	}
	
	public Set<EnumAttributes> getAttributes()
	{
		return this.attribute.getAttributes();
	}
	
	public void setCustomName(MutableStringTextComponent name)
	{
		this.customName.setValue(name);
	}
	
	public void setCustomName(String name)
	{
		this.customName.getValue().setText(name);
	}
	
	@Nonnull
	public MutableStringTextComponent getCustomName()
	{
		if(this.customName.getValue() != null)
		{
			return this.customName.getValue();
		}
		
		return null;
	}
	
	public void setPassenger(String entityName)
	{
		this.setPassenger(ComponentSummon.resolve(entityName));
	}
	
	public void setPassenger(ResourceLocation entityName)
	{
		if(entityName != null)
		{
			CompoundNBT passenger = new CompoundNBT();
			passenger.putString("id", entityName.toString());
			
			ListNBT list = new ListNBT();
			list.add(passenger);
			
			this.passengers.setValue(list);
		}
		else
		{
			this.passengers.setValue(null);
		}
	}
	
	@Nullable
	public ResourceLocation getPassenger()
	{
		ListNBT list = this.passengers.getValue();
		
		if(list != null && !list.isEmpty())
		{
			return new ResourceLocation(list.getCompound(0).getString("id"));
		}
		
		return null;
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
		this.changeNBTList(index, location, this.armorItemsArray, this::setArmorItems);
	}
	
	public void setArmorItems(ResourceLocation[] armor)
	{
		ListNBT list = new ListNBT();
		
		for(ResourceLocation item : armor)
		{
			CompoundNBT compound = new CompoundNBT();
			compound.putString("id", item.toString());
			compound.putInt("Count", 1);
			list.add(compound);
		}
		
		this.armorItems.setValue(list);
	}

	public ResourceLocation getArmorItem(int slot)
	{
		if(slot < this.armorItemsArray.length)
		{
			return this.armorItemsArray[slot];
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
		this.changeNBTList(index, location, this.handItemsArray, this::setHandItems);
	}
	
	private void changeNBTList(int index, ResourceLocation location, ResourceLocation[] array, Consumer<ResourceLocation[]> consumer)
	{
		if(index < array.length)
		{
			array[index] = location;
			consumer.accept(array);
		}
	}
	
	public void setHandItems(ResourceLocation[] armor)
	{
		ListNBT list = new ListNBT();
		
		for(ResourceLocation item : armor)
		{
			CompoundNBT compound = new CompoundNBT();
			compound.putString("id", item.toString());
			compound.putInt("Count", 1);
			list.add(compound);
		}
		
		this.handItems.setValue(list);
	}
	
	public ResourceLocation getHandItem(int slot)
	{
		if(slot < this.handItemsArray.length)
		{
			return this.handItemsArray[slot];
		}
		
		return Blocks.AIR.getRegistryName();
	}
	
	public void setAmplifier(Effect potion, byte amplifier)
	{
		this.potion.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(Effect potion, int seconds)
	{
		this.potion.setSeconds(potion, seconds);
	}
	
	public void setMinutes(Effect potion, int minutes)
	{
		this.potion.setMinutes(potion, minutes);
	}
	
	public void setHours(Effect potion, int hours)
	{
		this.potion.setHours(potion, hours);
	}
	
	public void setShowParticles(Effect potion, boolean showParticles)
	{
		this.potion.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(Effect potion, boolean ambient)
	{
		this.potion.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(Effect potion)
	{
		return this.potion.getAmplifier(potion);
	}

	public int getSeconds(Effect potion)
	{
		return this.potion.getSeconds(potion);
	}
	
	public int getMinutes(Effect potion)
	{
		return this.potion.getMinutes(potion);
	}
	
	public int getHours(Effect potion)
	{
		return this.potion.getHours(potion);
	}
	
	public boolean getShowParticles(Effect potion)
	{
		return this.potion.getShowParticles(potion);
	}
	
	public boolean getAmbient(Effect potion)
	{
		return this.potion.getAmbient(potion);
	}
	
	public Set<Effect> getEffects()
	{
		return this.potion.getEffects();
	}
	
	private INBT itemListSerializer(ListNBT list)
	{
		for(int x = 0; x < list.size(); x++)
		{
			if(!list.getCompound(x).getString("id").equals(Blocks.AIR.getRegistryName().toString()))
			{
				return list;
			}
		}
		
		return null;
	}
	
	private INBT textComponentSerializer(MutableStringTextComponent string)
	{
		if(string.getUnformattedComponentText() != null && !string.getUnformattedComponentText().isEmpty())
		{
			return StringNBT.func_229705_a_(string.serialize());
		}
		
		return null;
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
	
	@Override
	public String toCommand()
	{
		this.summon.setEntity(this.getEntity());
		this.summon.setHasPassenger(this.getPassenger() != null);
		
		return super.toCommand();
	}
}

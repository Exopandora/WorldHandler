package exopandora.worldhandler.builder.impl;

import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import exopandora.worldhandler.builder.CommandBuilderNBT;
import exopandora.worldhandler.builder.Syntax;
import exopandora.worldhandler.builder.component.impl.ComponentAttributeMob;
import exopandora.worldhandler.builder.component.impl.ComponentPotionMob;
import exopandora.worldhandler.builder.component.impl.ComponentSummon;
import exopandora.worldhandler.builder.component.impl.ComponentTag;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes.Applyable;
import exopandora.worldhandler.builder.types.Coordinate.CoordinateType;
import exopandora.worldhandler.builder.types.CoordinateDouble;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.format.text.ColoredString;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BuilderSummon extends CommandBuilderNBT
{
	private final ComponentAttributeMob attribute;
	private final ComponentTag<ColoredString> customName;
	private final ComponentTag<NBTTagList> passengers;
	private final ComponentTag<NBTTagList> armorItems;
	private final ComponentTag<NBTTagList> handItems;
	private final ComponentPotionMob potion;
	private final ComponentSummon summon;
	private final ResourceLocation[] armorItemsArray = {Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName()};
	private final ResourceLocation[] handItemsArray = {Blocks.AIR.getRegistryName(), Blocks.AIR.getRegistryName()};
	
	public BuilderSummon()
	{
		this.attribute = this.registerNBTComponent(new ComponentAttributeMob(attribute -> attribute.getApplyable().equals(Applyable.BOTH) || attribute.getApplyable().equals(Applyable.MOB)));
		this.customName = this.registerNBTComponent(new ComponentTag<ColoredString>("CustomName", new ColoredString(), this::colorStringSerializer));
		this.passengers = this.registerNBTComponent(new ComponentTag<NBTTagList>("Passengers"));
		this.armorItems = this.registerNBTComponent(new ComponentTag<NBTTagList>("ArmorItems", this::itemListSerializer));
		this.handItems = this.registerNBTComponent(new ComponentTag<NBTTagList>("HandItems", this::itemListSerializer));
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
	
	public void setCustomName(ColoredString name)
	{
		this.customName.setValue(name);
	}
	
	public void setCustomName(String name)
	{
		this.customName.getValue().setText(name);
	}
	
	@Nonnull
	public ColoredString getCustomName()
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
			NBTTagCompound passenger = new NBTTagCompound();
			passenger.setString("id", entityName.toString());
			
			NBTTagList list = new NBTTagList();
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
		NBTTagList list = this.passengers.getValue();
		
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
		NBTTagList list = new NBTTagList();
		
		for(ResourceLocation item : armor)
		{
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("id", item.toString());
			compound.setInt("Count", 1);
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
		NBTTagList list = new NBTTagList();
		
		for(ResourceLocation item : armor)
		{
			NBTTagCompound compound = new NBTTagCompound();
			compound.setString("id", item.toString());
			compound.setInt("Count", 1);
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
	
	public void setAmplifier(Potion potion, byte amplifier)
	{
		this.potion.setAmplifier(potion, amplifier);
	}
	
	public void setSeconds(Potion potion, int seconds)
	{
		this.potion.setSeconds(potion, seconds);
	}
	
	public void setMinutes(Potion potion, int minutes)
	{
		this.potion.setMinutes(potion, minutes);
	}
	
	public void setHours(Potion potion, int hours)
	{
		this.potion.setHours(potion, hours);
	}
	
	public void setShowParticles(Potion potion, boolean showParticles)
	{
		this.potion.setShowParticles(potion, showParticles);
	}
	
	public void setAmbient(Potion potion, boolean ambient)
	{
		this.potion.setAmbient(potion, ambient);
	}
	
	public byte getAmplifier(Potion potion)
	{
		return this.potion.getAmplifier(potion);
	}

	public int getSeconds(Potion potion)
	{
		return this.potion.getSeconds(potion);
	}
	
	public int getMinutes(Potion potion)
	{
		return this.potion.getMinutes(potion);
	}
	
	public int getHours(Potion potion)
	{
		return this.potion.getHours(potion);
	}
	
	public boolean getShowParticles(Potion potion)
	{
		return this.potion.getShowParticles(potion);
	}
	
	public boolean getAmbient(Potion potion)
	{
		return this.potion.getAmbient(potion);
	}
	
	public Set<Potion> getPotions()
	{
		return this.potion.getPotions();
	}
	
	private INBTBase itemListSerializer(NBTTagList list)
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
	
	private INBTBase colorStringSerializer(ColoredString string)
	{
		if(string.getText() != null && !string.getText().isEmpty())
		{
			return new NBTTagString(ITextComponent.Serializer.toJson(new TextComponentString(string.toString())));
		}
		
		return null;
	}
	
	@Override
	public void setNBT(NBTTagCompound nbt)
	{
		this.setNode(4, nbt);
	}
	
	@Override
	public String getCommandName()
	{
		return "summon";
	}
	
	@Override
	public Syntax getSyntax()
	{
		Syntax syntax = new Syntax();
		
		syntax.addRequired("entity_name", Type.RESOURCE_LOCATION);
		syntax.addOptional("x", Type.COORDINATE_DOUBLE);
		syntax.addOptional("y", Type.COORDINATE_DOUBLE);
		syntax.addOptional("z", Type.COORDINATE_DOUBLE);
		syntax.addOptional("nbt", Type.NBT);
		
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

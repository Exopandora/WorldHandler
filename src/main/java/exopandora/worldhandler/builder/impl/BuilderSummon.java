package exopandora.worldhandler.builder.impl;

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
import exopandora.worldhandler.builder.types.Coordinate;
import exopandora.worldhandler.builder.types.Type;
import exopandora.worldhandler.format.text.ColoredString;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	}
	
	public BuilderSummon(int direction)
	{
		this();
		this.setDirection(direction);
	}
	
	public void setDirection(int direction)
	{
		if(direction == 0)
		{
			this.setX(new Coordinate(0, true));
			this.setY(new Coordinate(0, true));
			this.setZ(new Coordinate(2, true));
		}
		else if(direction == 1)
		{
			this.setX(new Coordinate(-2, true));
			this.setY(new Coordinate(0, true));
			this.setZ(new Coordinate(0, true));
		}
		else if(direction == 2)
		{
			this.setX(new Coordinate(0, true));
			this.setY(new Coordinate(0, true));
			this.setZ(new Coordinate(-2, true));
		}
		else if(direction == 3)
		{
			this.setX(new Coordinate(2, true));
			this.setY(new Coordinate(0, true));
			this.setZ(new Coordinate(0, true));
		}
	}
	
	public void setEntity(String entityName)
	{
		ResourceLocation location = this.summon.resolve(entityName);
		
		this.summon.setName(entityName);
		this.summon.setEntity(location);
		
		this.setNode(0, location);
	}
	
	public ResourceLocation getEntity()
	{
		return this.getNodeAsResourceLocation(0);
	}
	
	public void setX(Coordinate x)
	{
		this.setNode(1, x);
	}
	
	public Coordinate getX()
	{
		return this.getNodeAsCoordinate(1);
	}
	
	public void setY(Coordinate y)
	{
		this.setNode(2, y);
	}
	
	public Coordinate getY()
	{
		return this.getNodeAsCoordinate(2);
	}
	
	public void setZ(Coordinate z)
	{
		this.setNode(3, z);
	}
	
	public Coordinate getZ()
	{
		return this.getNodeAsCoordinate(3);
	}
	
	public void setAttribute(EnumAttributes attribute, float ammount)
	{
		this.attribute.set(attribute, ammount);
	}
	
	public void removeAttribute(EnumAttributes attribute)
	{
		this.attribute.remove(attribute);
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
		this.setPassenger(this.summon.resolve(entityName));
	}
	
	public void setPassenger(ResourceLocation entityName)
	{
		if(entityName != null)
		{
			NBTTagCompound passenger = new NBTTagCompound();
			passenger.setString("id", entityName.toString());
			
			NBTTagList list = new NBTTagList();
			list.appendTag(passenger);
			
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
		
		if(list != null && !list.hasNoTags())
		{
			return new ResourceLocation(list.getCompoundTagAt(0).getString("id"));
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
			compound.setInteger("Count", 1);
			list.appendTag(compound);
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
			compound.setInteger("Count", 1);
			list.appendTag(compound);
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
		this.potion.get(potion).setAmplifier(amplifier);
	}

	public void setSeconds(Potion potion, int seconds)
	{
		this.potion.get(potion).setSeconds(seconds);
	}
	
	public void setMinutes(Potion potion, int minutes)
	{
		this.potion.get(potion).setMinutes(minutes);
	}
	
	public void setHours(Potion potion, int hours)
	{
		this.potion.get(potion).setHours(hours);
	}
	
	public void setShowParticles(Potion potion, boolean showParticles)
	{
		this.potion.get(potion).setShowParticles(showParticles);
	}
	
	public void setAmbient(Potion potion, boolean ambient)
	{
		this.potion.get(potion).setAmbient(ambient);
	}
	
	public byte getAmplifier(Potion potion)
	{
		return this.potion.get(potion).getAmplifier();
	}

	public int getSeconds(Potion potion)
	{
		return this.potion.get(potion).getSeconds();
	}
	
	public int getMinutes(Potion potion)
	{
		return this.potion.get(potion).getMinutes();
	}
	
	public int getHours(Potion potion)
	{
		return this.potion.get(potion).getHours();
	}
	
	public boolean getShowParticles(Potion potion)
	{
		return this.potion.get(potion).getShowParticles();
	}
	
	public boolean getAmbient(Potion potion)
	{
		return this.potion.get(potion).getAmbient();
	}
	
	private NBTBase itemListSerializer(NBTTagList list)
	{
		for(int x = 0; x < list.tagCount(); x++)
		{
			if(!list.getCompoundTagAt(x).getString("id").equals(Blocks.AIR.getRegistryName().toString()))
			{
				return list;
			}
		}
		
		return null;
	}
	
	private NBTBase colorStringSerializer(ColoredString string)
	{
		if(string.getText() != null && !string.getText().isEmpty())
		{
			return new NBTTagString(string.toString());
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
		syntax.addOptional("x", Type.COORDINATE);
		syntax.addOptional("y", Type.COORDINATE);
		syntax.addOptional("z", Type.COORDINATE);
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

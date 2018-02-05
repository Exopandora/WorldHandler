package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderSummon;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes;
import exopandora.worldhandler.builder.impl.abstr.EnumAttributes.Applyable;
import exopandora.worldhandler.gui.button.GuiButtonItem;
import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.button.GuiSlider;
import exopandora.worldhandler.gui.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.button.responder.AttributeResponder;
import exopandora.worldhandler.gui.button.responder.SimpleResponder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.content.element.impl.ElementPageList;
import exopandora.worldhandler.gui.content.element.logic.ILogicPageList;
import exopandora.worldhandler.main.WorldHandler;
import exopandora.worldhandler.util.UtilPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContentSummon extends Content
{
	private GuiTextFieldTooltip mobField;
	private GuiTextFieldTooltip customNameField;
	private GuiTextFieldTooltip passengerField;
	
	private int potionPage = 0;
	private int equipmentPage = 0;
	
	private String page = "main";
	private String mob;
	private String name;
	private String passenger;
	
	private final BuilderSummon builderSummon = new BuilderSummon();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderSummon;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.builderSummon.setDirection(UtilPlayer.getPlayerDirection());
		
		this.mobField = new GuiTextFieldTooltip(x + 118, y, 114, 20, I18n.format("gui.worldhandler.entities.summon.start.mob_id") + " (" + I18n.format("gui.worldhandler.generic.name") + ")");
		this.mobField.setValidator(Predicates.notNull());
		this.mobField.setText(this.mob);
		
		this.customNameField = new GuiTextFieldTooltip(x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.entities.summon.start.custom_name"));
		this.customNameField.setValidator(Predicates.notNull());
		this.customNameField.setText(this.name);
		
		this.passengerField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.entities.summon.start.passenger_mob_id"));
		this.passengerField.setValidator(Predicates.notNull());
		this.passengerField.setText(this.passenger);
		
		if(this.page.equals("attributes"))
		{
			ElementPageList<EnumAttributes, Object> attributes = new ElementPageList<EnumAttributes, Object>(x + 118, y, Stream.concat(EnumAttributes.getAttributesFor(Applyable.BOTH).stream(), EnumAttributes.getAttributesFor(Applyable.MOB).stream()).collect(Collectors.toList()), null, 114, 20, 3, this, new int[] {6, 7, 8}, new ILogicPageList<EnumAttributes, Object>()
			{
				@Override
				public String translate(EnumAttributes key)
				{
					return key.getTranslation();
				}
				
				@Override
				public void onClick(EnumAttributes clicked)
				{
					
				}
				
				@Override
				public String getRegistryName(EnumAttributes key)
				{
					return key.getAttribute();
				}
				
				@Override
				public void onRegister(int id, int x, int y, int width, int height, String display, String registry, boolean enabled, EnumAttributes value, Container container)
				{
					container.add(new GuiSlider<EnumAttributes>(Contents.SUMMON, container, value, x, y, width, height, display, value.getMin(), value.getMax(), value.getStart(), new AttributeResponder(response ->
					{
						builderSummon.setAttribute(value, response);
					})));
				}
				
				@Override
				public EnumAttributes convert(Object object)
				{
					return null;
				}
				
				@Override
				public String getId()
				{
					return "attributes";
				}
			});
			
			container.add(attributes);
		}
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonWorldHandler button3;
		GuiButtonWorldHandler button4;
		GuiButtonWorldHandler button5;
		GuiButtonWorldHandler button6;
		GuiButtonWorldHandler button7;
		GuiButtonWorldHandler button8;
		GuiButtonWorldHandler button9;
		GuiButtonWorldHandler button10;
		GuiButtonItem button11;
		GuiButtonItem button12;
		GuiButtonItem button13;
		GuiButtonItem button14;
		GuiButtonItem button15;
		GuiButtonWorldHandler button16;
		GuiButtonItem button17;
		GuiButtonItem button18;
		GuiButtonItem button19;
		GuiButtonItem button20;
		GuiButtonItem button21;
		GuiButtonWorldHandler button22;
		GuiButtonItem button23;
		GuiButtonItem button24;
		GuiButtonItem button25;
		GuiButtonItem button26;
		GuiButtonItem button27;
		GuiButtonWorldHandler button28;
		
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(button7 = new GuiButtonWorldHandler(12, x, y, 114, 20, I18n.format("gui.worldhandler.entities.summon.start")));
		container.add(button8 = new GuiButtonWorldHandler(3, x, y + 24, 114, 20, I18n.format("gui.worldhandler.entities.summon.potion_effects")));
		container.add(button9 = new GuiButtonWorldHandler(4, x, y + 48, 114, 20, I18n.format("gui.worldhandler.entities.summon.attributes")));
		container.add(button10 = new GuiButtonWorldHandler(5, x, y + 72, 114, 20, I18n.format("gui.worldhandler.entities.summon.equipment")));
		
		if(this.page.equals("main"))
		{
			button7.enabled = false;
			
			if(!this.builderSummon.needsCommandBlock() && !this.builderSummon.getCustomName().isSpecial())
			{
				container.add(button5 = new GuiButtonWorldHandler(9, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.title.entities.summon")));
			}
			else
			{
				container.add(button5 = new GuiButtonWorldHandler(9, x + 118, y + 72, 114, 20, I18n.format("gui.worldhandler.actions.place_command_block")));
			}
			
			button5.enabled = EntityList.isRegistered(this.builderSummon.getEntity());
		}
		else if(this.page.equals("potionEffects"))
		{
			button8.enabled = false;
			
			container.add(button3 = new GuiButtonWorldHandler(14, x + 118, y + 72, 56, 20, "<"));
			container.add(button4 = new GuiButtonWorldHandler(15, x + 118 + 60, y + 72, 55, 20, ">"));
			
			int count = 0;
			
			for(ResourceLocation location : this.getSortedPotionList())
			{
				Potion potion = Potion.REGISTRY.getObject(location);
				
				if(!potion.equals(MobEffects.INSTANT_DAMAGE) && !potion.equals(MobEffects.INSTANT_HEALTH))
				{
					if(this.potionPage == 0)
					{
						button3.enabled = false;
					}
						
					if(this.potionPage == Potion.REGISTRY.getKeys().size() - 3)
					{
						button4.enabled = false;
					}
					
					if(count == this.potionPage)
					{
						container.add(new GuiSlider<Potion>(this, container, "amplifier" + potion, x + 118, y, 114, 20, I18n.format(potion.getName()), 0, 100, 0, new SimpleResponder<Potion>(value ->
						{
							this.builderSummon.setAmplifier(potion, value.byteValue());
						})));
						container.add(new GuiSlider<Potion>(this, container, "duration" + potion, x + 118, y + 24, 114, 20, I18n.format("gui.worldhandler.potion.time.minutes"), 0, 100, 0, new SimpleResponder<Potion>(value ->
						{
							this.builderSummon.setMinutes(potion, value);
						})));
						container.add(new GuiButtonWorldHandler(54, x + 118, y + 48, 114, 20, I18n.format("gui.worldhandler.potions.effect.particles", this.builderSummon.getShowParticles(potion) ? I18n.format("gui.worldhandler.generic.on") : I18n.format("gui.worldhandler.generic.off"))));
						break;
					}
					
					count++;
				}
			}
		}
		else if(this.page.equals("attributes"))
		{
			button9.enabled = false;
		}
		else if(this.page.equals("equipment"))
		{
			container.add(button3 = new GuiButtonWorldHandler(16, x + 118, y + 72, 56, 20, "<"));
			container.add(button4 = new GuiButtonWorldHandler(17, x + 118 + 60, y + 72, 54, 20, ">"));
			
			if(this.equipmentPage == 0)
			{
				button3.enabled = false;
				
				container.add(button11 = new GuiButtonItem(18, x + 118, y, 18, 20, Items.LEATHER_HELMET));
				container.add(button12 = new GuiButtonItem(19, x + 118 + 20 - 1, y, 18, 20, Items.IRON_HELMET));
				container.add(button13 = new GuiButtonItem(20, x + 118 + 20 * 2 - 2, y, 18, 20, Items.CHAINMAIL_HELMET));
				container.add(button14 = new GuiButtonItem(21, x + 118 + 20 * 3 - 3, y, 18, 20, Items.GOLDEN_HELMET));
				container.add(button15 = new GuiButtonItem(22, x + 118 + 20 * 4 - 4, y, 18, 20, Items.DIAMOND_HELMET));
				container.add(button16 = new GuiButtonWorldHandler(23, x + 118 + 20 * 5 - 5, y, 20, 20, null));
				
				container.add(button17 = new GuiButtonItem(24, x + 118, y + 24, 18, 20, Items.LEATHER_CHESTPLATE));
				container.add(button18 = new GuiButtonItem(25, x + 118 + 20 - 1, y + 24, 18, 20, Items.IRON_CHESTPLATE));
				container.add(button19 = new GuiButtonItem(26, x + 118 + 20 * 2 - 2, y + 24, 18, 20, Items.CHAINMAIL_CHESTPLATE));
				container.add(button20 = new GuiButtonItem(27, x + 118 + 20 * 3 - 3, y + 24, 18, 20, Items.GOLDEN_CHESTPLATE));
				container.add(button21 = new GuiButtonItem(28, x + 118 + 20 * 4 - 4, y + 24, 18, 20, Items.DIAMOND_CHESTPLATE));
				container.add(button22 = new GuiButtonWorldHandler(29, x + 118 + 20 * 5 - 5, y + 24, 20, 20, null));
				
				container.add(button23 = new GuiButtonItem(30, x + 118, y + 48, 18, 20, Items.LEATHER_LEGGINGS));
				container.add(button24 = new GuiButtonItem(31, x + 118 + 20 - 1, y + 48, 18, 20, Items.IRON_LEGGINGS));
				container.add(button25 = new GuiButtonItem(32, x + 118 + 20 * 2 - 2, y + 48, 18, 20, Items.CHAINMAIL_LEGGINGS));
				container.add(button26 = new GuiButtonItem(33, x + 118 + 20 * 3 - 3, y + 48, 18, 20, Items.GOLDEN_LEGGINGS));
				container.add(button27 = new GuiButtonItem(34, x + 118 + 20 * 4 - 4, y + 48, 18, 20, Items.DIAMOND_LEGGINGS));
				container.add(button28 = new GuiButtonWorldHandler(35, x + 118 + 20 * 5 - 5, y + 48, 20, 20, null));
				
				button11.enabled = !this.builderSummon.getArmorItem(3).equals(Items.LEATHER_HELMET.getRegistryName());
				button12.enabled = !this.builderSummon.getArmorItem(3).equals(Items.IRON_HELMET.getRegistryName());
				button13.enabled = !this.builderSummon.getArmorItem(3).equals(Items.CHAINMAIL_HELMET.getRegistryName());
				button14.enabled = !this.builderSummon.getArmorItem(3).equals(Items.GOLDEN_HELMET.getRegistryName());
				button15.enabled = !this.builderSummon.getArmorItem(3).equals(Items.DIAMOND_HELMET.getRegistryName());
				button16.enabled = !this.builderSummon.getArmorItem(3).equals(Blocks.AIR.getRegistryName());

				button17.enabled = !this.builderSummon.getArmorItem(2).equals(Items.LEATHER_CHESTPLATE.getRegistryName());
				button18.enabled = !this.builderSummon.getArmorItem(2).equals(Items.IRON_CHESTPLATE.getRegistryName());
				button19.enabled = !this.builderSummon.getArmorItem(2).equals(Items.CHAINMAIL_CHESTPLATE.getRegistryName());
				button20.enabled = !this.builderSummon.getArmorItem(2).equals(Items.GOLDEN_CHESTPLATE.getRegistryName());
				button21.enabled = !this.builderSummon.getArmorItem(2).equals(Items.DIAMOND_CHESTPLATE.getRegistryName());
				button22.enabled = !this.builderSummon.getArmorItem(2).equals(Blocks.AIR.getRegistryName());
				
				button23.enabled = !this.builderSummon.getArmorItem(1).equals(Items.LEATHER_LEGGINGS.getRegistryName());
				button24.enabled = !this.builderSummon.getArmorItem(1).equals(Items.IRON_LEGGINGS.getRegistryName());
				button25.enabled = !this.builderSummon.getArmorItem(1).equals(Items.CHAINMAIL_LEGGINGS.getRegistryName());
				button26.enabled = !this.builderSummon.getArmorItem(1).equals(Items.GOLDEN_LEGGINGS.getRegistryName());
				button27.enabled = !this.builderSummon.getArmorItem(1).equals(Items.DIAMOND_LEGGINGS.getRegistryName());
				button28.enabled = !this.builderSummon.getArmorItem(1).equals(Blocks.AIR.getRegistryName());
			}
			else if(this.equipmentPage == 1)
			{
				button4.enabled = false;

				container.add(button11 = new GuiButtonItem(36, x + 118, y, 18, 20, Items.LEATHER_BOOTS));
				container.add(button12 = new GuiButtonItem(37, x + 118 + 20 - 1, y, 18, 20, Items.IRON_BOOTS));
				container.add(button13 = new GuiButtonItem(38, x + 118 + 20 * 2 - 2, y, 18, 20, Items.CHAINMAIL_BOOTS));
				container.add(button14 = new GuiButtonItem(39, x + 118 + 20 * 3 - 3, y, 18, 20, Items.GOLDEN_BOOTS));
				container.add(button15 = new GuiButtonItem(40, x + 118 + 20 * 4 - 4, y, 18, 20, Items.DIAMOND_BOOTS));
				container.add(button16 = new GuiButtonWorldHandler(41, x + 118 + 20 * 5 - 5, y, 20, 20, null));
				
				container.add(button17 = new GuiButtonItem(42, x + 118, y + 24, 18, 20, Items.WOODEN_SWORD));
				container.add(button18 = new GuiButtonItem(43, x + 118 + 20 - 1, y + 24, 18, 20, Items.STONE_SWORD));
				container.add(button19 = new GuiButtonItem(44, x + 118 + 20 * 2 - 2, y + 24, 18, 20, Items.IRON_SWORD));
				container.add(button20 = new GuiButtonItem(45, x + 118 + 20 * 3 - 3, y + 24, 18, 20, Items.GOLDEN_SWORD));
				container.add(button21 = new GuiButtonItem(46, x + 118 + 20 * 4 - 4, y + 24, 18, 20, Items.DIAMOND_SWORD));
				container.add(button22 = new GuiButtonWorldHandler(47, x + 118 + 20 * 5 - 5, y + 24, 20, 20, null));
				
				container.add(button23 = new GuiButtonItem(48, x + 118, y + 48, 18, 20, Items.WOODEN_SWORD));
				container.add(button24 = new GuiButtonItem(49, x + 118 + 20 - 1, y + 48, 18, 20, Items.STONE_SWORD));
				container.add(button25 = new GuiButtonItem(50, x + 118 + 20 * 2 - 2, y + 48, 18, 20, Items.IRON_SWORD));
				container.add(button26 = new GuiButtonItem(51, x + 118 + 20 * 3 - 3, y + 48, 18, 20, Items.GOLDEN_SWORD));
				container.add(button27 = new GuiButtonItem(52, x + 118 + 20 * 4 - 4, y + 48, 18, 20, Items.DIAMOND_SWORD));
				container.add(button28 = new GuiButtonWorldHandler(53, x + 118 + 20 * 5 - 5, y + 48, 20, 20, null));
				
				button11.enabled = !this.builderSummon.getArmorItem(0).equals(Items.LEATHER_BOOTS.getRegistryName());
				button12.enabled = !this.builderSummon.getArmorItem(0).equals(Items.IRON_BOOTS.getRegistryName());
				button13.enabled = !this.builderSummon.getArmorItem(0).equals(Items.CHAINMAIL_BOOTS.getRegistryName());
				button14.enabled = !this.builderSummon.getArmorItem(0).equals(Items.GOLDEN_BOOTS.getRegistryName());
				button15.enabled = !this.builderSummon.getArmorItem(0).equals(Items.DIAMOND_BOOTS.getRegistryName());
				button16.enabled = !this.builderSummon.getArmorItem(0).equals(Blocks.AIR.getRegistryName());
				
				button17.enabled = !this.builderSummon.getHandItem(0).equals(Items.WOODEN_SWORD.getRegistryName());
				button18.enabled = !this.builderSummon.getHandItem(0).equals(Items.STONE_SWORD.getRegistryName());
				button19.enabled = !this.builderSummon.getHandItem(0).equals(Items.IRON_SWORD.getRegistryName());
				button20.enabled = !this.builderSummon.getHandItem(0).equals(Items.GOLDEN_SWORD.getRegistryName());
				button21.enabled = !this.builderSummon.getHandItem(0).equals(Items.DIAMOND_SWORD.getRegistryName());
				button22.enabled = !this.builderSummon.getHandItem(0).equals(Blocks.AIR.getRegistryName());
				
				button23.enabled = !this.builderSummon.getHandItem(1).equals(Items.WOODEN_SWORD.getRegistryName());
				button24.enabled = !this.builderSummon.getHandItem(1).equals(Items.STONE_SWORD.getRegistryName());
				button25.enabled = !this.builderSummon.getHandItem(1).equals(Items.IRON_SWORD.getRegistryName());
				button26.enabled = !this.builderSummon.getHandItem(1).equals(Items.GOLDEN_SWORD.getRegistryName());
				button27.enabled = !this.builderSummon.getHandItem(1).equals(Items.DIAMOND_SWORD.getRegistryName());
				button28.enabled = !this.builderSummon.getHandItem(1).equals(Blocks.AIR.getRegistryName());
			}
			
			button10.enabled = false;
		}
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		switch(button.id)
		{
			case 3:
				this.page = "potionEffects";
				container.initGui();
				break;
			case 4:
				this.page = "attributes";
				container.initGui();
				break;
			case 5:
				this.page = "equipment";
				container.initGui();
				break;
			case 9:
				WorldHandler.sendCommand(this.builderSummon, this.builderSummon.getCustomName().isSpecial());
				break;
			case 12:
				this.page = "main";
				container.initGui();
				break;
			case 14:
				this.potionPage--;
				container.initGui();
				break;
			case 15:
				this.potionPage++;
				container.initGui();
				break;
			case 16:
				this.equipmentPage--;
				container.initGui();
				break;
			case 17:
				this.equipmentPage++;
				container.initGui();
				break;
			case 18:
				this.builderSummon.setArmorItem(3, Items.LEATHER_HELMET);
				container.initGui();
				break;
			case 19:
				this.builderSummon.setArmorItem(3, Items.IRON_HELMET);
				container.initGui();
				break;
			case 20:
				this.builderSummon.setArmorItem(3, Items.CHAINMAIL_HELMET);
				container.initGui();
				break;
			case 21:
				this.builderSummon.setArmorItem(3, Items.GOLDEN_HELMET);
				container.initGui();
				break;
			case 22:
				this.builderSummon.setArmorItem(3, Items.DIAMOND_HELMET);
				container.initGui();
				break;
			case 23:
				this.builderSummon.setArmorItem(3, Blocks.AIR);
				container.initGui();
				break;
			case 24:
				this.builderSummon.setArmorItem(2, Items.LEATHER_CHESTPLATE);
				container.initGui();
				break;
			case 25:
				this.builderSummon.setArmorItem(2, Items.IRON_CHESTPLATE);
				container.initGui();
				break;
			case 26:
				this.builderSummon.setArmorItem(2, Items.CHAINMAIL_CHESTPLATE);
				container.initGui();
				break;
			case 27:
				this.builderSummon.setArmorItem(2, Items.GOLDEN_CHESTPLATE);
				container.initGui();
				break;
			case 28:
				this.builderSummon.setArmorItem(2, Items.DIAMOND_CHESTPLATE);
				container.initGui();
				break;
			case 29:
				this.builderSummon.setArmorItem(2, Blocks.AIR);
				container.initGui();
				break;
			case 30:
				this.builderSummon.setArmorItem(1, Items.LEATHER_LEGGINGS);
				container.initGui();
				break;
			case 31:
				this.builderSummon.setArmorItem(1, Items.IRON_LEGGINGS);
				container.initGui();
				break;
			case 32:
				this.builderSummon.setArmorItem(1, Items.CHAINMAIL_LEGGINGS);
				container.initGui();
				break;
			case 33:
				this.builderSummon.setArmorItem(1, Items.GOLDEN_LEGGINGS);
				container.initGui();
				break;
			case 34:
				this.builderSummon.setArmorItem(1, Items.DIAMOND_LEGGINGS);
				container.initGui();
				break;
			case 35:
				this.builderSummon.setArmorItem(1, Blocks.AIR);
				container.initGui();
				break;
			case 36:
				this.builderSummon.setArmorItem(0, Items.LEATHER_BOOTS);
				container.initGui();
				break;
			case 37:
				this.builderSummon.setArmorItem(0, Items.IRON_BOOTS);
				container.initGui();
				break;
			case 38:
				this.builderSummon.setArmorItem(0, Items.CHAINMAIL_BOOTS);
				container.initGui();
				break;
			case 39:
				this.builderSummon.setArmorItem(0, Items.GOLDEN_BOOTS);
				container.initGui();
				break;
			case 40:
				this.builderSummon.setArmorItem(0, Items.DIAMOND_BOOTS);
				container.initGui();
				break;
			case 41:
				this.builderSummon.setArmorItem(0, Blocks.AIR);
				container.initGui();
				break;
			case 42:
				this.builderSummon.setHandItem(0, Items.WOODEN_SWORD);
				container.initGui();
				break;
			case 43:
				this.builderSummon.setHandItem(0, Items.STONE_SWORD);
				container.initGui();
				break;
			case 44:
				this.builderSummon.setHandItem(0, Items.IRON_SWORD);
				container.initGui();
				break;
			case 45:
				this.builderSummon.setHandItem(0, Items.GOLDEN_SWORD);
				container.initGui();
				break;
			case 46:
				this.builderSummon.setHandItem(0, Items.DIAMOND_SWORD);
				container.initGui();
				break;
			case 47:
				this.builderSummon.setHandItem(0, Blocks.AIR);
				container.initGui();
				break;
			case 48:
				this.builderSummon.setHandItem(1, Items.WOODEN_SWORD);
				container.initGui();
				break;
			case 49:
				this.builderSummon.setHandItem(1, Items.STONE_SWORD);
				container.initGui();
				break;
			case 50:
				this.builderSummon.setHandItem(1, Items.IRON_SWORD);
				container.initGui();
				break;
			case 51:
				this.builderSummon.setHandItem(1, Items.GOLDEN_SWORD);
				container.initGui();
				break;
			case 52:
				this.builderSummon.setHandItem(1, Items.DIAMOND_SWORD);
				container.initGui();
				break;
			case 53:
				this.builderSummon.setHandItem(1, Blocks.AIR);
				container.initGui();
				break;
			case 54:
				int count = 0;
				
				for(ResourceLocation value : this.getSortedPotionList())
				{
					Potion potion = Potion.getPotionFromResourceLocation(value.toString());
					
					if(!potion.equals(MobEffects.INSTANT_DAMAGE) && !potion.equals(MobEffects.INSTANT_HEALTH))
					{
						if(count == this.potionPage)
						{
							this.builderSummon.setShowParticles(potion, !this.builderSummon.getShowParticles(potion));
							break;
						}
						
						count++;
					}
				}
				container.initGui();
				break;
			default:
				break;
		}
	}
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(this.page.equals("main"))
		{
			this.mobField.drawTextBox();
			this.customNameField.drawTextBox();
			this.passengerField.drawTextBox();
		}
		else if(this.page.equals("equipment"))
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		 	Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/gui/container/beacon.png"));
		 	
		 	for(int row = 0; row < 3; row++)
		 	{
		 		container.drawTexturedModalRect(x + 116 + 99, y + 2 + 24 * row, 112, 221, 16, 16);
		 	}
		}
	}
	
	@Override
	public void keyTyped(Container container, char charTyped, int keyCode)
	{
		if(this.mobField.textboxKeyTyped(charTyped, keyCode))
		{
			this.mob = this.mobField.getText();
			this.builderSummon.setEntity(this.mob);
			container.initButtons();
		}
		
		if(this.customNameField.textboxKeyTyped(charTyped, keyCode))
		{
			this.name = this.customNameField.getText();
			this.builderSummon.setCustomName(this.name);
			container.initButtons();
		}
		
		if(this.passengerField.textboxKeyTyped(charTyped, keyCode))
		{
			this.passenger = this.passengerField.getText();
			this.builderSummon.setPassenger(this.passenger);
			container.initButtons();
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(this.page.equals("main"))
		{
			this.mobField.mouseClicked(mouseX, mouseY, mouseButton);
			this.customNameField.mouseClicked(mouseX, mouseY, mouseButton);
			this.passengerField.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	private List<ResourceLocation> getSortedPotionList()
	{
		List<ResourceLocation> potions = new ArrayList<ResourceLocation>(Potion.REGISTRY.getKeys());
		potions.sort((a, b) -> I18n.format(Potion.REGISTRY.getObject(a).getName()).compareTo(I18n.format(Potion.REGISTRY.getObject(b).getName())));
		
		return potions;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public String getTitle()
	{
		return I18n.format("gui.worldhandler.title.entities.summon");
	}
	
	@Override
	public String getTabTitle()
	{
		return I18n.format("gui.worldhandler.tab.entities.summon");
	}
	
	@Override
	public String[] getHeadline()
	{
		String[] headline = new String[2];
		
		headline[0] = I18n.format("gui.worldhandler.generic.browse");
		
		if(this.page.equals("potionEffects"))
		{
			headline[1] = (this.potionPage + 1) + "/" + (Potion.REGISTRY.getKeys().size() - 2);
		}
		else if(this.page.equals("equipment"))
		{
			headline[1] = (this.equipmentPage + 1) + "/2";
		}
		else if(this.page.equals("main"))
		{
			headline[1] = I18n.format("gui.worldhandler.generic.options");
		}
		
		return headline;
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SUMMON;
	}
}

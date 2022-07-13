package exopandora.worldhandler.gui.content.impl;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import exopandora.worldhandler.builder.argument.Coordinate;
import exopandora.worldhandler.builder.argument.tag.AbstractAttributeTag;
import exopandora.worldhandler.builder.argument.tag.ActiveEffectsTag;
import exopandora.worldhandler.builder.argument.tag.AttributesTag;
import exopandora.worldhandler.builder.argument.tag.EffectInstance;
import exopandora.worldhandler.builder.argument.tag.EntityTag;
import exopandora.worldhandler.builder.argument.tag.MutableTag;
import exopandora.worldhandler.builder.impl.SummonCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.EnumIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonIcon;
import exopandora.worldhandler.gui.widget.button.GuiButtonItem;
import exopandora.worldhandler.gui.widget.button.GuiSlider;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.gui.widget.button.LogicSliderAttribute;
import exopandora.worldhandler.gui.widget.button.LogicSliderSimple;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicColorMenu;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuColorField;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentSummon extends Content
{
	private static final ResourceLocation BEACON_LOCATION = new ResourceLocation("textures/gui/container/beacon.png");
	private static final Item[] HELMETS =
	{
		Items.AIR,
		Items.LEATHER_HELMET,
		Items.IRON_HELMET,
		Items.CHAINMAIL_HELMET,
		Items.GOLDEN_HELMET,
		Items.DIAMOND_HELMET,
		Items.NETHERITE_HELMET
	};
	private static final Item[] CHESTPLATES =
	{
		Items.AIR,
		Items.LEATHER_CHESTPLATE,
		Items.IRON_CHESTPLATE,
		Items.CHAINMAIL_CHESTPLATE,
		Items.GOLDEN_CHESTPLATE,
		Items.DIAMOND_CHESTPLATE,
		Items.NETHERITE_CHESTPLATE
	};
	private static final Item[] LEGGINGS =
	{
		Items.AIR,
		Items.LEATHER_LEGGINGS,
		Items.IRON_LEGGINGS,
		Items.CHAINMAIL_LEGGINGS,
		Items.GOLDEN_LEGGINGS,
		Items.DIAMOND_LEGGINGS,
		Items.NETHERITE_LEGGINGS
	};
	private static final Item[] BOOTS =
	{
		Items.AIR,
		Items.LEATHER_BOOTS,
		Items.IRON_BOOTS,
		Items.CHAINMAIL_BOOTS,
		Items.GOLDEN_BOOTS,
		Items.DIAMOND_BOOTS,
		Items.NETHERITE_BOOTS
	};
	private static final Item[] SWORDS =
	{
		Items.AIR,
		Items.WOODEN_SWORD,
		Items.STONE_SWORD,
		Items.IRON_SWORD,
		Items.GOLDEN_SWORD,
		Items.DIAMOND_SWORD,
		Items.NETHERITE_SWORD
	};
	private static final Item[][] ARMOR = {HELMETS, CHESTPLATES, LEGGINGS, BOOTS};
	private static final Item[][] HANDS = {SWORDS, SWORDS};
	private static final Random RANDOM = new Random();
	
	private GuiTextFieldTooltip mobField;
	private GuiTextFieldTooltip nbtField;
	
	private int potionPage = 0;
	private boolean editColor;
	
	private Page page = Page.START;
	private String mob;
	private String nbt;
	
	private final SummonCommandBuilder builderSummon = new SummonCommandBuilder();
	private final EntityTag entity = new EntityTag();
	private final AttributesTag attributes = new AttributesTag();
	private final ActiveEffectsTag effects = new ActiveEffectsTag();
	private final MutableTag mutable = new MutableTag();
	private final CommandPreview preview = new CommandPreview(this.builderSummon, SummonCommandBuilder.Label.SUMMON_POS_NBT);
	
	public ContentSummon()
	{
		this.builderSummon.pos().setX(new Coordinate.Ints(Coordinate.Type.LOCAL));
		this.builderSummon.pos().setY(new Coordinate.Ints(Coordinate.Type.LOCAL));
		this.builderSummon.pos().setZ(new Coordinate.Ints(2, Coordinate.Type.LOCAL));
		this.builderSummon.nbt().addTagProvider(this.entity);
		this.builderSummon.nbt().addTagProvider(this.attributes);
		this.builderSummon.nbt().addTagProvider(this.effects);
		this.builderSummon.nbt().addTagProvider(this.mutable);
	}
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void init(Container container)
	{
		for(Attribute attribute : this.attributes.getAttributes())
		{
			double value = this.attributes.get(attribute);
			double range = Config.getSliders().getMaxSummonAttributes();
			
			if(value > range)
			{
				this.attributes.set(attribute, range);
			}
			else if(value < -range)
			{
				this.attributes.set(attribute, -range);
			}
		}
		
		for(MobEffect effect : this.effects.getMobEffects())
		{
			EffectInstance tag = this.effects.getOrCreate(effect);
			
			if(tag.getAmplifier() > Config.getSliders().getMaxSummonPotionAmplifier())
			{
				tag.setAmplifier((byte) Config.getSliders().getMaxSummonPotionAmplifier());
			}
			
			if(tag.getMinutes() > Config.getSliders().getMaxSummonPotionMinutes())
			{
				tag.setMinutes((int) Config.getSliders().getMaxSummonPotionMinutes());
			}
		}
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.mobField = new GuiTextFieldTooltip(x + 118, y, 114, 20, Component.translatable("gui.worldhandler.entities.summon.start.mob_id"));
		this.mobField.setFilter(Predicates.notNull());
		this.mobField.setValue(this.mob);
		this.mobField.setResponder(text ->
		{
			this.mob = text;
			this.builderSummon.entity().deserialize(this.mob);
			this.updateMutableTag();
			container.initButtons();
		});
		
		this.nbtField = new GuiTextFieldTooltip(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.entities.summon.start.custom_nbt"));
		this.nbtField.setFilter(Predicates.notNull());
		this.nbtField.setValue(this.nbt);
		this.nbtField.setResponder(text ->
		{
			this.nbt = text;
			this.entity.setNBT(this.nbt);
			container.initButtons();
		});
		
		if(Page.START.equals(this.page))
		{
			MenuColorField customName = new MenuColorField(x, y, "gui.worldhandler.entities.summon.start.custom_name", this.entity.getCustomName(), new ILogicColorMenu()
			{
				@Override
				public boolean doDrawButtons()
				{
					return ContentSummon.this.editColor;
				}
				
				@Override
				public boolean doDrawTextField()
				{
					return ContentSummon.this.editColor;
				}
				
				@Override
				public String getId()
				{
					return "custom_name";
				}
			});
			
			container.add(customName);
		}
		else if(Page.ATTRIBUTES.equals(this.page))
		{
			MenuPageList<Attribute> attributes = new MenuPageList<Attribute>(x + 118, y, AbstractAttributeTag.ATTRIBUTES, 114, 20, 3, container, new ILogicPageList<Attribute>()
			{
				@Override
				public MutableComponent translate(Attribute attribute)
				{
					return Component.translatable(attribute.getDescriptionId());
				}
				
				@Override
				public MutableComponent toTooltip(Attribute attribute)
				{
					return Component.literal(ForgeRegistries.ATTRIBUTES.getKey(attribute).toString());
				}
				
				@Override
				public void onClick(Attribute item)
				{
					
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, Attribute attribute, ActionHandler actionHandler)
				{
					return new GuiSlider(x, y, width, height, -Config.getSliders().getMaxSummonAttributes(), Config.getSliders().getMaxSummonAttributes(), 0, container, new LogicSliderAttribute(attribute, text, value ->
					{
						ContentSummon.this.attributes.set(attribute, value);
					}));
				}
				
				@Override
				public boolean doDisable()
				{
					return false;
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
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		GuiButtonBase button4;
		GuiButtonBase button5;
		GuiButtonBase button6;
		GuiButtonBase button7;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, "gui.worldhandler.generic.back", () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, "gui.worldhandler.generic.backToGame", ActionHelper::backToGame));
		
		container.add(button4 = new GuiButtonBase(x, y, 114, 20, "gui.worldhandler.entities.summon.start", () -> this.changePage(container, Page.START)));
		container.add(button5 = new GuiButtonBase(x, y + 24, 114, 20, "gui.worldhandler.entities.summon.potion_effects", () -> this.changePage(container, Page.POTIONS)));
		container.add(button6 = new GuiButtonBase(x, y + 48, 114, 20, "gui.worldhandler.entities.summon.attributes", () -> this.changePage(container, Page.ATTRIBUTES)));
		container.add(button7 = new GuiButtonBase(x, y + 72, 114, 20, "gui.worldhandler.entities.summon.equipment", () -> this.changePage(container, Page.EQUIPMENT)));
		
		if(Page.START.equals(this.page))
		{
			button4.active = false;
			
			if(this.editColor)
			{
				container.add(new GuiButtonBase(x + 118, y + 72, 114, 20, "gui.worldhandler.generic.done", () -> this.toggleEditColor(container)));
			}
			else
			{
				container.add(this.mobField);
				container.add(new GuiButtonBase(x + 118, y + 24, 114, 20, "gui.worldhandler.entities.summon.start.custom_name", () -> this.toggleEditColor(container)));
				container.add(this.nbtField);
				
				if(!this.builderSummon.needsCommandBlock(SummonCommandBuilder.Label.SUMMON_POS_NBT, false) && !this.entity.getCustomName().isStyled())
				{
					container.add(button3 = new GuiButtonBase(x + 118, y + 72, 114, 20, "gui.worldhandler.title.entities.summon", () -> this.send(container.getPlayer())));
				}
				else
				{
					container.add(button3 = new GuiButtonBase(x + 118, y + 72, 114, 20, "gui.worldhandler.actions.place_command_block", () -> this.send(container.getPlayer())));
				}
				
				button3.active = this.builderSummon.entity().hasValue();
			}
		}
		else if(Page.POTIONS.equals(this.page))
		{
			button5.active = false;
			
			container.add(button1 = new GuiButtonBase(x + 118, y + 72, 56, 20, TextUtils.ARROW_LEFT, () ->
			{
				this.potionPage--;
				container.init();
			}));
			container.add(button2 = new GuiButtonBase(x + 118 + 60, y + 72, 55, 20, TextUtils.ARROW_RIGHT, () ->
			{
				this.potionPage++;
				container.init();
			}));
			
			int count = 0;
			
			for(ResourceLocation location : this.sortedEffects())
			{
				MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(location);
				
				if(effect.equals(MobEffects.HARM) || effect.equals(MobEffects.HEAL))
				{
					continue;
				}
				
				if(this.potionPage == 0)
				{
					button1.active = false;
				}
				
				if(this.potionPage == ForgeRegistries.MOB_EFFECTS.getKeys().size() - 3)
				{
					button2.active = false;
				}
				
				if(count == this.potionPage)
				{
					EffectInstance tag = this.effects.getOrCreate(effect);
					
					container.add(new GuiSlider(x + 118, y, 114, 20, 0, Config.getSliders().getMaxSummonPotionAmplifier(), 0, container, new LogicSliderSimple("amplifier" + ForgeRegistries.MOB_EFFECTS.getKey(effect), Component.translatable(effect.getDescriptionId()), value ->
					{
						tag.setAmplifier(value.byteValue());
					})));
					container.add(new GuiSlider(x + 118, y + 24, 114, 20, 0, Config.getSliders().getMaxSummonPotionMinutes(), 0, container, new LogicSliderSimple("duration" + ForgeRegistries.MOB_EFFECTS.getKey(effect), Component.translatable("gui.worldhandler.potion.time.minutes"), value ->
					{
						tag.setMinutes(value.intValue());
					})));
					container.add(new GuiButtonBase(x + 118, y + 48, 114, 20, Component.translatable("gui.worldhandler.potions.effect.particles", tag.doShowParticles() ? Component.translatable("gui.worldhandler.generic.on") : Component.translatable("gui.worldhandler.generic.off")), () ->
					{
						tag.setShowParticles(!tag.doShowParticles());
						container.init();
					}));
					break;
				}
				
				count++;
			}
		}
		else if(Page.ATTRIBUTES.equals(this.page))
		{
			button6.active = false;
		}
		else if(Page.EQUIPMENT.equals(this.page))
		{
			for(int i = 0; i < 4; i++)
	 		{
				final int index = i;
				
				container.add(new GuiButtonBase(x + 118, y + 24 * i, 20, 20, TextUtils.ARROW_LEFT, () ->
				{
					this.entity.setArmorItem(3 - index, ARMOR[index][Math.floorMod(ArrayUtils.indexOf(ARMOR[index], this.entity.getArmorItem(3 - index)) - 1, ARMOR[index].length)]);
					container.init();
				}));
				container.add(button1 = new GuiButtonItem(x + 118 + 24, y + 24 * i, 20, 20, this.entity.getArmorItem(3 - i), null));
				container.add(new GuiButtonBase(x + 118 + 47, y + 24 * i, 20, 20, TextUtils.ARROW_RIGHT, () ->
				{
					this.entity.setArmorItem(3 - index, ARMOR[index][Math.floorMod(ArrayUtils.indexOf(ARMOR[index], this.entity.getArmorItem(3 - index)) + 1, ARMOR[index].length)]);
					container.init();
				}));
				
				button1.active = false;
	 		}
			
			for(int i = 0; i < 2; i++)
	 		{
				final int index = i;
				
				container.add(new GuiButtonIcon(x + 118 + 70 + 24 * i, y + 12, 20, 20, EnumIcon.ARROW_UP, null, () ->
				{
					this.entity.setHandItem(index, HANDS[index][Math.floorMod(ArrayUtils.indexOf(HANDS[index], this.entity.getHandItem(index)) - 1, HANDS[index].length)]);
					container.init();
				}));
				container.add(button1 = new GuiButtonItem(x + 118 + 70 + 24 * i, y + 36, 20, 20, this.entity.getHandItem(i), null));
				container.add(new GuiButtonIcon(x + 118 + 70 + 24 * i, y + 60, 20, 20, EnumIcon.ARROW_DOWN, null, () ->
				{
					this.entity.setHandItem(index, HANDS[index][Math.floorMod(ArrayUtils.indexOf(HANDS[index], this.entity.getHandItem(index)) + 1, HANDS[index].length)]);
					container.init();
				}));
				
				button1.active = false;
	 		}
			
			button7.active = false;
		}
	}
	
	private void send(String player)
	{
		CommandHelper.sendCommand(player, this.builderSummon, SummonCommandBuilder.Label.SUMMON_POS_NBT, this.entity.getCustomName().isStyled());
	}
	
	private void updateMutableTag()
	{
		EntityType<?> entity = this.builderSummon.entity().getEntity();
		
		if(EntityType.CAT.equals(entity))
		{
			this.mutable.setKey("CatType");
			this.mutable.setTag(IntTag.valueOf(RANDOM.nextInt(11)));
		}
		else if(EntityType.VILLAGER.equals(entity))
		{
			for(VillagerProfession profession : ForgeRegistries.VILLAGER_PROFESSIONS)
			{
				if(StringUtils.equalsIgnoreCase(this.mob, profession.toString()))
				{
					CompoundTag villagerData = new CompoundTag();
					villagerData.putString("profession", ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession).toString());
					
					this.mutable.setKey("VillagerData");
					this.mutable.setTag(villagerData);
					
					return;
				}
			}
			
			this.mutable.reset();
		}
		else if(EntityType.ZOMBIE.equals(entity) && StringUtils.containsIgnoreCase(this.mob, "Baby"))
		{
			this.mutable.setKey("IsBaby");
			this.mutable.setTag(ByteTag.valueOf((byte) 1));
		}
		else if(EntityType.CHICKEN.equals(entity) && StringUtils.containsIgnoreCase(this.mob, "Jockey") && !this.entity.hasPassengers())
		{
			ListTag list = new ListTag();
			EntityTag zombie = new EntityTag(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ZOMBIE));
			
			zombie.setIsBaby(true);
			list.add(zombie.value());
			
			this.mutable.setKey("Passengers");
			this.mutable.setTag(list);
		}
		else if(EntityType.SPIDER.equals(entity) && StringUtils.containsIgnoreCase(this.mob, "Jockey") && !this.entity.hasPassengers())
		{
			ListTag list = new ListTag();
			EntityTag skeleton = new EntityTag(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.SKELETON));
			
			skeleton.setHandItem(0, Items.BOW);
			list.add(skeleton.value());
			
			this.mutable.setKey("Passengers");
			this.mutable.setTag(list);
		}
		else
		{
			this.mutable.reset();
		}
	}
	
	@Override
	public void tick(Container container)
	{
		if(Page.START.equals(this.page))
		{
			if(!this.editColor)
			{
				this.mobField.tick();
				this.nbtField.tick();
			}
		}
	}
	
	@Override
	public void drawScreen(PoseStack matrix, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		if(Page.START.equals(this.page))
		{
			if(!this.editColor)
			{
				this.mobField.renderButton(matrix, mouseX, mouseY, partialTicks);
				this.nbtField.renderButton(matrix, mouseX, mouseY, partialTicks);
			}
		}
		else if(Page.POTIONS.equals(this.page))
		{
			Minecraft.getInstance().font.draw(matrix, (this.potionPage + 1) + "/" + (ForgeRegistries.MOB_EFFECTS.getKeys().size() - 2), x + 118, y - 11, Config.getSkin().getHeadlineColor());
		}
		else if(Page.EQUIPMENT.equals(this.page))
		{
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, BEACON_LOCATION);
	 		container.setBlitOffset(0);
	 		
	 		for(int i = 0; i < 4; i++)
	 		{
		 		if(Items.AIR.equals(this.entity.getArmorItem(3 - i)))
		 		{
			 		container.blit(matrix, x + 118 + 24 + 2, y + 2 + 24 * i, 112, 221, 16, 16);
		 		}
	 		}
	 		
	 		for(int i = 0; i < 2; i++)
	 		{
		 		if(Items.AIR.equals(this.entity.getHandItem(i)))
		 		{
			 		container.blit(matrix, x + 118 + 70 + 2 + 24 * i, y + 2 + 36, 112, 221, 16, 16);
		 		}
	 		}
		}
	}
	
	private List<ResourceLocation> sortedEffects()
	{
		return ForgeRegistries.MOB_EFFECTS.getKeys().stream()
				.sorted((a, b) -> I18n.get(ForgeRegistries.MOB_EFFECTS.getValue(a).getDescriptionId()).compareTo(I18n.get(ForgeRegistries.MOB_EFFECTS.getValue(b).getDescriptionId())))
				.collect(Collectors.toList());
	}
	
	private void toggleEditColor(Container container)
	{
		this.editColor = !this.editColor;
		container.init();
	}
	
	private void changePage(Container container, Page page)
	{
		this.page = page;
		container.init();
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.entities.summon");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.entities.summon");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.SUMMON;
	}
	
	public static enum Page
	{
		START,
		POTIONS,
		ATTRIBUTES,
		EQUIPMENT;
	}
}

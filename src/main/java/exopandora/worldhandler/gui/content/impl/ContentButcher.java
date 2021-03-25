package exopandora.worldhandler.gui.content.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Predicates;
import com.mojang.blaze3d.matrix.MatrixStack;

import exopandora.worldhandler.builder.ICommandBuilder;
import exopandora.worldhandler.builder.impl.BuilderButcher;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiTextFieldTooltip;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class ContentButcher extends Content
{
	private GuiTextFieldTooltip radiusField;
	private String radius;
	private final BuilderButcher builderButcher = new BuilderButcher();
	
	@Override
	public ICommandBuilder getCommandBuilder()
	{
		return this.builderButcher;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.radiusField = new GuiTextFieldTooltip(x + 58, y, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.radius"));
		this.radiusField.setFilter(string ->
		{
			if(string == null)
			{
				return false;
			}
			
			if(!string.isEmpty())
			{
				try
				{
					Integer.parseInt(string);
				}
				catch(Exception e)
				{
					return false;
				}
			}
			
			return true;
		});
		this.radiusField.setValue(this.radius);
		this.radiusField.setResponder(text ->
		{
			this.radius = text;
			
			if(!this.radius.isEmpty())
			{
				this.builderButcher.setDistance(Integer.valueOf(this.radius));
			}
			else
			{
				this.builderButcher.setDistance(0);
			}
			
			container.initButtons();
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase slaughter;
		
		container.add(new GuiButtonBase(x, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.add(new GuiButtonBase(x + 118, y + 96, 114, 20, new TranslationTextComponent("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.add(this.radiusField);
		container.add(new GuiButtonBase(x + 58, y + 24, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.configure"), () ->
		{
			ActionHelper.open(Contents.BUTCHER_SETTINGS);
		}));
		
		boolean enabled = this.radius != null && !this.radius.isEmpty();
		
		container.add(slaughter = new GuiButtonBase(x + 58, y + 48, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.slaughter"), () ->
		{
			ContentButcher.slaughter(container.getPlayer(), Config.getButcher().getEntities().stream().map(ForgeRegistries.ENTITIES::getValue).filter(Predicates.notNull()).collect(Collectors.toList()), Integer.parseInt(this.radius));
		}));
		slaughter.active = enabled && !Config.getButcher().getEntities().isEmpty();
		
		container.add(slaughter = new GuiButtonBase(x + 58, y + 72, 114, 20, new TranslationTextComponent("gui.worldhandler.butcher.presets"), () ->
		{
			ActionHelper.open(Contents.BUTCHER_PRESETS.withBuilder(this.builderButcher).withRadius(Integer.parseInt(this.radius)));
		}));
		slaughter.active = enabled;
	}
	
	public static void slaughter(String username, Collection<EntityType<?>> entities, int radius)
	{
		PlayerEntity player = Minecraft.getInstance().player;
		World world = Minecraft.getInstance().level;
		
		if(player != null && world != null)
		{
			AxisAlignedBB aabb = new AxisAlignedBB(player.blockPosition()).inflate(radius);
			
			for(EntityType<?> entity : entities)
			{
				List<? extends Entity> targets = world.getEntities(entity, aabb, Predicates.alwaysTrue());
				targets.removeIf(target -> player.equals(target));
				
				if(!targets.isEmpty())
				{
					CommandHelper.sendCommand(username, new BuilderButcher(entity.getRegistryName(), radius));
				}
			}
		}
	}
	
	@Override
	public void tick(Container container)
	{
		this.radiusField.tick();
	}
	
	@Override
	public void drawScreen(MatrixStack stack, Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		this.radiusField.renderButton(stack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public IFormattableTextComponent getTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.title.entities.butcher");
	}
	
	@Override
	public IFormattableTextComponent getTabTitle()
	{
		return new TranslationTextComponent("gui.worldhandler.tab.entities.butcher");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.BUTCHER;
	}
}

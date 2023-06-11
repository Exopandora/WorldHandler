package exopandora.worldhandler.gui.content.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Predicates;

import exopandora.worldhandler.builder.argument.TargetArgument.SelectorTypes;
import exopandora.worldhandler.builder.impl.KillCommandBuilder;
import exopandora.worldhandler.config.Config;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiHintTextField;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentButcher extends Content
{
	private GuiHintTextField radiusField;
	private String radius;
	private final KillCommandBuilder builderKill = new KillCommandBuilder();
	private final CommandPreview preview = new CommandPreview(this.builderKill, KillCommandBuilder.Label.KILL_TARGETS);
	
	@Override
	public CommandPreview getCommandPreview()
	{
		return this.preview;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		this.radiusField = new GuiHintTextField(x + 58, y, 114, 20, Component.translatable("gui.worldhandler.butcher.radius"));
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
				this.builderKill.targets().setDistance(Double.valueOf(this.radius));
			}
			else
			{
				this.builderKill.targets().setDistance(0D);
			}
			
			container.initButtons();
		});
	}
	
	@Override
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase slaughter;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.back"), () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, Component.translatable("gui.worldhandler.generic.backToGame"), ActionHelper::backToGame));
		
		container.addRenderableWidget(this.radiusField);
		container.addRenderableWidget(new GuiButtonBase(x + 58, y + 24, 114, 20, Component.translatable("gui.worldhandler.butcher.configure"), () ->
		{
			ActionHelper.open(Contents.BUTCHER_SETTINGS);
		}));
		
		boolean enabled = this.radius != null && !this.radius.isEmpty();
		
		container.addRenderableWidget(slaughter = new GuiButtonBase(x + 58, y + 48, 114, 20, Component.translatable("gui.worldhandler.butcher.slaughter"), () ->
		{
			Collection<EntityType<?>> entities = Config.getButcher().getEntities().stream().map(ForgeRegistries.ENTITY_TYPES::getValue).filter(Predicates.notNull()).collect(Collectors.toList());
			ContentButcher.slaughter(container.getPlayer(), entities, Integer.parseInt(this.radius));
		}));
		slaughter.active = enabled && !Config.getButcher().getEntities().isEmpty();
		
		container.addRenderableWidget(slaughter = new GuiButtonBase(x + 58, y + 72, 114, 20, Component.translatable("gui.worldhandler.butcher.presets"), () ->
		{
			ActionHelper.open(Contents.BUTCHER_PRESETS.withBuilder(this.builderKill, KillCommandBuilder.Label.KILL_TARGETS).withRadius(Double.parseDouble(this.radius)));
		}));
		slaughter.active = enabled;
	}
	
	public static void slaughter(String username, Collection<EntityType<?>> entities, double radius)
	{
		Player player = Minecraft.getInstance().player;
		Level level = Minecraft.getInstance().level;
		
		if(player != null && level != null)
		{
			AABB aabb = new AABB(player.blockPosition()).inflate(radius);
			
			for(EntityType<?> entity : entities)
			{
				List<? extends Entity> targets = level.getEntities(entity, aabb, Predicates.alwaysTrue());
				targets.removeIf(target -> player.equals(target));
				
				if(!targets.isEmpty())
				{
					KillCommandBuilder kill = new KillCommandBuilder();
					kill.targets().setSelectorType(SelectorTypes.ALL_ENTITIES);
					kill.targets().setType(ForgeRegistries.ENTITY_TYPES.getKey(entity));
					kill.targets().setDistanceMax(radius);
					CommandHelper.sendCommand(username, kill, KillCommandBuilder.Label.KILL_TARGETS);
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
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.entities.butcher");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.entities.butcher");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.BUTCHER;
	}
}

package exopandora.worldhandler.gui.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestion;

import exopandora.worldhandler.builder.impl.LocateCommandBuilder;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import exopandora.worldhandler.gui.widget.button.GuiButtonBase;
import exopandora.worldhandler.gui.widget.button.GuiButtonTooltip;
import exopandora.worldhandler.gui.widget.menu.impl.ILogicPageList;
import exopandora.worldhandler.gui.widget.menu.impl.MenuPageList;
import exopandora.worldhandler.util.ActionHandler;
import exopandora.worldhandler.util.ActionHelper;
import exopandora.worldhandler.util.CommandHelper;
import exopandora.worldhandler.util.TranslationHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ContentLocate extends Content
{
	private final LocateCommandBuilder builderLocate = new LocateCommandBuilder();
	private final CommandPreview previewLocateBiome = new CommandPreview(this.builderLocate, LocateCommandBuilder.Label.BIOME);
	private final CommandPreview previewLocateStructure = new CommandPreview(this.builderLocate, LocateCommandBuilder.Label.STRUCTURE);
	private final CommandPreview previewLocatePoi = new CommandPreview(this.builderLocate, LocateCommandBuilder.Label.POI);
	private final CachedServerResource<List<ResourceLocation>> structures = new CachedServerResource<List<ResourceLocation>>();
	private Page page = Page.BIOME;
	
	@Override
	public CommandPreview getCommandPreview()
	{
		if(Page.BIOME.equals(this.page))
		{
			return this.previewLocateBiome;
		}
		else if(Page.STRUCTURE.equals(this.page))
		{
			return this.previewLocateStructure;
		}
		else if(Page.POI.equals(this.page))
		{
			return this.previewLocatePoi;
		}
		
		return null;
	}
	
	@Override
	public void initGui(Container container, int x, int y)
	{
		if(Page.BIOME.equals(this.page))
		{
			List<ResourceLocation> biomes = Minecraft.getInstance().getConnection().getSuggestionsProvider().registryAccess()
				.lookup(Registries.BIOME)
				.get()
				.listElementIds()
				.map(ResourceKey::location)
				.collect(Collectors.toList());
			
			MenuPageList<ResourceLocation> list = new MenuPageList<ResourceLocation>(x + 118, y, biomes, 114, 20, 3, container, new ILogicPageList<ResourceLocation>()
			{
				@Override
				public MutableComponent translate(ResourceLocation biome)
				{
					return Component.translatable(biome.toLanguageKey("biome"));
				}
				
				@Override
				public MutableComponent toTooltip(ResourceLocation biome)
				{
					return Component.literal(biome.toString());
				}
				
				@Override
				public void onClick(ResourceLocation biome)
				{
					ContentLocate.this.builderLocate.biome().set(biome);
					container.initButtons();
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, ResourceLocation biome, ActionHandler actionHandler)
				{
					return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(biome), actionHandler);
				}
				
				@Override
				public String getId()
				{
					return "biomes";
				}
			});
			container.addMenu(list);
		}
		else if(Page.STRUCTURE.equals(this.page))
		{
			if(!this.structures.isCurrent())
			{
				Minecraft.getInstance().getConnection().getSuggestionsProvider()
					.customSuggestion(new CommandContext<Object>(null, "locate structure ", null, null, null, null, null, null, null, true))
					.thenAccept(structures -> this.structures.set(structures.getList().stream()
						.map(Suggestion::getText)
						.filter(suggestion -> !suggestion.startsWith("#"))
						.map(ResourceLocation::new)
						.collect(Collectors.toList())))
					.thenRun(container::init);
			}
			else
			{
				MenuPageList<ResourceLocation> list = new MenuPageList<ResourceLocation>(x + 118, y, this.structures.get(), 114, 20, 3, container, new ILogicPageList<ResourceLocation>()
				{
					@Override
					public MutableComponent translate(ResourceLocation structure)
					{
						return Component.literal(structure.toString());
					}
					
					@Override
					public MutableComponent toTooltip(ResourceLocation structure)
					{
						return Component.literal(structure.toString());
					}
					
					@Override
					public void onClick(ResourceLocation structure)
					{
						ContentLocate.this.builderLocate.structure().set(structure);
						container.initButtons();
					}
					
					@Override
					public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, ResourceLocation structure, ActionHandler actionHandler)
					{
						return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(structure), actionHandler);
					}
					
					@Override
					public String getId()
					{
						return "structures";
					}
				});
				container.addMenu(list);
			}
		}
		else if(Page.POI.equals(this.page))
		{
			List<ResourceLocation> pois = new ArrayList<ResourceLocation>(ForgeRegistries.POI_TYPES.getKeys());
			MenuPageList<ResourceLocation> list = new MenuPageList<ResourceLocation>(x + 118, y, pois, 114, 20, 3, container, new ILogicPageList<ResourceLocation>()
			{
				@Override
				public MutableComponent translate(ResourceLocation poi)
				{
					String result = TranslationHelper.translate(poi);
					
					if(result != null)
					{
						return Component.translatable(result);
					}
					
					return Component.literal(poi.toString());
				}
				
				@Override
				public MutableComponent toTooltip(ResourceLocation poi)
				{
					return Component.literal(poi.toString());
				}
				
				@Override
				public void onClick(ResourceLocation poi)
				{
					ContentLocate.this.builderLocate.poi().set(poi);
					container.initButtons();
				}
				
				@Override
				public GuiButtonBase onRegister(int x, int y, int width, int height, MutableComponent text, ResourceLocation poi, ActionHandler actionHandler)
				{
					return new GuiButtonTooltip(x, y, width, height, text, this.toTooltip(poi), actionHandler);
				}
				
				@Override
				public String getId()
				{
					return "pois";
				}
			});
			container.addMenu(list);
		}
	}
	
	public void initButtons(Container container, int x, int y)
	{
		GuiButtonBase button1;
		GuiButtonBase button2;
		GuiButtonBase button3;
		
		container.addRenderableWidget(new GuiButtonBase(x, y + 96, 114, 20, "gui.worldhandler.generic.back", () -> ActionHelper.back(this)));
		container.addRenderableWidget(new GuiButtonBase(x + 118, y + 96, 114, 20, "gui.worldhandler.generic.backToGame", ActionHelper::backToGame));
		
		container.addRenderableWidget(button1 = new GuiButtonBase(x, y, 114, 20, "gui.worldhandler.locate.biome", () -> this.changePage(container, Page.BIOME)));
		container.addRenderableWidget(button2 = new GuiButtonBase(x, y + 24, 114, 20, "gui.worldhandler.locate.structure", () -> this.changePage(container, Page.STRUCTURE)));
		container.addRenderableWidget(button3 = new GuiButtonBase(x, y + 48, 114, 20, "gui.worldhandler.locate.poi", () -> this.changePage(container, Page.POI)));
		container.addRenderableWidget(new GuiButtonBase(x, y + 72, 114, 20, "gui.worldhandler.locate.locate", () ->
		{
			CommandHelper.sendCommand(container.getPlayer(), this.builderLocate, this.page.getLabel());
		}));
		
		button1.active = !Page.BIOME.equals(this.page);
		button2.active = !Page.STRUCTURE.equals(this.page);
		button3.active = !Page.POI.equals(this.page);
	}
	
	private void changePage(Container container, Page page)
	{
		this.page = page;
		container.init();
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.WORLD;
	}
	
	@Override
	public MutableComponent getTitle()
	{
		return Component.translatable("gui.worldhandler.title.world.locate");
	}
	
	@Override
	public MutableComponent getTabTitle()
	{
		return Component.translatable("gui.worldhandler.tab.world.locate");
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.LOCATE;
	}
	
	private static enum Page
	{
		BIOME(LocateCommandBuilder.Label.BIOME),
		STRUCTURE(LocateCommandBuilder.Label.STRUCTURE),
		POI(LocateCommandBuilder.Label.POI);
		
		private final LocateCommandBuilder.Label label;
		
		private Page(LocateCommandBuilder.Label label)
		{
			this.label = label;
		}
		
		public LocateCommandBuilder.Label getLabel()
		{
			return this.label;
		}
	}
	
	private static class CachedServerResource<T>
	{
		private UUID connectionId = null;
		private T value;
		
		public void set(T value)
		{
			this.connectionId = this.getCurrentConntectionId();
			this.value = value;
		}
		
		public T get()
		{
			return this.value;
		}
		
		public boolean isCurrent()
		{
			return this.connectionId == this.getCurrentConntectionId();
		}
		
		private UUID getCurrentConntectionId()
		{
			return Minecraft.getInstance().getConnection().getId();
		}
	}
}

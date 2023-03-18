package exopandora.worldhandler.gui.content;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import exopandora.worldhandler.Main;
import exopandora.worldhandler.gui.content.impl.ContentAdvancements;
import exopandora.worldhandler.gui.content.impl.ContentButcher;
import exopandora.worldhandler.gui.content.impl.ContentButcherPresets;
import exopandora.worldhandler.gui.content.impl.ContentButcherSettings;
import exopandora.worldhandler.gui.content.impl.ContentChangeWorld;
import exopandora.worldhandler.gui.content.impl.ContentCommandStack;
import exopandora.worldhandler.gui.content.impl.ContentContainers;
import exopandora.worldhandler.gui.content.impl.ContentContinue;
import exopandora.worldhandler.gui.content.impl.ContentCustomItem;
import exopandora.worldhandler.gui.content.impl.ContentEditBlocks;
import exopandora.worldhandler.gui.content.impl.ContentEnchantment;
import exopandora.worldhandler.gui.content.impl.ContentExperience;
import exopandora.worldhandler.gui.content.impl.ContentGamerules;
import exopandora.worldhandler.gui.content.impl.ContentLocate;
import exopandora.worldhandler.gui.content.impl.ContentMain;
import exopandora.worldhandler.gui.content.impl.ContentMultiplayer;
import exopandora.worldhandler.gui.content.impl.ContentNoteEditor;
import exopandora.worldhandler.gui.content.impl.ContentPlayer;
import exopandora.worldhandler.gui.content.impl.ContentPotions;
import exopandora.worldhandler.gui.content.impl.ContentRecipes;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardObjectives;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardPlayers;
import exopandora.worldhandler.gui.content.impl.ContentScoreboardTeams;
import exopandora.worldhandler.gui.content.impl.ContentSettings;
import exopandora.worldhandler.gui.content.impl.ContentSignEditor;
import exopandora.worldhandler.gui.content.impl.ContentSummon;
import exopandora.worldhandler.gui.content.impl.ContentUsercontent;
import exopandora.worldhandler.gui.content.impl.ContentWorldInfo;
import exopandora.worldhandler.usercontent.UsercontentLoader;
import exopandora.worldhandler.util.TranslationHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;

public abstract class Content implements IContent
{
	public static IForgeRegistry<Content> REGISTRY;
	public static final ResourceKey<Registry<Content>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(Main.MODID, "content"));
	
	@SubscribeEvent
	public static void createRegistry(NewRegistryEvent event)
	{
		event.create(new RegistryBuilder<Content>()
				.setName(REGISTRY_KEY.location())
				.disableSaving()
				.disableSync(), registry -> REGISTRY = registry);
	}
	
	@SubscribeEvent
	public static void register(RegisterEvent event)
	{
		if(event.getRegistryKey().equals(REGISTRY_KEY))
		{
			//MAIN
			TranslationHelper.register(event, REGISTRY_KEY, "main", () -> new ContentMain());
			TranslationHelper.register(event, REGISTRY_KEY, "containers", () -> new ContentContainers());
			TranslationHelper.register(event, REGISTRY_KEY, "multiplayer", () -> new ContentMultiplayer());
			
			//ENTITIES
			TranslationHelper.register(event, REGISTRY_KEY, "summon", () -> new ContentSummon());
			TranslationHelper.register(event, REGISTRY_KEY, "butcher", () -> new ContentButcher());
			TranslationHelper.register(event, REGISTRY_KEY, "butcher_settings", () -> new ContentButcherSettings());
			TranslationHelper.register(event, REGISTRY_KEY, "butcher_presets", () -> new ContentButcherPresets());
			
			//ITEMS
			TranslationHelper.register(event, REGISTRY_KEY, "custom_item", () -> new ContentCustomItem());
			TranslationHelper.register(event, REGISTRY_KEY, "enchantment", () -> new ContentEnchantment());
			TranslationHelper.register(event, REGISTRY_KEY, "recipes", () -> new ContentRecipes());
			
			//BLOCKS
			TranslationHelper.register(event, REGISTRY_KEY, "edit_blocks", () -> new ContentEditBlocks());
			TranslationHelper.register(event, REGISTRY_KEY, "sign_editor", () -> new ContentSignEditor());
			TranslationHelper.register(event, REGISTRY_KEY, "note_editor", () -> new ContentNoteEditor());
			
			//WORLD
			TranslationHelper.register(event, REGISTRY_KEY, "world", () -> new ContentWorldInfo());
			TranslationHelper.register(event, REGISTRY_KEY, "gamerules", () -> new ContentGamerules());
			TranslationHelper.register(event, REGISTRY_KEY, "locate", () -> new ContentLocate());
			
			//PLAYER
			TranslationHelper.register(event, REGISTRY_KEY, "player", () -> new ContentPlayer());
			TranslationHelper.register(event, REGISTRY_KEY, "experience", () -> new ContentExperience());
			TranslationHelper.register(event, REGISTRY_KEY, "advancements", () -> new ContentAdvancements());
			
			//SCOREBOARD
			TranslationHelper.register(event, REGISTRY_KEY, "scoreboard_objectives", () -> new ContentScoreboardObjectives());
			TranslationHelper.register(event, REGISTRY_KEY, "scoreboard_teams", () -> new ContentScoreboardTeams());
			TranslationHelper.register(event, REGISTRY_KEY, "scoreboard_players", () -> new ContentScoreboardPlayers());
			
			//MISC
			TranslationHelper.register(event, REGISTRY_KEY, "change_world", () -> new ContentChangeWorld());
			TranslationHelper.register(event, REGISTRY_KEY, "continue", () -> new ContentContinue());
			
			//NO CATEGORY
			TranslationHelper.register(event, REGISTRY_KEY, "potions", () -> new ContentPotions());
			TranslationHelper.register(event, REGISTRY_KEY, "command_stack", () -> new ContentCommandStack());
			TranslationHelper.register(event, REGISTRY_KEY, "settings", () -> new ContentSettings());
			
			//USERCONTENT
			UsercontentLoader.CONFIGS.forEach(config ->
			{
				TranslationHelper.register(event, REGISTRY_KEY, config.getId(), () ->
				{
					try
					{
						return new ContentUsercontent(config);
					}
					catch(Exception e)
					{
						throw new RuntimeException("Error loading js for usercontent: " + config.getId(), e);
					}
				});
			});
		}
	}
	
	private Map<String, Object> persistence;
	
	@SuppressWarnings("unchecked")
	public <T> T getPersistence(String id, Supplier<T> supplier)
	{
		if(this.persistence == null)
		{
			this.persistence = new HashMap<String, Object>();
		}
		
		return (T) this.persistence.computeIfAbsent(id, key -> supplier.get());
	}
	
	public void resetPersistence()
	{
		this.persistence.clear();
	}
	
	@Override
	public void setFocused(boolean focused)
	{
		return;
	}
	
	@Override
	public boolean isFocused()
	{
		return false;
	}
}

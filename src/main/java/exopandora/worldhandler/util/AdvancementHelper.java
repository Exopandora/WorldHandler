package exopandora.worldhandler.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraftforge.common.crafting.conditions.ICondition.IContext;

public class AdvancementHelper implements PreparableReloadListener
{
	private static final AdvancementHelper INSTANCE = new AdvancementHelper();
	private final ServerAdvancementManager manager = new ServerAdvancementManager(new LootDataManager(), IContext.EMPTY);
	
	@Override
	public CompletableFuture<Void> reload(PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			ReloadableResourceManager serverResourceManager = new ReloadableResourceManager(PackType.SERVER_DATA);
			serverResourceManager.registerReloadListener(this.manager);
			return serverResourceManager;
		}).thenCompose(stage::wait).thenAcceptAsync(serverResourceManager ->
		{
			List<PackResources> list = Minecraft.getInstance().getResourcePackRepository().getSelectedPacks().stream().map(Pack::open).collect(Collectors.toList());
			serverResourceManager.createReload(backgroundExecutor, gameExecutor, CompletableFuture.completedFuture(Unit.INSTANCE), list);
		});
	}
	
	public Collection<AdvancementHolder> getAdvancements()
	{
		return this.manager.getAllAdvancements();
	}
	
	public static AdvancementHelper getInstance()
	{
		return INSTANCE;
	}
}

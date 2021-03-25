package exopandora.worldhandler.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.Minecraft;
import net.minecraft.loot.LootPredicateManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.tags.NetworkTagManager;
import net.minecraft.util.Unit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AdvancementHelper implements IFutureReloadListener
{
	private static final AdvancementHelper INSTANCE = new AdvancementHelper();
	private final AdvancementManager manager = new AdvancementManager(new LootPredicateManager());
	
	@Override
	public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			SimpleReloadableResourceManager serverResourceManager = new SimpleReloadableResourceManager(ResourcePackType.SERVER_DATA);
			serverResourceManager.registerReloadListener(new NetworkTagManager());
			serverResourceManager.registerReloadListener(this.manager);
			return serverResourceManager;
		}).thenCompose(stage::wait).thenAcceptAsync(serverResourceManager ->
		{
			List<IResourcePack> list = Minecraft.getInstance().getResourcePackRepository().getSelectedPacks().stream().map(ResourcePackInfo::open).collect(Collectors.toList());
			serverResourceManager.createFullReload(backgroundExecutor, gameExecutor, CompletableFuture.completedFuture(Unit.INSTANCE), list);
		});
	}
	
	public Collection<Advancement> getAdvancements()
	{
		return this.manager.getAllAdvancements();
	}
	
	public static AdvancementHelper getInstance()
	{
		return INSTANCE;
	}
}

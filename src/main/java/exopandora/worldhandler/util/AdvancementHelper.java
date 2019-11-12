package exopandora.worldhandler.util;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.Minecraft;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
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
	private final AdvancementManager manager = new AdvancementManager();
	
	@Override
	public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
	{
		return CompletableFuture.supplyAsync(() ->
		{
			SimpleReloadableResourceManager serverResourceManager = new SimpleReloadableResourceManager(ResourcePackType.SERVER_DATA, Thread.currentThread());
			Minecraft.getInstance().getResourcePackList().getEnabledPacks().stream().map(ResourcePackInfo::getResourcePack).forEach(serverResourceManager::addResourcePack);
			serverResourceManager.addReloadListener(new NetworkTagManager());
			serverResourceManager.addReloadListener(this.manager);
			return serverResourceManager;
		}).thenCompose(stage::markCompleteAwaitingOthers).thenAcceptAsync(serverResourceManager ->
		{
			serverResourceManager.initialReload(backgroundExecutor, gameExecutor, CompletableFuture.completedFuture(Unit.INSTANCE));
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

package exopandora.worldhandler.builder.component;

import exopandora.worldhandler.builder.INBTWritable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IBuilderComponent extends INBTWritable
{
	String getTag();
}

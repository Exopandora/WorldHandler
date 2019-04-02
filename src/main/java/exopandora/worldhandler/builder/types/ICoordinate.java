package exopandora.worldhandler.builder.types;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ICoordinate<T extends Number>
{
	T zero();
}

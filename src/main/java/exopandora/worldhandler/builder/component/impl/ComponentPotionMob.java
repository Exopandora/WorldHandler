package exopandora.worldhandler.builder.component.impl;

import exopandora.worldhandler.builder.component.abstr.ComponentPotion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComponentPotionMob extends ComponentPotion
{
	@Override
	public String getTag()
	{
		return "ActiveEffects";
	}
}

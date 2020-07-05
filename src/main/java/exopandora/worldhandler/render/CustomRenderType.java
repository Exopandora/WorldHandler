package exopandora.worldhandler.render;

import java.util.OptionalDouble;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class CustomRenderType extends RenderType
{
	public static final RenderType LINES2 = RenderType.makeType("lines2",
			DefaultVertexFormats.POSITION_COLOR,
			GL11.GL_LINES,
			256,
			RenderType.State.getBuilder()
				.line(new RenderState.LineState(OptionalDouble.empty()))
				.layer(field_239235_M_)
				.transparency(TRANSLUCENT_TRANSPARENCY)
				.writeMask(COLOR_DEPTH_WRITE)
				.build(false));
	
	public CustomRenderType(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn)
	{
		super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
	}
}
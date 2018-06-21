package exopandora.worldhandler.gui.content.impl;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import exopandora.worldhandler.gui.button.GuiButtonWorldHandler;
import exopandora.worldhandler.gui.category.Categories;
import exopandora.worldhandler.gui.category.Category;
import exopandora.worldhandler.gui.container.Container;
import exopandora.worldhandler.gui.content.Content;
import exopandora.worldhandler.gui.content.Contents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ContentPlaysound extends Content
{
	@Override
	public void initButtons(Container container, int x, int y)
	{
		container.add(new GuiButtonWorldHandler(0, x, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.back")));
		container.add(new GuiButtonWorldHandler(1, x + 118, y + 96, 114, 20, I18n.format("gui.worldhandler.generic.backToGame")));
		
		container.add(new GuiButtonWorldHandler(2, x + 118 / 2, y + 72, 114, 20, "Test"));
	}
	
	@Override
	public void actionPerformed(Container container, GuiButton button)
	{
		if(button.id == 2)
		{
			try
			{
				GlStateManager.pushMatrix();
				FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
				buffer.clear();
				buffer.put(new float[] {1, 0, 0, 1,
										0, 1, 0, 0,
										0, 0, 1, 0,
										0, 0, 0, 0});
				buffer.rewind();
				
				System.out.println("-----------------");
				for(int x = 0; x < buffer.capacity(); x++)
				{
					if(x % 4 == 0 && x > 0)
					{
						System.out.println();
					}
					System.out.printf("%02.2f ", buffer.get(x));
				}
				System.out.println();
				
				GL11.glLoadMatrix(buffer);
				GlStateManager.matrixMode(GL11.GL_MODELVIEW);
				
				GlStateManager.rotate(90, 0, 1, 0);
//				GlStateManager.translate(1, 1, 1);
				
				float[] array = new float[buffer.capacity()];
				GlStateManager.getFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
				buffer.get(array);
				
				System.out.println("-----------------");
				for(int x = 0; x < array.length; x++)
				{
					if(x % 4 == 0 && x > 0)
					{
						System.out.println();
					}
					System.out.printf("%02.2f ", array[x]);
				}
				System.out.println();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private Vector3f low = null;
	private Vector3f high = null;
	
	@Override
	public void drawScreen(Container container, int x, int y, int mouseX, int mouseY, float partialTicks)
	{
		int width = x + 232 / 2;
		int height = y + 50;
		
		container.drawRect(width - 20, height - 40, width + 20, height, 0xFFFFFFFF);
		
		Class<? extends Entity> entityClass = EntityList.getClass(new ResourceLocation("witch"));
		
		try
		{
			Entity entity = EntityList.newEntity(entityClass, Minecraft.getMinecraft().world);
//			entity.ticksExisted++;
			
//			if(entity instanceof EntityLiving)
//			{
//				((EntityLiving) entity).onInitialSpawn(new DifficultyInstance(EnumDifficulty.NORMAL, 0, 0, 0), null);
//			}
			
			if(entity instanceof EntityLivingBase)
			{
				int maxWidth = 40;
				int maxHeight = 40;
				
				int xScale = 0;
				int yScale = 0;
				
				Render<? extends Entity> render = Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(entity.getClass());
				
				if(render instanceof RenderLiving)
				{
					final RenderLiving living = (RenderLiving) render;
					living.getMainModel().setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, entity);
					
					System.out.println("--------------");
					
					this.high = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
					this.low = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
					
					this.iterateModel(this.getBaseModels(living.getMainModel().boxList), new Stack(), vectors ->
					{
						for(int i = 0; i < vectors.length; i++)
						{
							this.low = new Vector3f(Math.min(this.low.x, vectors[i].x), Math.min(this.low.y, vectors[i].y), Math.min(this.low.z, vectors[i].z));
							this.high = new Vector3f(Math.max(this.high.x, vectors[i].x), Math.max(this.high.y, vectors[i].y), Math.max(this.high.z, vectors[i].z));
						}
					});
					
//					System.out.println(this.high.y - this.low.y);
					
					float widthInBlocks = Math.abs(this.high.x - this.low.x) / 16;
					float heightInBlocks = Math.abs(this.high.y - this.low.y) / 16;
					
//					System.out.println("Height: " + heightInBlocks);
//					System.out.println("Ent:	" + entity.height);
//					System.out.println("High: " + this.high);
//					System.out.println("Low:  " + this.low);
					
					xScale = MathHelper.floor(maxWidth / widthInBlocks);
					yScale = MathHelper.floor(maxHeight / heightInBlocks);
				}
				
//				xScale = MathHelper.floor(maxWidth / entity.width);
//				yScale = MathHelper.floor(maxHeight / entity.height);
				
//				System.out.println(low);
//				System.out.println(high);
				int scale = Math.min(xScale, yScale);
				int yAdjust = scale == xScale ? (maxHeight - scale) / 2 : 0;
				
//				System.out.println(scale);
				GuiInventory.drawEntityOnScreen(width, height - yAdjust, scale, (float) width - mouseX, (float) height - mouseY - entity.getEyeHeight() * yScale - yAdjust, (EntityLivingBase) entity);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void iterateModel(List<ModelRenderer> list, Stack<ModelShifts> shifts, Consumer<Vector3f[]> consumer)
	{
		for(ModelRenderer model : list)
		{
			if(!model.isHidden)
			{
				if(model.showModel)
				{
					/*
					 * offset = box width/height/depth
					 * point = box offset x/y/z
					 * rotation = rotation
					 */
					Vector3f modelOffset = new Vector3f(model.offsetX, model.offsetY, model.offsetZ);
					Vector3f modelRotationAngle = new Vector3f(model.rotateAngleX, model.rotateAngleY, model.rotateAngleZ);
					Vector3f modelRotationPoint = new Vector3f(model.rotationPointX, model.rotationPointY, model.rotationPointZ);
					
					shifts.push(new ModelShifts(modelOffset, modelRotationAngle, modelRotationPoint));
					
					if(model.childModels != null)
					{
						this.iterateModel(model.childModels, shifts, consumer);
					}
					
					for(ModelBox box : model.cubeList)
					{
						if(box != null)
						{
							Vector3f[] vectors = new Vector3f[]
							{
								new Vector3f(box.posX1, box.posY1, box.posZ1),
								new Vector3f(box.posX2, box.posY1, box.posZ1),
								new Vector3f(box.posX2, box.posY1, box.posZ2),
								new Vector3f(box.posX1, box.posY1, box.posZ2),
								new Vector3f(box.posX1, box.posY2, box.posZ1),
								new Vector3f(box.posX2, box.posY2, box.posZ1),
								new Vector3f(box.posX2, box.posY2, box.posZ2),
								new Vector3f(box.posX1, box.posY2, box.posZ2)
							};
							
//							System.out.println(shifts.size());
							
							for(int x = 0; x < 2; x++)
							{
								for(ModelShifts shift : shifts)
								{
									this.translate(shift.getOffset(), x, vectors);
//									GlStateManager.translate(shift.getOffset().x, shift.getOffset().y, shift.getOffset().z);
									
									if(shift.getAngle().equals(ORIGIN))
									{
										if(!shift.getPoint().equals(ORIGIN))
										{
											this.translate(shift.getPoint(), x, vectors);
//											GlStateManager.translate(shift.getPoint().x, shift.getPoint().y, shift.getPoint().z);
										}
									}
									else
									{
										this.translate(shift.getPoint(), x, vectors);
//										GlStateManager.translate(shift.getPoint().x, shift.getPoint().y, shift.getPoint().z);
										
										GlStateManager.pushMatrix();
										FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
										buffer.clear();
										this.loadMatrix(buffer, x, vectors);
										
										if(shift.getAngle().y != 0.0F)
										{
											GlStateManager.rotate(-shift.getAngle().y * RADIANT, 0.0F, 0.0F, 1.0F);
										}
										
										if(shift.getAngle().z != 0.0F)
										{
											GlStateManager.rotate(-shift.getAngle().z * RADIANT, 0.0F, 1.0F, 0.0F);
										}
										
										if(shift.getAngle().x != 0.0F)
										{
											GlStateManager.rotate(-shift.getAngle().x * RADIANT, 1.0F, 0.0F, 0.0F);
										}
										
										this.getMatrix(buffer, x, vectors);
										GlStateManager.popMatrix();
									}
								}
							}
							
							for(Vector3f vec : vectors)
							{
								System.out.println(vec.x + "\t" + -vec.y + "\t" + vec.z);
							}
							
							consumer.accept(vectors);
						}
					}
					
					shifts.pop();
				}
			}
		}
	}
	
	private void translate(Vector3f translation, int vectorArrayIndex, Vector3f[] vectors)
	{
		int arrayPartLength = 4;
		int arrayPart = vectorArrayIndex * arrayPartLength;
		int arrayPartMax = arrayPart + arrayPartLength;
		
		for(int y = arrayPart; y < arrayPartMax; y++)
		{
			Vector3f.add(vectors[y], translation, vectors[y]);
		}
	}
	
	private void loadMatrix(FloatBuffer buffer, int vectorArrayIndex, Vector3f[] vectors)
	{
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		
		for(int y = 0; y < buffer.capacity(); y++)
		{
			final int index = vectorArrayIndex * 4 + y % 4;
			
			if(y < 4)
			{
				buffer.put(vectors[index].x);
			}
			else if(y >= 4 && y < 8)
			{
				buffer.put(vectors[index].y);
			}
			else if(y >= 8 && y < 12)
			{
				buffer.put(vectors[index].z);
			}
			else
			{
				buffer.put(0);
			}
		}
		
		buffer.rewind();
		GL11.glLoadMatrix(buffer);
	}
	
	private void getMatrix(FloatBuffer buffer, int vectorArrayIndex, Vector3f[] vectors)
	{
		float[] array = new float[buffer.capacity()];
		GlStateManager.getFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		buffer.get(array);
		
		for(int y = 0; y < array.length; y++)
		{
			final int index = vectorArrayIndex * 4 + y % 4;
			
			if(y < 4)
			{
				vectors[index].x = array[y];
			}
			else if(y >= 4 && y < 8)
			{
				vectors[index].y = array[y];
			}
			else if(y >= 8 && y < 12)
			{
				vectors[index].z = array[y];
			}
		}
	}
	
	private static final class ModelShifts
	{
		private final Vector3f offset;
		private final Vector3f angle;
		private final Vector3f point;
		
		public ModelShifts(Vector3f offset, Vector3f angle, Vector3f point)
		{
			this.offset = offset;
			this.angle = angle;
			this.point = point;
		}
		
		public Vector3f getOffset()
		{
			return this.offset;
		}
		
		public Vector3f getAngle()
		{
			return this.angle;
		}
		
		public Vector3f getPoint()
		{
			return this.point;
		}
	}
	
	//TODO
	@Deprecated
	private void printGLMatrix()
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		float[] array = new float[buffer.capacity()];
		buffer.get(array);
		
		System.out.println();
		
		for(int x = 0; x < buffer.capacity(); x += 4)
		{
			System.out.println(String.format("%02.2f %02.2f %02.2f %02.2f", array[x], array[x + 1], array[x + 2], array[x + 3]));
		}
	}
	
	private static final float RADIANT = (float) (180F / Math.PI);
	private static final Vector3f ORIGIN = new Vector3f();
	
	private List<ModelRenderer> getBaseModels(List<ModelRenderer> list)
	{
		Set<ModelRenderer> baseModels = this.getModels(list);
		baseModels.removeAll(this.getAllChildren(list));
		return new ArrayList<ModelRenderer>(baseModels);
	}
	
	private Set<ModelRenderer> getAllChildren(List<ModelRenderer> list)
	{
		Set<ModelRenderer> result = new HashSet<ModelRenderer>();
		
		for(ModelRenderer model : list)
		{
			if(model.childModels != null)
			{
				result.addAll(this.getModels(model.childModels));
			}
		}
		
		return result;
	}
	
	private Set<ModelRenderer> getModels(List<ModelRenderer> list)
	{
		Set<ModelRenderer> result = new HashSet<ModelRenderer>();
		
		for(ModelRenderer model : list)
		{
			if(!result.contains(model))
			{
				result.add(model);
			}
			
			if(model.childModels != null)
			{
				result.addAll(this.getModels(model.childModels));
			}
		}
		
		return result;
	}
	
	@Override
	public Category getCategory()
	{
		return Categories.ENTITIES;
	}
	
	@Override
	public String getTitle()
	{
		return "Playsound";
	}
	
	@Override
	public String getTabTitle()
	{
		return "Playsound";
	}
	
	@Override
	public Content getActiveContent()
	{
		return Contents.PLAYSOUND;
	}
}

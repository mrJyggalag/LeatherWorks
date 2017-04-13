package panda.leatherworks.gui;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPack extends GuiContainer {
	private static final ResourceLocation PACK_GUI_TEXTURES = new ResourceLocation("leatherworks:textures/gui/container/pack.png");


	private ItemStack item;

	public GuiPack(InventoryPlayer playerInv, World worldIn, ItemStack Packitem) {
		this(playerInv, worldIn, BlockPos.ORIGIN, Packitem);
		
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	public GuiPack(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition, ItemStack Packitem) {
		super(new ContainerPack(playerInv, worldIn, blockPosition,Packitem));
		this.xSize = 176;
		this.ySize = 206;
		this.guiLeft = Math.abs((this.width - this.xSize) / 2);
		this.guiTop = Math.abs((this.height - this.ySize) / 2);
		this.item = Packitem;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(this.item.getDisplayName(), 50, 6, 12963509);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(PACK_GUI_TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
}
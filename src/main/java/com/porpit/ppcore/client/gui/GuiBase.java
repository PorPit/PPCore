package com.porpit.ppcore.client.gui;

import com.porpit.ppcore.inventory.ContainerBase;
import net.minecraft.client.gui.inventory.GuiContainer;

public abstract class GuiBase extends GuiContainer {

    protected ContainerBase container;

    public GuiBase(ContainerBase inventorySlotsIn){
        super(inventorySlotsIn);
    }
    public GuiBase(ContainerBase inventorySlotsIn,int xSize,int ySize) {
        super(inventorySlotsIn);
        this.xSize=xSize;
        this.ySize=ySize;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected abstract void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY);

    @Override
    protected  abstract void drawGuiContainerForegroundLayer(int mouseX, int mouseY);
}

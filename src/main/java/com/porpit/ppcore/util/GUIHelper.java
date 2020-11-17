package com.porpit.ppcore.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import javax.vecmath.Point2i;

public class GUIHelper {
    public static Point2i getMousePos( ScaledResolution scaledResolution){
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();
        int mouseX = Mouse.getX() * width / Minecraft.getMinecraft().displayWidth;
        int mouseZ = height - Mouse.getY() * height / Minecraft.getMinecraft().displayHeight - 1;
        return new Point2i(mouseX,mouseZ);
    }
}

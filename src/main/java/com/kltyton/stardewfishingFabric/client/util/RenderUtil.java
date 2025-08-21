package com.kltyton.stardewfishingFabric.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class RenderUtil {
    public static void blitF(GuiGraphics guiGraphics, ResourceLocation texture, float x, float y, int uOffset, int vOffset, int uWidth, int vHeight) {
        // Use GuiGraphics blit method with float coordinates
        guiGraphics.blit(texture, (int)x, (int)y, uOffset, vOffset, uWidth, vHeight);
    }

    public static void fillF(GuiGraphics guiGraphics, float pMinX, float pMinY, float pMaxX, float pMaxY, float pZ, int pColor) {
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        if (pMinX < pMaxX) {
            float temp = pMinX;
            pMinX = pMaxX;
            pMaxX = temp;
        }

        if (pMinY < pMaxY) {
            float temp = pMinY;
            pMinY = pMaxY;
            pMaxY = temp;
        }

        float alpha =  FastColor.ARGB32.alpha(pColor) / 255.0F;
        float red = FastColor.ARGB32.red(pColor) / 255.0F;
        float green = FastColor.ARGB32.green(pColor) / 255.0F;
        float blue = FastColor.ARGB32.blue(pColor) / 255.0F;
        VertexConsumer vertexconsumer = guiGraphics.bufferSource().getBuffer(RenderType.gui());
        vertexconsumer.addVertex(matrix4f, pMinX, pMinY, pZ).setColor(red, green, blue, alpha);
        vertexconsumer.addVertex(matrix4f, pMinX, pMaxY, pZ).setColor(red, green, blue, alpha);
        vertexconsumer.addVertex(matrix4f, pMaxX, pMaxY, pZ).setColor(red, green, blue, alpha);
        vertexconsumer.addVertex(matrix4f, pMaxX, pMinY, pZ).setColor(red, green, blue, alpha);
        guiGraphics.flush();
    }

    public static void drawRotatedAround(PoseStack poseStack, float radians, float pivotX, float pivotY, Runnable runnable) {
        poseStack.pushPose();
        poseStack.rotateAround(Axis.ZN.rotation(radians), pivotX, pivotY, 0);
        runnable.run();
        poseStack.popPose();
    }

    public static void drawWithAlpha(float alpha, Runnable runnable) {
        RenderSystem.setShaderColor(1, 1, 1, alpha);
        runnable.run();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void drawWithBlend(Runnable runnable) {
        RenderSystem.enableBlend();
        runnable.run();
        RenderSystem.disableBlend();
    }

    public static void drawWithShake(PoseStack poseStack, Shake shake, float partialTick, boolean doShake, Runnable runnable) {
        if (doShake) {
            poseStack.pushPose();
            poseStack.translate(shake.getXOffset(partialTick), shake.getYOffset(partialTick), 0);
        }

        runnable.run();

        if (doShake) {
            poseStack.popPose();
        }
    }
}

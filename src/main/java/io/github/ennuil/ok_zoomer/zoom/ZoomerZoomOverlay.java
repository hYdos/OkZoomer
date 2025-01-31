package io.github.ennuil.ok_zoomer.zoom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;

import io.github.ennuil.libzoomer.api.ZoomOverlay;
import io.github.ennuil.ok_zoomer.config.OkZoomerConfigManager;
import io.github.ennuil.ok_zoomer.config.ConfigEnums.ZoomTransitionOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

// Implements the zoom overlay
public class ZoomerZoomOverlay implements ZoomOverlay {
    private static final Identifier OVERLAY_ID = new Identifier("ok_zoomer:zoom_overlay");
    private Identifier textureId;
    private boolean active;
    private boolean zoomActive;
    private double divisor;
    private final MinecraftClient client;

    public float zoomOverlayAlpha = 0.0F;
    public float lastZoomOverlayAlpha = 0.0F;

    public ZoomerZoomOverlay(Identifier textureId) {
        this.textureId = textureId;
        this.active = false;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    public Identifier getIdentifier() {
        return OVERLAY_ID;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public void renderOverlay() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.zoomOverlayAlpha);
        RenderSystem.setShaderTexture(0, this.textureId);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0D, (double)this.client.getWindow().getScaledHeight(), -90.0D).uv(0.0F, 1.0F).next();
        bufferBuilder.vertex((double)this.client.getWindow().getScaledWidth(), (double)this.client.getWindow().getScaledHeight(), -90.0D).uv(1.0F, 1.0F).next();
        bufferBuilder.vertex((double)this.client.getWindow().getScaledWidth(), 0.0D, -90.0D).uv(1.0F, 0.0F).next();
        bufferBuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).next();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void tick(boolean active, double divisor, double transitionMultiplier) {
        this.divisor = divisor;
        this.zoomActive = active;
        if ((!active && zoomOverlayAlpha == 0.0f) || active) {
            this.active = active;
        }

        /*
        Due to how LibZoomer is implemented, it's always going to disappear when the HUD's hidden,
        this is not good for cinematic purposes...
        // TODO - Restore this feature
        if (this.client.options.hudHidden) {
            if (OkZoomerConfigPojo.tweaks.hideZoomOverlay) {
                return;
            }
        }
        */

        float zoomMultiplier = this.zoomActive ? 1.0F : 0.0F;

        lastZoomOverlayAlpha = zoomOverlayAlpha;

        if (OkZoomerConfigManager.ZOOM_TRANSITION.value().equals(ZoomTransitionOptions.SMOOTH)) {
            zoomOverlayAlpha += (zoomMultiplier - zoomOverlayAlpha) * OkZoomerConfigManager.SMOOTH_MULTIPLIER.value();
        } else if (OkZoomerConfigManager.ZOOM_TRANSITION.value().equals(ZoomTransitionOptions.LINEAR)) {
            double linearStep = MathHelper.clamp(1.0F / this.divisor, OkZoomerConfigManager.MINIMUM_LINEAR_STEP.value(), OkZoomerConfigManager.MAXIMUM_LINEAR_STEP.value());

            zoomOverlayAlpha = MathHelper.stepTowards(zoomOverlayAlpha, zoomMultiplier, (float)linearStep);
        }
    }
}

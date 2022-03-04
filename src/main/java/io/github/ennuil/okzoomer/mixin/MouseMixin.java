package io.github.ennuil.okzoomer.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import io.github.ennuil.okzoomer.config.OkZoomerConfigManager;
import io.github.ennuil.okzoomer.config.ConfigEnums.ZoomModes;
import io.github.ennuil.okzoomer.key_binds.ZoomKeyBinds;
import io.github.ennuil.okzoomer.packets.ZoomPackets;
import io.github.ennuil.okzoomer.utils.ZoomUtils;
import net.minecraft.client.Mouse;

// This mixin is responsible for the mouse-behavior-changing part of the zoom
@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Shadow
    private double scrollDelta;
    
    // Handles zoom scrolling
    @Inject(
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;scrollDelta:D", ordinal = 7),
        method = "onMouseScroll(JDD)V",
        cancellable = true
    )
    private void zoomerOnMouseScroll(CallbackInfo info) {
        if (this.scrollDelta != 0.0) {
            if (OkZoomerConfigManager.configInstance.features().getZoomScrolling() && !ZoomPackets.getDisableZoomScrolling()) {
                if (OkZoomerConfigManager.configInstance.features().getZoomMode().equals(ZoomModes.PERSISTENT)) {
                    if (!ZoomKeyBinds.ZOOM_KEY.isPressed()) return;
                }
                
                if (ZoomUtils.ZOOMER_ZOOM.getZoom()) {
                    ZoomUtils.changeZoomDivisor(this.scrollDelta > 0.0);
                    info.cancel();
                }
            }
        }
    }

    // Handles the zoom scrolling reset through the middle button
    @Inject(
        at = @At(value = "INVOKE", target = "net/minecraft/client/option/KeyBind.setKeyPressed(Lcom/mojang/blaze3d/platform/InputUtil$Key;Z)V"),
        method = "onMouseButton(JIII)V",
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void zoomerOnMouseButton(long window, int button, int action, int modifiers, CallbackInfo info, boolean bl, int i) {
        if (OkZoomerConfigManager.configInstance.features().getZoomScrolling() && !ZoomPackets.getDisableZoomScrolling()) {
            if (OkZoomerConfigManager.configInstance.features().getZoomMode().equals(ZoomModes.PERSISTENT)) {
                if (!ZoomKeyBinds.ZOOM_KEY.isPressed()) return;
            }
            
            if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && bl) {
                if (ZoomKeyBinds.ZOOM_KEY.isPressed()) {
                    if (OkZoomerConfigManager.configInstance.tweaks().getResetZoomWithMouse()) {
                        ZoomUtils.resetZoomDivisor(true);
                        info.cancel();
                    }
                }
            }
        }
    }
}
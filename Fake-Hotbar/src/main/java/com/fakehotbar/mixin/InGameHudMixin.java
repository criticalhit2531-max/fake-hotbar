package com.fakehotbar.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Visual-only hotbar reorder.
 *
 * Displayed left-to-right: inventory slots 4, 5, 3, 1, 6, 8, 7, 2, 9.
 * Inventory slots and key bindings are not changed.
 */
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    // Zero-based inventory slot indexes for the requested visual order.
    @Unique
    private static final int[] FAKEHOTBAR_ORDER = {3, 4, 2, 0, 5, 7, 6, 1, 8};

    @Unique
    private int fakehotbar$renderedSlot = 0;

    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void fakehotbar$resetCounter(DrawContext context,
                                         RenderTickCounter tickCounter,
                                         CallbackInfo ci) {
        fakehotbar$renderedSlot = 0;
    }

    @ModifyArg(
        method = "renderHotbar",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/client/render/RenderTickCounter;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V"
        ),
        index = 5
    )
    private ItemStack fakehotbar$changeDisplayedStack(ItemStack originalStack) {
        int displayIndex = fakehotbar$renderedSlot++;
        if (displayIndex >= FAKEHOTBAR_ORDER.length) {
            return originalStack;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return originalStack;
        }

        return client.player.getInventory().getStack(FAKEHOTBAR_ORDER[displayIndex]);
    }
}

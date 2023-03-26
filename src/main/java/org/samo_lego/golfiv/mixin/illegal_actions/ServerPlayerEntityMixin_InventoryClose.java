package org.samo_lego.golfiv.mixin.illegal_actions;

import net.minecraft.entity.Entity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.samo_lego.golfiv.casts.Golfer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

import static org.samo_lego.golfiv.GolfIV.golfConfig;

/**
 * Sets the status of the GUI to open.
 */
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin_InventoryClose {

    private final Golfer golfer = (Golfer) this;

    /**
     * Sets the GUI open status to true
     * if enabled in config.
     */
    @Inject(
            method = "openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/packet/Packet;)V"
            )
    )
    private void setOpenGui(@Nullable NamedScreenHandlerFactory factory, CallbackInfoReturnable<OptionalInt> cir) {
        golfer.setOpenGui(golfConfig.main.checkInventoryActions);
    }

    /**
     * Sets the open GUI status to false when
     * the player is teleported between worlds.
     */
    @Inject(
            method = "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;",
            at = @At("HEAD")
    )
    private void closeGui(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        golfer.setOpenGui(false);
    }

    @Inject(method = "closeHandledScreen", at = @At("TAIL"))
    private void closeHandledScreen(CallbackInfo ci) {
        golfer.setOpenGui(false);
    }
}

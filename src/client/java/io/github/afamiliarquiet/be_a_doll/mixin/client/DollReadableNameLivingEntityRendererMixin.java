package io.github.afamiliarquiet.be_a_doll.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.afamiliarquiet.be_a_doll.BeAMaid;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class DollReadableNameLivingEntityRendererMixin {
	@ModifyExpressionValue(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getCameraEntity()Lnet/minecraft/entity/Entity;"))
	private Entity orDoll(Entity original, @Local(name = "clientPlayerEntity", ordinal = 0) ClientPlayerEntity protagonist) {
		// should effectively make the entity != camera entity when doll, allowing nametag rendering
		return BeAMaid.isDoll(protagonist) ? null : original;
	}
}

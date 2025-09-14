package io.github.afamiliarquiet.be_a_doll.mixin.shoulder_riding;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.afamiliarquiet.be_a_doll.BeADecoration;
import io.github.afamiliarquiet.be_a_doll.letters.S2CDollDismountLetter;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class ShoulderAwarenessPlayerEntityMixin extends LivingEntity {
	@Shadow
	public abstract Arm getMainArm();

	protected ShoulderAwarenessPlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyExpressionValue(method = "addShoulderEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getShoulderEntityLeft()Lnet/minecraft/nbt/NbtCompound;"))
	private NbtCompound checkLeftShoulder(NbtCompound original) {
		if (BeADecoration.shoulderEntityIsEmpty(this, original.isEmpty(), Arm.LEFT)) {
			return original;
		} else {
			NbtCompound narwhal = new NbtCompound();
			narwhal.putBoolean("soFullOfStuff", true);
			return narwhal;
		}
	}

	@ModifyExpressionValue(method = "addShoulderEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getShoulderEntityRight()Lnet/minecraft/nbt/NbtCompound;"))
	private NbtCompound checkRightShoulder(NbtCompound original) {
		if (BeADecoration.shoulderEntityIsEmpty(this, original.isEmpty(), Arm.RIGHT)) {
			return original;
		} else {
			NbtCompound narwhal = new NbtCompound();
			narwhal.putBoolean("soFullOfStuff", true);
			return narwhal;
		}
	}

	@Inject(method = "dismountVehicle", at = @At("HEAD"))
	private void letGoOfIt(CallbackInfo ci) {
		if (this.getVehicle() instanceof ServerPlayerEntity serverPlayerMount) {
			// the instanceof spe already kinda checks for this being on server. should be fine.
			// wait maybe not? it's loading spe on client, is that bad? nah it should be fine.
			// blame backporting if this breaks things, spe's supposed to override dismountVehicle so i can inject there
			ServerPlayNetworking.send(serverPlayerMount, new S2CDollDismountLetter(List.of(this.getId())));
		}
	}
}

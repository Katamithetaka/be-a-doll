package io.github.afamiliarquiet.be_a_doll.mixin.shoulder_riding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class RiptideIgnorePassengersLivingEntityMixin extends Entity {
	public RiptideIgnorePassengersLivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@ModifyVariable(method = "tickRiptide", at = @At("STORE"))
	private Entity isTarget(Entity possibleTarget) {
		// should hopefully effectively make the instanceof check false
		return this.equals(possibleTarget.getVehicle()) ? null : possibleTarget;
	}
}

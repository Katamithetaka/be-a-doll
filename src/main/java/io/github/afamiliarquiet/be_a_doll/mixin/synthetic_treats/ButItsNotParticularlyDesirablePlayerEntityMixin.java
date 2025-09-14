package io.github.afamiliarquiet.be_a_doll.mixin.synthetic_treats;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.afamiliarquiet.be_a_doll.BeAMaid;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class ButItsNotParticularlyDesirablePlayerEntityMixin {
	@WrapOperation(method = "eatFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;eat(Lnet/minecraft/component/type/FoodComponent;)V"))
	private void sorryDollButThatsJustMakingAMessOnTheInside(HungerManager instance, FoodComponent foodComponent, Operation<Void> original) {
		if (BeAMaid.isDoll((PlayerEntity)(Object) this)) {
			instance.addExhaustion(4f);
		} else {
			original.call(instance, foodComponent);
		}
	}
}

package io.github.afamiliarquiet.be_a_doll.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.afamiliarquiet.be_a_doll.diary.BeAWitch;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class FragmentedDamageLivingEntityMixin {
	@ModifyExpressionValue(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z"))
	private boolean butWhatIfFragmentedToo(boolean original) {
		return original || hasStatusEffect(BeAWitch.FRAGMENTED);
	}

//	@ModifyExpressionValue(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;getAmplifier()I"))
//	private int subtractFragmented(int original) {
//		if (hasStatusEffect(BeAWitch.FRAGMENTED)) {
//			return original - (getStatusEffect(BeAWitch.FRAGMENTED).getAmplifier() + 1);
//		} else {
//			return original;
//		}
//	}

	@WrapOperation(method = "modifyAppliedDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;getAmplifier()I"))
	private int waitCrapDontTouchThatNullNOOOOO(StatusEffectInstance instance, Operation<Integer> original) {
//		return instance == null ? -1 : original.call(instance);
		int safeResistanceAmplifier = instance == null ? -1 : original.call(instance);
		if (hasStatusEffect(BeAWitch.FRAGMENTED)) {
			return safeResistanceAmplifier - (getStatusEffect(BeAWitch.FRAGMENTED).getAmplifier() + 1);
		} else {
			return safeResistanceAmplifier;
		}
	}

	@Shadow
	public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

	@Shadow
	public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);
}

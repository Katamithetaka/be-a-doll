package io.github.afamiliarquiet.be_a_doll.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.afamiliarquiet.be_a_doll.BeAMaid;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class DollsNeedNoAirInGameHudMixin {
	// silly little way to dodge calling isDoll multiple times. pretty unnecessary
	@Unique
	private boolean be_a_doll$secureBankDepositBox = false;

	@WrapOperation(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAir()I"))
	private int considerItToBeAtItsMaximumOrMaxedSoToSpeak(PlayerEntity instance, Operation<Integer> original) {
		be_a_doll$secureBankDepositBox = BeAMaid.isDoll(instance);
		return be_a_doll$secureBankDepositBox ? instance.getMaxAir() : original.call(instance);
	}

	@ModifyExpressionValue(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
	private boolean letsSupposeThatIAmADollAndYouAreAWaterAndYouWantToKillMe(boolean original) {
		return original && !be_a_doll$secureBankDepositBox; // i would simply dodge. or maybe not, backporting has made this less robust
	}
}

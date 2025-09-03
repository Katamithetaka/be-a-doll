package io.github.afamiliarquiet.be_a_doll.mixin.client;

import io.github.afamiliarquiet.be_a_doll.BeADoll;
import io.github.afamiliarquiet.be_a_doll.DollishState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class DollishPlayerEntityRenderStateMixin implements DollishState {
	@Unique
	private BeADoll.Variant be_a_doll$variant = BeADoll.Variant.DEFAULT;
	@Unique
	private boolean be_a_doll$doll = false;
	@Unique
	private boolean be_a_doll$targeted = false;
	@Unique
	private Text be_a_doll$dollName = null;

	@Override
	public void be_a_doll$setVariant(BeADoll.Variant variant) {
		this.be_a_doll$variant = variant;
	}

	@Override
	public BeADoll.Variant be_a_doll$getVariant() {
		return be_a_doll$variant;
	}

	@Override
	public void be_a_doll$setDoll(boolean doll) {
		this.be_a_doll$doll = doll;
	}

	@Override
	public boolean be_a_doll$isDoll() {
		return this.be_a_doll$doll;
	}

	@Override
	public void be_a_doll$setTargeted(boolean targeted) {
		this.be_a_doll$targeted = targeted;
	}

	@Override
	public boolean be_a_doll$isTargeted() {
		return this.be_a_doll$targeted;
	}

	@Override
	public void be_a_doll$setDollName(Text name) {
		this.be_a_doll$dollName = name;
	}

	@Override
	public Text be_a_doll$getDollName() {
		return this.be_a_doll$dollName;
	}
}

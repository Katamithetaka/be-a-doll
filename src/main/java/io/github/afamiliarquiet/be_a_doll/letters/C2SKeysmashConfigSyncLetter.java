package io.github.afamiliarquiet.be_a_doll.letters;

import io.github.afamiliarquiet.be_a_doll.BeADoll;
import io.github.afamiliarquiet.be_a_doll.diary.BeALibrarian;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record C2SKeysmashConfigSyncLetter(boolean useKeysmashing, boolean readableSelf, boolean readableOthers, String letterPoolOverride, float restockThreshold, boolean useOrderedSpooling, float baseClarityChance, float startingClarityScore, float keysmashedMultiplier, float spokenLoudlyClarity, float nonletterClarity) implements CustomPayload {
	public static final Id<C2SKeysmashConfigSyncLetter> ID = new Id<>(BeADoll.id("keysmash_config_letter"));

	public static final PacketCodec<ByteBuf, C2SKeysmashConfigSyncLetter> PACKET_CODEC = PacketCodec.ofStatic(C2SKeysmashConfigSyncLetter::encode, C2SKeysmashConfigSyncLetter::decode);

	// this certainly isn't super ideal
	public static final C2SKeysmashConfigSyncLetter DEFAULT = new C2SKeysmashConfigSyncLetter(true, false, true, "", 0.13f, false, 0.31f, 1f, 0.8f, 1.3f, 1f);

	public static void receive(C2SKeysmashConfigSyncLetter letter, ServerPlayNetworking.Context context) {
		BeALibrarian.filePasswordManager(context.player(), letter);
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static C2SKeysmashConfigSyncLetter decode(ByteBuf shmyteShmufSeeIfICare) {
		return new C2SKeysmashConfigSyncLetter(
			PacketCodecs.BOOL.decode(shmyteShmufSeeIfICare),
			PacketCodecs.BOOL.decode(shmyteShmufSeeIfICare),
			PacketCodecs.BOOL.decode(shmyteShmufSeeIfICare),
			PacketCodecs.STRING.decode(shmyteShmufSeeIfICare),
			PacketCodecs.FLOAT.decode(shmyteShmufSeeIfICare),
			PacketCodecs.BOOL.decode(shmyteShmufSeeIfICare),
			PacketCodecs.FLOAT.decode(shmyteShmufSeeIfICare),
			PacketCodecs.FLOAT.decode(shmyteShmufSeeIfICare),
			PacketCodecs.FLOAT.decode(shmyteShmufSeeIfICare),
			PacketCodecs.FLOAT.decode(shmyteShmufSeeIfICare),
			PacketCodecs.FLOAT.decode(shmyteShmufSeeIfICare)
		);
	}
	
	public static void encode(ByteBuf yeahWhatever, C2SKeysmashConfigSyncLetter iWishIHadBiggerTuple) {
		PacketCodecs.BOOL.encode(yeahWhatever, iWishIHadBiggerTuple.useKeysmashing);
		PacketCodecs.BOOL.encode(yeahWhatever, iWishIHadBiggerTuple.readableSelf);
		PacketCodecs.BOOL.encode(yeahWhatever, iWishIHadBiggerTuple.readableOthers);
		PacketCodecs.STRING.encode(yeahWhatever, iWishIHadBiggerTuple.letterPoolOverride);
		PacketCodecs.FLOAT.encode(yeahWhatever, iWishIHadBiggerTuple.restockThreshold);
		PacketCodecs.BOOL.encode(yeahWhatever, iWishIHadBiggerTuple.useOrderedSpooling);
		PacketCodecs.FLOAT.encode(yeahWhatever, iWishIHadBiggerTuple.baseClarityChance);
		PacketCodecs.FLOAT.encode(yeahWhatever, iWishIHadBiggerTuple.startingClarityScore);
		PacketCodecs.FLOAT.encode(yeahWhatever, iWishIHadBiggerTuple.keysmashedMultiplier);
		PacketCodecs.FLOAT.encode(yeahWhatever, iWishIHadBiggerTuple.spokenLoudlyClarity);
		PacketCodecs.FLOAT.encode(yeahWhatever, iWishIHadBiggerTuple.nonletterClarity);
	}
}

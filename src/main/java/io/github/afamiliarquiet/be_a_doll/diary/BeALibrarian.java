package io.github.afamiliarquiet.be_a_doll.diary;

import io.github.afamiliarquiet.be_a_doll.BeADoll;
import io.github.afamiliarquiet.be_a_doll.BeAMaid;
import io.github.afamiliarquiet.be_a_doll.letters.C2SKeysmashConfigSyncLetter;
import io.github.afamiliarquiet.be_a_doll.letters.IntraLibraryMessageCacheLetter;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import symbolics.division.occmy.obv.OccEntities;

@SuppressWarnings("UnstableApiUsage")
public class BeALibrarian {
	// how does this librarian differ from the maid? this one handles the forbidden (experimental) knowledge!
	public static final AttachmentType<BeADoll.Variant> DOLL_VARIANT = AttachmentRegistry.create(
		BeADoll.id("doll_variant"),
		builder -> builder
			.initializer(() -> BeADoll.Variant.DEFAULT)
			.persistent(BeADoll.Variant.CODEC)
			.syncWith(BeADoll.Variant.PACKET_CODEC, AttachmentSyncPredicate.all())
	);

	public static final AttachmentType<Text> DOLL_NAME = AttachmentRegistry.create(
		BeADoll.id("doll_name"),
		builder -> builder
			.persistent(TextCodecs.CODEC)
			.syncWith(TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC, AttachmentSyncPredicate.all())
	);

	// yep. keeping the letter, envelope and all. no sync needed. certainly no persistence.
	public static final AttachmentType<C2SKeysmashConfigSyncLetter> KEYSMASH_CONFIG = AttachmentRegistry.create(
		BeADoll.id("keysmash_config"),
		builder -> builder
			.initializer(() -> C2SKeysmashConfigSyncLetter.DEFAULT)
	);

	public static final AttachmentType<IntraLibraryMessageCacheLetter> MESSAGE_CACHE = AttachmentRegistry.create(
		BeADoll.id("message_cache")
	);


	public static void lookForABook() {

	}


	/**
	 * finds the doll's variant from attachment, or gets default if none found.<br/>
	 * you should check {@link io.github.afamiliarquiet.be_a_doll.BeAMaid#isDoll(PlayerEntity)} as the authority
	 * on whether a PlayerEntity is a player or a doll. remember this, quiet. i made javadoc for you.
	 * @return the doll's variant, or the default doll type (which is NOT a normal player and is still a doll type)
	 */
	public static @NotNull BeADoll.Variant inspectDollMaterial(@NotNull PlayerEntity doll) {
		return doll.getAttachedOrCreate(DOLL_VARIANT);
	}

	// okay so it looks bad now that i'm not using the above one at all. it's interesting yeah
	// listen okay i need isDoll to be authoritative because it relies on the attributes
	// and i don't ever want a doll to lose their attributes
	// so if they lose their attributes then they must not be a doll anymore, ergo a doll has not lost their attributes
	// being lopsided is no good so this avoids that
	public static @NotNull BeADoll.Variant inspectSupposedPlayer(@NotNull PlayerEntity supposedPlayer) {
		return BeAMaid.isDoll(supposedPlayer) ? inspectDollMaterial(supposedPlayer) : BeADoll.Variant.REPRESSED;
	}

	// yeah we're just washing off the experimental api smell here
	public static void reshapeDoll(@NotNull PlayerEntity doll, @NotNull BeADoll.Variant variant) {
		doll.setAttached(DOLL_VARIANT, variant);

		// special compat treat for clockwork dolls
		if (FabricLoader.getInstance().isModLoaded("occmy")) {
			if (variant == BeADoll.Variant.CLOCKWORK) {
				doll.setAttached(OccEntities.ENJOINED, Unit.INSTANCE);
			} else {
				doll.removeAttached(OccEntities.ENJOINED);
			}
		}
	}

	public static @Nullable Text inspectDollLabel(@NotNull PlayerEntity doll) {
		return doll.getAttached(DOLL_NAME);
	}

	public static void relabelDoll(@NotNull PlayerEntity doll, @Nullable Text name) {
		doll.setAttached(DOLL_NAME, name);
	}

	public static void repress(@NotNull PlayerEntity player) {
		// clean up special compat treat
		if (FabricLoader.getInstance().isModLoaded("occmy")) {
			if (inspectDollMaterial(player) == BeADoll.Variant.CLOCKWORK) {
				player.removeAttached(OccEntities.ENJOINED);
			}
		}

		player.removeAttached(DOLL_VARIANT);
		player.removeAttached(DOLL_NAME);
	}

	public static void filePasswordManager(@NotNull PlayerEntity player, C2SKeysmashConfigSyncLetter letter) {
		player.setAttached(KEYSMASH_CONFIG, letter);
	}

	public static @NotNull C2SKeysmashConfigSyncLetter checkFilesForPasswordManager(@NotNull PlayerEntity player) {
		return player.getAttachedOrCreate(KEYSMASH_CONFIG);
	}

	public static void filePaperwork(@NotNull PlayerEntity player, IntraLibraryMessageCacheLetter letter) {
		player.setAttached(MESSAGE_CACHE, letter);
	}

	public static @Nullable IntraLibraryMessageCacheLetter checkDocuments(@NotNull PlayerEntity player) {
		return player.getAttached(MESSAGE_CACHE);
	}

	public static void shredDocuments(@NotNull PlayerEntity player) {
		player.removeAttached(MESSAGE_CACHE);
	}
}

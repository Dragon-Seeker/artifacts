package artifacts.neoforge.registry;

import artifacts.Artifacts;
import artifacts.component.SwimData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Artifacts.MOD_ID);

    public static final Supplier<AttachmentType<SwimData>> SWIM_DATA = ATTACHMENT_TYPES.register("swim_data", () -> AttachmentType.builder(SwimData::new).build());
}

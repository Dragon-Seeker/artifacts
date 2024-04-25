package artifacts.world.placement;

import artifacts.Artifacts;
import artifacts.registry.ModPlacementModifierTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;

public class CampsiteCountPlacement extends RepeatingPlacement {

    private static final CampsiteCountPlacement INSTANCE = new CampsiteCountPlacement();
    public static final Codec<CampsiteCountPlacement> CODEC = Codec.unit(() -> INSTANCE);

    private CampsiteCountPlacement() {

    }

    public static CampsiteCountPlacement campsiteCount() {
        return INSTANCE;
    }

    @Override
    protected int count(RandomSource p_226333_, BlockPos p_226334_) {
        return Artifacts.CONFIG.common.campsite.getCount();
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifierTypes.CAMPSITE_COUNT.get();
    }
}

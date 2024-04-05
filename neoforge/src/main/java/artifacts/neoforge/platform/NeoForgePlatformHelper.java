package artifacts.neoforge.platform;

import artifacts.component.SwimData;
import artifacts.neoforge.ArtifactsNeoForge;
import artifacts.platform.PlatformHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.TierSortingRegistry;
import org.jetbrains.annotations.Nullable;

public class NeoForgePlatformHelper implements PlatformHelper {

    @Override
    public Attribute getStepHeightAttribute() {
        return NeoForgeMod.STEP_HEIGHT.value();
    }

    @Override
    public boolean isCorrectTierForDrops(Tier tier, BlockState state) {
        return TierSortingRegistry.isCorrectTierForDrops(tier, state);
    }

    @Nullable
    @Override
    public SwimData getSwimData(LivingEntity livingEntity) {
        // noinspection ConstantConditions
        if(!(livingEntity instanceof Player)) return null;
        return livingEntity.getData(ArtifactsNeoForge.SWIM_DATA_ATTACHMENT);
    }

    @Override
    public boolean isEyeInWater(Player player) {
        return player.isEyeInFluidType(NeoForgeMod.WATER_TYPE.value());
    }

}

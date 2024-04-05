package artifacts.fabric.platform;

import artifacts.component.SwimData;
import artifacts.fabric.ArtifactsFabric;
import artifacts.platform.PlatformHelper;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FabricPlatformHelper implements PlatformHelper {

    @Override
    public Attribute getStepHeightAttribute() {
        return StepHeightEntityAttributeMain.STEP_HEIGHT; // TODO 1.20.5
    }

    @Override
    public boolean isCorrectTierForDrops(Tier tier, BlockState state) {
        int i = tier.getLevel();
        if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return i >= 3;
        } else if (state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return i >= 2;
        } else if (state.is(BlockTags.NEEDS_STONE_TOOL)) {
            return i >= 1;
        }
        return true;
    }

    @Nullable
    @Override
    public SwimData getSwimData(LivingEntity livingEntity) {
        if(!(livingEntity instanceof Player)) return null;

        return livingEntity.getAttachedOrCreate(ArtifactsFabric.SWIM_DATA);
    }

    @Override
    public boolean isEyeInWater(Player player) {
        return player.isEyeInFluid(FluidTags.WATER);
    }
}

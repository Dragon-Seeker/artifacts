package artifacts.platform;

import artifacts.component.SwimData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface PlatformHelper {

    Attribute getStepHeightAttribute();

    boolean isCorrectTierForDrops(Tier tier, BlockState state);

    @Nullable
    SwimData getSwimData(LivingEntity player);

    boolean isEyeInWater(Player player);
}

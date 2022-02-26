package artifacts.common.item.curio.hands;

import artifacts.common.config.ModConfig;
import artifacts.common.item.curio.CurioItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.List;

public class DiggingClawsItem extends CurioItem {

    public DiggingClawsItem() {
        addListener(EventPriority.LOW, PlayerEvent.BreakSpeed.class, this::onBreakSpeed);
        addListener(PlayerEvent.HarvestCheck.class, this::onHarvestCheck);
        addListener(EventPriority.LOWEST, BlockEvent.BreakEvent.class, this::onBreakBlock, BlockEvent.BreakEvent::getPlayer);
    }

    private boolean canHarvest(BlockState state) {
        List<String> toolTypes = ModConfig.server.diggingClaws.toolTypes.get();
        int diggingClawsHarvestLevel = ModConfig.server.diggingClaws.harvestLevel.get() - 1;
        return state.getHarvestLevel() <= diggingClawsHarvestLevel &&
                (state.getHarvestTool() == null
                        || toolTypes.contains(state.getHarvestTool().getName())
                        || toolTypes.contains("*"));
    }

    private void onBreakSpeed(PlayerEvent.BreakSpeed event, LivingEntity wearer) {
        if (canHarvest(event.getState())) {
            event.setNewSpeed((float) (event.getNewSpeed() + ModConfig.server.diggingClaws.miningSpeedBonus.get()));
        }
    }

    private void onHarvestCheck(PlayerEvent.HarvestCheck event, LivingEntity wearer) {
        if (!event.canHarvest()) {
            int diggingClawsHarvestLevel = ModConfig.server.diggingClaws.harvestLevel.get() - 1;
            event.setCanHarvest(event.getTargetBlock().getHarvestLevel() <= diggingClawsHarvestLevel);
        }
    }

    private void onBreakBlock(BlockEvent.BreakEvent event, LivingEntity wearer) {
        damageEquippedStacks(wearer);
    }

    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}

package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {

    public ItemModels(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Artifacts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BuiltInRegistries.ITEM.stream()
                .filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(Artifacts.MOD_ID))
                .filter(item -> item != ModItems.MIMIC_SPAWN_EGG.get())
                .filter(item -> item != ModItems.UMBRELLA.get())
                .forEach(this::addGeneratedModel);
    }

    private void addGeneratedModel(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        withExistingParent("item/" + name, "item/generated").texture("layer0", Artifacts.id("item/%s", name));
    }
}

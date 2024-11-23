package growthcraft.lib.client;

import growthcraft.lib.item.GrowthcraftItem;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class GrowthcraftItemColor implements ItemColor {

    public int getColor(ItemStack itemStack, int layer) {

        if (itemStack.getItem() instanceof GrowthcraftItem growthcraftItem) {
            return growthcraftItem.getColor(itemStack, layer);
        }
        return 0;
    }
}

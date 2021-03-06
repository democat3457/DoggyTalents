package doggytalents.handler;

import com.mojang.blaze3d.systems.RenderSystem;

import doggytalents.entity.EntityDog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;

/**
 * @author ProPercivalalb
 **/
public class GameOverlay {

    private static Minecraft mc = Minecraft.getInstance();

    public static void onPreRenderGameOverlay(final RenderGameOverlayEvent.Post event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT && mc.player != null && mc.player.getRidingEntity() instanceof EntityDog) {
            EntityDog dog = (EntityDog)mc.player.getRidingEntity();
            int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
            int height = Minecraft.getInstance().getMainWindow().getScaledHeight();
            RenderSystem.pushMatrix();
            mc.getTextureManager().bindTexture(Screen.GUI_ICONS_LOCATION);

            RenderSystem.enableBlend();
            int left = width / 2 + 91;
            int top = height - ForgeIngameGui.right_height;
            ForgeIngameGui.right_height += 10;
            int level = MathHelper.ceil(((double)dog.getDogHunger() / (double) dog.getHungerFeature().getMaxHunger()) * 20.0D);

            for (int i = 0; i < 10; ++i) {
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                int y = top;
                int icon = 16;
                byte backgound = 12;

                mc.ingameGUI.blit(x, y, 16 + backgound * 9, 27, 9, 9);


                if (idx < level)
                    mc.ingameGUI.blit(x, y, icon + 36, 27, 9, 9);
                else if (idx == level)
                    mc.ingameGUI.blit(x, y, icon + 45, 27, 9, 9);
            }
            RenderSystem.disableBlend();

            RenderSystem.enableBlend();
            left = width / 2 + 91;
            top = height - ForgeIngameGui.right_height;
            RenderSystem.color4f(1.0F, 1.0F, 0.0F, 1.0F);
            int l6 = dog.getAir();
            int j7 = dog.getMaxAir();

            if(dog.areEyesInFluid(FluidTags.WATER) || l6 < j7) {
                int air = dog.getAir();
                int full = MathHelper.ceil((air - 2) * 10.0D / 300.0D);
                int partial = MathHelper.ceil(air * 10.0D / 300.0D) - full;

                for (int i = 0; i < full + partial; ++i) {
                    mc.ingameGUI.blit(left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
                }
                ForgeIngameGui.right_height += 10;
            }
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();

            RenderSystem.popMatrix();
        }
    }
}
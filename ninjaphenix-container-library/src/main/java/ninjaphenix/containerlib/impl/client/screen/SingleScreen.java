package ninjaphenix.containerlib.impl.client.screen;


import net.fabricmc.loader.api.FabricLoader;
import ninjaphenix.containerlib.api.screen.SingleScreenMeta;
import ninjaphenix.containerlib.api.client.screen.AbstractScreen;
import ninjaphenix.containerlib.api.client.screen.widget.ScreenTypeSelectionScreenButton;
import ninjaphenix.containerlib.impl.inventory.SingleContainer;

public class SingleScreen<T extends SingleContainer> extends AbstractScreen<T, SingleScreenMeta>
{
    private Rectangle blankArea = null;
    private ScreenTypeSelectionScreenButton screenSelectButton;

    public SingleScreen(T container)
    {
        super(container, (screenMeta) -> (screenMeta.WIDTH * 18 + 14) / 2 - 80);
        containerWidth = 14 + 18 * SCREEN_META.WIDTH;
        containerHeight = 17 + 97 + 18 * SCREEN_META.HEIGHT;
    }

    @Override
    protected void init()
    {
        super.init();
        int settingsXOffset = -19;
        if (FabricLoader.getInstance().isModLoaded("inventorysorter")) { settingsXOffset -= 18; }
        screenSelectButton = addButton(new ScreenTypeSelectionScreenButton(x + containerWidth + settingsXOffset, y + 4));
        final int blanked = SCREEN_META.BLANK_SLOTS;
        if (blanked > 0)
        {
            final int xOffset = 7 + (SCREEN_META.WIDTH - blanked) * 18;
            blankArea = new Rectangle(x + xOffset, y + containerHeight - 115, blanked * 18, 18,
                    xOffset, containerHeight, SCREEN_META.TEXTURE_WIDTH, SCREEN_META.TEXTURE_HEIGHT);
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta)
    {
        super.render(mouseX, mouseY, delta);
        screenSelectButton.renderTooltip(mouseX, mouseY, this::renderTooltip);
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY)
    {
        super.drawBackground(delta, mouseX, mouseY);
        if (blankArea != null) { blankArea.render(); }
    }
}



package com.neponies.mixin.client;

import com.minelittlepony.client.GuiPonySettings;
import com.minelittlepony.common.client.gui.ScrollContainer;
import com.minelittlepony.common.client.gui.Tooltip;
import com.minelittlepony.common.client.gui.dimension.IBounded;
import com.minelittlepony.common.client.gui.element.Scrollbar;
import com.minelittlepony.common.client.gui.element.Slider;
import com.minelittlepony.common.util.settings.Grouping;
import com.minelittlepony.common.util.settings.Setting;
import com.neponies.NEPoniesConfig;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.StreamSupport;

import static com.neponies.Constants.MAX_LABEL_RENDER_DISTANCE;
import static com.neponies.Constants.MIN_LABEL_RENDER_DISTANCE;

@Mixin(value = GuiPonySettings.class, remap = false)
public abstract class GuiPonySettingsMixin {

    private static final String NAME_DISTANCE_KEY = "minelp.options.ponycustomnamerenderdistance";
    private static final String PROFESSION_DISTANCE_KEY = "minelp.options.professionrenderdistance";

    @Shadow(remap = false)
    @Final
    private ScrollContainer content;

    @Redirect(
            method = "rebuildContent",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/minelittlepony/common/util/settings/Grouping;entries()Ljava/lang/Iterable;",
                    ordinal = 0
            )
    )
    private Iterable<Setting<?>> filterNumericSettingsFromToggleList(Grouping grouping) {
        return StreamSupport.stream(grouping.entries().spliterator(), false)
                .filter(setting -> setting.getType().rawType() == Boolean.class)
                .toList();
    }

    @Inject(method = "rebuildContent", at = @At("RETURN"), remap = false)
    private void addRenderDistanceSliders(CallbackInfo ci) {
        int contentWidth = content.getBounds().width;
        int left = contentWidth / 2 - 210;
        int right = contentWidth / 2 + 10;

        if (left < 0) {
            left = contentWidth / 2 - 100;
            right = left;
        }

        boolean splitColumns = right != left;
        int sliderTop = findLowestBottom(splitColumns) + 20;

        content.addButton(new Slider(left, sliderTop, MIN_LABEL_RENDER_DISTANCE, MAX_LABEL_RENDER_DISTANCE, NEPoniesConfig.getPonyCustomNameRenderDistance())
                .onChange(value -> (float) NEPoniesConfig.setPonyCustomNameRenderDistance(Math.round(value)))
                .setTextFormat(sender -> Text.translatable(NAME_DISTANCE_KEY, Math.round(sender.getValue())))
                .setTooltipFormat(sender -> Tooltip.of(NAME_DISTANCE_KEY + ".tooltip", 200)));

        content.addButton(new Slider(left, sliderTop + 20, MIN_LABEL_RENDER_DISTANCE, MAX_LABEL_RENDER_DISTANCE, NEPoniesConfig.getProfessionRenderDistance())
                .onChange(value -> (float) NEPoniesConfig.setProfessionRenderDistance(Math.round(value)))
                .setTextFormat(sender -> Text.translatable(PROFESSION_DISTANCE_KEY, Math.round(sender.getValue())))
                .setTooltipFormat(sender -> Tooltip.of(PROFESSION_DISTANCE_KEY + ".tooltip", 200)));
    }

    private int findLowestBottom(boolean splitColumns) {
        int middle = content.getBounds().width / 2;

        return content.getChildElements().stream()
                .filter(element -> !(element instanceof Scrollbar))
                .filter(IBounded.class::isInstance)
                .map(IBounded.class::cast)
                .mapToInt(bounded -> {
                    var bounds = bounded.getBounds();
                    if (splitColumns && bounds.left >= middle) {
                        return Integer.MIN_VALUE;
                    }
                    return bounds.bottom();
                })
                .max()
                .orElse(0);
    }
}

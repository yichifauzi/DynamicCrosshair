package mod.crend.dynamiccrosshair.config;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.render.CrosshairModifierRenderer;
import mod.crend.dynamiccrosshair.render.CrosshairStyleRenderer;
import mod.crend.libbamboo.render.ItemOrTagRenderer;
import mod.crend.libbamboo.type.BlockOrTag;
import mod.crend.libbamboo.type.ItemOrTag;
import mod.crend.libbamboo.auto.annotation.*;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

@AutoYaclConfig(modid= DynamicCrosshair.MOD_ID, translationKey = "dynamiccrosshair.title", filename = "dynamiccrosshair.json5")
public class Config {
    @SerialEntry
    public boolean dynamicCrosshair = true;
    @SerialEntry
    public boolean disableDebugCrosshair = false;
    @SerialEntry
    public boolean thirdPersonCrosshair = false;
    @SerialEntry
    public boolean hideWithScreen = true;
    @SerialEntry
    public boolean hideWithMap = true;
    @SerialEntry
    public boolean fixCenteredCrosshair = false;
    @SerialEntry
    public CrosshairConfig crosshairConfig = new CrosshairConfig();

    public static class CrosshairConfig {
        @SerialEntry
        public boolean onBlock = true;
        @SerialEntry
        public boolean onInteractableBlock = true;
        @SerialEntry
        public boolean onEntity = true;
        @SerialEntry
        public CrosshairPolicy holdingTool = CrosshairPolicy.Always;
        @SerialEntry
        public boolean displayCorrectTool = true;
        @SerialEntry
        public boolean holdingMeleeWeapon = true;
        @SerialEntry
        public boolean meleeWeaponOnEntity = false;
        @SerialEntry
        public boolean meleeWeaponOnBreakableBlock = false;
        @SerialEntry
        public UsableCrosshairPolicy holdingRangedWeapon = UsableCrosshairPolicy.IfInteractable;
        @SerialEntry
        public UsableCrosshairPolicy holdingThrowable = UsableCrosshairPolicy.IfInteractable;
        @SerialEntry
        public boolean holdingShield = true;
        @SerialEntry
        public BlockCrosshairPolicy holdingBlock = BlockCrosshairPolicy.IfInteractable;
        @SerialEntry
        public UsableCrosshairPolicy holdingUsableItem = UsableCrosshairPolicy.IfInteractable;
        @SerialEntry
        public boolean forceHoldingSpyglass = false;
    }

    public static class CrosshairColorReader implements EnableIf.Predicate {
        @Override
        public boolean test(Object color) {
            return color == CrosshairConfigColor.Custom;
        }
    }
    public static class CrosshairColorSettings {
        @SerialEntry
        @Translation(key="dynamiccrosshair.option.crosshairStyle.color.crosshairColor")
        public CrosshairConfigColor crosshairColor = CrosshairConfigColor.Unchanged;
        @SerialEntry
        @Translation(key="dynamiccrosshair.option.crosshairStyle.color.customColor")
        @EnableIf(field="crosshairColor", value=CrosshairColorReader.class)
        public Color customColor = new Color(0xFFAABBCC, true);
        @SerialEntry
        @Translation(key="dynamiccrosshair.option.crosshairStyle.color.forceColor")
        public boolean forceColor = false;
    }
    public static class CrosshairStyleSettings {
        @SerialEntry
        @Translation(key="dynamiccrosshair.option.crosshairStyle.style")
        @Decorate(decorator = CrosshairStyleRenderer.class)
        public CrosshairConfigStyle style = CrosshairConfigStyle.Cross;
        @SerialEntry
        @TransitiveObject
        public CrosshairColorSettings color = new CrosshairColorSettings();
    }
    public static class CrosshairModifierSettings {
        @SerialEntry
        @Translation(key="dynamiccrosshair.option.crosshairStyle.style")
        @Decorate(decorator = CrosshairModifierRenderer.class)
        public CrosshairConfigModifier style;
        @SerialEntry
        @TransitiveObject
        public CrosshairColorSettings color = new CrosshairColorSettings();
        @SerialEntry
        @Translation(key="dynamiccrosshair.option.crosshairStyle.isModifier")
        public boolean isModifier = true;
    }

    @SerialEntry
    @TransitiveObject
    @Category(name = "style")
    public CrosshairColorSettings color = new CrosshairColorSettings();

    @SerialEntry
    public boolean dynamicCrosshairStyle = true;
    @SerialEntry
    @TransitiveObject
    @Category(name = "style")
    public CrosshairStyles crosshairStyle = new CrosshairStyles();
    @SerialEntry
    @Category(name = "style")
    @TransitiveObject
    public CrosshairModifiers crosshairModifiers = new CrosshairModifiers();
    public static class CrosshairStyles {
        @SerialEntry
        public CrosshairStyleSettings regular = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings onBlock = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings onEntity = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings holdingTool = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings holdingMeleeWeapon = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings holdingRangedWeapon = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings holdingThrowable = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings holdingBlock = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings interact = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings useItem = new CrosshairStyleSettings();
        @SerialEntry
        public CrosshairStyleSettings shield = new CrosshairStyleSettings();

        public CrosshairStyles() {
            regular.style = CrosshairConfigStyle.Cross;
            onBlock.style = CrosshairConfigStyle.Cross;
            onEntity.style = CrosshairConfigStyle.DiagonalCross;
            holdingTool.style = CrosshairConfigStyle.Square;
            holdingMeleeWeapon.style = CrosshairConfigStyle.Cross;
            holdingRangedWeapon.style = CrosshairConfigStyle.DiagonalCross;
            holdingThrowable.style = CrosshairConfigStyle.Circle;
            holdingBlock.style = CrosshairConfigStyle.Diamond;
            interact.style = CrosshairConfigStyle.Brackets;
            useItem.style = CrosshairConfigStyle.RoundBrackets;
            shield.style = CrosshairConfigStyle.BracketsBottom;
        }
    }
    public static class CrosshairModifiers {
        @SerialEntry
        public CrosshairModifierSettings modCorrectTool = new CrosshairModifierSettings();
        @SerialEntry
        public CrosshairModifierSettings modIncorrectTool = new CrosshairModifierSettings();

        public CrosshairModifiers() {
            modCorrectTool.style = CrosshairConfigModifier.Dot;
            modIncorrectTool.style = CrosshairConfigModifier.DiagonalCross;
        }
    }

    @SerialEntry
    @Category(name="tweaks")
    public boolean enableTweaks = true;

    @SerialEntry
    @Category(name="tweaks")
    @DescriptionImage(ItemOrTagRenderer.OfItemOrTag.class)
    @EnableIf(field = "enableTweaks", value = EnableIf.BooleanPredicate.class)
    public List<ItemOrTag> additionalTools = Collections.emptyList();
    @SerialEntry
    @Category(name="tweaks")
    @DescriptionImage(ItemOrTagRenderer.OfItemOrTag.class)
    @EnableIf(field = "enableTweaks", value = EnableIf.BooleanPredicate.class)
    public List<ItemOrTag> additionalMeleeWeapons = Collections.emptyList();
    @SerialEntry
    @Category(name="tweaks")
    @DescriptionImage(ItemOrTagRenderer.OfItemOrTag.class)
    @EnableIf(field = "enableTweaks", value = EnableIf.BooleanPredicate.class)
    public List<ItemOrTag> additionalRangedWeapons = Collections.emptyList();
    @SerialEntry
    @Category(name="tweaks")
    @DescriptionImage(ItemOrTagRenderer.OfItemOrTag.class)
    @EnableIf(field = "enableTweaks", value = EnableIf.BooleanPredicate.class)
    public List<ItemOrTag> additionalThrowables = Collections.emptyList();
    @SerialEntry
    @Category(name="tweaks")
    @DescriptionImage(ItemOrTagRenderer.OfItemOrTag.class)
    @EnableIf(field = "enableTweaks", value = EnableIf.BooleanPredicate.class)
    public List<ItemOrTag> additionalUsableItems = Collections.emptyList();

    @SerialEntry
    @Category(name="tweaks")
    @DescriptionImage(ItemOrTagRenderer.OfBlockOrTag.class)
    @EnableIf(field = "enableTweaks", value = EnableIf.BooleanPredicate.class)
    public List<BlockOrTag> additionalInteractableBlocks = Collections.emptyList();

}

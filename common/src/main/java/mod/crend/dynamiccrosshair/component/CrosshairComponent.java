package mod.crend.dynamiccrosshair.component;

import mod.crend.dynamiccrosshair.DynamicCrosshairMod;
import mod.crend.dynamiccrosshair.api.Crosshair;
import mod.crend.dynamiccrosshair.api.InteractionType;
import mod.crend.dynamiccrosshair.config.CrosshairModifier;
import mod.crend.dynamiccrosshair.config.CrosshairStyle;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CrosshairComponent {

    public static final CrosshairComponent FORCE_CROSSHAIR = new CrosshairComponent(new Crosshair(InteractionType.FORCE_REGULAR_CROSSHAIR));
    public static final CrosshairStyle FORCE_CROSSHAIR_STYLE = FORCE_CROSSHAIR.getPrimaryStyle().get();

    CrosshairVariant primary = CrosshairVariant.NONE;
    CrosshairVariant secondary = CrosshairVariant.NONE;
    ModifierHit modifierHit = ModifierHit.NONE;

    public CrosshairComponent(Crosshair crosshair) {
        switch (crosshair.primaryInteraction()) {
            case TARGET_BLOCK -> primary = CrosshairVariant.OnBlock;
            case TARGET_ENTITY -> primary = CrosshairVariant.OnEntity;
            case MELEE_WEAPON -> primary = CrosshairVariant.HoldingMeleeWeapon;
            case TOOL -> primary = CrosshairVariant.HoldingTool;
            case CORRECT_TOOL -> { primary = CrosshairVariant.HoldingTool; modifierHit = ModifierHit.CORRECT_TOOL; }
            case INCORRECT_TOOL -> { primary = CrosshairVariant.HoldingTool; modifierHit = ModifierHit.INCORRECT_TOOL; }
            case FORCE_REGULAR_CROSSHAIR -> primary = CrosshairVariant.Regular;
        }
        switch (crosshair.secondaryInteraction()) {
            case USE_ITEM,
                 EQUIP_ITEM,
                 CONSUME_ITEM,
                 CHARGE_ITEM,
                 USE_ITEM_ON_BLOCK,
                 PLACE_ITEM_ON_BLOCK,
                 FILL_ITEM_FROM_BLOCK,
                 FILL_BLOCK_FROM_ITEM,
                 USE_BLOCK,
                 USE_ITEM_ON_ENTITY,
                 PICK_UP_ENTITY,
                 PLACE_ITEM_ON_ENTITY,
                 FILL_ITEM_FROM_ENTITY,
                 FILL_ENTITY_FROM_ITEM
                    -> secondary = CrosshairVariant.HoldingUsableItem;
            case SPYGLASS -> {
                if (DynamicCrosshairMod.config.dynamicCrosshairForceHoldingSpyglass()) {
                    primary = CrosshairVariant.Regular;
                } else {
                    secondary = CrosshairVariant.HoldingUsableItem;
                }
            }
            case THROW_ITEM
                    -> secondary = CrosshairVariant.HoldingThrowable;

            case PLACE_BLOCK,
                 PLACE_ENTITY
                    -> secondary = CrosshairVariant.HoldingBlock;

            case INTERACT_WITH_BLOCK,
                 TAKE_ITEM_FROM_BLOCK,
                 MOUNT_BLOCK,
                 TAKE_ITEM_FROM_ENTITY,
                 INTERACT_WITH_ENTITY,
                 MOUNT_ENTITY
                    -> secondary = CrosshairVariant.CanInteract;

            case RANGED_WEAPON, RANGED_WEAPON_CHARGING -> primary = CrosshairVariant.Regular;
            case RANGED_WEAPON_CHARGED -> primary = CrosshairVariant.HoldingRangedWeapon;
            case USABLE_TOOL -> { primary = CrosshairVariant.HoldingTool; secondary = CrosshairVariant.HoldingUsableItem; }
            case SHIELD -> secondary = CrosshairVariant.HoldingShield;

            case FORCE_REGULAR_CROSSHAIR -> modifierHit = ModifierHit.NONE;
        }
    }

    public Optional<CrosshairStyle> getPrimaryStyle() {
        return getCrosshairStyle(primary);
    }

    public Optional<CrosshairStyle> getSecondaryStyle() {
        return getCrosshairStyle(secondary);
    }

    public boolean secondaryStyleIsModifier() {
        // FIXME this should not be hardcoded
        return secondary == CrosshairVariant.NONE
                || secondary == CrosshairVariant.HoldingUsableItem
                || secondary == CrosshairVariant.HoldingShield
                || secondary == CrosshairVariant.HoldingRangedWeapon
                || secondary == CrosshairVariant.CanInteract;
    }

    private Optional<CrosshairStyle> getCrosshairStyle(CrosshairVariant variant) {
        if (variant == CrosshairVariant.NONE) return Optional.empty();
        if (variant == CrosshairVariant.OnBlock && !secondaryStyleIsModifier()) return Optional.empty();
        return Optional.of(switch (variant) {
            case Regular -> DynamicCrosshairMod.config.getCrosshairStyleRegular();
            case OnBlock -> DynamicCrosshairMod.config.getCrosshairStyleOnBlock();
            case OnEntity -> DynamicCrosshairMod.config.getCrosshairStyleOnEntity();
            case HoldingBlock -> DynamicCrosshairMod.config.getCrosshairStyleHoldingBlock();
            case HoldingTool -> DynamicCrosshairMod.config.getCrosshairStyleHoldingTool();
            case HoldingMeleeWeapon -> DynamicCrosshairMod.config.getCrosshairStyleHoldingMeleeWeapon();
            case HoldingRangedWeapon -> DynamicCrosshairMod.config.getCrosshairStyleHoldingRangedWeapon();
            case HoldingThrowable -> DynamicCrosshairMod.config.getCrosshairStyleHoldingThrowable();
            case HoldingUsableItem -> DynamicCrosshairMod.config.getCrosshairStyleUsableItem();
            case HoldingShield -> DynamicCrosshairMod.config.getCrosshairStyleShield();
            case CanInteract -> DynamicCrosshairMod.config.getCrosshairStyleInteractable();
			default -> throw new IllegalStateException("Unexpected value: " + variant);
		});
    }

    public List<CrosshairModifier> getModifiers() {
        List<CrosshairModifier> modifiers = new LinkedList<>();
        if (DynamicCrosshairMod.config.dynamicCrosshairDisplayCorrectTool()) {
            switch (modifierHit) {
                case CORRECT_TOOL -> modifiers.add(DynamicCrosshairMod.config.getCrosshairModifierCorrectTool());
                case INCORRECT_TOOL -> modifiers.add(DynamicCrosshairMod.config.getCrosshairModifierIncorrectTool());
            }
        }
        return modifiers;
    }
}

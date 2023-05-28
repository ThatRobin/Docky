package io.github.thatrobin.docky.utils;

import io.github.thatrobin.docky.providers.DockyPageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.nio.file.Path;
import java.util.Map;

@SuppressWarnings("unused")
public class DataTypeLoader {

    public static void registerApoliDataTypes() {
        DataTypeRegistry.register("array", gen_array());
        DataTypeRegistry.register("attribute_modifier", gen_attribute_modifier());
        DataTypeRegistry.register("modifier_operation", gen_attribute_modifier_operation());
        DataTypeRegistry.register("attributed_attribute_modifier", gen_attributed_attribute_modifier());
        DataTypeRegistry.register("attributed_modifier_operation", gen_attributed_attribute_modifier_operation());
        DataTypeRegistry.register("boolean", gen_boolean());
        DataTypeRegistry.register("comparison", gen_comparison());
        DataTypeRegistry.register("recipe", gen_recipe());
        DataTypeRegistry.register("damage_source", gen_damage_source());
        DataTypeRegistry.register("float", gen_float());
        DataTypeRegistry.register("hud_render", gen_hud_render());
        DataTypeRegistry.register("identifier", gen_identifier());
        DataTypeRegistry.register("ingredient", gen_ingredient());
        DataTypeRegistry.register("int", gen_int());
        DataTypeRegistry.register("item_slot", gen_item_slot());
        DataTypeRegistry.register("item_stack", gen_item_stack());
        DataTypeRegistry.register("key", gen_key());
        DataTypeRegistry.register("object", gen_object());
        DataTypeRegistry.register("particle_effect", gen_particle_effect());
        DataTypeRegistry.register("positioned_item_stack", gen_positioned_item_stack());
        DataTypeRegistry.register("status_effect_instance", gen_status_effect());
        DataTypeRegistry.register("stat", gen_stat());
        DataTypeRegistry.register("string", gen_string());
        DataTypeRegistry.register("text", gen_text_component());
        DataTypeRegistry.register("vector", gen_vector());
    }

    public static void provideApoliDataTypes(FabricDataGenerator.Pack pack, Path outputPath) {
        for (Map.Entry<String, PageBuilder> entry : DataTypeRegistry.entries()) {
            pack.addProvider(((output, future) -> new DockyPageProvider(output, outputPath, entry.getKey(), "docs/data_types", entry.getValue())));
        }
    }

    public static void provideApoliDataTypes(FabricDataGenerator.Pack pack, Path outputPath, String location) {
        for (Map.Entry<String, PageBuilder> entry : DataTypeRegistry.entries()) {
            pack.addProvider(((output, future) -> new DockyPageProvider(output, outputPath, entry.getKey(), location, entry.getValue())));
        }
    }

    public static PageBuilder gen_array() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Array")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("Arrays are lists of other existing data types. They are enclosed by square brackets and each element is separated from the next by a comma.");

        return pageBuilder;
    }

    public static PageBuilder gen_attribute_modifier() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Attribute Modifier")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) used to specify how a value should be modified.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`operation`", "[Attribute Modifier Operation](attribute_modifier_operation.md)", " ", "The operation which will be performed by this modifier.")
            .addRow("`value`", "[Float](float.md)", " ", "The value to use for the modifier operation.")
            .addRow("`resource`", "[Identifier](../data_types/identifier.md)", "_optional_", "If specified, the value of this power will be used instead of the value specified in the `value` field.")
            .addRow("`name`", "[String](string.md)", "_optional_", "A descriptive name for the modifier, describing where it comes from.")
            .addRow("`modifier`", "[Attribute Modifier](attribute_modifier.md)", "_optional_", "If specified, this modifier will be applied to the value of the modifier.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_attribute_modifier_operation() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Modifier Operation")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A [String](string.md) used to specify the operation used by [Attribute Modifiers](attribute_modifier.md).")
            .addNote("The listed values are ordered based on the order of priority; with `add_base_early` being applied before the other modifiers and `set_total` being applied last.");

        pageBuilder.addTitle3("Values");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Value", "Description")
            .addBreak()
            .addRow("`add_base_early`", "Adds (or subtracts) the modifier value to the base value. (`NewBaseValue = BaseValue + ModifierValue`)")
            .addRow("`multiply_base_additive`", "Adds (or subtracts) the base value multiplied by the modifier value to the current base value. (`NewBaseValue = BaseValue + (BaseValue * ModifierValue)`)")
            .addRow("`multiply_base_multiplicative`", "Multiplies the current base value by the modifier value. (`NewBaseValue = BaseValue * (1 + ModifierValue)`)")
            .addRow("`add_base_late`", "Adds (or subtracts) the modifier value to the base value. (`NewBaseValue = BaseValue + ModifierValue`)")
            .addRow("`min_base`", "Uses the modifier value as the minimum value for the base value using Java's built-in [`Math#max`](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#max-double-double-) method. (`NewBaseValue = Math.max(BaseValue, ModifierValue)`)")
            .addRow("`max_base`", "Uses the modifier value as the maximum value for the base value using Java's built-in [`Math#min`](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#min-double-double-) method. (`NewBaseValue = Math.min(BaseValue, ModifierValue)`)")
            .addRow("`set_base`", "Sets the modifier value as the base value. (`NewBaseValue = ModifierValue`)")
            .addRow("`multiply_total_additive`", "Multiplies the total value by the current total value multiplied by the modifier value. (`NewTotalValue = TotalValue * (TotalValue * ModifierValue)`)")
            .addRow("`multiply_total_multiplicative`", "Multiplies the total value by the modifier value + 1. (`NewTotalValue = TotalValue * (1 + ModifierValue)`)")
            .addRow("`min_total`", "Uses the modifier value as the minimum value for the total value using Java's built-in [`Math#max`](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#max-double-double-) method. (`NewTotalValue = Math.max(TotalValue, ModifierValue)`)")
            .addRow("`max_total`", "Uses the modifier value as the maximum value for the total value using Java's built-in [`Math#min`](https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#min-double-double-) method. (`NewTotalValue = Math.min(TotalValue, ModifierValue)`)")
            .addRow("`set_total`", "Sets the modifier value as the total value. (`NewTotalValue = ModifierValue`)");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_attributed_attribute_modifier() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Attributed Attribute Modifier")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) used to specify how a specific attribute should be modified. Basically an [Attribute Modifier](attribute_modifier.md) with an additional `attribute` field.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`attribute`", "[Identifier](identifier.md)", " ", "ID of the attribute which will be modified by this modifier.")
            .addRow("`operation`", "[Attributed Attribute Modifier Operation](attributed_attribute_modifier_operation.md)", " ", "The operation which will be performed by this modifier.")
            .addRow("`value`", "[Float](float.md)", " ", "The value to use for the modifier operation.")
            .addRow("`name`", "[String](string.md)", "_optional_", "A descriptive name for the modifier, describing where it comes from.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_attributed_attribute_modifier_operation() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Attributed Attribute Modifier Operation")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A [string](string.md) used to specify the operation used by [Attributed Attribute Modifiers](attributed_attribute_modifier.md).")
            .addNote("The listed values are ordered based on the order of priority; with `addition` being applied before `multiply_base` and `multiply_total` being applied last.");

        pageBuilder.addTitle3("Values");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Value", "Description")
            .addBreak()
            .addRow("`addition`", "Adds (or subtracts) the modifier value to the base value. (`NewValue = BaseValue + ModifierValue`)")
            .addRow("`multiply_base`", "Adds (or subtracts) the base value multiplied by the modifier value to the base value. (`NewValue = BaseValue + (BaseValue * ModifierValue)`)")
            .addRow("`multiply_total`", "Multiplies the total value by the modifier value + 1. (`NewTotalValue = TotalValue * (1 + ModifierValue)`)");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_boolean() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Boolean")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A data type of which the value can be either `true` or `false`.");
        return pageBuilder;
    }

    public static PageBuilder gen_comparison() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Comparison")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A [String](string.md) which specifies how two numbers should be compared. Usually the first number is provided by whatever condition you are in, and the second is specified in an accompanying `compare_to` field.");

        pageBuilder.addTitle3("Values");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Value", "Description")
            .addBreak()
            .addRow("`<`", "Checks if the first number is **less than** the second number.")
            .addRow("`<=`", "Checks if the first number is **less than or equal to** the second number.")
            .addRow("`>`", "Checks if the first number is **greater than** the second number.")
            .addRow("`>=`", "Checks if the first number is **greater than or equal to** the second number.")
            .addRow("`==`", "Checks if the first number is **equal to** the second number.")
            .addRow("`!=`", "Checks if the first number is **not equal to** the second number.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_recipe() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Crafting Recipe")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) specifying a shapeless or shaped crafting recipe. For some more information, view [the page on recipes on the MC wiki](https://minecraft.gamepedia.com/Recipe).");

        pageBuilder.addTitle3("Fields (both types)");

        PageBuilder.TableBuilder tableBuilderBothTypes = PageBuilder.TableBuilder.init();
        tableBuilderBothTypes.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`type`", "[Identifier](identifier.md)", "", "The type of recipe. Either `minecraft:crafting_shaped` or `minecraft:crafting_shapeless`. Other recipe types are not supported.")
            .addRow("`id`", "[Identifier](identifier.md)", "", "An ID for this recipe. Has to be unique among all recipes, otherwise there will be a conflict.")
            .addRow("`result`", "[Object](object.md) with an `item` [ID](identifier.md) and `count` [Integer](integer.md)", "", "The result of the crafting. **Note that vanilla does _not_ support NBT tags in the result.**");
        pageBuilder.addTable(tableBuilderBothTypes);

        pageBuilder.addTitle3("Fields (shapeless)");

        PageBuilder.TableBuilder tableBuilderShapeless = PageBuilder.TableBuilder.init();
        tableBuilderShapeless.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`ingredients`", "[Array](array.md) of [Ingredient](ingredient.md)", "", "The items that need to be put in the crafting grid for the recipe.");
        pageBuilder.addTable(tableBuilderShapeless);

        pageBuilder.addTitle3("Fields (shaped)");

        PageBuilder.TableBuilder tableBuilderShaped = PageBuilder.TableBuilder.init();
        tableBuilderShaped.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`pattern`", "[Array](array.md) of [Strings](string.md)", "", "Specifies the pattern, with each element representing one row. Use a single character to describe one item. A space means that position is empty.")
            .addRow("`key`", "[Identifier](identifier.md)", "", "Specifies which character in the pattern corresponds to which [Ingredient](ingredient.md).");
        pageBuilder.addTable(tableBuilderShaped);
        return pageBuilder;
    }

    public static PageBuilder gen_damage_source() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Damage Source")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) used to specify how to deal damage to an entity.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`name`", "[String](string.md)", " ", "The name of the damage source. Controls death message as well as other interactions. Consider the [List of Damage Source Names](../../misc/extras/damage_source_names.md) when picking a name.")
            .addRow("`bypasses_armor`", "[Boolean](boolean.md)", "`false`", "When true, armor values are not taken into account when calculating the actual damage amount taken.")
            .addRow("`fire`", "[Boolean](boolean.md)", "`false`", "When true, the damage will be considered fire damage.")
            .addRow("`unblockable`", "[Boolean](boolean.md)", "`false`", "When true, the damage will be unblockable (not reduced by resistance effects or protection enchantments).")
            .addRow("`magic`", "[Boolean](boolean.md)", "`false`", "When true, the damage will be considered magic damage.")
            .addRow("`out_of_world`", "[Boolean](boolean.md)", "`false`", "When true, the damage will be considered \"out of world\" damage, i.e. damage from falling into the void.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_float() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Float")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A floating point (decimal) number, like `6.0`, `-1.5` or `0.1`.");
        return pageBuilder;
    }

    public static PageBuilder gen_hud_render() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Hud Render")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) used to define how a resource or cooldown bar should be rendered.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`should_render`", "[Boolean](boolean.md)", "`true`", "Whether the bar should be visible or not.")
            .addRow("`sprite_location`", "[Identifier](identifier.md)", "\"origins:textures/gui/resource_bar.png\"", "The path to the file in the assets which contains what the bar looks like. See the [List of sprites](../../misc/extras/sprites.md) for a list of files included by default in the mod.")
            .addRow("`bar_index`", "[Integer](integer.md)", "`0`", "The indexed position of the bar on the sprite to use. Please note that indexes start at 0.")
            .addRow("`condition`", "[Entity Condition Type](../entity_condition_types.md)", "_optional_", "If set (and `should_render` is true), the bar will only display when the entity with the power fulfills this condition.")
            .addRow("`inverted`", "[Boolean](boolean.md)", "`false`", "If set to true, inverts the way the hud render process (it'll look like its value is being decreased).");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_identifier() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Identifier")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A [String](string.md) which specifies an identifier used in Minecraft. They are used to refer to items, blocks, status effects, locations in data or resource packs, and a lot of other things.\n" +
                "\n" +
                "\n" +
                "An identifier consists of a namespace and a path. Namespace and path are separated with a colon. An identifier may only contain lower-case letters (a-z), digits (0-9), `-`, `_` and `.`. It can include at most a single colon (`:`) for separation. The path may contain `/` to indicate \"folders\", but the namespace may not.\n" +
                "\n" +
                "If no namespace is specified, it will default to `minecraft`.\n" +
                "\n" +
                "Read more here: [Minecraft Fandom Wiki: Namespaced ID](https://minecraft.fandom.com/wiki/Namespaced_ID)");
        return pageBuilder;
    }

    public static PageBuilder gen_ingredient() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Ingredient")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("_Either_: an [Object](object.md) specifying a registered item or item tag.\n" +
                "\n" +
                "_Or_: an [Array](array.md) of [Objects](object.md) specifying a registered item or item tag.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`item`", "[Identifier](identifier.md)", "_optional_", "ID of a registered item.")
            .addRow("`tag`", "[Identifier](identifier.md)", "_optional_", "ID of an item tag. Will be ignored if `item` is set.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_int() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Integer")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A whole number (integer number), like 3 or -1. Numbers such as 0.3 or 5.5 are not allowed, those would be [Floats](float.md).");
        return pageBuilder;
    }

    public static PageBuilder gen_item_slot() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Item Slot")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A [string](string.md) that represents a slot from an entity or container's inventory.")
            .addNote("It's recommended to use the `container.<slot_number>` item slots for representing the slots of a power that uses the [Inventory (Power Type)](../power_types/inventory.md)");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Name", "Valid `<slot_number>`", "Mapped index")
            .addBreak()
            .addRow("`armor.chest`", " ", "102")
            .addRow("`armor.feet`", " ", "100")
            .addRow("`armor.head`", " ", "103")
            .addRow("`armor.legs`", " ", "101")
            .addRow("`container.<slot_number>`", "0 to 53", "0 to 53")
            .addRow("`enderchest.<slot_number>`", "0 to 26", "200 to 226")
            .addRow("`horse.<slot_number>`", "0 to 14", "500 to 514")
            .addRow("`horse.armor`", " ", "401")
            .addRow("`horse.saddle`", " ", "400")
            .addRow("`hotbar.<slot_number>`", "0 to 8", "0 to 8")
            .addRow("`inventory.<slot_number>`", "0 to 26", "9 to 35")
            .addRow("`villager.<slot_number>`", "0 to 7", "300 to 307")
            .addRow("`weapon.mainhand`", "`1`", "98")
            .addRow("`weapon.offhand`", "`1`", "99")
            .addRow("`weapon`", "", "98");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_item_stack() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Item Stack")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) which defines a new item stack.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`item`", "[Identifier](identifier.md)", " ", "ID of a registered item.")
            .addRow("`amount`", "[Integer](integer.md)", "`1`", "Size of the stack.")
            .addRow("`tag`", "[String](string.md)", "_optional_", "NBT data of the item.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_key() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Key")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) which defines a keybinding, used in active powers to define which key they react to.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`key`", "[String](string.md)", " ", "A string specifying the keybinding. See [Keybindings](../../misc/extras/keybindings.md) for possible values.")
            .addRow("`continuous`", "[Boolean](boolean.md)", "`false`", "Whether the keybinding should only trigger the power on the first tick the key is held down, or, if set to true, continuously on each tick while the key is held.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_object() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Object")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A complex piece of data consisting of more fields. Objects are enclosed in curly braces and contain multiple `\"key\": value` entries separated by commas.");
        return pageBuilder;
    }

    public static PageBuilder gen_particle_effect() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Particle Effect")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A data type that's either a [string](string.md) which defines only the particle type or an [object](object.md) which defines the particle type and its additional parameters.")
            .addNote("Refer to the [Minecraft Fandom Wiki: Particles (Particle IDs)](https://minecraft.fandom.com/wiki/Particles#Particle_IDs) page for a list of **vanilla** particle type IDs that you can use.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`type`", "[Identifier](identifier.md)", " ", "The namespace and ID of the particle type.")
            .addRow("`params`", "[String](string.md)", " ", "The additional parameter for the particle type.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_positioned_item_stack() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Positioned Item Stack")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) which defines a new item stack alongside a position in an inventory. Basically an [Item Stack](item_stack.md) with a `slot` field.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`item`", "[Identifier](identifier.md)", " ", "ID of a registered item.")
            .addRow("`amount`", "[Integer](integer.md)", "`1`", "Size of the stack.")
            .addRow("`tag`", "[String](string.md)", "_optional_", "NBT data of the item.")
            .addRow("`slot`", "[Integer](integer.md)", "_optional_", "Inventory slot position of the stack. If not specified, will be the first free slot in the inventory. See [Positioned Item Stack Slots](../../misc/extras/positioned_item_stack_slots.md) for possible values.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_stat() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Stat")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [object](object.md) specifying a statistic via a statistic type and an [identifier](identifier.md).")
            .addNote("See [Minecraft Fandom Wiki: Statistic (Resource location)](https://minecraft.fandom.com/wiki/Statistics#Resource_location) for a list of vanilla statistic types and names.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`type`", "[Identifier](identifier.md)", " ", "The type of the statistic.")
            .addRow("`id`", "[Identifier](identifier.md)", " ", "The name of the statistic; may depend on the specified type of the statistic.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_status_effect() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Status Effect Instance")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) used to define a status effect with duration, amplifier, etc.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`effect`", "[Identifier](identifier.md)", " ", "ID of the status effect.")
            .addRow("`duration`", "[Integer](integer.md)", "`100`", "Duration of the status effect in ticks.")
            .addRow("`amplifier`", "[Integer](integer.md)", "`0`", "Amplifier of the status effect.")
            .addRow("`is_ambient`", "[Boolean](boolean.md)", "`false`", "Whether the effect counts as an ambient effect.")
            .addRow("`show_particles`", "[Boolean](boolean.md)", "`true`", "Whether the status effect will spawn particles on the player.")
            .addRow("`show_icon`", "[Boolean](boolean.md)", "`true`", "Whether the status effect will show an icon on the HUD.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }

    public static PageBuilder gen_string() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("String")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A piece of text. Has to be enclosed by quotation marks. Please note that if there are supposed to be quotation marks within the text itself, they need to be escaped with a backslash. (`\\`)");
        return pageBuilder;
    }

    public static PageBuilder gen_text_component() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Text Component")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("A [string](string.md) or [object](object.md) used for displaying text with fancy formatting.")
            .addNote("You can refer to [Minecraft Fandom: Raw JSON text format](https://minecraft.fandom.com/wiki/Raw_JSON_text_format) for JSON text components you can use.");
        return pageBuilder;
    }

    public static PageBuilder gen_vector() {
        PageBuilder pageBuilder = PageBuilder.init();

        pageBuilder.addTitle("Vector")
            .addLink("Data Type", "../data_types.md").newLine()
            .addText("An [Object](object.md) that specifies the X, Y and Z coordinates of a certain point in space.");

        pageBuilder.addTitle3("Fields");

        PageBuilder.TableBuilder tableBuilder = PageBuilder.TableBuilder.init();
        tableBuilder.addRow("Field", "Type", "Default", "Description")
            .addBreak()
            .addRow("`x`", "[Float](float.md)", "`0.0`", "The X coordinate of the point.")
            .addRow("`y`", "[Float](float.md)", "`0.0`", "The Y coordinate of the point.")
            .addRow("`z`", "[Float](float.md)", "`0.0`", "The Z coordinate of the point.");
        pageBuilder.addTable(tableBuilder);
        return pageBuilder;
    }
}

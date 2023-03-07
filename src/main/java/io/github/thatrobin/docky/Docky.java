package io.github.thatrobin.docky;

import io.github.thatrobin.docky.utils.DocumentationBuilder;
import io.github.thatrobin.docky.utils.MkdocsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.thatrobin.docky.utils.MkdocsBuilder.createPage;
import static io.github.thatrobin.docky.utils.MkdocsBuilder.createSection;
import static io.github.thatrobin.docky.utils.MkdocsBuilder.init;

public class Docky {

    public static final Logger LOGGER = LogManager.getLogger(Docky.class);

    public static void main(String[] args) {
        DocumentationBuilder builder = new DocumentationBuilder();

        MkdocsBuilder mkdocsBuilder = new MkdocsBuilder();
        mkdocsBuilder.setName("Robin's Apoli Additions")
            .navigation(
                createPage("Home", "index.md"),
                createSection("Types",
                    createPage("Task Types", "types/task_types.md"),
                    createPage("Power Types", "types/power_types.md")
                ),
                createSection("Condition Types",
                    createPage("Entity Condition Types", "types/entity_condition_types.md"),
                    createPage("Bientity Condition Types", "types/bientity_condition_types.md"),
                    createPage("Block Condition Types", "types/block_condition_types.md"),
                    createPage("Item Condition Types", "types/item_condition_types.md")
                ),
                createSection("Action Types",
                    createPage("Entity Action Types", "types/entity_action_types.md"),
                    createPage("Bientity Action Types", "types/bientity_action_types.md"),
                    createPage("Block Action Types", "types/block_action_types.md"),
                    createPage("Item Action Types", "types/item_action_types.md")
                )
            )
            .theme();

        builder.mkdocs(mkdocsBuilder);

        LOGGER.info(builder.getMkDocsBuilder().get().build());
    }

}

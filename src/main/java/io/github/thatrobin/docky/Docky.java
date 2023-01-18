package io.github.thatrobin.docky;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

public class Docky implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.getExtensions().create("docky", DockyExtension.class, project);
        project.getLogger().debug("Created the `docky` extension.");

        TaskContainer tasks = project.getTasks();
        tasks.register("createDocs", TaskModrinthUpload.class, task -> {
            task.setGroup("docky");
            task.setDescription("Create documentation");
        });
        project.getLogger().debug("Registered the `createDocs` task.");

        project.getLogger().debug("Successfully applied the Docky plugin!");
    }
}

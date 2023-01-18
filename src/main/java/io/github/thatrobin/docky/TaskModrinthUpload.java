package io.github.thatrobin.docky;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;


public class TaskModrinthUpload extends DefaultTask {

    private final Logger log = this.getProject().getLogger();

    @TaskAction
    public void apply() {
        log.lifecycle("Minotaur: {}", this.getClass().getPackage().getImplementationVersion());
    }

}

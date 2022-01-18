package org.testcontainers.images.builder;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import org.junit.Test;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.utility.ResourceReaper;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageFromDockerfileTest {

    @Test
    public void shouldAddDefaultLabels() {
        ImageFromDockerfile image = new ImageFromDockerfile()
            .withDockerfileFromBuilder(it -> it.from("scratch"));

        String imageId = image.resolve();

        DockerClient dockerClient = DockerClientFactory.instance().client();

        InspectImageResponse inspectImageResponse = dockerClient.inspectImageCmd(imageId).exec();

        assertThat(inspectImageResponse.getConfig().getLabels())
            .containsAllEntriesOf(DockerClientFactory.DEFAULT_LABELS);
    }

    @Test
    public void shouldAddRyuktLabels() {
        ImageFromDockerfile image = new ImageFromDockerfile()
            .withDockerfileFromBuilder(it -> it.from("scratch"));

        String imageId = image.resolve();

        DockerClient dockerClient = DockerClientFactory.instance().client();

        InspectImageResponse inspectImageResponse = dockerClient.inspectImageCmd(imageId).exec();

        assertThat(inspectImageResponse.getConfig().getLabels())
            .containsAllEntriesOf(ResourceReaper.instance().getLabels());
    }

}

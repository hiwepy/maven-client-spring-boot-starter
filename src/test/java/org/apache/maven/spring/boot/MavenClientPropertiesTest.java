/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.maven.spring.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.deployer.resource.maven.MavenProperties;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MavenClientProperties}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("MavenClientProperties Tests")
class MavenClientPropertiesTest {

    @Test
    @DisplayName("Default constructor creates non-null instance")
    void testDefaultInstance() {
        MavenClientProperties props = new MavenClientProperties();
        assertThat(props).isNotNull();
    }

    @Test
    @DisplayName("PREFIX constant has expected value")
    void testPrefix() {
        assertThat(MavenClientProperties.PREFIX).isEqualTo("maven.settings");
    }

    @Test
    @DisplayName("deleteLocalCopyIfMissing can be set and read")
    void testDeleteLocalCopyIfMissing() {
        MavenClientProperties props = new MavenClientProperties();
        assertThat(props.isDeleteLocalCopyIfMissing()).isFalse();
        props.setDeleteLocalCopyIfMissing(true);
        assertThat(props.isDeleteLocalCopyIfMissing()).isTrue();
    }

    @Test
    @DisplayName("favorLocalRepository can be set and read")
    void testFavorLocalRepository() {
        MavenClientProperties props = new MavenClientProperties();
        assertThat(props.isFavorLocalRepository()).isFalse();
        props.setFavorLocalRepository(true);
        assertThat(props.isFavorLocalRepository()).isTrue();
    }

    @Test
    @DisplayName("resolveJavadoc can be set and read")
    void testResolveJavadoc() {
        MavenClientProperties props = new MavenClientProperties();
        assertThat(props.isResolveJavadoc()).isFalse();
        props.setResolveJavadoc(true);
        assertThat(props.isResolveJavadoc()).isTrue();
    }

    @Test
    @DisplayName("resolveSources can be set and read")
    void testResolveSources() {
        MavenClientProperties props = new MavenClientProperties();
        assertThat(props.isResolveSources()).isFalse();
        props.setResolveSources(true);
        assertThat(props.isResolveSources()).isTrue();
    }

    @Test
    @DisplayName("Inherits MavenProperties fields")
    void testInheritedFields() {
        MavenClientProperties props = new MavenClientProperties();
        props.setLocalRepository("/tmp/repo");
        assertThat(props.getLocalRepository()).isEqualTo("/tmp/repo");

        props.setChecksumPolicy("warn");
        assertThat(props.getChecksumPolicy()).isEqualTo("warn");

        props.setConnectTimeout(5000);
        assertThat(props.getConnectTimeout()).isEqualTo(5000);

        props.setRequestTimeout(10000);
        assertThat(props.getRequestTimeout()).isEqualTo(10000);

        props.setOffline(true);
        assertThat(props.isOffline()).isTrue();

        props.setResolvePom(true);
        assertThat(props.isResolvePom()).isTrue();

        props.setUpdatePolicy("daily");
        assertThat(props.getUpdatePolicy()).isEqualTo("daily");

        Map<String, MavenProperties.RemoteRepository> repos = new HashMap<>();
        repos.put("central", new MavenProperties.RemoteRepository("https://repo1.maven.org/maven2/"));
        props.setRemoteRepositories(repos);
        assertThat(props.getRemoteRepositories()).containsKey("central");
    }
}

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

import org.apache.maven.spring.boot.utils.RepositorySystemUtils;
import org.eclipse.aether.repository.Authentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.deployer.resource.maven.MavenProperties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link RepositorySystemUtils}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("RepositorySystemUtils Tests")
class RepositorySystemUtilsTest {

    @Test
    @DisplayName("isProxyEnabled returns false when no proxy configured")
    void testIsProxyEnabledFalse() {
        MavenProperties properties = new MavenProperties();
        assertThat(RepositorySystemUtils.isProxyEnabled(properties)).isFalse();
    }

    @Test
    @DisplayName("isProxyEnabled returns false when proxy host is null")
    void testIsProxyEnabledNullHost() {
        MavenProperties properties = new MavenProperties();
        MavenProperties.Proxy proxy = new MavenProperties.Proxy();
        proxy.setProtocol("http");
        proxy.setPort(8080);
        properties.setProxy(proxy);
        assertThat(RepositorySystemUtils.isProxyEnabled(properties)).isFalse();
    }

    @Test
    @DisplayName("isProxyEnabled returns false when port is zero")
    void testIsProxyEnabledZeroPort() {
        MavenProperties properties = new MavenProperties();
        MavenProperties.Proxy proxy = new MavenProperties.Proxy();
        proxy.setProtocol("http");
        proxy.setHost("proxy.example.com");
        proxy.setPort(0);
        properties.setProxy(proxy);
        assertThat(RepositorySystemUtils.isProxyEnabled(properties)).isFalse();
    }

    @Test
    @DisplayName("isProxyEnabled returns true when proxy is fully configured")
    void testIsProxyEnabledTrue() {
        MavenProperties properties = new MavenProperties();
        MavenProperties.Proxy proxy = new MavenProperties.Proxy();
        proxy.setProtocol("http");
        proxy.setHost("proxy.example.com");
        proxy.setPort(8080);
        properties.setProxy(proxy);
        assertThat(RepositorySystemUtils.isProxyEnabled(properties)).isTrue();
    }

    @Test
    @DisplayName("proxyHasCredentials returns false when no proxy")
    void testProxyHasCredentialsNoProxy() {
        MavenProperties properties = new MavenProperties();
        assertThat(RepositorySystemUtils.proxyHasCredentials(properties)).isFalse();
    }

    @Test
    @DisplayName("proxyHasCredentials returns true when proxy has auth")
    void testProxyHasCredentialsWithAuth() {
        MavenProperties properties = new MavenProperties();
        MavenProperties.Proxy proxy = new MavenProperties.Proxy();
        MavenProperties.Authentication auth = new MavenProperties.Authentication();
        auth.setUsername("user");
        auth.setPassword("pass");
        proxy.setAuth(auth);
        properties.setProxy(proxy);
        assertThat(RepositorySystemUtils.proxyHasCredentials(properties)).isTrue();
    }

    @Test
    @DisplayName("proxyHasCredentials returns false when proxy has no auth")
    void testProxyHasCredentialsNoAuth() {
        MavenProperties properties = new MavenProperties();
        MavenProperties.Proxy proxy = new MavenProperties.Proxy();
        proxy.setHost("proxy.example.com");
        proxy.setPort(8080);
        properties.setProxy(proxy);
        assertThat(RepositorySystemUtils.proxyHasCredentials(properties)).isFalse();
    }

    @Test
    @DisplayName("proxyHasCredentials returns false when auth has no username")
    void testProxyHasCredentialsNoUsername() {
        MavenProperties properties = new MavenProperties();
        MavenProperties.Proxy proxy = new MavenProperties.Proxy();
        MavenProperties.Authentication auth = new MavenProperties.Authentication();
        auth.setPassword("pass");
        proxy.setAuth(auth);
        properties.setProxy(proxy);
        assertThat(RepositorySystemUtils.proxyHasCredentials(properties)).isFalse();
    }

    @Test
    @DisplayName("remoteRepositoryHasCredentials returns false when no auth")
    void testRemoteRepoHasCredentialsFalse() {
        MavenProperties.RemoteRepository repo = new MavenProperties.RemoteRepository("http://example.com");
        assertThat(RepositorySystemUtils.remoteRepositoryHasCredentials(repo)).isFalse();
    }

    @Test
    @DisplayName("remoteRepositoryHasCredentials returns true when auth is set")
    void testRemoteRepoHasCredentialsTrue() {
        MavenProperties.RemoteRepository repo = new MavenProperties.RemoteRepository("http://example.com");
        MavenProperties.Authentication auth = new MavenProperties.Authentication();
        auth.setUsername("user");
        auth.setPassword("pass");
        repo.setAuth(auth);
        assertThat(RepositorySystemUtils.remoteRepositoryHasCredentials(repo)).isTrue();
    }

    @Test
    @DisplayName("remoteRepositoryHasCredentials returns false for null")
    void testRemoteRepoHasCredentialsNull() {
        assertThat(RepositorySystemUtils.remoteRepositoryHasCredentials(null)).isFalse();
    }

    @Test
    @DisplayName("newAuthentication creates valid Authentication object")
    void testNewAuthentication() {
        Authentication auth = RepositorySystemUtils.newAuthentication("user", "pass");
        assertThat(auth).isNotNull();
    }
}

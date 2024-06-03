package org.buaa.scholar.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class ElasticSearchClientConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        try {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.loadTrustMaterial(null, (chain, authType) -> true);

            // we need to disable ssl verify_certificates
            return ClientConfiguration.builder()
                    .connectedTo("localhost:9200")
                    .usingSsl()
                    .withBasicAuth("wy", "123456")
                    .withClientConfigurer(ElasticsearchClients.ElasticsearchHttpClientConfigurationCallback.from(
                            httpAsyncClientBuilder -> {
                                try {
                                    return httpAsyncClientBuilder.setSSLContext(sslContextBuilder.build())
                                            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
                                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    ))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

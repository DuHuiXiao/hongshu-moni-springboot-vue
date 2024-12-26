package com.app.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
@Slf4j
public class ESConfig {

    @Value("${es.url}")
    String esUrl;

    @Value("${es.port}")
    int esPort;

    @Value("${es.apiKey}")
    String apiKey;

    private RestClient restClient;
    private ElasticsearchClient client;


    private ElasticsearchTransport transport;

    @Bean(name = "elasticsearchClient")
    public ElasticsearchClient getElasticsearchClient() {
        RestClientBuilder clientBuilder = RestClient.builder(
                new HttpHost(esUrl, esPort)
        );
        if(apiKey != null){
            clientBuilder.setDefaultHeaders(new Header[]{
                    new BasicHeader("Authorization", "ApiKey " + apiKey)
            });
        }
        restClient = clientBuilder.build();
        // 使用Jackson映射器创建传输层
        transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );
        // 创建API客户端
        client = new ElasticsearchClient(transport);
        return client;
    }


    public void close() {
        if (client != null) {
            try {
                transport.close();
                restClient.close();
            } catch (IOException e) {
                log.error("关闭es连接异常");
            }
        }
    }
}

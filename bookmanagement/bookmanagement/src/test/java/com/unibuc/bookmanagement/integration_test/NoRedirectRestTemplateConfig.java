// acest template este creat special pentru teste,
// ca sa nu mai urmeze automat redirecturile (ex: dupa inregistrare)
// asa putem verifica in test daca raspunsul este 302 (redirect) sau nu
package com.unibuc.bookmanagement.integration_test;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

    /**
     * RestTemplate folosit în teste pentru a NU urmări automat redirect-urile,
     * ca să putem aserta răspunsul 302 (FOUND).
     */
    @Configuration
    public class NoRedirectRestTemplateConfig {
    @Bean
    @Primary
    public TestRestTemplate noRedirectTestRestTemplate() {

        CloseableHttpClient httpClient = HttpClients.custom()
                .disableRedirectHandling()   // stop follow-redirect
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplateBuilder builder = new RestTemplateBuilder()
                .requestFactory(() -> requestFactory);

        return new TestRestTemplate(builder);
    }

}

package com.devoteam.azure203iacdemo.azure;


import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AzureConfiguration {

    private final Logger logger = LoggerFactory.getLogger(AzureConfiguration.class);

    @Bean
    public AzureMetaDataService azureMetaDataService(final Azure azure) {
        return new AzureMetaDataService(azure, LoggerFactory.getLogger(AzureMetaDataService.class));
    }

    @Bean
    public Azure azure() throws IOException {
        AzureTokenCredentials azureTokenCredentials = new ApplicationTokenCredentials(AzureMetaData.CLIENT_ID, AzureMetaData.DOMAIN, AzureMetaData.SECRET, AzureEnvironment.AZURE);
        logger.debug("Token credentials set. " + azureTokenCredentials.toString());
        return Azure.configure()
                .withLogLevel(LogLevel.BASIC)
                .authenticate(azureTokenCredentials)
                .withDefaultSubscription();
        // if you want to use a special subscription id, use it here.
        //.withSubscription(AzureMetaData.SUBSCRIPTION_ID);
    }
}

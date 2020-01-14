package com.devoteam.azure203iacdemo.azure.compute;

import com.microsoft.azure.management.Azure;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureComputeConfiguration {

    @Bean
    public AzureComputeUtils azureComputeUtils() {
        return new AzureComputeUtils(LoggerFactory.getLogger(AzureComputeUtils.class));
    }

    @Bean
    public AzureComputeService azureComputeService(Azure azure, AzureComputeUtils azureComputeUtils) {
        return new AzureComputeService(LoggerFactory.getLogger(AzureComputeUtils.class), azure, azureComputeUtils);
    }
}

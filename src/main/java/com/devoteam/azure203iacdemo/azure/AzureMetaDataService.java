package com.devoteam.azure203iacdemo.azure;

import com.devoteam.azure203iacdemo.azure.compute.AzureComputeService;
import com.devoteam.azure203iacdemo.azure.compute.AzureComputeUtils;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.resources.ResourceGroup;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class AzureMetaDataService implements ApplicationRunner {
    private final Azure azure;
    private final Logger logger;

    @Autowired
    private AzureComputeService azureComputeService;

    @Autowired
    private AzureComputeUtils azureComputeUtils;

    public AzureMetaDataService(Azure azure, Logger logger) {
        this.azure = azure;
        this.logger = logger;
    }

    /**
     * Obtain all resource groups.
     *
     * @return List of all resource groups. Note: These may not all be loaded, as per Azure SDK spec.
     */
    public PagedList<ResourceGroup> getComputeResourceGroups() {
        return azure.resourceGroups().list();
    }

    public void deleteResourceGroupByName(final String resourceGroupName) {
        azure.resourceGroups().deleteByName(resourceGroupName);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Subscription ID: " + this.azure.subscriptionId());
    }
}

package com.devoteam.azure203iacdemo;

import com.devoteam.azure203iacdemo.azure.AzureMetaData;
import com.devoteam.azure203iacdemo.azure.AzureMetaDataService;
import com.devoteam.azure203iacdemo.azure.compute.AzureComputeService;
import com.devoteam.azure203iacdemo.azure.compute.AzureComputeUtils;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.compute.Disk;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CreateVirtualMachineITTest {

    @Autowired
    private AzureComputeService azureComputeService;

    @Autowired
    private AzureComputeUtils azureComputeUtils;

    @Autowired
    private AzureMetaDataService azureMetaDataService;

    @Test
    void createBasicVirtualMachine() {
        final String resourceGroupPrefix = "test";
        final String resourceGroupName = setupVirtualMachine(resourceGroupPrefix);

        // test infrastructure
        PagedList<VirtualMachine> virtualMachines = azureComputeService.getVirtualMachines();
        virtualMachines.loadAll();

        Optional<VirtualMachine> firstVirtualMachine = virtualMachines.stream()
                .filter(virtualMachine -> {
                    final String actual = virtualMachine.resourceGroupName().toUpperCase();
                    final String expectedStart = (AzureMetaData.RESOURCE_GROUP_PREFIX + resourceGroupPrefix).toUpperCase();
                    return actual.contains(expectedStart);
                })
                .findFirst();

        assertTrue(firstVirtualMachine.isPresent(), "Should have found virtual machine in this tests' resource group.");

        // clean up
        azureMetaDataService.deleteResourceGroupByName(resourceGroupName);
    }

    /**
     * @param resourceGroupPrefix prefix to use in resource group.
     */
    private String setupVirtualMachine(final String resourceGroupPrefix) {
        // setup infrastructure
        final String resourceGroup = azureComputeUtils.createRandomName(resourceGroupPrefix);
        Creatable<Disk> disk = azureComputeService.createDisk(resourceGroup);
        azureComputeService.createLinuxVirtualMachine(disk, resourceGroup);
        return resourceGroup;
    }


}

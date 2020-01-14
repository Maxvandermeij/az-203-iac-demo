package com.devoteam.azure203iacdemo.azure.compute;

import com.devoteam.azure203iacdemo.azure.AzureMetaData;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.compute.Disk;
import com.microsoft.azure.management.compute.KnownLinuxVirtualMachineImage;
import com.microsoft.azure.management.compute.VirtualMachine;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import org.slf4j.Logger;

import java.util.Date;

public class AzureComputeService {

    private final Logger logger;
    private final Azure azure;
    private final AzureComputeUtils azureComputeUtils;

    public AzureComputeService(Logger logger, Azure azure, AzureComputeUtils azureComputeUtils) {
        this.logger = logger;
        this.azure = azure;
        this.azureComputeUtils = azureComputeUtils;
    }

    public PagedList<VirtualMachine> getVirtualMachines() {
        return azure.virtualMachines().list();
    }

    /**
     * Creates a new disk to be created in the defined resource group through the Azure REST API.
     *
     * @param resourceGroupName the resource group for the disk to be put into.
     * @return Disk, representing the disk to be created.
     */
    public Creatable<Disk> createDisk(final String resourceGroupName) {
        return azure.disks().define(azureComputeUtils.createRandomName("dsk_"))
                .withRegion(AzureMetaData.REGION)
                .withNewResourceGroup(resourceGroupName)
                .withData()
                .withSizeInGB(100);
    }

    /**
     * Create a new Linux virtual machine through the Azure REST API.
     *
     * @param dataDiskCreatable the storage to be used.
     * @param resourceGroupName the resource group for the virtual machine to be put into.
     * @return VirtualMachine object, being the newly created virtual machine.
     */
    public VirtualMachine createLinuxVirtualMachine(Creatable<Disk> dataDiskCreatable, final String resourceGroupName) {
        Date t1 = new Date();
        final String linuxVirtualMachineName = azureComputeUtils.createRandomName("vm_");

        VirtualMachine virtualMachine = azure.virtualMachines()
                .define(linuxVirtualMachineName)
                .withRegion(AzureMetaData.REGION)
                .withNewResourceGroup(resourceGroupName)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS)
                .withRootUsername("JoranIsCluelessLol")
                .withRootPassword("Welkom01!")
                .withNewDataDisk(10)
                .withNewDataDisk(dataDiskCreatable)
                .withSize(VirtualMachineSizeTypes.STANDARD_B2MS)
                .create();

        Date t2 = new Date();
        logger.info("Created VM: (took " + ((t2.getTime() - t1.getTime()) / 1000) + " seconds) " + virtualMachine.id());

        logger.info("Applying tag.");
        virtualMachine.update()
                .withTag("ac203-iac-demo", "yes");
        logger.info("Tag applied. Returning.");

        return virtualMachine;
    }

    /**
     * Deletes virtual machine by ID.
     *
     * @param virtualMachineId the ID of the virtual machine to delete.
     */
    public void deleteVirtualMachineById(final String virtualMachineId) {
        azure.virtualMachines().deleteById(virtualMachineId);
    }
}

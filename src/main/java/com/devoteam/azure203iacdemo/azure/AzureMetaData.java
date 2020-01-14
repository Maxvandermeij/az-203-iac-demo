package com.devoteam.azure203iacdemo.azure;

import com.microsoft.azure.management.resources.fluentcore.arm.Region;

public class AzureMetaData {

    public static final Region REGION = Region.EUROPE_WEST;
    public static final String RESOURCE_GROUP_PREFIX = "ac203_demo_";


    //TODO: Fill this in.
    /**
     * If you want to use a custom subscription, put it here and use it in the {@link AzureConfiguration} class.
     */
    public static final String SUBSCRIPTION_ID = "";

    /**
     * Define the Application ID from the application registered in your Azure AD.
     */
    public static final String CLIENT_ID = "";

    /**
     * Define the tenant ID from the application registered in your Azure AD.
     */
    public static final String DOMAIN = "";

    /**
     * Define the application secret from the application registered in your Azure AD.
     */
    public static final String SECRET = "";

}

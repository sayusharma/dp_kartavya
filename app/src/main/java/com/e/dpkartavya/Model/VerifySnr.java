package com.e.dpkartavya.Model;

import java.util.ArrayList;

public class VerifySnr {
    private BasicDetails basicDetails;
    private ArrayList<ServiceProvider> serviceProviders;
    private SecurityChecks securityChecks;
    private MoreDetails moreDetails;
    public VerifySnr() {
    }

    public BasicDetails getBasicDetails() {
        return basicDetails;
    }

    public ArrayList<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public SecurityChecks getSecurityChecks() {
        return securityChecks;
    }

    public MoreDetails getMoreDetails() {
        return moreDetails;
    }

    public VerifySnr(BasicDetails basicDetails, ArrayList<ServiceProvider> serviceProviders, SecurityChecks securityChecks, MoreDetails moreDetails) {
        this.basicDetails = basicDetails;
        this.serviceProviders = serviceProviders;
        this.securityChecks = securityChecks;
        this.moreDetails = moreDetails;
    }
}

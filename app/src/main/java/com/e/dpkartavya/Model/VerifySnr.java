package com.e.dpkartavya.Model;

import com.e.dpkartavya.ServiceProviders;

public class VerifySnr {
    private BasicDetails basicDetails;
    private ServiceProviders serviceProviders;
    private SecurityChecks securityChecks;
    public VerifySnr() {
    }
    public VerifySnr(BasicDetails basicDetails, ServiceProviders serviceProviders, SecurityChecks securityChecks) {
        this.basicDetails = basicDetails;
        this.serviceProviders = serviceProviders;
        this.securityChecks = securityChecks;
    }

    public BasicDetails getBasicDetails() {
        return basicDetails;
    }

    public ServiceProviders getServiceProviders() {
        return serviceProviders;
    }

    public SecurityChecks getSecurityChecks() {
        return securityChecks;
    }
}

package com.t1t.t1c.containers.smartcards.emv;

import com.t1t.t1c.containers.GenericContainer;
import com.t1t.t1c.model.rest.GclEmvApplication;
import com.t1t.t1c.model.rest.GclEmvApplicationData;
import com.t1t.t1c.model.rest.GclEmvCertificate;

import java.util.List;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public interface IEmvContainer extends GenericContainer {

    List<GclEmvApplication> getApplications();

    GclEmvApplicationData getApplicationData();

    GclEmvCertificate getIccPublicKeyCertificate(String... aid);

    GclEmvCertificate getIssuerPublicKeyCertificate(String... aid);
}
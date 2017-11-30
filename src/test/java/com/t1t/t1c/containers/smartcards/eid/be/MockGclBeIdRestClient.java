package com.t1t.t1c.containers.smartcards.eid.be;

import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.model.rest.GclBeIDAllCertificates;
import com.t1t.t1c.model.rest.GclBeIdAddress;
import com.t1t.t1c.model.rest.GclBeIdAllData;
import com.t1t.t1c.model.rest.GclBeIdRn;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;
import t1c.containers.smartcards.eid.be.GclBeIdAddress;
import t1c.containers.smartcards.eid.be.GclBeIdAllData;
import t1c.containers.smartcards.eid.be.GclBeIdRn;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclBeIdRestClient {

    private final BehaviorDelegate<GclBeidRestClient> delegate;

    public MockGclBeIdRestClient(BehaviorDelegate<GclBeidRestClient> delegate) {
        this.delegate = delegate;
    }

    public Call<T1cResponse<GclBeIdRn>> getRnData(String containerId, String readerId) {
        return delegate.returning(MockResponseFactory.getGclBeIdRnResponse()).getRnData(containerId, readerId);
    }

    public Call<T1cResponse<GclBeIdAddress>> getBeIdAddress(String containerId, String readerId) {
        return delegate.returning(MockResponseFactory.getGclBeIdAddressResponse()).getBeIdAddress(containerId, readerId);
    }

    public Call<T1cResponse<String>> getBeIdPicture(String containerId, String readerId) {
        return delegate.returning(MockResponseFactory.getGclBeIdPictureResponse()).getBeIdPicture(containerId, readerId);
    }

    public Call<T1cResponse<String>> getRrnCertificate(String containerId, String readerId) {
        return delegate.returning(MockResponseFactory.getGclBeIdRrnCertificateResponse()).getRrnCertificate(containerId, readerId);
    }

    public Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(String containerId, String readerId, String filter) {
        return delegate.returning(MockResponseFactory.getGclBeIdAllDataResponse()).getBeIdAllData(containerId, readerId, filter);
    }

    public Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(String containerId, String readerId) {
        return delegate.returning(MockResponseFactory.getGclBeIdAllDataResponse()).getBeIdAllData(containerId, readerId);
    }

    public Call<T1cResponse<GclBeIDAllCertificates>> getBeIdAllCertificates(String containerId, String readerId) {
        return delegate.returning(MockResponseFactory.getGclBeIdAllCertificatesResponse()).getBeIdAllCertificates(containerId, readerId);
    }

    public Call<T1cResponse<GclBeIDAllCertificates>> getBeIdAllCertificates(String containerId, String readerId, String filter) {
        return delegate.returning(MockResponseFactory.getGclBeIdAllCertificatesResponse()).getBeIdAllCertificates(containerId, readerId, filter);
    }
}
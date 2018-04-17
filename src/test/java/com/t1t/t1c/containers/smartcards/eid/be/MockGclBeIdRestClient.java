package com.t1t.t1c.containers.smartcards.eid.be;


import com.t1t.t1c.MockResponseFactory;
import com.t1t.t1c.core.GclAuthenticateOrSignData;
import com.t1t.t1c.core.GclVerifyPinRequest;
import com.t1t.t1c.exceptions.RestException;
import com.t1t.t1c.mock.AbstractMockRestClient;
import com.t1t.t1c.model.T1cResponse;
import com.t1t.t1c.utils.PinUtil;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class MockGclBeIdRestClient extends AbstractMockRestClient<GclBeIdRestClient> implements GclBeIdRestClient {

    public MockGclBeIdRestClient(BehaviorDelegate<GclBeIdRestClient> delegate) {
        super(delegate);
    }

    @Override
    public Call<T1cResponse<GclBeIdRn>> getRnData(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdRnResponse()).getRnData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIdAddress>> getBeIdAddress(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdAddressResponse()).getBeIdAddress(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getBeIdPicture(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdPictureResponse()).getBeIdPicture(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getRrnCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdRrnCertificateResponse()).getRrnCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclBeIdAllDataResponse(filter)).getBeIdAllData(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<GclBeIdAllData>> getBeIdAllData(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclBeIdAllDataResponse(null)).getBeIdAllData(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclBeIdAllCertificatesResponse(null)).getBeIdAllCertificates(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<GclBeIdAllCertificates>> getBeIdAllCertificates(String containerId, String readerId, String filter) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getGclBeIdAllCertificatesResponse(filter)).getBeIdAllCertificates(containerId, readerId, filter);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId, GclVerifyPinRequest request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(request.getPin())).verifyPin(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<Object>> verifyPin(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.verifyPin(PinUtil.encryptPin("1111"))).verifyPin(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> authenticate(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).authenticate(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> sign(String containerId, String readerId, GclAuthenticateOrSignData request) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getSignedHashResponse(request.getPin())).sign(containerId, readerId, request);
    }

    @Override
    public Call<T1cResponse<String>> getRootCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdRootCertificateResponse()).getRootCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getCitizenCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdCitizenCertificateResponse()).getCitizenCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getNonRepudiationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdNonRepudiationCertificateResponse()).getNonRepudiationCertificate(containerId, readerId);
    }

    @Override
    public Call<T1cResponse<String>> getAuthenticationCertificate(String containerId, String readerId) throws RestException {
        return delegate.returningResponse(MockResponseFactory.getBeIdAuthenticationCertificateResponse()).getAuthenticationCertificate(containerId, readerId);
    }
}
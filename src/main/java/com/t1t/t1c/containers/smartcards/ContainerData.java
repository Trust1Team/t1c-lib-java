package com.t1t.t1c.containers.smartcards;

import com.t1t.t1c.model.T1cCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
public class ContainerData {

    private String fullName;
    private String givenName;
    private String surName;
    private String dateOfBirth;
    private String streetAndNumber;
    private String municipality;
    private String zipCode;
    private String country;
    private String nationality;
    private String base64Picture;
    private Map<Integer, T1cCertificate> authenticationCertificateChain;
    private Map<Integer, T1cCertificate> signingCertificateChain;
    private List<Map<Integer, T1cCertificate>> certificateChains;
    private Map<String, T1cCertificate> allCertificates;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public void setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBase64Picture() {
        return base64Picture;
    }

    public void setBase64Picture(String base64Picture) {
        this.base64Picture = base64Picture;
    }

    public Map<Integer, T1cCertificate> getAuthenticationCertificateChain() {
        return authenticationCertificateChain;
    }

    public void setAuthenticationCertificateChain(Map<Integer, T1cCertificate> authenticationCertificateChain) {
        this.authenticationCertificateChain = authenticationCertificateChain;
    }

    public Map<Integer, T1cCertificate> getSigningCertificateChain() {
        return signingCertificateChain;
    }

    public void setSigningCertificateChain(Map<Integer, T1cCertificate> signingCertificateChain) {
        this.signingCertificateChain = signingCertificateChain;
    }

    public List<Map<Integer, T1cCertificate>> getCertificateChains() {
        return certificateChains;
    }

    public void setCertificateChains(List<Map<Integer, T1cCertificate>> certificateChains) {
        this.certificateChains = certificateChains;
    }

    public Map<String, T1cCertificate> getAllCertificates() {
        return allCertificates;
    }

    public void setAllCertificates(Map<String, T1cCertificate> allCertificates) {
        this.allCertificates = allCertificates;
    }
}
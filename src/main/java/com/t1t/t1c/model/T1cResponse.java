package com.t1t.t1c.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author Guillaume Vandecasteele
 * @since 2017
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class T1cResponse<T> implements Serializable {

    private T data;
    private Boolean success;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T1cResponse<T> withData(T data) {
        setData(data);
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T1cResponse<T> withSuccess(Boolean success) {
        setSuccess(success);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof T1cResponse)) return false;

        T1cResponse<?> that = (T1cResponse<?>) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return success != null ? success.equals(that.success) : that.success == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (success != null ? success.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "T1cResponse{" +
                "data=" + data +
                ", success=" + success +
                '}';
    }
}
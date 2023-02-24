package com.synchrony.springboot.response;

import com.synchrony.springboot.model.ImgurData;

public class ImgurApiResponse {
    private boolean success;
    private int status;
    private ImgurData data;
    
    public ImgurApiResponse(boolean success, int status, ImgurData data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ImgurData getData() {
        return data;
    }

    public void setData(ImgurData data) {
        this.data = data;
    }
}
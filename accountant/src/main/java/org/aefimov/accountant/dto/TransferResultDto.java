package org.aefimov.accountant.dto;

import java.io.Serializable;

public class TransferResultDto implements Serializable {

    private Boolean isSuccessful;

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }
}

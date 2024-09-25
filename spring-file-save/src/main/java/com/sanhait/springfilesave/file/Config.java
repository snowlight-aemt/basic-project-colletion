package com.sanhait.springfilesave.file;

import java.io.Serializable;

public class Config implements Serializable {
    public int maxSeqNo;

    public int getMaxSeqNo() {
        return maxSeqNo;
    }

    public void setMaxSeqNo(int maxSeqNo) {
        this.maxSeqNo = maxSeqNo;
    }

    public Config(int maxSeqNo) {
        this.maxSeqNo = maxSeqNo;
    }
}
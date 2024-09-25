package com.sanhait.springfilesave.file;

import java.io.Serializable;

public class Config implements Serializable {
//    public int seqNo;
    public int maxSeqNo;

//    public int getSeqNo() {
//        return seqNo;
//    }
//
//    public void setSeqNo(int seqNo) {
//        this.seqNo = seqNo;
//    }

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
package com.sanhait.springfilesave.file;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
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
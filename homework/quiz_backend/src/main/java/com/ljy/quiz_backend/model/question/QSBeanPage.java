package com.ljy.quiz_backend.model.question;

import java.util.List;

public class QSBeanPage {
    private int total;
    private List<QSBeanOutManage> qsBeanList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<QSBeanOutManage> getQsBeanList() {
        return qsBeanList;
    }

    public void setQsBeanList(List<QSBeanOutManage> qsBeanList) {
        this.qsBeanList = qsBeanList;
    }
}

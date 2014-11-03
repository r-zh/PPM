package cn.edu.sdu.sc.spepms.system.common.models;

import javax.persistence.Entity;

@Entity
public class Bank extends AuditableModel {

    private String type;

    //金额
    private Integer numbers;

    //编号
    private String account;

    public String getType() {
        return type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer number) {
        this.numbers = number;
    }
    
}

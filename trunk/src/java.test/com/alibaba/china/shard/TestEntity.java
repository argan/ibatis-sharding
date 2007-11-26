package com.alibaba.china.shard;

public class TestEntity implements java.io.Serializable {

    private static final long serialVersionUID = -1088851445183277232L;

    private Integer           id;
    private String            loginName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}

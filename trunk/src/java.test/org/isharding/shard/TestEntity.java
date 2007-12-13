package org.isharding.shard;

public class TestEntity implements java.io.Serializable {

    private static final long serialVersionUID = -1088851445183277232L;

    private Integer           id;
    private String            loginName;
    private String            content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
    public String toString(){
        return String.format("%s", loginName);
    }
}

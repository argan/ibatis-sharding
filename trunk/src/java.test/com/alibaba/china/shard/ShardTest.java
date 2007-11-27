package com.alibaba.china.shard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShardTest extends TestCase {

    private SimpleDAO  simpleDAO;

    private DataSource dataSource1 = null;
    private DataSource dataSource2 = null;

    int                count1;
    int                count2;

    protected void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/beans.xml");
        this.simpleDAO = (SimpleDAO) ctx.getBean("simpleDAO");
        assertNotNull(this.simpleDAO);
        this.dataSource1 = (DataSource) ctx.getBean("dataSource1");
        assertNotNull(dataSource1);
        dataSource2 = (DataSource) ctx.getBean("dataSource2");
        assertNotNull(dataSource2);
    }

    protected void tearDown() {
         clearData(dataSource1);
         clearData(dataSource2);
    }

    private void initCompute() {
        int i = 0;
        // 6-8,0
        Set<String> set1 = new HashSet<String>(50);
        // 2-5,1,9
        Set<String> set2 = new HashSet<String>(50);
        while (i < 100) {
            String login = "login_name" + i;
            int mod = Math.abs((login).hashCode()) % 10;
            if (mod == 0 || mod == 6 || mod == 7 || mod == 8) {
                set1.add(login);
            } else {
                set2.add(login);
            }
            i++;
        }
        count1 = set1.size();
        count2 = set2.size();
    }

    private void clearData(DataSource ds) {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("delete from t_test");
        } catch (SQLException e) {
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
    }

    public void testInsert() {
        initCompute();
        initData();
        assertEquals(count1, checkCount(dataSource1));
        assertEquals(count2, checkCount(dataSource2));
    }

    private void initData() {
        int i = 0;
        while (i < 100) {
            String login = "login_name" + i;
            String content = "content " + System.currentTimeMillis();
            String sql = "insert into t_test (login_name,content) values ( ?,?)";

            int mod = Math.abs(login.hashCode()) % 10;
            // 6-8,0
            if (mod == 0 || (mod >= 6 && mod <= 8)) {
                insert(dataSource1, sql, login, content);
            } else {
                insert(dataSource2, sql, login, content);
            }
            i++;
        }
    }

    private void insert(DataSource dataSource, String sql, String login, String content) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, login);
            pstmt.setString(2, content);
            pstmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testSelectOne() {
        initData();
        TestEntity entity = this.simpleDAO.getEntity("login_name15");
        assertNotNull(entity);
    }

    public void testSelectMulti() {
        initData();
        String[] loginNames = { "login_name15", "login_name1", "login_name12", "login_name51", "login_name97" };
        List<TestEntity> list = this.simpleDAO.getEntities(loginNames);
        assertNotNull(list);
        assertEquals(5, list.size());
    }

    public void testSelectCount() {
        initData();
        int count = this.simpleDAO.getTotalCount();
        assertEquals(100, count);
    }

    private int checkCount(DataSource dataSource) {
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from t_test");
            rs.next();
            int result = rs.getInt(1);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}

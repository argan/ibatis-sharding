package org.isharding.shard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleDAOTest extends TestCase {

    private static final String SQL_CREATE_TABLE = "create table t_test(id int identity primary key,login_name varchar(32) not null,content varchar(200))";
    private static final String SQL_DROP_TABLE   = "drop table t_test ";
    private SimpleDAO           simpleDAO;

    private DataSource          dataSource1      = null;
    private DataSource          dataSource2      = null;

    int                         count1;
    int                         count2;

    protected void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/beans.xml");
        this.simpleDAO = (SimpleDAO) ctx.getBean("simpleDAO");
        assertNotNull(this.simpleDAO);
        this.dataSource1 = (DataSource) ctx.getBean("dataSource1");
        assertNotNull(dataSource1);
        dataSource2 = (DataSource) ctx.getBean("dataSource2");
        assertNotNull(dataSource2);

        execute(dataSource1, SQL_CREATE_TABLE);
        execute(dataSource2, SQL_CREATE_TABLE);
    }

    private void execute(DataSource dataSource, String sql) {
        try {
            Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void tearDown() {
        execute(dataSource1, SQL_DROP_TABLE);
        execute(dataSource2, SQL_DROP_TABLE);
    }

    public void testInsert() {
        initCompute();
        initData();
        assertEquals(count1, checkCount(dataSource1));
        assertEquals(count2, checkCount(dataSource2));
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

    public void testSelectMap() {
        initData();
        String[] loginNames = { "login_name15", "login_name1", "login_name12", "login_name51", "login_name97" };
        Map<String, TestEntity> map = this.simpleDAO.getEntitiesMap(loginNames);
        assertNotNull(map);
        assertEquals(5, map.size());
        assertNotNull(map.get("login_name15"));
        assertEquals(map.get("login_name15").getLoginName(), "login_name15");
    }

    public void testSelectCount() {
        initData();
        int count = this.simpleDAO.getTotalCount();
        assertEquals(100, count);
    }

    public void testSelectListSorted() {
        initData();
        String[] loginNames = { "login_name15", "login_name1", "login_name12", "login_name51", "login_name97" };
        List<TestEntity> list = this.simpleDAO.getEntitiesSorted(loginNames);
        assertNotNull(list);
        Arrays.sort(loginNames);
        String[] results = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            results[i] = list.get(i).getLoginName();
        }
        assertEquals(results.length, loginNames.length);
        for (int i = 0; i < results.length; i++) {
            assertEquals(results[i], loginNames[i]);
        }
    }

    public void testSelectPaged() {
        initData();
        List<TestEntity> list1 = this.simpleDAO.all(1, 50);
        List<TestEntity> list2 = this.simpleDAO.all(2, 50);
        List<TestEntity> listAll = this.simpleDAO.all(1, 100);
        for (int i = 0; i < list1.size(); i++) {
            assertEquals(list1.get(i).getLoginName(), listAll.get(i).getLoginName());
        }
        for (int i = 0; i < list2.size(); i++) {
            assertEquals(list2.get(i).getLoginName(), listAll.get(i + list1.size()).getLoginName());
        }
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

}

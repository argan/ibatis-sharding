package com.alibaba.china.shard;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ShardTest extends TestCase {

    private SimpleDAO simpleDAO;

    protected void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/beans.xml");
        this.simpleDAO = (SimpleDAO) ctx.getBean("simpleDAO");
        assertNotNull(this.simpleDAO);
    }

    public void testInsert() {
        int i = 0;
        while (i++ < 100) {
            TestEntity entity = new TestEntity();
            entity.setLoginName("argan" + System.currentTimeMillis());
            this.simpleDAO.insertEntity(entity);
            assertTrue(entity.getId() > 0);
        }
    }

}

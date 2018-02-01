package org.free.commons.generator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO
 * @author dongdaiming
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class BatchUpdateTest {
    @Autowired
    private DataSource dataSource;
    
    @Test
    public void testBatchUpdate() throws SQLException {
        long start = System.currentTimeMillis();
         Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into orders(order_status, order_no, biz_date) values (?, ?, ?)");
        conn.setAutoCommit(false);
        for(int i = 0; i < 1000000; i++) {
            ps.setInt(1, i % 4);
            ps.setString(2, String.valueOf(System.nanoTime()));
            ps.setDate(3, new Date(System.currentTimeMillis()));
            ps.addBatch();
            
            if((i + 1) % 400 == 0) {
                ps.executeBatch();
                conn.commit();
            }
        }
        
        System.out.println((System.currentTimeMillis() - start) / 1000 + " sec");
//        504 sec
    }
    
    @Test
    public void testBatchUpdate2() throws SQLException {
        long start = System.currentTimeMillis();
        Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into orders(order_status, order_no, biz_date) values (?, ?, ?)");
        for(int i = 0; i < 1000000; i++) {
            ps.setInt(1, i % 4);
            ps.setString(2, String.valueOf(System.nanoTime()));
            ps.setDate(3, new Date(System.currentTimeMillis()));
            ps.execute();
        }
        
        System.out.println((System.currentTimeMillis() - start) / 1000 + " sec");
//        40+ min
    }
    

}

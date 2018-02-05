package org.stathry.generator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.stathry.generator.OrderGenerator;
import org.stathry.generator.util.BoundAtomicInteger;

/**
 * TODO
 * @author dongdaiming
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class OrderGeneratorTest {
    
    @Autowired
    private OrderGenerator orderGenerator;
    
    @Test
    public void testSigle1() throws InterruptedException {
        BoundAtomicInteger inc = new BoundAtomicInteger(0, 9999);
        List<Integer> list = new ArrayList<Integer>(1000000);
        for(int i = 0 ; i < 1000000; i++) {
            list.add(inc.incrementWithBound());
        }
        System.out.println(list.size());
        System.out.println(Collections.max(list));
    }
    
    @Test
    public void testSigle2() throws InterruptedException {
        List<String> list = new ArrayList<>(1000000);
        String userId = "100847054";
        for(int i = 0 ; i < 1000000; i++) {
            list.add(orderGenerator.generateOrder(userId));
        }
        System.out.println(list.size());
        System.out.println(Collections.max(list, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        }));
    }

    @Test
    public void testPool() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(100000);
        String userId = "100847054";
        StringBuffer orders = new StringBuffer();
        for(int i = 0; i < 100; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < 1000; j++) {
                        orders.append(orderGenerator.generateOrder(userId).concat(","));
                        latch.countDown();
                    }
                }
            });
            latch.countDown();
        }
        
        latch.await();
        Thread.sleep(3000);
        orders.deleteCharAt(orders.length() - 1);
        String[] a = orders.toString().split(",");
        System.out.println(a.length);
        
        List<String> list = Arrays.asList(a);
        System.out.println(Collections.max(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        }));
        
        Set<String> set = new HashSet<>(a.length);
        for(String e : a) {
            set.add(e);
        }
        System.out.println(set.size());
    }
    @Test
    public void testPool2() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(100000);
        StringBuffer orders = new StringBuffer();
        for(int i = 0; i < 100; i++) {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < 1000; j++) {
                        orders.append(orderGenerator.generateOrderBySnow().concat(","));
                        latch.countDown();
                    }
                }
            });
            latch.countDown();
        }
        
        latch.await();
        Thread.sleep(3000);
        orders.deleteCharAt(orders.length() - 1);
        String[] a = orders.toString().split(",");
        System.out.println(a.length);
        
        List<String> list = Arrays.asList(a);
        System.out.println(Collections.max(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        }));
        
        Set<String> set = new HashSet<>(a.length);
        for(String e : a) {
            set.add(e);
        }
        System.out.println(set.size());
    }
    
}

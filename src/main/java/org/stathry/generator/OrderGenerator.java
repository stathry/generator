package org.stathry.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.stathry.generator.util.BoundAtomicInteger;
import org.stathry.generator.util.Snowflake;

/**
 * TODO
 */
@Component
public class OrderGenerator implements InitializingBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderGenerator.class);
    
    @Value("${commons.gen.machineCode}")
    private String machineCode;
    @Value("${commons.gen.incr.length}")
    private int incrLen;
    @Value("${commons.gen.user.length}")
    private int userLen;
    
    private int maxIncr;
    private BoundAtomicInteger seq;
    
    private Snowflake snow = new Snowflake(1, 1);
    
    /**
     * 生成订单号：date + machineCode + seq + userId
     * @param userId
     * @return
     */
    public String generateOrder(String userId) {
        if(StringUtils.isBlank(userId) || userId.trim().length() < userLen) {
            LOGGER.warn("invalid userId {}, length < {}", userId, userLen);
            return "";
        }
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        int i = userId.trim().length() - userLen; 
        StringBuilder order = new StringBuilder();
        return order.append(date).append(machineCode).append(incrmentSequence())
                .append(userId.trim().substring(i)).toString();
    }
    
    public String incrmentSequence() {
        int value = seq.incrementWithBound();
        return StringUtils.leftPad(String.valueOf(value), incrLen, "0");
    }
    
    public String generateOrderBySnow() {
        return String.valueOf(snow.nextId());
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        maxIncr = (int)(Math.pow(10, incrLen));
        maxIncr -= 1000;
        seq = new BoundAtomicInteger(0, maxIncr);
    } 
    
}

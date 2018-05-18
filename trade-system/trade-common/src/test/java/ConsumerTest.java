import com.pan.trade.common.rocketmq.PanProducer;
import com.pan.trade.common.rocketmq.PanPushConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by Loren on 2018/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rocktmq-consumer.xml")
public class ConsumerTest {

    @Autowired
    private PanPushConsumer consumer;

    @Test
    public void test() throws IOException, InterruptedException {
        consumer.init();
        Thread.sleep(10000);

    }

}

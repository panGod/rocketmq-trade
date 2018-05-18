import com.pan.trade.common.rocketmq.PanProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Loren on 2018/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rocktmq-producer.xml")
public class ProducerTest {

    @Autowired
    private PanProducer panProducer;

    @Test
    public void test(){
        this.panProducer.init();
        panProducer.send("Pan-test","order","123","this is a test message");
    }

}

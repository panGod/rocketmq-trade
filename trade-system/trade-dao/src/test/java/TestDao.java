import com.pan.bean.TradeUser;
import com.pan.dao.TradeUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Loren on 2018/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dao.xml")
public class TestDao {

    @Autowired
    private TradeUserMapper tradeUserMapper;

    @Test
    public void  test(){
        TradeUser tradeUser = new TradeUser();
        tradeUser.setUserName("zhangsan");
        tradeUser.setUserMobile("13714023255");
        tradeUser.setUserPassword("123456");
        tradeUser.setUserScore(100);
        tradeUser.setUserRegTime(new Date());
        tradeUser.setUserMoney(new BigDecimal(0.00));
        int insert = tradeUserMapper.insert(tradeUser);
        System.out.println(insert);
    }

}

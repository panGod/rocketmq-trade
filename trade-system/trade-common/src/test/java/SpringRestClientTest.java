import com.pan.trade.common.api.IUserApi;
import com.pan.trade.common.protocol.user.QueryUserReq;
import com.pan.trade.common.protocol.user.QueryUserRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Loren on 2018/5/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rest-client.xml")
public class SpringRestClientTest {

    @Autowired
    private IUserApi userApi;

    @Test
    public void test(){
        QueryUserReq queryUserReq
                 = new QueryUserReq();
        queryUserReq.setUserId(1);
        QueryUserRes queryUserRes = userApi.queryUserById(queryUserReq);
        System.out.println(queryUserRes);
    }

}

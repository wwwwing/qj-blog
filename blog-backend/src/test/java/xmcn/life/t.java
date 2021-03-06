package xmcn.life;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xcmn.life.config.RedisService;

import java.sql.SQLException;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogBackendApplication.class)
public class t {

    @Autowired
    private DruidDataSource druidDataSource;
    @Autowired
    private RedisService redisService;


    @Test
    public void dbTest() throws SQLException {
        DruidPooledConnection connection = druidDataSource.getConnection();
        System.out.println("conning");
        System.out.println(connection != null);
        System.out.println(connection.getClass());
        assert connection != null;
        connection.close();
    }

    @Test
    public void redisTest(){
        Set<String> keys = redisService.getTemplate().keys("*");
        System.out.println(keys);
    }

}

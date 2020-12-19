package com.iemij.test.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource hikariDataSource() throws Exception {
        // 参考http://blog.csdn.net/clementad/article/details/46928621
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
        dataSource.setConnectionTimeout(3 * 1000);//3sec
        //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
        dataSource.setIdleTimeout(10 * 60 * 1000);//10min
        //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，
        // 参考MySQL wait_timeout参数（show variables like '%timeout%';）
        dataSource.setMaxLifetime((28800 - 30) * 1000);//wait_timeout:28800sec,即8hours
        //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
        dataSource.setMaximumPoolSize(100);
        dataSource.setMinimumIdle(5);
        return dataSource;
    }
}

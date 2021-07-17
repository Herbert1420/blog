package com.layne.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//扫描mapper文件夹

@EnableTransactionManagement
@Configuration
public class MybatisConfig {
    //mybatis配置类
    //注册乐观锁插件
//    @Bean
//    public OptimisticLockerInterceptor optimisticLockerInnerInterceptor(){
//        return new OptimisticLockerInterceptor();
//    }
//
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
//
//    @Bean
//    public ISqlInjector iSqlInjector(){
//        return new LogicSqlInjector();
//    }
//
//    @Bean
//    @Profile({"dev","test"})//设置dev test环境开启,保证效率(测试环境和开发环境开启)
//    public PerformanceInterceptor performanceInterceptor(){
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        performanceInterceptor.setMaxTime(100);//sql执行的最大时间
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }
}

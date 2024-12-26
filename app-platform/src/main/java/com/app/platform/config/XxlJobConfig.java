package com.app.platform.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class XxlJobConfig {

    @Value("${app.admin.addresses}")
    String adminAddresses;
    @Value("${app.executor.appname}")
    String appname;
    @Value("${app.accessToken}")
    String accessToken;

    @Value("${app.executor.ip}")
    String ip;
    @Value("${app.executor.port}")
    Integer port;
    @Value("${app.executor.logpath}")
    String logpath;
    @Value("${app.executor.logretentiondays}")
    Integer logretentiondays;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logpath);
        xxlJobSpringExecutor.setLogRetentionDays(logretentiondays);

        return xxlJobSpringExecutor;
    }
}

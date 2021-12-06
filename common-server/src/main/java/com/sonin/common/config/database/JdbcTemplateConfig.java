package com.sonin.common.config.database;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.sonin.common.tool.util.CustomApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/12/6 14:58
 */
@Lazy(false)
@ConditionalOnClass({DataSourceConfig.class})
@AutoConfigureOrder(101)
public class JdbcTemplateConfig {

    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateConfig.class);

    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    public JdbcTemplateConfig() {
    }

    @Bean(name = {"initJdbcTemplate"})
    public void initJdbcTemplate() {
        logger.info(">>> JdbcTemplate实例化开始 <<<");
        Map<String, DataSource> dataSourceMap = this.dynamicRoutingDataSource.getCurrentDataSources();
        for (String key : dataSourceMap.keySet()) {
            CustomApplicationContext.setJdbcTemplateBean(key, JdbcTemplate.class, this.dynamicRoutingDataSource.getDataSource(key));
            logger.info(">>> " + key + " JdbcTemplateBean实例化完成 <<<");
        }
        logger.info(">>> JdbcTemplateBean实例化结束 <<<");
    }

}

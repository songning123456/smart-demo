package com.sonin.common.config.mybatis;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.sonin.common.config.yaml.YamlPropUtils;

/**
 * @author sonin
 * @date 2021/10/17 9:41
 * 自动生成mybatis-plus的相关代码
 */
public class GeneratorCodeConfig {

    public static void main(String[] args) throws Exception {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = getGlobalConfig();
        autoGenerator.setGlobalConfig(globalConfig);
        // 数据源配置
        DataSourceConfig dataSourceConfig = getDataSourceConfig();
        autoGenerator.setDataSource(dataSourceConfig);
        // 包配置
        PackageConfig packageConfig = getPackageConfig();
        autoGenerator.setPackageInfo(packageConfig);
        // 策略配置
        StrategyConfig strategyConfig = getStrategyConfig();
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.execute();
    }

    /**
     * 获取全局配置
     *
     * @return
     */
    private static GlobalConfig getGlobalConfig() throws Exception {
        Object author = YamlPropUtils.getInstance().getProperty("generate-code.global-config.author");
        if (author == null || "".equals(author)) {
            author = "sonin";
        }
        Object outputDir = YamlPropUtils.getInstance().getProperty("generate-code.global-config.output-dir");
        if (outputDir == null || "".equals(outputDir)) {
            throw new Exception("generate-code.global-config.output-dir为必填项");
        }
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setActiveRecord(false) // 是否使用AR模式
                .setAuthor(author.toString()) // 类作者名称
                .setOutputDir(outputDir.toString()) // 代码文件生成路径
                .setFileOverride(true) // 文件是否覆盖
                // .setIdType(IdType.NONE) // 主键策略
                .setServiceName("I%sService") // 设置生成的service接口的名字的首字母是否为I
                .setServiceImplName("%sServiceImpl") // 设置Service实现类名称格式
                .setMapperName("%sMapper") // 设置Mapper类的名称格式
                .setControllerName("%sController") // 设置Controller类的名称格式
                .setEntityName("%s") // 设置Entity类的名称格式
                .setOpen(false) // 生成文件后是否打开文件
                .setBaseResultMap(true) // 在mapper.xml文件中生成基本的resultMap
                .setBaseColumnList(true); // 在mapper.xml文件中生成基本的SQL片段
        return globalConfig;
    }

    /**
     * 数据源配置
     *
     * @return
     */
    private static DataSourceConfig getDataSourceConfig() throws Exception {
        Object driverName = YamlPropUtils.getInstance().getProperty("generate-code.datasource.driver-name");
        if (driverName == null || "".equals(driverName)) {
            throw new Exception("generate-code.datasource.driver-name为必填项");
        }
        Object url = YamlPropUtils.getInstance().getProperty("generate-code.datasource.url");
        if (url == null || "".equals(url)) {
            throw new Exception("generate-code.datasource.url为必填项");
        }
        Object username = YamlPropUtils.getInstance().getProperty("generate-code.datasource.username");
        if (username == null || "".equals(username)) {
            throw new Exception("generate-code.datasource.username为必填项");
        }
        Object password = YamlPropUtils.getInstance().getProperty("generate-code.datasource.password");
        if (password == null || "".equals(password)) {
            throw new Exception("generate-code.datasource.password为必填项");
        }
        Object schemaName = YamlPropUtils.getInstance().getProperty("generate-code.datasource.schemaName");
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDriverName(driverName.toString()) // 数据库驱动名称
                .setUrl(url.toString())// 数据库连接url
                .setUsername(username.toString()) // 数据库用户
                .setPassword(password.toString()); // 数据库密码
        if (schemaName != null && !"".equals(schemaName)) {
            dataSourceConfig.setSchemaName(schemaName.toString()); // 数据库表空间名称
        }
        return dataSourceConfig;
    }

    /**
     * 包名策略配置
     *
     * @return
     */
    private static PackageConfig getPackageConfig() throws Exception {
        Object parent = YamlPropUtils.getInstance().getProperty("generate-code.package-config.parent");
        if (parent == null || "".equals(parent)) {
            throw new Exception("generate-code.package-config.parent为必填项");
        }
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parent.toString()) // 父包
                .setMapper("mapper") // mapper文件的包名
                .setService("service") // service文件的包名
                .setServiceImpl("service.impl") // serviceImpl文件的包名
                .setController("controller") // controller文件的包名
                .setEntity("entity") // entity文件的包名
                .setXml("mapper.xml"); // mapper.xml文件存放的位置
        return packageConfig;
    }

    /**
     * 设置策略
     *
     * @return
     */
    private static StrategyConfig getStrategyConfig() throws Exception {
        Object include = YamlPropUtils.getInstance().getProperty("generate-code.strategy-config.include");
        if (include == null || "".equals(include)) {
            throw new Exception("generate-code.strategy-config.include为必填项");
        }
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setInclude(include.toString().split(","));
        strategyConfig.setControllerMappingHyphenStyle(true);
        return strategyConfig;
    }


}

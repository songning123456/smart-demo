package com.sonin.common.config.mybatis;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Scanner;

/**
 * @author sonin
 * @date 2021/10/17 9:41
 * 自动生成mybatis-plus的相关代码
 */
public class GeneratorCodeConfig {

    private static String scanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入表名,多个英文逗号','分割: ");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的表名,多个英文逗号分割!");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("sonin");
        globalConfig.setOpen(false);
        // 实体属性Swagger2注解
        globalConfig.setSwagger2(false);
        autoGenerator.setGlobalConfig(globalConfig);
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://192.168.2.110:3306/smart-demo?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        autoGenerator.setDataSource(dataSourceConfig);
        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.sonin.common");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        autoGenerator.setPackageInfo(packageConfig);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setInclude(scanner().split(","));
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    /**
     * 获取全局配置
     *
     * @return
     */
    private static GlobalConfig getGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setActiveRecord(false) // 是否使用AR模式
                .setAuthor("sonin") // 类作者名称
                .setOutputDir("") // 代码文件生成路径
                .setFileOverride(true) // 文件是否覆盖
                // .setIdType(IdType.NONE) // 主键策略
                .setServiceName("%sService") // 设置生成的service接口的名字的首字母是否为I
                .setServiceImplName("%sServiceImpl") // 设置Service实现类名称格式
                .setMapperName("%sMapper") // 设置Mapper类的名称格式
                .setControllerName("%sController") // 设置Controller类的名称格式
                .setEntityName("%sEntity") // 设置Entity类的名称格式
                .setOpen(false) // 生成文件后是否打开文件
                .setBaseResultMap(true)// 在mapper.xml文件中生成基本的resultMap
                .setBaseColumnList(true);// 在mapper.xml文件中生成基本的SQL片段
        return globalConfig;
    }

}

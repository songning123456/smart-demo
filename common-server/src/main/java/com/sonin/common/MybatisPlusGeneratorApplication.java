package com.sonin.common;

import com.sonin.common.config.mybatis.GeneratorCodeConfig;

/**
 * @author sonin
 * @date 2021/10/17 12:03
 */
public class MybatisPlusGeneratorApplication {

    public static void main(String[] args) {
        try {
            GeneratorCodeConfig generatorCodeConfig = new GeneratorCodeConfig();
            generatorCodeConfig.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

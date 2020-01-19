package com.weiyun.cn.autogenerator.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Data;

import java.util.List;

@Data
public class MybatisPlusAutogeneratorParams {

    private String moduleName = "DefaultProject";

    private String outputDir = System.getProperty("user.dir")+"/DefaultProject";

    private String author = "Author";

    private boolean opnenDir = Boolean.FALSE;

    private boolean fileOverWrite = Boolean.TRUE;

    private IdType idType = IdType.AUTO;

    private DateType dateType = DateType.ONLY_DATE;

    private boolean swagger2 = Boolean.FALSE;

    private String jdbcConnectionUrl;

    private String jdbcConnectionDriverName;

    private String jdbcConnectionUserName;

    private String jdbcConnectionPassword;

    private DbType dbType = DbType.MYSQL;

    private String javaMaintPackage;

    private String javaClientGeneratorController;

    private String javaModelGeneratorEntity;

    private String javaModelGeneratorService;

    private String javaModelGeneratorMapper;

    private List<TableInstall> tableInstall;

}

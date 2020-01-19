package com.weiyun.cn.autogenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.weiyun.cn.autogenerator.entity.MybatisPlusAutogeneratorParams;

import java.util.ArrayList;

public class Job {

    public static void excuteJob(MybatisPlusAutogeneratorParams mp,int index,int count){
        //数据库名称 icoding_mall
        //模块名 mall
        //
        String moduleName = mp.getModuleName();
        // 1、创建代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();
        // 2、全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(mp.getOutputDir());
        globalConfig.setAuthor(mp.getAuthor());
        //生成后是否打开资源管理器
        if (mp.isOpnenDir()){
            if((index+1)==count){
                globalConfig.setOpen(mp.isOpnenDir());
            }
            globalConfig.setOpen(false);
        }else {
            globalConfig.setOpen(false);
        }
        //重新生成时文件是否覆盖
        globalConfig.setFileOverride(mp.isFileOverWrite());
        //去掉Service接口的首字母I
        globalConfig.setServiceName("%sService");
        //主键策略
        globalConfig.setIdType(mp.getIdType());
        //定义生成的实体类中日期类型
        globalConfig.setDateType(mp.getDateType());
        //开启Swagger2模式
        globalConfig.setSwagger2(mp.isSwagger2());
        autoGenerator.setGlobalConfig(globalConfig);
        // 3、数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(mp.getJdbcConnectionUrl());
        dataSourceConfig.setDriverName(mp.getJdbcConnectionDriverName());
        dataSourceConfig.setUsername(mp.getJdbcConnectionUserName());
        dataSourceConfig.setPassword(mp.getJdbcConnectionPassword());
        dataSourceConfig.setDbType(mp.getDbType());
        autoGenerator.setDataSource(dataSourceConfig);
        // 4、包配置
        PackageConfig packageConfig = new PackageConfig();
        //模块名
        packageConfig.setModuleName(moduleName);
        packageConfig.setParent(mp.getJavaMaintPackage());
        packageConfig.setController(mp.getJavaClientGeneratorController());
        packageConfig.setEntity(mp.getJavaModelGeneratorEntity());
        packageConfig.setService(mp.getJavaModelGeneratorService());
        packageConfig.setMapper(mp.getJavaModelGeneratorMapper());
        autoGenerator.setPackageInfo(packageConfig);
        // 5、策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //设置要映射的表名
        strategyConfig.setInclude(mp.getTableInstall().get(index).getTableName());
        //数据库表映射到实体的命名策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        //设置表前缀不生成
        strategyConfig.setTablePrefix(mp.getTableInstall().get(index).getTablePrifix());
        //数据库表字段映射到实体的命名策略
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain= true) setter链式操作
        strategyConfig.setEntityLombokModel(true);
        //逻辑删除字段名
        strategyConfig.setLogicDeleteFieldName(mp.getTableInstall().get(index).getDeleted());
        //去掉布尔值的is_前缀
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        //自动填充
        TableFill gmtCreate = new TableFill("create_time", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<TableFill>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategyConfig.setTableFillList(tableFills);
        strategyConfig.setVersionFieldName(mp.getTableInstall().get(index).getVersionFieldName());//乐观锁列
        strategyConfig.setRestControllerStyle(true); //restful api风格控制器
        strategyConfig.setControllerMappingHyphenStyle(mp.getTableInstall().get(index).getControllerMappingHyphenStyle()); //url中驼峰转连字符
        autoGenerator.setStrategy(strategyConfig);
        // 6、执行
        autoGenerator.execute();
    }
}

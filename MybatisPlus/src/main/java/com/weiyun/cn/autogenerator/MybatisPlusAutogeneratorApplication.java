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
import com.weiyun.cn.autogenerator.config.MybatisParseXMLConfig;
import com.weiyun.cn.autogenerator.entity.MybatisPlusAutogeneratorParams;
import org.dom4j.DocumentException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MybatisPlusAutogeneratorApplication {

	public static void main(String[] args) {
		List<String> list = Arrays.asList(args);
		long count = list.stream().filter(str -> str.indexOf("-configfile") == 0).count();
		if (count <= 0 && (list.size()<= 0 || list.size() > 2)){
			System.out.println("配置参数有误。。。。。。");
			return;
		}
		list.forEach(System.out::println);
		String filePath = "";
		if (list.get(0).equals("-configfile")){
			filePath = list.get(1);
		};
		MybatisParseXMLConfig instance = null;
		try {
			instance = MybatisParseXMLConfig.getInstance(filePath);
		}catch (DocumentException e){
			System.out.println("配置参数有误,请检查配置文件路径");
			return;
		}
		MybatisPlusAutogeneratorParams mp = null;
		try {
			mp = instance.getMybatisPlusAutogenerator();
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("配置文件解析失败");
			return;
		}
		if (mp.getTableInstall().size() > 0){
			for (int i = 0; i < mp.getTableInstall().size(); i++) {
				Job.excuteJob(mp,i,mp.getTableInstall().size());
			}
		}
	}
}

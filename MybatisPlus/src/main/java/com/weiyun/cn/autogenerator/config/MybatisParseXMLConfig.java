package com.weiyun.cn.autogenerator.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.weiyun.cn.autogenerator.entity.MybatisPlusAutogeneratorParams;
import com.weiyun.cn.autogenerator.entity.TableInstall;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MybatisParseXMLConfig {

    private MybatisPlusAutogeneratorParams mybatisPlusAutogeneratorParams = new MybatisPlusAutogeneratorParams();

    private SAXReader reader = new SAXReader();

    private static MybatisParseXMLConfig instance = new MybatisParseXMLConfig();

    private Document document;

    private MybatisParseXMLConfig(){}

    public static MybatisParseXMLConfig getInstance (String systemId) throws DocumentException {
       instance.document = instance.reader.read(systemId);
        return instance;
    }

    public MybatisPlusAutogeneratorParams getMybatisPlusAutogenerator(){
        List<Element> elements = document.getRootElement().elements();
        //遍历第一行，获取一级标签
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String elementName = element.getName();
            switch (elementName){
                case "moduleName":parseModuleName(element); break;
                case "outputDir":parseOutputDir(element);break;
                case "author":parseAuthor(element);break;
                case "opnenDir":parse0pnenDir(element);break;
                case "fileOverWrite":parseFileOverWrite(element);break;
                case "idType":parseIdType(element);break;
                case "dateType":parseDateType(element);break;
                case "swagger2":parseSwagger2(element);break;
                case "jdbcConnection":parseJdbcConnection(element);break;
                case "dbType":parseDbType(element);break;
                case "javaMaintPackage":parseJavaMaintPackage(element);break;
                case "javaClientGeneratorController":parseJavaClientGeneratorController(element);break;
                case "javaModelGeneratorEntity":parseJavaModelGeneratorEntity(element);break;
                case "javaModelGeneratorService":parseJavaModelGeneratorService(element);break;
                case "javaModelGeneratorMapper":parseJavaModelGeneratorMapper(element);break;
                case "tableInstall":parseTableInstall(element);
            }
        }
        return instance.mybatisPlusAutogeneratorParams;
    }

    private void parseTableInstall(Element element){
        List<Element> elements = element.elements();
        List<TableInstall> list = new ArrayList<TableInstall>();
        for (int i = 0; i < elements.size(); i++) {
            TableInstall tableInstall = parseTable(elements.get(i));
            if (tableInstall!= null){
                list.add(tableInstall);
            }
        }
        mybatisPlusAutogeneratorParams.setTableInstall(list);
    }

    private TableInstall parseTable(Element element){
        TableInstall tableInstall = new TableInstall();
        List<Element> elements = element.elements();
        for ( int i = 0; i < elements.size(); i++){
            //获取table标签
            Element table = elements.get(i);
            //获取每个标签的值
            switch (table.getName()){
                case "tableName":tableInstall.setTableName(Optional.ofNullable(parseTableSomeInfo(table,"Xml中tableName不能为空")).orElse("")); break;
                case "domainObjectName" : tableInstall.setDomainObjectName(Optional.ofNullable(parseTableSomeInfo(table,"Xml中domainObjectName不能为空")).orElse("")); break;
                case "tablePrifix" : tableInstall.setTablePrifix(Optional.ofNullable(parseTableSomeInfo(table)).orElse("")); break;
                case "deleted" : tableInstall.setDeleted(Optional.ofNullable(parseTableSomeInfo(table)).orElse("")); break;
                case "tableAutoFill" : tableInstall.setTableAutoFill(Optional.ofNullable(parseAutoFill(table)).orElse(null)); break;
                case "versionFieldName" : tableInstall.setVersionFieldName(Optional.ofNullable(parseTableSomeInfo(table)).orElse("")); break;
                case "controllerMappingHyphenStyle": tableInstall.setControllerMappingHyphenStyle(parseControllerMappingHyphenStyle(table)); break;
            }
        }
        return tableInstall;
    }

    private boolean parseControllerMappingHyphenStyle(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "") && names.get(0).getValue().equals("false")) {
            return false;
        }
        return true;
    }

    private List<String> parseAutoFill(Element element){
        List<Attribute> names = element.attributes();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i).getName();
            String value = names.get(i).getValue();
            if(name.equals("create_time_value") ){
                if(value != null || value != null){
                    list.add(value);
                }
            }else if (name.equals("update_time_value")){
                if (value != null || value != null){
                    list.add(value);
                }
            }
        }
        return list.size()>0 ? list:null;
    }

    private String parseUpdateTime(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("update_time_value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "")) {
            return names.get(0).getValue();
        }
        return "update_time";
    }

    private String parseTableSomeInfo(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "")) {
            return names.get(0).getValue();
        }
        return null;
    }

    private String parseTableSomeInfo(Element element,String message){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "")) {
            return names.get(0).getValue();
        }
        throw new RuntimeException(message);
    }

    private void parseJdbcConnection(Element element){
        List<Element> elements = element.elements();
        for (int i = 0; i < elements.size() ; i++) {
            Element el = elements.get(i);
            String name = el.getName();
            switch (name){
                case "driverClass" :parseJdbcConnectionDriverName(el);
                case "url":parseJdbcConnectionUrl(el);
                case "username":parseJdbcConnectionUserName(el);
                case "password":parseJdbcConnectionPassword(el);
            }
        }
    }

    private void parseJdbcConnectionPassword(Element element){
        List<Attribute> value = element.attributes().stream().filter(e -> e.getName().equals("value")).collect(Collectors.toList());
        if (value.size()>0 && (value.get(0).getValue() != null || value.get(0).getValue() != "")){
            instance.mybatisPlusAutogeneratorParams.setJdbcConnectionPassword(value.get(0).getValue());
        }
    }

    private void parseJdbcConnectionUserName(Element element){
        List<Attribute> value = element.attributes().stream().filter(e -> e.getName().equals("value")).collect(Collectors.toList());
        if (value.size()>0 && (value.get(0).getValue() != null || value.get(0).getValue() != "")){
            instance.mybatisPlusAutogeneratorParams.setJdbcConnectionUserName(value.get(0).getValue());
        }
    }

    private void parseJdbcConnectionDriverName(Element element){
        List<Attribute> value = element.attributes().stream().filter(e -> e.getName().equals("value")).collect(Collectors.toList());
        if (value.size()>0 && (value.get(0).getValue() != null || value.get(0).getValue() != "")){
            instance.mybatisPlusAutogeneratorParams.setJdbcConnectionDriverName(value.get(0).getValue());
        }
    }

    private void parseJdbcConnectionUrl(Element element){
        List<Attribute> value = element.attributes().stream().filter(e -> e.getName().equals("value")).collect(Collectors.toList());
        if (value.size()>0 && (value.get(0).getValue() != null || value.get(0).getValue() != "")){
            instance.mybatisPlusAutogeneratorParams.setJdbcConnectionUrl(value.get(0).getValue());
        }
    }

    private void parseJavaModelGeneratorMapper(Element element){
        List<Attribute> names = element.attributes();
        String value = "";
        String defaultValue = "";
        for (int i = 0; i < names.size(); i++) {
            Attribute attribute = names.get(i);
            if (attribute.getName() == "value" && (attribute.getValue() != null || attribute.getValue() != "")) {
                value = attribute.getValue();
            }
            if (attribute.getName() == "defaultValue" && (attribute.getValue() != null || attribute.getValue() != "")) {
                defaultValue = attribute.getValue();
            }
            if (!value.equals("")) {
                instance.mybatisPlusAutogeneratorParams.setJavaModelGeneratorMapper(value);
            } else if (defaultValue != "") {
                instance.mybatisPlusAutogeneratorParams.setJavaModelGeneratorMapper(defaultValue);
            }
        }
    }

    private void parseJavaModelGeneratorService(Element element){
        List<Attribute> names = element.attributes();
        String value = "";
        String defaultValue = "";
        for (int i = 0; i < names.size(); i++) {
            Attribute attribute = names.get(i);
            if (attribute.getName() == "value" && (attribute.getValue() != null || attribute.getValue() != "")) {
                value = attribute.getValue();
            }
            if (attribute.getName() == "defaultValue" && (attribute.getValue() != null || attribute.getValue() != "")) {
                defaultValue = attribute.getValue();
            }
            if (!value.equals("")) {
                instance.mybatisPlusAutogeneratorParams.setJavaModelGeneratorService(value);
            } else if (defaultValue != "") {
                instance.mybatisPlusAutogeneratorParams.setJavaModelGeneratorService(defaultValue);
            }
        }
    }

    private void parseJavaModelGeneratorEntity(Element element){

        List<Attribute> names = element.attributes();
        String value = "";
        String defaultValue = "";
        for (int i = 0; i < names.size(); i++) {
            Attribute attribute = names.get(i);
            if (attribute.getName() == "value" && (attribute.getValue() != null || attribute.getValue() != "")) {
                value = attribute.getValue();
            }
            if (attribute.getName() == "defaultValue" && (attribute.getValue() != null || attribute.getValue() != "")) {
                defaultValue = attribute.getValue();
            }
            if (!value.equals("")) {
                instance.mybatisPlusAutogeneratorParams.setJavaModelGeneratorEntity(value);
            } else if (defaultValue != "") {
                instance.mybatisPlusAutogeneratorParams.setJavaModelGeneratorEntity(defaultValue);
            }
        }
    }

    private void parseJavaClientGeneratorController(Element element){
        List<Attribute> names = element.attributes();
        String value = "";
        String defaultValue = "";
        for (int i = 0; i < names.size(); i++) {
            Attribute attribute = names.get(i);
            if (attribute.getName() == "value" && (attribute.getValue() != null || attribute.getValue() != "")){
                value = attribute.getValue();
            }
            if (attribute.getName() == "defaultValue" && (attribute.getValue() != null || attribute.getValue() != "")){
                defaultValue = attribute.getValue();
            }
            if(!value.equals("")){
                instance.mybatisPlusAutogeneratorParams.setJavaClientGeneratorController(value);
            }else if(defaultValue != ""){
                instance.mybatisPlusAutogeneratorParams.setJavaClientGeneratorController(defaultValue);
            }

        }
    }

    private void parseJavaMaintPackage(Element element){
        List<Attribute> names = element.attributes();
        String value = "";
        String defaultValue = "";
        for (int i = 0; i < names.size(); i++) {
            Attribute attribute = names.get(i);
            if (attribute.getName() == "value" && (attribute.getValue() != null || attribute.getValue() != "")) {
                value = attribute.getValue();
            }
            if (attribute.getName() == "defaultValue" && (attribute.getValue() != null || attribute.getValue() != "")) {
                defaultValue = attribute.getValue();
            }
            if (!value.equals("")) {
                instance.mybatisPlusAutogeneratorParams.setJavaMaintPackage(value);
            } else if (defaultValue != "") {
                instance.mybatisPlusAutogeneratorParams.setJavaMaintPackage(defaultValue);
            }
        }
    }

    private void parseDbType(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "")) {
            instance.mybatisPlusAutogeneratorParams.setDbType(DbType.MYSQL);
        }
    }

    private void parseSwagger2(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "") && names.get(0).getValue().equals("true")) {
            instance.mybatisPlusAutogeneratorParams.setSwagger2(Boolean.TRUE);
        }
    }

    private void parseDateType(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "") && names.get(0).getValue().equals("SQL_PACK")) {
            instance.mybatisPlusAutogeneratorParams.setDateType(DateType.SQL_PACK);
        }
    }

    private void parseIdType(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "") && (names.get(0).getValue().equals("UUID") || names.get(0).getValue().equals("INPUT"))) {
            switch (names.get(0).getValue()){
                case "UUID":    instance.mybatisPlusAutogeneratorParams.setIdType(IdType.UUID);
                case "INPUT" : instance.mybatisPlusAutogeneratorParams.setIdType(IdType.INPUT);
                default:instance.mybatisPlusAutogeneratorParams.setIdType(IdType.AUTO);
            }
        }
    }

    private void parseFileOverWrite(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "") && names.get(0).getValue().equals("false")) {
            instance.mybatisPlusAutogeneratorParams.setFileOverWrite(Boolean.FALSE);
        }
    }

    private void parse0pnenDir(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "") && names.get(0).getValue().equals("true")) {
            instance.mybatisPlusAutogeneratorParams.setOpnenDir(Boolean.TRUE);
        }
    }

    private void parseAuthor(Element element){
        List<Attribute> names = element.attributes().stream().filter(attribute -> attribute.getName().equals("name")).collect(Collectors.toList());
        if (names.size() > 0 && (names.get(0).getValue() != null || names.get(0).getValue() != "")) {
            instance.mybatisPlusAutogeneratorParams.setAuthor(names.get(0).getValue());
        }
    }

    private void parseOutputDir(Element element){
        List<Attribute> locations = element.attributes().stream().filter(attribute -> attribute.getName().equals("location")).collect(Collectors.toList());
        if (locations.size() > 0){
            instance.mybatisPlusAutogeneratorParams.setOutputDir(locations.get(0).getValue());
        }
    }

    private void parseModuleName(Element element){
        List<Element> property = element.elements().stream().filter(element1 -> element1.getName().equals("property")).collect(Collectors.toList());
        if (property.size() > 0 ){
            Element element0 = property.get(0);
            List<Attribute> value = element0.attributes().stream().filter(attribute -> attribute.getName().equals("value")).collect(Collectors.toList());
            if (value.size() > 0 && (value.get(0).getValue() != null|| value.get(0).getValue() != "" ) ){
                instance.mybatisPlusAutogeneratorParams.setModuleName(value.get(0).getValue());
            }
        }
    }

    public static void main(String[] args) throws DocumentException {
        MybatisParseXMLConfig instance = MybatisParseXMLConfig.getInstance("H:\\UsefulData\\mybatis-plus\\generatorConfig-mysql.xml");
        MybatisPlusAutogeneratorParams mp = instance.getMybatisPlusAutogenerator();
        System.out.println(instance.mybatisPlusAutogeneratorParams);
    }
}

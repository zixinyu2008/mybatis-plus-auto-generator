package com.weiyun.cn.autogenerator.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableInstall {

    private String tableName;

    private String tablePrifix;

    private String deleted;

    private List<String> tableAutoFill = new ArrayList<String>();

    private String versionFieldName;

    private Boolean controllerMappingHyphenStyle;

    private String domainObjectName;

}

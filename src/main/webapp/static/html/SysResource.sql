CREATE TABLE `sys_resource` (
    `id` bigint(10) NULL AUTO_INCREMENT COMMENT '主键',
    `pid` bigint(10) NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(20) NOT NULL COMMENT '资源名称',
    `code` VARCHAR(20) NOT NULL COMMENT '资源代码',
    `type` varchar(20) NOT NULL COMMENT '资源分类',
    `url` varchar(255) NULL COMMENT '资源对应URL',
    `order` int(11) NULL COMMENT '排序id',
    `status` int(1) NOT NULL COMMENT '状态',
    `remark` varchar(20) NULL COMMENT '备注',
    `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源配置';


CREATE TABLE `sys_resource` (
 `id` bigint(10)   AUTO_INCREMENT  COMMENT '主键',
 `pid` bigint(10)   AUTO_INCREMENT  COMMENT '主键',
 `name` VARCHAR(20) NOT NULL   COMMENT '资源名称',
 `code` VARCHAR(20) NOT NULL   COMMENT '资源代码',
 `type` varchar(20) NOT NULL   COMMENT '资源分类',
 `url` varchar(255)    COMMENT '资源对应URL',
 `order` int(11)    COMMENT '排序id',
 `status` int(1) NOT NULL   COMMENT '状态',
 `remark` varchar(20)    COMMENT '备注',
 `createtime` timestamp   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源配置';


CREATE TABLE `sys_role` (
    `id` bigint(10) NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(20) NOT NULL COMMENT '角色名',
    `code` VARCHAR(20) NOT NULL COMMENT '角色代码',
    `order` int(11) NOT NULL COMMENT '排序',
    `status` int(1) NOT NULL COMMENT '状态',
    `remark` varchar(255) NULL COMMENT '备注',
    `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';


CREATE TABLE `sys_role` (
 `id` bigint(10)   AUTO_INCREMENT  COMMENT '主键',
 `name` VARCHAR(20) NOT NULL   COMMENT '角色名',
 `code` VARCHAR(20) NOT NULL   COMMENT '角色代码',
 `order` int(11) NOT NULL   COMMENT '排序',
 `status` int(1) NOT NULL   COMMENT '状态',
 `remark` varchar(255)    COMMENT '备注',
 `createtime` timestamp   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';


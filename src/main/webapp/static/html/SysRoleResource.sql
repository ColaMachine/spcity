CREATE TABLE `sys_role_resource` (
    `id` bigint(15) NULL AUTO_INCREMENT COMMENT '主键',
    `roleid` bigint(15) NOT NULL COMMENT '角色id',
    `rid` bigint(15) NOT NULL COMMENT '资源id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关系';


CREATE TABLE `sys_role_resource` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '主键',
 `roleid` bigint(15) NOT NULL   COMMENT '角色id',
 `rid` bigint(15) NOT NULL   COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源关系';


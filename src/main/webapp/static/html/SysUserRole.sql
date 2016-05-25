CREATE TABLE `sys_user_role` (
    `id` bigint(15) NULL AUTO_INCREMENT COMMENT '主键',
    `uid` bigint(15) NOT NULL COMMENT '用户id',
    `roleid` bigint(15) NOT NULL COMMENT '角色id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系';


CREATE TABLE `sys_user_role` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '主键',
 `uid` bigint(15) NOT NULL   COMMENT '用户id',
 `roleid` bigint(15) NOT NULL   COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系';


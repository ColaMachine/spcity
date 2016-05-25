CREATE TABLE `sys_user` (
    `id` bigint(15) NULL AUTO_INCREMENT COMMENT '主键',
    `username` VARCHAR(20) NOT NULL COMMENT '用户名',
    `password` VARCHAR(50) NOT NULL COMMENT '密码',
    `nkname` VARCHAR(20) NULL COMMENT '昵称',
    `type` int(4) NULL DEFAULT 0 COMMENT '类型',
    `status` int(1) NOT NULL DEFAULT 0 COMMENT '状态',
    `email` varchar(50) NULL COMMENT '邮箱地址',
    `telno` varchar(11) NULL COMMENT '手机号码',
    `idcard` varchar(18) NULL COMMENT '身份证号码',
    `sex` int(1) NULL DEFAULT 0 COMMENT '身份证号码',
    `birth` date NULL COMMENT '出生年月',
    `integral` int(11) NULL COMMENT '积分',
    `address` varchar(50) NULL COMMENT '地址',
    `weichat` varchar(20) NULL COMMENT '微信',
    `qq` bigint(11) NULL COMMENT 'qq',
    `face` varchar(100) NULL COMMENT '头像',
    `remark` varchar(200) NULL COMMENT '备注',
    `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';


CREATE TABLE `sys_user` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '主键',
 `username` VARCHAR(20) NOT NULL   COMMENT '用户名',
 `password` VARCHAR(50) NOT NULL   COMMENT '密码',
 `nkname` VARCHAR(20)    COMMENT '昵称',
 `type` int(4)   DEFAULT 0  COMMENT '类型',
 `status` int(1) NOT NULL  DEFAULT 0  COMMENT '状态',
 `email` varchar(50)    COMMENT '邮箱地址',
 `telno` varchar(11)    COMMENT '手机号码',
 `idcard` varchar(18)    COMMENT '身份证号码',
 `sex` int(1)   DEFAULT 0  COMMENT '身份证号码',
 `birth` date    COMMENT '出生年月',
 `integral` int(11)    COMMENT '积分',
 `address` varchar(50)    COMMENT '地址',
 `weichat` varchar(20)    COMMENT '微信',
 `qq` bigint(11)    COMMENT 'qq',
 `face` varchar(100)    COMMENT '头像',
 `remark` varchar(200)    COMMENT '备注',
 `createtime` timestamp   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '创建时间',
 `updatetime` timestamp   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';


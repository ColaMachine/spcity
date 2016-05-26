drop table `active`;
drop table `artical`;
drop table `sys_user_role`;
drop table `partner`;
drop table `expert`;
drop table `sys_resource`;
drop table `sys_role_resource`;
drop table `sys_user_resource`;
drop table `sys_user`;
drop table `pwdrst`;
drop table `sys_role`;

CREATE TABLE `active` (
  `userid` bigint(20) NOT NULL,
  `activeid` varchar(40) NOT NULL,
  `createdtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `activedtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `actived` bit(1) DEFAULT NULL,
  PRIMARY KEY (`activeid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `artical` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(40) NOT NULL COMMENT '标题',
  `content` varchar(4000) NOT NULL COMMENT '正文',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` bigint(11) NOT NULL COMMENT '创建人',
  `pic` varchar(40) DEFAULT NULL COMMENT '封面',
  `creatorname` varchar(20) DEFAULT NULL COMMENT '创建人姓名',
  `createtime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='新闻咨询';


CREATE TABLE `expert` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `face` varchar(100) DEFAULT NULL COMMENT '头像',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `title` varchar(45) DEFAULT NULL,
  `baike` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='专家';


CREATE TABLE `partner` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(40) NOT NULL COMMENT '名称',
  `brief` varchar(500) NOT NULL COMMENT '简介',
  `address` varchar(100) NOT NULL COMMENT '地址',
  `logo` varchar(50) NOT NULL COMMENT 'logo',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `creator` bigint(11) NOT NULL COMMENT '创建人',
  `pic` varchar(40) DEFAULT NULL COMMENT '封面',
  `creatorname` varchar(20) DEFAULT NULL COMMENT '创建人姓名',
  `createtime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='合作伙伴    ';

CREATE TABLE `pwdrst` (
  `idpwdrst` varchar(32) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `used` tinyint(1) NOT NULL,
  `resettime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idpwdrst`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `sys_resource` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` bigint(10) DEFAULT NULL COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '资源名称',
  `code` varchar(20) NOT NULL COMMENT '资源代码',
  `type` varchar(20) DEFAULT NULL COMMENT '资源分类',
  `url` varchar(255) DEFAULT NULL COMMENT '资源对应URL',
  `order` int(11) DEFAULT NULL COMMENT '排序id',
  `status` int(1) NOT NULL COMMENT '状态',
  `remark` varchar(20) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='资源配置';

CREATE TABLE `sys_role` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '角色名',
  `code` varchar(20) NOT NULL COMMENT '角色代码',
  `order` int(11) NOT NULL COMMENT '排序',
  `status` int(1) NOT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';

CREATE TABLE `sys_role_resource` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `roleid` bigint(15) NOT NULL COMMENT '角色id',
  `rid` bigint(15) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='角色资源关系';

CREATE TABLE `sys_user` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `nkname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `type` int(4) DEFAULT '0' COMMENT '类型',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱地址',
  `telno` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `sex` int(1) DEFAULT '0' COMMENT '身份证号码',
  `birth` date DEFAULT NULL COMMENT '出生年月',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `weichat` varchar(20) DEFAULT NULL COMMENT '微信',
  `qq` bigint(11) DEFAULT NULL COMMENT 'qq',
  `face` varchar(100) DEFAULT NULL COMMENT '头像',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='用户';


CREATE TABLE `sys_user_resource` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` bigint(10) NOT NULL COMMENT '用户id',
  `rid` bigint(10) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资源关系';

CREATE TABLE `sys_user_role` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` bigint(15) NOT NULL COMMENT '用户id',
  `roleid` bigint(15) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

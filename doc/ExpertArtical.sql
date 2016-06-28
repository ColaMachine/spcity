CREATE TABLE `expert_artical` (
    `id` bigint(15) NULL AUTO_INCREMENT COMMENT '编号',
    `expertId` bigint(15) NOT NULL COMMENT '专家id',
    `content` varchar(50000) NULL COMMENT '内容',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家文献';


CREATE TABLE `expert_artical` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '编号',
 `expertId` bigint(15) NOT NULL   COMMENT '专家id',
 `content` varchar(50000)    COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家文献';


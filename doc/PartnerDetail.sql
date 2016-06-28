CREATE TABLE `partner_detail` (
    `id` bigint(15) NULL AUTO_INCREMENT COMMENT '编号',
    `partnerId` bigint(15) NOT NULL COMMENT '合作伙伴',
    `content` varchar(50000) NULL COMMENT '内容',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作伙伴介绍';


CREATE TABLE `partner_detail` (
 `id` bigint(15)   AUTO_INCREMENT  COMMENT '编号',
 `partnerId` bigint(15) NOT NULL   COMMENT '合作伙伴',
 `content` varchar(50000)    COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作伙伴介绍';


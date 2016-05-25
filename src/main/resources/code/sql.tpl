${sql}


CREATE TABLE `${table.tableName}` (
 <#list table.cols as col>
 `${col.name}` ${col.type} <#if col.nn >NOT NULL</#if> <#if col.ai > AUTO_INCREMENT</#if> <#if col.def?? >DEFAULT ${col.def} </#if> COMMENT '${col.remark}',
</#list>
  PRIMARY KEY (`${table.pk.name}`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${table.remark}';


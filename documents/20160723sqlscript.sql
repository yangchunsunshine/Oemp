 ---------------------合同表字段的改动
 alter table mnt_customContract add accBookCostPay INT(10)  COMMENT '是否已交账本费 0:未交 1:已交' ;
 alter table mnt_customContract add accNo varchar(100)  COMMENT '合同编号';
  -------------------------------注意以下脚本包含删除字段，请勿轻易在生产环境执行，请先对生产环境进行数据迁移处理--------------------------------------------------------------------------------------------
 alter table mnt_customContract drop column monthCost ;
 alter table mnt_customContract add column monthCost decimal(16,8)  COMMENT '每月代账费';
  alter table mnt_customContract drop column accBookCost ;
 alter table mnt_customContract add column accBookCost decimal(16,8) COMMENT '账本费';
  alter table mnt_customContract drop column commission ;
 alter table mnt_customContract add column commission decimal(8,4) COMMENT '会计提成';
  alter table mnt_customContract drop column discount ;
 alter table mnt_customContract add column discount decimal(8,4) COMMENT '折扣';
--------------------缴费表字段的改动
alter table mnt_expenseDetail add accNo varchar(100)  COMMENT '合同编号';


ALTER TABLE `mnt_expenseDetail`
ADD COLUMN `payFromDate`  datetime NULL COMMENT '缴费起始月份' AFTER `accNo`,
ADD COLUMN `payToDate`  datetime NULL COMMENT '缴费截止月份' AFTER `payFromDate`;
ALTER TABLE `mnt_expenseDetail`
ADD COLUMN `accId`  varchar(50) NULL COMMENT '合同id (mnt_customContract表主键)' AFTER `deleteFlag`;
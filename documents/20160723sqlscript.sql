 ---------------------��ͬ���ֶεĸĶ�
 alter table mnt_customContract add accBookCostPay INT(10)  COMMENT '�Ƿ��ѽ��˱��� 0:δ�� 1:�ѽ�' ;
 alter table mnt_customContract add accNo varchar(100)  COMMENT '��ͬ���';
  -------------------------------ע�����½ű�����ɾ���ֶΣ�������������������ִ�У����ȶ�����������������Ǩ�ƴ���--------------------------------------------------------------------------------------------
 alter table mnt_customContract drop column monthCost ;
 alter table mnt_customContract add column monthCost decimal(16,8)  COMMENT 'ÿ�´��˷�';
  alter table mnt_customContract drop column accBookCost ;
 alter table mnt_customContract add column accBookCost decimal(16,8) COMMENT '�˱���';
  alter table mnt_customContract drop column commission ;
 alter table mnt_customContract add column commission decimal(8,4) COMMENT '������';
  alter table mnt_customContract drop column discount ;
 alter table mnt_customContract add column discount decimal(8,4) COMMENT '�ۿ�';
--------------------�ɷѱ��ֶεĸĶ�
alter table mnt_expenseDetail add accNo varchar(100)  COMMENT '��ͬ���';


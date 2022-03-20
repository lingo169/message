/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/3/7 18:05:09                            */
/*==============================================================*/


drop table if exists CS_CHATS;

drop table if exists CS_CUSTOMER;

drop table if exists CS_CUSTOMER_GROUP;

drop index Index_2 on CS_CUSTOMER_RELS;

drop index Index_1 on CS_CUSTOMER_RELS;

drop table if exists CS_CUSTOMER_RELS;

/*==============================================================*/
/* Table: CS_CHATS                                              */
/*==============================================================*/
create table CS_CHATS
(
   ID                   VARCHAR(36) not null comment 'ID',
   CUSTOMER_GROUP_ID    bigint not null comment '�ͻ�Ⱥ���',
   SENDER               VARCHAR(36) comment '������',
   RECIPIENT            VARCHAR(36) comment '�����ߣ��ͻ�ID����ȺID��',
   CONTENT              VARCHAR(800) comment '��Ϣ����',
   IMG_URL              VARCHAR(900) comment 'ͼƬ�ļ��б�',
   READ_FLAG            VARCHAR(1) comment '��ȡ��־ 0��δ����1���Ѷ���2��������3��ɾ��',
   RECIPIENT_FLAG       VARCHAR(1) comment '0���ͻ���1��Ⱥ',
   CREATE_ID            bigint comment '������',
   CREATE_TIME          VARCHAR(14) comment '����ʱ��',
   UPDATE_ID            bigint comment '������',
   UPDATE_TIME          VARCHAR(14) comment '����ʱ��',
   primary key (ID)
);

alter table CS_CHATS comment '�����¼��';

/*==============================================================*/
/* Table: CS_CUSTOMER                                           */
/*==============================================================*/
create table CS_CUSTOMER
(
   ID                   bigint not null comment 'ID',
   CUSTOMER_NO          VARCHAR(36) not null comment '�ͻ���',
   NAMESPACE            VARCHAR(60) comment '�������ָ�������Ⱥ��',
   ROOM                 VARCHAR(60) comment '�������ָ�ϵͳ',
   PORTRAIT             VARCHAR(256) comment '�ͻ�ͷ��',
   PASSWORD             VARCHAR(50) comment '����',
   OPENID               VARCHAR(36) comment 'OPENID',
   LEVEL_NO             VARCHAR(36) comment '�ͻ��ȼ����',
   CUSTOMER_NAME        VARCHAR(60) comment '�ͻ�����',
   GENDER               VARCHAR(6) comment '�Ա�  male���У�female��Ů',
   NATION               VARCHAR(40) comment '����',
   CARD_TYPE            VARCHAR(40) comment '֤������',
   CARD_NAME            VARCHAR(80) comment '֤������',
   CARD_ID              VARCHAR(30) comment '֤������',
   MOBILE               VARCHAR(20) comment '�ֻ���',
   EMAIL                VARCHAR(40) comment '����',
   STATUS               VARCHAR(4) comment '״̬',
   CREATE_ID            bigint comment '������',
   CREATE_TIME          VARCHAR(14) comment '����ʱ��',
   UPDATE_ID            bigint comment '������',
   UPDATE_TIME          VARCHAR(14) comment '����ʱ��',
   primary key (ID)
);

alter table CS_CUSTOMER comment '�ͻ���';

/*==============================================================*/
/* Table: CS_CUSTOMER_GROUP                                     */
/*==============================================================*/
create table CS_CUSTOMER_GROUP
(
   ID                   bigint not null comment 'ID',
   CUSTOMER_GROUP_NO    VARCHAR(36) not null comment '�ͻ�Ⱥ���',
   GROUP_FLAG           VARCHAR(1) comment '0��һ��һ��1��һ�Զ�',
   NAMESPACE            VARCHAR(60) comment '�������ָ�������Ⱥ��',
   ROOM                 VARCHAR(60) comment '�������ָ�ϵͳ',
   PORTRAIT             VARCHAR(256) comment '�ͻ�ͷ��',
   CUSTOMER_GROUP_NAME  VARCHAR(80) comment '�ͻ�Ⱥ����',
   SENDER               VARCHAR(36) comment '������',
   CONTENT              VARCHAR(40) comment '����һ������',
   END_TIME             VARCHAR(14) comment '���¹�ͨʱ��',
   STATUS               char(10) comment '0��ʧЧ��1����Ч��2����ͣ',
   CREATE_ID            bigint comment '������',
   CREATE_TIME          VARCHAR(14) comment '����ʱ��',
   UPDATE_ID            bigint comment '������',
   UPDATE_TIME          VARCHAR(14) comment '����ʱ��',
   primary key (ID)
);

alter table CS_CUSTOMER_GROUP comment '�ͻ�Ⱥ';

/*==============================================================*/
/* Table: CS_CUSTOMER_RELS                                      */
/*==============================================================*/
create table CS_CUSTOMER_RELS
(
   ID                   bigint not null comment 'ID',
   CUSTOMER_ID          bigint not null comment '�ͻ�ID',
   CUSTOMER_GROUP_ID    bigint not null comment '�ͻ�ȺID���ͻ�ID��',
   CUSTOMER_REL_ID      bigint comment '��ϵID���ͻ���ǩ���У�',
   OPT_FLAG             VARCHAR(1) comment '0��������
            1��������',
   LEADER               VARCHAR(1) comment '0������
            1����������Ա
            2������Ա
            3����ͨ��Ա
            ',
   REL_FLAG             VARCHAR(1) comment '0���ͻ���1��Ⱥ',
   STATUS               VARCHAR(1) comment '0��������
            1���Ѿ�ȷ��',
   CREATE_ID            VARCHAR(36) comment '������',
   CREATE_TIME          VARCHAR(14) comment '����ʱ��',
   UPDATE_ID            VARCHAR(36) comment '������',
   UPDATE_TIME          VARCHAR(14) comment '����ʱ��',
   primary key (ID)
);

alter table CS_CUSTOMER_RELS comment '�ͻ���ϵ��';

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on CS_CUSTOMER_RELS
(
   CUSTOMER_ID
);

/*==============================================================*/
/* Index: Index_2                                               */
/*==============================================================*/
create index Index_2 on CS_CUSTOMER_RELS
(
   CUSTOMER_GROUP_ID
);


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
   CUSTOMER_GROUP_ID    bigint not null comment '客户群编号',
   SENDER               VARCHAR(36) comment '发出者',
   RECIPIENT            VARCHAR(36) comment '接收者（客户ID或者群ID）',
   CONTENT              VARCHAR(800) comment '消息内容',
   IMG_URL              VARCHAR(900) comment '图片文件列表',
   READ_FLAG            VARCHAR(1) comment '读取标志 0：未读，1：已读，2：撤销，3：删除',
   RECIPIENT_FLAG       VARCHAR(1) comment '0：客户，1：群',
   CREATE_ID            bigint comment '创建人',
   CREATE_TIME          VARCHAR(14) comment '创建时间',
   UPDATE_ID            bigint comment '更新人',
   UPDATE_TIME          VARCHAR(14) comment '更新时间',
   primary key (ID)
);

alter table CS_CHATS comment '聊天记录表';

/*==============================================================*/
/* Table: CS_CUSTOMER                                           */
/*==============================================================*/
create table CS_CUSTOMER
(
   ID                   bigint not null comment 'ID',
   CUSTOMER_NO          VARCHAR(36) not null comment '客户号',
   NAMESPACE            VARCHAR(60) comment '用于区分各单独和群体',
   ROOM                 VARCHAR(60) comment '用于区分各系统',
   PORTRAIT             VARCHAR(256) comment '客户头像',
   PASSWORD             VARCHAR(50) comment '密码',
   OPENID               VARCHAR(36) comment 'OPENID',
   LEVEL_NO             VARCHAR(36) comment '客户等级编号',
   CUSTOMER_NAME        VARCHAR(60) comment '客户名称',
   GENDER               VARCHAR(6) comment '性别  male：男，female：女',
   NATION               VARCHAR(40) comment '民族',
   CARD_TYPE            VARCHAR(40) comment '证件类型',
   CARD_NAME            VARCHAR(80) comment '证件名称',
   CARD_ID              VARCHAR(30) comment '证件号码',
   MOBILE               VARCHAR(20) comment '手机号',
   EMAIL                VARCHAR(40) comment '邮箱',
   STATUS               VARCHAR(4) comment '状态',
   CREATE_ID            bigint comment '创建人',
   CREATE_TIME          VARCHAR(14) comment '创建时间',
   UPDATE_ID            bigint comment '更新人',
   UPDATE_TIME          VARCHAR(14) comment '更新时间',
   primary key (ID)
);

alter table CS_CUSTOMER comment '客户表';

/*==============================================================*/
/* Table: CS_CUSTOMER_GROUP                                     */
/*==============================================================*/
create table CS_CUSTOMER_GROUP
(
   ID                   bigint not null comment 'ID',
   CUSTOMER_GROUP_NO    VARCHAR(36) not null comment '客户群编号',
   GROUP_FLAG           VARCHAR(1) comment '0：一对一，1：一对多',
   NAMESPACE            VARCHAR(60) comment '用于区分各单独和群体',
   ROOM                 VARCHAR(60) comment '用于区分各系统',
   PORTRAIT             VARCHAR(256) comment '客户头像',
   CUSTOMER_GROUP_NAME  VARCHAR(80) comment '客户群名称',
   SENDER               VARCHAR(36) comment '发出者',
   CONTENT              VARCHAR(40) comment '最新一次内容',
   END_TIME             VARCHAR(14) comment '最新沟通时间',
   STATUS               char(10) comment '0：失效，1：生效，2：暂停',
   CREATE_ID            bigint comment '创建人',
   CREATE_TIME          VARCHAR(14) comment '创建时间',
   UPDATE_ID            bigint comment '更新人',
   UPDATE_TIME          VARCHAR(14) comment '更新时间',
   primary key (ID)
);

alter table CS_CUSTOMER_GROUP comment '客户群';

/*==============================================================*/
/* Table: CS_CUSTOMER_RELS                                      */
/*==============================================================*/
create table CS_CUSTOMER_RELS
(
   ID                   bigint not null comment 'ID',
   CUSTOMER_ID          bigint not null comment '客户ID',
   CUSTOMER_GROUP_ID    bigint not null comment '客户群ID（客户ID）',
   CUSTOMER_REL_ID      bigint comment '关系ID（客户标签才有）',
   OPT_FLAG             VARCHAR(1) comment '0：申请者
            1：接收者',
   LEADER               VARCHAR(1) comment '0：好友
            1：超级管理员
            2：管理员
            3：普通会员
            ',
   REL_FLAG             VARCHAR(1) comment '0：客户，1：群',
   STATUS               VARCHAR(1) comment '0：申请中
            1：已经确认',
   CREATE_ID            VARCHAR(36) comment '创建人',
   CREATE_TIME          VARCHAR(14) comment '创建时间',
   UPDATE_ID            VARCHAR(36) comment '更新人',
   UPDATE_TIME          VARCHAR(14) comment '更新时间',
   primary key (ID)
);

alter table CS_CUSTOMER_RELS comment '客户关系表';

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


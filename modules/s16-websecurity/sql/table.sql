create database manageui default character set utf8;

-- ------------------------------
-- 创建管理员帐号表t_admin_user
-- ----------------------------
CREATE TABLE `t_admin_user` (
      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
      `passwd` varchar(256) NOT NULL DEFAULT '' COMMENT '用户密码',
      `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名字',
      `email` varchar(64) NOT NULL DEFAULT '' COMMENT 'email',
      `lastPasswordResetDate` DATETIME COMMENT 'lastPasswordResetDate',
      PRIMARY KEY (`id`)
) ENGINE=MYISAM;

-- ----------------------------
-- 添加3个管理帐号
-- ----------------------------
INSERT INTO `t_admin_user`(username,passwd,lastPasswordResetDate) VALUES ('admin', 'admin',now());
INSERT INTO `t_admin_user`(username,passwd,lastPasswordResetDate) VALUES ('test', 'test', now());
INSERT INTO `t_admin_user`(username,passwd,lastPasswordResetDate) VALUES ('ljy', '111111', now());

-- ----------------------------
-- 创建权限表t_role
-- ----------------------------
CREATE TABLE `t_role` (
      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
      `role` varchar(40) NOT NULL DEFAULT '',
      `descpt` varchar(40) NOT NULL DEFAULT '' COMMENT '角色描述',
      `category` varchar(40) NOT NULL DEFAULT '' COMMENT '分类',
      PRIMARY KEY (`id`)
) ENGINE=MYISAM;

-- ----------------------------
-- 加入4个操作权限
-- ----------------------------
INSERT INTO `t_role`(role,descpt,category) VALUES ('ROLE_ADMIN', '系统管理员', 'sysadmin');
INSERT INTO `t_role`(role,descpt,category) VALUES ('ROLE_UPDATE_FILM', '影片修改', 'filmadmin');
INSERT INTO `t_role`(role,descpt,category) VALUES ('ROLE_DELETE_FILM', '影片删除', 'filmadmin');
INSERT INTO `t_role`(role,descpt,category) VALUES ('ROLE_ADD_FILM', '影片添加', 'filmadmin');


-- ----------------------------
-- 创建管理员对应权限表t_user_role
--
-- ----------------------------
CREATE TABLE `t_user_role` (
      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
      `userid` bigint(20) unsigned NOT NULL,
      `roleid` bigint(20) unsigned NOT NULL,
      PRIMARY KEY (`id`),
      KEY `userid` (`userid`),
      KEY `roleid` (`roleid`),
      CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `t_admin_user` (`id`),
      CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`roleid`) REFERENCES `t_role` (`id`)
) ENGINE=MYISAM;

-- ----------------------------
-- 加入管理员对应的权限
--
-- ----------------------------
INSERT INTO `t_user_role`(userid,roleid) VALUES (1,1);
INSERT INTO `t_user_role`(userid,roleid) VALUES (3,2);
INSERT INTO `t_user_role`(userid,roleid) VALUES (3,3);
INSERT INTO `t_user_role`(userid,roleid) VALUES (3,4);
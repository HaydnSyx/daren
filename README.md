## 项目部署：
1. 配置数据库
	- 手动添加一位Admin用户，并赋予相关权限
```
		INSERT INTO tb_muser VALUES ('1', 'admin', '14e1b600b1fd579f47433b88e8d85291', '1', 'Admin', null, null, null, null, null, 'T');
		INSERT INTO tb_role VALUES ('1', '超级管理员', '2016-10-11 15:48:58', null, null);
		INSERT INTO tb_module VALUES ('1', null, '首页', '/index.htm', '2016-10-11 16:32:57');
		INSERT INTO tb_module VALUES ('2', null, '系统相关', '/system.htm', '2016-10-11 17:06:22');
		INSERT INTO tb_module VALUES ('3', null, '权限', '/authority,htm', '2016-10-11 18:00:45');
		INSERT INTO tb_module VALUES ('4', '3', '模块列表', '', '2016-10-11 18:01:27');
		INSERT INTO tb_module VALUES ('5', '3', '功能', '', '2016-10-11 16:32:17');
		INSERT INTO tb_module VALUES ('6', '3', '角色', '', '2016-10-11 16:32:34');
		INSERT INTO tb_module VALUES ('7', null, '管理员', '/muser.htm', '2016-10-11 18:16:09');
		INSERT INTO tb_func VALUES ('1', '1', '查看', '/index.htm', '2016-06-03 16:34:18');
		INSERT INTO tb_func VALUES ('2', '2', '修改密码', '/muser/editpwd.htm', '2016-06-03 17:07:46');
		INSERT INTO tb_func VALUES ('3', '2', '退出登录', '/signout.htm', '2016-06-03 17:08:19');
		INSERT INTO tb_func VALUES ('5', '4', '查看', '/module/list.htm', '2015-11-17 16:39:23');
		INSERT INTO tb_func VALUES ('6', '4', '添加', '/module/add.htm', '2015-11-17 16:39:45');
		INSERT INTO tb_func VALUES ('7', '4', '更新', '/module/update.htm', '2015-11-17 16:39:57');
		INSERT INTO tb_func VALUES ('8', '4', '删除', '/module/delete.htm', '2015-11-17 16:40:09');
		INSERT INTO tb_func VALUES ('9', '5', '查看', '/func/list.htm', '2015-11-17 16:41:29');
		INSERT INTO tb_func VALUES ('10', '5', '添加', '/func/add.htm', '2015-11-17 16:41:41');
		INSERT INTO tb_func VALUES ('11', '5', '更新', '/func/update.htm', '2015-11-17 16:41:54');
		INSERT INTO tb_func VALUES ('12', '5', '删除', '/func/delete.htm', '2015-11-17 16:42:04');
		INSERT INTO tb_func VALUES ('13', '6', '查看', '/role/list.htm', '2015-11-17 16:42:29');
		INSERT INTO tb_func VALUES ('14', '6', '添加', '/role/add.htm', '2015-11-17 16:42:41');
		INSERT INTO tb_func VALUES ('15', '6', '更新', '/role/update.htm', '2015-11-17 16:42:51');
		INSERT INTO tb_func VALUES ('16', '6', '删除', '/role/delete.htm', '2015-11-17 16:43:02');
		INSERT INTO tb_func VALUES ('17', '6', '授权', '/role/addPerm.htm', '2015-11-17 17:30:53');
		INSERT INTO tb_func VALUES ('18', '7', '查看', '/muser/list.htm', '2016-06-06 18:17:42');
		INSERT INTO tb_func VALUES ('19', '7', '添加', '/muser/add.htm', '2016-06-06 18:17:53');
		INSERT INTO tb_func VALUES ('20', '7', '更新', '/muser/update.htm', '2016-06-06 18:18:04');
		INSERT INTO tb_func VALUES ('21', '7', '冻结', '/muser/updateStatus.htm', '2016-06-06 18:25:46');
		INSERT INTO tb_perm VALUES ('1', '1', '1');
		INSERT INTO tb_perm VALUES ('2', '2', '1');
		INSERT INTO tb_perm VALUES ('3', '3', '1');
		INSERT INTO tb_perm VALUES ('5', '5', '1');
		INSERT INTO tb_perm VALUES ('6', '6', '1');
		INSERT INTO tb_perm VALUES ('7', '7', '1');
		INSERT INTO tb_perm VALUES ('8', '8', '1');
		INSERT INTO tb_perm VALUES ('9', '9', '1');
		INSERT INTO tb_perm VALUES ('10', '10', '1');
		INSERT INTO tb_perm VALUES ('11', '11', '1');
		INSERT INTO tb_perm VALUES ('12', '12', '1');
		INSERT INTO tb_perm VALUES ('13', '13', '1');
		INSERT INTO tb_perm VALUES ('14', '14', '1');
		INSERT INTO tb_perm VALUES ('15', '15', '1');
		INSERT INTO tb_perm VALUES ('16', '16', '1');
		INSERT INTO tb_perm VALUES ('17', '17', '1');
		INSERT INTO tb_perm VALUES ('18', '18', '1');
		INSERT INTO tb_perm VALUES ('19', '19', '1');
		INSERT INTO tb_perm VALUES ('20', '20', '1');
		INSERT INTO tb_perm VALUES ('21', '21', '1');
```
	- 配置属性表
```
		INSERT INTO tb_properties VALUES ('website', 'http://haydnsyx.hk1.ngrok.cc/taobao-admin');
		INSERT INTO tb_properties VALUES ('website_test', 'http://localhost:8080/taobao-admin');
		INSERT INTO tb_properties VALUES ('ueditor_server_real_path', 'F:/apacheUpload');
		INSERT INTO tb_properties VALUES ('ueditor_server_website_test', 'http://localhost');
		INSERT INTO tb_properties VALUES ('file_path', '/taobao-admin');
		INSERT INTO tb_properties VALUES ('file_img_path', '/img');
		INSERT INTO tb_properties VALUES ('file_text_path', '/text');
		INSERT INTO tb_properties VALUES ('file_common_path', '/common');
		INSERT INTO tb_properties VALUES ('ftp_server_website_test', 'ftp://localhost');
		INSERT INTO tb_properties VALUES ('ftp_server_real_path', 'F:/upload');
		INSERT INTO tb_properties VALUES ('ftp_server_addr', '127.0.0.1');
		INSERT INTO tb_properties VALUES ('ftp_server_port', '21');
		INSERT INTO tb_properties VALUES ('ftp_server_username', 'haydnsyx');
		INSERT INTO tb_properties VALUES ('ftp_server_password', 'sun82293234');
		INSERT INTO tb_properties VALUES ('file_singleproduct_path', '/singleproduct');
		INSERT INTO tb_properties VALUES ('file_detaillist_path', '/detaillist');
		INSERT INTO tb_properties VALUES ('file_card_path', '/card');
```

2. 开启Apache服务器 -> 存储编辑图片等文件
	- 修改httpd.conf
```
		约180行 修改为 -> DocumentRoot "F:/apacheUpload/"
		约207行 修改为 -> <Directory "F:/apacheUpload">
```
	
3. 开启FTP服务器 -> 存储附件信息
	- 设置共享文件夹名称为 upload
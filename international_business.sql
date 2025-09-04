/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : international_business

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 28/04/2024 11:47:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '管理员', 'http://localhost:9090/files/1707752401554-24010901.jpg', 'ADMIN', '13677889922', 'admin@xm.com');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户名称',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '国家',
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户邮箱',
  `payment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '客户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (12, 'Alvin Cullum', '加拿大', '0046-925178242 ', 'MikeSweden@webmail.co.za', '电汇');
INSERT INTO `customer` VALUES (13, 'John Wilson', '美国', '658-9337', 'JackAmerica@aol.com', '信用证');
INSERT INTO `customer` VALUES (14, 'Susan Brown', '英国', '07735-783245', 'Susan12@gmail.com', '信用证');
INSERT INTO `customer` VALUES (15, 'Lars Smith', '瑞典', '0046-734256788', 'Larsove@caron.se', '信用证');
INSERT INTO `customer` VALUES (16, 'Keita Lee', '韩国', '03-52570013', 'Keita@candel.co.jp', '电汇');

-- ----------------------------
-- Table structure for declare
-- ----------------------------
DROP TABLE IF EXISTS `declare`;
CREATE TABLE `declare`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `inv` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '合同号',
  `hscode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'HS_CODE',
  `types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `quantity` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数量',
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '单价',
  `total_price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '总价',
  `from_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '原产国',
  `to_country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最终目的地',
  `goodplace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '境内货源地',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '客户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of declare
-- ----------------------------
INSERT INTO `declare` VALUES (8, 'FJ023H12923', '6403990090', 'WOMEN SHOES', '1120', '5.5', '112', '中国', '美国', '温州');
INSERT INTO `declare` VALUES (9, 'FJ023H02425', '6403911990', 'WOMEN SHOES', '4560', '9.67', '44095.2', '中国', '瑞典', '温州');
INSERT INTO `declare` VALUES (10, 'FJ023H04658', '6403911990', 'WOMEN SHOES', '3240', '6.0', '19440', '中国', '加拿大', '温州');

-- ----------------------------
-- Table structure for factory
-- ----------------------------
DROP TABLE IF EXISTS `factory`;
CREATE TABLE `factory`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工厂名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `certi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '其他证书',
  `bsci` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'bsci',
  `iso` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'iso',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '禁用状态',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '工厂' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of factory
-- ----------------------------
INSERT INTO `factory` VALUES (8, 'Haixing', '123', '海星鞋业', 'http://localhost:9090/files/1708666096430-724833ccae2638d32657da43f9fa8d35.jpeg', 'FACTORY', '0577-88324576', 'Haixing123@163.com', '', '有', '有', 0, '温州市鹿城区腾风路与鞋都大道交叉口南');
INSERT INTO `factory` VALUES (9, 'shengwei', '123', '盛威鞋业', 'http://localhost:9090/files/1708857037583-b9f19b85db2b3817b3ab2a9f9f177e0d.jpeg', 'FACTORY', '0577-88342567', 'shengwei@qq.com', NULL, '无', '有', 0, '温州市鹿城区双屿伊利路82号');
INSERT INTO `factory` VALUES (10, 'huaxing', '123', '华兴鞋业', 'http://localhost:9090/files/1713152012698-4ed81b1fcda28eb0b572752667f3629d.png', 'FACTORY', '0577-89103421', 'huaxing3421@163.com', NULL, '无', '有', 0, '温州市龙湾区滨海大道1018号');
INSERT INTO `factory` VALUES (11, 'haisen', '123', '海森鞋业', 'http://localhost:9090/files/1713155401225-7422354e4b17b121253605e618737c18.jpg', 'FACTORY', '0577-872110284', 'haisenxieye@163.com', NULL, '无', '无', 0, '温州市龙湾区钱水路102号');
INSERT INTO `factory` VALUES (12, 'kangdun', '123', '康顿鞋业', 'http://localhost:9090/files/1713155512790-a7e942ad9135244eac719d62be145261.jpg', 'FACTORY', '0577-88219324', 'kangdun123.@163.com', NULL, '有', '无', 0, '温州市龙湾区状元街道江永路56号');
INSERT INTO `factory` VALUES (13, 'yierhui', '123', '意惠鞋业', 'http://localhost:9090/files/1713155573954-d495dad85e3badaef95ba3e716db3d4b.jpg', 'FACTORY', '0578-89213587', 'yierhui@qq.com', NULL, '无', '无', 0, '丽水市青田县安东路与温东路交叉路口');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `goods_no` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品编号',
  `types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品属性',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `fabric` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '面料',
  `lining` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内里',
  `heel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '鞋跟',
  `outsole` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '大底',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '可用' COMMENT '状态',
  `ptotal` double(10, 2) NULL DEFAULT NULL COMMENT '总下单额',
  `stotal` int(11) NULL DEFAULT NULL COMMENT '总下单量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (2, '01-2356', '运动鞋', 2.56, 'PU', '网布', '/', '白/红薄底', 'http://localhost:9090/files/1709126638718-屏幕截图 2024-01-17 221937.png', '可用', 18034.00, 2540);
INSERT INTO `goods` VALUES (3, '03-2430', '靴子', 3.85, '皮革', '呢绒', '黑色', '黑色', 'http://localhost:9090/files/1710507552881-屏幕截图 2024-01-17 221959.png', '可用', 52852.00, 7060);
INSERT INTO `goods` VALUES (4, '02-2601', '跟鞋', 1.40, '皮革', '网布', '金色', '棕色', 'http://localhost:9090/files/1713151752175-ca49bd6b4df4bd3ec77493ed313ffeed.jpg', '可用', 25276.00, 4660);
INSERT INTO `goods` VALUES (5, '02-2731', '跟鞋', 1.38, '绒布', '皮革', '酒红', '浅棕', 'http://localhost:9090/files/1713151794271-621eecccc4388a1c0b55fe586875ed3b.jpg', '可用', 4200.00, 1200);
INSERT INTO `goods` VALUES (6, '01-2491', '运动鞋', 2.78, '皮革白', '布里', '/', '白色薄底', 'http://localhost:9090/files/1713151859476-9d5a9108e6fc0fe9b32fb411675490e3.jpeg', '可用', 0.00, 0);
INSERT INTO `goods` VALUES (7, '04-2134', '帆布鞋', 2.34, '帆布蓝色', 'PU', '/', '白黑厚底', 'http://localhost:9090/files/1713151943128-6773950ab657f8414c693f2cc8fba087.jpeg', '可用', 26226.00, 4230);
INSERT INTO `goods` VALUES (8, '05-1012', '雪地靴', 4.50, 'PU革', '呢绒', '/', '棕色厚底', 'http://localhost:9090/files/1713191780033-屏幕截图 2024-01-17 221832.png', '可用', 44095.20, 4560);

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作',
  `username` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logs
-- ----------------------------
INSERT INTO `logs` VALUES (65, '新增订单null', 'admin', '新增', '127.0.0.1', '2024-04-20 10:54:05');
INSERT INTO `logs` VALUES (66, '新增订单1111', 'admin', '新增', '127.0.0.1', '2024-04-20 10:55:59');
INSERT INTO `logs` VALUES (67, '新增订单1111', 'admin', '新增', '127.0.0.1', '2024-04-20 10:59:14');
INSERT INTO `logs` VALUES (73, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-24 15:31:18');
INSERT INTO `logs` VALUES (74, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-24 15:40:16');
INSERT INTO `logs` VALUES (75, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-24 20:35:58');
INSERT INTO `logs` VALUES (76, '新增05-1123商品信息', 'admin', '新增商品信息', '127.0.0.1', '2024-04-24 20:45:18');
INSERT INTO `logs` VALUES (77, '新增01-2312商品信息', 'admin', '新增商品信息', '127.0.0.1', '2024-04-24 20:47:06');
INSERT INTO `logs` VALUES (78, '新增111商品信息', 'admin', '新增商品信息', '127.0.0.1', '2024-04-24 20:49:50');
INSERT INTO `logs` VALUES (79, '新增订单24010623', 'admin', '新增', '127.0.0.1', '2024-04-24 20:54:14');
INSERT INTO `logs` VALUES (80, '新增订单K24021025', 'admin', '新增', '127.0.0.1', '2024-04-24 20:58:37');
INSERT INTO `logs` VALUES (81, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-25 10:40:04');
INSERT INTO `logs` VALUES (82, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-25 15:06:02');
INSERT INTO `logs` VALUES (83, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-25 16:02:52');
INSERT INTO `logs` VALUES (84, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-25 16:23:03');
INSERT INTO `logs` VALUES (85, '系统登录', 'xiaoming', '登录', '127.0.0.1', '2024-04-25 16:34:39');
INSERT INTO `logs` VALUES (86, '系统登录', 'Haixing', '登录', '127.0.0.1', '2024-04-25 16:36:19');
INSERT INTO `logs` VALUES (87, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-25 16:37:08');
INSERT INTO `logs` VALUES (88, '新增订单K24021025', 'admin', '新增', '127.0.0.1', '2024-04-25 16:43:39');
INSERT INTO `logs` VALUES (89, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-28 08:59:53');
INSERT INTO `logs` VALUES (90, '系统登录', 'admin', '登录', '127.0.0.1', '2024-04-28 10:59:59');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `factory` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `staff` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '负责人',
  `exp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (20, '24030701', '内里材料不合适', '2024-04-15', '进行中', 'Haixing', '小明', '开发部正在确认修改中');
INSERT INTO `notice` VALUES (21, '24041203', '鞋跟供应商交货期推迟', '2024-04-15', '未处理', 'haisen', '小华', NULL);
INSERT INTO `notice` VALUES (22, '23020421', '防水面料测试结果未达到所需标准，需要修改材料', '2024-04-15', '已处理', 'kangdun', '小华', '新的防水材料供应商信息，已经通过邮件发送给相关负责人');
INSERT INTO `notice` VALUES (23, '24020501', '鞋底供货不足，生产无法正常进行，需要增加供应商', '2024-04-16', '未处理', 'shengwei', '小红', NULL);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `customer_id` int(11) NULL DEFAULT NULL COMMENT '客户ID',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `factory_id` int(11) NULL DEFAULT NULL COMMENT '工厂ID',
  `good_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品编号',
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '颜色',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '码段',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工厂交货日期',
  `notice` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '特别要求',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '已出货' COMMENT '状态',
  `staff_id` int(11) NULL DEFAULT NULL COMMENT '员工ID',
  `exec_id` int(11) NULL DEFAULT NULL COMMENT '1订单 2配码 3装箱 4订船 5报关 6完成',
  `fstatus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '工厂生产状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (69, 12, 'M24020501', 9, '01-2356', '黑红白', '36-42', 7.10, 2540, 'http://localhost:9090/files/1713157371061-屏幕截图 2024-01-17 221937.png', '2024-07-01', NULL, '未出货', 8, 4, '未完成');
INSERT INTO `order` VALUES (70, 13, 'J24010701', 8, '03-2430', '黑色', '36-40', 6.20, 1260, 'http://localhost:9090/files/1713157361383-屏幕截图 2024-01-17 221959.png', '2024-05-01', NULL, '未出货', 7, 4, '未完成');
INSERT INTO `order` VALUES (71, 14, 'S24021203', 11, '02-2601', '白色', '36-42', 5.40, 3540, 'http://localhost:9090/files/1713164515974-ca49bd6b4df4bd3ec77493ed313ffeed.jpg', '2024-12-03', NULL, '未出货', 9, 4, '未完成');
INSERT INTO `order` VALUES (72, 12, 'M23051204', 11, '02-2601', '白色', '36-41', 5.50, 1120, 'http://localhost:9090/files/1713171994214-ca49bd6b4df4bd3ec77493ed313ffeed.jpg', '2023-12-04', NULL, '已出货', 8, 5, '已完成');
INSERT INTO `order` VALUES (73, 15, 'L23020421', 12, '05-1012', '卡其色', '37-41', 9.67, 4560, 'http://localhost:9090/files/1713191980999-屏幕截图 2024-01-17 221832.png', '2023-04-21', '需要通过防水测试', '已出货', 9, 5, '已完成');
INSERT INTO `order` VALUES (74, 13, 'J23010415', 8, '03-2430', '黑色', '36-40', 6.00, 3240, 'http://localhost:9090/files/1713232558665-屏幕截图 2024-01-17 221959.png', '2023-04-15', NULL, '已出货', 7, 5, '已完成');
INSERT INTO `order` VALUES (75, 16, 'K24010413', 10, '04-2134', '帆布蓝色', '36-41', 6.20, 4230, 'http://localhost:9090/files/1713233522409-6773950ab657f8414c693f2cc8fba087.jpeg', '2024-04-13', NULL, '未出货', 8, 3, '已完成');
INSERT INTO `order` VALUES (76, 16, 'K24021025', 8, '02-2731', '酒红色', '36-40', 3.50, 1200, 'http://localhost:9090/files/1714034612894-621eecccc4388a1c0b55fe586875ed3b.jpg', '2024-10-25', NULL, '未出货', 7, 4, '未完成');

-- ----------------------------
-- Table structure for packlist
-- ----------------------------
DROP TABLE IF EXISTS `packlist`;
CREATE TABLE `packlist`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `customer_id` int(11) NULL DEFAULT NULL COMMENT '客户',
  `fromcus` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '港口',
  `inv` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '合同号',
  `todate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '日期',
  `shoes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '鞋子',
  `prs` int(11) NULL DEFAULT NULL COMMENT '总数',
  `ctns` int(11) NULL DEFAULT NULL COMMENT '集装箱数',
  `netw` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '净重',
  `gross` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '毛重',
  `cbm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '立方米',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '装箱单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of packlist
-- ----------------------------
INSERT INTO `packlist` VALUES (18, 13, 'NINGBO,CHINA', 'FJ024H03924', '2024-07-04', '休闲鞋', 1260, 140, '925.45', '1276.9', '12.03');
INSERT INTO `packlist` VALUES (19, 12, 'NINGBO,CHINA', 'FJ024H07231', '2024-05-01', '休闲鞋', 2540, 293, '1865.6', '2574.1', '24.26');
INSERT INTO `packlist` VALUES (20, 14, 'NINGBO,CHINA', 'FJ024H12935', '2024-12-05', '休闲鞋', 3540, 354, '2384.2', '3412.8', '33.63');
INSERT INTO `packlist` VALUES (21, 12, 'NINGBO,CHINA', 'FJ023H12923', '2023-12-08', '休闲鞋', 1120, 112, '817.6', '1131.2', '10.64');
INSERT INTO `packlist` VALUES (22, 15, 'NINGBO,CHINA', 'FJ023H02425', '2024-04-27', '休闲鞋', 4560, 912, '3528.7', '4821.8', '43.32');
INSERT INTO `packlist` VALUES (23, 13, 'NINGBO,CHINA', 'FJ023H04658', '2024-04-24', '休闲鞋', 3240, 360, '2365.2', '3272.4', '30.78');
INSERT INTO `packlist` VALUES (24, 16, 'QINGDAO,CHINA', 'FJ024H04523', '2024-04-17', '休闲鞋', 4230, 423, '3087.9', '4272.3', '40.19');
INSERT INTO `packlist` VALUES (25, 16, 'NINGBO,CHINA', 'FJ024H10253', '2024-10-28', '休闲鞋', 1200, 120, '876.0', '1210.0', '11.4');

-- ----------------------------
-- Table structure for ship
-- ----------------------------
DROP TABLE IF EXISTS `ship`;
CREATE TABLE `ship`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `shipper` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发货地址',
  `consignee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `notify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知人',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '品名',
  `hscode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'HS_CODE',
  `depature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '启运港',
  `arrival` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '到达港',
  `schedule` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '船期',
  `freight` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '货代',
  `cartons` int(255) NULL DEFAULT NULL COMMENT '箱数',
  `prs` int(255) NULL DEFAULT NULL COMMENT '双数',
  `containers` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '集装箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订船资料' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ship
-- ----------------------------
INSERT INTO `ship` VALUES (11, 'HW INTERNATIONAL TRADING CO.,LTD, 9 FLOOR, HUASHENG BUILDING, NO.9, NINGBO STREET, LUCHENG DISTRICT, WENZHOU ZHEJIANG', 'SPACE, LOS ANGELES, CA 90023, UNITED STATES', 'SAME AS CONSIGNEE', 'J24010701', 'WOMEN SHOES', '6402190090', 'NINGBO, CHINA', 'LOS ANGELES', '2024-07-20', 'Maersk', 140, 1260, '1个40#GP');
INSERT INTO `ship` VALUES (12, 'HW INTERNATIONAL TRADING CO.,LTD, 9 FLOOR, HUASHENG BUILDING, NO.9, NINGBO STREET, LUCHENG DISTRICT, WENZHOU ZHEJIANG', '1021 WEST STREET, VANCOUVER, BC V6T 1Z4', 'JACK', 'M24020501', 'WOMEN SHOES', '6403911990', 'NINGBO, CHINA', ' VANCOUVER, CNANADA', '2024-05-23', 'Maersk', 293, 2540, '1个40#GP');
INSERT INTO `ship` VALUES (13, 'HW INTERNATIONAL TRADING CO.,LTD, 9 FLOOR, HUASHENG BUILDING, NO.9, NINGBO STREET, LUCHENG DISTRICT, WENZHOU ZHEJIANG', '24 FLOOR, MANCHESTER BUILDING，MALET St，MANCHESTER，UK', 'SUSAN', 'S24021203', 'WOMEN SHOES', '6403990090', 'NINGBO,CHINA', ' MANCHESTER, UK', '2024-12-25', 'CMA', 354, 3540, '1个40#GP');
INSERT INTO `ship` VALUES (14, 'HW INTERNATIONAL TRADING CO.,LTD, 9 FLOOR, HUASHENG BUILDING, NO.9, NINGBO STREET, LUCHENG DISTRICT, WENZHOU ZHEJIANG', 'SPACE, LOS ANGELES, CA 90023, UNITED STATES', 'SAME AS CONSIGNE', 'M23051204', 'WOMEN SHOES', '6403990090', 'NINGBO,CHINA', 'LOS ANGELES', '2023-12-22', 'Maersk', 112, 1120, '1个40#GP');
INSERT INTO `ship` VALUES (15, 'HW INTERNATIONAL TRADING CO.,LTD, 9 FLOOR, HUASHENG BUILDING, NO.9, NINGBO STREET, LUCHENG DISTRICT, WENZHOU ZHEJIANG', 'THREMA SCANDINNAVIA AB, VASTERGA 3, GLIMAKRA SERDEN', 'SAME AS CONSIGNE', 'L23020421', 'WOMEN SHOES', '6403911990', 'NINGBO,CHINA', 'GOTHENBURG, SWEDEN', '2024-05-08', 'MSC', 912, 4560, '1个40#GP');
INSERT INTO `ship` VALUES (16, 'HW INTERNATIONAL TRADING CO.,LTD, 9 FLOOR, HUASHENG BUILDING, NO.9, NINGBO STREET, LUCHENG DISTRICT, WENZHOU ZHEJIANG', '1021 WEST STREET, VANCOUVER, BC V6T 1Z4', 'Jack', 'J23010415', 'WOMEN SHOES', '6403911990', 'NINGBO,CHINA', 'VANCOUVER, CNANADA', '2023-05-02', 'COSCO', 360, 3240, '1个40#GP');
INSERT INTO `ship` VALUES (17, 'HW INTERNATIONAL TRADING CO.,LTD, 9 FLOOR, HUASHENG BUILDING, NO.9, NINGBO STREET, LUCHENG DISTRICT, WENZHOU ZHEJIANG', '35 FLOOR, GUKJEGEUMYUNG-RO, YEONGDEUNGPO-GU, SEOUL', 'SAME AS CONSIGNE', 'K24021025', 'WOMEN SHOES', '6403990090', 'NINGBO,CHINA', 'BUSAN,KOREA', '2024-11-02', 'ONE', 120, 1200, '1个40#GP');

-- ----------------------------
-- Table structure for sizenum
-- ----------------------------
DROP TABLE IF EXISTS `sizenum`;
CREATE TABLE `sizenum`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '颜色',
  `tsi` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '36',
  `tse` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '37',
  `te` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '38',
  `tn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '39',
  `ft` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '40',
  `fo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '41',
  `fw` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '42',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '规格',
  `boxes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '箱数',
  `stotal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '总数',
  `boxes_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '箱号',
  `asscode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '配码代码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '配码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sizenum
-- ----------------------------
INSERT INTO `sizenum` VALUES (27, 'J24010701', '黑色', '1', '2', '3', '2', '1', '0', '0', '9双/箱', '140', '1260', '1-140', '03A');
INSERT INTO `sizenum` VALUES (28, 'M24020501', '黑红白', '1', '2', '1', '1', '1', '2', '1', '9双/箱', '293', '2540', '101-393', '01A');
INSERT INTO `sizenum` VALUES (29, 'S24021203', '白色', '1', '2', '2', '2', '1', '1', '1', '10双/箱', '354', '3540', '1-354', '02B');
INSERT INTO `sizenum` VALUES (30, 'M23051204', '白色', '1', '2', '2', '2', '2', '1', '0', '10双/箱', '112', '1120', '1-112', '02A');
INSERT INTO `sizenum` VALUES (31, 'L23020421', '卡其色', '0', '1', '1', '1', '1', '1', '0', '5双/箱', '912', '4560', '501-1412', '05A');
INSERT INTO `sizenum` VALUES (32, 'J23010415', '黑色', '1', '2', '3', '2', '1', '0', '0', '9双/箱', '360', '3240', '1-360', '03A');
INSERT INTO `sizenum` VALUES (33, 'K24010413', '帆布蓝色', '1', '3', '2', '2', '1', '1', '0', '10双/箱', '423', '4230', '1-423', '04A');
INSERT INTO `sizenum` VALUES (34, 'K24021025', '酒红色', '3', '3', '2', '1', '1', '0', '0', '10双/箱', '120', '1200', '1-120', '02C');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '出生年月',
  `department_id` int(11) NULL DEFAULT NULL COMMENT '所属部门ID',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '禁用状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES (7, 'xiaoming', '123', '小明', 'http://localhost:9090/files/1707752171829-24010911.jpg', 'STAFF', '男', '13858234765', '13858234765@qq.com', '1994-02-09', NULL, 0);
INSERT INTO `staff` VALUES (8, 'xiaohong', '123', '小红', 'http://localhost:9090/files/1710933794436-24010910.jpg', 'STAFF', '女', '13872364023', 'xiaohong@qq.com', '1993-03-16', NULL, 0);
INSERT INTO `staff` VALUES (9, 'xiaohua', '123', '小华', 'http://localhost:9090/files/1713164289774-24010903.jpg', 'STAFF', '男', '137238943021', 'xiaohua0921@163.vip.com', '1990-04-03', NULL, 0);

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '供应商地址',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人电话',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '可用' COMMENT '状态',
  `payment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付期限',
  `things` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '供应材料',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '供应商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES (1, '华顺鞋材', NULL, '小王', '13526738935', '可用', '半月一结', '鞋底');
INSERT INTO `supplier` VALUES (2, '阿隆鞋材', NULL, '阿隆', '18972340921', '可用', '三月一结', '鞋带');

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : 49.232.2.175
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : 49.232.2.175:3306
 Source Schema         : rms

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 07/02/2023 23:00:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` varbinary(16) NOT NULL COMMENT '备注ID',
  `interview_id` varbinary(16) NOT NULL COMMENT '面试表ID',
  `comment_type` int NOT NULL COMMENT '备注类型[0简历上传备注 1简历Review备注 2面试反馈备注]',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注内容',
  `creater` varbinary(16) NOT NULL COMMENT '创建人ID',
  `creat_date` datetime NOT NULL COMMENT '创建时间',
  `modifyer` varbinary(16) NULL DEFAULT NULL COMMENT '修改人ID',
  `modify_date` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`  (
  `id` varbinary(16) NOT NULL COMMENT '部门ID',
  `company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary_type`;
CREATE TABLE `t_dictionary_type`  (
  `id` varbinary(16) NOT NULL COMMENT '数据字典类型ID',
  `type_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据字典类型值',
  `comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据字典类型备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code_unique`(`type_code` ASC) USING BTREE COMMENT 'type_code唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_dictionary_value
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary_value`;
CREATE TABLE `t_dictionary_value`  (
  `id` varbinary(16) NOT NULL COMMENT '数据字典值ID',
  `type_id` varbinary(16) NOT NULL COMMENT '数据字典类型ID',
  `value_key` int NOT NULL COMMENT '数据字典值代码',
  `value_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据字典值',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据字典值备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code_value_key_unique`(`type_id` ASC, `value_key` ASC) USING BTREE COMMENT 'type_code和value_key不能同时重复'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_interview
-- ----------------------------
DROP TABLE IF EXISTS `t_interview`;
CREATE TABLE `t_interview`  (
  `id` varbinary(16) NOT NULL COMMENT '面试ID',
  `resume_id` varbinary(16) NOT NULL COMMENT '对应简历表ID',
  `date` datetime NULL DEFAULT NULL COMMENT '面试时间',
  `interviewer_1` varbinary(16) NULL DEFAULT NULL COMMENT '面试官1,用户表ID',
  `interviewer_2` varbinary(16) NULL DEFAULT NULL COMMENT '面试官2,用户表ID',
  `interviewer_3` varbinary(16) NULL DEFAULT NULL COMMENT '面试官3,用户表ID',
  `interviewer_4` varbinary(16) NULL DEFAULT NULL COMMENT '面试官4,用户表ID',
  `status` int NOT NULL COMMENT '面试状态，对应数据字典值表',
  `creater` varbinary(16) NOT NULL COMMENT '创建人ID',
  `creat_date` datetime NOT NULL COMMENT '创建时间',
  `modifyer` varbinary(16) NULL DEFAULT NULL COMMENT '修改人ID',
  `modify_date` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `reviewer` varbinary(16) NULL DEFAULT NULL COMMENT 'Review人,用户表ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_reviewer`(`reviewer` ASC) USING BTREE COMMENT '审阅人索引，对应t_user表id',
  INDEX `idx_modity_date`(`modify_date` ASC) USING BTREE COMMENT '修改时间索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_interview_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_interview_feedback`;
CREATE TABLE `t_interview_feedback`  (
  `id` varbinary(16) NOT NULL COMMENT '面试反馈ID',
  `interview_id` varbinary(16) NOT NULL COMMENT '对应面试表ID',
  `interviewer` varbinary(16) NOT NULL COMMENT '评价人，对应用户表ID',
  `result` int NULL DEFAULT NULL COMMENT '面试结果:是否通过,数据字典值',
  `ref_dept_id` varbinary(16) NULL DEFAULT NULL COMMENT '参考部门ID',
  `ref_position` int NULL DEFAULT NULL COMMENT '推荐职位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_interview_feedback_score
-- ----------------------------
DROP TABLE IF EXISTS `t_interview_feedback_score`;
CREATE TABLE `t_interview_feedback_score`  (
  `id` varbinary(16) NOT NULL COMMENT '面试反馈得分ID',
  `interview_feedback_id` varbinary(16) NOT NULL COMMENT '对应面试表反馈表ID',
  `item` int NOT NULL COMMENT '得分项，对应数据字典值表',
  `score` int NOT NULL COMMENT '分数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` varbinary(16) NOT NULL COMMENT '权限ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限代码',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '前端路径',
  `is_menu` int NOT NULL COMMENT '是否是前端路径',
  `order_by` int NOT NULL COMMENT '排序依据',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_by`(`order_by` ASC) USING BTREE COMMENT '排序索引',
  INDEX `idx_is_menu`(`is_menu` ASC) USING BTREE COMMENT '菜单索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_resume
-- ----------------------------
DROP TABLE IF EXISTS `t_resume`;
CREATE TABLE `t_resume`  (
  `id` varbinary(16) NOT NULL COMMENT '简历ID',
  `file_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `sex` int NOT NULL COMMENT '性别,数据字典值表',
  `birthday` date NOT NULL COMMENT '生日',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `tel` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `education` int NOT NULL COMMENT '学历,数据字典值表',
  `grad_date` date NULL DEFAULT NULL COMMENT '毕业时间',
  `grad_school` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业学校',
  `major` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `tech_ability` int NULL DEFAULT NULL COMMENT '技术能力,数据字典值表',
  `lang_ability` int NULL DEFAULT NULL COMMENT '语言能力,数据字典值表',
  `other_ability` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其它能力',
  `work_experience` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作经验',
  `self_assessment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自我评价',
  `source` int NULL DEFAULT NULL COMMENT '简历来源,数据字典值表',
  `internal_recommender` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内推人名字',
  `intended_dept` varbinary(16) NULL DEFAULT NULL COMMENT '意向部门,部门表ID',
  `obey` int NULL DEFAULT NULL COMMENT '是否服从分配,数据字典值表',
  `creater` varbinary(16) NOT NULL COMMENT '创建人ID',
  `creat_date` date NOT NULL COMMENT '创建时间',
  `modifyer` varbinary(16) NULL DEFAULT NULL COMMENT '修改人ID',
  `modify_date` date NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` varbinary(16) NOT NULL COMMENT '角色ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名',
  `comment` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_name`(`name` ASC) USING BTREE COMMENT '角色名索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `id` varbinary(16) NOT NULL COMMENT '主键ID',
  `role_id` varbinary(16) NOT NULL COMMENT '角色ID',
  `permission_id` varbinary(16) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE COMMENT '角色id索引',
  INDEX `idx_permission_id`(`permission_id` ASC) USING BTREE COMMENT '权限id索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varbinary(16) NOT NULL COMMENT '用户ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `sign_in_date` date NOT NULL COMMENT '注册时间',
  `dept_id` varbinary(16) NULL DEFAULT NULL COMMENT '所属部门，部门表ID',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `status` int NOT NULL COMMENT '状态,数据字典值表ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_unique`(`username` ASC) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` varbinary(16) NOT NULL COMMENT '用户角色ID',
  `user_id` varbinary(16) NOT NULL COMMENT '用户ID',
  `role_id` varbinary(16) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE COMMENT '角色id索引',
  INDEX `idx_user_id_role_id`(`user_id` ASC, `role_id` ASC) USING BTREE COMMENT '用户角色索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

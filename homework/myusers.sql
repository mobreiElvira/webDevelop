/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : myusers

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 05/12/2025 22:35:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for questions
-- ----------------------------
DROP TABLE IF EXISTS `questions`;
CREATE TABLE `questions`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `questionText` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answer1Text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answer1Correct` tinyint(1) NOT NULL DEFAULT 0,
  `answer2Text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answer2Correct` tinyint(1) NOT NULL DEFAULT 0,
  `answer3Text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answer3Correct` tinyint(1) NOT NULL DEFAULT 0,
  `answer4Text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `answer4Correct` tinyint(1) NOT NULL DEFAULT 0,
  `isDelete` tinyint NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of questions
-- ----------------------------
INSERT INTO `questions` VALUES (1, 'What is the capital of France?', 'Paris', 1, 'London', 0, 'Berlin', 0, 'Madrid', 0, 0, '2025-11-10 17:15:02', '2025-11-10 17:15:02');
INSERT INTO `questions` VALUES (2, 'which of the following is not a programming language?', 'Java', 0, 'Apple', 1, 'Python', 0, 'Javascript', 0, 1, '2025-11-10 17:27:38', '2025-11-10 17:27:38');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `updateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `isDelete` tinyint NULL DEFAULT NULL,
  `userRole` int NULL DEFAULT 0 COMMENT '表示用户角色， 0 普通用户， 1 管理员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', '9fea45d6c22f8cb65d01b3b32b5cfcc9', '2025-11-10 11:14:31', '2025-11-09 23:33:21', 0, 0);
INSERT INTO `user` VALUES (2, 'fromapifox', 'abb283b0200e38c5676e51f4ee23f28b', '2025-11-10 11:23:13', '2025-11-09 23:51:51', 10, 0);
INSERT INTO `user` VALUES (3, 'apifoxafterservice', 'abb283b0200e38c5676e51f4ee23f28b', '2025-11-10 11:24:31', '2025-11-10 00:06:39', 0, 0);
INSERT INTO `user` VALUES (4, 'testuser', 'password123', '2025-11-10 10:58:36', '2025-11-10 10:58:36', 0, 0);
INSERT INTO `user` VALUES (5, 'toms', 'password123', '2025-12-05 11:55:02', '2025-12-05 11:55:02', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;

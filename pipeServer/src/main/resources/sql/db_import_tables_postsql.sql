/*
Navicat PGSQL Data Transfer

Source Server         : LocalPostgreSQL
Source Server Version : 90103
Source Host           : localhost:5432
Source Database       : db_import
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90103
File Encoding         : 65001

Date: 2016-09-01 11:57:31
*/


-- ----------------------------
-- Table structure for t_sheet_table
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_sheet_table";
CREATE TABLE "public"."t_sheet_table" (
"id" int4 NOT NULL,
"table_id" int4,
"file_name" varchar(255) COLLATE "default" NOT NULL,
"sheet_name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for t_table
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_table";
CREATE TABLE "public"."t_table" (
"id" int4 NOT NULL,
"ip" varchar(255) COLLATE "default",
"port" int4,
"db" varchar(255) COLLATE "default",
"schema" varchar(255) COLLATE "default",
"table" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for t_table_structure
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_table_structure";
CREATE TABLE "public"."t_table_structure" (
"id" int8 NOT NULL,
"table_id" int4,
"field_name" varchar(255) COLLATE "default",
"column_name" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table t_sheet_table
-- ----------------------------
ALTER TABLE "public"."t_sheet_table" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_table
-- ----------------------------
ALTER TABLE "public"."t_table" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_table_structure
-- ----------------------------
ALTER TABLE "public"."t_table_structure" ADD PRIMARY KEY ("id");

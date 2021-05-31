# -*- coding: utf-8 -*-
# @Time    : 2021/5/2 7:43 下午
# @Author  : lichengyu
# @File    : TableTool.py
import os
import sqlite3

from Tools.CommonTool import CommonTool
from Tools.LoggerTool import information
from sklearn.metrics.pairwise import cosine_similarity


class TableTool(object):
    def desc(self, db, table):
        cu = db.cursor()
        cu.execute(f"PRAGMA table_info({table})")
        print(cu.fetchall())
        db.commit()

    def connect(self):
        """
        连接数据库
        :return:
        """
        root = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
        db = ct.read_yaml(root + "/config/config.yaml").get("DB_path")
        cx = sqlite3.connect(db)
        information.info("连接成功")
        return cx

    def create_table_early_data(self, db):
        """
        创建data表
        :return:
        """
        cu = db.cursor()
        fields = ""
        for i in range(1, 73):
            fields = fields + ",location" + str(i)

        cu.execute(f"create table early_data (id text,eye text,sightingLoseRatio text,falseNegativeRatio text,"
                   f"falsePositiveRatio text{fields},primary key (id,eye))")
        db.commit()
        information.info("创建成功")

    def create_table_late_data(self, db):
        """
        创建data表
        :return:
        """

        cu = db.cursor()
        fields = ""
        for i in range(1, 101):
            fields = fields + ",location" + str(i)

        cu.execute(f"create table late_data (id text,eye text,sightingLoseRatio text,falseNegativeRatio text,"
                   f"falsePositiveRatio text{fields},primary key (id,eye))")
        db.commit()
        information.info("创建成功")

    def create_table_user(self, db):
        """
        创建user表
        :return:
        """
        cu = db.cursor()
        cu.execute(f"create table user (id text,passWord text,primary key (id))")
        db.commit()
        information.info("创建成功")

    def insert(self, db, table, val: str):
        """
        插入数据
        :return:
        """
        cu = db.cursor()
        val = val.replace(",", "\',\'")
        cu.execute(f"insert into {table} values('{val}')")
        db.commit()
        information.info("插入成功")

    def register(self, db, uid, password):
        """
        注册id
        :return:
        """
        cu = db.cursor()
        try:
            cu.execute(f"insert into user values('{uid}','{password}')")
            db.commit()
            information.info("插入成功")
            return True
        except Exception:
            return False

    def select(self, db, table, Id, eye):
        """
        查询数据
        :return:
        """
        cu = db.cursor()
        cu.execute(f"select * from {table} where id='{Id}' and eye='{eye}'")
        arr = cu.fetchall()
        print(arr)
        if len(arr) > 0:
            return ",".join(arr[0])

    def calculate(self, db, table, val: str):
        """
        计算比率
        :param val:
        :param db:
        :param eye:
        :return:
        """
        cu = db.cursor()
        a = val.split(",")
        fields = f"eye"
        max_range = 73 if table == "early_data" else 101
        for i in range(1, max_range):
            fields = fields + f",cast(sum(case when location{i}='{a[i + 3]}' then 1 else 0 end)*1./count(*) as char)"
        print(fields)
        cu.execute(f"select {fields} from {table} where eye='{a[1]}'")
        arr = cu.fetchall()
        print(arr)
        if len(arr) > 0:
            return ",".join(arr[0])

    def update(self, db, table, val: str):
        """
        更新数据
        :return:
        """
        cu = db.cursor()
        arr = val.split(",")
        fields = f"sightingLoseRatio='{arr[2]}',falseNegativeRatio='{arr[3]}',falsePositiveRatio='{arr[4]}'"
        max_range = 73 if table == "early_data" else 101
        for i in range(1, max_range):
            fields = fields + f",location{i}='{arr[i + 3]}'"
        try:
            cu.execute(
                f"update {table} set {fields} where id = '{arr[0]}' and eye='{arr[1]}'")
            db.commit()
            return True
        except Exception:
            return False

    def delete(self, db, table, Id):
        """
        删除数据
        :return:
        """
        cu = db.cursor()
        cu.execute(f"delete from {table} where id = '{Id}'")
        db.commit()
        information.info("删除成功")

    def drop(self, db, table):
        """
        删除表
        :return:
        """
        cu = db.cursor()
        cu.execute(f"DROP TABLE {table}")
        db.commit()
        information.info("删除成功")

    def check(self, db, Id, passWord):
        """
        检验用户名和密码是否正确
        :param db:
        :return:
        """
        cu = db.cursor()
        cu.execute(f"select * from user where id='{Id}' and passWord='{passWord}'")
        arr = cu.fetchall()
        print(arr)
        if len(arr) > 0:
            return True
        else:
            return False

    def similar(self, a, b):
        """
        余弦值相似算法
        cosθ = a · b / ( ||a||*||b|| )=(x1, y1) · (x2, y2) / (sqrt(x1^2+ y1^2) + sqrt(x2^2+ y2^2))
        :param a:
        :param b:
        :return:
        """
        s = cosine_similarity([a], [b])
        return s[0][0]

    def avg(self, db, table, eye):
        """
        求平均值
        :return:
        """
        cu = db.cursor()
        max_range = 73 if table == "early_data" else 101
        fields = ""
        for i in range(1, max_range):
            fields = fields + f",avg(location{i})"
        fields = fields[1:]
        print(fields)
        cu.execute(f"select {fields} from {table} where eye='{eye}'")
        arr = cu.fetchall()
        print(arr)
        if len(arr) > 0:
            return arr[0]


ct = CommonTool()
if __name__ == '__main__':
    con = TableTool().connect()
    # TableTool().create_table_early_data(con)
    # TableTool().create_table_late_data(con)
    # TableTool().drop(con, 'people')
    # TableTool().desc(con)
    # TableTool().select(con, "410821199904050135", "左眼")
    # TableTool().delete(con, "410821199904050135")

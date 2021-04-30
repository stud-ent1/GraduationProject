# -*- coding: utf-8 -*-
# @Time    : 2021/4/22 11:04 上午
# @Author  : lichengyu
# @File    : Main.py.py
from typing import Optional

from fastapi import FastAPI
import sqlite3

app = FastAPI()


# uvicorn Main:app --host '192.168.1.109' --reload

@app.get("/select")
def select_val(Id, eye):
    cx = open_db()
    cu = cx.cursor()
    cu.execute(f"select * from people where id='{Id}' and eye='{eye}'")
    arr = cu.fetchall()
    if len(arr) > 0:
        return ",".join(arr[0])


@app.get("/insert")
def insert_val(Id, eye, sightingLoseRatio, falseNegativeRatio, falsePositiveRatio):
    cx = open_db()
    cu = cx.cursor()
    print(Id + eye + sightingLoseRatio + falsePositiveRatio + falseNegativeRatio)
    try:
        cu.execute(f"insert into people values('{Id}','{eye}', '{sightingLoseRatio}', '{falseNegativeRatio}',"
                   f"'{falsePositiveRatio}')")
    except sqlite3.IntegrityError:
        print("执行更新")
        cu.execute(
            f"update people set sightingLoseRatio='{sightingLoseRatio}',falseNegativeRatio='{falseNegativeRatio}',"
            f"falsePositiveRatio='{falseNegativeRatio}' where id = '{Id}' and eye='{eye}'")
    cx.commit()
    return "插入成功"


def open_db():
    cx = sqlite3.connect("/Users/lichengyu/Desktop/GraduationProject/DB/result.db")
    return cx


def crete_table(cx):
    cu = cx.cursor()
    cu.execute('create table people (id text,eye text,sightingLoseRatio text,falseNegativeRatio text,'
               'falsePositiveRatio text,primary key (id,eye))')
    cx.commit()


def update_val(cx):
    cu = cx.cursor()
    cu.execute("update people set name='name2' where id = 0")
    cx.commit()


def del_val(cx):
    cu = cx.cursor()
    cu.execute("delete from catalog where id = 1")
    cx.commit()


def desc_table(cx):
    cu = cx.cursor()
    cu.execute("PRAGMA table_info(people)")
    print(cu.fetchall())
    cx.commit()


def drop_table(cx):
    cu = cx.cursor()
    cu.execute("DROP TABLE catalog")
    cx.commit()


if __name__ == '__main__':
    cx = open_db()
    # crete_table(cx)
    # desc_table(cx)
    # update_val(cx)
    # del_val(cx)
    # select_val()
    # drop_table(cx)
    cx.close()

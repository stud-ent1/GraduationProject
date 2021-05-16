# -*- coding: utf-8 -*-
# @Time    : 2021/5/3 9:26 下午
# @Author  : lichengyu
# @File    : Service.py
import json
import sqlite3
from fastapi.responses import JSONResponse
from fastapi import FastAPI

from sql.TableTool import TableTool

app = FastAPI()


# uvicorn service.Service:app --host '192.168.1.100' --reload
@app.get("/insert")
def insert_val(val):
    con = tt.connect()
    tmp = json.loads(val)
    if tmp.get("program") == "24-2(适用于前期)":
        table = "early_data"
    else:
        table = "late_data"
    try:
        tt.insert(con, table, tmp.get("val"))
    except sqlite3.IntegrityError:
        print("执行更新")
        tt.update(con, table, tmp.get("val"))


@app.get("/register")
def register_val(val):
    con = tt.connect()
    tmp = json.loads(val)
    if tt.register(con, tmp.get("id"), tmp.get("passWord")):
        return JSONResponse(content={"status": "success"})
    else:
        return JSONResponse(content={"status": "fail"})


@app.get("/check")
def check_val(val):
    con = tt.connect()
    tmp = json.loads(val)
    select_result = {"status": tt.check(con, tmp.get("id"), tmp.get("passWord"))}
    return JSONResponse(content=select_result)


@app.get("/select")
def select_val(val):
    con = tt.connect()
    tmp = json.loads(val)
    if tmp.get("program") == "24-2(适用于前期)":
        table = "early_data"
    else:
        table = "late_data"
    select_result = {"eye": tmp.get("eye"), "GrayScale": tt.select(con, table, tmp.get("id"), tmp.get("eye"))}
    if select_result["GrayScale"] is not None:
        select_result["RatioScale"] = tt.calculate(con, table, select_result["GrayScale"])
    else:
        select_result["RatioScale"] = None
    return JSONResponse(content=select_result)


tt = TableTool()

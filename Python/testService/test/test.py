# -*- coding: utf-8 -*-
# @Time    : 2021/5/3 9:58 下午
# @Author  : lichengyu
# @File    : test.py
import base64

if __name__ == '__main__':
    a = "阿我恶意无语"
    jia = base64.b64encode(a.encode())
    print(jia.decode())
    jie = base64.b64decode(jia)
    print(jie.decode())

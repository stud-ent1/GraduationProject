# -*- coding: utf-8 -*-
# @Time    : 2021/5/2 8:06 下午
# @Author  : lichengyu
# @File    : CommonTool.py
import base64
import subprocess

import yaml

from Tools.LoggerTool import information


class CommonTool(object):
    def exec_command(self):
        """
        执行liunx指令并获取返回值，并返回
        :return:
        """
        information.info("执行命令:" + self)
        p = subprocess.run(self, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        if p.returncode == 0:
            if p.stdout != b'':
                information.info(f"命令输出:{p.stdout.decode(encoding='utf-8')}")
                return p.stdout.decode(encoding='utf-8')
            else:
                information.info(f"命令输出:执行成功")
        else:
            information.error(f"错误信息:{p.stderr.decode(encoding='utf-8')}")
            raise Exception()

    def read_yaml(self, filename):
        """
        读取yaml文件，并返回文件的信息
        :param filename: 文件名
        :param self:
        :return: 字典
        """
        with open(filename, 'rb') as f:
            # yaml文件通过---分节，多个节组合成一个列表
            data = yaml.safe_load(f)
        return data

    def encode(self, string):
        return base64.b64encode(string.encode()).decode()

    def decode(self, string):
        return base64.b64decode(string).decode()

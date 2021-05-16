# -*- coding: utf-8 -*-
# @Time    : 2021/5/2 8:13 下午
# @Author  : lichengyu
# @File    : LoggerTool.py
import logging


class GetLog(object):
    def __init__(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
        self.logger = logging.getLogger(__name__)


information = GetLog().logger

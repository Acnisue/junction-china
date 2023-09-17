import re

import requests
from bs4 import BeautifulSoup

# 保存图片路径
path = "data/empty/"
# 编码
keyword = "三轮车"
url = f"https://image.baidu.com/search/index?tn=baiduimage&word={keyword}"
header = {
    "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36",
    "Cookie": "BIDUPSID=A276975C2AC06923DD2A8DF51F26CB27; PSTM=1683958263; BAIDUID=A276975C2AC0692389AF408E898136F3:FG=1; ZFY=bk1OH0ZcywChbKpjsL:AuI3T4d:B9:Az17712HzaB7Zw7Y:C; BAIDUID_BFESS=A276975C2AC0692389AF408E898136F3:FG=1; BDRCVFR[dG2JNJb_ajR]=mk3SLVN4HKm; userFrom=null; BDRCVFR[-pGxjrCMryR]=mk3SLVN4HKm; ab_sr=1.0.1_Zjk0MDUyZjAxZGM2YmM5NWVjZmFkOTZmNGUxMTQ4ZTU2OGIwMTY2M2NkNzIyYzZhZjkwNGYzYTg1M2JmNjc3MTUxODc1NzNjMGE3ZWI4YjM0MGZiYmRhZGU3NDExMmQ5NGUxYjI5YmI2Y2Q3ODgwODUzY2E4NzA3ZjliODJmNjRkNWVlYmQ5YmNiM2YxYjcwNzE0YzZjMTlkMjVmYWVkMA=="}


# 爬取百度图片
def get_img():
    response = requests.get(url,headers=header)
    # print(response.text)
    soup = BeautifulSoup(response.text, 'html.parser')
    images = soup.find_all('empty')
    print(images)
    # print(images)
    # for image in images:
    #     img_url = image['data-imgurl']
    #     print(img_url)
    #     # download_img(img_url, path)


# download image
def download_img(url, path):
    response = requests.get(url, headers=header)
    with open(path + get_uuid() + ".jpg", "wb") as f:
        f.write(response.content)


# uuid
def get_uuid():
    import uuid
    return str(uuid.uuid1())


# -*- coding: utf-8 -*-
"""
Created on Wed Mar 29 10:17:50 2023
@author: MatpyMaster
"""
import requests
import os
import re


def get_images_from_baidu(keyword, page_num, save_dir):
    header = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36'}
    # 请求的 url
    url = 'https://image.baidu.com/search/acjson?'
    n = 0
    for pn in range(0, 30 * page_num, 30):
        # 请求参数
        param = {'tn': 'resultjson_com',
                 'logid': '7603311155072595725',
                 'ipn': 'rj',
                 'ct': 201326592,
                 'is': '',
                 'fp': 'result',
                 'queryWord': keyword,
                 'cl': 2,
                 'lm': -1,
                 'ie': 'utf-8',
                 'oe': 'utf-8',
                 'adpicid': '',
                 'st': -1,
                 'z': '',
                 'ic': '',
                 'hd': '',
                 'latest': '',
                 'copyright': '',
                 'word': keyword,
                 's': '',
                 'se': '',
                 'tab': '',
                 'width': '',
                 'height': '',
                 'face': 0,
                 'istype': 2,
                 'qc': '',
                 'nc': '1',
                 'fr': '',
                 'expermode': '',
                 'force': '',
                 'cg': '',  # 这个参数没公开，但是不可少
                 'pn': pn,  # 显示：30-60-90
                 'rn': '90',  # 每页显示 30 条
                 'gsm': '1e',
                 '1618827096642': ''
                 }
        request = requests.get(url=url, headers=header, params=param)
        if request.status_code == 200:
            print('Request success.')
        request.encoding = 'utf-8'
        # 正则方式提取图片链接
        html = request.text
        image_url_list = re.findall('"thumbURL":"(.*?)",', html, re.S)

        if not os.path.exists(save_dir):
            os.makedirs(save_dir)

        for image_url in image_url_list:
            image_data = requests.get(url=image_url, headers=header).content
            with open(os.path.join(save_dir, f'{n:06d}.jpg'), 'wb') as fp:
                fp.write(image_data)
            n = n + 1


if __name__ == "__main__":
    keyword = '载人三轮车'
    page_num = 1
    page_num = int(page_num)
    save_dir = './data/people'
    for i in range(1, 10):
        get_images_from_baidu(keyword, i, save_dir)
        print(f'第 {i} 页下载完成')

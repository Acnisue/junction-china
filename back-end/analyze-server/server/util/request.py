import json
import uuid

import requests

bad_json_formate = json.dumps({
    'code': 400,
    'msg': 'bad json formate',
    'data': ''
})

server_error = json.dumps({
    'code': 500,
    'msg': 'server_error',
    'data': ''
})


def parse(json_str):
    data = json.loads(json_str)
    try:
        if data['code'] == 200:
            return data['data']
    except Exception as e:
        print(e)
        return None


def download(url, path):
    response = requests.get(url)
    type = response.headers['Content-Type']
    fileName = ''
    if type == 'image/jpeg':
        fileName = id() + '.jpg'
    elif type == 'image/png':
        fileName = id() + '.png'
    with open(path + fileName, 'wb') as f:
        f.write(response.content)
    return path+fileName


def id():
    # uuid
    return str(uuid.uuid1())

# import concurrent
# import json
# import socket
# from concurrent import futures
#
import json
import os.path
import uuid

from server.util import request, recognize
#
# max_thread = 10
# listen_port = 8888
# max_bytes = 1024 * 1024 * 1024
#
#
# def handle_client(client_socket, client_address):
#     print(f'客户端 {client_address} 连接成功！')
#
#     # 处理客户端请求
#     data = client_socket.recv(max_bytes)  # 接收数据
#     if data:
#         try:
#             print(f'接收到来自客户端的数据：{data.decode()}')
#             result = request.parse(data.decode())
#             if result is None:
#                 client_socket.send(request.bad_json_formate.encode())
#                 client_socket.close()
#             else:
#                 url = result['url']
#                 fileName = request.download(url, '../server/img/')
#                 result = recognize.vision(fileName)
#                 # 编码字符串
#                 print(f"result:{result}")
#                 client_socket.send(json.dumps(result.tolist()).encode())
#                 client_socket.close()
#         except Exception as e:
#             print(e)
#             client_socket.send(request.server_error.encode())
#             client_socket.close()
#         # 在这里编写处理客户端请求的逻辑
#
#     # 关闭与客户端的连接
#     client_socket.close()
#
#
# def main():
#     # 创建一个 TCP 套接字
#     server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#     # 绑定 IP 地址和端口号
#     server_address = ('172.16.129.18', listen_port)
#     server_socket.bind(server_address)
#     # 监听连接
#     server_socket.listen(5)
#
#     print('服务器启动，等待客户端连接...')
#
#     # 创建一个最大容纳5个线程的线程池
#     executor = concurrent.futures.ThreadPoolExecutor(max_workers=max_thread)
#
#     while True:
#         # 接受客户端连接
#         client_socket, client_address = server_socket.accept()
#         # 使用线程池处理客户端连接
#         futures = executor.submit(handle_client, client_socket, client_address)
#         try:
#             futures.result()
#         except Exception as e:
#             print(e)
#
#
# if __name__ == '__main__':
#     main()
from flask import Flask, send_file, request, make_response

app = Flask(__name__)

root_dir = '/Users/robot/Desktop/Projects/Python/tricycle-recoginze/server/runs/detect/exp'


# 返回文件
@app.route('/ai/file', methods=['GET'])
def download_file():
    file_path = os.path.join(root_dir, request.args.get('fileName'))
    return send_file(file_path, as_attachment=True)


@app.route("/ai/vision", methods=['POST'])
def vision():
    # 获取图片
    file = request.files.get('file')
    file_type = 'img'
    fileName = ''
    # 保存图片
    if file.content_type == 'image/jpeg' or file.content_type == 'image/jpg':
        fileName = str(uuid.uuid1()) + '.jpg'
    if file.content_type == 'image/png':
        fileName = str(uuid.uuid1()) + '.png'
    if file.content_type == 'video/mp4':
        fileName = str(uuid.uuid1()) + '.mp4'
        file_type = 'video'
    file.save(os.path.join(root_dir, fileName))
    # 检测图片
    result = None
    if file_type == 'img':
        result, dealFileName = recognize.vision(path=root_dir, photo=fileName)
    if file_type == 'video':
        result, dealFileName = recognize.visionVideo(path=root_dir, video=fileName)
    # # # 返回结果
    response = make_response(json.dumps(result.tolist()))
    response.headers['Content-Type'] = 'application/json'
    return response


@app.route("/ai/vision/result", methods=['POST'])
def visionResult():
    # 获取图片
    file = request.files.get('file')
    file_type = 'img'
    # 保存图片
    fileName = ''
    if file.content_type == 'image/jpeg' or file.content_type == 'image/jpg':
        fileName = str(uuid.uuid1()) + '.jpg'
    if file.content_type == 'image/png':
        fileName = str(uuid.uuid1()) + '.png'
    if file.content_type == 'video/mp4':
        fileName = str(uuid.uuid1()) + '.mp4'
        file_type = 'video'
    file.save(os.path.join(root_dir, fileName))
    # 检测图片
    re = None
    dealFileName = None
    if file_type == 'img':
        re, dealFileName = recognize.vision(path=root_dir, photo=fileName)
    if file_type == 'video':
        re, dealFileName = recognize.visionVideo(path=root_dir, video=fileName)
    # # 返回结果
    result = {
        'code': 200,
        'msg': 'success',
        'data': {
            'result': re.tolist(),
            'url': '/ai/file?fileName=' + dealFileName
        }
    }
    response = make_response(json.dumps(result))
    response.headers['Content-Type'] = 'application/json'
    return response


if __name__ == '__main__':
    app.run(host="172.16.129.18", port=5000)

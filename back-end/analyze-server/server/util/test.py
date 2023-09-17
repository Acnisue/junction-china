import subprocess

command = ['python', 'detect.py', '--source', '/Users/robot/Desktop/Projects/Python/tricycle-recoginze/server/img/test-1.mp4', '--weights', '/Users/robot/Desktop/Projects/Python/tricycle-recoginze/vision/yolov5/train/best.pt', '--conf', '0.5', '--save-txt', '--save-conf']

# 执行命令并获取输出
process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
output, error = process.communicate()

# 输出命令的执行结果
if output:
    print(output.decode('utf-8'))
# import cv2
# import numpy as np
# import torch
# from PIL import Image
#
#
# model = torch.hub.load('/Users/robot/Desktop/Projects/Python/tricycle-recoginze/vision/yolov5', 'custom',
#                        path='/Users/robot/Desktop/Projects/Python/tricycle-recoginze/vision/yolov5/train/best.pt',
#                        source='local')
#
# # 视频输入
# cap = cv2.VideoCapture("/Users/robot/Desktop/Projects/Python/tricycle-recoginze/server/runs/detect/exp/bb3e5784-54a9-11ee-8295-72b95aced384.mp4")
#
# # 定义输出视频的参数
# output_path = './output.mp4'
# fps = cap.get(cv2.CAP_PROP_FPS)
# width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
# height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
# fourcc = cv2.VideoWriter_fourcc(*'mp4v')
# out = cv2.VideoWriter(output_path, fourcc, fps, (width, height))
#
# while cap.isOpened():
#     # 读取视频帧
#     ret, frame = cap.read()
#     if not ret:
#         break
#
#     # 将图像从 OpenCV 格式转换为 PIL 格式
#     pil_image = Image.fromarray(cv2.cvtColor(frame, cv2.COLOR_BGR2RGB))
#
#     # 使用 YOLOv5 模型进行目标检测
#     results = model(pil_image)
#
#     # 在图像上绘制检测框
#     annotated_image = results.render()
#
#     # 在 OpenCV 格式图像上绘制目标检测结果
#     annotated_frame = cv2.cvtColor(np.array(annotated_image), cv2.COLOR_RGB2BGR)
#
#     # 将帧写入输出视频文件
#     out.write(annotated_frame)
#
# # 释放资源
# cap.release()
# out.release()
# cv2.destroyAllWindows()
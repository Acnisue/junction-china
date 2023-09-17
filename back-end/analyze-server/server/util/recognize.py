import os.path
import uuid

import cv2
import numpy as np
import torch
from PIL import Image
from torchvision import transforms


def vision(path, photo):
    model = torch.hub.load('/Users/robot/Desktop/Projects/Python/tricycle-recoginze/vision/yolov5/', 'custom',
                           path='/Users/robot/Desktop/Projects/Python/tricycle-recoginze/vision/yolov5/train/best.pt',
                           source='local')

    im = Image.open(os.path.join(path, photo))

    results = model(im)
    results.save(exist_ok=True)
    # results.show()
    # 拿到结果
    return results.pandas().xyxy[0]['name'].values, photo
    # Results
    # print(type(results.print()))  # or .show(), .save(), .crop(), .pandas(), etc.


def visionVideo(path, video):
    # 加载预训练权重
    model = torch.hub.load('/Users/robot/Desktop/Projects/Python/tricycle-recoginze/vision/yolov5', 'custom',
                           path='/Users/robot/Desktop/Projects/Python/tricycle-recoginze/vision/yolov5/train/best.pt',
                           source='local')

    # 视频输入
    video_format = video.split('.')[-1]  # 获取视频文件的格式
    suffix = path.split('.')[-1]
    deal_data = str(uuid.uuid1()) + '.' + suffix
    if video_format == 'mp4':
        cap = cv2.VideoCapture(os.path.join(path, video))
    else:
        raise Exception("Invalid video format. Only support mp4 format.")

    results = None

    while cap.isOpened():
        # 参数ret 为True 或者False,代表有没有读取到图片帧
        # 第二个参数frame表示截取到一帧的图片
        ret, frame = cap.read()
        # 将图片帧输入模型
        results = model(frame)
        # 输出目标检测结果到屏幕
        # cv2.imshow('YOLO', np.squeeze(results.render()))
        # # 退出
        # if cv2.waitKey(10) & 0xFF == ord('q'):
        #     break
        print(results.pandas().xyxy[0]['name'].values)
    results.save(exist_ok=True)
    # 释放资源
    cap.release()
    cv2.destroyAllWindows()
    return results.pandas().xyxy[0]['name'].values, video


if __name__ == '__main__':
    visionVideo('/Users/robot/Desktop/Projects/Python/tricycle-recoginze/server/img/test.mp4')

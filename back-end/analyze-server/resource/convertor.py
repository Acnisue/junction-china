import os
import xml.etree.ElementTree as ET

# 定义目标类别列表，按照LabelImg中的编号顺序排列
classes = ['empty', 'only_driver', 'overloading', 'passenger', 'driver']

# 定义输入和输出文件路径
input_dir = '/Users/robot/Desktop/Projects/Python/tricycle-recoginze/resource/data/people_label'
output_dir = '/Users/robot/Desktop/Projects/Python/tricycle-recoginze/resource/data/people_label_yolov'

# 循环读取每个XML文件并进行转换
for filename in os.listdir(input_dir):
    if not filename.endswith('.xml'):
        continue
    # 解析XML文件以获取目标类别和坐标信息
    print(filename)
    tree = ET.parse(os.path.join(input_dir, filename))
    root = tree.getroot()
    size = root.find('size')
    width = int(size.find('width').text)
    height = int(size.find('height').text)
    if (width == 0 or height == 0):
        continue
    objects = root.findall('object')
    with open(os.path.join(output_dir, filename.replace('.xml', '.txt')), 'w') as out_file:
        for obj in objects:
            class_name = obj.find('name').text
            bbox = obj.find('bndbox')
            x_center = (float(bbox.find('xmin').text) + float(bbox.find('xmax').text)) / 2
            if x_center == 0:
                x_center = 1
            x_center = x_center / width
            y_center = (float(bbox.find('ymin').text) + float(bbox.find('ymax').text)) / 2
            if y_center == 0:
                y_center = 1
            y_center = y_center / height
            bbox_width = (float(bbox.find('xmax').text) - float(bbox.find('xmin').text))
            if bbox_width == 0:
                bbox_width = 1
            bbox_width = bbox_width / width
            bbox_height = (float(bbox.find('ymax').text) - float(bbox.find('ymin').text))
            if bbox_height == 0:
                bbox_height = 1
            bbox_height = bbox_height / height
            class_index = classes.index(class_name)
            out_file.write(f"{class_index} {x_center:.6f} {y_center:.6f} {bbox_width:.6f} {bbox_height:.6f}\n")

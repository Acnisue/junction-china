import zhongguo from '@/utils/map.json'
import beijing from '@/utils/beijing.json'
import hebei from '@/utils/hebei.json'
import shanxi from '@/utils/shanxi.json'
import heilongjiang from '@/utils/heilongjiang.json'
import jilin from '@/utils/jilin.json'
import liaoning from '@/utils/liaoning.json'
import jiangsu from '@/utils/jiangsu.json'
import zhejiang from '@/utils/zhejiang.json'
import anhui from '@/utils/anhui.json'
import fujian from '@/utils/fujian.json'
import jiangxi from '@/utils/jiangxi.json'
import shandong from '@/utils/shandong.json'
import henan from '@/utils/henan.json'
import hubei from '@/utils/hubei.json'
import hunan from '@/utils/hunan.json'
import guangdong from '@/utils/guangdong.json'
import hainan from '@/utils/hainan.json'
import sichuan from '@/utils/sichuan.json'
import guizhou from '@/utils/guizhou.json'
import yunnan from '@/utils/yunnan.json'
import shanxi_陕 from '@/utils/shanxii_陕.json'
import gansu from '@/utils/gansu.json'
import qinghai from '@/utils/qinghai.json'
import taiwan from '@/utils/taiwan.json'
import neimenggu from '@/utils/neimenggu.json'
import xizang from '@/utils/xizang.json'
import guangxi from '@/utils/guangxi.json'
import ningxia from '@/utils/ningxia.json'
import xinjiang from '@/utils/xinjiang.json'
import shanghai from '@/utils/shanghai.json'
import tianjin from '@/utils/tianjin.json'
import chongqing from '@/utils/chongqing.json'
import xianggang from '@/utils/xianggang.json'
import aomen from '@/utils/aomen.json'
const mapDict = {
  "北京": "beijing",
  "河北": "hebei",
  "山西": "shanxi",
  "黑龙江": "heilongjiang",
  "吉林": "jilin",
  "辽宁": "liaoning",
  "江苏": "jiangsu",
  "浙江": "zhejiang",
  "安徽": "anhui",
  "福建": "fujian",
  "江西": "jiangxi",
  "山东": "shandong",
  "河南": "henan",
  "湖北": "hubei",
  "湖南": "hunan",
  "广东": "guangdong",
  "海南": "hainan",
  "四川": "sichuan",
  "贵州": "guizhou",
  "云南": "yunnan",
  "陕西": "shanxi_陕",
  "甘肃": "gansu",
  "青海": "qinghai",
  "台湾": "taiwan",
  "内蒙古": "neimenggu",
  "广西": "guangxi",
  "西藏": "xizang",
  "宁夏": "ningxia",
  "新疆": "xinjiang",
  "上海": "shanghai",
  "天津": "tianjin",
  "重庆": "chongqing",
  "香港": "xianggang",
  "澳门": "aomen",

}

const mapData = {
  beijing,
  hebei,
  shanxi,
  heilongjiang,
  jilin,
  liaoning,
  jiangsu,
  zhejiang,
  anhui,
  fujian,
  jiangxi,
  shandong,
  henan,
  hubei,
  hunan,
  guangdong,
  hainan,
  sichuan,
  guizhou,
  yunnan,
  shanxi_陕,
  gansu,
  qinghai,
  taiwan,
  neimenggu,
  xizang,
  guangxi,
  ningxia,
  xinjiang,
  shanghai,
  tianjin,
  chongqing,
  xianggang,
  aomen,
}

export function getMap(mapName) {
  const cityName = mapDict[mapName]
  if (cityName) {
    return [cityName, mapData[cityName]]
  }
  return ['china', zhongguo]
}

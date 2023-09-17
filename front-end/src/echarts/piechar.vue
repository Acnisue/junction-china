<template>
    <div id="main"></div>
</template>
<script>
export default {
    data() {
        return {
        }
    },
    mounted() {
        let myChart = this.$echarts.init(document.getElementById('main'));
        myChart.setOption({
            backgroundColor: this.$echarts.graphic.LinearGradient(1, 0, 0, 0, [
                {
                    offset: 0,
                    color: 'transparent'
                }, {
                    offset: 1,
                    color: '#0a6476'

                }
            ]),
            title: [
                {
                    // text: '三轮车使用数量',
                    // backgroundColor: 'white'
                }
            ],
            polar: {
                radius: [30, '80%']
            },
            angleAxis: {
                max: 4,
                startAngle: 75
            },
            radiusAxis: {
                type: 'category',
                data: ['泸州', '成都', '绵竹', '德阳']
            },
            tooltip: {},
            series: {
                type: 'bar',
                data: [2, 1.2, 2.4, 3.6],
                coordinateSystem: 'polar',
                label: {
                    show: true,
                    position: 'middle',
                    formatter: '{b}: {c}'
                },
                itemStyle: {
                    color: '#365f79'
                },
            }
        })
        // echart图表自适应
        window.addEventListener("resize", function () {
            myChart.resize();
        });
    },
}
</script>
<style scoped>
/* .myChart {
    width: 100%;
    height: 35vh;
} */
#main {
    width: 100%;
    height: 300px;
}
</style>
    
    
<template>
    <div id="pillar"></div>
</template>
<script>
export default {
    data() {
        return {
        }
    },
    mounted() {
        let myChart = this.$echarts.init(document.getElementById('pillar'));
        const data = [];
        for (let i = 0; i < 7; ++i) {
            data.push(Math.round(Math.random() * 200));
        }
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
            xAxis: {
                type: 'category',
                data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: [120, 200, 150, 80, 70, 110, 130],
                    type: 'bar',
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.2)'
                    },
                    type: 'bar',
                    label: {
                        show: true,
                        position: 'right',
                        valueAnimation: true
                    },
                    itemStyle: {
                        color: '#365f79'
                    },
                    animationDuration: 0,
                    animationDurationUpdate: 3000,
                    animationEasing: 'linear',
                    animationEasingUpdate: 'linear'
                }
            ]
        })
        function run() {
            for (var i = 0; i < data.length; ++i) {
                if (Math.random() > 0.9) {
                    data[i] += Math.round(Math.random() * 2000);
                } else {
                    data[i] += Math.round(Math.random() * 200);
                }
            }
            myChart.setOption({
                series: [
                    {
                        type: 'bar',
                        data
                    }
                ]
            });
        }
        setTimeout(function () {
            run();
        }, 0);
        setInterval(function () {
            run();
        }, 3000);
                 // echart图表自适应
                 window.addEventListener("resize", function () {
                myChart.resize();
            });
    },
}
</script>
<style scoped>
#pillar {
    width: 100%;
    height: 400px;
}
</style>
    
    
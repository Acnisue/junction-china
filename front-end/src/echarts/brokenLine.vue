<template>
    <div id="brokenLine"></div>
</template>
<script>
export default {
    data() {
        return {
        }
    },
    methods: {
        initCharts() {
            let myChart = this.$echarts.init(document.getElementById('brokenLine'));
            let base = +new Date(1988, 9, 3);
            let oneDay = 24 * 3600 * 1000;
            let data = [[base, Math.random() * 300]];
            for (let i = 1; i < 20000; i++) {
                let now = new Date((base += oneDay));
                data.push([+now, Math.round((Math.random() - 0.5) * 20 + data[i - 1][1])]);
            }
            myChart.setOption({
                backgroundColor: this.$echarts.graphic.LinearGradient(1, 0, 0, 0, [
                    {
                        offset: 0,
                        color: '#0a6476'
                    }, {
                        offset: 1,
                        color: 'transparent'
                    }
                ]),
                tooltip: {
                    trigger: 'axis',
                    position: function (pt) {
                        return [pt[0], '10%'];
                    }
                },
                title: {
                    left: 'center',
                    text: '省市违法行为',
                    color: '#ccc',
                    textStyle: {
                        color: 'white'
                    }

                },
                xAxis: {
                    type: 'time',
                    boundaryGap: false,
                    axisLine: {
                        onZero: false,
                    },
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%']
                },
                series: [
                    {
                        name: 'Fake Data',
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        areaStyle: {},
                        data: data
                    }
                ]
            })
            // echart图表自适应
            window.addEventListener("resize", function () {
                myChart.resize();
            });
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.initCharts();
        })
    },
}
</script>
<style scoped>
#brokenLine {
    width: 100%;
    height: 400px;
}
</style>
    
    
<template>
    <div id="piecharRight"></div>
</template>
<script>
export default {
    data() {
        return {
        }
    },
    methods: {
        initCharts() {
            let myChart = this.$echarts.init(document.getElementById('piecharRight'));

            // Generate data
            let category = [];
            let dottedBase = +new Date();
            let lineData = [];
            let barData = [];
            for (let i = 0; i < 20; i++) {
                let date = new Date((dottedBase += 3600 * 24 * 1000));
                category.push(
                    [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('-')
                );
                let b = Math.random() * 200;
                let d = Math.random() * 200;
                barData.push(b);
                lineData.push(d + b);
            }
            myChart.setOption({
                backgroundColor: this.$echarts.graphic.LinearGradient(1, 0, 0, 0, [
                    {
                    offset: 1,
                    color: '#0a6476'
                }, {
                    offset: 1,
                    color: 'transparent'
                }
            ]),
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data: ['line', 'bar'],
                    textStyle: {
                        color: '#ccc'
                    }
                },
                xAxis: {
                    data: ['成都', '泸州', '重庆','遂宁','南充','绵阳'],
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    }
                },
                yAxis: {
                    splitLine: { show: false },
                    axisLine: {
                        lineStyle: {
                            color: '#ccc'
                        }
                    }
                },
                series: [
                    {
                        name: 'line',
                        type: 'line',
                        smooth: true,
                        showAllSymbol: true,
                        symbol: 'emptyCircle',
                        symbolSize: 15,
                        data: lineData
                    },
                    {
                        name: 'bar',
                        type: 'bar',
                        barWidth: 10,
                        itemStyle: {
                            borderRadius: 5,
                            color: this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: '#14c8d4' },
                                { offset: 1, color: '#43eec6' }
                            ])
                        },
                        data: barData
                    },
                    {
                        name: 'line',
                        type: 'bar',
                        barGap: '-100%',
                        barWidth: 10,
                        itemStyle: {
                            color: this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: 'rgba(20,200,212,0.5)' },
                                { offset: 0.2, color: 'rgba(20,200,212,0.2)' },
                                { offset: 1, color: 'rgba(20,200,212,0)' }
                            ])
                        },
                        z: -12,
                        data: lineData
                    },
                    {
                        name: 'dotted',
                        type: 'pictorialBar',
                        symbol: 'rect',
                        itemStyle: {
                            color: '#0f375f'
                        },
                        symbolRepeat: true,
                        symbolSize: [12, 4],
                        symbolMargin: 1,
                        z: -10,
                        data: lineData
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
#piecharRight {
    width: 100%;
    height: 500px;
}
</style>
    
    
<template>
    <div class="piecharItem" ref="piecharItem"></div>
</template>
<script>
export default {
    data() {
        return {
        }
    },
    props: ['data'],
    methods: {
        initCharts() {
            let myChart = this.$echarts.init(this.$refs.piecharItem);
            const data = [];
            for (let i = 0; i < 7; ++i) {
                data.push(Math.round(Math.random() * 200));
            }
            myChart.setOption({
                title: {
                    text: `${this.data[5]}车辆违载数量(天)`,
                    left: 'center',
                    bottom: -7,
                    textStyle: {
                        color: 'white'
                    }

                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                series: [
                    {
                        itemStyle: {
                            normal: {
                                color: function (colors) {
                                    var colorList = [
                                        '#fc8251',
                                        '#5470c6',
                                        '#9A60B4',
                                        '#ef6567',
                                        '#f9c956',
                                        '#3BA272'
                                    ];
                                    return colorList[colors.dataIndex];
                                }
                            },
                        },

                        type: 'pie',
                        radius: '65%',
                        center: ['50%', '50%'],
                        selectedMode: 'single',
                        data: [
                            {
                                value: this.data[4],
                                name: '0',
                                label: {
                                    backgroundColor: '#eee',
                                    borderColor: '#777',
                                    borderWidth: 1,
                                    borderRadius: 4,
                                    rich: {
                                        title: {
                                            color: '#eee',
                                            align: 'center'
                                        },
                                        abg: {
                                            backgroundColor: '#333',
                                            width: '100%',
                                            align: 'right',
                                            height: 25,
                                            borderRadius: [4, 4, 0, 0]
                                        },
                                        Sunny: {
                                            height: 30,
                                            align: 'left',
                                            // backgroundColor: {
                                            //     image: weatherIcons.Sunny
                                            // }
                                        },
                                        Cloudy: {
                                            height: 30,
                                            align: 'left',
                                            // backgroundColor: {
                                            //     image: weatherIcons.Cloudy
                                            // }
                                        },
                                        Showers: {
                                            height: 30,
                                            align: 'left',
                                            // backgroundColor: {
                                            //     image: weatherIcons.Showers
                                            // }
                                        },
                                        weatherHead: {
                                            color: '#333',
                                            height: 24,
                                            align: 'left'
                                        },
                                        hr: {
                                            borderColor: '#777',
                                            width: '100%',
                                            borderWidth: 0.5,
                                            height: 0
                                        },
                                        value: {
                                            width: 20,
                                            padding: [0, 20, 0, 30],
                                            align: 'left'
                                        },
                                        valueHead: {
                                            color: '#333',
                                            width: 20,
                                            padding: [0, 20, 0, 30],
                                            align: 'center'
                                        },
                                        rate: {
                                            width: 40,
                                            align: 'right',
                                            padding: [0, 10, 0, 0]
                                        },
                                        rateHead: {
                                            color: '#333',
                                            width: 40,
                                            align: 'center',
                                            padding: [0, 10, 0, 0]
                                        }
                                    }
                                }
                            },
                            { value: this.data[0], name: '10' },
                            { value: this.data[1], name: '50' },
                            { value: this.data[2], name: '100' },
                            { value: this.data[3], name: '100以上' }
                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            })
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.initCharts(this.id)
        })
    },
}
</script>
<style scoped>
.piecharItem {
    width: 100%;
    height: 130px;
}
</style>
    
    
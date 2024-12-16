$(window).on('load', function () {
    $(".loading").fadeOut(); // 页面加载完成后隐藏加载框
});

// 设置动态字体大小
$(document).ready(function () {
    var whei = $(window).width();
    $("html").css({ fontSize: whei / 20 });

    $(window).resize(function () {
        var whei = $(window).width();
        $("html").css({ fontSize: whei / 20 });
    });
    showChart('echarts1');
});

function echarts_1(chart) {
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {type: 'shadow'},
        }, "grid": {
            "top": "20%",
            "right": "50",
            "bottom": "20",
            "left": "30",
        },
        legend: {
            data: ['字段1', '字段2', '字段3', '字段4', '字段5', '字段6'],
            right: 'center', width: '100%',
            textStyle: {
                color: "#fff"
            },
            itemWidth: 12,
            itemHeight: 10,
        },


        "xAxis": [
            {
                "type": "category",
                data: ['2016', '2017', '2018', '2019'],
                axisLine: {lineStyle: {color: "rgba(255,255,255,.1)"}},
                axisLabel: {
                    textStyle: {color: "rgba(255,255,255,.7)", fontSize: '14',},
                },

            },
        ],
        "yAxis": [
            {
                "type": "value",
                "name": "单位万",
                axisTick: {show: false},
                splitLine: {
                    show: false,

                },
                "axisLabel": {
                    "show": true,
                    fontSize: 14,
                    color: "rgba(255,255,255,.6)"

                },
                axisLine: {
                    min: 0,
                    max: 10,
                    lineStyle: {color: 'rgba(255,255,255,.1)'}
                },//左线色

            },
            {
                "type": "value",
                "name": "增速",
                "show": true,
                "axisLabel": {
                    "show": true,
                    fontSize: 14,
                    formatter: "{value} %",
                    color: "rgba(255,255,255,.6)"
                },
                axisTick: {show: false},
                axisLine: {lineStyle: {color: 'rgba(255,255,255,.1)'}},//右线色
                splitLine: {show: true, lineStyle: {color: 'rgba(255,255,255,.1)'}},//x轴线
            },
        ],
        "series": [

            {
                "name": "字段1",
                "type": "bar",
                "data": [36.6, 38.80, 40.84, 41.60],
                "barWidth": "15%",
                "itemStyle": {
                    "normal": {
                        barBorderRadius: 15,
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#8bd46e'
                        }, {
                            offset: 1,
                            color: '#09bcb7'
                        }]),
                    }
                },
                "barGap": "0.2"
            },
            {
                "name": "字段2",
                "type": "bar",
                "data": [14.8, 14.1, 15, 16.30],
                "barWidth": "15%",
                "itemStyle": {
                    "normal": {
                        barBorderRadius: 15,
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#248ff7'
                        }, {
                            offset: 1,
                            color: '#6851f1'
                        }]),
                    }
                },
                "barGap": "0.2"
            },
            {
                "name": "字段3",
                "type": "bar",
                "data": [9.2, 9.1, 9.85, 8.9],
                "barWidth": "15%",
                "itemStyle": {
                    "normal": {
                        barBorderRadius: 15,
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#fccb05'
                        }, {
                            offset: 1,
                            color: '#f5804d'
                        }]),
                    }
                },
                "barGap": "0.2"
            },
            {
                "name": "字段4",
                "type": "line",
                smooth: true,
                "yAxisIndex": 1,
                "data": [0, 6.01, 5.26, 1.48],
                lineStyle: {
                    normal: {
                        width: 2
                    },
                },
                "itemStyle": {
                    "normal": {
                        "color": "#86d370",

                    }
                },

            }
            ,
            {
                "name": "字段5",
                "type": "line",
                "yAxisIndex": 1,

                "data": [0, -4.73, 6.38, 8.67],
                lineStyle: {
                    normal: {
                        width: 2
                    },
                },
                "itemStyle": {
                    "normal": {
                        "color": "#3496f8",

                    }
                },
                "smooth": true
            },
            {
                "name": "字段6",
                "type": "line",
                "yAxisIndex": 1,

                "data": [0, -1.09, 8.24, -9.64],
                lineStyle: {
                    normal: {
                        width: 2
                    },
                },
                "itemStyle": {
                    "normal": {
                        "color": "#fbc30d",

                    }
                },
                "smooth": true
            }
        ]
    };

    chart.setOption(option);
    window.addEventListener("resize", function () {
        chart.resize();
    });

}

function echarts_2(chart) {
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {type: 'shadow'},
            // formatter:'{c}' ,
        },
        grid: {
            left: '0',
            top: '30',
            right: '10',
            bottom: '-20',
            containLabel: true
        },
        legend: {
            data: ['字段1', '字段2'],
            right: 'center',
            top: 0,
            textStyle: {
                color: "#fff"
            },
            itemWidth: 12,
            itemHeight: 10,
            // itemGap: 35
        },

        xAxis: [{
            type: 'category',
            boundaryGap: false,
            axisLabel: {
                rotate: -90,
                textStyle: {
                    color: "rgba(255,255,255,.6)",
                    fontSize: 14,

                },
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(255,255,255,.1)'
                }

            },

            data: ['17年3月', '17年6月', '17年9月', '17年12月', '18年3月', '18年6月', '18年9月', '18年12月', '19年3月', '19年6月', '19年9月', '19年12月']

        }, {

            axisPointer: {show: false},
            axisLine: {show: false},
            position: 'bottom',
            offset: 20,


        }],

        yAxis: [{
            type: 'value',
            axisTick: {show: false},
            // splitNumber: 6,
            axisLine: {
                lineStyle: {
                    color: 'rgba(255,255,255,.1)'
                }
            },
            axisLabel: {
                formatter: "{value} %",
                textStyle: {
                    color: "rgba(255,255,255,.6)",
                    fontSize: 14,
                },
            },

            splitLine: {
                lineStyle: {
                    color: 'rgba(255,255,255,.1)'
                }
            }
        }],
        series: [
            {
                name: '字段1',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 5,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        color: 'rgba(228, 228, 126, 1)',
                        width: 2
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(228, 228, 126, .2)'
                        }, {
                            offset: 1,
                            color: 'rgba(228, 228, 126, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgba(228, 228, 126, 1)',
                        borderColor: 'rgba(228, 228, 126, .1)',
                        borderWidth: 12
                    }
                },
                data: [12.50, 14.4, 16.1, 14.9, 20.1, 17.2, 17.0, 13.42, 20.12, 18.94, 17.27, 16.10]

            }, {
                name: '字段2',
                type: 'line',
                smooth: true,
                symbol: 'circle',
                symbolSize: 5,
                showSymbol: false,
                lineStyle: {

                    normal: {
                        color: 'rgba(255, 128, 128, 1)',
                        width: 2
                    }
                },
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(255, 128, 128,.2)'
                        }, {
                            offset: 1,
                            color: 'rgba(255, 128, 128, 0)'
                        }], false),
                        shadowColor: 'rgba(0, 0, 0, 0.1)',
                    }
                },
                itemStyle: {
                    normal: {
                        color: 'rgba(255, 128, 128, 1)',
                        borderColor: 'rgba(255, 128, 128, .1)',
                        borderWidth: 12
                    }
                },
                data: [-6.4, 0.1, 6.6, 11.2, 42.1, 26.0, 20.2, 18.31, 21.59, 24.42, 34.03, 32.9]
            },
        ]
    };

    chart.setOption(option);
    window.addEventListener("resize", function () {
        chart.resize();
    });
}

function echarts_3(chart) {
    option = {

        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['字段1', '字段2', '字段3'],
            right: 'center',
            top: 0,
            textStyle: {
                color: "#fff"
            },
            itemWidth: 12,
            itemHeight: 10,
            // itemGap: 35
        },
        grid: {
            left: '0',
            right: '20',
            bottom: '0',
            top: '15%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: ['字段1', '字段2', '字段3', '字段3', '字段4', '字段5', '字段6', '字段7', '字段8', '字段9'],
            axisLine: {
                lineStyle: {
                    color: 'white'

                }
            },
            axisLabel: {
                //rotate:-90,
                formatter: function (value) {
                    return value.split("").join("\n");
                },
                textStyle: {
                    color: "rgba(255,255,255,.6)",
                    fontSize: 14,
                }
            },
        },

        yAxis: {
            type: 'value',
            splitNumber: 4,
            axisTick: {show: false},
            splitLine: {
                show: true,
                lineStyle: {
                    color: 'rgba(255,255,255,0.1)'
                }
            },
            axisLabel: {
                textStyle: {
                    color: "rgba(255,255,255,.6)",
                    fontSize: 14,
                }
            },
            axisLine: {show: false},
        },

        series: [{
            name: '字段1',
            type: 'bar',
            stack: 'a',
            barWidth: '30', barGap: 0,
            itemStyle: {
                normal: {
                    color: '#8bd46e',
                }
            },
            data: [331, 497, 440, 81, 163, 366, 57, 188, 172, 2295]
        },
            {
                name: '字段2',
                type: 'bar',
                stack: 'a',
                barWidth: '30', barGap: 0,
                itemStyle: {
                    normal: {
                        color: '#f5804d',
                        barBorderRadius: 0,
                    }
                },
                data: [48, -97, 56, -59, 90, 98, 64, 61, -8, 253]
            },
            {
                name: '字段3',
                type: 'bar',
                stack: 'a',
                barWidth: '30', barGap: 0,
                itemStyle: {
                    normal: {
                        color: '#248ff7',
                        barBorderRadius: 0,
                    }
                },
                data: [-13, -21, -112, 5, 0, -5, 72, -3, 8, -69]
            }
        ]
    };

    chart.setOption(option);
    window.addEventListener("resize", function () {
        chart.resize();
    });
}

function echarts_5(chart) {
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {type: 'shadow'},
        }, "grid": {
            "top": "15%",
            "right": "10%",
            "bottom": "20",
            "left": "10%",
        },
        legend: {
            data: ['字段1', '字段2'],
            right: 'center',
            top: 0,
            textStyle: {
                color: "#fff"
            },
            itemWidth: 12,
            itemHeight: 10,
        },
        "xAxis": [
            {
                "type": "category",

                data: ['2016', '2017', '2018', '2019'],
                axisLine: {lineStyle: {color: "rgba(255,255,255,.1)"}},
                axisLabel: {
                    textStyle: {color: "rgba(255,255,255,.7)", fontSize: '14',},
                },

            },
        ],
        "yAxis": [
            {
                "type": "value",
                "name": "单位1",
                splitLine: {show: false},
                axisTick: {show: false},
                "axisLabel": {
                    "show": true,
                    color: "rgba(255,255,255,.6)"

                },
                axisLine: {lineStyle: {color: 'rgba(255,255,255,.1)'}},//左线色

            },
            {
                "type": "value",
                "name": "单位2",
                "show": true,
                axisTick: {show: false},
                "axisLabel": {
                    "show": true,
                    formatter: "{value} %",
                    color: "rgba(255,255,255,.6)"
                },
                axisLine: {lineStyle: {color: 'rgba(255,255,255,.1)'}},//右线色
                splitLine: {show: true, lineStyle: {color: 'rgba(255,255,255,.1)'}},//x轴线
            },
        ],
        "series": [

            {
                "name": "字段1",
                "type": "bar",
                "data": [
                    18453.35, 20572.22, 24274.22, 30500.00
                ],
                "barWidth": "20%",

                "itemStyle": {
                    "normal": {
                        barBorderRadius: 15,
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#fccb05'
                        }, {
                            offset: 1,
                            color: '#f5804d'
                        }]),
                    }
                },
                "barGap": "0"
            },
            {
                "name": "字段2",
                "type": "line",
                "yAxisIndex": 1,

                "data": [0, 11.48, 18.00, 25.65],
                lineStyle: {
                    normal: {
                        width: 2
                    },
                },
                "itemStyle": {
                    "normal": {
                        "color": "#ff3300",

                    }
                },
                "smooth": true
            }
        ]
    };

    chart.setOption(option);
    window.addEventListener("resize", function () {
        chart.resize();
    });
}

function echarts_4(chart) {
    // 子弹图
    const fieldSelect = document.getElementById('field-select');

    // 初始化字段数据
    const allFields = {
        5: {
            yAxisData: ['字段1', '字段2', '字段3', '字段4', '字段5'],
            seriesData: [25, 30, 34, 40, 43],
            whiteBoxData: [99.5, 99.5, 99.5, 99.5, 99.5]
        },
        10: {
            yAxisData: ['字段1', '字段2', '字段3', '字段4', '字段5', '字段6', '字段7', '字段8', '字段9', '字段10'],
            seriesData: [25, 30, 34, 40, 43, 48, 52, 56, 70, 80],
            whiteBoxData: [99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5]
        },
        20: {
            yAxisData: ['字段1', '字段2', '字段3', '字段4', '字段5', '字段6', '字段7', '字段8', '字段9', '字段10', '字段11', '字段12', '字段13', '字段14', '字段15', '字段16', '字段17', '字段18', '字段19', '字段20'],
            seriesData: [25, 30, 34, 40, 43, 48, 52, 56, 70, 80, 90, 85, 60, 50, 45, 30, 40, 55, 70, 85],
            whiteBoxData: [99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5, 99.5]
        }
    };

    // 初始化图表选项
    function updateChart(numFields) {
        const data = allFields[numFields];

        fetch('http://localhost:8080/JavaTopics/getTopNTopic?topN=' + numFields)
            .then(response => response.json())  // 解析 JSON 格式的响应
            .then(data_get => {
                // 转换数据格式
                const yAxisData = data_get.map(item => Object.keys(item)[0]); // 提取字段名
                const whiteBoxData = data_get.map(item => Number(Object.values(item)[0])); // 转换为数字
                const seriesData = whiteBoxData.map(value => value / 10); // 计算 seriesData

                // 更新现有数据对象
                data.yAxisData = yAxisData.reverse();
                data.seriesData = seriesData.reverse();
                data.whiteBoxData = whiteBoxData.reverse();

                console.log(data);  // 检查转换后的数据格式

                // 更新图表选项
                const option = {
                    grid: {
                        left: '2%',
                        top: '1%',
                        right: '5%',
                        bottom: '0%',
                        containLabel: true
                    },
                    xAxis: [{
                        show: false,
                    }],
                    yAxis: [
                        {
                            axisLine: {                   // 显示轴线
                                lineStyle: {
                                    color: '#ccc'
                                }
                            },
                            axisTick: { show: false },
                            axisLabel: {
                                textStyle: {
                                    color: 'rgba(255,255,255,.6)',
                                    fontSize: 14,
                                }
                            },
                            data: data.yAxisData
                        },
                        {
                            name: 'Number of occurrences',
                            nameLocation: 'middle',
                            nameGap: 30,
                            nameTextStyle: {
                                color: 'rgba(255,255,255,.6)',
                                fontSize: 16,
                            },
                            axisLine: {
                                lineStyle: {
                                    color: '#ccc'
                                }
                            },
                            data: data.whiteBoxData
                        }
                    ],
                    series: [{
                        name: '条',
                        type: 'bar',
                        yAxisIndex: 0,
                        data: data.seriesData,
                        label: {
                            normal: {
                                show: true,
                                position: 'right',
                                formatter: function (param) {
                                    return param.value + '%';
                                },
                                textStyle: {
                                    color: 'rgba(255,255,255,.8)',
                                    fontSize: '12',
                                }
                            }
                        },
                        barWidth: 15,
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                                    offset: 0,
                                    color: '#03c893'
                                },
                                    {
                                        offset: 1,
                                        color: '#0091ff'
                                    }
                                ]),
                                barBorderRadius: 15,
                            }
                        },
                        z: 2
                    }, {
                        name: '白框',
                        type: 'bar',
                        yAxisIndex: 1,
                        barGap: '-100%',
                        data: data.whiteBoxData,
                        barWidth: 15,
                        itemStyle: {
                            normal: {
                                color: 'rgba(0,0,0,.2)',
                                barBorderRadius: 15,
                            }
                        },
                        z: 1
                    }]
                };

                // 更新图表
                chart.setOption(option);
            })
            .catch(error => {
                console.error('Error:', error);  // 错误处理
            });
    }


    // 初始化时设置为5个字段
    updateChart(5);

    // 监听下拉框选择变化
    fieldSelect.addEventListener('change', function () {
        const selectedValue = parseInt(fieldSelect.value);
        updateChart(selectedValue);
    });

    window.addEventListener("resize", function () {
        chart.resize();
    });
}


function echarts_6(chart) {
    // 饼状图
    const fieldSelect = document.getElementById('field-select');


    option = {
        title: {
            text: '5132',
            subtext: '总体',
            x: 'center',
            y: '40%',
            textStyle: {
                color: '#fff',
                fontSize: 22,
                lineHeight: 10,
            },
            subtextStyle: {
                color: '#90979c',
                fontSize: 16,
                lineHeight: 10,

            },
        },
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
        },

        visualMap: {
            show: false,
            min: 500,
            max: 600,
            inRange: {
                //colorLightness: [0, 1]
            }
        },
        series: [{
            name: '访问来源',
            type: 'pie',
            radius: ['50%', '70%'],
            center: ['50%', '50%'],
            color: ['rgb(131,249,103)', '#FBFE27', '#FE5050', '#1DB7E5'], //'#FBFE27','rgb(11,228,96)','#FE5050'
            data: [{
                "value": 1924,
                "name": "字段名称1"
            }, {
                "value": 1055,
                "name": "字段名称2"
            }, {
                "value": 1532,
                "name": "字段名称3"
            }
            ].sort(function (a, b) {
                return a.value - b.value
            }),
            roseType: 'radius',

            label: {
                normal: {
                    formatter: ['{c|{c}万}', '{b|{b}}'].join('\n'),
                    rich: {
                        c: {
                            color: 'rgb(241,246,104)',
                            fontSize: 20,
                            fontWeight: 'bold',
                            lineHeight: 5
                        },
                        b: {
                            color: 'rgb(98,137,169)',
                            fontSize: 14,
                            height: 44
                        },
                    },
                }
            },
            labelLine: {
                normal: {
                    lineStyle: {
                        color: 'rgb(98,137,169)',
                    },
                    smooth: 0.2,
                    length: 10,
                    length2: 20,

                }
            }
        }]
    };

    chart.setOption(option);

    // 监听下拉框选择变化
    fieldSelect.addEventListener('change', function () {
        const selectedValue = parseInt(fieldSelect.value);
        updateChart(selectedValue);
    });
    window.addEventListener("resize", function () {
        chart.resize();
    });
}
// 用于存储当前的 ECharts 实例
let currentChart = null;

// 显示指定的图表
function showChart(chartId) {
    // 如果当前有图表实例，先销毁它
    if (currentChart) {
        currentChart.dispose();
    }

    // 初始化新的图表实例
    currentChart = echarts.init(document.getElementById('chartContainer'));

    // 根据传入的 chartId 调用对应的图表函数
    switch (chartId) {
        case 'echarts1':
            echarts_1(currentChart);
            break;
        case 'echarts2':
            echarts_2(currentChart);
            break;
        case 'echarts3':
            echarts_3(currentChart);
            break;
        case 'echarts4':
            echarts_4(currentChart);
            break;
        case 'echarts5':
            echarts_5(currentChart);
            break;
        case 'echarts6':
            echarts_6(currentChart);
            break;
        default:
            console.error('未知图表ID');
    }
}
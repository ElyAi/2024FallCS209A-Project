$(window).on('load', function () {
    $(".loading").fadeOut(); // 页面加载完成后隐藏加载框
});

// 设置动态字体大小
$(document).ready(function () {
    var whei = $(window).width();
    $("html").css({ fontSize: whei / 20 });
    showChart('echarts2');
});


function echarts_2(chart) {
    const fieldSelect = document.getElementById('query-btn');

    // 初始化图表选项
    function updateChart(numFields) {

        fetch('http://localhost:8080/UserEngagement/getHighQualityTopic?topN=' + numFields)
            .then(response => response.json())  // 解析 JSON 格式的响应
            .then(data_get => {
                // 转换数据格式
                const yAxisData = data_get.map(item => Object.keys(item)[0]); // 提取字段名
                const whiteBoxData = data_get.map(item => Number(Object.values(item)[0])); // 转换为数字


                const option = {
                    backgroundColor: '#00264d', // 设置背景色
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: { type: 'line' } // 线型指示器
                    },
                    grid: {
                        left: '5%',
                        right: '5%',
                        top: '15%',
                        bottom: '10%',
                        containLabel: true
                    },
                    legend: {
                        data: ['字段1'], // 只保留一个字段
                        right: 'center',
                        top: '5%',
                        textStyle: {
                            color: '#fff',
                            fontSize: 12
                        }
                    },
                    xAxis: [{
                        type: 'category',
                        boundaryGap: false,
                        axisLabel: {
                            rotate: 20, // 稍微倾斜，提升可读性
                            textStyle: {
                                color: "rgba(255,255,255,.8)",
                                fontSize: 12,
                            },
                        },
                        axisLine: {
                            lineStyle: {
                                color: 'rgba(255,255,255,.3)'
                            }
                        },
                        data: yAxisData
                    }],
                    yAxis: [{
                        type: 'value',
                        axisLabel: {
                            textStyle: {
                                color: "rgba(255,255,255,.8)",
                                fontSize: 12,
                            },
                            formatter: "{value}" // 如果不需要显示百分号
                        },
                        splitLine: {
                            lineStyle: {
                                color: 'rgba(255,255,255,0.1)' // 设置网格线颜色
                            }
                        },
                        axisLine: {
                            show: false // 隐藏Y轴线
                        }
                    }],
                    series: [{
                        name: '字段1',
                        type: 'line',
                        smooth: true, // 使折线更平滑
                        symbol: 'circle', // 数据点的形状
                        symbolSize: 8, // 数据点大小
                        lineStyle: {
                            normal: {
                                color: 'rgba(75, 192, 192, 1)', // 折线颜色
                                width: 3 // 折线宽度
                            }
                        },
                        areaStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                    { offset: 0, color: 'rgba(75, 192, 192, 0.4)' }, // 渐变开始颜色
                                    { offset: 1, color: 'rgba(75, 192, 192, 0)' } // 渐变结束颜色
                                ]),
                                shadowColor: 'rgba(0, 0, 0, 0.1)', // 阴影颜色
                                shadowBlur: 10
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#4bc0c0', // 数据点的填充颜色
                                borderColor: '#fff',
                                borderWidth: 2
                            }
                        },
                        label: {
                            show: true,
                            position: 'top',
                            color: '#fff',
                            fontSize: 12,
                        },
                        data: whiteBoxData
                    }]
                };


                chart.setOption(option);
            })
            .catch(error => {
                console.error('Error:', error);  // 错误处理
            });
    }

    // 初始化时设置为5个字段
    updateChart(5);

    fieldSelect.addEventListener('click', function () {
        // 获取输入框的值
        const topN = document.getElementById('topN-input').value;

        // 检查输入是否合法
        if (topN === '' || isNaN(topN) || topN <= 0) {
            alert('请输入一个有效的正整数！');
            return;
        }
        updateChart(topN);
    });

}

function echarts_3(chart) {
    // 条形图
    const fieldSelect = document.getElementById('query-btn');

    // 初始化图表选项
    function updateChart(numFields) {
        fetch('http://localhost:8080/UserEngagement/getHighQualityTopic?topN=' + numFields)
            .then(response => response.json())  // 解析 JSON 格式的响应
            .then(data_get => {
                // 转换数据格式
                const yAxisData = data_get.map(item => Number(Object.values(item)[0])); // 转换为数字（即 Y 轴数据）
                const xAxisData = data_get.map(item => Object.keys(item)[0]); // 提取字段名（即 X 轴标签）

                console.log('Y 轴数据:', yAxisData);
                console.log('X 轴标签:', xAxisData);

                const option = {
                    backgroundColor: '#00264d', // 图表背景色
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['字段1'],
                        right: 'center',
                        top: '5%',
                        textStyle: {
                            color: "#fff"
                        },
                    },
                    grid: {
                        left: '5%',
                        right: '5%',
                        bottom: '10%',
                        top: '20%',
                        containLabel: true
                    },
                    xAxis: {
                        type: 'category',
                        data: xAxisData,
                        axisLine: {
                            lineStyle: {
                                color: 'white'  // X轴线颜色
                            }
                        },
                        axisLabel: {
                            rotate: 30, // 标签倾斜 30 度
                            textStyle: {
                                color: "rgba(255,255,255,.8)",
                                fontSize: 12,
                            }
                        }
                    },
                    yAxis: {
                        type: 'value',
                        splitNumber: 4,
                        splitLine: {
                            lineStyle: {
                                color: 'rgba(255,255,255,0.1)' // Y轴网格线
                            }
                        },
                        axisLabel: {
                            textStyle: {
                                color: "rgba(255,255,255,.8)",
                                fontSize: 12,
                            }
                        }
                    },
                    series: [{
                        name: '字段1',
                        type: 'bar',
                        barWidth: '40%', // 柱子宽度
                        data: yAxisData,
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient( // 渐变色
                                    0, 0, 0, 1,
                                    [
                                        { offset: 0, color: '#83bff6' },
                                        { offset: 0.5, color: '#188df0' },
                                        { offset: 1, color: '#0f5aaa' }
                                    ]
                                ),
                                barBorderRadius: [5, 5, 0, 0], // 圆角柱状图
                            },
                            emphasis: {
                                color: '#4caf50' // 鼠标悬停时的颜色
                            }
                        },
                        label: {
                            show: true,
                            position: 'top', // 数据标签显示在顶部
                            color: '#fff',
                            fontSize: 12
                        }
                    }]
                };

                chart.setOption(option); // 更新图表
            })
            .catch(error => {
                console.error('Error:', error);  // 错误处理
            });
    }

    // 初始化时设置为5个字段
    updateChart(5);

    fieldSelect.addEventListener('click', function () {
        // 获取输入框的值
        const topN = document.getElementById('topN-input').value;

        // 检查输入是否合法
        if (topN === '' || isNaN(topN) || topN <= 0) {
            alert('请输入一个有效的正整数！');
            return;
        }
        updateChart(topN);
    });
}

function echarts_5(chart) {
    const fieldSelect = document.getElementById('query-btn');

    // 初始化图表选项
    function updateChart(numFields) {
        // const data = allFields[numFields];

        fetch('http://localhost:8080/UserEngagement/getHighQualityTopic?topN=' + numFields)
            .then(response => response.json())  // 解析 JSON 格式的响应
            .then(data_get => {
                // 转换数据格式
                const yAxisData = data_get.map(item => Object.keys(item)[0]); // 提取字段名
                const whiteBoxData = data_get.map(item => Number(Object.values(item)[0])); // 转换为数字

                const option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {type: 'shadow'},
                    }, "grid": {
                        "top": "15%",
                        "right": "10%",
                        "bottom": "60",
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

                            data: yAxisData,
                            axisLine: {lineStyle: {color: "rgba(255,255,255,.1)"}},
                            axisLabel: {
                                interval: 0, // 强制显示所有标签
                                rotate: 30, // 将标签文字旋转30度
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
                            "data": whiteBoxData,
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

                            "data": whiteBoxData,
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
            })
            .catch(error => {
                console.error('Error:', error);  // 错误处理
            });
    }

    // 初始化时设置为5个字段
    updateChart(5);

    fieldSelect.addEventListener('click', function () {
        // 获取输入框的值
        const topN = document.getElementById('topN-input').value;

        // 检查输入是否合法
        if (topN === '' || isNaN(topN) || topN <= 0) {
            alert('请输入一个有效的正整数！');
            return;
        }
        updateChart(topN);
    });
}

function echarts_4(chart) {
    // 子弹图
    const fieldSelect = document.getElementById('query-btn');

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

        fetch('http://localhost:8080/UserEngagement/getHighQualityTopic?topN=' + numFields)
            .then(response => response.json())  // 解析 JSON 格式的响应
            .then(data_get => {
                // 转换数据格式
                const yAxisData = data_get.map(item => Object.keys(item)[0]); // 提取字段名
                const whiteBoxData = data_get.map(item => Number(Object.values(item)[0])); // 转换为数字
                const seriesData = whiteBoxData.map(value => value / 10); // 计算 seriesData


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
                            data: yAxisData
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
                            data: whiteBoxData
                        }
                    ],
                    series: [{
                        name: '条',
                        type: 'bar',
                        yAxisIndex: 0,
                        data: seriesData,
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
                        data: whiteBoxData,
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

    fieldSelect.addEventListener('click', function () {
        // 获取输入框的值
        const topN = document.getElementById('topN-input').value;

        // 检查输入是否合法
        if (topN === '' || isNaN(topN) || topN <= 0) {
            alert('请输入一个有效的正整数！');
            return;
        }
        updateChart(topN);
    });
}

function echarts_6(chart) {
    // 饼状图
    const fieldSelect = document.getElementById('query-btn');

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
        // const data = allFields[numFields];

        fetch('http://localhost:8080/UserEngagement/getHighQualityTopic?topN=' + numFields)
            .then(response => response.json())  // 解析 JSON 格式的响应
            .then(data_get => {
                // 转换数据格式
                const pieData = data_get.map(item => {
                    const key = Object.keys(item)[0]; // 获取对象的键名（如 'string'）
                    const value = item[key]; // 获取对应的值（如 77）
                    return {
                        name: key,  // 键名作为 name
                        value: value // 对应的值作为 value
                    };
                });

                console.log(pieData);  // 检查转换后的数据格式

                const option = {
                    title: {
                        text: '3465',
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
                        data: pieData.sort(function (a, b) {
                            return a.value - b.value;
                        }),
                        roseType: 'radius',

                        label: {
                            normal: {
                                formatter: ['{c|{c}条}', '{b|{b}}'].join('\n'),
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
            })
            .catch(error => {
                console.error('Error:', error);  // 错误处理
            });
    }


    // 初始化时设置为5个字段
    updateChart(5);


    fieldSelect.addEventListener('click', function () {
        // 获取输入框的值
        const topN = document.getElementById('topN-input').value;

        // 检查输入是否合法
        if (topN === '' || isNaN(topN) || topN <= 0) {
            alert('请输入一个有效的正整数！');
            return;
        }
        updateChart(topN);
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
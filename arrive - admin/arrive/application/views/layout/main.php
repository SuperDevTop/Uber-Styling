
<!DOCTYPE html>
<html>
    <head lang="en">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Arrive5</title>

        <link href="img/favicon.144x144.png" rel="apple-touch-icon" type="image/png" sizes="144x144">
        <link href="img/favicon.114x114.png" rel="apple-touch-icon" type="image/png" sizes="114x114">
        <link href="img/favicon.72x72.png" rel="apple-touch-icon" type="image/png" sizes="72x72">
        <link href="img/favicon.57x57.png" rel="apple-touch-icon" type="image/png">
        <link href="img/favicon.png" rel="icon" type="image/png">
        <link href="img/favicon.ico" rel="shortcut icon">

        <?php $this->load->view('layout/css'); ?>
    </head>
    <body class="with-side-menu control-panel control-panel-compact">

      
        <?php

if($this->uri->segment(3)!='payment')
{

  
        $this->load->view('layout/header');
        $this->load->view('layout/sidebar');
    }
        ?>

        <div id="indexLoader" style="z-index: 100; margin-top: 35%; margin-left: 50%;width:100%;height:100%;position: absolute; opacity:0.8;">
            <span>
                <img src="<?php echo base_url(); ?>assets/js/Progress.gif">
            </span>
        </div>
        <?php $this->load->view($main_view); ?>
        <?php $this->load->view('layout/js'); ?>

        <script>
            $(document).ready(function () {
                $('#indexLoader').fadeOut('fast');
                $('.panel').each(function () {
                    try {
                        $(this).lobiPanel({
                            sortable: true
                        }).on('dragged.lobiPanel', function (ev, lobiPanel) {
                            $('.dahsboard-column').matchHeight();
                        });
                    } catch (err) {
                    }
                });

                google.charts.load('current', {'packages': ['corechart']});
                google.charts.setOnLoadCallback(drawChart);
                function drawChart() {
                    var dataTable = new google.visualization.DataTable();
                    dataTable.addColumn('string', 'Day');
                    dataTable.addColumn('number', 'Values');
                    // A column for custom tooltip content
                    dataTable.addColumn({type: 'string', role: 'tooltip', 'p': {'html': true}});
                    dataTable.addRows([
                        ['MON', 130, ' '],
                        ['TUE', 130, '130'],
                        ['WED', 180, '180'],
                        ['THU', 175, '175'],
                        ['FRI', 200, '200'],
                        ['SAT', 170, '170'],
                        ['SUN', 250, '250'],
                        ['MON', 220, '220'],
                        ['TUE', 220, ' ']
                    ]);

                    var options = {
                        height: 314,
                        legend: 'none',
                        areaOpacity: 0.18,
                        axisTitlesPosition: 'out',
                        hAxis: {
                            title: '',
                            textStyle: {
                                color: '#fff',
                                fontName: 'Proxima Nova',
                                fontSize: 11,
                                bold: true,
                                italic: false
                            },
                            textPosition: 'out'
                        },
                        vAxis: {
                            minValue: 0,
                            textPosition: 'out',
                            textStyle: {
                                color: '#fff',
                                fontName: 'Proxima Nova',
                                fontSize: 11,
                                bold: true,
                                italic: false
                            },
                            baselineColor: '#16b4fc',
                            ticks: [0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350],
                            gridlines: {
                                color: '#1ba0fc',
                                count: 15
                            }
                        },
                        lineWidth: 2,
                        colors: ['#fff'],
                        curveType: 'function',
                        pointSize: 5,
                        pointShapeType: 'circle',
                        pointFillColor: '#f00',
                        backgroundColor: {
                            fill: '#008ffb',
                            strokeWidth: 0,
                        },
                        chartArea: {
                            left: 0,
                            top: 0,
                            width: '100%',
                            height: '100%'
                        },
                        fontSize: 11,
                        fontName: 'Proxima Nova',
                        tooltip: {
                            trigger: 'selection',
                            isHtml: true
                        }
                    };

                    var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
                    chart.draw(dataTable, options);
                }
                $(window).resize(function () {
                    drawChart();
                    setTimeout(function () {
                    }, 1000);
                });
            });       
    // Jquery date picker 
     $( ".datepicker" ).datepicker();
   $(function(){
        $('#user').multiSelect({
            positionMenuWithin: $('.position-menu-within'),
            noneText: 'Select User',
            search: true,
            selectAll: true,
        });
       
    });

</script>
    </body>
</html>
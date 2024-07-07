import { ChartDataset, ChartOptions, ChartType } from 'chart.js';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent implements OnInit {
  @Input() chartData: ChartDataset[] = [];
  @Input() chartLabels: string[] = [];
  @Input() label: string = '';
  @Input() chartType: ChartType = 'line';
  
  chartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        display: true,
        title: {
          display: true,
          text: ''
        },
        grid: {
          drawBorder: false
        }
      },
      y: {
        display: true,
        title: {
          display: true,
          text:''
        }
      }
    },
    plugins: {
      legend: {
        display: true,
        position: 'top'
      },
      tooltip: {
        backgroundColor: 'white',
        displayColors: false,
        padding: 10,
        titleColor: '#2D2F33',
        titleFont: {
          size: 18
        },
        bodyColor: '#2D2F33',
        bodyFont: {
          size: 13
        }
      }
    }
  };

  constructor() { }

  ngOnInit(): void {
    if (this.chartData.length > 0) {
      this.chartData[0].label = this.label;
    }
  }
}

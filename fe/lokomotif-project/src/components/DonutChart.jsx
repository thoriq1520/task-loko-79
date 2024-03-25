import React, { useEffect, useState } from 'react';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from 'chart.js';
import { Doughnut } from 'react-chartjs-2';
import axios from 'axios';

ChartJS.register(Title, Tooltip, Legend, ArcElement);

export const options = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      display: true,
      position: 'top',
      labels: {
        color: 'white',
      },
    },
  },
  tooltips: {
    callbacks: {
      label: (tooltipItem, data) => {
        const dataset = data.datasets[tooltipItem.datasetIndex];
        const total = dataset.data.reduce((sum, value) => sum + value, 0);
        const value = dataset.data[tooltipItem.index];
        const percentage = ((value / total) * 100).toFixed(2) + '%';
        return `${data.labels[tooltipItem.index]}: ${percentage}`;
      },
    },
  },
};

export function DonutChart() {
  const [data, setData] = useState([]);
  const [groupedData, setGroupedData] = useState({ labels: [], datasets: [] });

  useEffect(() => {
    axios.get('http://localhost:8081/summaries/getAll')
      .then((response) => {
        const apiData = response.data;
        setData(apiData);
      })
      .catch((error) => {
        console.error('Error fetching data: ', error);
      });
  }, []);

  useEffect(() => {
    const grouped = data.reduce((result, item) => {
      const status = item.status;
      const jumlah = item.jumlah;

      if (!result[status]) {
        result[status] = {
          status,
          total: 0,
        };
      }

      result[status].total += jumlah;

      return result;
    }, {});

    const groupedDataArray = Object.values(grouped);

    const labels = groupedDataArray.map((item) => item.status);
    const dataset = {
      data: groupedDataArray.map((item) => item.total),
      backgroundColor: [
        'rgba(255, 99, 132, 0.5)',
        'rgba(54, 162, 235, 0.5)',
        'rgba(75, 192, 192, 0.5)',
      ],
    };

    setGroupedData({ labels, datasets: [dataset] });
  }, [data]);

  return (
    <div className="donut-chart">
      <Doughnut data={groupedData} options={options} width={250} height={250}/>
    </div>
  );
}

export default DonutChart;

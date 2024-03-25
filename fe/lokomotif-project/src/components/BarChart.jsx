import React, { useEffect, useState } from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar } from 'react-chartjs-2';
import axios from 'axios';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

export const options = {
  responsive: true,
  plugins: {
    legend: {
      position: 'top',
      labels: {
        color: 'white',
      },
    },
    title: {
      display: true,
      text: 'Summary',
      color: 'white',
    },
  },
  scales: {
    x: {
      grid: {
        color: 'white',
      },
      ticks: {
        color: 'white',
      },
    },
    y: {
      grid: {
        color: 'white',
      },
      ticks: {
        color: 'white',
      },
    },
  },
};

export function BarChart() {
  const [data, setData] = useState({ labels: [], datasets: [] });
  const [data2, setData2] = useState([]);
  const [groupedData, setGroupedData] = useState({ labels: [], datasets: [] });

  useEffect(() => {
    axios.get('http://localhost:8081/summaries/getAll')
      .then((response) => {
        const apiData = response.data;
        const labels = apiData.map((item) => item.tanggal);
        const dataset = {
          label: 'Data',
          data: apiData.map((item) => item.jumlah),
          backgroundColor: 'rgba(110,65,250,1)',
        };

        setData({ labels, datasets: [dataset] });
      })
      .catch((error) => {
        console.error('Error fetching data: ', error);
      });
  }, []);
  useEffect(() => {
    axios.get('http://localhost:8081/summaries/getAll')
      .then((response) => {
        const apiData = response.data;
        setData2(apiData);
      })
      .catch((error) => {
        console.error('Error fetching data: ', error);
      });
  }, []);

  useEffect(() => {

    const grouped = data2.reduce((result, item) => {
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
       label: 'Dataset 2',
       data: groupedDataArray.map((item) => item.total),
       backgroundColor: 'rgba(110,65,250,1)',
     };

    setGroupedData({ labels, datasets: [dataset] });
  }, [data2]);

 


  return (
    <div>
      <Bar options={options} data={data} height={150}/>
    </div>
  );
}

export default BarChart;

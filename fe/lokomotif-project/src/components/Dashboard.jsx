import React from 'react'
import BarChart from './BarChart'
import DonutChart from './DonutChart'

const Dashboard = () => {
  return (
<div class="bg-custom flex flex-col">
  <div class="lg:flex lg:items-center lg:justify-between mt-8 ml-10">
    <div class="min-w-0 flex-1">
      <h2 class="text-2xl font-bold leading-7 text-white sm:truncate sm:text-3xl sm:tracking-tight mt-4">Dashboard</h2>
    </div>
  </div>
  <div class="flex-shrink-0">
    <div class="flex">
      <div class="w-1/2 h-1/2 flex-1 bg-custom2 m-4 p-4 rounded-xl shadow-md overflow-hidden">
        <BarChart />
      </div>
      <div class="w-1/2  flex-1 bg-custom2 m-4 p-4 rounded-xl shadow-md overflow-hidden">
        <DonutChart />
      </div>
    </div>
  </div>
</div>
  )
}

export default Dashboard
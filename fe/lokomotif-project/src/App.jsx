import React from 'react';
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './components/Dashboard';
import Lokomotif from './components/Lokomotif.jsx';


const router = createBrowserRouter(
  createRoutesFromElements(
      <Route path="/">
        <Route index element={<Dashboard />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="lokomotif" element={<Lokomotif />} />
      </Route>
      
  )
)

function App({routes}) {

  return (
    <>
      <div class="bg-custom w-screen h-screen">
        <Navbar />
        <RouterProvider router={router}/>
      </div>
    </>
  );
}

export default App;
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import './css/reset.css';
import './css/fonts.css';
import './css/basic.css';
import './css/grid.css';
import './css/services.css';
import './css/buttons.css';
import './css/startpage.css';
import './css/choose.css';
import './css/service-page.css'
import App from './App';

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </React.StrictMode>
  );
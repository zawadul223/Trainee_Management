import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
//import './index2.css'
//import '../node_modules/react-bootstrap/dist/react-bootstrap.js'
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter } from 'react-router-dom';

ReactDOM.createRoot(document.getElementById('root')).render(
    <BrowserRouter>
    
        <App />
    
    </BrowserRouter>
    
    
    
)

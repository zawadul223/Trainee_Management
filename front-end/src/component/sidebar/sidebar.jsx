import React from 'react';
import '../../App.css'
import { SidebarData } from "./sidebar_data"
import Logo from '../../assets/bjit-logo2.svg'
import { key } from 'localforage';
import { Container, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function Sidebar({setIsLoggedIn}) {
  const navigate = useNavigate();
  const handleLogout = () => {
    // Clear user authentication-related data from localStorage
    localStorage.removeItem("userToken");
    localStorage.removeItem("email")
    localStorage.removeItem("role")
    localStorage.removeItem("id")
    setIsLoggedIn(false)

    
  };
  return (
    <div className='Sidebar'>
      <div className='logo'>
        <img src={Logo} alt='Logo' />
      </div>
      <ul className='SidebarList'>
        {SidebarData.map((val, key) => {
          return (
            <li key={key} className='row' id={window.location.pathname == val.link ? "active" : " "} onClick={() => { window.location.pathname = val.link }}>
              {" "}
              <div id="title">{val.title}</div>
            </li>
          )
          
        })}
      </ul>
      <ul className='SidebarList' >
        <li className = "row">
        <Button
              variant="contained"
              color="Danger"
              onClick={handleLogout}
            >
               Logout
            </Button>
        </li>
      
      </ul>
    </div>
  );
}

export default Sidebar;

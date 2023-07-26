import { useState, useEffect } from 'react'
import './App.css'
//import UserDropdown from './component/user_icon/user_icon'
import LoginPage from './component/pages/login.jsx'
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Sidebar from './component/sidebar/sidebar'
import Notice from './component/pages/notice';
import { Container } from 'react-bootstrap';
import Batches from './component/pages/batches';
import BatchDetails from './component/pages/batch_detail';
import TraineeRegistrationPage from './component/pages/register_trainee';
import TrainerRegistrationPage from './component/pages/register_trainer';
import ClassroomPage from './component/classroom/classroom';
import TrainerClassroomPage from './component/classroom/trainer_classroom_page';
import TraineeClassroomPage from './component/classroom/trainee_classroom_page';

function App() {

  const currentPath = window.location.pathname;
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  //console.log(localStorage.getItem("userToken"));

  useEffect(() => {
    const userToken = localStorage.getItem("userToken");
    if (userToken) {
      setIsLoggedIn(true);
    }
  }, []);

  return (
    <div className='App'>
      {isLoggedIn && (
        <>
          <Sidebar setIsLoggedIn={setIsLoggedIn} />

        </>

      )}

      <div className="content">

        <Routes>
          {isLoggedIn ? (
            <>
              <Route path="/" element={<Notice />} />
              <Route path="/allnotice" element={<Notice />} />
              <Route path='/batch' element={<Batches />} />
              <Route path='/batch_detail' element={<BatchDetails />} />
              <Route path='/traineeRegister' element={<TraineeRegistrationPage />} />
              <Route path='/trainerRegister' element={<TrainerRegistrationPage />} />
              <Route path='/classroom' element={<ClassroomPage />}/>
              <Route path='/trainerClassroom' element={<TrainerClassroomPage />} />
              <Route path='/traineeClassroom' element={<TraineeClassroomPage />} />
            </>
          ) : (
            <>
              <Route path="/" element={<LoginPage setIsLoggedIn={setIsLoggedIn} />} />
              <Route path="/login" element={<LoginPage setIsLoggedIn={setIsLoggedIn} />} />
            </>
          )}


        </Routes>

      </div>

    </div>
  )
}

export default App

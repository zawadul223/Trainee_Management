import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
//import { useHistory } from 'react-router-dom';

function LoginPage({setIsLoggedIn}) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loginStatus, setLoginStatus] = useState(null); // null for initial state, true for success, false for error
  //const history = useHistory();

  const navigate = useNavigate();

  function handleSubmit(e) {
    e.preventDefault();

    console.log(email, password);
    fetch('http://localhost:8080/user/login', {
      method: 'POST',
      crossDomain: true,
       headers: {
        'Content-Type': 'application/json',
         Accept: 'application/json',
         'Access-Control-Allow-Origin': '*',
       },
      body: JSON.stringify({
        email,
        password,
      }),
    })
      .then((res) => {
        if (res.ok) {
          setLoginStatus(true);
          
          // localStorage.setItem("userToken",res.data.token);
          // Use the navigate function to redirect to App component on successful login
          setTimeout(() => {
            console.log("navigating");
            navigate('/');
            setIsLoggedIn(true)

          }, 1000);


        } else {
          setLoginStatus(false);
        }
        return res.json();
      })
      .then((data) => {
        console.log(data.token); 
        localStorage.setItem("userToken",data.token);
        localStorage.setItem("email", data.email);
        localStorage.setItem("role", data.role);
        localStorage.setItem("id", data.id);
        // Handle additional data if needed
      })
      .catch((error) => {
        console.error(error);
        setLoginStatus(false);
      });
  }


  return (
    <div className="auth-wrapper">
      <div className="auth-inner">
        <form onSubmit={handleSubmit}>
          <h3>Sign In</h3>

          <div className="mb-3">
            <label>Email address</label>
            <input
              type="email"
              className="form-control"
              placeholder="Enter email"
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>

          <div className="mb-3">
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter password"
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>

          <div className="d-grid">
            <button type="submit" className="btn btn-primary">
              Submit
            </button>
          </div>
          
        </form>
      </div>
    </div>
  );
}

export default LoginPage;

// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';

// function LoginPage({ setIsLoggedIn }) {
//   const [email, setEmail] = useState('');
//   const [password, setPassword] = useState('');
//   const [loginStatus, setLoginStatus] = useState(null); // null for initial state, true for success, false for error

//   const navigate = useNavigate();

//   function handleSubmit(e) {
//     e.preventDefault();

//     console.log(email, password);
//     fetch('http://localhost:8080/user/login', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json',
//         Accept: 'application/json',
//       },
//       body: JSON.stringify({
//         email,
//         password,
//       }),
//     })
//       .then((res) => {
//         if (res.ok) {
//           setLoginStatus(true);

//           // Use the navigate function to redirect to App component on successful login
//           setTimeout(() => {
//             console.log("navigating");
//             navigate('/');
//             setIsLoggedIn(true);
//           }, 1000);
//         } else {
//           setLoginStatus(false);
//         }
//         return res.json();
//       })
//       .then((data) => {
//         console.log(data.token);
//         localStorage.setItem("userToken", "Bearer " + data.token); // Set the JWT token with "Bearer " prefix
//         localStorage.setItem("email", data.email);
//         localStorage.setItem("role", data.role);
//         localStorage.setItem("id", data.id);
//         // Handle additional data if needed
//       })
//       .catch((error) => {
//         console.error(error);
//         setLoginStatus(false);
//       });
//   }

//   return (
//     <div className="auth-wrapper">
//       <div className="auth-inner">
//         <form onSubmit={handleSubmit}>
//           <h3>Sign In</h3>

//           <div className="mb-3">
//             <label>Email address</label>
//             <input
//               type="email"
//               className="form-control"
//               placeholder="Enter email"
//               onChange={(e) => setEmail(e.target.value)}
//             />
//           </div>

//           <div className="mb-3">
//             <label>Password</label>
//             <input
//               type="password"
//               className="form-control"
//               placeholder="Enter password"
//               onChange={(e) => setPassword(e.target.value)}
//             />
//           </div>

//           <div className="d-grid">
//             <button type="submit" className="btn btn-primary">
//               Submit
//             </button>
//           </div>
//         </form>
//       </div>
//     </div>
//   );
// }

// export default LoginPage;

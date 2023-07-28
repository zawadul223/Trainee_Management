import React, { useEffect, useState } from 'react';
import { Container, Row, Col, Alert } from 'react-bootstrap';
import { Card } from 'react-bootstrap';

const Notice = () => {
  const [notices, setNotices] = useState([]);
  const [batchNumber, setBatchNumber] = useState(1); // Default batch number is 1

  useEffect(() => {
    fetchNotices();
  }, [batchNumber]);

  const fetchNotices = () => {
    // Fetch data from the API using fetch
    fetch(`http://localhost:8080/batch/notice/getNotices/${batchNumber}`)
      .then((response) => response.json())
      .then((data) => {
        setNotices(data);
      })
      .catch((error) => {
        console.error('Error fetching notices:', error);
      });
  };

  const handleInputChange = (event) => {
    // Update batchNumber state when the input value changes
    setBatchNumber(event.target.value);
  };

  const handleKeyPress = (event) => {
    // Call fetchNotices() when Enter key is pressed
    if (event.key === 'Enter') {
      fetchNotices();
    }
  };

  return (
    <div>
      <Container>
        <h1>All Notices</h1>
        <div>
          <label>Enter Batch Number: </label>
          <input
            type="number"
            value={batchNumber}
            onChange={handleInputChange}
            onClick={handleKeyPress}
          />
        </div>
        {notices.map((notice, index) => (
          <Card key={index} style={{ width: '48rem' }} className='mt-4'>
            <Card.Body>
              <Row xs={12} className='box-shadow'>
                <Col>
                  <h6 style={{ fontStyle: 'italic' }}>{notice.trainerName}</h6>
                  <p>{notice.notice}</p>
                </Col>
                <Col style={{ color: 'grey' }} className='text-end'>
                  {notice.time}
                </Col>
              </Row>
            </Card.Body>
          </Card>
        ))}
      </Container>
    </div>
  );
};

export default Notice;

// import React, { useEffect, useState } from 'react';
// import { Container, Row, Col, Alert } from 'react-bootstrap';
// import { Card } from 'react-bootstrap';

// const Notice = () => {
//   const [notices, setNotices] = useState([]);
//   const [batchNumber, setBatchNumber] = useState(1); // Default batch number is 1

//   useEffect(() => {
//     fetchNotices();
//   }, [batchNumber]);

//   const fetchNotices = () => {
//     const userToken = localStorage.getItem('userToken'); // Get the JWT token from local storage

//     // Fetch data from the API using fetch with the Authorization header
//     fetch(`http://localhost:8080/batch/notice/getNotices/${batchNumber}`, {
//       headers: {
//         'Content-Type': 'application/json',
//         Authorization: userToken, // Set the Authorization header with the JWT token
//       },
//     })
//       .then((response) => {
//         if (!response.ok) {
//           throw new Error('Request failed'); // Handle error responses
//         }
//         return response.json();
//       })
//       .then((data) => {
//         setNotices(data);
//       })
//       .catch((error) => {
//         console.error('Error fetching notices:', error);
//       });
//   };

//   const handleInputChange = (event) => {
//     // Update batchNumber state when the input value changes
//     setBatchNumber(event.target.value);
//   };

//   const handleKeyPress = (event) => {
//     // Call fetchNotices() when Enter key is pressed
//     if (event.key === 'Enter') {
//       fetchNotices();
//     }
//   };

//   return (
//     <div>
//       <Container>
//         <h1>All Notices</h1>
//         <div>
//           <label>Enter Batch Number: </label>
//           <input
//             type="number"
//             value={batchNumber}
//             onChange={handleInputChange}
//             onClick={handleKeyPress}
//           />
//         </div>
//         {notices.map((notice, index) => (
//           <Card key={index} style={{ width: '48rem' }} className='mt-4'>
//             <Card.Body>
//               <Row xs={12} className='box-shadow'>
//                 <Col>
//                   <h6 style={{ fontStyle: 'italic' }}>{notice.trainerName}</h6>
//                   <p>{notice.notice}</p>
//                 </Col>
//                 <Col style={{ color: 'grey' }} className='text-end'>
//                   {notice.time}
//                 </Col>
//               </Row>
//             </Card.Body>
//           </Card>
//         ))}
//       </Container>
//     </div>
//   );
// };

// export default Notice;

import React, { useState } from 'react';
import { Form, Table, Button, Modal } from 'react-bootstrap';

const BatchDetails = () => {
  const [batchNumber, setBatchNumber] = useState('');
  const [batchData, setBatchData] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [unassignedTrainees, setUnassignedTrainees] = useState([]);
  const [selectedTrainees, setSelectedTrainees] = useState([]);


  const handleInputChange = (event) => {
    setBatchNumber(event.target.value);
  };

  const handleFetchBatchDetails = () => {
    if (!batchNumber) {
      return;
    }

    fetch(`http://localhost:8080/batch/details/${batchNumber}`)
      .then((response) => response.json())
      .then((data) => {
        setBatchData(data);
        setShowModal(false); // Hide the modal after batch details are fetched
      })
      .catch((error) => {
        console.error('Error fetching batch details:', error);
        setBatchData(null); // Clear batchData on error
      });
  };

  const handleFetchUnassignedTrainees = () => {
    fetch('http://localhost:8080/user/unassigned/trainees')
      .then((response) => response.json())
      .then((data) => {
        // Assuming the data is returned as an array of trainee names
        // Update the unassignedTrainees state to contain an array of objects with IDs and names
        const traineesWithIds = data.unassignedList.map((trainee, index) => ({
          traineeId: index, // You can replace this with the actual trainee ID if available from the API
          name: trainee,
        }));
        setUnassignedTrainees(traineesWithIds);
        setShowModal(true); // Show the modal when unassigned trainees are fetched
      })
      .catch((error) => {
        console.error('Error fetching unassigned trainees:', error);
      });
  };

  const handleTraineeCheckboxChange = (traineeId, checked) => {
    if (checked) {
      setSelectedTrainees((prevSelected) => [...prevSelected, traineeId]);
    } else {
      setSelectedTrainees((prevSelected) =>
        prevSelected.filter((id) => id !== traineeId)
      );
    }
  };

  const handleAssignTrainees = () => {
    // Replace 1 with the actual batch ID
    const batchId = batchData?.batchId;

    if (batchId && selectedTrainees.length > 0) {
      fetch(`http://localhost:8080/batch/assign/trainee/${batchId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedTrainees),
      })
        .then((response) => response.json())
        .then((data) => {
          // Refresh the batch details after assigning trainees
          handleFetchBatchDetails();
          setShowModal(false);
          setSelectedTrainees([]); // Clear the selected trainees after assigning
        })
        .catch((error) => {
          console.error('Error assigning trainees:', error);
        });
    }
  };

  return (
    <div>
      <h1>Batch Details</h1>
      <Form.Control
        type="text"
        placeholder="Enter Batch Number"
        value={batchNumber}
        onChange={handleInputChange}
        onKeyDown={(event) => {
          if (event.key === 'Enter') {
            handleFetchBatchDetails();
          }
        }}
        size="sm" // Add this to make the text area smaller
      />
      <br />
      {batchData && (
        <div>
          <h2>{batchData.batchName}</h2>
          <p>
            Start Date: {batchData.startDate} | End Date: {batchData.endDate}
          </p>

          <h3>Trainees</h3>
          <ul>
            {batchData.traineeNames.map((trainee, index) => (
              <li key={index}>{trainee}</li>
            ))}
          </ul>
          {batchData && (
        <Button variant="primary" onClick={handleFetchUnassignedTrainees}>
          Assign Trainees
        </Button>
      )}


          <h3 className='mt-5'>Course Details</h3>
          <Table striped bordered hover>
          <thead>
              <tr>
                <th>Course Name</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Trainer</th>
              </tr>
            </thead>
            <tbody>
              {batchData.courses.map((course, index) => (
                <tr key={index}>
                  <td>{course.courseName}</td>
                  <td>{course.startDate}</td>
                  <td>{course.endDate}</td>
                  <td>{course.trainerNames.join(', ')}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      )}
      

      {/* Modal for assigning trainees */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
  <Modal.Body>
    <div style={{ maxHeight: '300px', overflowY: 'auto' }}>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Trainee Name</th>
            <th>Assign</th>
          </tr>
        </thead>
        <tbody>
          {unassignedTrainees.map((trainee, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              {/* Render the trainee name instead of the whole trainee object */}
              <td>{trainee.name}</td>
              <td>
                <Form.Check
                  type="checkbox"
                  onChange={(e) =>
                    handleTraineeCheckboxChange(trainee.traineeId, e.target.checked)
                  }
                  checked={selectedTrainees.includes(trainee.traineeId)}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  </Modal.Body>
  <Modal.Footer>
    <Button variant="secondary" onClick={() => setShowModal(false)}>
      Cancel
    </Button>
    <Button variant="primary" onClick={handleAssignTrainees}>
      Assign
    </Button>
  </Modal.Footer>
</Modal>


    </div>
  );
};

export default BatchDetails;


// import React, { useState, useEffect } from 'react';
// import { Form, Table, Button, Modal } from 'react-bootstrap';

// const BatchDetails = () => {
//   const [batchNumber, setBatchNumber] = useState('');
//   const [batchData, setBatchData] = useState(null);
//   const [showModal, setShowModal] = useState(false);
//   const [unassignedTrainees, setUnassignedTrainees] = useState([]);
//   const [selectedTrainees, setSelectedTrainees] = useState([]);

//   useEffect(() => {
//     fetchUnassignedTrainees();
//   }, []);

//   const fetchUnassignedTrainees = () => {
//     const userToken = localStorage.getItem('userToken');

//     fetch('http://localhost:8080/user/unassigned/trainees', {
//       headers: {
//         'Content-Type': 'application/json',
//         Authorization: userToken ? `Bearer ${userToken}` : '',
//       },
//     })
//       .then((response) => response.json())
//       .then((data) => {
//         const traineesWithIds = data.unassignedList.map((trainee, index) => ({
//           traineeId: index,
//           name: trainee,
//         }));
//         setUnassignedTrainees(traineesWithIds);
//       })
//       .catch((error) => {
//         console.error('Error fetching unassigned trainees:', error);
//       });
//   };

//   const handleInputChange = (event) => {
//     setBatchNumber(event.target.value);
//   };

//   const handleFetchBatchDetails = () => {
//     if (!batchNumber) {
//       return;
//     }

//     fetch(`http://localhost:8080/batch/details/${batchNumber}`, {
//       headers: {
//         'Content-Type': 'application/json',
//         Authorization: localStorage.getItem('userToken')
//           ? `Bearer ${localStorage.getItem('userToken')}`
//           : '',
//       },
//     })
//       .then((response) => response.json())
//       .then((data) => {
//         setBatchData(data);
//         setShowModal(false);
//       })
//       .catch((error) => {
//         console.error('Error fetching batch details:', error);
//         setBatchData(null);
//       });
//   };

//   const handleTraineeCheckboxChange = (traineeId, checked) => {
//     if (checked) {
//       setSelectedTrainees((prevSelected) => [...prevSelected, traineeId]);
//     } else {
//       setSelectedTrainees((prevSelected) =>
//         prevSelected.filter((id) => id !== traineeId)
//       );
//     }
//   };

//   const handleAssignTrainees = () => {
//     const batchId = batchData?.batchId;

//     if (batchId && selectedTrainees.length > 0) {
//       fetch(`http://localhost:8080/batch/assign/trainee/${batchId}`, {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//           Authorization: localStorage.getItem('userToken')
//             ? `Bearer ${localStorage.getItem('userToken')}`
//             : '',
//         },
//         body: JSON.stringify(selectedTrainees),
//       })
//         .then((response) => response.json())
//         .then((data) => {
//           handleFetchBatchDetails();
//           setShowModal(false);
//           setSelectedTrainees([]);
//         })
//         .catch((error) => {
//           console.error('Error assigning trainees:', error);
//         });
//     }
//   };

//   return (
//     <div>
//       <h1>Batch Details</h1>
//       <Form.Control
//         type="text"
//         placeholder="Enter Batch Number"
//         value={batchNumber}
//         onChange={handleInputChange}
//         onKeyDown={(event) => {
//           if (event.key === 'Enter') {
//             handleFetchBatchDetails();
//           }
//         }}
//         size="sm"
//       />
//       <br />
//       {batchData && (
//         <div>
//           <h2>{batchData.batchName}</h2>
//           <p>
//             Start Date: {batchData.startDate} | End Date: {batchData.endDate}
//           </p>

//           <h3>Trainees</h3>
//           <ul>
//             {batchData.traineeNames.map((trainee, index) => (
//               <li key={index}>{trainee}</li>
//             ))}
//           </ul>
//           {batchData && (
//             <Button variant="primary" onClick={handleFetchUnassignedTrainees}>
//               Assign Trainees
//             </Button>
//           )}

//           <h3 className="mt-5">Course Details</h3>
//                      <Table striped bordered hover>
//            <thead>
//                <tr>
//                <th>Course Name</th>
//                  <th>Start Date</th>
//                  <th>End Date</th>
//                  <th>Trainer</th>
//                </tr>
//              </thead>
//              <tbody>
//                {batchData.courses.map((course, index) => (
//                  <tr key={index}>
//                    <td>{course.courseName}</td>
//                    <td>{course.startDate}</td>
//                    <td>{course.endDate}</td>
//                    <td>{course.trainerNames.join(', ')}</td>
//                  </tr>
//                ))}
//              </tbody>
//            </Table>
//         </div>
//       )}

//       {/* Modal for assigning trainees */}
//       <Modal show={showModal} onHide={() => setShowModal(false)}>
//         <Modal.Body>
//           <div style={{ maxHeight: '300px', overflowY: 'auto' }}>
//             <Table striped bordered hover>
//               <thead>
//                 <tr>
//                   <th>#</th>
//                   <th>Trainee Name</th>
//                   <th>Assign</th>
//                 </tr>
//               </thead>
//               <tbody>
//                 {unassignedTrainees.map((trainee, index) => (
//                   <tr key={index}>
//                     <td>{index + 1}</td>
//                     <td>{trainee.name}</td>
//                     <td>
//                       <Form.Check
//                         type="checkbox"
//                         onChange={(e) =>
//                           handleTraineeCheckboxChange(
//                             trainee.traineeId,
//                             e.target.checked
//                           )
//                         }
//                         checked={selectedTrainees.includes(trainee.traineeId)}
//                       />
//                     </td>
//                   </tr>
//                 ))}
//               </tbody>
//             </Table>
//           </div>
//         </Modal.Body>
//         <Modal.Footer>
//           <Button variant="secondary" onClick={() => setShowModal(false)}>
//             Cancel
//           </Button>
//           <Button variant="primary" onClick={handleAssignTrainees}>
//             Assign
//           </Button>
//         </Modal.Footer>
//       </Modal>
//     </div>
//   );
// };

// export default BatchDetails;

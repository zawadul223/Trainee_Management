import React, { useEffect, useState } from 'react';
import { Container, Table, Button, Modal, Form } from 'react-bootstrap';
import { Card } from 'react-bootstrap';

const Batches = () => {
  const [batches, setBatches] = useState([]);
  const [batchName, setBatchName] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');

  useEffect(() => {
    fetchBatches();
  }, []);

  const fetchBatches = () => {
    // Fetch data from the API using fetch
    fetch(`http://localhost:8080/batch/all`)
      .then((response) => response.json())
      .then((data) => {
        setBatches(data);
      })
      .catch((error) => {
        console.error('Error fetching batches:', error);
      });
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    if (name === 'batchName') {
      setBatchName(value);
    } else if (name === 'startDate') {
      setStartDate(value);
    } else if (name === 'endDate') {
      setEndDate(value);
    }
  };

  const handleCreateBatch = () => {
    const newBatch = {
      batchName: batchName,
      startDate: startDate,
      endDate: endDate,
    };

    // Send data to the API using fetch
    fetch('http://localhost:8080/batch/create', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newBatch),
    })
      .then((response) => response.json())
      .then((data) => {
        // Check if the batch was created successfully
        if (data.success) {
          
          setModalMessage('Batch created');
        } else {
          setModalMessage('Couldn\'t create a batch');
        }
        setShowModal(true);
      })
      .catch((error) => {
        console.error('Error creating batch:', error);
        setModalMessage('Couldn\'t create a batch');
        setShowModal(true);
      });
  };

  return (
    <div>
      <Container>
        <h1 className="mb-4">All Batches</h1>
        <Button variant="primary" className='mb-3' onClick={() => setShowModal(true)}>Create New Batch</Button>

        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Batch Name</th>
              <th>Start Date</th>
              <th>End Date</th>
            </tr>
          </thead>
          <tbody>
            {batches.map((batch, index) => (
              <tr key={index}>
                <td>{batch.batchName}</td>
                <td>{batch.startDate}</td>
                <td>{batch.endDate}</td>
              </tr>
            ))}
          </tbody>
        </Table>

        {/* Modal for creating a new batch */}
        <Modal show={showModal} onHide={() => setShowModal(false)}>
          <Modal.Header closeButton>
            <Modal.Title>Create New Batch</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form>
              <Form.Group controlId="formBatchName">
                <Form.Label>Batch Name</Form.Label>
                <Form.Control
                  type="text"
                  name="batchName"
                  value={batchName}
                  onChange={handleInputChange}
                />
              </Form.Group>

              <Form.Group controlId="formStartDate">
                <Form.Label>Start Date</Form.Label>
                <Form.Control
                  type="date"
                  name="startDate"
                  value={startDate}
                  onChange={handleInputChange}
                />
              </Form.Group>

              <Form.Group controlId="formEndDate">
                <Form.Label>End Date</Form.Label>
                <Form.Control
                  type="date"
                  name="endDate"
                  value={endDate}
                  onChange={handleInputChange}
                />
              </Form.Group>
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowModal(false)}>Cancel</Button>
            <Button variant="primary" onClick={handleCreateBatch}>Create</Button>
          </Modal.Footer>
        </Modal>

        {/* Modal for showing success/error message */}
        <Modal show={!!modalMessage} onHide={() => setModalMessage('')}>
          <Modal.Header closeButton>
            <Modal.Title>Batch Creation Status</Modal.Title>
          </Modal.Header>
          <Modal.Body>{modalMessage}</Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={() => setModalMessage('')}>Close</Button>
          </Modal.Footer>
        </Modal>
      </Container>
    </div>
  );
};

export default Batches;


// import React, { useEffect, useState } from 'react';
// import { Container, Table, Button, Modal, Form } from 'react-bootstrap';
// import { Card } from 'react-bootstrap';

// const Batches = () => {
//   const [batches, setBatches] = useState([]);
//   const [batchName, setBatchName] = useState('');
//   const [startDate, setStartDate] = useState('');
//   const [endDate, setEndDate] = useState('');
//   const [showModal, setShowModal] = useState(false);
//   const [modalMessage, setModalMessage] = useState('');

//   useEffect(() => {
//     fetchBatches();
//   }, []);

//   const fetchBatches = () => {
//     const userToken = localStorage.getItem('userToken'); // Get the JWT token from local storage

//     // Fetch data from the API using fetch with the Authorization header
//     fetch('http://localhost:8080/batch/all', {
//       headers: {
//         'Content-Type': 'application/json',
//         Authorization: userToken, // Set the Authorization header with the JWT token
//       },
//     })
//       .then((response) => response.json())
//       .then((data) => {
//         setBatches(data);
//       })
//       .catch((error) => {
//         console.error('Error fetching batches:', error);
//       });
//   };

//   const handleInputChange = (event) => {
//     const { name, value } = event.target;
//     if (name === 'batchName') {
//       setBatchName(value);
//     } else if (name === 'startDate') {
//       setStartDate(value);
//     } else if (name === 'endDate') {
//       setEndDate(value);
//     }
//   };

//   const handleCreateBatch = () => {
//     const newBatch = {
//       batchName: batchName,
//       startDate: startDate,
//       endDate: endDate,
//     };

//     // Send data to the API using fetch with the Authorization header
//     const userToken = localStorage.getItem('userToken'); // Get the JWT token from local storage
//     fetch('http://localhost:8080/batch/create', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json',
//         Authorization: userToken, // Set the Authorization header with the JWT token
//       },
//       body: JSON.stringify(newBatch),
//     })
//       .then((response) => response.json())
//       .then((data) => {
//         // Check if the batch was created successfully
//         if (data.success) {
//           setModalMessage('Batch created');
//         } else {
//           setModalMessage("Couldn't create a batch");
//         }
//         setShowModal(true);
//       })
//       .catch((error) => {
//         console.error('Error creating batch:', error);
//         setModalMessage("Couldn't create a batch");
//         setShowModal(true);
//       });
//   };

//   return (
//     <div>
//       <Container>
//         <h1 className="mb-4">All Batches</h1>
//         <Button variant="primary" className="mb-3" onClick={() => setShowModal(true)}>
//           Create New Batch
//         </Button>

//         <Table striped bordered hover>
//           <thead>
//             <tr>
//               <th>Batch Name</th>
//               <th>Start Date</th>
//               <th>End Date</th>
//             </tr>
//           </thead>
//           <tbody>
//             {batches.map((batch, index) => (
//               <tr key={index}>
//                 <td>{batch.batchName}</td>
//                 <td>{batch.startDate}</td>
//                 <td>{batch.endDate}</td>
//               </tr>
//             ))}
//           </tbody>
//         </Table>

//         {/* Modal for creating a new batch */}
//         <Modal show={showModal} onHide={() => setShowModal(false)}>
//           <Modal.Header closeButton>
//             <Modal.Title>Create New Batch</Modal.Title>
//           </Modal.Header>
//           <Modal.Body>
//             <Form>
//               <Form.Group controlId="formBatchName">
//                 <Form.Label>Batch Name</Form.Label>
//                 <Form.Control
//                   type="text"
//                   name="batchName"
//                   value={batchName}
//                   onChange={handleInputChange}
//                 />
//               </Form.Group>

//               <Form.Group controlId="formStartDate">
//                 <Form.Label>Start Date</Form.Label>
//                 <Form.Control
//                   type="date"
//                   name="startDate"
//                   value={startDate}
//                   onChange={handleInputChange}
//                 />
//               </Form.Group>

//               <Form.Group controlId="formEndDate">
//                 <Form.Label>End Date</Form.Label>
//                 <Form.Control
//                   type="date"
//                   name="endDate"
//                   value={endDate}
//                   onChange={handleInputChange}
//                 />
//               </Form.Group>
//             </Form>
//           </Modal.Body>
//           <Modal.Footer>
//             <Button variant="secondary" onClick={() => setShowModal(false)}>Cancel</Button>
//             <Button variant="primary" onClick={handleCreateBatch}>Create</Button>
//           </Modal.Footer>
//         </Modal>
//         {/* Modal for showing success/error message */}
//         <Modal show={!!modalMessage} onHide={() => setModalMessage('')}>
//           <Modal.Header closeButton>
//             <Modal.Title>Batch Creation Status</Modal.Title>
//           </Modal.Header>
//           <Modal.Body>{modalMessage}</Modal.Body>
//           <Modal.Footer>
//             <Button variant="primary" onClick={() => setModalMessage('')}>Close</Button>
//           </Modal.Footer>
//         </Modal>
//       </Container>
//     </div>
//   );
// };

// export default Batches;

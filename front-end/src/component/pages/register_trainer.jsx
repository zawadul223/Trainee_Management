import React, { useState } from 'react';
import { Card, Form, Button, Container, Row, Col } from 'react-bootstrap';

const TrainerRegistrationPage = () => {
  const [trainerData, setTrainerData] = useState({
    name: '',
    designation: '',
    trainerEmail: '',
    joiningDate: '',
    experience: '',
    expertise: '',
    trainerContactNo: '',
    password: '',
  });
  const [registrationStatus, setRegistrationStatus] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTrainerData({ ...trainerData, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    // Send trainerData to the API
    fetch('http://localhost:8080/user/register/trainer', {
      method: 'POST',
      crossDomain: true,
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
      body: JSON.stringify(trainerData),
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        } else {
          throw new Error('Trainer registration failed');
        }
      })
      .then((data) => {
        // Set the registration status to true to display the success message
        
        setRegistrationStatus(true);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card className="p-4 shadow" style={{ width: '800px' }}>
        <Card.Title className="text-center mb-4">
          Trainer Registration
        </Card.Title>
        <Form onSubmit={handleSubmit}>
          <Row>
            <Col>
              <Form.Group controlId="name">
                <Form.Label>Name</Form.Label>
                <Form.Control
                  type="text"
                  name="name"
                  value={trainerData.name}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="designation">
                <Form.Label>Designation</Form.Label>
                <Form.Control
                  type="text"
                  name="designation"
                  value={trainerData.designation}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="trainerEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  name="trainerEmail"
                  value={trainerData.trainerEmail}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="joiningDate">
                <Form.Label>Joining Date</Form.Label>
                <Form.Control
                  type="date"
                  name="joiningDate"
                  value={trainerData.joiningDate}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="experience">
                <Form.Label>Experience</Form.Label>
                <Form.Control
                  type="text"
                  name="experience"
                  value={trainerData.experience}
                  onChange={handleChange}
                />
              </Form.Group>
            </Col>

            <Col>
              <Form.Group controlId="expertise">
                <Form.Label>Expertise</Form.Label>
                <Form.Control
                  type="text"
                  name="expertise"
                  value={trainerData.expertise}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="trainerContactNo">
                <Form.Label>Contact Number</Form.Label>
                <Form.Control
                  type="text"
                  name="trainerContactNo"
                  value={trainerData.trainerContactNo}
                  onChange={handleChange}
                />
              </Form.Group>

              <Form.Group controlId="password">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  value={trainerData.password}
                  onChange={handleChange}
                />
              </Form.Group>
            </Col>
          </Row>

          <Button variant="primary" type="submit" className="w-100 mt-4">
            Submit
          </Button>
        </Form>
        {registrationStatus && (
          <div className="alert alert-success mt-3" role="alert">
            Registration Successful!
          </div>
        )}
      </Card>
    </Container>
  );
};

export default TrainerRegistrationPage;

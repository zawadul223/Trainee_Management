import React, { useState, useEffect } from 'react';
import { Button, Modal, Form, Card } from 'react-bootstrap';

const TrainerClassroomPage = ({ batches }) => {
  const [showModal, setShowModal] = useState(false);
  const [classroomId, setClassroomId] = useState('');
  const [postMessage, setPostMessage] = useState('');
  const [posts, setPosts] = useState([]);

  const trainerId = localStorage.getItem('id');

  const handleShowModal = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleCreatePost = () => {
    const postModel = {
      message: postMessage,
    };

    fetch(`http://localhost:8080/classroom/post/${classroomId}/${trainerId}`, {
      method: 'POST',
      crossDomain: true,
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
      body: JSON.stringify(postModel),
    })
      .then((res) => {
        if (res.ok) {
          alert('Post created successfully!');
          handleCloseModal();
          fetchPosts();
        } else {
          alert('Failed to create post. Please try again later.');
        }
      })
      .catch((error) => {
        console.error('Error creating post:', error);
        alert('Failed to create post. Please try again later.');
      });
  };

  const fetchPosts = () => {
    if (!classroomId) {
      return;
    }

    fetch(`http://localhost:8080/classroom/allPosts/${classroomId}`)
      .then((response) => response.json())
      .then((data) => {
        setPosts(data);
      })
      .catch((error) => {
        console.error('Error fetching posts:', error);
        setPosts([]);
      });
  };

  useEffect(() => {
    if (batches.length > 0) {
      // Assuming the first batchId should be selected by default
      setClassroomId(batches[0]);
    }
  }, [batches]);

  useEffect(() => {
    fetchPosts();
  }, [classroomId]);

  return (
    <div>
      <h1>Trainer Classroom</h1>
      <Form.Group controlId="classroomId">
        <Form.Label>Select Batch ID</Form.Label>
        <Form.Control as="select" value={classroomId} onChange={(e) => setClassroomId(e.target.value)}>
          {batches.map((batchId) => (
            <option key={batchId} value={batchId}>
              Batch {batchId}
            </option>
          ))}
        </Form.Control>
      </Form.Group>

      {/* Display posts */}
      {posts.map((post, index) => (
        <Card key={index} className="mt-4">
          <Card.Header>
            <h5 style={{ fontFamily: 'Arial', fontWeight: 'bold' }}>{post.trainerName}</h5>
            <p style={{ fontFamily: 'Times New Roman', fontSize: '12px' }}>{post.date}</p>
          </Card.Header>
          <Card.Body>
            <p style={{ fontFamily: 'Calibri', fontSize: '16px' }}>{post.message}</p>
          </Card.Body>
        </Card>
      ))}

      <Button variant="primary" onClick={handleShowModal}>
        Create Post
      </Button>
      <Modal show={showModal} onHide={handleCloseModal}>
        {/* ... (existing code for modal, same as before) */}
      </Modal>
    </div>
  );
};

export default TrainerClassroomPage;

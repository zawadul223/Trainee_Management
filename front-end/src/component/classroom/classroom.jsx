import React, { useState, useEffect } from 'react';
import { Button, Modal, Form, Card } from 'react-bootstrap';

const ClassroomPage = () => {
  const [showModal, setShowModal] = useState(false);
  const [classroomId, setClassroomId] = useState('');
  const [postMessage, setPostMessage] = useState('');
  const [posts, setPosts] = useState([]);
  const userRole = localStorage.getItem('role');

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

    fetch(`http://localhost:8080/classroom/post/${classroomId}`, {
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
    fetchPosts();
  }, [classroomId]);

  return (
    <div>
      <h1>Classroom</h1>
      {userRole === 'ADMIN' && (
        <Form.Group className="mt-3">
          <Form.Control
            type="text"
            placeholder="Enter Classroom ID"
            value={classroomId}
            onChange={(e) => setClassroomId(e.target.value)}
          />
        </Form.Group>
      )}

      {/* Modal for creating a post */}
      {/* <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Create Post</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group controlId="postMessage">
            <Form.Label>Post Message</Form.Label>
            <Form.Control
              as="textarea"
              rows={5}
              value={postMessage}
              onChange={(e) => setPostMessage(e.target.value)}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
          <Button variant="primary" onClick={handleCreatePost}>
            Create
          </Button>
        </Modal.Footer>
      </Modal> */}

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
    </div>
  );
};

export default ClassroomPage;

import React, { useState, useEffect } from 'react';
import { Button, Modal, Form, Card } from 'react-bootstrap';

const TrainerClassroomPage = () => {
  const [batches, setBatches] = useState([]);
  const [selectedBatch, setSelectedBatch] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [postMessage, setPostMessage] = useState('');
  const [posts, setPosts] = useState([]);
  const trainerId = localStorage.getItem('id');
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

    fetch(`http://localhost:8080/classroom/post/${selectedBatch}/${trainerId}`, {
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

  const fetchBatches = () => {
    fetch(`http://localhost:8080/user/trainer/getBatch/${trainerId}`)
      .then((response) => response.json())
      .then((data) => {
        setBatches(data.batchIds);
      })
      .catch((error) => {
        console.error('Error fetching trainer batches:', error);
      });
  };

  const fetchBatchPosts = (batchId) => {
    setSelectedBatch(batchId);
    fetch(`http://localhost:8080/classroom/allPosts/${batchId}`)
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
    fetchBatches();
  }, []);

  useEffect(() => {
    if (selectedBatch) {
      fetchBatchPosts(selectedBatch);
    }
  }, [selectedBatch]);

  return (
    <div>
      <h1>Trainer Classroom</h1>
      {userRole === "TRAINER" && 
      <>
        <div>
        {batches.map((batchId, index) => (
          <div key={index}>
            <button onClick={() => fetchBatchPosts(batchId)}>Batch {batchId}</button>
          </div>
        ))}
      </div>

      {/* Modal for creating a post */}
      <Modal show={showModal} onHide={handleCloseModal}>
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
      </Modal>

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
      </>}
      
    </div>
  );
};

export default TrainerClassroomPage;

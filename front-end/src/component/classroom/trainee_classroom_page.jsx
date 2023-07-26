import React, { useState, useEffect } from 'react';
import { Card } from 'react-bootstrap';

const TraineeClassroomPage = () => {
  const [classroomId, setClassroomId] = useState('');
  const [posts, setPosts] = useState([]);

  const traineeId = localStorage.getItem('id');

  const fetchTraineeBatchId = () => {
    fetch(`http://localhost:8080/user/trainee/getBatch/${traineeId}`)
      .then((response) => response.json())
      .then((data) => {
        setClassroomId(data.batchId);
      })
      .catch((error) => {
        console.error('Error fetching trainee batch ID:', error);
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
    fetchTraineeBatchId();
  }, []);

  useEffect(() => {
    fetchPosts();
  }, [classroomId]);

  return (
    <div>
      <h1>Trainee Classroom</h1>

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

export default TraineeClassroomPage;

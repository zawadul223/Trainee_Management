import React, { useState } from 'react';
import { FiUser } from 'react-icons/fi';
import DropdownSelect from 'react-dropdown-select';
import './user_icon.css'

const UserDropdown = () => {
  const [showLogout, setShowLogout] = useState(false);

  const handleLogout = () => {
    // Implement your logout logic here
    console.log('Logging out...');
    // For this example, we just hide the logout dropdown
    setShowLogout(false);
  };

  const userOptions = [
    { label: 'Logout', value: 'logout', onClick: handleLogout },
  ];

  return (
    <div>
      <div id='user-icon' onClick={() => setShowLogout(!showLogout)}>
        <FiUser />
      </div>
      {showLogout && (
        <div id='logout-dropdown'>
          <DropdownSelect
            searchable={false}
            options={userOptions}
            onChange={() => handleLogout()}
            dropdownGap={5}
            keepOpenOnSelect
          />
        </div>
      )}
    </div>
  );
};

export default UserDropdown;

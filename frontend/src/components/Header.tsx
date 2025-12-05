import React, { useEffect } from 'react';
import { useGetOrganizationsConfigurationsDatabaseQuery } from '../store/types.generated';

const Header: React.FC = () => {
  return (
    <header className="app-header">
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '1rem' }}>
        <p>SOA Lab2</p>
      </div>
    </header>
  );
};

export default Header;


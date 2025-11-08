import React, { useEffect } from 'react';
import { useGetOrganizationsConfigurationsDatabaseQuery } from '../store/types.generated';

const Header: React.FC = () => {
  const { data: databaseVariant, isLoading, refetch } = useGetOrganizationsConfigurationsDatabaseQuery();

  useEffect(() => {
    const interval = setInterval(() => {
      refetch();
    }, 5000);

    return () => clearInterval(interval);
  }, [refetch]);

  const getDatabaseLogoUrl = () => {
    if (!databaseVariant) return null;
    
    const logos: Record<string, string> = {
      MYSQL: 'https://www.mysql.com/common/logos/logo-mysql-170x115.png',
      POSTGRESQL: 'https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg',
    };
    
    return logos[databaseVariant] || null;
  };

  const getDatabaseName = () => {
    if (!databaseVariant) return '';
    return databaseVariant === 'MYSQL' ? 'MySQL' : 'PostgreSQL';
  };

  return (
    <header className="app-header">
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '1rem' }}>
        <p>SOA Lab2</p>
        {!isLoading && databaseVariant && (
          <div style={{
            display: 'flex',
            alignItems: 'center',
            gap: '0.5rem',
            background: 'rgba(151, 133, 232, 0.1)',
            padding: '0.5rem 1rem',
            borderRadius: '20px',
            border: '1px solid rgba(151, 133, 232, 0.3)'
          }}>
            <img 
              src={getDatabaseLogoUrl() || ''} 
              alt={getDatabaseName()}
              style={{ 
                width: '24px', 
                height: '24px', 
                objectFit: 'contain' 
              }} 
            />
            <span style={{ color: '#9785e8', fontWeight: 600, fontSize: '0.9rem' }}>{getDatabaseName()}</span>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;


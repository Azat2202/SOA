import React, { useEffect, useState } from 'react';
import { useGetOrgdirectoryOrganizationQuery, OrganizationStatus } from '../store/types.generated';

interface OrganizationCreationModalProps {
  workflowUuid: string;
  onClose: () => void;
}

const STATUS_SEQUENCE: OrganizationStatus[] = [
  'INITIATED',
  'MONEY_TAKEN',
  'ORGANIZATION_CREATED',
  'MONEY_RETURNING',
];

const STATUS_LABELS: Record<OrganizationStatus, string> = {
  INITIATED: 'Initiated',
  MONEY_TAKEN: 'Money Taken',
  ORGANIZATION_CREATED: 'Organization Created',
  MONEY_RETURNING: 'Money Returned',
};

const OrganizationCreationModal: React.FC<OrganizationCreationModalProps> = ({
  workflowUuid,
  onClose,
}) => {
  const [currentStatus, setCurrentStatus] = useState<OrganizationStatus | null>(null);
  const { data: statusData, refetch } = useGetOrgdirectoryOrganizationQuery(
    { uuid: workflowUuid },
    { skip: !workflowUuid }
  );

  useEffect(() => {
    if (statusData) {
      setCurrentStatus(statusData);
    }
  }, [statusData]);

  useEffect(() => {
    const interval = setInterval(() => {
      refetch();
    }, 300);

    return () => clearInterval(interval);
  }, [refetch]);

  const getStatusIndex = (status: OrganizationStatus | null): number => {
    if (!status) return -1;
    return STATUS_SEQUENCE.indexOf(status);
  };

  const currentIndex = getStatusIndex(currentStatus);
  
  const isAllCompleted = currentStatus === 'ORGANIZATION_CREATED';
  const isMoneyReturning = currentStatus === 'MONEY_RETURNING';

  return (
    <div
      style={{
        position: 'fixed',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        zIndex: 1000,
      }}
      onClick={onClose}
    >
      <div
        style={{
          backgroundColor: 'white',
          borderRadius: '12px',
          padding: '2rem',
          maxWidth: '600px',
          width: '90%',
          boxShadow: '0 8px 32px rgba(0, 0, 0, 0.2)',
        }}
        onClick={(e) => e.stopPropagation()}
      >
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
          <h2 style={{ margin: 0, color: '#2c3e50' }}>
            {isAllCompleted ? 'Organization Created Successfully!' : isMoneyReturning ? 'Organization Creation Failed' : 'Creating Organization'}
          </h2>
          <button
            onClick={onClose}
            style={{
              background: 'none',
              border: 'none',
              fontSize: '1.5rem',
              cursor: 'pointer',
              color: '#666',
              padding: '0.5rem',
            }}
          >
            ×
          </button>
        </div>

        <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
          {STATUS_SEQUENCE.map((status, index) => {
            const isFailed = isMoneyReturning && status === 'ORGANIZATION_CREATED';
            const isMoneyReturningCompleted = isMoneyReturning && status === 'MONEY_RETURNING';
            const isCompleted = ((currentIndex >= index || isAllCompleted) && !isFailed && status !== 'MONEY_RETURNING') || isMoneyReturningCompleted;
            const isCurrent = !isAllCompleted && !isMoneyReturning && currentIndex === index - 1 && currentIndex >= 0;
            const isPending = (currentIndex < index - 1 || (currentIndex === -1 && index > 0)) && !isFailed && !isMoneyReturningCompleted;

            return (
              <div
                key={status}
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '1rem',
                  padding: '1rem',
                  borderRadius: '8px',
                  backgroundColor: isFailed ? '#ffeaea' : isCurrent ? '#e6e2fb' : isCompleted ? '#e8f5e8' : '#f8f9fa',
                  border: isFailed ? '2px solid #e74c3c' : isCurrent ? '2px solid #9785e8' : '2px solid transparent',
                  transition: 'all 0.3s ease',
                }}
              >
                <div style={{ width: '40px', height: '40px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                  {isFailed ? (
                    <span style={{ fontSize: '1.5rem', color: '#e74c3c' }}>✗</span>
                  ) : isCompleted ? (
                    <span style={{ fontSize: '1.5rem', color: '#2e7d32' }}>✓</span>
                  ) : isCurrent ? (
                    <div
                      style={{
                        width: '24px',
                        height: '24px',
                        border: '3px solid #9785e8',
                        borderTop: '3px solid transparent',
                        borderRadius: '50%',
                        animation: 'spin 1s linear infinite',
                      }}
                    />
                  ) : (
                    <div
                      style={{
                        width: '24px',
                        height: '24px',
                        borderRadius: '50%',
                        backgroundColor: '#ddd',
                      }}
                    />
                  )}
                </div>
                <div style={{ flex: 1 }}>
                  <div
                    style={{
                      fontWeight: isCurrent || isFailed ? 600 : 400,
                      color: isFailed ? '#e74c3c' : isCurrent ? '#9785e8' : isCompleted ? '#2e7d32' : '#666',
                      fontSize: '1.1rem',
                    }}
                  >
                    {STATUS_LABELS[status]}
                  </div>
                  {isCurrent && (
                    <div style={{ fontSize: '0.875rem', color: '#666', marginTop: '0.25rem' }}>
                      In progress...
                    </div>
                  )}
                  {isFailed && (
                    <div style={{ fontSize: '0.875rem', color: '#e74c3c', marginTop: '0.25rem' }}>
                      Failed
                    </div>
                  )}
                </div>
              </div>
            );
          })}
        </div>
      </div>
      <style>{`
        @keyframes spin {
          0% { transform: rotate(0deg); }
          100% { transform: rotate(360deg); }
        }
      `}</style>
    </div>
  );
};

export default OrganizationCreationModal;


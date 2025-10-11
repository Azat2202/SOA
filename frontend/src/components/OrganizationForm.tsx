import React, { useState } from 'react';
import { Organization, OrganizationRead } from '../store/types.generated';

interface OrganizationFormProps {
  organization?: OrganizationRead;
  onSubmit: (data: Organization) => void;
  onCancel: () => void;
  title: string;
}

const OrganizationForm: React.FC<OrganizationFormProps> = ({
  organization,
  onSubmit,
  onCancel,
  title,
}) => {
  const [formData, setFormData] = useState<Organization>({
    name: organization?.name || '',
    coordinates: {
      x: organization?.coordinates?.x || 0,
      y: organization?.coordinates?.y || 0,
    },
    annualTurnover: organization?.annualTurnover || 1,
    fullName: organization?.fullName || '',
    employeesCount: organization?.employeesCount || null,
    type: organization?.type || 'PUBLIC',
    postalAddress: organization?.postalAddress ? {
      street: organization.postalAddress.street,
      town: {
        x: organization.postalAddress.town.x,
        y: organization.postalAddress.town.y,
        z: organization.postalAddress.town.z,
        name: organization.postalAddress.town.name || '',
      },
    } : undefined,
  });

  const [errors, setErrors] = useState<Record<string, string>>({});

  const validateForm = (): boolean => {
    const newErrors: Record<string, string> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Name is required';
    }

    if (isNaN(formData.coordinates.x)) {
      newErrors.coordinatesX = 'X coordinate is required';
    }

    if (isNaN(formData.coordinates.y)) {
      newErrors.coordinatesY = 'Y coordinate is required';
    }

    if (isNaN(formData.annualTurnover)) {
      newErrors.annualTurnover = 'Annual turnover is required';
    }

    if (formData.coordinates.x < -365) {
      newErrors.coordinatesX = 'Coordinate X must be greater than -365';
    }

    if (!isNaN(formData.annualTurnover) && formData.annualTurnover < 1) {
      newErrors.annualTurnover = 'Annual turnover must be greater than 0';
    }

    if (formData.employeesCount && formData.employeesCount < 1) {
      newErrors.employeesCount = 'Employee number must be greater than 0';
    }

    if (formData.fullName && formData.fullName.length > 918) {
      newErrors.fullName = 'Fullname must be shorter than 919';
    }

    if (formData.postalAddress) {
      if (!formData.postalAddress.street.trim()) {
        newErrors.postalAddressStreet = 'Street is required';
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      onSubmit(formData);
    }
  };

  const handleInputChange = (field: string, value: any) => {
    setFormData(prev => {
      const newData = { ...prev };
      
      if (field.includes('.')) {
        const [parent, child, little] = field.split('.');
        if (parent === 'coordinates') {
          newData.coordinates = { ...newData.coordinates, [child]: value };
        } else if (parent === 'postalAddress') {
          if (child === 'street') {
            newData.postalAddress = { ...newData.postalAddress!, street: value };
          } else if (child ==='town') {
            newData.postalAddress = {
              ...newData.postalAddress!,
              town: { ...newData.postalAddress!.town, [little]: value }
            };
          }
        }
      } else {
        (newData as any)[field] = value;
      }
      
      return newData;
    });

    // Clear error when user starts typing
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: '' }));
    }
  };

  const togglePostalAddress = () => {
    if (formData.postalAddress) {
      setFormData(prev => ({ ...prev, postalAddress: undefined }));
    } else {
      setFormData(prev => ({
        ...prev,
        postalAddress: {
          street: '',
          town: { x: 0, y: 0, z: 0, name: '' },
        },
      }));
    }
  };

  return (
    <div className="card">
      <div className="card-header">
        <h3>{title}</h3>
      </div>
      <div className="card-body">
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="col-6">
              <div className="form-group" style={{maxWidth: '550px'}}>
                <label className="form-label">Name *</label>
                <input
                  type="text"
                  className={`form-control ${errors.name ? 'error' : ''}`}
                  value={formData.name}
                  onChange={(e) => handleInputChange('name', e.target.value)}
                  placeholder="Name"
                />
                {errors.name && <div className="error-message">{errors.name}</div>}
              </div>
            </div>
            <div className="col-6">
              <div className="form-group" style={{maxWidth: '550px'}}>
                <label className="form-label">Fullname</label>
                <input
                  type="text"
                  className={`form-control ${errors.fullName ? 'error' : ''}`}
                  value={formData.fullName || ''}
                  onChange={(e) => handleInputChange('fullName', e.target.value)}
                  placeholder="Fullname (max length 918)"
                />
                {errors.fullName && <div className="error-message">{errors.fullName}</div>}
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-4">
              <div className="form-group" style={{maxWidth: '250px'}}>
                <label className="form-label">Coordinate X *</label>
                <input
                  type="number"
                  className={`form-control ${errors.coordinatesX ? 'error' : ''}`}
                  value={formData.coordinates.x}
                  onChange={(e) => handleInputChange('coordinates.x', parseInt(e.target.value))}
                  placeholder="X"
                />
                {errors.coordinatesX && <div className="error-message">{errors.coordinatesX}</div>}
              </div>
            </div>
            <div className="col-4">
              <div className="form-group" style={{maxWidth: '250px'}}>
                <label className="form-label">Coordinate Y *</label>
                <input
                  type="number"
                  step="0.01"
                  className={`form-control ${errors.coordinatesY ? 'error' : ''}`}
                  value={formData.coordinates.y}
                  onChange={(e) => handleInputChange('coordinates.y', parseFloat(e.target.value))}
                  placeholder="Y"
                />
                {errors.coordinatesY && <div className="error-message">{errors.coordinatesY}</div>}
              </div>
            </div>
            <div className="col-4">
              <div className="form-group" style={{maxWidth: '350px'}}>
                <label className="form-label">Type *</label>
                <select
                  className="form-control form-select"
                  value={formData.type}
                  onChange={(e) => handleInputChange('type', e.target.value as 'PUBLIC' | 'TRUST' | 'OPEN_JOINT_STOCK_COMPANY')}
                >
                  <option value="PUBLIC">Public</option>
                  <option value="TRUST">Trust</option>
                  <option value="OPEN_JOINT_STOCK_COMPANY">Open joint stock company</option>
                </select>
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-6">
              <div className="form-group" style={{maxWidth: '250px'}}>
                <label className="form-label">Annual turnover *</label>
                <input
                  type="number"
                  className={`form-control ${errors.annualTurnover ? 'error' : ''}`}
                  value={formData.annualTurnover}
                  onChange={(e) => handleInputChange('annualTurnover', parseInt(e.target.value))}
                  placeholder="Annual turnover"
                />
                {errors.annualTurnover && <div className="error-message">{errors.annualTurnover}</div>}
              </div>
            </div>
            <div className="col-6">
              <div className="form-group" style={{maxWidth: '250px'}}>
                <label className="form-label">Employee number</label>
                <input
                  type="number"
                  className={`form-control ${errors.employeesCount ? 'error' : ''}`}
                  value={formData.employeesCount || ''}
                  onChange={(e) => handleInputChange('employeesCount', e.target.value ? parseInt(e.target.value) : null)}
                  placeholder="Number"
                />
                {errors.employeesCount && <div className="error-message">{errors.employeesCount}</div>}
              </div>
            </div>
          </div>

          <div className="form-group">
            <label className="form-label">
              <input
                type="checkbox"
                checked={!!formData.postalAddress}
                onChange={togglePostalAddress}
                style={{ marginRight: '0.5rem' }}
              />
              Add post address
            </label>
          </div>

          {formData.postalAddress && (
            <div className="row">
              <div className="col-12">
                <h4>Почтовый адрес</h4>
              </div>
              <div className="col-6">
                <div className="form-group" style={{maxWidth: '550px'}}>
                  <label className="form-label">Street *</label>
                  <input
                    type="text"
                    className={`form-control ${errors.postalAddressStreet ? 'error' : ''}`}
                    value={formData.postalAddress.street}
                    onChange={(e) => handleInputChange('postalAddress.street', e.target.value)}
                    placeholder="Street name"
                  />
                  {errors.postalAddressStreet && <div className="error-message">{errors.postalAddressStreet}</div>}
                </div>
              </div>
              <div className="col-6">
                <div className="form-group" style={{maxWidth: '550px'}}>
                  <label className="form-label">Town</label>
                  <input
                    type="text"
                    className="form-control"
                    value={formData.postalAddress.town.name || ''}
                    onChange={(e) => handleInputChange('postalAddress.town.name', e.target.value)}
                    placeholder="Town name"
                  />
                </div>
              </div>
              <div className="col-4">
                <div className="form-group" style={{maxWidth: '250px'}}>
                  <label className="form-label">Town location X</label>
                  <input
                    type="number"
                    step="0.01"
                    className="form-control"
                    value={formData.postalAddress.town.x}
                    onChange={(e) => handleInputChange('postalAddress.town.x', parseFloat(e.target.value))}
                    placeholder="X"
                  />
                </div>
              </div>
              <div className="col-4">
                <div className="form-group" style={{maxWidth: '250px'}}>
                  <label className="form-label">Town location Y</label>
                  <input
                    type="number"
                    step="0.01"
                    className="form-control"
                    value={formData.postalAddress.town.y}
                    onChange={(e) => handleInputChange('postalAddress.town.y', parseFloat(e.target.value))}
                    placeholder="Y"
                  />
                </div>
              </div>
              <div className="col-4">
                <div className="form-group" style={{maxWidth: '250px'}}>
                  <label className="form-label">Town location Z</label>
                  <input
                    type="number"
                    className="form-control"
                    value={formData.postalAddress.town.z}
                    onChange={(e) => handleInputChange('postalAddress.town.z', parseInt(e.target.value))}
                    placeholder="Z"
                  />
                </div>
              </div>
            </div>
          )}

          <div className="row">
            <div className="col-12">
              <div style={{ display: 'flex', gap: '1rem', justifyContent: 'flex-end' }}>
                <button type="button" className="btn btn-secondary" onClick={onCancel}>
                  Cancel
                </button>
                <button type="submit" className="btn btn-success">
                  {organization ? 'Save' : 'Create'}
                </button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default OrganizationForm;

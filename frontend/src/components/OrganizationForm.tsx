import React, { useState } from 'react';
import { Organization, OrganizationRead } from '../store/types.generated';
import * as string_decoder from "node:string_decoder";

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

  // Local state for decimal inputs to preserve intermediate values like "8."
  const [coordXInput, setCoordXInput] = useState(formData.coordinates?.x?.toString() || '');
  const [coordYInput, setCoordYInput] = useState(formData.coordinates?.y?.toString() || '');
  const [townXInput, setTownXInput] = useState(formData.postalAddress?.town?.x?.toString() || '');
  const [townYInput, setTownYInput] = useState(formData.postalAddress?.town?.y?.toString() || '');
  const [townZInput, setTownZInput] = useState(formData.postalAddress?.town?.z?.toString() || '');

  const validateForm = (): boolean => {
    const newErrors: Record<string, string> = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Name is required';
    }

    if (!formData.coordinates.x) {
      newErrors.coordinatesX = 'X coordinate is required';
    }

    if (!formData.coordinates.y) {
      newErrors.coordinatesY = 'Y coordinate is required';
    }

    if (formData.coordinates.x < -365) {
      newErrors.coordinatesX = 'Coordinate X must be greater than -366';
    }

    if (!formData.annualTurnover || formData.annualTurnover < 1) {
      newErrors.annualTurnover = 'Annual turnover is required and must be greater than 0';
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

    if (!formData.postalAddress?.town.z) {
      newErrors.townCoordinatesZ = 'Z coordinate is required';
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
                    type="text"
                    inputMode="numeric"
                    className={`form-control ${errors.coordinatesX ? 'error' : ''}`}
                    placeholder="X"
                    value={coordXInput}
                    maxLength={10}
                    onChange={(e) => {
                      const value = e.target.value.replace(/[^\d-]/g, '');
                      const num = parseInt(value);
                      if (value === '' || value === '-' || (!isNaN(num) && num > -366 && num <= 2147483647)) {
                        setCoordXInput(value);
                        handleInputChange('coordinates.x', (value && value !== '-') ? parseInt(value) : undefined);
                      }
                    }}
                />
                {errors.coordinatesX && <div className="error-message">{errors.coordinatesX}</div>}
              </div>
            </div>
            <div className="col-4">
              <div className="form-group" style={{maxWidth: '250px'}}>
                <label className="form-label">Coordinate Y *</label>
                <input
                    type="text"
                    inputMode="decimal"
                    className={`form-control ${errors.coordinatesY ? 'error' : ''}`}
                    placeholder="Y"
                    value={coordYInput}
                    maxLength={7}
                    onChange={(e) => {
                      let value = e.target.value.replace(/[^\d.-]/g, '');

                      const dotCount = (value.match(/\./g) || []).length;
                      if (dotCount > 1) {
                        const parts = value.split('.');
                        value = parts[0] + '.' + parts.slice(1).join('');
                      }
                      const num = parseFloat(value);
                      if (value === '' || value === '-' || value.endsWith('.') || (!isNaN(num) && num >= -1000000 && num <= 1000000)) {
                        setCoordYInput(value);
                        handleInputChange('coordinates.y', (value && value !== '-' && !value.endsWith('.')) ? parseFloat(value) : undefined);
                      }
                    }}
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
                    type="text"
                    inputMode="numeric"
                    className={`form-control ${errors.annualTurnover ? 'error' : ''}`}
                    value={formData.annualTurnover}
                    maxLength={10}
                    onChange={(e) => {
                      const value = e.target.value.replace(/\D/g, '');
                      const num = parseInt(value);
                      if (value === '' || (!isNaN(num) && num > 0 && num <= 2147483647)) {
                        handleInputChange('annualTurnover', value ? parseInt(value) : null);
                      }
                    }}
                    placeholder="Annual turnover"
                />
                {errors.annualTurnover && <div className="error-message">{errors.annualTurnover}</div>}
              </div>
            </div>
            <div className="col-6">
              <div className="form-group" style={{maxWidth: '250px'}}>
                <label className="form-label">Employee number</label>
                <input
                    type="text"
                    inputMode="numeric"
                    className={`form-control ${errors.employeesCount ? 'error' : ''}`}
                    value={formData.employeesCount || ''}
                    maxLength={10}
                    onChange={(e) => {
                      const value = e.target.value.replace(/\D/g, '');
                      const num = parseInt(value);
                      if (value === '' || (!isNaN(num) && num > 0 && num <= 2147483647)) {
                        handleInputChange('employeesCount', value ? parseInt(value) : null);
                      }
                    }}
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
                      type="text"
                      inputMode="decimal"
                      className="form-control"
                      placeholder="X"
                      value={townXInput}
                      maxLength={7}
                      onChange={(e) => {
                        let value = e.target.value.replace(/[^\d.-]/g, '');

                        const dotCount = (value.match(/\./g) || []).length;
                        if (dotCount > 1) {
                          const parts = value.split('.');
                          value = parts[0] + '.' + parts.slice(1).join('');
                        }
                        const num = parseFloat(value);
                        if (value === '' || value === '-' || value.endsWith('.') || (!isNaN(num) && num >= -1000000 && num <= 1000000)) {
                          setTownXInput(value);
                          handleInputChange('postalAddress.town.x', (value && value !== '-' && !value.endsWith('.')) ? parseFloat(value) : undefined);
                        }
                      }}
                  />
                </div>
              </div>
              <div className="col-4">
                <div className="form-group" style={{maxWidth: '250px'}}>
                  <label className="form-label">Town location Y</label>
                  <input
                      type="text"
                      inputMode="decimal"
                      className="form-control"
                      placeholder="Y"
                      value={townYInput}
                      maxLength={7}
                      onChange={(e) => {
                        let value = e.target.value.replace(/[^\d.-]/g, '');

                        const dotCount = (value.match(/\./g) || []).length;
                        if (dotCount > 1) {
                          const parts = value.split('.');
                          value = parts[0] + '.' + parts.slice(1).join('');
                        }
                        const num = parseFloat(value);
                        if (value === '' || value === '-' || value.endsWith('.') || (!isNaN(num) && num >= -1000000 && num <= 1000000)) {
                          setTownYInput(value);
                          handleInputChange('postalAddress.town.y', (value && value !== '-' && !value.endsWith('.')) ? parseFloat(value) : undefined);
                        }
                      }}
                  />
                </div>
              </div>
              <div className="col-4">
                <div className="form-group" style={{maxWidth: '250px'}}>
                  <label className="form-label">Town location Z *</label>
                  <input
                      type="text"
                      inputMode="numeric"
                      className={`form-control ${errors.townCoordinatesZ ? 'error' : ''}`}
                      placeholder="Z"
                      value={townZInput}
                      maxLength={10}
                      onChange={(e) => {
                        const value = e.target.value.replace(/[^\d-]/g, '');
                        const num = parseInt(value);
                        if (value === '' || value === '-' || (!isNaN(num) && num >= -2147483648 && num <= 2147483647)) {
                          setTownZInput(value);
                          handleInputChange('postalAddress.town.z', (value && value !== '-') ? parseInt(value) : undefined);
                        }
                      }}
                  />
                  {errors.townCoordinatesZ && <div className="error-message">{errors.townCoordinatesZ}</div>}
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

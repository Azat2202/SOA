import React, { useState } from 'react';
import { OrganizationRead, OrganizationFilters } from '../store/types.generated';

interface CompactOrganizationTableProps {
  organizations: OrganizationRead[];
  // loading: boolean;
  totalCount: number;
  currentPage: number;
  pageSize: number;
  onEdit: (organization: OrganizationRead) => void;
  onDelete: (id: number) => void;
  onDeleteByFullname: (fullName: string) => void;
  onFiltersChange: (filters: OrganizationFilters['filter']) => void;
  onSortingChange: (sorting: OrganizationFilters['sort']) => void;
  onPageChange: (page: number) => void;
  onPageSizeChange: (size: number) => void;
  onFilterByTurnover: (minTurnover: number, maxTurnover: number) => void;
  onFilterByType: (type: 'PUBLIC' | 'TRUST' | 'OPEN_JOINT_STOCK_COMPANY') => void;
  onGetEmployeesStats: () => void;
  onGetTurnoverStats: () => void;
  employeesStats?: { count?: number };
  turnoverStats?: { count?: number };
}

const CompactOrganizationTable: React.FC<CompactOrganizationTableProps> = ({
  organizations,
  // loading,
  totalCount,
  currentPage,
  pageSize,
  onEdit,
  onDelete,
  onDeleteByFullname,
  onFiltersChange,
  onSortingChange,
  onPageChange,
  onPageSizeChange,
  onFilterByTurnover,
  onFilterByType,
  onGetEmployeesStats,
  onGetTurnoverStats,
  employeesStats,
  turnoverStats,
}) => {
  const [filters, setFilters] = useState<OrganizationFilters['filter']>({});
  const [sorting, setSorting] = useState<OrganizationFilters['sort']>([]);
  const [showAdvancedFunctions, setShowAdvancedFunctions] = useState(false);
  const [deleteFullname, setDeleteFullname] = useState('');
  const [turnoverRange, setTurnoverRange] = useState({ min: '', max: '' });
  const [filterType, setFilterType] = useState('');

  const formatDate = (dateString?: string) => {
    if (!dateString) return 'Дата не указана';
    return new Date(dateString).toLocaleDateString('ru-RU');
  };

  const formatType = (type: string) => {
    const typeMap: Record<string, string> = {
      'PUBLIC': 'Public',
      'TRUST': 'Trust',
      'OPEN_JOINT_STOCK_COMPANY': 'OJS',
    };
    return typeMap[type] || type;
  };

  const getTypeBadgeClass = (type: string) => {
    const badgeMap: Record<string, string> = {
      'PUBLIC': 'badge-primary',
      'TRUST': 'badge-success',
      'OPEN_JOINT_STOCK_COMPANY': 'badge-warning',
    };
    return badgeMap[type] || 'badge-info';
  };

  const formatNumber = (num: number) => {
    return new Intl.NumberFormat('ru-RU').format(num);
  };

  const handleFilterChange = (field: string, value: any) => {
    const newFilters = { ...filters };
    
    if (field.includes('.')) {
      const [parent, child] = field.split('.');
      if (parent === 'coordinates') {
        newFilters.coordinates = { ...newFilters.coordinates, [child]: value };
      } else if (parent === 'creationDate') {
        newFilters.creationDate = { ...newFilters.creationDate, [child]: value };
      } else if (parent === 'postalAddress') {
        if (child === 'street') {
          newFilters.postalAddress = { ...newFilters.postalAddress, street: value };
        } else if (child.startsWith('town.')) {
          const townField = child.split('.')[1];
          newFilters.postalAddress = {
            ...newFilters.postalAddress,
            town: { ...newFilters.postalAddress?.town, [townField]: value }
          };
        }
      }
    } else {
      (newFilters as any)[field] = value;
    }
    
    setFilters(newFilters);
    onFiltersChange(newFilters);
  };

  const handleSortChange = (field: string) => {
    const currentSorting = sorting || [];
    const existingSort = currentSorting.find(s => s.field === field);
    let newSorting: OrganizationFilters['sort'] = [];

    if (existingSort) {
      // Переключаем направление сортировки
      if (existingSort.direction === 'ASC') {
        newSorting = currentSorting.map(s => 
          s.field === field ? { ...s, direction: 'DESC' } : s
        );
      } else {
        // Убираем сортировку
        newSorting = currentSorting.filter(s => s.field !== field);
      }
    } else {
      // Добавляем новую сортировку
      newSorting = [...currentSorting, { field: field as any, direction: 'ASC' }];
    }

    setSorting(newSorting);
    onSortingChange(newSorting);
  };

  const getSortIcon = (field: string) => {
    const currentSorting = sorting || [];
    const sort = currentSorting.find(s => s.field === field);
    if (!sort) return '↕';
    return sort.direction === 'ASC' ? '↑' : '↓';
  };

  const clearFilters = () => {
    setFilters({});
    setSorting([]);
    onFiltersChange({});
    onSortingChange([]);
  };

  const handleAdvancedFunction = (action: string) => {
    switch (action) {
      case 'deleteByFullname':
        if (deleteFullname.trim()) {
          onDeleteByFullname(deleteFullname.trim());
          setDeleteFullname('');
        }
        break;
      case 'filterByTurnover':
        const min = parseFloat(turnoverRange.min);
        const max = parseFloat(turnoverRange.max);
        if (!isNaN(min) && !isNaN(max)) {
          onFilterByTurnover(min, max);
        }
        break;
      case 'filterByType':
        if (filterType) {
          onFilterByType(filterType as any);
        }
        break;
      case 'getEmployeesStats':
        onGetEmployeesStats();
        break;
      case 'getTurnoverStats':
        onGetTurnoverStats();
        break;
    }
  };

  const totalPages = Math.ceil(totalCount / pageSize);

  return (
    <div className="card">
      <div className="card-header">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h3>Organizations ({totalCount})</h3>
          <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
            <button
              className="btn btn-secondary"
              onClick={clearFilters}
              style={{ fontSize: '0.875rem' }}
            >
              Clear all
            </button>
          </div>
        </div>
      </div>
      
      <div className="card-body">
         {/* Advanced Functions Button */}
         <div style={{ marginBottom: '1rem', textAlign: 'right' }}>
           <button
             className="btn btn-outline-primary"
             onClick={() => setShowAdvancedFunctions(!showAdvancedFunctions)}
             style={{ fontSize: '0.875rem' }}
           >
             {showAdvancedFunctions ? 'Hide' : 'Show'} Advanced Functions
           </button>
         </div>

        {/*/!* Расширенные фильтры *!/*/}
        {/*{showAdvancedFilters && (*/}
        {/*  <div style={{ marginBottom: '1rem', padding: '1rem', background: '#e9ecef', borderRadius: '8px' }}>*/}
        {/*    <h4 style={{ margin: '0 0 1rem 0', color: '#2c3e50' }}>Расширенные фильтры</h4>*/}
        {/*    <div className="row">*/}
        {/*      <div className="col-3">*/}
        {/*        <label className="form-label">Дата создания (от)</label>*/}
        {/*        <input*/}
        {/*          type="date"*/}
        {/*          className="form-control"*/}
        {/*          value={filters?.creationDate?.min || ''}*/}
        {/*          onChange={(e) => handleFilterChange('creationDate.min', e.target.value || undefined)}*/}
        {/*        />*/}
        {/*      </div>*/}
        {/*      <div className="col-3">*/}
        {/*        <label className="form-label">Дата создания (до)</label>*/}
        {/*        <input*/}
        {/*          type="date"*/}
        {/*          className="form-control"*/}
        {/*          value={filters?.creationDate?.max || ''}*/}
        {/*          onChange={(e) => handleFilterChange('creationDate.max', e.target.value || undefined)}*/}
        {/*        />*/}
        {/*      </div>*/}
        {/*      <div className="col-3">*/}
        {/*        <label className="form-label">Координата X (мин)</label>*/}
        {/*        <input*/}
        {/*          type="number"*/}
        {/*          className="form-control"*/}
        {/*          value={filters?.coordinates?.x || ''}*/}
        {/*          onChange={(e) => handleFilterChange('coordinates.x', e.target.value ? parseInt(e.target.value) : undefined)}*/}
        {/*        />*/}
        {/*      </div>*/}
        {/*      <div className="col-3">*/}
        {/*        <label className="form-label">Координата Y (мин)</label>*/}
        {/*        <input*/}
        {/*          type="number"*/}
        {/*          step="0.01"*/}
        {/*          className="form-control"*/}
        {/*          value={filters?.coordinates?.y || ''}*/}
        {/*          onChange={(e) => handleFilterChange('coordinates.y', e.target.value ? parseFloat(e.target.value) : undefined)}*/}
        {/*        />*/}
        {/*      </div>*/}
        {/*    </div>*/}
        {/*  </div>*/}
        {/*)}*/}

        {showAdvancedFunctions && (
         <div style={{ 
           marginTop: '1rem', 
           padding: '1.5rem', 
           background: '#f8f9fa', 
           borderRadius: '8px',
           border: '1px solid #dee2e6'
         }}>
           <h4 style={{ margin: '0 0 1.5rem 0', color: '#2c3e50' }}>Advanced Functions</h4>
           
           <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
             {/* Delete by Full Name */}
             <div>
               <h5 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Delete by Full Name</h5>
               <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                 <input
                   type="text"
                   className="form-control"
                   placeholder="Enter full name"
                   value={deleteFullname}
                   onChange={(e) => setDeleteFullname(e.target.value)}
                   style={{ flex: 1 }}
                 />
                 <button
                   className="btn btn-danger"
                   onClick={() => handleAdvancedFunction('deleteByFullname')}
                   disabled={!deleteFullname.trim()}
                 >
                   Delete
                 </button>
               </div>
             </div>

             {/* Filter by Type */}
             <div>
               <h5 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Filter by Type</h5>
               <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                 <button
                   className="btn btn-outline"
                   onClick={() => onFilterByType('PUBLIC')}
                 >
                   Public
                 </button>
                 <button
                   className="btn btn-outline"
                   onClick={() => onFilterByType('TRUST')}
                 >
                   Trust
                 </button>
                 <button
                   className="btn btn-outline"
                   onClick={() => onFilterByType('OPEN_JOINT_STOCK_COMPANY')}
                 >
                   OJS
                 </button>
               </div>
             </div>

             {/* Filter by Turnover Range */}
             <div>
               <h5 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Filter by Turnover Range</h5>
               <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                 <input
                   type="number"
                   className="form-control"
                   placeholder="Min turnover"
                   value={turnoverRange.min}
                   onChange={(e) => setTurnoverRange({...turnoverRange, min: e.target.value})}
                   style={{ width: '150px' }}
                 />
                 <span>to</span>
                 <input
                   type="number"
                   className="form-control"
                   placeholder="Max turnover"
                   value={turnoverRange.max}
                   onChange={(e) => setTurnoverRange({...turnoverRange, max: e.target.value})}
                   style={{ width: '150px' }}
                 />
                 <button
                   className="btn btn-primary"
                   onClick={() => handleAdvancedFunction('filterByTurnover')}
                   disabled={!turnoverRange.min || !turnoverRange.max}
                 >
                   Apply Filter
                 </button>
               </div>
             </div>

             {/* Statistics */}
             <div>
               <h5 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Statistics</h5>
               <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
                 <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                   <button
                     className="btn btn-success"
                     onClick={() => handleAdvancedFunction('getEmployeesStats')}
                   >
                     Get Employees Stats
                   </button>
                   <span style={{ 
                     fontSize: '0.875rem', 
                     fontWeight: 'bold', 
                     color: '#28a745',
                     padding: '0.25rem 0.5rem',
                     backgroundColor: '#e8f5e8',
                     borderRadius: '4px'
                   }}>
                     {employeesStats?.count !== undefined ? formatNumber(employeesStats.count) : '—'} organizations
                   </span>
                 </div>
                 
                 <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                   <button
                     className="btn btn-info"
                     onClick={() => handleAdvancedFunction('getTurnoverStats')}
                   >
                     Get Turnover Stats
                   </button>
                   <span style={{ 
                     fontSize: '0.875rem', 
                     fontWeight: 'bold', 
                     color: '#17a2b8',
                     padding: '0.25rem 0.5rem',
                     backgroundColor: '#e0f2f1',
                     borderRadius: '4px'
                   }}>
                     {turnoverStats?.count !== undefined ? formatNumber(turnoverStats.count) : '—'} organizations
                   </span>
                 </div>
               </div>
             </div>
           </div>
         </div>
       )}

        {/* Таблица */}
        <div className="table-container">
          <table className="table">
            <thead>
              <tr>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    ID
                    <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('ID')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('ID')}
                    </button>
                  </div>
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Name
                    <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('NAME')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('NAME')}
                    </button>
                  </div>
                  <input
                    type="text"
                    className="form-control"
                    value={filters?.name || ''}
                    onChange={(e) => handleFilterChange('name', e.target.value || null)}
                    placeholder="Name"
                    style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Fullname
                    <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('FULL_NAME')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('FULL_NAME')}
                    </button>
                  </div>
                  <input
                    type="text"
                    className="form-control"
                    value={filters?.fullName || ''}
                    onChange={(e) => handleFilterChange('fullName', e.target.value)}
                    placeholder="Fullname"
                    style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Type
                    <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('TYPE')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('TYPE')}
                    </button>
                  </div>
                  <select
                    className="form-control form-select"
                    value={filters?.type || ''}
                    onChange={(e) => handleFilterChange('type', e.target.value || undefined)}
                    style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  >
                    <option value="">All</option>
                    <option value="PUBLIC">Public</option>
                    <option value="TRUST">Trust</option>
                    <option value="OPEN_JOINT_STOCK_COMPANY">OJS</option>
                  </select>
                </th>
                <th>
                  <div style={{ display: 'table', alignItems: 'center', gap: '0.5rem' }}>
                    Annual turnover
                    <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('ANNUAL_TURNOVER')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('ANNUAL_TURNOVER')}
                    </button>
                  </div>
                   <div>
                   <input
                       type="number"
                       className="form-control"
                       placeholder="Min turnover"
                       onChange={(e) => handleFilterChange('annualTurnover.min', e.target.value ? parseInt(e.target.value) : undefined)}
                       style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                   <input
                       type="number"
                       className="form-control"
                       placeholder="Max turnover"
                       onChange={(e) => handleFilterChange('annualTurnover.max', e.target.value ? parseInt(e.target.value) : undefined)}
                       style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                   </div>
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Employees number
                    <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('EMPLOYEES_COUNT')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('EMPLOYEES_COUNT')}
                    </button>
                  </div>
                  <input
                    type="number"
                    className="form-control"
                    onChange={(e) => handleFilterChange('employeesCount', e.target.value ? parseInt(e.target.value) : undefined)}
                    placeholder="Number"
                    style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Creation date
                    <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('CREATION_DATE')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('CREATION_DATE')}
                    </button>

                  </div>
                  <div>
                    <input
                        type="date"
                        className="form-control"
                        value={filters?.creationDate?.min || ''}
                        onChange={(e) => handleFilterChange('creationDate.min', e.target.value || undefined)}
                    />
                    <input
                        type="date"
                        className="form-control"
                        value={filters?.creationDate?.max || ''}
                        onChange={(e) => handleFilterChange('creationDate.max', e.target.value || undefined)}
                    />
                  </div>
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Coord X
                    <button
                        className="btn btn-outline"
                        onClick={() => handleSortChange('COORDINATES_X')}
                        style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('COORDINATES_X')}
                    </button>
                  </div>
                  <input
                      type="number"
                      className="form-control"
                      value={filters?.coordinates?.x || ''}
                      placeholder="X"
                      onChange={(e) => handleFilterChange('coordinates.x', e.target.value ? parseInt(e.target.value) : undefined)}
                  />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Coord Y
                    <button
                        className="btn btn-outline"
                        onClick={() => handleSortChange('COORDINATES_Y')}
                        style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('COORDINATES_Y')}
                    </button>
                  </div>
                  <input
                      type="number"
                      className="form-control"
                      value={filters?.coordinates?.y || ''}
                      placeholder="Y"
                      onChange={(e) => handleFilterChange('coordinates.y', e.target.value ? parseInt(e.target.value) : undefined)}
                  />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                  Street
                  <button
                      className="btn btn-outline"
                      onClick={() => handleSortChange('ADDRESS_STREET')}
                      style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                  >
                    {getSortIcon('ADDRESS_STREET')}
                  </button>
                </div>
                   <input
                     type="text"
                     className="form-control"
                     value={filters?.postalAddress?.street || ''}
                     onChange={(e) => handleFilterChange('postalAddress.street', e.target.value || undefined)}
                     placeholder="Street"
                     style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Town
                    <button
                        className="btn btn-outline"
                        onClick={() => handleSortChange('ADDRESS_TOWN_NAME')}
                        style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('ADDRESS_TOWN_NAME')}
                    </button>
                  </div>
                   <input
                     type="text"
                     className="form-control"
                     value={filters?.postalAddress?.town?.name || ''}
                     onChange={(e) => handleFilterChange('postalAddress.town.name', e.target.value || undefined)}
                     placeholder="Town"
                     style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    X
                    <button
                        className="btn btn-outline"
                        onClick={() => handleSortChange('ADDRESS_TOWN_X')}
                        style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('ADDRESS_TOWN_X')}
                    </button>
                  </div>
                   <input
                     type="number"
                     className="form-control"
                     value={filters?.postalAddress?.town?.x || ''}
                     placeholder="X"
                     onChange={(e) => handleFilterChange('postalAddress.town.x', e.target.value ? parseInt(e.target.value) : undefined)}
                     style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Y
                    <button
                        className="btn btn-outline"
                        onClick={() => handleSortChange('ADDRESS_TOWN_Y')}
                        style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('ADDRESS_TOWN_Y')}
                    </button>
                  </div>
                   <input
                     type="number"
                     className="form-control"
                     value={filters?.postalAddress?.town?.y || ''}
                     placeholder="Y"
                     onChange={(e) => handleFilterChange('postalAddress.town.y', e.target.value ? parseInt(e.target.value) : undefined)}
                     style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                </th>
                <th>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                    Z
                    <button
                        className="btn btn-outline"
                        onClick={() => handleSortChange('ADDRESS_TOWN_Z')}
                        style={{ padding: '0.25rem', fontSize: '0.75rem' }}
                    >
                      {getSortIcon('ADDRESS_TOWN_Z')}
                    </button>
                  </div>
                   <input
                     type="number"
                     className="form-control"
                     value={filters?.postalAddress?.town?.z || ''}
                     placeholder="Z"
                     onChange={(e) => handleFilterChange('postalAddress.town.z', e.target.value ? parseInt(e.target.value) : undefined)}
                     style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                </th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {organizations.map((org) => (
                <tr key={org.id}>
                  <td>
                    <strong>{org.id}</strong>
                  </td>
                  <td>
                    <div>
                      <strong>{org.name}</strong>
                    </div>
                  </td>
                  <td>
                    <div style={{ maxWidth: '200px', wordWrap: 'break-word' }}>
                      {org.fullName || (
                        <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                          Не указано
                        </span>
                      )}
                    </div>
                  </td>
                  <td>
                    <span className={`badge ${getTypeBadgeClass(org.type)}`}>
                      {formatType(org.type)}
                    </span>
                  </td>
                  <td>
                    <div>
                      <strong>{formatNumber(org.annualTurnover)}</strong>
                    </div>
                  </td>
                  <td>
                    {org.employeesCount ? (
                      <span>{formatNumber(org.employeesCount)}</span>
                    ) : (
                      <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                        -
                      </span>
                    )}
                  </td>
                  <td>
                    <div>
                      {formatDate(org.creationDate)}
                    </div>
                  </td>
                  <td>
                    <div style={{ fontSize: '0.9rem' }}>
                      <div>{org.coordinates.x}</div>
                    </div>
                  </td>
                  <td>
                    <div style={{ fontSize: '0.9rem' }}>
                      <div>{org.coordinates.y}</div>
                    </div>
                  </td>
                  <td>
                    {org.postalAddress ? (
                      <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                        <div>{org.postalAddress.street}</div>
                      </div>
                    ) : (
                      <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                        -
                      </span>
                    )}
                  </td>
                  <td>
                    {org.postalAddress ? (
                        <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                          {org.postalAddress.town.name && (
                              <div>{org.postalAddress.town.name}</div>
                          )}
                        </div>
                    ) : (
                        <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                        -
                      </span>
                    )}
                  </td>
                  <td>
                    {org.postalAddress ? (
                        <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                          <div>{org.postalAddress.town.x}</div>
                        </div>
                    ) : (
                        <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                        -
                      </span>
                    )}
                  </td>
                  <td>
                    {org.postalAddress ? (
                        <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                          <div>{org.postalAddress.town.y}</div>
                        </div>
                    ) : (
                        <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                        -
                      </span>
                    )}
                  </td>
                  <td>
                    {org.postalAddress ? (
                        <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                          <div>{org.postalAddress.town.z}</div>
                        </div>
                    ) : (
                        <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                        -
                      </span>
                    )}
                  </td>
                  <td>
                    <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                      <button
                        className="btn btn-outline"
                        onClick={() => onEdit(org)}
                        style={{ padding: '0.5rem', fontSize: '0.875rem' }}
                      >
                        ✏️
                      </button>
                      <button
                        className="btn btn-danger"
                        onClick={() => onDelete(org.id!)}
                        style={{ padding: '0.5rem', fontSize: '0.875rem' }}
                      >
                        🗑️
                      </button>
                      {/*todo move to functions*/}
                      {/*{org.fullName && (*/}
                      {/*  <button*/}
                      {/*    className="btn btn-secondary"*/}
                      {/*    onClick={() => onDeleteByFullname(org.fullName!)}*/}
                      {/*    style={{ padding: '0.5rem', fontSize: '0.875rem' }}*/}
                      {/*    title={`Удалить по полному имени: ${org.fullName}`}*/}
                      {/*  >*/}
                      {/*    🗑️*/}
                      {/*  </button>*/}
                      {/*)}*/}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Пагинация */}
        <div style={{ marginTop: '1rem' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
            <div>
              <strong>
                {formatNumber(currentPage * pageSize + 1)}-{formatNumber(Math.min((currentPage + 1) * pageSize, totalCount))} out of {formatNumber(totalCount)} orgs
              </strong>
            </div>
            
            <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
              <div>
                <label style={{ marginRight: '0.5rem' }}>Orgs by page:</label>
                <select
                  className="form-control form-select"
                  value={pageSize}
                  onChange={(e) => onPageSizeChange(parseInt(e.target.value))}
                  style={{ width: 'auto', display: 'inline-block' }}
                >
                  <option value={10}>10</option>
                  <option value={20}>20</option>
                  <option value={50}>50</option>
                  <option value={100}>100</option>
                </select>
              </div>
              
              <div className="pagination">
                <button
                  onClick={() => onPageChange(0)}
                  disabled={currentPage === 0}
                  title="First page"
                >
                  &lt;&lt;
                  {/*⏮️*/}
                </button>
                
                <button
                  onClick={() => onPageChange(currentPage - 1)}
                  disabled={currentPage === 0}
                  title="Previous page"
                >
                  {/*◀️*/}
                  &lt;
                </button>
                
                <span style={{ padding: '0.5rem 1rem', color: '#7f8c8d' }}>
                  Page {currentPage + 1} out of {totalPages}
                </span>
                
                <button
                  onClick={() => onPageChange(currentPage + 1)}
                  disabled={currentPage === totalPages - 1}
                  title="Next page"
                >
                  {/*▶️*/}
                  &gt;
                </button>
                
                <button
                  onClick={() => onPageChange(totalPages - 1)}
                  disabled={currentPage === totalPages - 1}
                  title="Last page"
                >
                  {/*⏭️*/}
                  &gt;&gt;
                </button>
              </div>
            </div>
          </div>
         </div>
       </div>

     </div>
   );
 };
 
 export default CompactOrganizationTable;

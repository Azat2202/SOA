import React, {useState} from 'react';
import {OrganizationRead, OrganizationFilters} from '../store/types.generated';

interface CompactOrganizationTableProps {
  organizations: OrganizationRead[];
  totalCount: number;
  currentPage: number;
  pageSize: number;
  empStats: number;
  turnoverStats: number;
  orgSearch: OrganizationRead | null;
  onEdit: (organization: OrganizationRead) => void;
  onDelete: (id: number) => void;
  onDeleteByFullname: (fullName: string) => void;
  onFiltersChange: (filters: OrganizationFilters['filter']) => void;
  onSortingChange: (sorting: OrganizationFilters['sort']) => void;
  onPageChange: (page: number) => void;
  onPageSizeChange: (size: number) => void;
  onFilterByTurnover: (minTurnover: number, maxTurnover: number) => void;
  onFilterByType: (type: 'PUBLIC' | 'TRUST' | 'OPEN_JOINT_STOCK_COMPANY' | '') => void;
  onGetEmployeesStats: (quantity: number) => void;
  onGetTurnoverStats: (maxTurnover: number) => void;
  onGetOrganizationById: (id: number) => void;
}

const CompactOrganizationTable: React.FC<CompactOrganizationTableProps> = ({
  organizations,
  totalCount,
  currentPage,
  pageSize,
  empStats,
  turnoverStats,
  orgSearch,
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
  onGetOrganizationById,
}) => {
  const [filters, setFilters] = useState<OrganizationFilters['filter']>({});
  const [sorting, setSorting] = useState<OrganizationFilters['sort']>([]);
  const [showAdvancedFunctions, setShowAdvancedFunctions] = useState(false);
  const [deleteFullname, setDeleteFullname] = useState('');
  const [turnoverRange, setTurnoverRange] = useState({ min: '', max: '' });
  const [filterType, setFilterType] = useState('');
  const [employeesQuantity, setEmployeesQuantity] = useState('');
  const [maxTurnover, setMaxTurnover] = useState('');
  const [organizationId, setOrganizationId] = useState('');
  const [showEmpStats, setShowEmpStats] = useState(false);
  const [showTurnoverStats, setShowTurnoverStats] = useState(false);
  const [showSearchStats, setShowSearchStats] = useState(false);
  
  // Local state for decimal inputs to preserve intermediate values like "8."
  const [coordXInput, setCoordXInput] = useState('');
  const [coordYInput, setCoordYInput] = useState('');
  const [townXInput, setTownXInput] = useState('');
  const [townYInput, setTownYInput] = useState('');
  const [townZInput, setTownZInput] = useState('');


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
      const [parent, child, little] = field.split('.');
      if (parent === 'coordinates') {
        newFilters.coordinates = { ...newFilters.coordinates, [child]: value };
      } else if (parent === 'creationDate') {
        newFilters.creationDate = { ...newFilters.creationDate, [child]: value };
      } else if (parent === 'postalAddress') {
        if (child === 'street') {
          newFilters.postalAddress = {...newFilters.postalAddress, street: value};
        } else if (child ==='town') {
          newFilters.postalAddress = {...newFilters.postalAddress, town: {
              ...newFilters.postalAddress?.town, [little]: value
            }};
        }
      } else if (parent === 'annualTurnover') {
        newFilters.annualTurnover = {...newFilters.annualTurnover, [child]: value}
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
    setCoordXInput('');
    setCoordYInput('');
    setTownXInput('');
    setTownYInput('');
    setTownZInput('');
    onFiltersChange({});
    onSortingChange([]);
  };

  const clearMainTableFilters = () => {
    setFilters({});
    setSorting([]);
    setCoordXInput('');
    setCoordYInput('');
    setTownXInput('');
    setTownYInput('');
    setTownZInput('');
    onFiltersChange({});
  };

  const handleDeleteByFullname = ()  => {
      if (deleteFullname.trim()) {
        onDeleteByFullname(deleteFullname.trim());
        setDeleteFullname('');
      }
  };

  const handleFilterByTurnover = ()  => {
    const min = parseFloat(turnoverRange.min);
    const max = parseFloat(turnoverRange.max);
    if (!isNaN(min) && !isNaN(max)) {
      clearMainTableFilters(); // Очищаем фильтры основной таблицы
      onFilterByTurnover(min, max);
    }
  };

  const handleGetEmployeesStats = ()  => {
    const quantity = parseInt(employeesQuantity);
    if (!isNaN(quantity)) {
      onGetEmployeesStats(quantity);
      setShowEmpStats(true);
    }
  };

  const handleGetTurnoverStats = ()  => {
    const turnover = parseInt(maxTurnover);
    if (!isNaN(turnover)) {
      onGetTurnoverStats(turnover);
      setShowTurnoverStats(true);
    }
  };

  const handleGetOrganizationById = ()  => {
    const id = parseInt(organizationId);
    if (!isNaN(id)) {
      onGetOrganizationById(id);
      setShowSearchStats(true);
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
              Clear filters
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

        {showAdvancedFunctions && (
         <div style={{
           marginTop: '1rem',
           marginBottom: '2rem',
           padding: '1.5rem',
           background: '#f8f9fa',
           borderRadius: '8px',
           border: '1px solid #dee2e6'
         }}>
           <h3 style={{ margin: '0 0 1.5rem 0', color: '#2c3e50' }}>Advanced Functions</h3>

           <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
             {/* Delete by Full Name */}
             <div>
               <h4 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Delete by Full Name</h4>
               <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                 <input
                   type="text"
                   className="form-control"
                   placeholder="Enter full name"
                   value={deleteFullname}
                   maxLength={254}
                   onChange={(e) => setDeleteFullname(e.target.value)}
                   style={{ flex: 1 }}
                 />
                 <button
                   className="btn btn-danger"
                   onClick={() => handleDeleteByFullname()}
                   disabled={!deleteFullname.trim()}
                 >
                   Delete
                 </button>
               </div>
             </div>

             {/* Filter by Type */}
             <div>
               <h4 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Filter by Type</h4>
               <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap', alignItems: 'center' }}>
                 <button
                   className={`btn ${filterType === 'PUBLIC' ? 'btn-primary' : 'btn-outline'}`}
                   onClick={() => {
                     if (filterType === 'PUBLIC') {
                       setFilterType('');
                       onFilterByType('' as any);
                     } else {
                       clearMainTableFilters(); // Очищаем фильтры основной таблицы
                       setFilterType('PUBLIC');
                       onFilterByType('PUBLIC');
                     }
                   }}
                 >
                   Public
                 </button>
                 <button
                   className={`btn ${filterType === 'TRUST' ? 'btn-primary' : 'btn-outline'}`}
                   onClick={() => {
                     if (filterType === 'TRUST') {
                       setFilterType('');
                       onFilterByType('' as any);
                     } else {
                       clearMainTableFilters(); // Очищаем фильтры основной таблицы
                       setFilterType('TRUST');
                       onFilterByType('TRUST');
                     }
                   }}
                 >
                   Trust
                 </button>
                 <button
                   className={`btn ${filterType === 'OPEN_JOINT_STOCK_COMPANY' ? 'btn-primary' : 'btn-outline'}`}
                   onClick={() => {
                     if (filterType === 'OPEN_JOINT_STOCK_COMPANY') {
                       setFilterType('');
                       onFilterByType('' as any);
                     } else {
                       clearMainTableFilters(); // Очищаем фильтры основной таблицы
                       setFilterType('OPEN_JOINT_STOCK_COMPANY');
                       onFilterByType('OPEN_JOINT_STOCK_COMPANY');
                     }
                   }}
                 >
                   OJS
                 </button>
                 {filterType && (
                   <button
                     className="btn btn-secondary"
                     onClick={() => {
                       setFilterType('');
                       onFilterByType('' as any);
                     }}
                   >
                     Clear
                   </button>
                 )}
               </div>
             </div>

             {/* Filter by Turnover Range */}
             <div>
               <h4 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Filter by Turnover Range</h4>
               <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                 <input
                   type="text"
                   inputMode="numeric"
                   className="form-control"
                   placeholder="Min turnover"
                   value={turnoverRange.min}
                   maxLength={254}
                   onChange={(e) => {
                     const value = e.target.value.replace(/[^\d.-]/g, '');
                     const num = parseFloat(value);
                     if (value === '' || value === '-' || (!isNaN(num) && num >= 0)) {
                       setTurnoverRange({...turnoverRange, min: value});
                     }
                   }}
                   style={{ width: '150px' }}
                 />
                 <span>to</span>
                 <input
                   type="text"
                   inputMode="numeric"
                   className="form-control"
                   placeholder="Max turnover"
                   value={turnoverRange.max}
                   maxLength={254}
                   onChange={(e) => {
                     const value = e.target.value.replace(/[^\d.-]/g, '');
                     const num = parseFloat(value);
                     if (value === '' || value === '-' || (!isNaN(num) && num >= 0)) {
                       setTurnoverRange({...turnoverRange, max: value});
                     }
                   }}
                   style={{ width: '150px' }}
                 />
                 <button
                   className="btn btn-success"
                   onClick={() => handleFilterByTurnover()}
                   disabled={!turnoverRange.min || !turnoverRange.max}
                 >
                   Apply Filter
                 </button>
                 {turnoverRange.min && turnoverRange.max && (
                     <button
                         className="btn btn-secondary"
                         onClick={() => {
                           setTurnoverRange({...turnoverRange, min: '', max: ''});
                           onFilterByType('' as any);
                         }}
                     >
                       Clear
                     </button>
                 )}
               </div>
             </div>

             {/* Organization Count by Employees */}
             <div>
               <h4 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Organization Count by Employees</h4>
               <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center', flexWrap: 'wrap' }}>
                 <input
                   type="text"
                   inputMode="numeric"
                   className="form-control"
                   placeholder="Enter employees quantity"
                   value={employeesQuantity}
                   maxLength={254}
                   onChange={(e) => {
                     const value = e.target.value.replace(/\D/g, '');
                     const num = parseInt(value);
                     if (value === '' || (!isNaN(num) && num >= 0 && num <= 2147483647)) {
                       setEmployeesQuantity(value);
                     }
                   }}
                   style={{ width: '200px' }}
                 />
                 <button
                   className="btn btn-success"
                   onClick={() => handleGetEmployeesStats()}
                   disabled={!employeesQuantity.trim()}
                 >
                   Get Count
                 </button>
                 {showEmpStats && (
                     <div>
                       <span className="stats">{empStats}</span>
                       <button
                           className="btn btn-secondary"
                           onClick={() => {
                             setEmployeesQuantity('');
                             setShowEmpStats(false);
                           }}
                       >
                         Clear
                       </button>
                     </div>
                 )}
               </div>
             </div>

             {/* Organization Count by Turnover */}
             <div>
               <h4 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Organization Count by Max Turnover</h4>
               <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center', flexWrap: 'wrap' }}>
                 <input
                   type="text"
                   inputMode="numeric"
                   className="form-control"
                   placeholder="Enter max turnover"
                   value={maxTurnover}
                   maxLength={254}
                   onChange={(e) => {
                     const value = e.target.value.replace(/\D/g, '');
                     const num = parseInt(value);
                     if (value === '' || (!isNaN(num) && num >= 0 && num <= 2147483647)) {
                       setMaxTurnover(value);
                     }
                   }}
                   style={{ width: '200px' }}
                 />
                 <button
                   className="btn btn-success"
                   onClick={() => handleGetTurnoverStats()}
                   disabled={!maxTurnover.trim()}
                 >
                   Get Count
                 </button>
                 {showTurnoverStats && (
                     <div>
                       <span className="stats">{turnoverStats}</span>
                       <button
                           className="btn btn-secondary"
                           onClick={() => {
                             setMaxTurnover('');
                             setShowTurnoverStats(false);
                           }}
                       >
                         Clear
                       </button>
                     </div>
                 )}
               </div>
             </div>

             {/* Get Organization by ID */}
             <div>
               <h4 style={{ margin: '0 0 0.5rem 0', color: '#495057' }}>Get Organization by ID</h4>
               <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                 <input
                   type="text"
                   inputMode="numeric"
                   className="form-control"
                   placeholder="Enter organization ID"
                   value={organizationId}
                   maxLength={254}
                   onChange={(e) => {
                     const value = e.target.value.replace(/\D/g, '');
                     const num = parseInt(value);
                     if (value === '' || (!isNaN(num) && num >= 0 && num <= 2147483647)) {
                       setOrganizationId(value);
                     }
                   }}
                   style={{ width: '200px' }}
                 />
                 <button
                   className="btn btn-success"
                   onClick={() => handleGetOrganizationById()}
                   disabled={!organizationId.trim()}
                 >
                   Get by ID
                 </button>
                 {showSearchStats && (
                     <div>
                       <button
                           className="btn btn-secondary"
                           onClick={() => {
                             setOrganizationId('');
                             setShowSearchStats(false);
                           }}
                       >
                         Clear
                       </button>
                     </div>
                 )}
               </div>
             <div>
               {orgSearch !== null && showSearchStats && (
             <div className="table-container">
               <table className="table">
                 <thead>
                 <tr>
                   <th style={{ width: '60px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       ID
                     </div>
                   </th>
                   <th style={{ width: '120px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Name
                     </div>
                   </th>
                   <th style={{ width: '150px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Fullname
                     </div>
                   </th>
                   <th style={{ width: '100px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Type
                     </div>
                   </th>
                   <th style={{ width: '140px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Annual turnover
                     </div>
                   </th>
                   <th style={{ width: '100px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Employees number
                     </div>
                   </th>
                   <th style={{ width: '120px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Creation date
                     </div>
                   </th>
                   <th style={{ width: '80px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Coord X
                     </div>
                   </th>
                   <th style={{ width: '80px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Coord Y
                     </div>
                   </th>
                   <th style={{ width: '120px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Street
                     </div>
                   </th>
                   <th style={{ width: '100px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Town
                     </div>
                   </th>
                   <th style={{ width: '60px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       X
                     </div>
                   </th>
                   <th style={{ width: '60px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Y
                     </div>
                   </th>
                   <th style={{ width: '60px' }}>
                     <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                       Z
                     </div>
                   </th>
                 </tr>
                 </thead>
                 <tbody>
                     <tr key={orgSearch.id}>
                       <td>
                         <strong>{orgSearch.id}</strong>
                       </td>
                      <td>
                        <div style={{ whiteSpace: 'pre-wrap' }}>
                          <strong>{orgSearch.name}</strong>
                        </div>
                      </td>
                       <td>
                         <div style={{ maxWidth: '200px', wordWrap: 'break-word' }}>
                           {orgSearch.fullName || (
                               <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                        Не указано
                      </span>
                           )}
                         </div>
                       </td>
                       <td>
                  <span className={`badge ${getTypeBadgeClass(orgSearch.type)}`}>
                    {formatType(orgSearch.type)}
                  </span>
                       </td>
                       <td>
                         <div>
                           <strong>{formatNumber(orgSearch.annualTurnover)}</strong>
                         </div>
                       </td>
                       <td>
                         {orgSearch.employeesCount ? (
                             <span>{formatNumber(orgSearch.employeesCount)}</span>
                         ) : (
                             <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                      -
                    </span>
                         )}
                       </td>
                       <td>
                         <div>
                           {formatDate(orgSearch.creationDate)}
                         </div>
                       </td>
                       <td>
                         <div style={{ fontSize: '0.9rem' }}>
                           <div>{orgSearch.coordinates.x}</div>
                         </div>
                       </td>
                       <td>
                         <div style={{ fontSize: '0.9rem' }}>
                           <div>{orgSearch.coordinates.y}</div>
                         </div>
                       </td>
                       <td>
                         {orgSearch.postalAddress ? (
                             <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                               <div>{orgSearch.postalAddress.street}</div>
                             </div>
                         ) : (
                             <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                      -
                    </span>
                         )}
                       </td>
                       <td>
                         {orgSearch.postalAddress ? (
                             <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                               {orgSearch.postalAddress.town.name && (
                                   <div>{orgSearch.postalAddress.town.name}</div>
                               )}
                             </div>
                         ) : (
                             <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                      -
                    </span>
                         )}
                       </td>
                       <td>
                         {orgSearch.postalAddress ? (
                             <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                               <div>{orgSearch.postalAddress.town.x}</div>
                             </div>
                         ) : (
                             <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                      -
                    </span>
                         )}
                       </td>
                       <td>
                         {orgSearch.postalAddress ? (
                             <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                               <div>{orgSearch.postalAddress.town.y}</div>
                             </div>
                         ) : (
                             <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                      -
                    </span>
                         )}
                       </td>
                       <td>
                         {orgSearch.postalAddress ? (
                             <div style={{ fontSize: '0.9rem', maxWidth: '200px' }}>
                               <div>{orgSearch.postalAddress.town.z}</div>
                             </div>
                         ) : (
                             <span style={{ color: '#7f8c8d', fontStyle: 'italic' }}>
                      -
                    </span>
                         )}
                       </td>
                     </tr>
                 </tbody>
               </table>
             </div>
               )}
               </div>
             </div>
           </div>
         </div>
       )}

        {/* Таблица */}
        <div className="table-container">
          <table className="table">
            <thead className="thead">
              <tr>
                <th style={{ width: '60px' }}>
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
                <th style={{ width: '120px' }}>
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
                    maxLength={254}
                    onChange={(e) => handleFilterChange('name', e.target.value)}
                    placeholder="Name"
                    style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '150px' }}>
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
                    maxLength={254}
                    onChange={(e) => handleFilterChange('fullName', e.target.value)}
                    placeholder="Fullname"
                    style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '100px' }}>
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
                    style={{ marginTop: '0.5rem', fontSize: '0.875rem', width: '100px' }}
                  >
                    <option value="">All</option>
                    <option value="PUBLIC">Public</option>
                    <option value="TRUST">Trust</option>
                    <option value="OPEN_JOINT_STOCK_COMPANY">OJS</option>
                  </select>
                </th>
                <th style={{ width: '140px' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
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
                       type="text"
                       inputMode="numeric"
                       className="form-control"
                       value={filters?.annualTurnover?.min || ''}
                       placeholder="Min turnover"
                       maxLength={254}
                       onChange={(e) => {
                         const value = e.target.value.replace(/\D/g, '');
                         const num = parseInt(value);
                         if (value === '' || (!isNaN(num) && num >= 0 && num <= 2147483647)) {
                           handleFilterChange('annualTurnover.min', value ? parseInt(value) : undefined);
                         }
                       }}
                       style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                   <input
                       type="text"
                       inputMode="numeric"
                       className="form-control"
                       value={filters?.annualTurnover?.max || ''}
                       placeholder="Max turnover"
                       maxLength={254}
                       onChange={(e) => {
                         const value = e.target.value.replace(/\D/g, '');
                         const num = parseInt(value);
                         if (value === '' || (!isNaN(num) && num >= 0 && num <= 2147483647)) {
                           handleFilterChange('annualTurnover.max', value ? parseInt(value) : undefined);
                         }
                       }}
                       style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                   </div>
                </th>
                <th style={{ width: '100px' }}>
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
                      type="text"
                      inputMode="numeric"
                      className="form-control"
                      value={filters?.employeesCount || ''}
                      maxLength={254}
                      onChange={(e) => {
                        const value = e.target.value.replace(/\D/g, '');
                        const num = parseInt(value);
                        if (value === '' || (!isNaN(num) && num >= 0 && num <= 2147483647)) {
                          handleFilterChange('employeesCount', value ? parseInt(value) : undefined);
                        }
                      }}
                      placeholder="Number"
                      style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '120px' }}>
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
                        max={'2999-12-31'}
                        value={filters?.creationDate?.min || ''}
                        className="form-control"
                        onChange={(e) => handleFilterChange('creationDate.min', e.target.value || undefined)}
                        style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                    />
                    <input
                        type="date"
                        className="form-control"
                        max={'2999-12-31'}
                        value={filters?.creationDate?.max || ''}
                        onChange={(e) => handleFilterChange('creationDate.max', e.target.value || undefined)}
                        style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                    />
                  </div>
                </th>
                <th style={{ width: '80px' }}>
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
                      type="text"
                      inputMode="numeric"
                      className="form-control"
                      placeholder="X"
                      value={coordXInput}
                      maxLength={254}
                      onChange={(e) => {
                        const value = e.target.value.replace(/[^\d-]/g, '');
                        const num = parseInt(value);
                        if (value === '' || value === '-' || (!isNaN(num) && num >= -2147483648 && num <= 2147483647)) {
                          setCoordXInput(value);
                          handleFilterChange('coordinates.x', (value && value !== '-') ? parseInt(value) : undefined);
                        }
                      }}
                      style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '80px' }}>
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
                      type="text"
                      inputMode="decimal"
                      className="form-control"
                      placeholder="Y"
                      value={coordYInput}
                      maxLength={254}
                      onChange={(e) => {
                        const value = e.target.value.replace(/[^\d.-]/g, '');
                        const num = parseFloat(value);
                        if (value === '' || value === '-' || value.endsWith('.') || (!isNaN(num) && num >= -1000000 && num <= 1000000)) {
                          setCoordYInput(value);
                          handleFilterChange('coordinates.y', (value && value !== '-' && !value.endsWith('.')) ? parseFloat(value) : undefined);
                        }
                      }}
                      style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '120px' }}>
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
                     maxLength={254}
                     onChange={(e) => handleFilterChange('postalAddress.street', e.target.value || undefined)}
                     placeholder="Street"
                     style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                </th>
                <th style={{ width: '100px' }}>
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
                     maxLength={254}
                     onChange={(e) => handleFilterChange('postalAddress.town.name', e.target.value || undefined)}
                     placeholder="Town"
                     style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                   />
                </th>
                <th style={{ width: '80px' }}>
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
                      type="text"
                      inputMode="decimal"
                      className="form-control"
                      placeholder="X"
                      value={townXInput}
                      maxLength={254}
                      onChange={(e) => {
                        const value = e.target.value.replace(/[^\d.-]/g, '');
                        const num = parseFloat(value);
                        if (value === '' || value === '-' || value.endsWith('.') || (!isNaN(num) && num >= -1000000 && num <= 1000000)) {
                          setTownXInput(value);
                          handleFilterChange('postalAddress.town.x', (value && value !== '-' && !value.endsWith('.')) ? parseFloat(value) : undefined);
                        }
                      }}
                      style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '60px' }}>
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
                      type="text"
                      inputMode="decimal"
                      className="form-control"
                      placeholder="Y"
                      value={townYInput}
                      maxLength={254}
                      onChange={(e) => {
                        const value = e.target.value.replace(/[^\d.-]/g, '');
                        const num = parseFloat(value);
                        if (value === '' || value === '-' || value.endsWith('.') || (!isNaN(num) && num >= -1000000 && num <= 1000000)) {
                          setTownYInput(value);
                          handleFilterChange('postalAddress.town.y', (value && value !== '-' && !value.endsWith('.')) ? parseFloat(value) : undefined);
                        }
                      }}
                      style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '60px' }}>
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
                      type="text"
                      inputMode="numeric"
                      className="form-control"
                      placeholder="Z"
                      value={townZInput}
                      maxLength={254}
                      onChange={(e) => {
                        const value = e.target.value.replace(/[^\d-]/g, '');
                        const num = parseInt(value);
                        if (value === '' || value === '-' || (!isNaN(num) && num >= -2147483648 && num <= 2147483647)) {
                          setTownZInput(value);
                          handleFilterChange('postalAddress.town.z', (value && value !== '-') ? parseInt(value) : undefined);
                        }
                      }}
                      style={{ marginTop: '0.5rem', fontSize: '0.875rem' }}
                  />
                </th>
                <th style={{ width: '100px' }}></th>
              </tr>
            </thead>
            <tbody>
              {organizations.map((org) => (
                <tr key={org.id}>
                  <td>
                    <strong>{org.id}</strong>
                  </td>
                  <td>
                    <div style={{ whiteSpace: 'pre-wrap' }}>
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
                        style={{ padding: '0.5rem', fontSize: '0.875rem', width: '4rem'}}
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-danger-outline"
                        onClick={() => onDelete(org.id!)}
                        style={{ padding: '0.5rem', fontSize: '0.875rem', width: '4rem' }}
                      >
                        Delete
                      </button>
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
                  style={{ width: '5rem', display: 'inline-block' }}
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
                </button>

                <button
                  onClick={() => onPageChange(currentPage - 1)}
                  disabled={currentPage === 0}
                  title="Previous page"
                >
                  &lt;
                </button>

                <span style={{ padding: '0.5rem 1rem', color: '#474c4c' }}>
                  Page {currentPage + 1} out of {totalPages}
                </span>

                <button
                  onClick={() => onPageChange(currentPage + 1)}
                  disabled={currentPage === totalPages - 1}
                  title="Next page"
                >
                  &gt;
                </button>

                <button
                  onClick={() => onPageChange(totalPages - 1)}
                  disabled={currentPage === totalPages - 1}
                  title="Last page"
                >
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

import React, { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { useDispatch } from 'react-redux';
import type { AppDispatch } from '../store/store';
import {
  usePostOrganizationsFilterMutation,
  usePostOrganizationsMutation,
  usePutOrganizationsByIdMutation,
  useDeleteOrganizationsByIdMutation,
  usePostOrganizationsDeleteByFullnameMutation,
  usePostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverMutation,
  usePostOrgdirectoryFilterTypeByTypeMutation,
  organizationsApi,
  OrganizationFilters,
  Organization,
  OrganizationRead,
  Pagination,
} from '../store/types.generated';
import OrganizationForm from './OrganizationForm';
import CompactOrganizationTable from './CompactOrganizationTable';

const OrganizationsPage: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const [organizations, setOrganizations] = useState<OrganizationRead[]>([]);
  const [totalCount, setTotalCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(20);
  const [filters, setFilters] = useState<OrganizationFilters['filter']>({});
  const [sorting, setSorting] = useState<OrganizationFilters['sort']>([]);
  const [showForm, setShowForm] = useState(false);
  const [editingOrganization, setEditingOrganization] = useState<OrganizationRead | null>(null);

  // API hooks
  const [filterOrganizations] = usePostOrganizationsFilterMutation();
  const [createOrganization] = usePostOrganizationsMutation();
  const [updateOrganization] = usePutOrganizationsByIdMutation();
  const [deleteOrganization] = useDeleteOrganizationsByIdMutation();
  const [deleteByFullname] = usePostOrganizationsDeleteByFullnameMutation();
  const [filterByTurnover] = usePostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverMutation();
  const [filterByType] = usePostOrgdirectoryFilterTypeByTypeMutation();

  // Load organizations on component mount and when filters/sorting change
  useEffect(() => {
    loadOrganizations();
  }, [currentPage, pageSize, filters, sorting]);

  const loadOrganizations = async () => {
    try {
      const pagination: Pagination = {
        page: currentPage,
        size: pageSize,
      };

      const organizationFilters: OrganizationFilters = {
        pagination,
        filter: filters,
        sort: sorting,
      };

      const result = await filterOrganizations({ organizationFilters }).unwrap();
      
      if (result.organizations) {
        setOrganizations(result.organizations);
        setTotalCount(result.totalCount || 0);
      }
    } catch (error: any) {
      console.error('Error loading organizations:', error);
      toast.error(`Ошибка загрузки организаций: ${error.data?.message || error.message}`);
    }
  };

  const handleCreateOrganization = async (organizationData: Organization) => {
    try {
      await createOrganization({ organization: organizationData }).unwrap();
      toast.success('Организация успешно создана');
      setShowForm(false);
      loadOrganizations();
    } catch (error: any) {
      console.error('Error creating organization:', error);
      toast.error(`Ошибка создания организации: ${error.data?.message || error.message}`);
    }
  };

  const handleUpdateOrganization = async (id: number, organizationData: Organization) => {
    try {
      await updateOrganization({ id, organization: organizationData }).unwrap();
      toast.success('Организация успешно обновлена');
      setEditingOrganization(null);
      loadOrganizations();
    } catch (error: any) {
      console.error('Error updating organization:', error);
      toast.error(`Ошибка обновления организации: ${error.data?.message || error.message}`);
    }
  };

  const handleDeleteOrganization = async (id: number) => {
    if (window.confirm('Вы уверены, что хотите удалить эту организацию?')) {
      try {
        await deleteOrganization({ id }).unwrap();
        toast.success('Организация успешно удалена');
        loadOrganizations();
      } catch (error: any) {
        console.error('Error deleting organization:', error);
        toast.error(`Ошибка удаления организации: ${error.data?.message || error.message}`);
      }
    }
  };

  const handleDeleteByFullname = async (fullName: string) => {
    if (window.confirm(`Вы уверены, что хотите удалить организацию с полным именем "${fullName}"?`)) {
      try {
        await deleteByFullname({ body: {fullname: fullName} }).unwrap();
        toast.success('Организация успешно удалена');
        loadOrganizations();
      } catch (error: any) {
        console.error('Error deleting organization by fullname:', error);
        toast.error(`Ошибка удаления организации: ${error.data?.message || error.message}`);
      }
    }
  };

  const handleFilterByTurnover = async (minTurnover: number, maxTurnover: number) => {
    try {
      const pagination: Pagination = {
        page: 0,
        size: pageSize,
      };

      const result = await filterByTurnover({
        'min-annual-turnover': minTurnover,
        'max-annual-turnover': maxTurnover,
        pagination,
      }).unwrap();

      if (result.organizations) {
        setOrganizations(result.organizations);
        setTotalCount(result.totalCount || 0);
        setCurrentPage(0);
      }
    } catch (error: any) {
      console.error('Error filtering by turnover:', error);
      toast.error(`Ошибка фильтрации по обороту: ${error.data?.message || error.message}`);
    }
  };

  const handleFilterByType = async (type: 'PUBLIC' | 'TRUST' | 'OPEN_JOINT_STOCK_COMPANY' | '') => {
    try {
      if (!type) {
        // Clear type filter - reload with current filters
        loadOrganizations();
        return;
      }

      const pagination: Pagination = {
        page: 0,
        size: pageSize,
      };

      const result = await filterByType({
        type: type as 'PUBLIC' | 'TRUST' | 'OPEN_JOINT_STOCK_COMPANY',
        pagination,
      }).unwrap();

      if (result.organizations) {
        setOrganizations(result.organizations);
        setTotalCount(result.totalCount || 0);
        setCurrentPage(0);
      }
    } catch (error: any) {
      console.error('Error filtering by type:', error);
      toast.error(`Filter error: ${error.data?.message || error.message}`);
    }
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const handlePageSizeChange = (size: number) => {
    setPageSize(size);
    setCurrentPage(0);
  };

  const handleFiltersChange = (newFilters: OrganizationFilters['filter']) => {
    setFilters(newFilters);
    setCurrentPage(0);
  };

  const handleSortingChange = (newSorting: OrganizationFilters['sort']) => {
    setSorting(newSorting);
    setCurrentPage(0);
  };

  const handleGetEmployeesStats = async (quantity: number) => {
    try {
      const result = await dispatch(organizationsApi.endpoints.getOrganizationsQuantityByEmployees.initiate({ quantity }));
      console.log('Employees stats:', result);
      if (result.data) {
        toast.success(`Found ${result.data.count} organizations with ${quantity}+ employees`);
      }
    } catch (error: any) {
      console.error('Error getting employees stats:', error);
      toast.error(`Error getting employees stats: ${error.data?.message || error.message}`);
    }
  };

  const handleGetTurnoverStats = async (maxTurnover: number) => {
    try {
      const result = await dispatch(organizationsApi.endpoints.getOrganizationsQuantityByTurnover.initiate({ 'max-turnover': maxTurnover }));
      console.log('Turnover stats:', result);
      if (result.data) {
        toast.success(`Found ${result.data.count} organizations with turnover <= ${maxTurnover}`);
      }
    } catch (error: any) {
      console.error('Error getting turnover stats:', error);
      toast.error(`Error getting turnover stats: ${error.data?.message || error.message}`);
    }
  };

  const handleGetOrganizationById = async (id: number) => {
    try {
      const result = await dispatch(organizationsApi.endpoints.getOrganizationsById.initiate({ id }));
      console.log('Organization by ID:', result);
      if (result.data) {
        toast.success(`Organization found: ${result.data.name}`);
        return result.data;
      }
    } catch (error: any) {
      console.error('Error getting organization by ID:', error);
      toast.error(`Error getting organization: ${error.data?.message || error.message}`);
    }
  };

  const handleEditOrganization = (organization: OrganizationRead) => {
    setEditingOrganization(organization);
  };

  const handleCancelEdit = () => {
    setEditingOrganization(null);
    setShowForm(false);
  };

  const totalPages = Math.ceil(totalCount / pageSize);

  return (
    <div className="organizations-page">
      <div className="row">
        <div className="col-12">
          <div className="card">
            <div className="card-body">
              <div className="row">
                <div className="col-12 mb-3">
                  <button
                    className="btn btn-primary"
                    onClick={() => setShowForm(true)}
                  >
                    ➕ Create Organization
                  </button>
                </div>
              </div>

              {showForm && (
                <div className="row">
                  <div className="col-12">
                    <OrganizationForm
                      onSubmit={handleCreateOrganization}
                      onCancel={handleCancelEdit}
                      title="New Organization"
                    />
                  </div>
                </div>
              )}

              {editingOrganization && (
                <div className="row">
                  <div className="col-12">
                    <OrganizationForm
                      organization={editingOrganization}
                      onSubmit={(data) => handleUpdateOrganization(editingOrganization.id!, data)}
                      onCancel={handleCancelEdit}
                      title="Edit Organization"
                    />
                  </div>
                </div>
              )}

              <CompactOrganizationTable
                organizations={organizations}
                totalCount={totalCount}
                currentPage={currentPage}
                pageSize={pageSize}
                onEdit={handleEditOrganization}
                onDelete={handleDeleteOrganization}
                onDeleteByFullname={handleDeleteByFullname}
                onFiltersChange={handleFiltersChange}
                onSortingChange={handleSortingChange}
                onPageChange={handlePageChange}
                onPageSizeChange={handlePageSizeChange}
                onFilterByTurnover={handleFilterByTurnover}
                onFilterByType={handleFilterByType}
                onGetEmployeesStats={handleGetEmployeesStats}
                onGetTurnoverStats={handleGetTurnoverStats}
                onGetOrganizationById={handleGetOrganizationById}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OrganizationsPage;

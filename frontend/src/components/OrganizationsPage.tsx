import React, {useState, useEffect, useRef} from 'react';
import {toast} from 'react-toastify';
import {useDispatch} from 'react-redux';
import type {AppDispatch} from '../store/store';
import {
    usePostOrganizationsFilterMutation,
    usePostOrganizationsMutation,
    usePutOrganizationsByIdMutation,
    useDeleteOrganizationsByIdMutation,
    usePostOrganizationsDeleteByFullnameMutation,
    usePostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverMutation,
    usePostOrgdirectoryFilterTypeByTypeMutation,
    useGetOrgdirectoryBalanceQuery,
    usePostOrgdirectoryBalanceMutation,
    organizationsApi,
    OrganizationFilters,
    Organization,
    OrganizationRead,
    Pagination, useGetOrganizationsConfigurationsDatabaseQuery, DatabaseVariant,
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
    const [empStats, setEmpStats] = useState(0);
    const [turnoverStats, setTurnoverStats] = useState(0);
    const [orgSearch, setOrgSearch] = useState<OrganizationRead | null>(null);

    const editFormRef = useRef<HTMLDivElement>(null);

    // API hooks
    const [filterOrganizations] = usePostOrganizationsFilterMutation();
    const [createOrganization] = usePostOrganizationsMutation();
    const [updateOrganization] = usePutOrganizationsByIdMutation();
    const [deleteOrganization] = useDeleteOrganizationsByIdMutation();
    const [deleteByFullname] = usePostOrganizationsDeleteByFullnameMutation();
    const [filterByTurnover] = usePostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverMutation();
    const [filterByType] = usePostOrgdirectoryFilterTypeByTypeMutation();
    const {data: balanceData, refetch: refetchBalance} = useGetOrgdirectoryBalanceQuery();
    const [addBalance, {isLoading: isAddingBalance}] = usePostOrgdirectoryBalanceMutation();

    // Load organizations on component mount and when filters/sorting change
    useEffect(() => {
        loadOrganizations();
    }, [currentPage, pageSize, filters, sorting]);

    // useEffect(() => {
    //   if (error) {
    //     toast.error(`Error getting db type: ${error.data?.message || error.message}`);
    //   }
    // }, [error]);

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

            const result = await filterOrganizations({organizationFilters}).unwrap();

            if (result.organizations) {
                setOrganizations(result.organizations);
                setTotalCount(result.totalCount || 0);
            }
        } catch (error: any) {
            console.error('Error loading organizations:', error);
            toast.error(`Failed to load page: check if server is running`);
        }
    };

    useEffect(() => {
        const interval = setInterval(() => {
            loadOrganizations();
        }, 5000);
        return () => clearInterval(interval);
    }, [loadOrganizations]);

    const handleCreateOrganization = async (organizationData: Organization) => {
        try {
            await createOrganization({organization: organizationData}).unwrap();
            toast.success('Organization created!');
            setShowForm(false);
            loadOrganizations();
        } catch (error: any) {
            console.error('Error creating organization:', error);
            toast.error(`Error creating organization: ${error.data?.message || error.message}`);
        }
    };

    const handleUpdateOrganization = async (id: number, organizationData: Organization) => {
        try {
            await updateOrganization({id, organization: organizationData}).unwrap();
            toast.success(`Organization updated!`);
            setEditingOrganization(null);
            loadOrganizations();
        } catch (error: any) {
            console.error('Error updating organization:', error);
            if (error.status === 404) {
                toast.error(`Organization with ID ${id} not found`);
            } else if (!error.data) {
                toast.error('Oooops something wrong');
            } else {
                toast.error(`Error updating organization: ${error.data?.message || error.message}`);
            }
        }
    };

    const handleDeleteOrganization = async (id: number) => {
        if (window.confirm(`Confirm delete organization with ID = ${id}?`)) {
            try {
                await deleteOrganization({id}).unwrap();
                toast.success(`Organization with ID = ${id} deleted!`);
                loadOrganizations();
            } catch (error: any) {
                console.error('Error deleting organization:', error);
                if (error.status === 404) {
                    toast.error(`Organization with ID = ${id} not found`);
                } else if (!error.data) {
                    toast.error('Oooops something wrong');
                } else {
                    toast.error(`Error deleting organization: ${error.data?.message || error.message}`);
                }
            }
        }
    };

    const handleDeleteByFullname = async (fullName: string) => {
        if (window.confirm(`Confirm delete organization "${fullName}"?`)) {
            try {
                await deleteByFullname({body: {fullname: fullName}}).unwrap();
                toast.success(`Organization "${fullName}" deleted!`);
                loadOrganizations();
            } catch (error: any) {
                console.error('Error deleting organization by fullname:', error);
                if (error.status === 404) {
                    toast.error(`Organization "${fullName}" not found`);
                } else if (!error.data) {
                    toast.error('Oooops something wrong');
                } else {
                    toast.error(`Ошибка удаления организации: ${error.data?.message || error.message}`);
                }
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
            const result = await dispatch(organizationsApi.endpoints.getOrganizationsQuantityByEmployees.initiate({quantity}));
            if (result.data) {
                setEmpStats(result.data.count || 0)
            }
        } catch (error: any) {
            console.error('Error getting employees stats:', error);
            toast.error(`Error getting employees stats: ${error.data?.message || error.message}`);
        }
    };

    const handleGetTurnoverStats = async (maxTurnover: number) => {
        try {
            const result = await dispatch(organizationsApi.endpoints.getOrganizationsQuantityByTurnover.initiate({'max-turnover': maxTurnover}));
            if (result.data) {
                setTurnoverStats(result.data.count || 0)
            }
        } catch (error: any) {
            console.error('Error getting turnover stats:', error);
            toast.error(`Error getting turnover stats: ${error.data?.message || error.message}`);
        }
    };

    const handleGetOrganizationById = async (id: number) => {
        try {
            const result = await dispatch(organizationsApi.endpoints.getOrganizationsById.initiate({id}));
            // if (result.data) {
            setOrgSearch(result.data || null);
            // }
        } catch (error: any) {
            console.error('Error getting organization by ID:', error);
            toast.error(`Error getting organization: ${error.data?.message || error.message}`);
        }
    };

    // const handleGetDatabaseType = async () => {
    //   try {
    //     const result = await dispatch(organizationsApi.endpoints.getOrganizationsConfigurationsDatabase.initiate());
    //     // if (result.data) {
    //     setDbType(result.data || null);
    //     // }
    //   } catch (error: any) {
    //     console.error('Error getting db type:', error);
    //     toast.error(`Error getting db type: ${error.data?.message || error.message}`);
    //   }
    // };

    const handleAddBalance = async () => {
        try {
            const currentBalance = balanceData?.balance || 0;
            await addBalance({balance: {balance: 10000}}).unwrap();
            toast.success('Balance topped up by 10000!');
            refetchBalance();
        } catch (error: any) {
            toast.error(`Error adding balance: ${error.data?.message || error.message}`);
        }
    };

    const handleEditOrganization = (organization: OrganizationRead) => {
        setEditingOrganization(organization);
        editFormRef.current?.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        });
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
                        <div className="card-body" ref={editFormRef}>
                            <div className="row">
                                <div className="col-12 mb-3 d-flex align-items-center justify-content-between">
                                    <button
                                        className="btn btn-primary"
                                        onClick={() => {
                                            setShowForm(true)
                                            editFormRef.current?.scrollIntoView({
                                                behavior: 'smooth',
                                                block: 'start'
                                            });
                                        }
                                        }
                                    >
                                        ➕ Create Organization
                                    </button>
                                    <div className="d-flex align-items-center gap-2 border rounded shadow-sm px-3 py-2">
                                        <span>Balance: <strong>{balanceData?.balance ?? 0}</strong></span>
                                        <button
                                            className="btn btn-success btn-sm"
                                            onClick={handleAddBalance}
                                            disabled={isAddingBalance}
                                        >
                                            {isAddingBalance ? '...' : '+10000'}
                                        </button>
                                    </div>
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
                                empStats={empStats}
                                turnoverStats={turnoverStats}
                                orgSearch={orgSearch}
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

import { api } from "./baseApi";
const injectedRtkApi = api.injectEndpoints({
  endpoints: (build) => ({
    postOrganizations: build.mutation<
      PostOrganizationsApiResponse,
      PostOrganizationsApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations`,
        method: "POST",
        body: queryArg.organization,
      }),
    }),
    postOrganizationsFilter: build.mutation<
      PostOrganizationsFilterApiResponse,
      PostOrganizationsFilterApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations/filter`,
        method: "POST",
        body: queryArg.organizationFilters,
      }),
    }),
    getOrganizationsConfigurationsDatabase: build.query<
      GetOrganizationsConfigurationsDatabaseApiResponse,
      GetOrganizationsConfigurationsDatabaseApiArg
    >({
      query: () => ({ url: `/organizations/configurations/database` }),
    }),
    postOrganizationsConfigurationsDatabase: build.mutation<
      PostOrganizationsConfigurationsDatabaseApiResponse,
      PostOrganizationsConfigurationsDatabaseApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations/configurations/database`,
        method: "POST",
        body: queryArg.databaseVariant,
      }),
    }),
    getOrganizationsById: build.query<
      GetOrganizationsByIdApiResponse,
      GetOrganizationsByIdApiArg
    >({
      query: (queryArg) => ({ url: `/organizations/${queryArg.id}` }),
    }),
    putOrganizationsById: build.mutation<
      PutOrganizationsByIdApiResponse,
      PutOrganizationsByIdApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations/${queryArg.id}`,
        method: "PUT",
        body: queryArg.organization,
      }),
    }),
    deleteOrganizationsById: build.mutation<
      DeleteOrganizationsByIdApiResponse,
      DeleteOrganizationsByIdApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations/${queryArg.id}`,
        method: "DELETE",
      }),
    }),
    postOrganizationsDeleteByFullname: build.mutation<
      PostOrganizationsDeleteByFullnameApiResponse,
      PostOrganizationsDeleteByFullnameApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations/delete/by-fullname`,
        method: "POST",
        body: queryArg.body,
      }),
    }),
    getOrganizationsQuantityByEmployees: build.query<
      GetOrganizationsQuantityByEmployeesApiResponse,
      GetOrganizationsQuantityByEmployeesApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations/quantity/by-employees`,
        params: {
          quantity: queryArg.quantity,
        },
      }),
    }),
    getOrganizationsQuantityByTurnover: build.query<
      GetOrganizationsQuantityByTurnoverApiResponse,
      GetOrganizationsQuantityByTurnoverApiArg
    >({
      query: (queryArg) => ({
        url: `/organizations/quantity/by-turnover`,
        params: {
          "max-turnover": queryArg["max-turnover"],
        },
      }),
    }),
    postOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnover:
      build.mutation<
        PostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverApiResponse,
        PostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverApiArg
      >({
        query: (queryArg) => ({
          url: `/orgdirectory/filter/turnover/${queryArg["min-annual-turnover"]}/${queryArg["max-annual-turnover"]}`,
          method: "POST",
          body: queryArg.pagination,
        }),
      }),
    postOrgdirectoryFilterTypeByType: build.mutation<
      PostOrgdirectoryFilterTypeByTypeApiResponse,
      PostOrgdirectoryFilterTypeByTypeApiArg
    >({
      query: (queryArg) => ({
        url: `/orgdirectory/filter/type/${queryArg["type"]}`,
        method: "POST",
        body: queryArg.pagination,
      }),
    }),
  }),
  overrideExisting: false,
});
export { injectedRtkApi as organizationsApi };
export type PostOrganizationsApiResponse =
  /** status 201 Организация успешно создана */ OrganizationRead;
export type PostOrganizationsApiArg = {
  organization: Organization;
};
export type PostOrganizationsFilterApiResponse =
  /** status 200 Успешное получение списка организаций */ OrganizationArrayRead;
export type PostOrganizationsFilterApiArg = {
  organizationFilters: OrganizationFilters;
};
export type GetOrganizationsConfigurationsDatabaseApiResponse =
  /** status 200 Конфигурация базы данных получена */ DatabaseVariant;
export type GetOrganizationsConfigurationsDatabaseApiArg = void;
export type PostOrganizationsConfigurationsDatabaseApiResponse =
  /** status 200 Конфигурация базы данных установлена */ DatabaseVariant;
export type PostOrganizationsConfigurationsDatabaseApiArg = {
  databaseVariant: DatabaseVariant;
};
export type GetOrganizationsByIdApiResponse =
  /** status 200 Организация найдена */ OrganizationRead;
export type GetOrganizationsByIdApiArg = {
  id: number;
};
export type PutOrganizationsByIdApiResponse =
  /** status 200 Организация успешно обновлена */ OrganizationRead;
export type PutOrganizationsByIdApiArg = {
  id: number;
  organization: Organization;
};
export type DeleteOrganizationsByIdApiResponse = unknown;
export type DeleteOrganizationsByIdApiArg = {
  id: number;
};
export type PostOrganizationsDeleteByFullnameApiResponse = unknown;
export type PostOrganizationsDeleteByFullnameApiArg = {
  body: {
    fullname?: string;
  };
};
export type GetOrganizationsQuantityByEmployeesApiResponse =
  /** status 200 Успешное получение количества */ {
    count?: number;
  };
export type GetOrganizationsQuantityByEmployeesApiArg = {
  quantity: number;
};
export type GetOrganizationsQuantityByTurnoverApiResponse =
  /** status 200 Успешное получение количества */ {
    count?: number;
  };
export type GetOrganizationsQuantityByTurnoverApiArg = {
  "max-turnover": number;
};
export type PostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverApiResponse =
  /** status 200 Успешная фильтрация */ OrganizationArrayRead;
export type PostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverApiArg =
  {
    "min-annual-turnover": number;
    "max-annual-turnover": number;
    pagination: Pagination;
  };
export type PostOrgdirectoryFilterTypeByTypeApiResponse =
  /** status 200 Успешная фильтрация */ OrganizationArrayRead;
export type PostOrgdirectoryFilterTypeByTypeApiArg = {
  type: "PUBLIC" | "TRUST" | "OPEN_JOINT_STOCK_COMPANY";
  pagination: Pagination;
};
export type Coordinates = {
  /** Значение должно быть больше -366 */
  x: number;
  y: number;
};
export type Location = {
  x: number;
  y: number;
  /** Поле не может быть null */
  z: number;
  name?: string | null;
};
export type Address = {
  /** Строка не может быть пустой */
  street: string;
  town: Location;
};
export type Organization = {
  /** Поле не может быть null или пустым */
  name: string;
  coordinates: Coordinates;
  /** Значение должно быть больше 0 */
  annualTurnover: number;
  fullName?: string | null;
  /** Значение должно быть больше 0 если указано */
  employeesCount?: number | null;
  type: "PUBLIC" | "TRUST" | "OPEN_JOINT_STOCK_COMPANY";
  postalAddress?: Address;
};
export type OrganizationRead = {
  /** Генерируется автоматически, должно быть больше 0 */
  id?: number;
  /** Поле не может быть null или пустым */
  name: string;
  coordinates: Coordinates;
  /** Генерируется автоматически */
  creationDate?: string;
  /** Значение должно быть больше 0 */
  annualTurnover: number;
  fullName?: string | null;
  /** Значение должно быть больше 0 если указано */
  employeesCount?: number | null;
  type: "PUBLIC" | "TRUST" | "OPEN_JOINT_STOCK_COMPANY";
  postalAddress?: Address;
};
export type Error = {
  error?: string;
  message?: string;
  timestamp?: string;
};
export type OrganizationArray = {
  organizations?: Organization[];
  totalCount?: number;
  page?: number;
  size?: number;
};
export type OrganizationArrayRead = {
  organizations?: OrganizationRead[];
  totalCount?: number;
  page?: number;
  size?: number;
};
export type Pagination = {
  /** Номер страницы */
  page?: number;
  /** Размер страницы */
  size?: number;
};
export type OrganizationFilters = {
  pagination?: Pagination;
  sort?: {
    field?:
      | "ID"
      | "NAME"
      | "CREATION_DATE"
      | "ANNUAL_TURNOVER"
      | "FULL_NAME"
      | "EMPLOYEES_COUNT"
      | "TYPE"
      | "ADDRESS_STREET"
      | "ADDRESS_TOWN_X"
      | "ADDRESS_TOWN_Y"
      | "ADDRESS_TOWN_Z"
      | "ADDRESS_TOWN_NAME"
      | "COORDINATES_X"
      | "COORDINATES_Y"
      | "LOCATION_NAME";
    direction?: "ASC" | "DESC";
  }[];
  filter?: {
    organizationId?: number;
    name?: string;
    coordinates?: {
      x?: number;
      y?: number;
    };
    creationDate?: {
      min?: string;
      max?: string;
    };
    annualTurnover?: {
      min?: number;
      max?: number;
    };
    fullName?: string;
    employeesCount?: number;
    type?: "PUBLIC" | "TRUST" | "OPEN_JOINT_STOCK_COMPANY";
    postalAddress?: {
      street?: string;
      town?: {
        x?: number;
        y?: number;
        z?: number;
        name?: string;
      };
    };
  };
};
export type DatabaseVariant = "MYSQL" | "POSTGRESQL";
export const {
  usePostOrganizationsMutation,
  usePostOrganizationsFilterMutation,
  useGetOrganizationsConfigurationsDatabaseQuery,
  usePostOrganizationsConfigurationsDatabaseMutation,
  useGetOrganizationsByIdQuery,
  usePutOrganizationsByIdMutation,
  useDeleteOrganizationsByIdMutation,
  usePostOrganizationsDeleteByFullnameMutation,
  useGetOrganizationsQuantityByEmployeesQuery,
  useGetOrganizationsQuantityByTurnoverQuery,
  usePostOrgdirectoryFilterTurnoverByMinAnnualTurnoverAndMaxAnnualTurnoverMutation,
  usePostOrgdirectoryFilterTypeByTypeMutation,
} = injectedRtkApi;

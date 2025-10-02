// Or from '@reduxjs/toolkit/query' if not using the auto-generated hooks


// initialize an empty api service that we'll inject endpoints into later as needed
import {BaseQueryApi, createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react';

const baseQuery = fetchBaseQuery({
    baseUrl: 'https://localhost/',
});

export const api = createApi({
    baseQuery: baseQuery,
    endpoints: () => ({}),
})
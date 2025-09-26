import { configureStore } from '@reduxjs/toolkit';
import { organizationsApi } from './types.generated';

export const store = configureStore({
  reducer: {
    [organizationsApi.reducerPath]: organizationsApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(organizationsApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

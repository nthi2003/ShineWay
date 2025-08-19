import axios from 'axios';
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { serializeAxiosError } from 'app/shared/reducers/reducer.utils';
export interface Category {
  id?: string;
  name?: string;
}
const initialState = {
  loading: false,
  errorMessage: null as string | null,
  entities: [],
};

export type CategoryState = Readonly<typeof initialState>;

export const getCategories = createAsyncThunk('category/fetch_list', async () => axios.get<Category[]>('/api/categories'), {
  serializeError: serializeAxiosError,
});

export const categorySlice = createSlice({
  name: 'category',
  initialState,
  reducers: {
    reset() {
      return initialState;
    },
  },
  extraReducers(builder) {
    builder
      .addCase(getCategories.pending, state => {
        state.loading = true;
        state.errorMessage = null;
      })
      .addCase(getCategories.rejected, (state, action) => {
        state.loading = false;
        state.errorMessage = action.error.message ?? 'Error fetching categories';
      })
      .addCase(getCategories.fulfilled, (state, action) => {
        state.loading = false;
        state.entities = action.payload.data;
      });
  },
});

export const { reset } = categorySlice.actions;

export default categorySlice.reducer;

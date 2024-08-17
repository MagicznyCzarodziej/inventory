import axios, { AxiosError, GenericAbortSignal } from 'axios';

export const CANNOT_REFRESH_TOKEN_ERROR_MESSAGE = "CANNOT_REFRESH_TOKEN"

interface ErrorResponse {
  error: {
    code: string;
    message: string;
  }
}

const axiosInstance = axios.create({
  // baseURL: "http://10.0.2.2:8080",
  // baseURL: "http://192.168.0.66:8080",
  baseURL: "http://inventory-api.przemyslawpitus.pl",
  withCredentials: true,
})

const refreshToken = async () => {
  return await axiosInstance.post('/auth/refresh-token', {})
}

axiosInstance.interceptors.response.use((response) => {
  return response
}, async (error: AxiosError) => {
  const request = error.config

  if (error.response?.status === 401 && (error.response.data as ErrorResponse).error.code === 'EXPIRED_ACCESS_TOKEN') {
    try {
      const refreshResponse = await refreshToken()

      if (refreshResponse.status === 204) {
        // Retry original request
        return axiosInstance(request!)
      }
    } catch (e) {
      return Promise.reject(new Error(CANNOT_REFRESH_TOKEN_ERROR_MESSAGE))
    }
  }

  return Promise.reject(error)
})

const api = {
  get: async <R>(url: string, signal?: GenericAbortSignal,) => {
    const response = await axiosInstance.get<R>(url, {
      headers: {
        Accept: 'application/json'
      },
      signal,
    });
    return response.data;
  },
  getImage: async <R>(url: string, signal?: GenericAbortSignal,) => {
    const response = await axiosInstance.get<R>(url, {
      headers: {
        Accept: 'image/jpeg'
      },
      responseType: 'arraybuffer',
      signal,
    });
    return response.data;
  },
  post: async <R, B>(url: string, body: B) => {
    const response = await axiosInstance.post<R>(url, body, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      }
    });
    return response.data;
  },
  uploadImage: async <R, B>(url: string, body: B) => {
    const response = await axiosInstance.post<R>(url, body, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'multipart/form-data'
      }
    });
    return response.data;
  },
  put: async <R, B>(url: string, body: B) => {
    const response = await axiosInstance.put<R>(url, body, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      }
    });
    return response.data;
  },
  delete: async <R>(url: string) => {
    const response = await axiosInstance.delete<R>(url, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      }
    });
    return response.data;
  },
};

export { api }

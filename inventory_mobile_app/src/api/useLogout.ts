import { api } from './api';
import { useMutation } from '@tanstack/react-query';

const logout = () => api.post<undefined, {}>('/auth/logout', {});

export const useLogout = () => {
  return useMutation({
    mutationFn: logout,
  });
};

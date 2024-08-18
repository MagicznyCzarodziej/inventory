import { api } from './api';
import { useMutation, useQueryClient } from '@tanstack/react-query';

const login = ({ username, password }: LoginRequest) => api.post<undefined, LoginRequest>("/auth/login",
  {
    username,
    password,
  });

export const useLogin = () => {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: login,
    onSuccess: () => {
      queryClient.invalidateQueries()
    }
  });
};

interface LoginRequest {
  username: string;
  password: string;
}
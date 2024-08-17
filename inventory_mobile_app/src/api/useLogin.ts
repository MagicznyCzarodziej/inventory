import { api } from './api';
import { useMutation } from '@tanstack/react-query';

const login = ({ username, password }: LoginRequest) => api.post<undefined, LoginRequest>("/auth/login",
  {
    username,
    password,
  });

export const useLogin = () => useMutation({ mutationFn: login });

interface LoginRequest {
  username: string;
  password: string;
}
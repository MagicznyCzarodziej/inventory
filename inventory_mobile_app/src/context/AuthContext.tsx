import { createContext, PropsWithChildren, useContext } from 'react';
import { useQueryClient } from '@tanstack/react-query';
import { useCurrentUser } from '../api/useCurrentUser';
import { clearSession } from '../auth/clearSession';

interface AuthContextValues {
  isAuthenticated: boolean;
  isLoading: boolean;
  username: string | null;
  refetchUser: () => void;
  clearAuth: () => void;
}

export const AuthContext = createContext<AuthContextValues>({
  isAuthenticated: false,
  isLoading: true,
  username: null,
  refetchUser: () => {
  },
  clearAuth: () => {
  },
});

export const useAuth = () => useContext(AuthContext);

export const AuthContextProvider = (props: PropsWithChildren) => {
  const { children } = props;
  const queryClient = useQueryClient();
  const { data, isLoading, isError, refetch } = useCurrentUser();

  const isAuthenticated = !isLoading && !isError && !!data;
  const username = data?.username ?? null;

  const clearAuth = () => {
    clearSession(queryClient);
  };

  return <AuthContext.Provider value={{
    isAuthenticated,
    isLoading, username,
    refetchUser: () => {
      refetch();
    },
    clearAuth,
  }}>
    {children}
  </AuthContext.Provider>;
};

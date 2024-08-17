import { useNavigation } from '@react-navigation/native';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { CANNOT_REFRESH_TOKEN_ERROR_MESSAGE } from '../api/api';
import { PropsWithChildren } from 'react';

const REQUEST_RETRY_COUNT = 3

const useCreateQueryClient = () => {
  const { navigate } = useNavigation()

  const navigateToLoginPageOnExpiredRefreshToken = (failureCount: number, error: Error) => {
    if (error.message === CANNOT_REFRESH_TOKEN_ERROR_MESSAGE) {
      navigate('ACCOUNT')
      return false
    }

    return failureCount < REQUEST_RETRY_COUNT
  }

  return new QueryClient({
    defaultOptions: {
      queries: {
        retry: navigateToLoginPageOnExpiredRefreshToken
      },
      mutations: {
        retry: navigateToLoginPageOnExpiredRefreshToken
      }
    }
  })
}

export const WrapWithQueryClient = ({ children }: PropsWithChildren) => {
  const queryClient = useCreateQueryClient()

  return <QueryClientProvider client={queryClient}>
    {children}
  </QueryClientProvider>
}
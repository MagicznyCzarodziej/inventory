import { MutationCache, QueryCache, QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { CANNOT_REFRESH_TOKEN_ERROR_MESSAGE } from '../api/api';
import { clearSession } from '../auth/clearSession';
import { PropsWithChildren, useRef } from 'react';
import { AxiosError } from 'axios';

const MAX_RETRIES = 3;

const isAuthRefreshError = (error: Error) => error.message === CANNOT_REFRESH_TOKEN_ERROR_MESSAGE;

const isClientError = (error: Error) => {
  const status = (error as AxiosError)?.response?.status;
  return status !== undefined && status >= 400 && status < 500;
};

const shouldRetry = (failureCount: number, error: Error) => {
  if (isAuthRefreshError(error) || isClientError(error)) {
    return false;
  }

  return failureCount < MAX_RETRIES;
};

const createQueryClient = (onAuthError: () => void) => {
  const onError = (error: Error) => {
    if (isAuthRefreshError(error)) {
      onAuthError();
    }
  };

  return new QueryClient({
    queryCache: new QueryCache({ onError }),
    mutationCache: new MutationCache({ onError }),
    defaultOptions: {
      queries: { retry: shouldRetry },
      mutations: { retry: shouldRetry },
    },
  });
};

export const WrapWithQueryClient = ({ children }: PropsWithChildren) => {
  const queryClientRef = useRef<QueryClient>();

  if (!queryClientRef.current) {
    queryClientRef.current = createQueryClient(() => {
      clearSession(queryClientRef.current!);
    });
  }

  return <QueryClientProvider client={queryClientRef.current}>
    {children}
  </QueryClientProvider>;
};

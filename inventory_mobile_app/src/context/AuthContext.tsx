import { createContext, PropsWithChildren } from 'react';

interface AuthContextValues {

}

export const AuthContext = createContext<AuthContextValues>({});

export const AuthContextProvider = (props: PropsWithChildren) => {
  const { children } = props



  return <AuthContext.Provider value={{}}>
    {children}
  </AuthContext.Provider>
}
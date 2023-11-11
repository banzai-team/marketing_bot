import { createContext, ReactNode, useContext, useReducer } from "react";
import { AuthProvider as OidcProvider, useAuth } from "react-oidc-context";
import { oidcConfig } from "~/config/config";

type AuthActions = { type: 'SIGN_IN', payload: { user: unknown } } | { type: 'SIGN_OUT' }

type AuthState = {
  state: 'SIGNED_IN'
  currentUser: unknown;
} | {
  state: 'SIGNED_OUT'
} | {
  state: 'UNKNOWN'
};

const AuthReducer = (state: AuthState, action: AuthActions): AuthState => {
  switch (action.type) {
    case "SIGN_IN":
      return {
        state: 'SIGNED_IN',
        currentUser: action.payload.user,
      };
      break
    case "SIGN_OUT":
      return {
        state: 'SIGNED_OUT',
      }
  }
}

type AuthContextProps = {
  state: AuthState
  dispatch: (value: AuthActions) => void
}

export const AuthContext = createContext<AuthContextProps>({
  state: { state: 'UNKNOWN' }, dispatch: (val) => {
  }
});

const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [state, dispatch] = useReducer(AuthReducer, { state: 'UNKNOWN' })

  return (
    <OidcProvider {...oidcConfig}>
      <AuthContext.Provider value={{ state, dispatch }}>
        {children}
      </AuthContext.Provider>
    </OidcProvider>
  );
};

const useAuthState = () => {
  const { state } = useContext(AuthContext);
  return {
    state,
  };
};


export { useAuthState, useAuth, AuthProvider };

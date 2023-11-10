import { createContext, ReactNode, useContext, useReducer } from "react";
import { User } from "firebase/auth";
import { AuthProvider as OidcProvider } from "react-oidc-context";

type AuthActions = { type: 'SIGN_IN', payload: { user: User } } | { type: 'SIGN_OUT' }

type AuthState = {
  state: 'SIGNED_IN'
  currentUser: User;
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

const oidcConfig = {
  authority: "https://gazprom-auth.banzai-predict.site/realms/gazprom-mrkt",
  client_id: "webapp",
  redirect_uri: "http://localhost:5173",
};

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

const useSignIn = () => {
  const { dispatch } = useContext(AuthContext)
  return {
    signIn: (user: User) => {
      dispatch({ type: "SIGN_IN", payload: { user } })
    }
  }
}

const useSignOut = () => {
  const { dispatch } = useContext(AuthContext)
  return {
    signOut: () => {
      dispatch({ type: "SIGN_OUT" })
    }
  }
}

export { useAuthState, useSignIn, useSignOut, AuthProvider };

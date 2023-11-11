declare global {
  interface Window {
    API_URL?: string;
    KEYCLOAK_CLIENT_ID?: string;
    KEYCLOAK_AUTHORITY?: string;
  }
}

export const config = {
  apiUrl: window.API_URL || import.meta.env.VITE_API_URL || "/api"
}

export const oidcConfig = {
  authority: window.KEYCLOAK_AUTHORITY || "https://gazprom-auth.banzai-predict.site/realms/gazprom-mrkt",
  client_id: window.KEYCLOAK_CLIENT_ID || "webapp",
  redirect_uri: window.location.origin,
};

declare global {
  interface Window {
    API_URL?: string;
  }
}

export const config = {
  apiUrl: window.API_URL || import.meta.env.VITE_API_URL || "/api"
}

export const oidcConfig = {
  authority: "https://gazprom-auth.banzai-predict.site/realms/gazprom-mrkt",
  client_id: "webapp",
  redirect_uri: window.location.origin,
};

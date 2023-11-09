import {HelmetProvider} from "react-helmet-async";
import {AuthProvider} from "~/components/contexts/UserContext";
import {Router} from "~/components/router/Router";
import { QueryClient, QueryClientProvider } from "react-query";

export const App = () => {
    const queryClient = new QueryClient();
    
    return (
        <HelmetProvider>
            <AuthProvider>
                <QueryClientProvider client={queryClient}>
                    <main>
                        <Router/>
                    </main>
                </QueryClientProvider>
            </AuthProvider>
        </HelmetProvider>
    )
};

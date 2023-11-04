import {HelmetProvider} from "react-helmet-async";
import {AuthProvider} from "~/components/contexts/UserContext";
import {Router} from "~/components/router/Router";

export const App = () => {
    return (
        <HelmetProvider>
            <AuthProvider>
                <main>
                    <Router/>
                </main>
            </AuthProvider>
        </HelmetProvider>
    )
};
